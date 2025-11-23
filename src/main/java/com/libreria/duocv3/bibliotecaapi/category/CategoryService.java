package com.libreria.duocv3.bibliotecaapi.category;

import java.util.UUID;

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
            throw new IllegalArgumentException("El nombre de categoría no puede estar vacío");
        }

        // Normalizar: trim + lowercase
        String normalized = rawName.trim().toLowerCase();

        return repo.findByNameIgnoreCase(normalized)
                .orElseGet(() -> {
                    Category c = new Category();
                    
                    // Capitalizamos para guardarlo bonito
                    c.setName(capitalize(normalized));

                    // Generamos ID si no existe
                    c.setId(UUID.randomUUID().toString());

                    return repo.save(c);
                });
    }

    // Capitaliza: "ficción" → "Ficción"
    private String capitalize(String text) {
        if (text.isEmpty()) return text;
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
