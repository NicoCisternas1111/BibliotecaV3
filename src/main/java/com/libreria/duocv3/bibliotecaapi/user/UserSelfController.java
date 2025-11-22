package com.libreria.duocv3.bibliotecaapi.user;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.libreria.duocv3.bibliotecaapi.user.dto.ChangePasswordRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "self-user-controller", description = "Operaciones de usuario autenticado")
@RestController
@RequestMapping("/api/users/me")
public class UserSelfController {

    private final UserService users;

    public UserSelfController(UserService users) {
        this.users = users;
    }

    @Operation(operationId = "changeOwnPassword", summary = "Cambia la contraseña del usuario autenticado")
    @PostMapping("/change-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@Valid @RequestBody ChangePasswordRequest req, Authentication auth) {
        String email = auth.getName(); // set por JwtFilter
        users.changeOwnPassword(email, req.currentPassword(), req.newPassword());
    }
}
