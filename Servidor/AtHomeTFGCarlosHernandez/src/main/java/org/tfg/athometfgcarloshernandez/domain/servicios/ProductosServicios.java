package org.tfg.athometfgcarloshernandez.domain.servicios;

import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.tfg.athometfgcarloshernandez.data.dao.GuardadoDeImagenDao;
import org.tfg.athometfgcarloshernandez.data.model.*;
import org.tfg.athometfgcarloshernandez.data.repositories.*;
import org.tfg.athometfgcarloshernandez.domain.errores.NotFoundException;
import org.tfg.athometfgcarloshernandez.domain.model.mappers.CajonMappers;
import org.tfg.athometfgcarloshernandez.domain.model.mappers.MuebleMapper;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesError;
import org.tfg.athometfgcarloshernandez.spring.model.CajonDTO;
import org.tfg.athometfgcarloshernandez.spring.model.MuebleDTO;
import org.tfg.athometfgcarloshernandez.spring.model.ProductoDTO;
import org.tfg.athometfgcarloshernandez.spring.model.response.CargarProductosResponseDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductosServicios {
    private final MueblesRepository mueblesRepository;
    private final CajonesRepository cajonesRepository;
    private final AlmacenaRepository almacenaRepository;
    private final CestaRepository cestaRepository;
    private final MuebleMapper muebleMapper;
    private final CajonMappers cajonMappers;
    private final GuardadoDeImagenDao guardadoDeImagenDao;
    private final UsuarioRepository usuarioRepository;
    private final PedidosRepository pedidosRepository;

    @Transactional
    public CargarProductosResponseDTO getProductos(String idCajon, String idMueble) {
        if (idMueble == null) {
            CajonEntity cajon = cajonesRepository.findById(Integer.parseInt(idCajon))
                    .orElseThrow(() -> new NotFoundException(ConstantesError.CAJON_NO_ENCONTRADO));

            MuebleEntity mueble = cajon.getMuebleEntity();
            List<MuebleEntity> muebles = mueblesRepository.findByIdHabitacionEntity(mueble.getIdHabitacionEntity());
            List<CajonEntity> cajones = cajonesRepository.findByMuebleEntity(mueble);
            List<AlmacenaEntity> almacenado = almacenaRepository.findByIdCajon(cajon);

            return getCargarProductosResponseDTO(muebles, cajones, almacenado, cajon.getPropietario().getId());
        } else if (idCajon == null) {
            MuebleEntity mueble = mueblesRepository.findById(Integer.parseInt(idMueble))
                    .orElseThrow(() -> new NotFoundException(ConstantesError.MUEBLE_NO_ENCONTRADO));
            List<MuebleEntity> muebles = new ArrayList<>();
            muebles.add(mueble);
            List<CajonEntity> cajones = cajonesRepository.findByMuebleEntity(mueble);
            List<AlmacenaEntity> almacenado = new ArrayList<>();
            if (!cajones.isEmpty()) {
                almacenado = almacenaRepository.findByIdCajon(cajones.get(0));
            }
            return getCargarProductosResponseDTO(muebles, cajones, almacenado,0);
        } else {
            CajonEntity cajon = cajonesRepository.findById(Integer.parseInt(idCajon))
                    .orElseThrow(() -> new NotFoundException(ConstantesError.CAJON_NO_ENCONTRADO));
            List<AlmacenaEntity> almacenado = almacenaRepository.findByIdCajon(cajon);
            return getCargarProductosResponseDTO(new ArrayList<>(), new ArrayList<>(), almacenado, cajon.getPropietario().getId());
        }
    }

    private CargarProductosResponseDTO getCargarProductosResponseDTO(List<MuebleEntity> muebles, List<CajonEntity> cajones, List<AlmacenaEntity> almacenado, int idPropietario) {
        List<ProductoDTO> productosDTO = almacenado.stream()
                .map(almacenaEntity -> new ProductoDTO(
                        almacenaEntity.getId().toString(),
                        almacenaEntity.getNombre(),
                        almacenaEntity.getCantidad(),
                        almacenaEntity.getImagen()))
                .toList();

        List<CajonDTO> cajonesDTO = cajones.stream()
                .map(cajonMappers::cajonEntityToCajonDTO)
                .toList();

        List<MuebleDTO> mueblesDTO = muebles.stream()
                .map(muebleMapper::toMuebleDTO)
                .toList();
        return new CargarProductosResponseDTO(productosDTO, cajonesDTO, mueblesDTO, idPropietario);
    }

    public ProductoDTO agregarProducto(String nombre, Integer cantidad, MultipartFile imagen, String idCajon) {
        CajonEntity cajonEntity = cajonesRepository.findById(Integer.parseInt(idCajon))
                .orElseThrow(() -> new NotFoundException(ConstantesError.CAJON_NO_ENCONTRADO));
        String rutaImagen = guardadoDeImagenDao.guardarImagen(imagen);
        AlmacenaEntity almacenaEntity = new AlmacenaEntity(0, cajonEntity, nombre, cantidad, rutaImagen);
        almacenaEntity = almacenaRepository.save(almacenaEntity);
        return new ProductoDTO(
                almacenaEntity.getId().toString(),
                almacenaEntity.getNombre(),
                almacenaEntity.getCantidad(),
                almacenaEntity.getImagen()
        );
    }

    @Transactional
    public void cambiarCantidadProducto(String idProducto, Integer cantidad) {
        AlmacenaEntity almacenaEntity = almacenaRepository.findByIdWithCajonAndPropietario(Integer.parseInt(idProducto))
                .orElseThrow(() -> new NotFoundException(ConstantesError.PRODUCTO_NO_ENCONTRADO));
        Hibernate.initialize(almacenaEntity.getIdCajon().getPropietario());

        CestaEntity cestaEntity = cestaRepository.getByIdUsuarioAndNombre(almacenaEntity.getIdCajon().getPropietario(), almacenaEntity.getNombre());

        if (cantidad == 0) {
            if (cestaEntity == null) {
                cestaEntity = new CestaEntity(0, almacenaEntity.getNombre(), almacenaEntity.getCantidad(), almacenaEntity.getIdCajon().getPropietario());
            } else {
                cestaEntity.setCantidad(cestaEntity.getCantidad() + almacenaEntity.getCantidad());
            }
            cestaRepository.save(cestaEntity);
            almacenaRepository.delete(almacenaEntity);
            return;
        }

        if (cestaEntity == null && cantidad < almacenaEntity.getCantidad()) {
            cestaEntity = new CestaEntity(0, almacenaEntity.getNombre(), almacenaEntity.getCantidad() - cantidad, almacenaEntity.getIdCajon().getPropietario());
            cestaRepository.save(cestaEntity);
        } else if (cestaEntity != null) {
            cestaEntity.setCantidad(cestaEntity.getCantidad() + (almacenaEntity.getCantidad() - cantidad));
            cestaRepository.save(cestaEntity);
        }

        almacenaEntity.setCantidad(cantidad);
        almacenaRepository.save(almacenaEntity);
    }

    public Resource cargarImagen(String nombre) {
        return guardadoDeImagenDao.cargarImagen(nombre);
    }

    public CajonDTO agregarCajonConMueble(Integer idMueble, String nombre, Integer idPropietario) {
        MuebleEntity muebleEntity = mueblesRepository.findById(idMueble)
                .orElseThrow(() -> new NotFoundException(ConstantesError.MUEBLE_NO_ENCONTRADO));
        UsuarioEntity usuarioEntity = usuarioRepository.findById(idPropietario)
                .orElseThrow(() -> new NotFoundException(ConstantesError.USUARIO_NO_ENCONTRADO));
        CajonEntity cajonEntity = new CajonEntity(0, nombre, usuarioEntity, muebleEntity);
        cajonEntity = cajonesRepository.save(cajonEntity);
        return cajonMappers.cajonEntityToCajonDTO(cajonEntity);
    }

    public String pedirPrestado(int idProducto, int idUsuario) {
        AlmacenaEntity almacenaEntity = almacenaRepository.findById(idProducto)
                .orElseThrow(() -> new NotFoundException(ConstantesError.PRODUCTO_NO_ENCONTRADO));
        UsuarioEntity usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new NotFoundException(ConstantesError.USUARIO_NO_ENCONTRADO));
        UsuarioEntity propietario = almacenaEntity.getIdCajon().getPropietario();
        pedidosRepository.save(new PedidoEntity(null, almacenaEntity, propietario, usuario, null));
        return almacenaEntity.getNombre();
    }
}
