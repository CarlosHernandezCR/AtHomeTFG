// UsuarioServicios.java
package org.tfg.inhometfgcarloshernandez.domain.servicios;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.tfg.inhometfgcarloshernandez.common.utils.UserTools;
import org.tfg.inhometfgcarloshernandez.data.model.CasaEntity;
import org.tfg.inhometfgcarloshernandez.data.model.UsuarioEntity;
import org.tfg.inhometfgcarloshernandez.data.repositories.CasaRepository;
import org.tfg.inhometfgcarloshernandez.data.repositories.UsuarioRepository;
import org.tfg.inhometfgcarloshernandez.domain.errores.CustomedException;
import org.tfg.inhometfgcarloshernandez.domain.model.mappers.UsuarioMappers;
import org.tfg.inhometfgcarloshernandez.spring.common.constantes.ConstantesError;
import org.tfg.inhometfgcarloshernandez.spring.common.utils.TokensTools;
import org.tfg.inhometfgcarloshernandez.spring.model.UsuarioDTO;
import org.tfg.inhometfgcarloshernandez.spring.model.response.LoginResponseDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServicios {
    private final AuthenticationManager authenticationManager;
    private final TokensTools tokensTools;
    private final UserDetailsService userDetailsService;
    private final UserTools userTools;
    private final CasaRepository casaRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMappers usuarioMappers;

    public LoginResponseDTO doLogin(String identificador, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(identificador);
        Authentication auth;
        try {
            auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetails.getUsername(), password));
        } catch (AuthenticationException e) {
            throw new CustomedException(ConstantesError.ERROR_INICIAR_SESION);
        }
        UsuarioEntity usuarioEntity = userTools.getUsuarioEntityByIdentificador(identificador);
        List<Integer> idCasas= casaRepository.findCasasPorUsuarioId(usuarioEntity.getId()).stream().map(CasaEntity::getId).toList();
        String accessToken = tokensTools.generateAccessToken(auth.getName(), usuarioEntity.getId(), idCasas);
        String refreshToken = tokensTools.generateRefreshToken(auth.getName(), usuarioEntity.getId(), idCasas);
        return new LoginResponseDTO(accessToken, refreshToken);
    }

    public String refreshTokens(String refreshToken) {
        return tokensTools.refreshTokens(refreshToken);
    }
    public List<UsuarioDTO> getUsuarios(int idCasa) {
        return  usuarioRepository.findByIdCasaEntityId(idCasa)
                .stream()
                .map(usuarioMappers::toUsuarioDTO)
                .toList();
    }
}