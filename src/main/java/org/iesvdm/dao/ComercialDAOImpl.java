package org.iesvdm.dao;

import java.util.List;
import java.util.Optional;
import org.iesvdm.modelo.Comercial;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@AllArgsConstructor
public class ComercialDAOImpl implements ComercialDAO {

	private JdbcTemplate jdbcTemplate;
	private JdbcClient jdbcClient;

	@Override
	public void create(Comercial comercial) {

		KeyHolder keyHolder = new GeneratedKeyHolder();

		int rowsUpdated = jdbcClient.sql("""
				INSERT INTO comercial (nombre, apellido1, apellido2, comisión)
				VALUES (?,?,?,?)
				""")
				.param(comercial.getNombre())
				.param(comercial.getApellido1())
				.param(comercial.getApellido2())
				.param(comercial.getComision())
				.update(keyHolder);

		comercial.setId(keyHolder.getKey().intValue());
		log.info("Insertados {} registros",rowsUpdated);

	}

	@Override
	public List<Comercial> getAll() {
		
		List<Comercial> listComercial = jdbcTemplate.query(
				"SELECT * FROM comercial",
				(rs, rowNum) -> new Comercial(rs.getInt("id"),
						rs.getString("nombre"),
						rs.getString("apellido1"),
						rs.getString("apellido2"),
						rs.getBigDecimal("comisión"))
		);
		
		log.info("Devueltos {} registros.", listComercial.size());
		
        return listComercial;
	}

	@Override
	public Optional<Comercial> find(int id) {
		Optional<Comercial> comercial = jdbcClient.sql("""
                            SELECT * FROM comercial WHERE id = ?
                           """)
				.param(id)
				.query((rs, rowNum) -> new Comercial(
						rs.getInt("id"),
						rs.getString("nombre"),
						rs.getString("apellido1"),
						rs.getString("apellido2"),
						rs.getBigDecimal("comisión")))
				.optional();
		return comercial;
	}

	@Override
	public void update(Comercial comercial) {

		String query = """
                UPDATE comercial
                SET
                nombre = :nombre,
				apellido1 = :apellido1,
                apellido2 = :apellido2,
				comisión = :comision
                WHERE id = :id
                """;
		int rowsUpdated = jdbcClient.sql(query)
				.paramSource(comercial)
				.update();

		log.info("Actualizados {} registros",rowsUpdated);

	}

	@Override
	public void delete(long id) {

		String queryEliminacionCascada = """
                DELETE FROM pedido WHERE id_comercial = ?
                """;
		int rowsDeleted1 = jdbcClient.sql(queryEliminacionCascada)
				.param(id)
				.update();

		log.info("Borrados {} registros",rowsDeleted1);

		String query = """
                DELETE FROM comercial WHERE id = :id
                """;
		int rowsDeleted2 = jdbcClient.sql(query)
				.param(id)
				.update();

		log.info("Borrados {} registros",rowsDeleted2);
	}
}