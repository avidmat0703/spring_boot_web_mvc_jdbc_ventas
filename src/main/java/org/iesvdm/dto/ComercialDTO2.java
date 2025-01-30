package org.iesvdm.dto;

import lombok.Data;
import org.iesvdm.modelo.Comercial;
import java.math.BigDecimal;

@Data
public class ComercialDTO2 extends Comercial{
    private int numeroPedidosPorCliente;
    private int pedidosUltimoTrimestre;
    private int pedidosUltimoSemestre;
    private int pedidosUltimoAnio;
    private int pedidosUltimoLustro;
    public ComercialDTO2(int id, String nombre, String apellido1, String apellido2, BigDecimal comision,
                         int numeroPedidosPorCliente, int pedidosUltimoTrimestre,
                         int pedidosUltimoSemestre, int pedidosUltimoAnio, int pedidosUltimoLustro) {
        super(id, nombre, apellido1, apellido2, comision);
        this.numeroPedidosPorCliente = numeroPedidosPorCliente;
        this.pedidosUltimoTrimestre = pedidosUltimoTrimestre;
        this.pedidosUltimoSemestre = pedidosUltimoSemestre;
        this.pedidosUltimoAnio = pedidosUltimoAnio;
        this.pedidosUltimoLustro = pedidosUltimoLustro;
    }
}