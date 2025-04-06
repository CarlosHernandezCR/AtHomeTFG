package org.tfg.athometfgcarloshernandez.spring.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.tfg.athometfgcarloshernandez.domain.errores.CustomedException;
import org.tfg.athometfgcarloshernandez.domain.errores.TokenException;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesError;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesSpring;

@Component
@RequiredArgsConstructor
public class TokensTools {
    private final Security security;

    public String generarAccessToken(String subject, int idUsuario) {
        try {
            Date now = new Date();
            return Jwts.builder()
                    .setSubject(subject)
                    .setIssuer(ConstantesSpring.ME)
                    .setIssuedAt(now)
                    .setExpiration(Date
                            .from(LocalDateTime.now().plusSeconds(ConstantesSpring.CADUCIDAD_CODIGO).atZone(ZoneId.systemDefault())
                                    .toInstant()))
                    .claim(ConstantesSpring.IDUSUARIO, idUsuario)
                    .signWith(security.readPrivateKeyFromKeyStoreServer())
                    .compact();

        } catch (UnrecoverableEntryException | CertificateException | KeyStoreException | IOException |
                 NoSuchAlgorithmException e) {
            throw new CustomedException(ConstantesError.ERROR_GERERATE_ACCESS_TOKEN);
        }
    }
    public String generarAccessTokenConCasa(String subject, int idUsuario, int idCasa) {
        try {
            Date now = new Date();
            return Jwts.builder()
                    .setSubject(subject)
                    .setIssuer(ConstantesSpring.ME)
                    .setIssuedAt(now)
                    .setExpiration(Date
                            .from(LocalDateTime.now().plusSeconds(ConstantesSpring.CADUCIDAD_CODIGO).atZone(ZoneId.systemDefault())
                                    .toInstant()))
                    .claim(ConstantesSpring.IDUSUARIO, idUsuario)
                    .claim(ConstantesSpring.IDCASA, idCasa)
                    .signWith(security.readPrivateKeyFromKeyStoreServer())
                    .compact();

        } catch (UnrecoverableEntryException | CertificateException | KeyStoreException | IOException |
                 NoSuchAlgorithmException e) {
            throw new CustomedException(ConstantesError.ERROR_GERERATE_ACCESS_TOKEN);
        }
    }

    public String generateRefreshToken(String subject, int idUsuario) {
        try {
            Date now = new Date();
            return Jwts.builder()
                    .setSubject(subject)
                    .setIssuer(ConstantesSpring.ME)
                    .setIssuedAt(now)
                    .setExpiration(Date
                            .from(LocalDateTime.now().plusSeconds(ConstantesSpring.CADUCIDAD_CODIGO_REFRESH).atZone(ZoneId.systemDefault())
                                    .toInstant()))
                    .claim(ConstantesSpring.IDUSUARIO, idUsuario)
                    .signWith(security.readPrivateKeyFromKeyStoreServer())
                    .compact();
        } catch (UnrecoverableEntryException | CertificateException | KeyStoreException | IOException |
                 NoSuchAlgorithmException e) {
            throw new CustomedException(ConstantesError.ERROR_GERERATE_REFRESH_TOKEN);
        }
    }

    public String generarRefreshTokenConCasa(String subject, int idUsuario, int idCasa) {
        try {
            Date now = new Date();
            return Jwts.builder()
                    .setSubject(subject)
                    .setIssuer(ConstantesSpring.ME)
                    .setIssuedAt(now)
                    .setExpiration(Date
                            .from(LocalDateTime.now().plusSeconds(ConstantesSpring.CADUCIDAD_CODIGO_REFRESH).atZone(ZoneId.systemDefault())
                                    .toInstant()))
                    .claim(ConstantesSpring.IDUSUARIO, idUsuario)
                    .claim(ConstantesSpring.IDCASA, idCasa)
                    .signWith(security.readPrivateKeyFromKeyStoreServer())
                    .compact();
        } catch (UnrecoverableEntryException | CertificateException | KeyStoreException | IOException |
                 NoSuchAlgorithmException e) {
            throw new CustomedException(ConstantesError.ERROR_GERERATE_REFRESH_TOKEN);
        }
    }

    public void validarToken(String token) {
        Jws<Claims> claimsJws = parseToken(token);
        long expirationMillis = claimsJws.getBody().getExpiration().getTime();
        if (!(System.currentTimeMillis() <= expirationMillis))
            throw new TokenException(ConstantesError.TOKEN_EXPIRADO);
    }

    public String refreshTokens(String refreshToken) {
        if (refreshToken == null) throw new CustomedException(ConstantesError.NO_HAY_TOKEN);
        Jws<Claims> claimsJws = parseToken(refreshToken);
        String subject = claimsJws.getBody().getSubject();
        int idUsuario = claimsJws.getBody().get(ConstantesSpring.IDUSUARIO, Integer.class);
        return generarAccessToken(subject, idUsuario);
    }

    public Jws<Claims> parseToken(String token) {try {
            token = removeBearerPrefix(token);
            return Jwts.parserBuilder()
                    .setSigningKey(security.readCertificateFromKeyStoreServer().getPublicKey())
                    .build()
                    .parseClaimsJws(token);
        } catch (CertificateException e) {
            throw new TokenException(ConstantesError.TOKEN_INVALIDO);
        } catch (IOException e) {
            throw new CustomedException(ConstantesError.ERROR_IO);
        } catch (KeyStoreException e) {
            throw new CustomedException(ConstantesError.ERROR_KEYSTORE);
        } catch (NoSuchAlgorithmException e) {
            throw new CustomedException(ConstantesError.ERROR_ALGORITHM);
        } catch (ExpiredJwtException e) {
            throw new TokenException(ConstantesError.TOKEN_EXPIRADO);
        }
    }

    private String removeBearerPrefix(String token) {
        if (token.startsWith(ConstantesSpring.BEARER)) {
            return token.substring(7);
        }
        return token;
    }

    public String getSubjectDesdeToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(security.readCertificateFromKeyStoreServer().getPublicKey())
                    .build()
                    .parseClaimsJws(token);
            return claimsJws.getBody().getSubject();
        } catch (Exception e) {
            throw new TokenException(ConstantesError.TOKEN_INVALIDO);
        }

    }

    public String actualizarAccessTokenConCasa(String token, int idCasa) {
        Jws<Claims> claimsJws = parseToken(token);
        String subject = claimsJws.getBody().getSubject();
        int idUsuario = claimsJws.getBody().get(ConstantesSpring.IDUSUARIO, Integer.class);
        return generarAccessTokenConCasa(subject, idUsuario, idCasa);
    }
    public String actualizarRefreshTokenConCasa(String token, int idCasa) {
        Jws<Claims> claimsJws = parseToken(token);
        String subject = claimsJws.getBody().getSubject();
        int idUsuario = claimsJws.getBody().get(ConstantesSpring.IDUSUARIO, Integer.class);
        return generarRefreshTokenConCasa(subject, idUsuario, idCasa);
    }
}