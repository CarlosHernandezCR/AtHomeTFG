package org.tfg.inhometfgcarloshernandez.domain.model.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.tfg.inhometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.inhometfgcarloshernandez.data.model.CasaEntity;
import org.tfg.inhometfgcarloshernandez.data.model.HabitacionEntity;
import org.tfg.inhometfgcarloshernandez.spring.model.HabitacionDTO;
import org.tfg.inhometfgcarloshernandez.spring.model.request.AgregarHabitacionRequestDTO;

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