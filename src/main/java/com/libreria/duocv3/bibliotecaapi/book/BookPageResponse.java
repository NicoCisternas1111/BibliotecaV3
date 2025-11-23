package com.libreria.duocv3.bibliotecaapi.book;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Página paginada de libros")
public record BookPageResponse(
        List<BookResponse> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean last
) {}
