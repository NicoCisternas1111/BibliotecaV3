package com.libreria.duocv3.bibliotecaapi.domain.book;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Book {

    @Id
    private String id; // usamos el ISBN como identificador

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String category;

    @NotNull
    private BigDecimal price;

    @Column(length = 2048)
    private String description;

    @Column(length = 4096)
    private String extendedDescription;

    private String image;

    // ---- Getters y Setters ----
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getExtendedDescription() { return extendedDescription; }
    public void setExtendedDescription(String extendedDescription) { this.extendedDescription = extendedDescription; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}