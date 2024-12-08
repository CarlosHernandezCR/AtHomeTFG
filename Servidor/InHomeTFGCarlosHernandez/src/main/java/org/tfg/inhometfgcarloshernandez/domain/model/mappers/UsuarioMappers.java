package org.tfg.inhometfgcarloshernandez.domain.model.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tfg.inhometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.inhometfgcarloshernandez.data.model.UsuarioEntity;
import org.tfg.inhometfgcarloshernandez.domain.model.Usuario;
import org.tfg.inhometfgcarloshernandez.spring.model.UsuarioDTO;
import org.tfg.inhometfgcarloshernandez.spring.model.response.LoginResponseDTO;

@Mapper(componentModel = Constantes.SPRING)
public interface UsuarioMappers {

    @Mapping(target = "id")
    Usuario toUsuario(UsuarioEntity usuarioEntity);

    @Mapping(target = "id")
    UsuarioDTO toUsuarioDTO(UsuarioEntity usuarioEntity);
}
