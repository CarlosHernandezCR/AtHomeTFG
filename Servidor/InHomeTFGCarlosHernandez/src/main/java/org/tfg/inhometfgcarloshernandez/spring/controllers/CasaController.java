package org.tfg.inhometfgcarloshernandez.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tfg.inhometfgcarloshernandez.domain.servicios.CasaServicios;
import org.tfg.inhometfgcarloshernandez.spring.common.constantes.ConstantesServer;
import org.tfg.inhometfgcarloshernandez.spring.model.response.PantallaEstadosResponseDTO;

@RestController
@RequestMapping({ConstantesServer.CASAPATH})
public class CasaController {
    private final CasaServicios casaServicios;

    @Autowired
    public CasaController(CasaServicios casaServicios) {
        this.casaServicios = casaServicios;
    }

    @GetMapping(ConstantesServer.CASAPRIMERAPANTALLA)
    public ResponseEntity<PantallaEstadosResponseDTO> getPrimerPantalla(@RequestBody Integer id) {
        return ResponseEntity.ok(casaServicios.getDatosPrimeraPantalla(id));
    }
}
