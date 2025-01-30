package org.iesvdm.dao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@AllArgsConstructor
public class PedidoDAOImpl implements PedidoDAO{

    @Autowired
    JdbcClient jdbcClient;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void create(Pedido pedido) {

    }

    @Override
    public List<Pedido> getAll() {

        String query = """
                SELECT * FROM pedido
                """;

        RowMapper<Pedido> rowMapperPedido = (rs, rowNum) -> new Pedido(
                rs.getInt("id"),
                rs.getDouble("total"),
                rs.getDate("fecha"),
                rs.getInt("id_cliente"),
                rs.getInt("id_comercial")
        );

        return jdbcClient.sql(query)
                .query(rowMapperPedido)
                .list();

    }

    @Override
    public List<Pedido> getAllByComercialId(int id) {

        String query = """
                SELECT * FROM pedido WHERE id_comercial = :id
                """;

        RowMapper<Pedido> rowMapperPedido = (rs, rowNum) -> new Pedido(
                rs.getInt("id"),
                rs.getDouble("total"),
                rs.getDate("fecha"),
                rs.getInt("id_cliente"),
                rs.getInt("id_comercial")
        );

        return jdbcClient.sql(query)
                .param("id",id)
                .query(rowMapperPedido)
                .list();

    }

    @Override
    public List<PedidoDTO> getAllDTOByComercialId(int id_comercial) {

        String query = """
            SELECT p.*, c.nombre AS nombreCliente, com.nombre AS nombreComercial
            FROM pedido p
            JOIN cliente c ON p.id_cliente = c.id
            JOIN comercial com ON p.id_comercial = com.id
            WHERE p.id_comercial = ?
            """;

        BeanPropertyRowMapper<PedidoDTO> rowMapper = new BeanPropertyRowMapper<>(PedidoDTO.class);

        List<PedidoDTO> pedidoDTOList = jdbcClient.sql(query)
                .param(id_comercial)
                .query(rowMapper)
                .list();

        return pedidoDTOList;
    }

    @Override
    public List<PedidoDTO> getAllDTOByClienteId(int id_cliente) {

        String query = """
            SELECT p.*, c.nombre AS nombreCliente, com.nombre AS nombreComercial
            FROM pedido p
            JOIN cliente c ON p.id_cliente = c.id
            JOIN comercial com ON p.id_comercial = com.id
            WHERE p.id_cliente = ?
            """;

        BeanPropertyRowMapper<PedidoDTO> rowMapper = new BeanPropertyRowMapper<>(PedidoDTO.class);

        List<PedidoDTO> pedidoDTOList = jdbcClient.sql(query)
                .param(id_cliente)
                .query(rowMapper)
                .list();

        return pedidoDTOList;
    }

    @Override
    public Optional<Pedido> find(int id) {
        return Optional.empty();
    }

    @Override
    public void update(Pedido pedido) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public int contarPedidosEnPeriodo(int id_comercial, int id_cliente, int meses) {
        String query = """
               SELECT COUNT(*)
               FROM pedido
               WHERE id_cliente = ?
               AND id_comercial = ?
               AND fecha >= CURDATE() - INTERVAL ? MONTH
			""";

        return jdbcClient.sql(query)
                .param(id_cliente)
                .param(id_comercial)
                .param(meses)
                .query(Integer.class)
                .single();
    }
}