-- Crear tabla categories con VARCHAR(36)
CREATE TABLE categories (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insertar categorías base
INSERT INTO categories (id, name)
VALUES
(REPLACE(UUID(), '-', ''), 'Ficción'),
(REPLACE(UUID(), '-', ''), 'Tecnología'),
(REPLACE(UUID(), '-', ''), 'Cocina');

-- Agregar columna category_id a books
ALTER TABLE books
    ADD COLUMN category_id VARCHAR(36) NULL;

-- Agregar llave foránea
ALTER TABLE books
    ADD CONSTRAINT fk_books_category
    FOREIGN KEY (category_id) REFERENCES categories(id)
    ON DELETE SET NULL;

-- Asignar categoría por defecto a los seeds
UPDATE books
SET category_id = (SELECT id FROM categories LIMIT 1);
