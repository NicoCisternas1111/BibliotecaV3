package com.libreria.duocv3.bibliotecaapi.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libreria.duocv3.bibliotecaapi.auth.dto.LoginRequest;
import com.libreria.duocv3.bibliotecaapi.auth.dto.MeResponse;
import com.libreria.duocv3.bibliotecaapi.auth.dto.TokenResponse;
import com.libreria.duocv3.bibliotecaapi.common.ErrorResponse;
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
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

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

    // =============================
    // PING
    // =============================
    @Operation(
            summary = "Ping de prueba",
            description = "Permite verificar si el backend está operativo."
    )
    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }

    // =============================
    // REGISTRO
    // =============================
    @Operation(
            summary = "Registrar usuario",
            description = "Registra un nuevo usuario CLIENT en el sistema."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Usuario creado correctamente",
            content = @Content(schema = @Schema(implementation = UserResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody CreateUserRequest req) {
        return ResponseEntity.ok(users.createClient(req));
    }

    // =============================
    // LOGIN
    // =============================
    @Operation(
            summary = "Iniciar sesión",
            description = "Autentica un usuario por email y contraseña, devolviendo un token JWT válido."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Autenticación exitosa",
            content = @Content(schema = @Schema(implementation = TokenResponse.class))
    )
    @ApiResponse(
            responseCode = "401",
            description = "Credenciales inválidas",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {

        User u;

        try {
            u = users.loadByEmail(req.email());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(
                            "Credenciales inválidas",
                            "El email o la contraseña no coinciden"
                    ));
        }

        if (!encoder.matches(req.password(), u.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(
                            "Credenciales inválidas",
                            "El email o la contraseña no coinciden"
                    ));
        }

        String token = jwt.generate(u.getEmail(), u.getRole().name());
        return ResponseEntity.ok(new TokenResponse(token, u.getRole().name(), u.getName()));
    }

    // =============================
    // /auth/me (Requiere Token)
    // =============================
    @Operation(
            summary = "Obtener información del usuario autenticado",
            description = "Retorna los datos del usuario basados en el token JWT enviado en el header."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Datos del usuario autenticado",
            content = @Content(schema = @Schema(implementation = MeResponse.class))
    )
    @ApiResponse(
            responseCode = "401",
            description = "Token inválido o faltante",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @SecurityRequirement(name = "BearerAuth")
    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication auth) {

        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(
                            "Token inválido",
                            "No se pudo obtener el usuario autenticado"
                    ));
        }

        String email = auth.getName(); // ← viene del JwtFilter
        User u = users.loadByEmail(email);

        return ResponseEntity.ok(
                new MeResponse(
                        u.getName(),
                        u.getEmail(),
                        u.getRole().name()
                )
        );
    }
}
