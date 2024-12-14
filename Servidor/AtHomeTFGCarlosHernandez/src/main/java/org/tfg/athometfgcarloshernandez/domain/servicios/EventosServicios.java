package org.tfg.athometfgcarloshernandez.domain.servicios;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tfg.athometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.athometfgcarloshernandez.data.model.*;
import org.tfg.athometfgcarloshernandez.data.repositories.EventosRepository;
import org.tfg.athometfgcarloshernandez.data.repositories.UsuarioRepository;
import org.tfg.athometfgcarloshernandez.data.repositories.VotoRepository;
import org.tfg.athometfgcarloshernandez.domain.errores.CustomedException;
import org.tfg.athometfgcarloshernandez.domain.errores.YaVotadoException;
import org.tfg.athometfgcarloshernandez.domain.model.Evento;
import org.tfg.athometfgcarloshernandez.domain.model.mappers.EventoMappers;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesError;
import org.tfg.athometfgcarloshernandez.spring.model.EventoDTO;

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
    private final VotoRepository votoRepository;

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constantes.FORMATO_FECHA);
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

public void votar(int idUsuario, int idEvento) {
    UsuarioEntity usuarioEntity = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new CustomedException(ConstantesError.USUARIO_NO_ENCONTRADO));
    EventoEntity eventoEntity = eventosRepository.findById(idEvento)
            .orElseThrow(() -> new CustomedException(ConstantesError.EVENTO_NO_ENCONTRADO));
    List<VotoEntity> votoEntities = votoRepository.findByEvento(eventoEntity);

    boolean yaVotado = votoEntities.stream()
            .anyMatch(voto -> voto.getUsuario() != null && voto.getUsuario().getId() == idUsuario);

    if (yaVotado) {
        throw new YaVotadoException(ConstantesError.YA_VOTADO);
    } else {
        VotoEntity votoEntity = new VotoEntity();
        votoEntity.setUsuario(usuarioEntity);
        votoEntity.setEvento(eventoEntity);
        votoRepository.save(votoEntity);

        String votacion = eventoEntity.getVotacion();
        String[] votos = votacion.split("/");
        int votosActuales = Integer.parseInt(votos[0]);
        int nResidentes = Integer.parseInt(votos[1]);
        votosActuales++;
        if (votosActuales == nResidentes) {
            eventoEntity.setVotacion(Constantes.VOTACION_ACEPTADA);
        } else {
            eventoEntity.setVotacion(votosActuales + "/" + nResidentes);
        }
        eventosRepository.save(eventoEntity);
    }
}
}