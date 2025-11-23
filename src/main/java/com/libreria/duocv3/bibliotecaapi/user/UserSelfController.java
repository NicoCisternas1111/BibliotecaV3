package com.libreria.duocv3.bibliotecaapi.user;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.libreria.duocv3.bibliotecaapi.user.dto.ChangePasswordRequest;
import com.libreria.duocv3.bibliotecaapi.user.dto.UserResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "self-user-controller", description = "Operaciones del usuario autenticado")
@RestController
@RequestMapping("/api/users/me")
public class UserSelfController {

    private final UserService users;

    public UserSelfController(UserService users) {
        this.users = users;
    }

    // =============================================
    // 1) Obtener información del usuario autenticado
    // =============================================
    @Operation(
        operationId = "getAuthenticatedUser",
        summary = "Obtiene los datos del usuario autenticado"
    )
    @GetMapping
    public UserResponse getMe(Authentication auth) {
        String email = auth.getName();
        return users.findUserByEmailResponse(email);
    }

    // =============================================
    // 2) Cambiar contraseña del usuario autenticado
    // =============================================
    @Operation(
        operationId = "changeOwnPassword",
        summary = "Cambia la contraseña del usuario autenticado"
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
