package org.tfg.inhometfgcarloshernandez.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Evento {
    private int id;
    private String tipo;
    private String nombre;
    private String votacion;
    private LocalTime horaComienzo;
    private LocalTime horaFin;
    private String organizador;
}
