package org.iesvdm.controlador;

import org.iesvdm.dto.ComercialDTO;
import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
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
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
public class ComercialController {

    private ComercialService comercialService;

    @Autowired
    ClienteService clienteService;

    public ComercialController(ComercialService comercialService) {
        this.comercialService = comercialService;
    }

    @GetMapping("/comerciales")
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



    @GetMapping("/comerciales/crear")
    public String crear(@ModelAttribute("comercial") Comercial comercial) {

        return "crear-comercial";
    }

    @PostMapping("/comerciales/crear")
        public String submitCrear(@Valid @ModelAttribute("comercial") Comercial comercial, BindingResult bindingResult) {
            if (bindingResult.hasErrors()){
                return "crear-comercial";
            }
            comercialService.newComercial(comercial);
            return "redirect:/comerciales";
        }

    @GetMapping("/comerciales/editar/{id}")
    public String editar(Model model, @PathVariable Integer id) {

        Comercial comercial = comercialService.one(id);
        model.addAttribute("comercial", comercial);

        return "editar-comercial";

    }

    @PostMapping("/comerciales/editar/{id}")
        public String submitEditar(@Valid @ModelAttribute("comercial") Comercial comercial, BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                return "editar-comercial";
            }
            comercialService.replaceComercial(comercial);
            return "redirect:/comerciales";
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