package org.tfg.inhometfgcarloshernandez.spring.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgregarCajonRequestDTO {
    private int idCasa;
    private String nombreHabitacion;
    private String nombreMueble;
    private String nombre;
    private int idPropietario;
}
