package com.libreria.duocv3.bibliotecaapi.category;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    private final CategoryRepository repo;

    public CategoryService(CategoryRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Category getOrCreate(String rawName) {
        if (rawName == null || rawName.isBlank()) {
            throw new IllegalArgumentException("El nombre de categoría no puede ser vacío");
        }

        String normalized = rawName.trim();

        return repo.findByNameIgnoreCase(normalized)
                .orElseGet(() -> {
                    Category c = new Category();
                    c.setName(normalized);
                    return repo.save(c);
                });
    }
}
