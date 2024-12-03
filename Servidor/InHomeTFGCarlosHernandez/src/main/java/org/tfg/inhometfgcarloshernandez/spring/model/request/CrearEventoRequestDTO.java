package org.tfg.inhometfgcarloshernandez.spring.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tfg.inhometfgcarloshernandez.spring.model.EventoDTO;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CrearEventoRequestDTO {
    private int idCasa;
    private EventoDTO eventoCasa;
    private String fecha;
}
