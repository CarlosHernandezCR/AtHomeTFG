package org.tfg.athometfgcarloshernandez.domain.model.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.tfg.athometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.athometfgcarloshernandez.common.constantes.MappingConstantes;
import org.tfg.athometfgcarloshernandez.data.model.CajonEntity;
import org.tfg.athometfgcarloshernandez.data.model.UsuarioEntity;
import org.tfg.athometfgcarloshernandez.spring.model.CajonDTO;

@Mapper(componentModel = Constantes.SPRING)
public interface CajonMappers {

    @Mapping(target = MappingConstantes.PROPIETARIO, source = MappingConstantes.PROPIETARIO, qualifiedByName = MappingConstantes.USUARIO_ENTITY_TO_STRING)
    @Mapping(target = MappingConstantes.ID_PROPIETARIO, source = MappingConstantes.ID_PROPIETARIO_ATRIBUTO)
    CajonDTO cajonEntityToCajonDTO(CajonEntity cajonEntity);

    @Named(MappingConstantes.USUARIO_ENTITY_TO_STRING)
    default String usuarioEntityToString(UsuarioEntity usuarioEntity) {
        return usuarioEntity != null ? usuarioEntity.getNombre() : null;
    }
}