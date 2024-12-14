package org.tfg.athometfgcarloshernandez.domain.model.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tfg.athometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.athometfgcarloshernandez.common.constantes.MappingConstantes;
import org.tfg.athometfgcarloshernandez.data.model.UsuarioEntity;
import org.tfg.athometfgcarloshernandez.spring.model.UsuarioDTO;

@Mapper(componentModel = Constantes.SPRING)
public interface UsuarioMappers {

    @Mapping(target = MappingConstantes.ID)
    UsuarioDTO toUsuarioDTO(UsuarioEntity usuarioEntity);

}
