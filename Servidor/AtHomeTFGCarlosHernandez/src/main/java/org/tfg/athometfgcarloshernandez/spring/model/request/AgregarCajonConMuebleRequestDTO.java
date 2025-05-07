package org.tfg.athometfgcarloshernandez.spring.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgregarCajonConMuebleRequestDTO {
    private Integer idMueble;
    private String nombre;
    private Integer idPropietario;
}