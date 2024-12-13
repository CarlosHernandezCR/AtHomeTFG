package org.tfg.athometfgcarloshernandez.domain.model.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tfg.athometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.athometfgcarloshernandez.data.model.EventoEntity;
import org.tfg.athometfgcarloshernandez.data.model.UsuarioEntity;
import org.tfg.athometfgcarloshernandez.domain.model.Evento;
import org.tfg.athometfgcarloshernandez.spring.model.EventoDTO;

@Mapper(componentModel = Constantes.SPRING)
public interface EventoMappers {
    @Mapping(target = "organizador", source = "organizador.nombre")
    Evento toEvento(EventoEntity eventoEntity);

    @Mapping(target = "nombre", source = "nombre")
    EventoEntity toEventoEntity(Evento evento);

    @Mapping(target = "organizador", source = "organizador")
    Evento toEvento(EventoDTO eventoDTO);


    default String map(UsuarioEntity value) {
        return value != null ? value.getNombre() : null;
    }

    default UsuarioEntity stringToUsuarioEntity(String value) {
        if (value == null) {
            return null;
        }
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setNombre(value);
        return usuarioEntity;
    }
}
