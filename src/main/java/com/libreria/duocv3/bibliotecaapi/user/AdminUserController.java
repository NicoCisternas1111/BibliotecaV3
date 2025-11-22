package com.libreria.duocv3.bibliotecaapi.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.libreria.duocv3.bibliotecaapi.user.dto.CreateUserRequest;
import com.libreria.duocv3.bibliotecaapi.user.dto.UserResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "admin-user-controller", description = "Gestión de usuarios (admin)")
@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final UserService users;

    public AdminUserController(UserService users) {
        this.users = users;
    }

    @Operation(operationId = "adminCreateClient", summary = "Crea un usuario cliente (ROLE_USER)")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createClient(@Valid @RequestBody CreateUserRequest req) {
        return users.createClient(req);
    }
}
