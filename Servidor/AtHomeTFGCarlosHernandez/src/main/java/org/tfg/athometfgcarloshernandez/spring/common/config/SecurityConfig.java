package org.tfg.athometfgcarloshernandez.spring.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesServer;
import org.tfg.athometfgcarloshernandez.spring.security.JwtTokenFilter;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                    corsConfig.setAllowedOrigins(List.of(ConstantesServer.BASE_API, ConstantesServer.BASE_API_INSTI));
                    corsConfig.setAllowedMethods(List.of(ConstantesServer.GET, ConstantesServer.PUT, ConstantesServer.POST, ConstantesServer.DELETE));
                    corsConfig.setAllowedHeaders(List.of(ConstantesServer.ASTERISCO));
                    return corsConfig;
                }))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(POST,ConstantesServer.LOGINPATH).permitAll()
                        .requestMatchers(POST,ConstantesServer.LOGINPATH+ConstantesServer.REFRESH_TOKEN_PATH).permitAll()
                        .requestMatchers(POST,  ConstantesServer.REGISTERPATH).permitAll()
                        .requestMatchers(GET, ConstantesServer.REGISTERPATH + ConstantesServer.VALIDAR_USUARIO).permitAll()
                        .requestMatchers(GET,  ConstantesServer.DESCARGAR + ConstantesServer.APK).permitAll()
                        .requestMatchers(ConstantesServer.STATIC, ConstantesServer.BARRA, ConstantesServer.DESCARGAR_APK, ConstantesServer.INDEX, ConstantesServer.JS, ConstantesServer.CSS).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}