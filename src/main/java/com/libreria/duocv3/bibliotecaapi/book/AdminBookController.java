package com.libreria.duocv3.bibliotecaapi.book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libreria.duocv3.bibliotecaapi.book.dto.BookUpsertRequest;
import com.libreria.duocv3.bibliotecaapi.category.CategoryService;
import com.libreria.duocv3.bibliotecaapi.common.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Books (Admin)", description = "CRUD administrativo de libros. Requiere rol ADMIN.")
@RestController
@RequestMapping("/api/admin/books")
@PreAuthorize("hasRole('ADMIN')")
public class AdminBookController {

        private final BookRepository bookRepo;
        private final CategoryService categoryService;

        public AdminBookController(BookRepository bookRepo, CategoryService categoryService) {
                this.bookRepo = bookRepo;
                this.categoryService = categoryService;
        }

        // ============================================================
        // CREATE
        // ============================================================
        @Operation(summary = "Registrar nuevo libro", description = """
                        Añade un nuevo libro a la base de datos.
                        Requisitos:
                        - Se requiere rol ADMIN
                        - `precio` y `stock` no pueden ser negativos.
                        - `titulo`, `autor` y `categoria` son obligatorios.
                        """, requestBody = @RequestBody(content = @Content(mediaType = "application/json", examples = {
                        @ExampleObject(name = "Novela Clásica", value = "{\"title\": \"1984\", \"author\": \"George Orwell\", \"category\": \"Distopía\", \"price\": 12990, \"stock\": 50}"),
                        @ExampleObject(name = "Libro Técnico", value = "{\"title\": \"Java a Fondo\", \"author\": \"Pablo Augusto\", \"category\": \"Programación\", \"price\": 45000, \"stock\": 10}")
        })))
        @ApiResponse(responseCode = "200", description = "Libro creado correctamente", content = @Content(schema = @Schema(implementation = BookResponse.class)))
        @ApiResponse(responseCode = "400", description = "Datos inválidos o categoría inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        @ApiResponse(responseCode = "401", description = "Token inválido o faltante", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        @ApiResponse(responseCode = "403", description = "No autorizado — requiere rol ADMIN", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        @PostMapping
        public ResponseEntity<?> create(@Valid @RequestBody BookUpsertRequest req) {

                var category = categoryService.getOrCreate(req.category());
                var book = new Book(
                                req.title(),
                                req.author(),
                                req.price(),
                                req.stock(),
                                req.description(),
                                req.extendedDescription(),
                                req.image(),
                                category);

                bookRepo.save(book);

                return ResponseEntity.ok(toResponse(book));
        }

        // ============================================================
        // UPDATE
        // ============================================================
        @Operation(summary = "Editar libro existente", description = "Actualiza totalmente los datos de un libro dado su ID. Si se omite un campo opcional, este podría quedar nulo. Se requiere rol ADMIN")
        @ApiResponse(responseCode = "200", description = "Libro actualizado correctamente", content = @Content(schema = @Schema(implementation = BookResponse.class)))
        @ApiResponse(responseCode = "404", description = "El libro que intentas editar no existe.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        @PutMapping("/{id}")
        public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody BookUpsertRequest req) {

                var book = bookRepo.findById(id).orElse(null);
                if (book == null) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(new ErrorResponse("Libro no encontrado", "ID: " + id));
                }

                var category = categoryService.getOrCreate(req.category());

                book.setTitle(req.title());
                book.setAuthor(req.author());
                book.setPrice(req.price());
                book.setStock(req.stock());
                book.setDescription(req.description());
                book.setExtendedDescription(req.extendedDescription());
                book.setImage(req.image());
                book.setCategory(category);

                bookRepo.save(book);

                return ResponseEntity.ok(toResponse(book));
        }

        // ============================================================
        // DELETE
        // ============================================================
        @Operation(summary = "Eliminar libro", description = "Borra permanentemente un libro del catálogo. Esta acción no se puede deshacer. Se requiere rol ADMIN")
        @ApiResponse(responseCode = "204", description = "Libro eliminado correctamente")
        @ApiResponse(responseCode = "404", description = "Libro no encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        @DeleteMapping("/{id}")
        public ResponseEntity<?> delete(@PathVariable String id) {

                if (!bookRepo.existsById(id)) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(new ErrorResponse("Libro no encontrado", "ID: " + id));
                }

                bookRepo.deleteById(id);
                return ResponseEntity.noContent().build();
        }

        // ============================================================
        // Conversión entidad → DTO
        // ============================================================
        private BookResponse toResponse(Book b) {
                return new BookResponse(
                                b.getId(),
                                b.getTitle(),
                                b.getAuthor(),
                                b.getCategory() != null ? b.getCategory().getName() : null,
                                b.getPrice(),
                                b.getStock(),
                                b.getDescription(),
                                b.getExtendedDescription(),
                                b.getImage());
        }
}
