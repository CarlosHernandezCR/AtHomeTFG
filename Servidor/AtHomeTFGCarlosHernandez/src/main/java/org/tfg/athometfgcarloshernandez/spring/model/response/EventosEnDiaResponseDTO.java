package org.tfg.athometfgcarloshernandez.spring.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tfg.athometfgcarloshernandez.domain.model.Evento;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventosEnDiaResponseDTO {
    private List<Evento> eventosResponseDTO;
}
