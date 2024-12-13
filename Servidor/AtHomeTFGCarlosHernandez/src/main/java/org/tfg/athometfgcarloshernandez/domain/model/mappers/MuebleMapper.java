package org.tfg.athometfgcarloshernandez.domain.model.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tfg.athometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.athometfgcarloshernandez.data.model.MuebleEntity;
import org.tfg.athometfgcarloshernandez.spring.model.MuebleDTO;
import org.tfg.athometfgcarloshernandez.spring.model.request.AgregarMuebleRequestDTO;

@Mapper(componentModel = Constantes.SPRING)
public interface MuebleMapper {
    MuebleDTO toMuebleDTO(MuebleEntity muebleEntity);


    @Mapping(target = "id", ignore = true)
    MuebleEntity toMuebleEntity(AgregarMuebleRequestDTO agregarMuebleRequestDTO);
}