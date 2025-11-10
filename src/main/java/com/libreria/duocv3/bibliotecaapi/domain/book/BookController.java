package com.libreria.duocv3.bibliotecaapi.domain.book;

import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*") // habilita acceso desde el frontend
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    // 🔹 GET /api/books?q=texto&category=categoria&page=0&size=10
    @GetMapping
    public Page<Book> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String category,
            Pageable pageable
    ) {
        return service.list(q, category, pageable);
    }

    // 🔹 GET /api/books/{id}
    @GetMapping("/{id}")
    public Book get(@PathVariable String id) {
        return service.get(id);
    }
}
