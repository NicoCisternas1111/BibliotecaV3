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
                        Recupera un listado paginado de libros del catálogo.
                        Permite filtrar por múltiples criterios simultáneamente.

                        **Lógica de Filtros:**
                        - Los filtros son acumulativos (AND).
                        - La búsqueda de texto (`q`) es insensible a mayúsculas/minúsculas.
                        - Si no se encuentran resultados, devuelve una página vacía, no un error.
                        """)
        @ApiResponse(responseCode = "200", description = "Resultados obtenidos correctamente", content = @Content(schema = @Schema(implementation = BookPageResponse.class)))
        @GetMapping
        public Page<BookResponse> list(

                        @Parameter(description = "Texto para buscar coincidencias en Título o Autor. Ejemplo: 'Harry Potter'", example = "Cien años") @RequestParam(required = false) String q,

                        @Parameter(description = "Nombre exacto de la categoría literaria.", example = "Ficción") @RequestParam(required = false) String category,

                        @Parameter(description = "Precio mínimo en pesos chilenos (CLP).", example = "5000") @RequestParam(required = false) Integer priceMin,

                        @Parameter(description = "Precio máximo en pesos chilenos (CLP).", example = "30000") @RequestParam(required = false) Integer priceMax,

                        @Parameter(description = "Opciones de paginación y ordenamiento. Por defecto: page=0, size=10.") Pageable pageable) {
                Pageable safe = SortValidator.validate(pageable);
                return service.search(q, category, priceMin, priceMax, safe);
                }

        // =====================================================================
        // DETALLE DE LIBRO
        // =====================================================================
        @Operation(summary = "Ver detalle de un libro", description = "Obtiene la información completa de un libro específico utilizando su identificador único (ID).")
        @ApiResponse(responseCode = "200", description = "Libro encontrado", content = @Content(schema = @Schema(implementation = BookResponse.class)))
        @ApiResponse(responseCode = "404", description = "Libro no encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        @GetMapping("/{id}")
        public BookResponse get(
                @Parameter(description = "ID único del libro (String)", required = true, example = "b123-456") 
                @PathVariable String id) {
        return service.get(id);
        }
}
