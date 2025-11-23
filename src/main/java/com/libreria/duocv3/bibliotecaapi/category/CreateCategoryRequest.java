package com.libreria.duocv3.bibliotecaapi.category;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(
        @NotBlank String name
) {}
