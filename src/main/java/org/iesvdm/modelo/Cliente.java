package org.iesvdm.modelo;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
	private int id;

	@Size(max = 30, message = "{error.nombre.size.max}")
	private String nombre;

	@NotBlank
	@Size(max = 30, message = "{error.apellido1.size.max}")
	private String apellido1;

	@Size(max = 30, message = "{error.apellido2.size.max}")
	private String apellido2;

	@NotBlank
	@Size(max = 50, message = "{error.ciudad.size.max}")
	private String ciudad;

	@Min(value = 100, message = "{error.categoria.min}")
	@Max(value = 1000, message = "{error.categoria.max}")
	private int categoria;

	@Email(message = "{error.correo.formato}", regexp="^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-zA-Z.]{2,5}")
	@NotBlank(message = "{error.correo.blanco}")
	private String correo;
}