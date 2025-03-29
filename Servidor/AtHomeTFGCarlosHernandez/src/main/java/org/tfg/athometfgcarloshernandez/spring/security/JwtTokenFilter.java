package org.tfg.athometfgcarloshernandez.spring.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNullApi;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesServer;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesSpring;
import org.tfg.athometfgcarloshernandez.spring.common.utils.TokensTools;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final TokensTools jwtTokenUtil;
    private final UserDetailsServiceImpl userRepo;

    public JwtTokenFilter(TokensTools jwtTokenUtil,
                          UserDetailsServiceImpl userRepo) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepo = userRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        if (isExemptedPath(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        final String header = request.getHeader(AUTHORIZATION);
        if (header == null || !header.startsWith(ConstantesSpring.BEARER)) {
            chain.doFilter(request, response);
            return;
        }

        final String token = header.substring(7).trim();
        jwtTokenUtil.validarToken(token);
        UserDetails userDetails = userRepo.loadUserByUsername(jwtTokenUtil.getSubjectDesdeToken(token));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails == null ? List.of() : userDetails.getAuthorities()
        );
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private boolean isExemptedPath(String requestURI) {
        return requestURI.equals(ConstantesServer.LOGINPATH) ||
                requestURI.equals(ConstantesServer.REGISTERPATH) ||
                requestURI.equals(ConstantesServer.REGISTERPATH + ConstantesServer.VALIDAR_USUARIO) ||
                requestURI.equals(ConstantesServer.LOGINPATH + ConstantesServer.REFRESH_TOKEN_PATH) ||
                requestURI.equals(ConstantesServer.OLVIDAR_CONTRASENA);
    }

}