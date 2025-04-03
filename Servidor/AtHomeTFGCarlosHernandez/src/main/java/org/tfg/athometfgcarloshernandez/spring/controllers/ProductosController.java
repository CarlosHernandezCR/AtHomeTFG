package org.tfg.athometfgcarloshernandez.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tfg.athometfgcarloshernandez.domain.servicios.ProductosServicios;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesServer;
import org.tfg.athometfgcarloshernandez.spring.model.ProductoDTO;
import org.tfg.athometfgcarloshernandez.spring.model.request.*;
import org.tfg.athometfgcarloshernandez.spring.model.response.CargarProductosResponseDTO;

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

    @PostMapping(ConstantesServer.AGREGAR_PRODUTO)
    public ResponseEntity<ProductoDTO> agregarProducto(@RequestBody AgregarProductoRequestDTO agregarProductoRequestDTO) {
        return ResponseEntity.ok( productosServicios.agregarProducto(agregarProductoRequestDTO.getNombre(),
                agregarProductoRequestDTO.getCantidad(), agregarProductoRequestDTO.getIdCajon()));
    }
    @PostMapping(ConstantesServer.CAMBIAR_CANTIDAD)
    public ResponseEntity<Void> cambiarCantidadProducto(@RequestBody CambiarCantidadProductoRequestDTO agregarProductoRequestDTO) {
        productosServicios.cambiarCantidadProducto(agregarProductoRequestDTO.getIdProducto(),
                agregarProductoRequestDTO.getCantidad());
        return ResponseEntity.ok().build();
    }

}
