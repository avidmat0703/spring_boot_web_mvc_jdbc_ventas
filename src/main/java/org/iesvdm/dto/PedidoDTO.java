package org.iesvdm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoDTO {

    int id;
    double total;
    Date fecha;
    int id_cliente;
    int id_comercial;

    String nombreCliente;
    String nombreComercial;

}
