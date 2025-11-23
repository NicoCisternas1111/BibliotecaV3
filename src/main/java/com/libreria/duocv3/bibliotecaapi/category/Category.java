package com.libreria.duocv3.bibliotecaapi.category;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import com.libreria.duocv3.bibliotecaapi.common.model.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category extends Auditable {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    public Category() {
        this.id = UUID.randomUUID().toString();
        this.setCreatedAt(Instant.now());
        this.setUpdatedAt(Instant.now());
    }

    public Category(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.setCreatedAt(Instant.now());
        this.setUpdatedAt(Instant.now());
    }

    public String getId() { return id; }
    public String getName() { return name; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category c)) return false;
        return Objects.equals(id, c.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
