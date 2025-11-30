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

@Tag(name = "Auth", description = "Endpoints para el registro de usuarios y obtención de Tokens JWT.")
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
        @Operation(summary = "Health Check", description = "Endpoint ligero para verificar que la API está online y respondiendo.")
        @GetMapping("/ping")
        public String ping() {
                return "ok";
        }

        // =============================
        // REGISTRO
        // =============================
        @Operation(summary = "Registrar nuevo cliente", description = """
                        Crea una cuenta de usuario con rol **CLIENT**.

                        Validaciones:
                        - El email debe ser único en el sistema.
                        - La contraseña debe cumplir con los requisitos mínimos de seguridad (6 Digitos).
                        """)
        @ApiResponse(responseCode = "200", description = "Usuario registrado con éxito.", content = @Content(schema = @Schema(implementation = UserResponse.class)))
        @ApiResponse(responseCode = "400", description = "Email ya registrado o datos inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        @PostMapping("/register")
        public ResponseEntity<UserResponse> register(@Valid @RequestBody CreateUserRequest req) {
                return ResponseEntity.ok(users.createClient(req));
        }

        // =============================
        // LOGIN
        // =============================
        @Operation(summary = "Iniciar Sesión (Obtener Token)", description = """
                        Autentica al usuario verificando email y contraseña.
                        Respuesta:
                        Retorna un JWT (JSON Web Token) que debe ser enviado en el header `Authorization: Bearer <token>` para acceder a endpoints protegidos.
                        """)
        @ApiResponse(responseCode = "200", description = "Login exitoso. Devuelve el Token.", content = @Content(schema = @Schema(implementation = TokenResponse.class)))
        @ApiResponse(responseCode = "401", description = "Credenciales incorrectas (Email o password no coinciden).", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        @PostMapping("/login")
        public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {

                User u;

                try {
                        u = users.loadByEmail(req.email());
                } catch (IllegalArgumentException e) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                        .body(new ErrorResponse(
                                                        "Credenciales inválidas",
                                                        "El email o la contraseña no coinciden"));
                }

                if (!encoder.matches(req.password(), u.getPassword())) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                        .body(new ErrorResponse(
                                                        "Credenciales inválidas",
                                                        "El email o la contraseña no coinciden"));
                }

                String token = jwt.generate(u.getEmail(), u.getRole().name());
                return ResponseEntity.ok(new TokenResponse(token, u.getRole().name(), u.getName()));
        }

        // =============================
        // /auth/me (Requiere Token)
        // =============================
        @Operation(summary = "Perfil de Usuario", description = "Decodifica el token actual para devolver la información del usuario conectado.")
        @ApiResponse(responseCode = "200", description = "Datos del usuario autenticado", content = @Content(schema = @Schema(implementation = MeResponse.class)))
        @ApiResponse(responseCode = "401", description = "Token inválido o faltante", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        @SecurityRequirement(name = "BearerAuth")
        @GetMapping("/me")
        public ResponseEntity<?> me(Authentication auth) {

                if (auth == null || !auth.isAuthenticated()) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                        .body(new ErrorResponse(
                                                        "Token inválido",
                                                        "No se pudo obtener el usuario autenticado"));
                }

                String email = auth.getName();
                User u = users.loadByEmail(email);

                return ResponseEntity.ok(
                                new MeResponse(
                                                u.getName(),
                                                u.getEmail(),
                                                u.getRole().name()));
        }
}
