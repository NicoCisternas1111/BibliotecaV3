package com.libreria.duocv3.bibliotecaapi.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, String> {

    @Query("""
        SELECT b FROM Book b
        WHERE 
            (:q IS NULL OR 
                LOWER(b.title) LIKE LOWER(CONCAT('%', :q, '%')) OR
                LOWER(b.author) LIKE LOWER(CONCAT('%', :q, '%')) OR
                LOWER(b.category.name) LIKE LOWER(CONCAT('%', :q, '%'))
            )
        AND (:category IS NULL OR LOWER(b.category.name) = LOWER(:category))
        AND (:priceMin IS NULL OR b.price >= :priceMin)
        AND (:priceMax IS NULL OR b.price <= :priceMax)
        """)
    Page<Book> search(
            @Param("q") String q,
            @Param("category") String category,
            @Param("priceMin") Integer priceMin,
            @Param("priceMax") Integer priceMax,
            Pageable pageable
    );
}
