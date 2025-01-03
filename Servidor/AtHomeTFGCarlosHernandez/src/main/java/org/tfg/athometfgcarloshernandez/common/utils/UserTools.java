package org.tfg.athometfgcarloshernandez.common.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.tfg.athometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.athometfgcarloshernandez.data.model.UsuarioEntity;
import org.tfg.athometfgcarloshernandez.data.repositories.UsuarioRepository;
import org.tfg.athometfgcarloshernandez.domain.errores.NotFoundException;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesError;

@Component
@RequiredArgsConstructor
public class UserTools {
    private final UsuarioRepository usuarioRepository;

    public UsuarioEntity getUsuarioEntityByIdentificador(String identificador) {
        if (identificador.contains(Constantes.ARROBA)) {
            return usuarioRepository.findByCorreo(identificador)
                    .orElseThrow(() -> new NotFoundException(ConstantesError.CORREO_NO_ENCONTRADO + identificador));
        } else if (identificador.matches("\\d+")) {
            return usuarioRepository.findByTelefono(identificador)
                    .orElseThrow(() -> new NotFoundException(ConstantesError.TELEFONO_NO_ENCONTRADO + identificador));
        } else {
            return usuarioRepository.findByNombre(identificador)
                    .orElseThrow(() -> new NotFoundException(ConstantesError.NOMBRE_NO_ENCONTRADO + identificador));
        }
    }
}