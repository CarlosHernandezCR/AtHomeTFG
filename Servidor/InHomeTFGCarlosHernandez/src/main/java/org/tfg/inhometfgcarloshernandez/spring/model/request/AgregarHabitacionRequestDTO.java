package org.tfg.inhometfgcarloshernandez.spring.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgregarHabitacionRequestDTO {
    private int idCasa;
    private String nombre;
}
