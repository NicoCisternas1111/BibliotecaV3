package com.libreria.duocv3.bibliotecaapi.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.libreria.duocv3.bibliotecaapi.auth.dto.LoginRequest;
import com.libreria.duocv3.bibliotecaapi.auth.dto.TokenResponse;
import com.libreria.duocv3.bibliotecaapi.security.JwtUtil;
import com.libreria.duocv3.bibliotecaapi.user.User;
import com.libreria.duocv3.bibliotecaapi.user.UserService;
import com.libreria.duocv3.bibliotecaapi.user.dto.CreateUserRequest;
import com.libreria.duocv3.bibliotecaapi.user.dto.UserResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Auth", description = "Endpoints de autenticación y generación de tokens JWT")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService users;
    private final PasswordEncoder encoder;
    private final JwtUtil jwt;

    public AuthController(UserService users, PasswordEncoder encoder, JwtUtil jwt) {
        this.users = users;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    @Operation(
        summary = "Ping de prueba",
        description = "Permite verificar si el backend está operativo."
    )
    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }

    @Operation(
        summary = "Registrar usuario",
        description = "Registra un nuevo usuario CLIENT en el sistema."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Usuario creado",
        content = @Content(schema = @Schema(implementation = UserResponse.class))
    )
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody CreateUserRequest req) {
        return ResponseEntity.ok(users.createClient(req));
    }

    @Operation(
        summary = "Iniciar sesión",
        description = "Autentica un usuario por email y contraseña, devolviendo un token JWT válido."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Autenticación exitosa",
        content = @Content(schema = @Schema(implementation = TokenResponse.class))
    )
    @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest req) {
        User u;

        try {
            u = users.loadByEmail(req.email());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new TokenResponse(null, null, "Credenciales inválidas"));
        }

        if (!encoder.matches(req.password(), u.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new TokenResponse(null, null, "Credenciales inválidas"));
        }

        String token = jwt.generate(u.getEmail(), u.getRole().name());
        return ResponseEntity.ok(new TokenResponse(token, u.getRole().name(), u.getName()));
    }
}
