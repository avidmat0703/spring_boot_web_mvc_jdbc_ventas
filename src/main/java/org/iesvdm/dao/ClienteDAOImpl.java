package org.iesvdm.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class ClienteDAOImpl implements ClienteDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private JdbcClient jdbcClient;

	@Override
	public synchronized void create(Cliente cliente) {

		String sqlInsert = """
							INSERT INTO cliente (nombre, apellido1, apellido2, ciudad, categoría) 
							VALUES  (     ?,         ?,         ?,       ?,         ?)
						   """;

		KeyHolder keyHolder = new GeneratedKeyHolder();
		int rows = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[] { "id" });
			int idx = 1;
			ps.setString(idx++, cliente.getNombre());
			ps.setString(idx++, cliente.getApellido1());
			ps.setString(idx++, cliente.getApellido2());
			ps.setString(idx++, cliente.getCiudad());
			ps.setInt(idx, cliente.getCategoria());
			return ps;
		},keyHolder);

		cliente.setId(keyHolder.getKey().intValue());

		log.info("Insertados {} registros.", rows);
	}

	@Override
	public List<Cliente> getAll() {

		List<Cliente> listFab = jdbcTemplate.query(
				"SELECT * FROM cliente",
				(rs, rowNum) -> new Cliente(rs.getInt("id"),
						rs.getString("nombre"),
						rs.getString("apellido1"),
						rs.getString("apellido2"),
						rs.getString("ciudad"),
						rs.getInt("categoría")
				)
		);

		log.info("Devueltos {} registros.", listFab.size());

		return listFab;

	}

	@Override
	public Optional<Cliente> find(int id) {

		Cliente fab =  jdbcTemplate
				.queryForObject("SELECT * FROM cliente WHERE id = ?"
						, (rs, rowNum) -> new Cliente(rs.getInt("id"),
								rs.getString("nombre"),
								rs.getString("apellido1"),
								rs.getString("apellido2"),
								rs.getString("ciudad"),
								rs.getInt("categoría"))
						, id
				);

		if (fab != null) {
			return Optional.of(fab);}
		else {
			log.info("Cliente no encontrado.");
			return Optional.empty(); }

	}

	@Override
	public void update(Cliente cliente) {

		int rows = jdbcTemplate.update("""
										UPDATE cliente SET 
														nombre = ?, 
														apellido1 = ?, 
														apellido2 = ?,
														ciudad = ?,
														categoría = ?  
												WHERE id = ?
										""", cliente.getNombre()
				, cliente.getApellido1()
				, cliente.getApellido2()
				, cliente.getCiudad()
				, cliente.getCategoria()
				, cliente.getId());

		log.info("Update de Cliente con {} registros actualizados.", rows);

	}

	@Override
	public void delete(long id) {
		int rows1 = jdbcTemplate.update("DELETE FROM pedido WHERE id_cliente = ?", id);
		int rows = jdbcTemplate.update("DELETE FROM cliente WHERE id = ?", id);

		log.info("Delete de pedido con {} registros eliminados.", rows1);
		log.info("Delete de Cliente con {} registros eliminados.", rows);


	}

	@Override
	public List<Comercial> getComercialesById(int id) {

		String query = """
			SELECT com.*
			FROM comercial com
			JOIN pedido p ON com.id = p.id_comercial
			WHERE p.id_cliente = ?
			""";

		BeanPropertyRowMapper<Comercial> rowMapper = new BeanPropertyRowMapper<>(Comercial.class);

		return jdbcClient.sql(query)
				.param(id)
				.query(rowMapper)
				.list();
	}

	@Override
	public Integer getPedidosEnComun(int id_cliente, int id_comercial) {

		String query = """
			SELECT count(*)
			FROM comercial com
			JOIN pedido p ON com.id = p.id_comercial
			WHERE p.id_cliente = ? AND p.id_comercial = ?
			""";

		return jdbcClient.sql(query)
							.param(id_cliente)
							.param(id_comercial)
							.query(Integer.class)
							.single();
	}
}