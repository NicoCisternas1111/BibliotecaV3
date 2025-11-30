package com.libreria.duocv3.bibliotecaapi.book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.libreria.duocv3.bibliotecaapi.book.dto.BookUpsertRequest;
import com.libreria.duocv3.bibliotecaapi.category.CategoryService;
import com.libreria.duocv3.bibliotecaapi.common.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@Tag(name = "2. Gestión de Inventario (Admin)", description = "Operaciones restringidas para administradores. Permite crear, editar y eliminar libros del sistema. Requiere autenticación con rol 'ADMIN'.")
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/api/admin/books")
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
        @Operation(summary = "Registrar un nuevo libro", description = """
                        Añade un nuevo libro a la base de datos y al catálogo público.

                        **Requisitos:**
                        - Se requiere rol **ADMIN**.
                        - La categoría se crea automáticamente si no existe.

                        **Validaciones:**
                        - `precio` y `stock` no pueden ser negativos.
                        - `titulo`, `autor` y `category` son campos obligatorios.
                        """,
                        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del libro a crear. Selecciona un ejemplo abajo.", content = @Content(mediaType = "application/json", examples = {
                                        @ExampleObject(name = "Ejemplo Novela", summary = "Libro de ficción estándar", value = "{\"title\": \"Cien años de soledad\", \"author\": \"Gabriel García Márquez\", \"category\": \"Realismo Mágico\", \"price\": 15990, \"stock\": 20, \"description\": \"Obra maestra.\", \"image\": \"https://ejemplo.com/portada.jpg\"}"),
                                        @ExampleObject(name = "Ejemplo Técnico", summary = "Libro técnico caro y con poco stock", value = "{\"title\": \"Clean Code\", \"author\": \"Robert C. Martin\", \"category\": \"Programación\", \"price\": 45000, \"stock\": 5, \"description\": \"Manual de estilo de código.\"}")
                        })))
        @ApiResponse(responseCode = "200", description = "Libro creado exitosamente.", content = @Content(schema = @Schema(implementation = BookResponse.class)))
        @ApiResponse(responseCode = "400", description = "Datos inválidos (ej. precio negativo, faltan campos).", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        @ApiResponse(responseCode = "401", description = "No autorizado (Falta token JWT).", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        @ApiResponse(responseCode = "403", description = "Prohibido (El usuario no es administrador).", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
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
        @Operation(summary = "Modificar libro existente", description = "Actualiza totalmente la información de un libro dado su ID. \n\n**Nota:** Se debe enviar el objeto JSON completo; los campos omitidos en el cuerpo de la petición podrían quedar nulos en la base de datos.")
        @ApiResponse(responseCode = "200", description = "Libro actualizado correctamente.", content = @Content(schema = @Schema(implementation = BookResponse.class)))
        @ApiResponse(responseCode = "404", description = "No se encontró ningún libro con el ID especificado.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))

        // Aquí también usamos la ruta completa por consistencia y para documentar el
        // body de entrada
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nuevos datos del libro. Todos los campos son requeridos para una actualización completa.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookUpsertRequest.class)))
        @PutMapping("/{id}")
        public ResponseEntity<?> update(
                        @Parameter(description = "ID único del libro a editar", required = true, example = "uuid-1234-5678") @PathVariable String id,
                        @Valid @RequestBody BookUpsertRequest req) {

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
        @Operation(summary = "Eliminar libro del catálogo", description = "Borra permanentemente el registro de un libro. Esta acción **no se puede deshacer**.")
        @ApiResponse(responseCode = "204", description = "Libro eliminado correctamente (No devuelve contenido).")
        @ApiResponse(responseCode = "404", description = "El libro ya no existe o el ID es incorrecto.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        @DeleteMapping("/{id}")
        public ResponseEntity<?> delete(
                        @Parameter(description = "ID del libro a eliminar", required = true) @PathVariable String id) {

                if (!bookRepo.existsById(id)) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(new ErrorResponse("Libro no encontrado", "ID: " + id));
                }

                bookRepo.deleteById(id);
                return ResponseEntity.noContent().build();
        }

        // ============================================================
        // Conversión
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