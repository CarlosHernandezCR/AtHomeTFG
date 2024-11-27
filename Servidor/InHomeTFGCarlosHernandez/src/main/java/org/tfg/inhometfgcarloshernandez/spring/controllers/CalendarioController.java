package org.tfg.inhometfgcarloshernandez.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tfg.inhometfgcarloshernandez.domain.servicios.EventosServicios;
import org.tfg.inhometfgcarloshernandez.spring.common.constantes.ConstantesServer;
import org.tfg.inhometfgcarloshernandez.spring.model.request.DiasEventosRequestDTO;
import org.tfg.inhometfgcarloshernandez.spring.model.response.DiasEventosResponseDTO;

import static org.tfg.inhometfgcarloshernandez.spring.common.constantes.ConstantesServer.GET_EVENTOS_MES;


@RestController
@RequestMapping({ConstantesServer.CALENDARIOPATH})
public class CalendarioController {
    private final EventosServicios eventosServicios;

    @Autowired
    public CalendarioController(EventosServicios eventosServicios) {
        this.eventosServicios = eventosServicios;
    }

    @PostMapping(GET_EVENTOS_MES)
    public ResponseEntity<DiasEventosResponseDTO> getEventosMes(@RequestBody DiasEventosRequestDTO getEventosMesRequestDTO) {
        return ResponseEntity.ok(eventosServicios.getEventosMes(getEventosMesRequestDTO.getIdCasa(), getEventosMesRequestDTO.getMes(), getEventosMesRequestDTO.getAnio()));
    }
}
