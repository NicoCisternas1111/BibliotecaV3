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

    // ============================================================
    // BUSCAR / LISTAR LIBROS — devuelve Page<BookResponse>
    // ============================================================
    public Page<BookResponse> search(
            String q,
            String category,
            Integer priceMin,
            Integer priceMax,
            Pageable pageable
    ) {
        return repo.search(q, category, priceMin, priceMax, pageable)
                   .map(this::toResponse);  // convertimos Book → BookResponse
    }

    // ============================================================
    // OBTENER DETALLE — devuelve BookResponse
    // ============================================================
    public BookResponse get(String id) {

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID no puede ser nulo");
        }

        Book book = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Libro no encontrado"
                ));

        return toResponse(book);
    }


    // ============================================================
    // Conversión de entidad a DTO limpio
    // ============================================================
    private BookResponse toResponse(Book b) {
        return new BookResponse(
                b.getId(),
                b.getTitle(),
                b.getAuthor(),
                b.getCategory() != null ? b.getCategory().getName() : null,
                b.getPrice()
        );
    }
}
