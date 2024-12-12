package org.tfg.inhometfgcarloshernandez.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tfg.inhometfgcarloshernandez.domain.servicios.CasaServicios;
import org.tfg.inhometfgcarloshernandez.spring.common.constantes.ConstantesServer;
import org.tfg.inhometfgcarloshernandez.spring.model.request.CambiarEstadoRequestDTO;
import org.tfg.inhometfgcarloshernandez.spring.model.response.GetCasasResponseDTO;
import org.tfg.inhometfgcarloshernandez.spring.model.response.PantallaEstadosResponseDTO;



@RestController
@RequestMapping({ConstantesServer.CASAPATH})
public class CasaController {
    private final CasaServicios casaServicios;

    @Autowired
    public CasaController(CasaServicios casaServicios) {
        this.casaServicios = casaServicios;
    }

    @GetMapping(ConstantesServer.PANTALLA_CASA)
    public ResponseEntity<PantallaEstadosResponseDTO> getPantallaEstados(@RequestParam String idUsuario,@RequestParam String idCasa, @RequestParam String token) {
        return ResponseEntity.ok( casaServicios.getDatosPantallaEstados(idUsuario, idCasa,token));
    }

    @PostMapping(ConstantesServer.CAMBIAR_ESTADO)
    public ResponseEntity<Void> cambiarEstado(@RequestBody CambiarEstadoRequestDTO cambiarEstadoRequestDTO) {
        return casaServicios.cambiarEstado(cambiarEstadoRequestDTO.getEstado(), cambiarEstadoRequestDTO.getIdUsuario(), cambiarEstadoRequestDTO.getIdCasa());
    }

    @GetMapping(ConstantesServer.GET_CASAS)
    public ResponseEntity<GetCasasResponseDTO> getCasas(@RequestParam String idUsuario) {
        return ResponseEntity.ok(new GetCasasResponseDTO(casaServicios.getCasas(idUsuario)));
    }
}
