package org.tfg.athometfgcarloshernandez.spring.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class AgregarCajonConMuebleRequestDTO {
    private String idMueble;
    private String nombre;
    private Integer idPropietario;
}
