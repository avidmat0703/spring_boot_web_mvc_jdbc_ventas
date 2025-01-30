package org.iesvdm.service;

import java.util.*;
import org.iesvdm.dao.ClienteDAO;
import org.iesvdm.dao.PedidoDAO;
import org.iesvdm.dto.ComercialDTO2;
import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.mapper.ComercialMapper;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
	
	private ClienteDAO clienteDAO;

	@Autowired
	PedidoDAO pedidoDAO;
	@Autowired
	ComercialMapper comercialMapper;

	public ClienteService(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}
	
	public List<Cliente> listAll() {
		
		return clienteDAO.getAll();
		
	}

	public Cliente one(Integer id) {
		Optional<Cliente> optCli = clienteDAO.find(id);
        return optCli.orElse(null);
	}

	public void newCliente(Cliente cliente) {

		clienteDAO.create(cliente);

	}

	public void replaceCliente(Cliente cliente) {

		clienteDAO.update(cliente);

	}

	public void deleteCliente(int id) {

		clienteDAO.delete(id);

	}

	public double getSumTotalPedidos (int id){
		List<PedidoDTO> pedidoDTOList = pedidoDAO.getAllDTOByClienteId(id);

        return pedidoDTOList.stream()
                .mapToDouble(PedidoDTO::getTotal)
                .sum();
	}

	public List<ComercialDTO2> getComercialesDTOAsociados(int id){

		List<Comercial> comerciales = clienteDAO.getComercialesById(id);
		Set<ComercialDTO2> comercialesDTO = new HashSet<>();

		for (Comercial comercial : comerciales){
			int pedidosComun = clienteDAO.getPedidosEnComun(comercial.getId(), id);
			int pedidosTrimestre = pedidoDAO.contarPedidosEnPeriodo(comercial.getId(), id, 3);
			int pedidosSemestre = pedidoDAO.contarPedidosEnPeriodo(comercial.getId(), id, 6);
			int pedidosAnio = pedidoDAO.contarPedidosEnPeriodo(comercial.getId(), id, 12);
			int pedidosLustro = pedidoDAO.contarPedidosEnPeriodo(comercial.getId(), id, 60);

			ComercialDTO2 comercialDTO2 = comercialMapper.comercialAComercialDTO2(
					comercial,
					pedidosComun,
					pedidosTrimestre,
					pedidosSemestre,
					pedidosAnio,
					pedidosLustro
			);
			comercialesDTO.add(comercialDTO2);
		}
		return new ArrayList<>(comercialesDTO);
	}
}