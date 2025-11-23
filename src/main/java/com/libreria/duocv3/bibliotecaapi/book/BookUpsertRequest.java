package com.libreria.duocv3.bibliotecaapi.book;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Modelo usado para crear o actualizar libros")
public record BookUpsertRequest(

        @Schema(description = "Título del libro", example = "El Señor de los Anillos")
        @NotBlank(message = "El título es obligatorio")
        @Size(min = 2, max = 200, message = "El título debe tener entre 2 y 200 caracteres")
        String title,

        @Schema(description = "Autor del libro", example = "J.R.R. Tolkien")
        @NotBlank(message = "El autor es obligatorio")
        @Size(min = 2, max = 200, message = "El autor debe tener entre 2 y 200 caracteres")
        String author,

        @Schema(description = "Precio del libro (>= 0)", example = "12990")
        @NotNull(message = "El precio es obligatorio")
        @Min(value = 0, message = "El precio no puede ser negativo")
        Double price,

        @Schema(description = "Cantidad disponible en stock", example = "10")
        @NotNull(message = "El stock es obligatorio")
        @Min(value = 0, message = "El stock no puede ser negativo")
        Integer stock,

        @Schema(description = "Descripción corta del libro", example = "Novela de fantasía épica.")
        @NotBlank(message = "La descripción es obligatoria")
        @Size(min = 5, max = 500, message = "La descripción debe tener entre 5 y 500 caracteres")
        String description,

        @Schema(description = "Descripción extendida del libro (opcional)", example = "Historia completa, personajes, mundo, ambientación, etc.")
        String extendedDescription,

        @Schema(description = "URL de imagen del libro (opcional)", example = "https://cdn.com/libros/imagen.jpg")
        String image,

        @Schema(description = "Nombre de la categoría del libro", example = "Fantasía")
        @NotBlank(message = "La categoría es obligatoria")
        @Size(min = 3, max = 50, message = "La categoría debe tener entre 3 y 50 caracteres")
        String category
) {}
