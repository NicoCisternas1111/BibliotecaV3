package com.libreria.duocv3.bibliotecaapi.book;

import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

final class SortValidator {

    private static final Set<String> ALLOWED = Set.of(
        "id", "title", "author", "price", "createdAt", "updatedAt","category.name"
    );

    static Pageable validate(Pageable pageable) {
        if (pageable == null) return null;
        for (Sort.Order order : pageable.getSort()) {
            String prop = order.getProperty();
            if (!ALLOWED.contains(prop)) {
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Campo de orden no permitido: " + prop
                );
            }
        }
        return pageable;
    }

    private SortValidator() {}
}
