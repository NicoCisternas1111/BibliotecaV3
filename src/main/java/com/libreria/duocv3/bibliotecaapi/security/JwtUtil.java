package com.libreria.duocv3.bibliotecaapi.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration}") // minutos
    private long expMinutes;

    private Key key() {
        // HS256 requiere clave >= 256 bits (>= 32 chars)
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generate(String email, String role) {
        long now = System.currentTimeMillis();
        Date issued = new Date(now);
        Date exp = new Date(now + expMinutes * 60_000);

        return Jwts.builder()
                .setSubject(email) // se guarda como "subject"
                .setIssuedAt(issued)
                .setExpiration(exp)
                .addClaims(Map.of("role", role))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token);
    }

    public String getEmail(String token) {
        return parse(token).getBody().getSubject();
    }

    public String getRole(String token) {
        return parse(token).getBody().get("role", String.class);
    }

    public boolean isValid(String token) {
        try { 
            parse(token); 
            return true; 
        } catch (Exception e) { 
            return false; 
        }
    }

    // 🔥 Método necesario para /auth/me
    public String getCurrentUserEmail() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getName() == null) {
            return null;
        }

        // auth.getName() es el email que pusimos en JwtFilter
        return auth.getName();
    }
}
