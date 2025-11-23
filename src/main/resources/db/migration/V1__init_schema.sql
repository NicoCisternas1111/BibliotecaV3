-- ===========================
-- V1: INIT SCHEMA
-- Creación inicial de tablas
-- ===========================

CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE books (
    id VARCHAR(36) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    description VARCHAR(2048),
    extended_description VARCHAR(4096),
    image VARCHAR(255),
    stock INT NOT NULL DEFAULT 0,
    
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255)
);

-- ===========================
-- Índices recomendados
-- ===========================

CREATE INDEX idx_book_title ON books (title);
CREATE INDEX idx_book_category ON books (category);
CREATE INDEX idx_book_price ON books (price);
