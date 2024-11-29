package org.tfg.inhometfgcarloshernandez.domain.model.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tfg.inhometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.inhometfgcarloshernandez.data.model.EventoEntity;
import org.tfg.inhometfgcarloshernandez.data.model.UsuarioEntity;
import org.tfg.inhometfgcarloshernandez.domain.model.Evento;

@Mapper(componentModel = Constantes.SPRING)
public interface EventoMappers {
    @Mapping(target = "organizador", source = "organizador.nombre")
    Evento toEvento(EventoEntity eventoEntity);

    default String map(UsuarioEntity value) {
        return value != null ? value.getNombre() : null;
    }
}
