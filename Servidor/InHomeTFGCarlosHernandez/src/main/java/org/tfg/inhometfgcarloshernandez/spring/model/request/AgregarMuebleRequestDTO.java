package org.tfg.inhometfgcarloshernandez.spring.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgregarMuebleRequestDTO {
    private int idCasa;
    private String nombreHabitacion;
    private String nombre;
}
