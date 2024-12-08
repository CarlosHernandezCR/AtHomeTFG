package org.tfg.inhometfgcarloshernandez.domain.model.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.tfg.inhometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.inhometfgcarloshernandez.data.model.CajonEntity;
import org.tfg.inhometfgcarloshernandez.data.model.MuebleEntity;
import org.tfg.inhometfgcarloshernandez.data.model.UsuarioEntity;
import org.tfg.inhometfgcarloshernandez.spring.model.CajonDTO;
import org.tfg.inhometfgcarloshernandez.spring.model.request.AgregarCajonRequestDTO;

@Mapper(componentModel = Constantes.SPRING)
public interface CajonMappers {

    @Mapping(target = "propietario", source = "propietario", qualifiedByName = "usuarioEntityToString")
    CajonDTO cajonEntityToCajonDTO(CajonEntity cajonEntity);

    @Named("usuarioEntityToString")
    default String usuarioEntityToString(UsuarioEntity usuarioEntity) {
        return usuarioEntity != null ? usuarioEntity.getNombre() : null;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "propietario", source = "idPropietario", qualifiedByName = "mapIdToUsuarioEntity")
    @Mapping(target = "muebleEntity", source = "nombreMueble", qualifiedByName = "mapNombreToMuebleEntity")
    CajonEntity toCajonEntity(AgregarCajonRequestDTO agregarCajonRequestDTO);

    @Named("mapIdToUsuarioEntity")
    default UsuarioEntity mapIdToUsuarioEntity(int idPropietario) {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(idPropietario);
        return usuarioEntity;
    }

    @Named("mapNombreToMuebleEntity")
    default MuebleEntity mapNombreToMuebleEntity(String nombreMueble) {
        MuebleEntity muebleEntity = new MuebleEntity();
        muebleEntity.setNombre(nombreMueble);
        return muebleEntity;
    }
}