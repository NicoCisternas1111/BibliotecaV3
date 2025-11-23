package com.libreria.duocv3.bibliotecaapi.user.dto;

public record UserResponse(
        Long id,
        String name,
        String email,
        String role
) {}
