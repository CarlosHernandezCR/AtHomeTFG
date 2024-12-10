package org.tfg.inhometfgcarloshernandez.spring.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.tfg.inhometfgcarloshernandez.domain.errores.CustomedException;
import org.tfg.inhometfgcarloshernandez.spring.common.constantes.ConstantesServer;
import org.tfg.inhometfgcarloshernandez.spring.common.constantes.ConstantesSpring;
import org.tfg.inhometfgcarloshernandez.spring.common.utils.TokensTools;

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
        if (request.getRequestURI().equals(ConstantesServer.LOGINPATH) ||
                request.getRequestURI().equals(ConstantesServer.REGISTER) ||
                request.getRequestURI().equals(ConstantesServer.REGISTER + ConstantesServer.VALIDAR_USUARIO) ||
                request.getRequestURI().equals(ConstantesServer.LOGINPATH + ConstantesServer.REFRESH_TOKEN_PATH) ||
                request.getRequestURI().equals(ConstantesServer.OLVIDAR_CONTRASENA)) {
            chain.doFilter(request, response);
            return;
        }

        final String header = request.getHeader(AUTHORIZATION);
        if (header == null || !header.startsWith(ConstantesSpring.BEARER)) {
            chain.doFilter(request, response);
            return;
        }
        final String token = header.split(" ")[1].trim();
        try {
            if (!jwtTokenUtil.validateToken(token)) {
                chain.doFilter(request, response);
                return;
            }
            UserDetails userDetails = userRepo.loadUserByUsername(jwtTokenUtil.getUsernameFromToken(token));
            UsernamePasswordAuthenticationToken
                    authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null,
                    userDetails == null ?
                            List.of() : userDetails.getAuthorities()
            );
            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch (CustomedException exception) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

}