package org.iesvdm.controlador;

import java.util.List;
import org.iesvdm.dto.ComercialDTO2;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import jakarta.validation.constraints.Size;

@Controller
public class ClienteController {

	private ClienteService clienteService;

	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@GetMapping("/clientes")
	public String listar(Model model) {

		List<Cliente> listaClientes =  clienteService.listAll();
		model.addAttribute("listaClientes", listaClientes);

		return "clientes";

	}

	@GetMapping("/clientes/{id}")
	public String detalle(Model model, @PathVariable Integer id) {

		Cliente cliente = clienteService.one(id);
		model.addAttribute("cliente", cliente);

		List<ComercialDTO2> comercialDTOList = clienteService.getComercialesDTOAsociados(id);
		model.addAttribute("comerciales",comercialDTOList);
		System.out.println("Comerciales encontrados: " + comercialDTOList.size());
		for (ComercialDTO2 c : comercialDTOList) {
			System.out.println("Nombre: " + c.getNombre());
		}

		return "detalle-cliente";
	}

	@GetMapping("/clientes/crear")
	public String crear(@ModelAttribute ("cliente") Cliente cliente, Model model) {

		return "crear-cliente";
	}

	@PostMapping("/clientes/crear")
		public String submitCrear(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult bindingResult) {

			if (bindingResult.hasErrors()) {
				return "crear-cliente";
			}
			clienteService.newCliente(cliente);
			return "redirect:/clientes";
		}

	@GetMapping("/clientes/editar/{id}")
	public String editar(Model model, @PathVariable Integer id) {

		Cliente cliente = clienteService.one(id);
		model.addAttribute("cliente", cliente);

		return "editar-cliente";

	}

	@PostMapping("/clientes/editar/{id}")
		public String submitEditar(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult bindingResult) {
			if (bindingResult.hasErrors()) {
				return "editar-cliente";
			}
			clienteService.replaceCliente(cliente);
			return "redirect:/clientes";
		}

	@PostMapping("/clientes/borrar/{id}")
	public RedirectView submitBorrar(@PathVariable Integer id) {

		clienteService.deleteCliente(id);

		return new RedirectView("/clientes");
	}
}