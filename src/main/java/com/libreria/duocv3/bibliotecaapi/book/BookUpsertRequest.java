package com.libreria.duocv3.bibliotecaapi.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookUpsertRequest(
        @NotBlank String title,
        @NotBlank String author,

        @NotNull @Min(0) Double price,
        @NotNull @Min(0) Integer stock,

        @NotBlank String description,
        String extendedDescription,
        String image,

        @NotBlank String category
) {}
