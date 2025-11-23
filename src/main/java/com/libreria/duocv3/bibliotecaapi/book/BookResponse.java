package com.libreria.duocv3.bibliotecaapi.book;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representación pública de un libro")
public record BookResponse(
        String id,
        String title,
        String author,
        String category,
        Integer price,
        Integer stock,
        String description,
        String extendedDescription,
        String image
) {}
