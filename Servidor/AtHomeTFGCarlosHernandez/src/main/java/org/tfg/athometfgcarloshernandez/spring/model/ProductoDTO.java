package org.tfg.athometfgcarloshernandez.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductoDTO {
    private String id;
    private String nombre;
    private int unidades;
    private String imagen;
}
