package org.tfg.inhometfgcarloshernandez.domain.model.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tfg.inhometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.inhometfgcarloshernandez.data.model.MuebleEntity;
import org.tfg.inhometfgcarloshernandez.spring.model.MuebleDTO;

@Mapper(componentModel = Constantes.SPRING)
public interface MuebleMapper {
    MuebleDTO toMuebleDTO(MuebleEntity muebleEntity);

    @Mapping(target = "nombreHabitacion", ignore = true) // Si no necesitas el nombreHabitación desde el DTO.
    MuebleEntity toMuebleEntity(MuebleDTO muebleDTO);
}
