package org.iesvdm.service;

import org.iesvdm.dao.ComercialDAO;
import org.iesvdm.dao.PedidoDAO;
import org.iesvdm.dto.ComercialDTO;
import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
public class ComercialService {

    private ComercialDAO comercialDAO;

    @Autowired
    PedidoDAO pedidoDAO;
    // Se utiliza inyección automática por constructor del framework Spring.
    // Por tanto, se puede omitir la anotación Autowired
    // @Autowired
    public ComercialService(ComercialDAO comercialDAO) {
        this.comercialDAO = comercialDAO;
    }

    public ComercialDTO getComercialDTO (Integer id) {

        Comercial comercial = one(id);

        //Creo lista de pedidos asociados a un comercial
        List<PedidoDTO> pedidoDTOList = pedidoDAO.getAllDTOByComercialId(comercial.getId());

        //Total de totales de pedidos
        double total = pedidoDTOList.stream()
                .mapToDouble( p -> p.getTotal())
                .sum();

        OptionalDouble totalMax = pedidoDTOList.stream()
                .mapToDouble( p -> p.getTotal())
                .max();

        OptionalDouble totalMin = pedidoDTOList.stream()
                .mapToDouble( p -> p.getTotal())
                .min();

        int cantidadPedidos = pedidoDTOList.size();
        double media = total/cantidadPedidos;

        ComercialDTO comercialDTO = ComercialDTO.builder()
                .comercial(comercial)
                .total(total)
                .media(media)
                .totalMax(totalMax.getAsDouble())
                .totalMin(totalMin.getAsDouble())
                .build();

        return comercialDTO;
    }

    public List<Comercial> listAll() {
        return comercialDAO.getAll();
    }

    public List<Pedido> listAllPedidos(int id) {
        return pedidoDAO.getAllByComercialId(id);
    }

    public List<PedidoDTO> listAllPedidosDTOByComercialId(int id){
        return pedidoDAO.getAllDTOByComercialId(id);
    }

    public Comercial one(Integer id) {
        Optional<Comercial> optCom = comercialDAO.find(id);
        return optCom.orElse(null);
    }

    public void newComercial(Comercial comercial) {
        comercialDAO.create(comercial);
    }

    public void replaceComercial(Comercial comercial) {
        comercialDAO.update(comercial);
    }

    public void deleteComercial(int id) {
        comercialDAO.delete(id);
    }
}


