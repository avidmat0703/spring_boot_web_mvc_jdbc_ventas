package org.iesvdm.controlador;

import org.iesvdm.dto.ComercialDTO;
import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.iesvdm.service.ClienteService;
import org.iesvdm.service.ComercialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ComercialController {

    private ComercialService comercialService;

    @Autowired
    ClienteService clienteService;

    //Se utiliza inyección automática por constructor del framework Spring.
    //Por tanto, se puede omitir la anotación Autowired
    //@Autowired
    public ComercialController(ComercialService comercialService) {
        this.comercialService = comercialService;
    }

    @GetMapping("/comerciales") //Al no tener ruta base para el controlador, cada método tiene que tener la ruta completa
    public String listar(Model model) {

        List<Comercial> listaComerciales =  comercialService.listAll();
        model.addAttribute("listaComerciales", listaComerciales);

        return "comerciales";

    }

    @GetMapping("/comerciales/{id}")
    public String detalle(Model model, @PathVariable Integer id) {

        ComercialDTO comercialDTO = comercialService.getComercialDTO(id);
        List<PedidoDTO> pedidos = comercialService.listAllPedidosDTOByComercialId(id);
        Map<Cliente, Double> totalPorCliente = new HashMap<>();

        for (PedidoDTO pedido : pedidos){
            Cliente cliente = clienteService.one(pedido.getId_cliente());
            double sumtotal = clienteService.getSumTotalPedidos(cliente.getId());
            totalPorCliente.put(cliente, sumtotal);
        }

        // Ordenar el mapa de mayor a menor por el total
        Map<Cliente, Double> sortedTotalPorCliente = totalPorCliente.entrySet()
                .stream()
                .sorted(Map.Entry.<Cliente, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> entry.getValue(),
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        model.addAttribute("clientes", sortedTotalPorCliente);
        model.addAttribute("comercial", comercialDTO);
        model.addAttribute("pedidos", pedidos);

        return "detalle-comercial";
    }



    @GetMapping("/comerciales/crear") //Al no tener ruta base para el controlador, cada método tiene que tener la ruta completa
    public String crear(@ModelAttribute("comercial") Comercial comercial) {

        return "crear-comercial";
    }

    @PostMapping("/comerciales/crear")
    public RedirectView submitCrear(@ModelAttribute("comercial") Comercial comercial) {

        comercialService.newComercial(comercial);
        return new RedirectView("/comerciales") ;
    }

    @GetMapping("/comerciales/editar/{id}")
    public String editar(Model model, @PathVariable Integer id) {

        Comercial comercial = comercialService.one(id);
        model.addAttribute("comercial", comercial);

        return "editar-comercial";

    }

    @PostMapping("/comerciales/editar/{id}")
    public RedirectView submitEditar(@ModelAttribute("comercial") Comercial comercial) {

        comercialService.replaceComercial(comercial);

        return new RedirectView("/comerciales");
    }

    @PostMapping("/comerciales/borrar/{id}")
    public RedirectView submitBorrar(@PathVariable Integer id) {

        comercialService.deleteComercial(id);

        return new RedirectView("/comerciales");
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

}
