//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.tfg.inhometfgcarloshernandez.domain.servicios;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tfg.inhometfgcarloshernandez.data.repositories.UsuarioRepository;
import org.tfg.inhometfgcarloshernandez.domain.errores.NotFoundException;
import org.tfg.inhometfgcarloshernandez.domain.model.Usuario;
import org.tfg.inhometfgcarloshernandez.domain.model.mappers.UsuarioMappers;

@Service
@RequiredArgsConstructor
public class UsuarioServicios {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMappers usuarioMappers;

    public Usuario findByEmail(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .map(usuarioMappers::toUsuario)
                .orElseThrow(() -> new NotFoundException("Usuario con correo " + correo + " no encontrado"));
    }


}
