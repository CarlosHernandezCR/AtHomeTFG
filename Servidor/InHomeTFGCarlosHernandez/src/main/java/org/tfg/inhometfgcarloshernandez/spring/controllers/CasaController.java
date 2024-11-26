package org.tfg.inhometfgcarloshernandez.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tfg.inhometfgcarloshernandez.domain.servicios.CasaServicios;
import org.tfg.inhometfgcarloshernandez.spring.common.constantes.ConstantesServer;
import org.tfg.inhometfgcarloshernandez.spring.model.request.CambiarEstadoRequestDTO;
import org.tfg.inhometfgcarloshernandez.spring.model.response.PantallaEstadosResponseDTO;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;


@RestController
@RequestMapping({ConstantesServer.CASAPATH})
public class CasaController {
    private final CasaServicios casaServicios;

    @Autowired
    public CasaController(CasaServicios casaServicios) {
        this.casaServicios = casaServicios;
    }

    @GetMapping(ConstantesServer.CASA_PRIMERA_PANTALLA + ConstantesServer.IDVARIABLE)
    public ResponseEntity<PantallaEstadosResponseDTO> getPrimerPantalla(@PathVariable int id) {
        return ResponseEntity.ok(casaServicios.getDatosPrimeraPantalla(id));
    }

    @PostMapping(ConstantesServer.CAMBIAR_ESTADO)
    public ResponseEntity<Void> cambiarEstado(@RequestBody CambiarEstadoRequestDTO cambiarEstadoRequestDTO) {
        return casaServicios.cambiarEstado(cambiarEstadoRequestDTO.getEstado(), cambiarEstadoRequestDTO.getId());
    }
}
