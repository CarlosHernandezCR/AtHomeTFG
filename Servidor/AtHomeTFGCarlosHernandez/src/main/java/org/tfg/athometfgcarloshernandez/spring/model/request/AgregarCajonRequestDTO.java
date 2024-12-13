package org.tfg.athometfgcarloshernandez.spring.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgregarCajonRequestDTO {
    private int idCasa;
    private int idHabitacion;
    private int idMueble;
    private String nombre;
    private int idPropietario;
}
