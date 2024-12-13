package org.tfg.athometfgcarloshernandez.domain.model.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.tfg.athometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.athometfgcarloshernandez.data.model.CasaEntity;
import org.tfg.athometfgcarloshernandez.data.model.HabitacionEntity;
import org.tfg.athometfgcarloshernandez.spring.model.HabitacionDTO;
import org.tfg.athometfgcarloshernandez.spring.model.request.AgregarHabitacionRequestDTO;

@Mapper(componentModel = Constantes.SPRING)
public interface HabitacionMapper {
    @Mapping(target = "idCasaEntity", source = "idCasa", qualifiedByName = "mapIdCasaToEntity")
    HabitacionEntity toHabitacionEntity(AgregarHabitacionRequestDTO agregarHabitacionRequestDTO);

    @Named("mapIdCasaToEntity")
    default CasaEntity mapIdCasaToEntity(int idCasa) {
        CasaEntity casaEntity = new CasaEntity();
        casaEntity.setId(idCasa);
        return casaEntity;
    }

    @Mapping(target = "id", source = "id")
    HabitacionDTO toHabitacionDTO(HabitacionEntity habitacionEntity);
}