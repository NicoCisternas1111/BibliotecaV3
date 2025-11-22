package com.libreria.duocv3.bibliotecaapi.book;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.libreria.duocv3.bibliotecaapi.book.dto.BookUpsertRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/books")
public class AdminBookController {

    private final BookRepository bookRepo;

    public AdminBookController(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@Valid @RequestBody BookUpsertRequest req) {
        Book b = new Book();
        b.setId(UUID.randomUUID().toString());
        b.setTitle(req.title());
        b.setAuthor(req.author());
        b.setCategory(req.category());
        b.setPrice(req.price());
        b.setStock(req.stock());
        b.setDescription(req.description());
        b.setExtendedDescription(req.extendedDescription());
        b.setImage(req.image());
        return bookRepo.save(b);
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable String id, @Valid @RequestBody BookUpsertRequest req) {
        Book b = bookRepo.findById(id)
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND, "Libro no existe"));

        b.setTitle(req.title());
        b.setAuthor(req.author());
        b.setCategory(req.category());
        b.setPrice(req.price());
        b.setStock(req.stock());
        b.setDescription(req.description());
        b.setExtendedDescription(req.extendedDescription());
        b.setImage(req.image());
        return bookRepo.save(b);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        if (!bookRepo.existsById(id)) {
            throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND, "Libro no existe");
        }
        bookRepo.deleteById(id);
    }

}
