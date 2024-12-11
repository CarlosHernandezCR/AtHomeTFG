package org.tfg.inhometfgcarloshernandez.spring.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
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
import java.util.List;

import org.tfg.inhometfgcarloshernandez.domain.errores.CustomedException;
import org.tfg.inhometfgcarloshernandez.domain.errores.TokenException;
import org.tfg.inhometfgcarloshernandez.spring.common.constantes.ConstantesError;
import org.tfg.inhometfgcarloshernandez.spring.common.constantes.ConstantesSpring;
import org.tfg.inhometfgcarloshernandez.spring.security.Security;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class TokensTools {
    private final Security security;

    public String generateAccessToken(String subject, int idUsuario, List<Integer> idCasas) {
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
                    .claim(ConstantesSpring.IDCASAS, idCasas)
                    .signWith(security.readPrivateKeyFromKeyStoreServer())
                    .compact();

        } catch (UnrecoverableEntryException | CertificateException | KeyStoreException | IOException |
                 NoSuchAlgorithmException e) {
            throw new CustomedException(ConstantesError.ERROR_GERERATE_ACCESS_TOKEN);
        }
    }

    public String generateRefreshToken(String subject, int idUsuario, List<Integer> idCasas) {
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


    public boolean validateToken(String token) {
        Jws<Claims> claimsJws = parseToken(token);
        long expirationMillis = claimsJws.getBody().getExpiration().getTime();
        if (System.currentTimeMillis() <= expirationMillis) return true;
        else throw new TokenException(ConstantesError.TOKEN_EXPIRADO);
    }

    public String refreshTokens(String refreshToken) {
        if (refreshToken == null) throw new CustomedException(ConstantesError.NO_HAY_TOKEN);
        Jws<Claims> claimsJws = parseToken(refreshToken);
        String subject = claimsJws.getBody().getSubject();
        int idUsuario = claimsJws.getBody().get(ConstantesSpring.IDUSUARIO, Integer.class);
        List<Integer> idCasas = claimsJws.getBody().get(ConstantesSpring.IDCASAS, List.class);
        return generateAccessToken(subject, idUsuario, idCasas);
    }

    public Jws<Claims> parseToken(String token) {
        try {
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
            throw new CustomedException(ConstantesError.TOKEN_EXPIRADO);
        }
    }

    public String getUsernameFromToken(String token) {
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

    public String extraerToken(HttpServletRequest request) {
        final String header = request.getHeader(AUTHORIZATION);
        if (header == null || !header.startsWith(ConstantesSpring.BEARER)) {
            throw new TokenException(ConstantesError.TOKEN_INVALIDO);
        }
        return header.split(" ")[1].trim();
    }
}