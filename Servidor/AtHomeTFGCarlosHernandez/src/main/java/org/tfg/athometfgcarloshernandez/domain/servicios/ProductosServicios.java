package org.tfg.athometfgcarloshernandez.domain.servicios;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tfg.athometfgcarloshernandez.data.model.AlmacenaEntity;
import org.tfg.athometfgcarloshernandez.data.model.CajonEntity;
import org.tfg.athometfgcarloshernandez.data.model.MuebleEntity;
import org.tfg.athometfgcarloshernandez.data.repositories.AlmacenaRepository;
import org.tfg.athometfgcarloshernandez.data.repositories.CajonesRepository;
import org.tfg.athometfgcarloshernandez.data.repositories.MueblesRepository;
import org.tfg.athometfgcarloshernandez.data.repositories.ProductosRepository;
import org.tfg.athometfgcarloshernandez.domain.errores.NotFoundException;
import org.tfg.athometfgcarloshernandez.domain.model.mappers.CajonMappers;
import org.tfg.athometfgcarloshernandez.domain.model.mappers.MuebleMapper;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesError;
import org.tfg.athometfgcarloshernandez.spring.model.CajonDTO;
import org.tfg.athometfgcarloshernandez.spring.model.MuebleDTO;
import org.tfg.athometfgcarloshernandez.spring.model.ProductoDTO;
import org.tfg.athometfgcarloshernandez.spring.model.response.CargarProductosResponseDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductosServicios {
    private final MueblesRepository mueblesRepository;
    private final CajonesRepository cajonesRepository;
    private final AlmacenaRepository almacenaRepository;
    private final MuebleMapper muebleMapper;
    private final CajonMappers cajonMappers;

    public CargarProductosResponseDTO getProductos(String idCajon) {
        CajonEntity cajon = cajonesRepository.findById(Integer.parseInt(idCajon))
                .orElseThrow(() -> new NotFoundException(ConstantesError.CAJON_NO_ENCONTRADO));

        MuebleEntity mueble = cajon.getMuebleEntity();
        List<MuebleEntity> muebles = mueblesRepository.findByIdHabitacionEntity(mueble.getIdHabitacionEntity());
        List<CajonEntity> cajones = cajonesRepository.findByMuebleEntity(mueble);
        List<AlmacenaEntity> almacenado = almacenaRepository.findByIdCajon(cajon);

        List<ProductoDTO> productosDTO = almacenado.stream()
                .map(almacenaEntity -> new ProductoDTO(
                        almacenaEntity.getIdProductos().getId().toString(),
                        almacenaEntity.getIdProductos().getNombre(),
                        almacenaEntity.getCantidad()))
                .toList();

        List<CajonDTO> cajonesDTO = cajones.stream()
                .map(cajonMappers::cajonEntityToCajonDTO)
                .toList();

        List<MuebleDTO> mueblesDTO = muebles.stream()
                .map(muebleMapper::toMuebleDTO)
                .toList();

        return new CargarProductosResponseDTO(productosDTO, cajonesDTO, mueblesDTO, mueble.getId().toString(), cajon.getId().toString());
    }

}
