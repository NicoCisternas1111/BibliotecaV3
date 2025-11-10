package com.libreria.duocv3.bibliotecaapi.domain.book;

import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BookService {

    private final BookRepository repo;

    public BookService(BookRepository repo) {
        this.repo = repo;
    }

    // Listar libros con filtros opcionales (búsqueda y categoría)
    public Page<Book> list(String q, String category, Pageable pageable) {
        return repo.search(q, category, pageable);
    }

    // Obtener un libro específico por ID (ISBN)
    public Book get(String id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro no encontrado"));
    }
}
