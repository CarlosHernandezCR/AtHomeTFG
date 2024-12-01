package org.tfg.inhometfgcarloshernandez.domain.servicios;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tfg.inhometfgcarloshernandez.data.model.CasaEntity;
import org.tfg.inhometfgcarloshernandez.data.model.EventoEntity;
import org.tfg.inhometfgcarloshernandez.data.model.UsuarioEntity;
import org.tfg.inhometfgcarloshernandez.data.repositories.EventosRepository;
import org.tfg.inhometfgcarloshernandez.data.repositories.UsuarioRepository;
import org.tfg.inhometfgcarloshernandez.domain.model.Evento;
import org.tfg.inhometfgcarloshernandez.domain.model.mappers.EventoMappers;
import org.tfg.inhometfgcarloshernandez.spring.model.request.EventoDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventosServicios {

    private final EventosRepository eventosRepository;
    private final EventoMappers eventoMappers;
    private final UsuarioRepository usuarioRepository;

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

    public void crearEvento(int idCasa, EventoDTO evento, String fecha) {
        EventoEntity eventoEntity = new EventoEntity();

        eventoEntity.setNombre(evento.getNombre());

        eventoEntity.setTipo(evento.getTipo());

        int nResidentes= usuarioRepository.findNumeroResidentes(idCasa);
        String votacion= "1/" + nResidentes;
        eventoEntity.setVotacion(votacion);

        UsuarioEntity organizador = new UsuarioEntity();
        organizador.setId(Integer.parseInt(evento.getOrganizador()));
        eventoEntity.setOrganizador(organizador);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fechaEntity = LocalDate.parse(fecha, formatter);
        eventoEntity.setFecha(fechaEntity);

        CasaEntity casaEntity = new CasaEntity();
        casaEntity.setId(idCasa);
        eventoEntity.setIdCasaEntity(casaEntity);

        LocalTime horaComienzo = LocalTime.parse(evento.getHoraComienzo());
        LocalTime horaFin = LocalTime.parse(evento.getHoraFin());
        eventoEntity.setHoraComienzo(horaComienzo);
        eventoEntity.setHoraFin(horaFin);

        eventosRepository.save(eventoEntity);
    }
}