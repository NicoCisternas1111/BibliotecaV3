package com.libreria.duocv3.bibliotecaapi.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.libreria.duocv3.bibliotecaapi.common.ErrorResponse;
import com.libreria.duocv3.bibliotecaapi.user.dto.ChangePasswordRequest;
import com.libreria.duocv3.bibliotecaapi.user.dto.UserResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(
        name = "User (Self)",
        description = "Operaciones disponibles para el usuario autenticado usando JWT"
)
@RestController
@RequestMapping("/api/users/me")
public class UserSelfController {

    private final UserService users;

    public UserSelfController(UserService users) {
        this.users = users;
    }

    // =====================================================
    // 1) Obtener información del usuario autenticado
    // =====================================================
    @Operation(
            summary = "Obtener perfil personal",
            description = "Retorna los datos del usuario autenticado usando el JWT enviado en el header Authorization."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Perfil obtenido correctamente",
            content = @Content(schema = @Schema(implementation = UserResponse.class))
    )
    @ApiResponse(
            responseCode = "401",
            description = "Token inválido o faltante",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping
    public ResponseEntity<UserResponse> getMe(Authentication auth) {
        String email = auth.getName();
        UserResponse response = users.findUserByEmailResponse(email);
        return ResponseEntity.ok(response);
    }

    // =====================================================
    // 2) Cambiar contraseña
    // =====================================================
    @Operation(
            summary = "Cambiar contraseña",
            description = "Permite cambiar la contraseña del usuario autenticado. Requiere contraseña actual válida."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Contraseña cambiada correctamente"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "401",
            description = "Token inválido o faltante",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "403",
            description = "Contraseña actual incorrecta",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @PostMapping("/change-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(
            @Valid @RequestBody ChangePasswordRequest req,
            Authentication auth
    ) {
        String email = auth.getName();
        users.changeOwnPassword(email, req.currentPassword(), req.newPassword());
    }
}
