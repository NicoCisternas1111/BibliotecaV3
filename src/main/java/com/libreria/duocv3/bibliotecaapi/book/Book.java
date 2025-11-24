package com.libreria.duocv3.bibliotecaapi.book;

import java.time.Instant;
import java.util.UUID;

import com.libreria.duocv3.bibliotecaapi.category.Category;
import com.libreria.duocv3.bibliotecaapi.common.model.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book extends Auditable {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 200)
    private String author;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stock;

    @Column(length = 255)
    private String image;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String extendedDescription;

    public Book() {
        this.id = UUID.randomUUID().toString();
    }

    public Book(
            String title,
            String author,
            Integer price,
            Integer stock,
            String description,
            String extendedDescription,
            String image,
            Category category
    ) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.author = author;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.extendedDescription = extendedDescription;
        this.image = image;
        this.category = category;

        this.setCreatedAt(Instant.now());
        this.setUpdatedAt(Instant.now());
    }

    // ----- GETTERS / SETTERS -----

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getExtendedDescription() { return extendedDescription; }
    public void setExtendedDescription(String extendedDescription) { this.extendedDescription = extendedDescription; }
}
