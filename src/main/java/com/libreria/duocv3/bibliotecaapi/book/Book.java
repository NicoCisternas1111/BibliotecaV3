package com.libreria.duocv3.bibliotecaapi.book;

import org.hibernate.annotations.GenericGenerator;

import com.libreria.duocv3.bibliotecaapi.common.model.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Book extends Auditable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(columnDefinition = "CHAR(36)")
    private String id;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String category;

    @NotNull
    private Integer price;

    @Column(length = 2048)
    private String description;

    @Column(length = 4096)
    private String extendedDescription;

    private String image;

    @Column(nullable = false/*, columnDefinition = "int default 0"*/)
    private Integer stock = 0;

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getExtendedDescription() { return extendedDescription; }
    public void setExtendedDescription(String extendedDescription) { this.extendedDescription = extendedDescription; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}