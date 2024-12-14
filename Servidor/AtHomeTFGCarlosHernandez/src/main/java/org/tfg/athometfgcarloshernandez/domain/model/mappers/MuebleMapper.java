package org.tfg.athometfgcarloshernandez.domain.model.mappers;

import org.mapstruct.Mapper;
import org.tfg.athometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.athometfgcarloshernandez.data.model.MuebleEntity;
import org.tfg.athometfgcarloshernandez.spring.model.MuebleDTO;

@Mapper(componentModel = Constantes.SPRING)
public interface MuebleMapper {
    MuebleDTO toMuebleDTO(MuebleEntity muebleEntity);
}