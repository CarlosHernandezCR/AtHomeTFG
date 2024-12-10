package org.tfg.inhometfgcarloshernandez.spring.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.tfg.inhometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.inhometfgcarloshernandez.common.utils.UserTools;
import org.tfg.inhometfgcarloshernandez.data.model.CredencialesEntity;
import org.tfg.inhometfgcarloshernandez.data.model.UsuarioEntity;
import org.tfg.inhometfgcarloshernandez.data.repositories.CredencialesRepository;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final CredencialesRepository credencialesRepository;
    private final UserTools userTools;

    @Override
    public UserDetails loadUserByUsername(String identificador) throws UsernameNotFoundException {
        UsuarioEntity usuarioEntity = userTools.getUsuarioEntityByIdentificador(identificador);
        CredencialesEntity credenciales = credencialesRepository.findByIdUsuario(usuarioEntity);
        if (credenciales == null) {
            throw new UsernameNotFoundException(Constantes.NO_ENCONTRADO + identificador);
        }
        return User.builder()
                .username(usuarioEntity.getNombre())
                .password(credenciales.getPassword())
                .build();
    }
}