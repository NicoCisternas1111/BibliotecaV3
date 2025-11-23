package com.libreria.duocv3.bibliotecaapi.book;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libreria.duocv3.bibliotecaapi.book.dto.BookUpsertRequest;
import com.libreria.duocv3.bibliotecaapi.category.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/books")
@PreAuthorize("hasRole('ADMIN')")
public class AdminBookController {

    private final BookRepository bookRepo;
    private final CategoryService categoryService;

    public AdminBookController(BookRepository bookRepo, CategoryService categoryService) {
        this.bookRepo = bookRepo;
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Book> create(@Valid @RequestBody BookUpsertRequest req) {

        var category = categoryService.getOrCreate(req.category());

        var book = new Book(
                req.title(),
                req.author(),
                req.price(),                // Integer OK
                req.stock(),                // Integer OK
                req.description(),
                req.extendedDescription(),
                req.image(),
                category
        );

        bookRepo.save(book);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable String id, @Valid @RequestBody BookUpsertRequest req) {

        var book = bookRepo.findById(id).orElse(null);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }

        var category = categoryService.getOrCreate(req.category());

        book.setTitle(req.title());
        book.setAuthor(req.author());
        book.setPrice(req.price());                    // Integer
        book.setStock(req.stock());                    // Integer
        book.setDescription(req.description());
        book.setExtendedDescription(req.extendedDescription());
        book.setImage(req.image());
        book.setCategory(category);

        bookRepo.save(book);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (!bookRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bookRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
