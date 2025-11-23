package com.libreria.duocv3.bibliotecaapi.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public Page<Book> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer priceMin,
            @RequestParam(required = false) Integer priceMax,
            Pageable pageable
    ) {
        Pageable safe = SortValidator.validate(pageable);
        return service.search(q, category, priceMin, priceMax, safe);
    }

    @GetMapping("/{id}")
    public Book get(@PathVariable String id) {
        return service.get(id);
    }
}
