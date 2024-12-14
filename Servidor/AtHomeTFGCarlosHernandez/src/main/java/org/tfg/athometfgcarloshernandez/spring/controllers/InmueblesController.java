package org.tfg.athometfgcarloshernandez.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tfg.athometfgcarloshernandez.domain.servicios.InmuebleServicios;
import org.tfg.athometfgcarloshernandez.domain.servicios.UsuarioServicios;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesServer;
import org.tfg.athometfgcarloshernandez.spring.model.request.AgregarCajonRequestDTO;
import org.tfg.athometfgcarloshernandez.spring.model.request.AgregarHabitacionRequestDTO;
import org.tfg.athometfgcarloshernandez.spring.model.request.AgregarMuebleRequestDTO;
import org.tfg.athometfgcarloshernandez.spring.model.response.GetUsuariosResponseDTO;
import org.tfg.athometfgcarloshernandez.spring.model.response.PantallaInmueblesResponseDTO;

@RestController
@RequestMapping({ConstantesServer.INMUEBLESPATH})
public class InmueblesController {
    private final InmuebleServicios inmuebleServicios;
    private final UsuarioServicios usuarioServicios;

    @Autowired
    public InmueblesController(InmuebleServicios inmuebleServicios, UsuarioServicios usuarioServicios){
        this.inmuebleServicios =inmuebleServicios;
        this.usuarioServicios = usuarioServicios;
    }

    @GetMapping(ConstantesServer.PANTALLA_INMUEBLES)
    public ResponseEntity<PantallaInmueblesResponseDTO> getInmuebles(
            @RequestParam int idCasa,
            @RequestParam(required = false) String habitacion,
            @RequestParam(required = false) String mueble) {

        PantallaInmueblesResponseDTO response = inmuebleServicios.getInmuebles(idCasa, habitacion, mueble);
        return ResponseEntity.ok(response);
    }

    @GetMapping(ConstantesServer.GET_USUARIOS)
    public ResponseEntity<GetUsuariosResponseDTO> getUsuarios(@RequestParam int idCasa) {
        GetUsuariosResponseDTO response = new GetUsuariosResponseDTO(usuarioServicios.getUsuarios(idCasa));
        return ResponseEntity.ok(response);
    }


    @PostMapping(ConstantesServer.AGREGAR_HABITACION)
    public ResponseEntity<Void> agregarHabitacion(@RequestBody AgregarHabitacionRequestDTO agregarHabitacionRequestDTO) {
        inmuebleServicios.agregarHabitacion(agregarHabitacionRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping(ConstantesServer.AGREGAR_MUEBLE)
    public ResponseEntity<Void> agregarMueble(@RequestBody AgregarMuebleRequestDTO agregarMuebleRequestDTO) {
        inmuebleServicios.agregarMueble(agregarMuebleRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping(ConstantesServer.AGREGAR_CAJON)
    public ResponseEntity<Void> agregarCajon(@RequestBody AgregarCajonRequestDTO agregarCajonRequestDTO) {
        inmuebleServicios.agregarCajon(agregarCajonRequestDTO);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping(ConstantesServer.BORRAR_CAJON)
    public ResponseEntity<Void> borrarCajon(@RequestParam int idCajon) {
        inmuebleServicios.borrarCajon(idCajon);
        return ResponseEntity.ok().build();
    }
}
