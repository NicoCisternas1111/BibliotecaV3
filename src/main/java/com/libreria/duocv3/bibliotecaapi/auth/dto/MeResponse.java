package com.libreria.duocv3.bibliotecaapi.auth.dto;

public record MeResponse(
        String name,
        String email,
        String role
) {}
