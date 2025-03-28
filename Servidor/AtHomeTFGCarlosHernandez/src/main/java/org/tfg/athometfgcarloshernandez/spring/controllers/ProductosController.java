package org.tfg.athometfgcarloshernandez.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tfg.athometfgcarloshernandez.domain.servicios.CasaServicios;
import org.tfg.athometfgcarloshernandez.domain.servicios.ProductosServicios;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesServer;
import org.tfg.athometfgcarloshernandez.spring.model.request.*;
import org.tfg.athometfgcarloshernandez.spring.model.response.CambiarEstadoResponseDTO;
import org.tfg.athometfgcarloshernandez.spring.model.response.CargarProductosResponseDTO;
import org.tfg.athometfgcarloshernandez.spring.model.response.GetCasasResponseDTO;
import org.tfg.athometfgcarloshernandez.spring.model.response.PantallaEstadosResponseDTO;

@RestController
@RequestMapping({ConstantesServer.PRODUCTOSPATH})
public class ProductosController {

    private final ProductosServicios productosServicios;

    @Autowired
    public ProductosController(ProductosServicios productosServicios) {
        this.productosServicios = productosServicios;
    }

    @GetMapping(ConstantesServer.CARGAR_PRODUCTOS)
    public ResponseEntity<CargarProductosResponseDTO> getProductos(@RequestParam(required = false) String idCajon,
                                                                  @RequestParam(required = false) String idMueble) {
        return ResponseEntity.ok( productosServicios.getProductos(idCajon, idMueble));
    }

//    @PostMapping(ConstantesServer.CAMBIAR_ESTADO)
//    public ResponseEntity<CambiarEstadoResponseDTO> cambiarEstado(@RequestBody CambiarEstadoRequestDTO cambiarEstadoRequestDTO) {
//        CambiarEstadoResponseDTO color = new CambiarEstadoResponseDTO(casaServicios.cambiarEstado(cambiarEstadoRequestDTO.getEstado(), cambiarEstadoRequestDTO.getIdUsuario(), cambiarEstadoRequestDTO.getIdCasa()));
//        return ResponseEntity.ok(color);
//    }
//
//    @GetMapping(ConstantesServer.GET_CASAS)
//    public ResponseEntity<GetCasasResponseDTO> getCasas(@RequestParam String idUsuario) {
//        return ResponseEntity.ok(new GetCasasResponseDTO(casaServicios.getCasas(idUsuario)));
//    }
//
//    @PostMapping(ConstantesServer.AGREGAR_CASA)
//    public ResponseEntity<Void> addCasa(@RequestBody AgregarCasaRequestDTO agregarCasaRequestDTO) {
//        casaServicios.agregarCasa(agregarCasaRequestDTO.getIdUsuario(), agregarCasaRequestDTO.getNombre(), agregarCasaRequestDTO.getDireccion(), agregarCasaRequestDTO.getCodigoPostal());
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping(ConstantesServer.UNIRSE_CASA)
//    public ResponseEntity<Void> unirseCasa(@RequestBody UnirseCasaRequestDTO unirseCasaRequestDTO) {
//        casaServicios.unirseCasa(unirseCasaRequestDTO.getIdUsuario(), unirseCasaRequestDTO.getCodigoInvitacion());
//        return ResponseEntity.ok().build();
//    }
//    @PutMapping(ConstantesServer.SALIR_CASA)
//    public ResponseEntity<Void> salirCasa(@RequestBody SalirCasaRequestDTO salirCasaRequestDTO) {
//        casaServicios.salirCasa(salirCasaRequestDTO.getIdUsuario(), salirCasaRequestDTO.getIdCasa());
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping(ConstantesServer.CREAR_ESTADO)
//    public ResponseEntity<Void> crearEstado(@RequestBody CrearEstadoRequestDTO crearEstadoRequestDTO) {
//        casaServicios.crearEstado(crearEstadoRequestDTO.getEstado(),crearEstadoRequestDTO.getColor(),crearEstadoRequestDTO.getIdUsuario());
//        return ResponseEntity.ok().build();
//    }

}
