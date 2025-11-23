package com.libreria.duocv3.bibliotecaapi.category;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libreria.duocv3.bibliotecaapi.common.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(
        name = "Categories",
        description = "Administración y listado público de categorías"
)
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryRepository categoryRepo;

    public CategoryController(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    // ======================================================
    // GET — LISTAR CATEGORÍAS (PÚBLICO)
    // ======================================================
    @Operation(
            summary = "Listar categorías",
            description = "Endpoint público que retorna todas las categorías disponibles."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista obtenida correctamente",
            content = @Content(schema = @Schema(implementation = Category.class))
    )
    @GetMapping
    public ResponseEntity<List<Category>> listAll() {
        return ResponseEntity.ok(categoryRepo.findAll());
    }


    // ======================================================
    // POST — CREAR CATEGORÍA (ADMIN)
    // ======================================================
    @Operation(
            summary = "Crear categoría",
            description = "Crea una nueva categoría. Solo para administradores."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Categoría creada",
            content = @Content(schema = @Schema(implementation = Category.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "La categoría ya existe o los datos son inválidos",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "401",
            description = "Token inválido o faltante",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "403",
            description = "No autorizado (requiere rol ADMIN)",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody CreateCategoryRequest req) {

        String raw = req.name().trim();

        if (raw.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(
                            "Nombre inválido",
                            "El nombre de categoría no puede estar vacío"
                    ));
        }

        String name = raw.toLowerCase();

        if (categoryRepo.findByNameIgnoreCase(name).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(
                            "Categoría repetida",
                            "Ya existe una categoría con ese nombre"
                    ));
        }

        Category c = new Category();
        c.setName(name);

        categoryRepo.save(c);

        return ResponseEntity.ok(c);
    }
}
