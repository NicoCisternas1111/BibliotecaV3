-- ===========================
-- V2: SEED DATA (CORREGIDO)
-- ===========================

-- 1. Insertar Categorías
INSERT INTO categories (id, name) VALUES 
(UUID(), 'Novela'),
(UUID(), 'Infantil'),
(UUID(), 'Distopía');

-- 2. Insertar Libros usando la relación
INSERT INTO books (id, title, author, price, stock, description, extended_description, image, category_id)
VALUES 
(UUID(), 'Cien Años de Soledad', 'Gabriel García Márquez', 12990, 10, 
    'Clásico de la literatura latinoamericana.',
    'Una obra que narra la historia de la familia Buendía a lo largo de varias generaciones.',
    'https://images.cdn2.buscalibre.com/fit-in/360x360/c0/63/c0633c2d4dd430b32d5e02475461f030.jpg',
    (SELECT id FROM categories WHERE name = 'Novela' LIMIT 1)
),

(UUID(), 'El Principito', 'Antoine de Saint-Exupéry', 8990, 15, 
    'Un libro para niños y adultos sobre la amistad y el sentido de la vida.',
    'Incluye ilustraciones originales del autor y reflexiones profundas.',
    'https://images.cdn3.buscalibre.com/fit-in/360x360/dd/80/dd80e5c9a9ca4f49f887cde556408253.jpg',
    (SELECT id FROM categories WHERE name = 'Infantil' LIMIT 1)
),

(UUID(), '1984', 'George Orwell', 10990, 20, 
    'Novela distópica sobre un régimen totalitario.',
    'Explora temas de vigilancia, control social y libertad.',
    'https://images.cdn3.buscalibre.com/fit-in/360x360/3b/86/3b86d549b55251ba298cab1ec32d32eb.jpg',
    (SELECT id FROM categories WHERE name = 'Distopía' LIMIT 1)
);