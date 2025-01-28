package org.iesvdm.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    private int id;
    private double total;
    private Date fecha;
    private int id_cliente;
    private int id_comercial;

}
