package org.tfg.athometfgcarloshernandez.domain.servicios;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tfg.athometfgcarloshernandez.data.model.AlmacenaEntity;
import org.tfg.athometfgcarloshernandez.data.model.CajonEntity;
import org.tfg.athometfgcarloshernandez.data.model.CestaEntity;
import org.tfg.athometfgcarloshernandez.data.model.MuebleEntity;
import org.tfg.athometfgcarloshernandez.data.repositories.AlmacenaRepository;
import org.tfg.athometfgcarloshernandez.data.repositories.CajonesRepository;
import org.tfg.athometfgcarloshernandez.data.repositories.CestaRepository;
import org.tfg.athometfgcarloshernandez.data.repositories.MueblesRepository;
import org.tfg.athometfgcarloshernandez.domain.errores.NotFoundException;
import org.tfg.athometfgcarloshernandez.domain.model.mappers.CajonMappers;
import org.tfg.athometfgcarloshernandez.domain.model.mappers.MuebleMapper;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesError;
import org.tfg.athometfgcarloshernandez.spring.model.CajonDTO;
import org.tfg.athometfgcarloshernandez.spring.model.MuebleDTO;
import org.tfg.athometfgcarloshernandez.spring.model.ProductoDTO;
import org.tfg.athometfgcarloshernandez.spring.model.response.CargarProductosResponseDTO;

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
            List<CajonEntity> cajones = cajonesRepository.findByMuebleEntity(mueble);
            List<AlmacenaEntity> almacenado = new ArrayList<>();
            if (!cajones.isEmpty()) {
                almacenado = almacenaRepository.findByIdCajon(cajones.get(0));
            }
            return getCargarProductosResponseDTO(new ArrayList<>(), cajones, almacenado, cajones.get(0).getPropietario().getId());
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

    public ProductoDTO agregarProducto(String nombre, Integer cantidad, String imagen, String idCajon) {
        CajonEntity cajonEntity = cajonesRepository.findById(Integer.parseInt(idCajon))
                .orElseThrow(() -> new NotFoundException(ConstantesError.CAJON_NO_ENCONTRADO));
        AlmacenaEntity almacenaEntity = new AlmacenaEntity(0, cajonEntity, nombre, cantidad, imagen);
        return almacenaRepository.save(almacenaEntity).getId() != null ?
                new ProductoDTO(almacenaEntity.getId().toString(), almacenaEntity.getNombre(), almacenaEntity.getCantidad(), almacenaEntity.getImagen()) :
                null;
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
}
