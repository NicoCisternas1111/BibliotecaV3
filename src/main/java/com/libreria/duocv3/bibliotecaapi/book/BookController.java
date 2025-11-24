package com.libreria.duocv3.bibliotecaapi.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.libreria.duocv3.bibliotecaapi.common.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Books (Public)", description = "Catálogo público de libros. Búsquedas y obtención de detalle sin autenticación.")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/books")
public class BookController {

        private final BookService service;

        public BookController(BookService service) {
                this.service = service;
        }

        // =====================================================================
        // LISTAR / BUSCAR LIBROS
        // =====================================================================
        @Operation(summary = "Buscar libros", description = """
                        Endpoint público para buscar y listar libros mediante filtros:
                        - texto (q)
                        - categoría
                        - precio mínimo / máximo
                        - paginación y ordenamiento

                        Retorna un Page<BookResponse>.
                        """)
        @ApiResponse(responseCode = "200", description = "Resultados obtenidos correctamente", content = @Content(schema = @Schema(implementation = BookPageResponse.class)))
        @GetMapping
        public Page<BookResponse> list(

                        @Parameter(description = "Texto a buscar en título o autor") @RequestParam(required = false) String q,

                        @Parameter(description = "Categoría exacta del libro") @RequestParam(required = false) String category,

                        @Parameter(description = "Precio mínimo") @RequestParam(required = false) Integer priceMin,

                        @Parameter(description = "Precio máximo") @RequestParam(required = false) Integer priceMax,

                        @Parameter(description = "Información de paginación y ordenamiento") Pageable pageable) {
                Pageable safe = SortValidator.validate(pageable);
                return service.search(q, category, priceMin, priceMax, safe);
        }

        // =====================================================================
        // DETALLE DE LIBRO
        // =====================================================================
        @Operation(summary = "Obtener un libro por ID", description = "Retorna el detalle de un libro de forma pública.")
        @ApiResponse(responseCode = "200", description = "Libro encontrado", content = @Content(schema = @Schema(implementation = BookResponse.class)))
        @ApiResponse(responseCode = "404", description = "Libro no encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        @GetMapping("/{id}")
        public BookResponse get(@PathVariable String id) {
                return service.get(id);
        }
}
