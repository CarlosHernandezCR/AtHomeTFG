package org.tfg.athometfgcarloshernandez.domain.servicios;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.tfg.athometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.athometfgcarloshernandez.common.utils.UserTools;
import org.tfg.athometfgcarloshernandez.data.model.*;
import org.tfg.athometfgcarloshernandez.data.repositories.CredencialesRepository;
import org.tfg.athometfgcarloshernandez.data.repositories.EstadosUsuariosRepository;
import org.tfg.athometfgcarloshernandez.data.repositories.UsuarioRepository;
import org.tfg.athometfgcarloshernandez.domain.errores.CustomedException;
import org.tfg.athometfgcarloshernandez.domain.errores.ErrorLoginException;
import org.tfg.athometfgcarloshernandez.domain.model.mappers.UsuarioMappers;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesError;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesServer;
import org.tfg.athometfgcarloshernandez.spring.common.utils.TokensTools;
import org.tfg.athometfgcarloshernandez.spring.model.UsuarioDTO;
import org.tfg.athometfgcarloshernandez.spring.model.response.LoginResponseDTO;
import org.tfg.athometfgcarloshernandez.spring.common.utils.Security;

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
            throw new ErrorLoginException(ConstantesError.ERROR_INICIAR_SESION);
        }
        UsuarioEntity usuarioEntity = userTools.getUsuarioEntityByIdentificador(identificador);
        String accessToken = tokensTools.generarAccessToken(auth.getName(), usuarioEntity.getId() );
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

    public void registro(String nombre, String password, String correo, String telefono, String color) {
        UsuarioEntity usuario = new UsuarioEntity(0, nombre, correo, telefono,color);
        usuario = usuarioRepository.save(usuario);
        String contraCodificada = security.encriptarContra(password);
        String codigoActivacion = security.generarCodigoSeguro();
        Instant ahora = Instant.now();
        Instant fechaCaducidad = ahora.plus(Duration.ofMinutes(ConstantesServer.CADUCIDAD_CODIGO_REGISTRO));
        CredencialesEntity credenciales = new CredencialesEntity(
                0, usuario, contraCodificada, false, codigoActivacion, fechaCaducidad
        );
        credencialesRepository.save(credenciales);
        mandarMail.generateAndSendEmail(
                correo,
                Constantes.ACTIVACION_CODIGO + codigoActivacion + Constantes.ACTIVAR_SU_CUENTA_HTML,
                ConstantesServer.ASUNTO_ACTIVACION
        );

        estadosUsuariosRepository.save(new EstadosUsuarioEntity(0, usuario, new EstadoEntity(ConstantesServer.ESTADO_PREDETERMINADO1, ConstantesServer.COLOR_ESTADO_PREDETERMINADO1)));
        estadosUsuariosRepository.save(new EstadosUsuarioEntity(0, usuario, new EstadoEntity(ConstantesServer.ESTADO_PREDETERMINADO2, ConstantesServer.COLOR_ESTADO_PREDETERMINADO2)));
        estadosUsuariosRepository.save(new EstadosUsuarioEntity(0, usuario, new EstadoEntity(ConstantesServer.ESTADO_PREDETERMINADO3, ConstantesServer.COLOR_ESTADO_PREDETERMINADO3)));
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