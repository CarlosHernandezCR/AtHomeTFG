package org.tfg.inhometfgcarloshernandez.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tfg.inhometfgcarloshernandez.domain.servicios.InmuebleServicios;
import org.tfg.inhometfgcarloshernandez.spring.common.constantes.ConstantesServer;
import org.tfg.inhometfgcarloshernandez.spring.model.response.PantallaInmueblesResponseDTO;

@RestController
@RequestMapping({ConstantesServer.INMUEBLESPATH})
public class InmueblesController {
    private final InmuebleServicios inmuebleServicios;

    @Autowired
    public InmueblesController(InmuebleServicios inmuebleServicios){this.inmuebleServicios =inmuebleServicios;}


    @GetMapping(ConstantesServer.PANTALLA_INMUEBLES)
    public ResponseEntity<PantallaInmueblesResponseDTO> getInmuebles(
            @RequestParam int idCasa,
            @RequestParam(required = false) String nombreHabitacion,
            @RequestParam(required = false) Integer idMueble) {

        PantallaInmueblesResponseDTO response = inmuebleServicios.getInmuebles(idCasa, nombreHabitacion, idMueble);
        return ResponseEntity.ok(response);
    }

}
