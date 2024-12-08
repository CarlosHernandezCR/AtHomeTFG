package org.tfg.inhometfgcarloshernandez.domain.model.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tfg.inhometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.inhometfgcarloshernandez.data.model.MuebleEntity;
import org.tfg.inhometfgcarloshernandez.spring.model.MuebleDTO;
import org.tfg.inhometfgcarloshernandez.spring.model.request.AgregarMuebleRequestDTO;

@Mapper(componentModel = Constantes.SPRING)
public interface MuebleMapper {
    MuebleDTO toMuebleDTO(MuebleEntity muebleEntity);


    @Mapping(target = "id", ignore = true)
    MuebleEntity toMuebleEntity(AgregarMuebleRequestDTO agregarMuebleRequestDTO);
}