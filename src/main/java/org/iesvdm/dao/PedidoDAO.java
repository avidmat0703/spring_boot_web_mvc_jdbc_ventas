package org.iesvdm.dao;

import java.util.List;
import java.util.Optional;

import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.modelo.Pedido;

public interface PedidoDAO {

    public void create(Pedido pedido);

    public List<Pedido> getAll();

    public List<Pedido> getAllByComercialId(int id);

    public List<PedidoDTO> getAllDTOByComercialId(int id);

    List<PedidoDTO> getAllDTOByClienteId(int id_cliente);

    public Optional<Pedido> find(int id);

    public void update(Pedido pedido);

    public void delete(long id);
}

