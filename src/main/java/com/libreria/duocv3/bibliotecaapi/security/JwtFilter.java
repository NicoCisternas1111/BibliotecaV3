package com.libreria.duocv3.bibliotecaapi.security;

import java.io.IOException;
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
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String header = req.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            if (jwt.isValid(token)) {
                String email = jwt.getEmail(token);
                String role = jwt.getRole(token);

                // Validar existencia de usuario
                try {
                    users.loadByEmail(email);
                } catch (Exception e) {
                    chain.doFilter(req, res);
                    return;
                }

                var authorities = role != null
                        ? List.<SimpleGrantedAuthority>of(new SimpleGrantedAuthority(role))
                        : List.<SimpleGrantedAuthority>of();

                var auth = new UsernamePasswordAuthenticationToken(
                        email,               // principal
                        null,                // credentials
                        authorities          // Collection<? extends GrantedAuthority>
                );

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        chain.doFilter(req, res);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest req) {
        String path = req.getServletPath();

        return path.startsWith("/auth/")
            || path.startsWith("/swagger-ui")
            || path.startsWith("/v3/api-docs");
    }
}
