package org.tfg.athometfgcarloshernandez.domain.model.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tfg.athometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.athometfgcarloshernandez.common.constantes.MappingConstantes;
import org.tfg.athometfgcarloshernandez.data.model.CasaEntity;
import org.tfg.athometfgcarloshernandez.spring.model.CasaDetallesDTO;

@Mapper(componentModel = Constantes.SPRING)
public interface CasaMapper {
    @Mapping(target = MappingConstantes.ID)
    CasaDetallesDTO casaEntityToCasaDetallesDTO(CasaEntity casaEntity);
}
