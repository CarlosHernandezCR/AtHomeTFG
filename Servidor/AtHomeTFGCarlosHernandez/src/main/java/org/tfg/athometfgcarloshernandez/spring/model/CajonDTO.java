package org.tfg.athometfgcarloshernandez.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CajonDTO {
    private int id;
    private String nombre;
    private String idPropietario;
    private String propietario;
}
