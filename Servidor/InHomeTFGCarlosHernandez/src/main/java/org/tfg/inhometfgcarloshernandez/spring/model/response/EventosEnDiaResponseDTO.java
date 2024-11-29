package org.tfg.inhometfgcarloshernandez.spring.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tfg.inhometfgcarloshernandez.domain.model.Evento;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventosEnDiaResponseDTO {
    private List<Evento> eventosResponseDTO;
}
