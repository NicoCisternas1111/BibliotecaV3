package com.libreria.duocv3.bibliotecaapi.seed;
/*
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.libreria.duocv3.bibliotecaapi.book.Book;
import com.libreria.duocv3.bibliotecaapi.book.BookRepository;

*@Component
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
                    9900, // Integer
                    10,   // stock
                    "https://covers.openlibrary.org/b/id/8231856-L.jpg"),

                create("9789875667637", "Cien años de soledad", "Gabriel García Márquez", "Realismo mágico",
                    "La historia de la familia Buendía a lo largo de siete generaciones.",
                    "Obra maestra del realismo mágico y la literatura latinoamericana.",
                    11900,
                    8,
                    "https://covers.openlibrary.org/b/id/7984916-L.jpg"),

                create("9788491050079", "Don Quijote de la Mancha", "Miguel de Cervantes", "Clásico",
                    "Las aventuras de un hidalgo que pierde la razón y se convierte en caballero andante.",
                    "Considerada la primera novela moderna y una obra maestra de la literatura universal.",
                    10900,
                    12,
                    "https://covers.openlibrary.org/b/id/8128690-L.jpg")
            );

            repo.saveAll(books);
            System.out.println("📚 Libros de ejemplo cargados en la base de datos (" + books.size() + ").");
        }
    }

    private Book create(String id, String title, String author, String category,
                        String desc, String extDesc, Integer price, Integer stock, String img) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setCategory(category);
        book.setDescription(desc);
        book.setExtendedDescription(extDesc);
        book.setPrice(price);
        book.setStock(stock);
        book.setImage(img);
        return book;
    }
}
*/