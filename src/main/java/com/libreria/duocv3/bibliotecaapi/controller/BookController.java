package com.libreria.duocv3.bibliotecaapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.libreria.duocv3.bibliotecaapi.domain.book.Book;
import com.libreria.duocv3.bibliotecaapi.domain.book.BookService;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    // GET /api/books?q=texto&category=categoria&page=0&size=10
    @GetMapping
    public Page<Book> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String category,
            Pageable pageable
    ) {
        return service.list(q, category, pageable);
    }

    // GET /api/books/{id}
    @GetMapping("/{id}")
    public Book get(@PathVariable String id) {
        return service.get(id);
    }
}
