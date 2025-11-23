package com.libreria.duocv3.bibliotecaapi.security;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.libreria.duocv3.bibliotecaapi.user.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwt;
    private final UserService users;

    public JwtFilter(JwtUtil jwt, UserService users) {
        this.jwt = jwt;
        this.users = users;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain)
            throws ServletException, IOException {

        // Si YA hay autenticación, no la tocamos
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            chain.doFilter(req, res);
            return;
        }

        String header = req.getHeader(HttpHeaders.AUTHORIZATION);

        // Si no viene Authorization Bearer, seguimos sin autenticar
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }

        String token = header.substring(7);

        // Si el token es inválido, seguimos sin autenticar
        if (!jwt.isValid(token)) {
            chain.doFilter(req, res);
            return;
        }

        String email = jwt.getEmail(token);
        String role = jwt.getRole(token);

        // Validar que el usuario exista
        try {
            users.loadByEmail(email);
        } catch (Exception e) {
            // Si no existe el usuario, seguimos sin autenticar
            chain.doFilter(req, res);
            return;
        }

        List<SimpleGrantedAuthority> authorities =
                (role != null)
                        ? List.of(new SimpleGrantedAuthority(role))
                        : Collections.emptyList();

        var auth = new UsernamePasswordAuthenticationToken(
                email,      // principal
                null,       // credentials
                authorities // roles
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        chain.doFilter(req, res);
    }
}
