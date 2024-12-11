package org.tfg.inhometfgcarloshernandez.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CasaDetallesDTO {
    private int id;
    private String nombre;
    private String direccion;
    private String codigo;
}
