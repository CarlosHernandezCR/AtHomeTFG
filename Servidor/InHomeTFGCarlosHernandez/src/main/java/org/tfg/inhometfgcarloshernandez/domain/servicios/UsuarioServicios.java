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
import org.tfg.inhometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.inhometfgcarloshernandez.common.utils.UserTools;
import org.tfg.inhometfgcarloshernandez.data.model.*;
import org.tfg.inhometfgcarloshernandez.data.repositories.CasaRepository;
import org.tfg.inhometfgcarloshernandez.data.repositories.CredencialesRepository;
import org.tfg.inhometfgcarloshernandez.data.repositories.EstadosUsuariosRepository;
import org.tfg.inhometfgcarloshernandez.data.repositories.UsuarioRepository;
import org.tfg.inhometfgcarloshernandez.domain.errores.CustomedException;
import org.tfg.inhometfgcarloshernandez.domain.model.mappers.UsuarioMappers;
import org.tfg.inhometfgcarloshernandez.spring.common.constantes.ConstantesError;
import org.tfg.inhometfgcarloshernandez.spring.common.utils.TokensTools;
import org.tfg.inhometfgcarloshernandez.spring.model.UsuarioDTO;
import org.tfg.inhometfgcarloshernandez.spring.model.response.LoginResponseDTO;
import org.tfg.inhometfgcarloshernandez.spring.security.Security;

import java.time.Duration;
import java.time.Instant;
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
    private final CredencialesRepository credencialesRepository;
    private final Security security;
    private final EstadosUsuariosRepository estadosUsuariosRepository;
    private final MandarMail mandarMail;


    public LoginResponseDTO doLogin(String identificador, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(identificador);
        Authentication auth;
        try {
            auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetails.getUsername(), password));
        } catch (AuthenticationException e) {
            throw new CustomedException(ConstantesError.ERROR_INICIAR_SESION);
        }
        UsuarioEntity usuarioEntity = userTools.getUsuarioEntityByIdentificador(identificador);
        String accessToken = tokensTools.generateAccessToken(auth.getName(), usuarioEntity.getId() );
        String refreshToken = tokensTools.generateRefreshToken(auth.getName(), usuarioEntity.getId());
        return new LoginResponseDTO(accessToken, refreshToken);
    }

    public String refreshTokens(String refreshToken) {
        return tokensTools.refreshTokens(refreshToken);
    }

    public List<UsuarioDTO> getUsuarios(int idCasa) {
        return usuarioRepository.findByIdCasaEntityId(idCasa)
                .stream()
                .map(usuarioMappers::toUsuarioDTO)
                .toList();
    }

    public void registro(String nombre, String password, String correo, String telefono) {
        UsuarioEntity usuario = new UsuarioEntity(0, nombre, correo, telefono, "En Casa");
        usuario = usuarioRepository.save(usuario);
        String contraCodificada = security.encriptarContra(password);
        String codigoActivacion = security.generarCodigoSeguro();
        Instant ahora = Instant.now();
        Instant fechaCaducidad = ahora.plus(Duration.ofMinutes(5));
        CredencialesEntity credenciales = new CredencialesEntity(
                0, usuario, contraCodificada, false, codigoActivacion, fechaCaducidad
        );
        credencialesRepository.save(credenciales);
        mandarMail.generateAndSendEmail(
                correo,
                Constantes.ACTIVACION_CODIGO + codigoActivacion + Constantes.ACTIVAR_SU_CUENTA_HTML,
                codigoActivacion
        );
        estadosUsuariosRepository.save(new EstadosUsuarioEntity(0, usuario, new EstadoEntity("Durmiendo", "#FF0000")));
        estadosUsuariosRepository.save(new EstadosUsuarioEntity(0, usuario, new EstadoEntity("En casa", "#00FF00")));
        estadosUsuariosRepository.save(new EstadosUsuarioEntity(0, usuario, new EstadoEntity("Fuera de casa", "#FFFF00")));
    }

    public void validateUser(String codigoActivacion) {
        CredencialesEntity credencialesEntity = credencialesRepository.findByCodigoActivacion(codigoActivacion);
        if (credencialesEntity != null) {
            if (credencialesEntity.getFechaExpiracionCodigo().isAfter(Instant.now())) {
                credencialesEntity.setActivado(true);
                credencialesRepository.save(credencialesEntity);
            } else {
                throw new CustomedException(ConstantesError.CODIGO_EXPIRADO);
            }
        } else {
            throw new CustomedException(ConstantesError.CODIGO_NO_VALIDO);
        }
    }
}