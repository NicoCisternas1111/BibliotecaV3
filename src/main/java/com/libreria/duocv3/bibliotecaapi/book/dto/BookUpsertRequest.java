package com.libreria.duocv3.bibliotecaapi.book.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookUpsertRequest(
        @NotBlank String title,
        @NotBlank String author,
        @NotBlank String category,
        @NotNull @Min(0) Integer price,
        @NotNull @Min(0) Integer stock,
        String description,
        String extendedDescription,
        String image
) {}
