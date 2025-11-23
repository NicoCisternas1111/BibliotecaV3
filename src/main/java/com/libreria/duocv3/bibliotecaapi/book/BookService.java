package com.libreria.duocv3.bibliotecaapi.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BookService {

    private final BookRepository repo;

    public BookService(BookRepository repo) {
        this.repo = repo;
    }

    public Page<Book> search(String q, String category, Integer priceMin, Integer priceMax, Pageable pageable) {
        return repo.search(q, category, priceMin, priceMax, pageable);
    }

    public Book get(String id) {
        if (id == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID no puede ser nulo");

        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Libro no encontrado"
                ));
    }
}
