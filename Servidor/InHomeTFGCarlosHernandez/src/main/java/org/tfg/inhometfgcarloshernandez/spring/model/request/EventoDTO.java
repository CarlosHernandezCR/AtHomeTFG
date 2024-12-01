package org.tfg.inhometfgcarloshernandez.spring.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoDTO {
    private String tipo;
    private String nombre;
    private String votacion;
    private String horaComienzo;
    private String horaFin;
    private String organizador;
}
