package org.iesvdm.dao;

import java.util.List;
import java.util.Optional;

import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.text.html.Option;

//Anotación lombok para logging (traza) de la aplicación
@Slf4j
@Repository
//Utilizo lombok para generar el constructor
@AllArgsConstructor
public class ComercialDAOImpl implements ComercialDAO {

	//JdbcTemplate se inyecta por el constructor de la clase automáticamente
	//
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
                							  rs.getFloat("comisión"))
                						 	
        );
		
		log.info("Devueltos {} registros.", listComercial.size());
		
        return listComercial;
	}

	@Override
	public Optional<Comercial> find(int id) {

		Optional<Comercial> optCom = jdbcClient.sql("""
													SELECT * FROM comercial WHERE id = :id
													""")
				.param("id",id)
				.query(Comercial.class)
				.optional();

		return optCom;
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
                WHERE
                id = :id
                """;
		int rowsUpdated = jdbcClient.sql(query)
				.paramSource(comercial)
				.update();

		log.info("Actualizados {} registros",rowsUpdated);

	}

	@Override
	public void delete(long id) {

		//Borramos todos los pedidos que coincidan con el id de comercial
		String queryEliminacionCascada = """
                DELETE FROM pedido WHERE id_comercial = :id
                """;
		int rowsDeleted1 = jdbcClient.sql(queryEliminacionCascada)
				.param("id",id)
				.update();

		log.info("Borrados {} registros",rowsDeleted1);

		//Borramos el comercial
		String query = """
                DELETE FROM comercial WHERE id = :id
                """;
		int rowsDeleted2 = jdbcClient.sql(query)
				.param("id",id)
				.update();

		log.info("Borrados {} registros",rowsDeleted2);
	}

}
