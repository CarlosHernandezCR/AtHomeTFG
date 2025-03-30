package org.tfg.athometfgcarloshernandez.spring.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AgregarProductoRequestDTO {
    private String idCajon;
    private String nombre;
    private Integer cantidad;
}
