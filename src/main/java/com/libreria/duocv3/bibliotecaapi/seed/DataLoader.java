package com.libreria.duocv3.bibliotecaapi.seed;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.libreria.duocv3.bibliotecaapi.domain.book.Book;
import com.libreria.duocv3.bibliotecaapi.domain.book.BookRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final BookRepository repo;

    public DataLoader(BookRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repo.count() == 0) {
            List<Book> books = List.of(
                create("9780140449136", "La Odisea", "Homero", "Clásico",
                    "La epopeya de Odiseo tras la guerra de Troya.",
                    "Uno de los grandes poemas épicos de la literatura universal.",
                    new BigDecimal("9900"),
                    "https://covers.openlibrary.org/b/id/8231856-L.jpg"),

                create("9789875667637", "Cien años de soledad", "Gabriel García Márquez", "Realismo mágico",
                    "La historia de la familia Buendía a lo largo de siete generaciones.",
                    "Obra maestra del realismo mágico y la literatura latinoamericana.",
                    new BigDecimal("11900"),
                    "https://covers.openlibrary.org/b/id/7984916-L.jpg"),

                create("9788491050079", "Don Quijote de la Mancha", "Miguel de Cervantes", "Clásico",
                    "Las aventuras de un hidalgo que pierde la razón y se convierte en caballero andante.",
                    "Considerada la primera novela moderna y una obra maestra de la literatura universal.",
                    new BigDecimal("10900"),
                    "https://covers.openlibrary.org/b/id/8128690-L.jpg")
            );

            // 👇 Esto elimina el warning del analizador estático
            @SuppressWarnings("null")
            Iterable<Book> saved = repo.saveAll(books);

            System.out.println("📚 Libros de ejemplo cargados en la base de datos (" + ((List<Book>) saved).size() + ").");
        }
    }

    private Book create(String id, String title, String author, String category,
                        String desc, String extDesc, BigDecimal price, String img) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setCategory(category);
        book.setDescription(desc);
        book.setExtendedDescription(extDesc);
        book.setPrice(price);
        book.setImage(img);
        return book;
    }
}
