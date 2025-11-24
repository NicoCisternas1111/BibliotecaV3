package com.libreria.duocv3.bibliotecaapi.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.libreria.duocv3.bibliotecaapi.security.JwtFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .authorizeHttpRequests(auth -> auth

                // =====================
                //        PÚBLICO
                // =====================
                .requestMatchers("/auth/login", "/auth/register", "/auth/ping").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                // CORS preflight (React/Front)
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // Catálogo público de libros
                .requestMatchers(HttpMethod.GET, "/api/books/**").permitAll()

                // =====================
                //    AUTENTICADOS
                // =====================
                // Perfil propio (/auth/me)
                .requestMatchers(HttpMethod.GET, "/auth/me").authenticated()

                // Operaciones sobre el propio usuario
                .requestMatchers("/api/users/me/**").authenticated()

                // Endpoint especial: permitir que cualquier usuario logueado
                // pueda actualizar stock al comprar desde el carrito
                .requestMatchers(HttpMethod.PUT, "/api/admin/books/**").authenticated()

                // =====================
                //        ADMIN
                // =====================
                .requestMatchers("/api/categories/**").hasRole("ADMIN")
                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                // =====================
                //   TODO LO DEMÁS
                // =====================
                .anyRequest().authenticated()
            )

            // Filtro JWT antes que UsernamePasswordAuthenticationFilter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOriginPatterns(List.of("*"));
        cfg.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        cfg.setAllowedHeaders(List.of("*"));
        cfg.setExposedHeaders(List.of("Authorization"));
        cfg.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}
