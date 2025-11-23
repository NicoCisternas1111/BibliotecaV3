INSERT INTO book (id, title, author, category, price, stock, description, extended_description, image)
VALUES 
(UUID(), 'Cien Años de Soledad', 'Gabriel García Márquez', 'Novela', 12990, 10,
 'Clásico de la literatura latinoamericana.',
 'Una obra que narra la historia de la familia Buendía a lo largo de varias generaciones.',
 'https://example.com/cien-anos.jpg'
),

(UUID(), 'El Principito', 'Antoine de Saint-Exupéry', 'Infantil', 8990, 15,
 'Un libro para niños y adultos sobre la amistad y el sentido de la vida.',
 'Incluye ilustraciones originales del autor y reflexiones profundas.',
 'https://example.com/el-principito.jpg'
),

(UUID(), '1984', 'George Orwell', 'Distopía', 10990, 20,
 'Novela distópica sobre un régimen totalitario.',
 'Explora temas de vigilancia, control social y libertad.',
 'https://example.com/1984.jpg'
);
