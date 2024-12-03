package org.tfg.inhometfgcarloshernandez.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tfg.inhometfgcarloshernandez.domain.model.Evento;
import org.tfg.inhometfgcarloshernandez.domain.servicios.EventosServicios;
import org.tfg.inhometfgcarloshernandez.spring.common.constantes.ConstantesServer;
import org.tfg.inhometfgcarloshernandez.spring.model.request.CrearEventoRequestDTO;
import org.tfg.inhometfgcarloshernandez.spring.model.request.DiasEventosRequestDTO;
import org.tfg.inhometfgcarloshernandez.spring.model.request.EventosEnDiaRequestDTO;
import org.tfg.inhometfgcarloshernandez.spring.model.response.DiasConEventosResponseDTO;
import org.tfg.inhometfgcarloshernandez.spring.model.response.EventosEnDiaResponseDTO;

import java.util.List;

import static org.tfg.inhometfgcarloshernandez.spring.common.constantes.ConstantesServer.GET_EVENTOS_MES;


@RestController
@RequestMapping({ConstantesServer.CALENDARIOPATH})
public class CalendarioController {
    private final EventosServicios eventosServicios;

    @Autowired
    public CalendarioController(EventosServicios eventosServicios) {
        this.eventosServicios = eventosServicios;
    }

    @GetMapping(GET_EVENTOS_MES)
    public ResponseEntity<DiasConEventosResponseDTO> getEventosMes(
            @RequestParam int idCasa,
            @RequestParam int mes,
            @RequestParam int anio
    ) {
        List<Integer> diasConEvento = eventosServicios.getEventosMes(idCasa, mes, anio);
        return ResponseEntity.ok(new DiasConEventosResponseDTO(diasConEvento));
    }


    @GetMapping(ConstantesServer.GET_EVENTOS_DIA)
    public ResponseEntity<EventosEnDiaResponseDTO> getEventosDia(@RequestParam int idCasa,
                                                                 @RequestParam int dia,
                                                                 @RequestParam int mes,
                                                                 @RequestParam int anio) {
        List<Evento> eventosDia = eventosServicios.getEventosDia(idCasa, dia, mes, anio);
        return ResponseEntity.ok(new EventosEnDiaResponseDTO(eventosDia));
    }

    @PostMapping(ConstantesServer.CREAR_EVENTO)
    public ResponseEntity<Void> crearEvento(@RequestBody CrearEventoRequestDTO evento) {
        eventosServicios.crearEvento(evento.getIdCasa(), evento.getEventoCasa(), evento.getFecha());
        return ResponseEntity.ok().build();
    }
}