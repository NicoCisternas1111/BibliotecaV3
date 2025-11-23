package com.libreria.duocv3.bibliotecaapi.category;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryRepository categoryRepo;

    public CategoryController(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @GetMapping
    public ResponseEntity<List<Category>> listAll() {
        return ResponseEntity.ok(categoryRepo.findAll());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> create(@RequestBody CreateCategoryRequest req) {

        var raw = req.name().trim();
        var name = raw.toLowerCase();

        if (categoryRepo.findByNameIgnoreCase(name).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        var c = new Category();
        c.setName(name);
        categoryRepo.save(c);

        return ResponseEntity.ok(c);
    }
}
