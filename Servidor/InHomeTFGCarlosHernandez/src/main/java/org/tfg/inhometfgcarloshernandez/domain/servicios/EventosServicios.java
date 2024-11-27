package org.tfg.inhometfgcarloshernandez.domain.servicios;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tfg.inhometfgcarloshernandez.data.repositories.EventosRepository;
import org.tfg.inhometfgcarloshernandez.spring.model.response.DiasEventosResponseDTO;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventosServicios {

    private final EventosRepository eventosRepository;

    public DiasEventosResponseDTO getEventosMes(int idCasa, int mes, int anio) {
        List<Integer> diasConEvento = eventosRepository.findDiasConEventos(idCasa, mes, anio);
        if (diasConEvento == null || diasConEvento.isEmpty()) {
            diasConEvento = new ArrayList<>();
        }
        return new DiasEventosResponseDTO(diasConEvento);
    }
}
