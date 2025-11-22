package com.libreria.duocv3.bibliotecaapi.auth;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.libreria.duocv3.bibliotecaapi.auth.dto.LoginRequest;
import com.libreria.duocv3.bibliotecaapi.auth.dto.TokenResponse;
import com.libreria.duocv3.bibliotecaapi.security.JwtUtil;
import com.libreria.duocv3.bibliotecaapi.user.User;
import com.libreria.duocv3.bibliotecaapi.user.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*") // solo dev
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService users;
    private final PasswordEncoder encoder;
    private final JwtUtil jwt;

    public AuthController(UserService users, PasswordEncoder encoder, JwtUtil jwt) {
        this.users = users; this.encoder = encoder; this.jwt = jwt;
    }

    @GetMapping("/ping") public String ping() { return "ok"; }

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest req) {
        User u;
        try {
            u = users.loadByEmail(req.email()); // si no existe, lanzará IllegalArgumentException
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }

        if (u == null || u.getPassword() == null || !encoder.matches(req.password(), u.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }

        String token = jwt.generate(u.getEmail(), u.getRole().name());
        return new TokenResponse(token, u.getRole().name(), u.getName());
    }

}
