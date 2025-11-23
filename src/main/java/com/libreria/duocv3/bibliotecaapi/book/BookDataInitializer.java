package com.libreria.duocv3.bibliotecaapi.book;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.libreria.duocv3.bibliotecaapi.category.Category;
import com.libreria.duocv3.bibliotecaapi.category.CategoryService;

@Component
public class BookDataInitializer implements CommandLineRunner {

    private final BookRepository books;
    private final CategoryService categories;

    public BookDataInitializer(BookRepository books, CategoryService categories) {
        this.books = books;
        this.categories = categories;
    }

    @Override
    public void run(String... args) {
        
        books.deleteAll();

        // ===================== CLÁSICOS Y REALISMO MÁGICO =====================

        create(
            "978-8437604947",
            "Don Quijote de la Mancha",
            "Miguel de Cervantes",
            "Clásico",
            25000,
            10,
            "La obra cumbre de la literatura española sobre un hidalgo que enloquece leyendo libros de caballerías.",
            "En un lugar de la Mancha, vive un hidalgo que decide convertirse en caballero andante. La novela explora la frontera entre realidad e idealismo.",
            "https://example.com/covers/don-quijote.jpg"
        );

        create(
            "978-8432231265",
            "1984",
            "George Orwell",
            "Distopía",
            18000,
            12,
            "Una distopía sobre un régimen totalitario que controla cada aspecto de la vida.",
            "La novela sigue a Winston Smith en un mundo dominado por el Gran Hermano, donde la vigilancia y la manipulación de la verdad son la norma.",
            "https://example.com/covers/1984.jpg"
        );

        create(
            "978-8478887983",
            "El Principito",
            "Antoine de Saint-Exupéry",
            "Clásico",
            12000,
            20,
            "Un piloto conoce a un pequeño príncipe proveniente de otro planeta.",
            "A través de su viaje por distintos asteroides, el Principito reflexiona sobre la amistad, el amor, la soledad y lo que realmente importa en la vida.",
            "https://example.com/covers/el-principito.jpg"
        );

        create(
            "978-8445074220",
            "La Comunidad del Anillo",
            "J. R. R. Tolkien",
            "Fantasía",
            19990,
            8,
            "Primera parte de la trilogía El Señor de los Anillos.",
            "Un grupo de héroes se reúne para destruir el Anillo Único y evitar que Sauron domine la Tierra Media.",
            "https://example.com/covers/comunidad-anillo.jpg"
        );

        create(
            "978-0345339683",
            "El Hobbit",
            "J. R. R. Tolkien",
            "Fantasía",
            17000,
            15,
            "La aventura de Bilbo Bolsón antes de los sucesos de El Señor de los Anillos.",
            "Bilbo se une a un grupo de enanos para recuperar un tesoro custodiado por el dragón Smaug, en un viaje lleno de criaturas y descubrimientos.",
            "https://example.com/covers/el-hobbit.jpg"
        );

        create(
            "978-8497592208",
            "Crónica de una muerte anunciada",
            "Gabriel García Márquez",
            "Realismo mágico",
            14000,
            10,
            "Una crónica sobre un asesinato anunciado por todo un pueblo.",
            "La novela reconstruye, a través de múltiples voces, los hechos que rodean el asesinato de Santiago Nasar, cuestionando la responsabilidad colectiva.",
            "https://example.com/covers/cronica-muerte-anunciada.jpg"
        );

        create(
            "978-0679720201",
            "El guardián entre el centeno",
            "J. D. Salinger",
            "Ficción",
            13500,
            9,
            "La historia de Holden Caulfield, un adolescente en crisis existencial.",
            "Holden narra sus días vagando por Nueva York mientras lidia con la hipocresía del mundo adulto y su propia sensación de vacío.",
            "https://example.com/covers/guardian-centeno.jpg"
        );

        create(
            "978-0141182636",
            "El gran Gatsby",
            "F. Scott Fitzgerald",
            "Clásico",
            16000,
            11,
            "Retrato de la alta sociedad estadounidense en los años 20.",
            "Nick Carraway observa la vida de su enigmático vecino, Jay Gatsby, y el sueño americano marcado por el lujo y la desilusión.",
            "https://example.com/covers/gran-gatsby.jpg"
        );

        create(
            "978-0307474278",
            "Cien años de soledad",
            "Gabriel García Márquez",
            "Realismo mágico",
            19000,
            14,
            "La saga de la familia Buendía en el pueblo de Macondo.",
            "Una mezcla de fantasía y realidad que recorre varias generaciones, reflexionando sobre el poder, la soledad y el destino de un pueblo latinoamericano.",
            "https://example.com/covers/cien-anos-soledad.jpg"
        );

        create(
            "978-8432226804",
            "Rayuela",
            "Julio Cortázar",
            "Ficción",
            18500,
            8,
            "Una novela abierta que se puede leer de múltiples maneras.",
            "Sigue la vida de Horacio Oliveira entre París y Buenos Aires, mezclando experimentación formal, reflexión existencial y juegos con el lector.",
            "https://example.com/covers/rayuela.jpg"
        );

        create(
            "978-8401337208",
            "La casa de los espíritus",
            "Isabel Allende",
            "Realismo mágico",
            17500,
            10,
            "La historia de varias generaciones de la familia Trueba.",
            "Combina política, amor, fantasmas y memoria histórica para retratar las transformaciones sociales de un país latinoamericano.",
            "https://example.com/covers/casa-de-los-espiritus.jpg"
        );

        create(
            "978-8408184881",
            "La sombra del viento",
            "Carlos Ruiz Zafón",
            "Misterio",
            17000,
            13,
            "Un niño encuentra un libro maldito en el Cementerio de los Libros Olvidados.",
            "Daniel Sempere se obsesiona con descubrir la historia del autor Julián Carax, destapando secretos oscuros en la Barcelona de posguerra.",
            "https://example.com/covers/sombra-viento.jpg"
        );

        create(
            "978-8466629950",
            "Los pilares de la tierra",
            "Ken Follett",
            "Histórico",
            21000,
            9,
            "Una saga épica sobre la construcción de una catedral en la Edad Media.",
            "Entre intrigas políticas, luchas por el poder y dramas personales, se levanta la catedral de Kingsbridge.",
            "https://example.com/covers/pilares-tierra.jpg"
        );

        // ===================== FANTASÍA Y JUVENIL =====================

        create(
            "978-0553593716",
            "Juego de tronos",
            "George R. R. Martin",
            "Fantasía",
            22000,
            10,
            "Primera parte de la saga Canción de hielo y fuego.",
            "Nobles familias se enfrentan por el Trono de Hierro mientras fuerzas antiguas despiertan en el norte.",
            "https://example.com/covers/juego-de-tronos.jpg"
        );

        create(
            "978-8478884456",
            "Harry Potter y la piedra filosofal",
            "J. K. Rowling",
            "Fantasía",
            16000,
            25,
            "El inicio de la historia de Harry Potter en Hogwarts.",
            "Harry descubre que es mago, hace amigos y se enfrenta por primera vez a la sombra de Lord Voldemort.",
            "https://example.com/covers/hp-piedra-filosofal.jpg"
        );

        create(
            "978-8478884951",
            "Harry Potter y la cámara secreta",
            "J. K. Rowling",
            "Fantasía",
            16000,
            22,
            "Segundo año de Harry Potter en Hogwarts.",
            "Extraños sucesos ocurren en la escuela mientras una antigua cámara es abierta y un monstruo recorre los pasillos.",
            "https://example.com/covers/hp-camara-secreta.jpg"
        );

        create(
            "978-0439023481",
            "Los juegos del hambre",
            "Suzanne Collins",
            "Distopía",
            15000,
            18,
            "Katniss Everdeen debe luchar por su vida en un cruel reality show.",
            "En un futuro autoritario, cada distrito envía tributos para combatir hasta la muerte como castigo y espectáculo televisado.",
            "https://example.com/covers/juegos-del-hambre.jpg"
        );

        create(
            "978-0439023498",
            "En llamas",
            "Suzanne Collins",
            "Distopía",
            15000,
            16,
            "Segunda parte de la trilogía de Los juegos del hambre.",
            "Katniss se convierte en símbolo de rebelión mientras el Capitolio prepara una nueva edición aún más peligrosa.",
            "https://example.com/covers/en-llamas.jpg"
        );

        create(
            "978-0439023511",
            "Sinsajo",
            "Suzanne Collins",
            "Distopía",
            15000,
            14,
            "Conclusión de la trilogía de Los juegos del hambre.",
            "La guerra abierta contra el Capitolio obliga a Katniss a decidir qué tipo de futuro está dispuesta a construir.",
            "https://example.com/covers/sinsajo.jpg"
        );

        create(
            "978-8467926698",
            "El nombre del viento",
            "Patrick Rothfuss",
            "Fantasía",
            21000,
            10,
            "La primera parte de la historia de Kvothe, un mago legendario.",
            "Kvothe narra su vida desde su infancia en una troupe itinerante hasta sus años en la Universidad, en busca de los Chandrian.",
            "https://example.com/covers/nombre-del-viento.jpg"
        );

        create(
            "978-8401352836",
            "El temor de un hombre sabio",
            "Patrick Rothfuss",
            "Fantasía",
            23000,
            8,
            "Segunda parte de la Crónica del asesino de reyes.",
            "Kvothe continúa su formación, viaja por distintos reinos y se enfrenta a nuevos peligros y leyendas.",
            "https://example.com/covers/temor-hombre-sabio.jpg"
        );

        // ===================== CIENCIA FICCIÓN =====================

        create(
            "978-0441172719",
            "Dune",
            "Frank Herbert",
            "Ciencia ficción",
            19000,
            12,
            "La historia de Paul Atreides en el desierto del planeta Arrakis.",
            "Intrigas políticas, religión y ecología se mezclan en esta épica space opera alrededor de la especia melange.",
            "https://example.com/covers/dune.jpg"
        );

        create(
            "978-0553293357",
            "Fundación",
            "Isaac Asimov",
            "Ciencia ficción",
            16000,
            10,
            "Un matemático predice la caída del Imperio Galáctico.",
            "Hari Seldon diseña un plan para reducir la edad oscura que seguirá a la caída del imperio mediante la ciencia y el conocimiento.",
            "https://example.com/covers/fundacion.jpg"
        );

        create(
            "978-0380549579",
            "Neuromante",
            "William Gibson",
            "Ciencia ficción",
            15000,
            7,
            "Un hacker fracasado es contratado para un último gran trabajo.",
            "La novela que popularizó el ciberpunk, con corporaciones omnipresentes, inteligencia artificial y realidad virtual.",
            "https://example.com/covers/neuromante.jpg"
        );

        create(
            "978-0553418026",
            "El problema de los tres cuerpos",
            "Liu Cixin",
            "Ciencia ficción",
            19000,
            9,
            "Una misteriosa señal desde el espacio cambia el destino de la humanidad.",
            "Mientras una sociedad secreta contacta a una civilización alienígena, científicos enfrentan fenómenos físicos inexplicables.",
            "https://example.com/covers/tres-cuerpos.jpg"
        );

        create(
            "978-0307887443",
            "Ready Player One",
            "Ernest Cline",
            "Ciencia ficción",
            15500,
            13,
            "Una cacería de huevos de pascua en un mundo virtual masivo.",
            "Wade Watts compite contra corporaciones y jugadores por resolver los acertijos que dejó el creador de OASIS.",
            "https://example.com/covers/ready-player-one.jpg"
        );

        create(
            "978-8497594257",
            "Fahrenheit 451",
            "Ray Bradbury",
            "Distopía",
            14000,
            11,
            "En un futuro donde los libros están prohibidos, un bombero los quema.",
            "Guy Montag comienza a cuestionar el sistema cuando descubre el poder de la lectura y la reflexión.",
            "https://example.com/covers/fahrenheit-451.jpg"
        );

        create(
            "978-8413143003",
            "El cuento de la criada",
            "Margaret Atwood",
            "Distopía",
            17000,
            8,
            "Una mujer vive como criada para dar hijos a la élite de un régimen teocrático.",
            "Offred cuenta su vida en Gilead, un estado totalitario que controla el cuerpo y la reproducción de las mujeres.",
            "https://example.com/covers/cuento-criada.jpg"
        );

        // ===================== TERROR =====================

        create(
            "978-0450411434",
            "It",
            "Stephen King",
            "Terror",
            22000,
            7,
            "Un grupo de amigos se enfrenta a un mal ancestral que toma la forma de un payaso.",
            "La novela alterna entre su infancia y adultez mientras regresan a Derry para terminar con It de una vez por todas.",
            "https://example.com/covers/it-stephen-king.jpg"
        );

        create(
            "978-0307743657",
            "El resplandor",
            "Stephen King",
            "Terror",
            18000,
            9,
            "Una familia se queda aislada en un hotel embrujado durante el invierno.",
            "Jack Torrance lucha con sus propios demonios mientras el Overlook Hotel despierta fuerzas sobrenaturales.",
            "https://example.com/covers/el-resplandor.jpg"
        );

        create(
            "978-0141439846",
            "Drácula",
            "Bram Stoker",
            "Terror",
            14000,
            10,
            "El clásico vampírico que define a Drácula como icono de terror.",
            "A través de cartas y diarios, se relata la llegada del conde Drácula a Inglaterra y el intento por detenerlo.",
            "https://example.com/covers/dracula.jpg"
        );

        create(
            "978-0143131847",
            "Frankenstein",
            "Mary Shelley",
            "Terror",
            13500,
            10,
            "Un científico crea vida y luego reniega de su criatura.",
            "La novela cuestiona los límites de la ciencia, la responsabilidad y lo que significa ser humano.",
            "https://example.com/covers/frankenstein.jpg"
        );

        // ===================== ROMANCE / FICCIÓN CONTEMPORÁNEA =====================

        create(
            "978-0141439518",
            "Orgullo y prejuicio",
            "Jane Austen",
            "Romance",
            15000,
            12,
            "La relación entre Elizabeth Bennet y el señor Darcy en la Inglaterra rural.",
            "Una crítica sutil a las normas sociales y de clase a través de diálogos ingeniosos y personajes memorables.",
            "https://example.com/covers/orgullo-prejuicio.jpg"
        );

        create(
            "978-0061120084",
            "Matar a un ruiseñor",
            "Harper Lee",
            "Clásico",
            15000,
            10,
            "Una niña observa un juicio marcado por el racismo en el sur de Estados Unidos.",
            "A través de los ojos de Scout Finch se exploran la injusticia racial, la moral y la pérdida de la inocencia.",
            "https://example.com/covers/matar-ruisenor.jpg"
        );

        create(
            "978-0307474279",
            "La ladrona de libros",
            "Markus Zusak",
            "Histórico",
            16000,
            11,
            "La Muerte narra la historia de una niña que roba libros en la Alemania nazi.",
            "Liesel Meminger encuentra consuelo en la lectura mientras su familia esconde a un judío en el sótano.",
            "https://example.com/covers/ladrona-libros.jpg"
        );

        create(
            "978-0307474270",
            "El código Da Vinci",
            "Dan Brown",
            "Thriller",
            16000,
            14,
            "Un asesinato en el Louvre desata una búsqueda de secretos religiosos.",
            "El profesor Robert Langdon sigue pistas mezclando arte, simbología y teorías sobre el Santo Grial.",
            "https://example.com/covers/codigo-da-vinci.jpg"
        );

        create(
            "978-0743493468",
            "Ángeles y demonios",
            "Dan Brown",
            "Thriller",
            15500,
            12,
            "Una conspiración en el Vaticano ligada a la antigua orden de los Illuminati.",
            "Langdon debe resolver enigmas por Roma para evitar una catástrofe durante un cónclave papal.",
            "https://example.com/covers/angeles-demonios.jpg"
        );

        create(
            "978-0552779776",
            "La chica del tren",
            "Paula Hawkins",
            "Thriller",
            15000,
            10,
            "Una mujer observa cada día desde el tren a una pareja aparentemente perfecta.",
            "Cuando la mujer desaparece, Rachel se involucra en el caso mezclando recuerdos confusos y obsesión.",
            "https://example.com/covers/chica-del-tren.jpg"
        );

        create(
            "978-0307588364",
            "Perdida",
            "Gillian Flynn",
            "Thriller",
            16000,
            9,
            "La esposa de Nick desaparece el día de su quinto aniversario.",
            "La investigación revela secretos de la pareja y la novela juega con la perspectiva y la manipulación.",
            "https://example.com/covers/perdida.jpg"
        );

        // ===================== NO FICCIÓN / DESARROLLO PERSONAL =====================

        create(
            "978-0061122415",
            "El alquimista",
            "Paulo Coelho",
            "Ficción espiritual",
            14000,
            18,
            "Un pastor andaluz sueña con encontrar un tesoro en las pirámides de Egipto.",
            "Santiago emprende un viaje físico y espiritual donde aprende a seguir los sueños y escuchar al corazón.",
            "https://example.com/covers/alquimista.jpg"
        );

        create(
            "978-0446677455",
            "Padre rico, padre pobre",
            "Robert Kiyosaki",
            "Finanzas personales",
            15000,
            20,
            "Un libro sobre educación financiera y mentalidad de riqueza.",
            "Kiyosaki compara las enseñanzas de su 'padre pobre' y su 'padre rico' para explicar activos, pasivos y libertad financiera.",
            "https://example.com/covers/padre-rico-padre-pobre.jpg"
        );

        create(
            "978-1847941831",
            "Hábitos atómicos",
            "James Clear",
            "Autoayuda",
            17000,
            17,
            "Guía práctica para construir buenos hábitos y eliminar los malos.",
            "Explica cómo pequeños cambios constantes generan grandes resultados a largo plazo.",
            "https://example.com/covers/habitos-atomicos.jpg"
        );

        create(
            "978-0062316097",
            "Sapiens",
            "Yuval Noah Harari",
            "Historia",
            19000,
            13,
            "Breve historia de la humanidad desde los cazadores-recolectores hasta la era moderna.",
            "Analiza cómo los mitos compartidos, la ciencia y el capitalismo han moldeado nuestra especie.",
            "https://example.com/covers/sapiens.jpg"
        );

        create(
            "978-8423345569",
            "El hombre en busca de sentido",
            "Viktor Frankl",
            "Psicología",
            15000,
            12,
            "Un psiquiatra relata su experiencia en campos de concentración nazis.",
            "Frankl reflexiona sobre cómo encontrar sentido incluso en el sufrimiento extremo mediante la logoterapia.",
            "https://example.com/covers/hombre-busca-sentido.jpg"
        );

        create(
            "978-0060256678",
            "El diario de Ana Frank",
            "Ana Frank",
            "Biografía",
            14000,
            15,
            "El diario de una adolescente judía escondida durante la ocupación nazi.",
            "Ana cuenta su vida en la clandestinidad, sus miedos, esperanzas y pensamientos sobre el mundo.",
            "https://example.com/covers/diario-ana-frank.jpg"
        );

        create(
            "978-1878424310",
            "Los cuatro acuerdos",
            "Miguel Ruiz",
            "Espiritualidad",
            13000,
            14,
            "Enseñanzas basadas en la sabiduría tolteca.",
            "Plantea cuatro acuerdos simples para alcanzar libertad personal y romper creencias limitantes.",
            "https://example.com/covers/cuatro-acuerdos.jpg"
        );

        create(
            "978-1577314806",
            "El poder del ahora",
            "Eckhart Tolle",
            "Espiritualidad",
            15000,
            12,
            "Un libro sobre la importancia de vivir en el momento presente.",
            "Explora cómo el ego y la mente generan sufrimiento y cómo la atención plena puede aliviarlo.",
            "https://example.com/covers/poder-del-ahora.jpg"
        );

        create(
            "978-6073132941",
            "Pequeño cerdo capitalista",
            "Sofía Macías",
            "Finanzas personales",
            14000,
            16,
            "Introducción amigable a las finanzas personales para hispanohablantes.",
            "Explica ahorro, inversión y planificación financiera con ejemplos cotidianos y lenguaje simple.",
            "https://example.com/covers/pequeno-cerdo-capitalista.jpg"
        );

        create(
            "978-8415431641",
            "La magia del orden",
            "Marie Kondo",
            "Autoayuda",
            13500,
            11,
            "Guía para ordenar el hogar y la vida a través del método KonMari.",
            "Propone conservar solo lo que 'despierta alegría' y reorganizar los espacios de forma definitiva.",
            "https://example.com/covers/magia-del-orden.jpg"
        );

        // ===================== INFANTIL / JUVENIL =====================

        create(
            "978-8494597107",
            "El monstruo de colores",
            "Anna Llenas",
            "Infantil",
            12000,
            25,
            "Un cuento ilustrado para ayudar a los niños a identificar sus emociones.",
            "El monstruo se siente confundido y debe ordenar sus sentimientos en colores distintos.",
            "https://example.com/covers/monstruo-de-colores.jpg"
        );

        create(
            "978-8420471839",
            "La historia interminable",
            "Michael Ende",
            "Fantasía",
            17000,
            10,
            "Un niño se sumerge literalmente en el libro que está leyendo.",
            "Bastián ayuda al guerrero Atreyu a salvar el reino de Fantasía de la Nada que todo lo devora.",
            "https://example.com/covers/historia-interminable.jpg"
        );

        create(
            "978-6071133001",
            "Bajo la misma estrella",
            "John Green",
            "Juvenil",
            14000,
            13,
            "Dos adolescentes con cáncer se enamoran.",
            "Hazel y Gus se conocen en un grupo de apoyo, enfrentando la enfermedad con humor, dolor y amor.",
            "https://example.com/covers/bajo-misma-estrella.jpg"
        );
    }

    private void create(
            String id,
            String title,
            String author,
            String categoryName,
            Integer price,
            Integer stock,
            String description,
            String extendedDescription,
            String image
    ) {
        if (books.existsById(id)) {
            return;
        }

        Book b = new Book();
        b.setId(id);
        b.setTitle(title);
        b.setAuthor(author);
        b.setPrice(price);
        b.setStock(stock);
        b.setDescription(description);
        b.setExtendedDescription(extendedDescription);
        b.setImage(image);

        Category category = categories.getOrCreate(categoryName);
        b.setCategory(category);

        books.save(b);
    }
}
