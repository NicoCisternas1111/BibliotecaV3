package com.libreria.duocv3.bibliotecaapi.book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Tag(name = "book-controller", description = "Catálogo público de libros")
@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @Operation(operationId = "publicListBooks", summary = "Lista libros (público)")
    @GetMapping
    public Page<Book> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer priceMin,
            @RequestParam(required = false) Integer priceMax,
            Pageable pageable
    ) {
        return service.search(q, category, priceMin, priceMax, pageable);
    }

    @Operation(operationId = "publicGetBookById", summary = "Obtiene libro por id (público)")
    @GetMapping("/{id}")
    public Book get(@PathVariable String id) {
        return service.get(id);
    }
}
