package org.tfg.athometfgcarloshernandez.spring.controllers;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.tfg.athometfgcarloshernandez.domain.errores.CustomedException;
import org.tfg.athometfgcarloshernandez.domain.servicios.ProductosServicios;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesError;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesServer;
import org.tfg.athometfgcarloshernandez.spring.model.CajonDTO;
import org.tfg.athometfgcarloshernandez.spring.model.ProductoDTO;
import org.tfg.athometfgcarloshernandez.spring.model.request.*;
import org.tfg.athometfgcarloshernandez.spring.model.response.CargarProductosResponseDTO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesServer.*;
import static org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesSpring.APPLICATION_OCTET_STREAM;

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
    public ResponseEntity<ProductoDTO> agregarProducto(
            @RequestParam(NOMBRE) String nombre,
            @RequestParam(CANTIDAD) int cantidad,
            @RequestParam(ID_CAJON) String idCajon,
            @RequestPart(IMAGEN) MultipartFile imagen) {
        ProductoDTO productoDTO = productosServicios.agregarProducto(nombre, cantidad, imagen, idCajon);
        return ResponseEntity.ok(productoDTO);
    }

    @PostMapping(ConstantesServer.CAMBIAR_CANTIDAD)
    public ResponseEntity<Void> cambiarCantidadProducto(@RequestBody CambiarCantidadProductoRequestDTO agregarProductoRequestDTO) {
        productosServicios.cambiarCantidadProducto(agregarProductoRequestDTO.getIdProducto(),
                agregarProductoRequestDTO.getCantidad());
        return ResponseEntity.ok().build();
    }

    @GetMapping(ConstantesServer.CARGAR_IMAGEN)
    public ResponseEntity<byte[]> cargarImagen(@PathVariable String nombre) {
        try {
            Resource imagen = productosServicios.cargarImagen(nombre);
            String contentType = Files.probeContentType(Paths.get(imagen.getFile().getAbsolutePath()));
            if (contentType == null) {
                contentType = APPLICATION_OCTET_STREAM;
            }
            byte[] imagenBytes = imagen.getInputStream().readAllBytes();
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(imagenBytes);

        } catch (IOException e) {
            throw new CustomedException(ConstantesError.ERROR_MANDAR_IMAGEN);
        }
    }
    @PostMapping(ConstantesServer.AGREGAR_CAJON_CON_MUEBLE)
    public ResponseEntity<CajonDTO> agregarCajon(@RequestBody AgregarCajonConMuebleRequestDTO agregarCajonConMuebleRequestDTO) {
        return ResponseEntity.ok(
                productosServicios.agregarCajonConMueble(
                        agregarCajonConMuebleRequestDTO.getIdMueble(),
                        agregarCajonConMuebleRequestDTO.getNombre(),
                        agregarCajonConMuebleRequestDTO.getIdPropietario()
                ));
    }


}
