package org.tfg.athometfgcarloshernandez.domain.model.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tfg.athometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.athometfgcarloshernandez.common.constantes.MappingConstantes;
import org.tfg.athometfgcarloshernandez.data.model.HabitacionEntity;
import org.tfg.athometfgcarloshernandez.spring.model.HabitacionDTO;

@Mapper(componentModel = Constantes.SPRING)
public interface HabitacionMapper {

    @Mapping(target = MappingConstantes.ID, source = MappingConstantes.ID)
    HabitacionDTO toHabitacionDTO(HabitacionEntity habitacionEntity);
}