package com.libreria.duocv3.bibliotecaapi.domain.book;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, String> {

    @Query("""
        select b from Book b
        where (:q is null or lower(b.title) like lower(concat('%',:q,'%'))
               or lower(b.author) like lower(concat('%',:q,'%')))
          and (:category is null or lower(b.category)=lower(:category))
    """)
    Page<Book> search(@Param("q") String q, @Param("category") String category, Pageable pageable);
}
