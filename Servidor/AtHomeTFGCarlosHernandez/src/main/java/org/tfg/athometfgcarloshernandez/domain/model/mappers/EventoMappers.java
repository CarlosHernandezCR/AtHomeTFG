package org.tfg.athometfgcarloshernandez.domain.model.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tfg.athometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.athometfgcarloshernandez.common.constantes.MappingConstantes;
import org.tfg.athometfgcarloshernandez.data.model.EventoEntity;
import org.tfg.athometfgcarloshernandez.data.model.UsuarioEntity;
import org.tfg.athometfgcarloshernandez.domain.model.Evento;

@Mapper(componentModel = Constantes.SPRING)
public interface EventoMappers {
    @Mapping(target = MappingConstantes.ORGANIZADOR, source = MappingConstantes.ORGANIZADOR_ATRIBUTO)
    Evento toEvento(EventoEntity eventoEntity);

    default String map(UsuarioEntity value) {
        return value != null ? value.getNombre() : null;
    }

}
