package org.tfg.inhometfgcarloshernandez.domain.model.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tfg.inhometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.inhometfgcarloshernandez.data.model.CasaEntity;
import org.tfg.inhometfgcarloshernandez.spring.model.CasaDetallesDTO;

@Mapper(componentModel = Constantes.SPRING)
public interface CasaMapper {
    @Mapping(target = "id")
    CasaDetallesDTO casaEntityToCasaDetallesDTO(CasaEntity casaEntity);
}
