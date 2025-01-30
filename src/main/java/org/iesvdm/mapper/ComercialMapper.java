package org.iesvdm.mapper;

import org.iesvdm.dto.ComercialDTO2;
import org.iesvdm.modelo.Comercial;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComercialMapper {

    @Mapping(target = "numeroPedidosPorCliente", source = "numeroPedidosPorClienteIn")
    @Mapping(target = "pedidosUltimoTrimestre", source = "pedidosTrimestreIn")
    @Mapping(target = "pedidosUltimoSemestre", source = "pedidosSemestreIn")
    @Mapping(target = "pedidosUltimoAnio", source = "pedidosAnioIn")
    @Mapping(target = "pedidosUltimoLustro", source = "pedidosLustroIn")
    ComercialDTO2 comercialAComercialDTO2(
            Comercial comercial,
            int numeroPedidosPorClienteIn,
            int pedidosTrimestreIn,
            int pedidosSemestreIn,
            int pedidosAnioIn,
            int pedidosLustroIn
    );

    Comercial comercialDTO2ACliente(ComercialDTO2 comercialDTO2);
}