//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.tfg.inhometfgcarloshernandez.domain.servicios;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tfg.inhometfgcarloshernandez.data.repositories.UsuarioRepository;
import org.tfg.inhometfgcarloshernandez.domain.modelo.Usuario;
import org.tfg.inhometfgcarloshernandez.domain.modelo.mappers.UsuarioMappers;

@Service
@RequiredArgsConstructor
public class UsuarioServicios {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMappers usuarioMappers;

    public Usuario findByEmail(String correo) {
            return usuarioRepository.findByCorreo(correo)
                    .map(usuarioMappers::toUsuario) // Solo mapea si encuentra el usuario
                    .orElse(null); // Devuelve null si no encuentra el usuario//
    }

}
