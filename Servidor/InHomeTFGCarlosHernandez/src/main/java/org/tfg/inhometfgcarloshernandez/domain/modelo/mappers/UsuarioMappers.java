package org.tfg.inhometfgcarloshernandez.domain.modelo.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tfg.inhometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.inhometfgcarloshernandez.data.model.UsuarioEntity;
import org.tfg.inhometfgcarloshernandez.domain.modelo.Usuario;
import org.tfg.inhometfgcarloshernandez.spring.model.response.LoginResponseDTO;

@Mapper(componentModel = Constantes.SPRING)
public interface UsuarioMappers {
    @Mapping(target = "id", ignore = true)
    UsuarioEntity toUsuarioEntity(Usuario usuario);

    @Mapping(target = "id", ignore = true)
    Usuario toUsuario(UsuarioEntity usuarioEntity);


    @Mapping(target = "estado", source = "estado")
    LoginResponseDTO toLoginResponseDTO(Usuario usuario);
}
