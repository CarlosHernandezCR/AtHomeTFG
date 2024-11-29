package org.tfg.inhometfgcarloshernandez.domain.servicios;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tfg.inhometfgcarloshernandez.data.model.EventoEntity;
import org.tfg.inhometfgcarloshernandez.data.repositories.EventosRepository;
import org.tfg.inhometfgcarloshernandez.domain.model.Evento;
import org.tfg.inhometfgcarloshernandez.domain.model.mappers.EventoMappers;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventosServicios {

    private final EventosRepository eventosRepository;
    private final EventoMappers eventoMappers;

    public List<Integer> getEventosMes(int idCasa, int mes, int anio) {
        List<Integer> diasConEvento = eventosRepository.findDiasConEventos(idCasa, mes, anio);
        if (diasConEvento == null || diasConEvento.isEmpty()) {
            diasConEvento = new ArrayList<>();
        }
        return diasConEvento;
    }

    public List<Evento> getEventosDia(int idCasa, int dia, int mes, int anio) {
        List<EventoEntity> diasConEvento = eventosRepository.findEventosEnDia(idCasa, dia, mes, anio);
        if (diasConEvento == null || diasConEvento.isEmpty()) {
            diasConEvento = new ArrayList<>();
        }
        return diasConEvento.stream().map(eventoMappers::toEvento).toList();
    }
}