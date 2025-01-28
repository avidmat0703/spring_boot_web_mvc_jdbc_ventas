package org.iesvdm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.iesvdm.modelo.Comercial;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComercialDTO {

    Comercial comercial;

    double total;
    double media;
    double totalMax;
    double totalMin;

}
