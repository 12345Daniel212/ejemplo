use LecturaAvanza;

CREATE TABLE Autores (
    id INT PRIMARY KEY,
    nombre VARCHAR(50),
    nacionalidad VARCHAR(50)
);

CREATE TABLE Libros (
    isbn VARCHAR(20) PRIMARY KEY,
    titulo VARCHAR(100),
    anio_publicacion INT,
    paginas INT,
    id_autor INT,
    -- Regla 1: Año entre 1900 y 2024 
    CONSTRAINT chk_anio CHECK (anio_publicacion >= 1900 AND anio_publicacion <= 2024),
    -- Regla 2: Páginas mayor a 10 
    CONSTRAINT chk_paginas CHECK (paginas > 10),
    -- Llave Foránea
    FOREIGN KEY (id_autor) REFERENCES Autores(id)
);

CREATE TABLE Prestamos (
    id INT PRIMARY KEY,
    fecha_prestamo DATE,
    fecha_devolucion DATE,
    isbn_libro VARCHAR(20),
    -- Regla 3: Devolución no anterior al préstamo 
    CONSTRAINT chk_fechas CHECK (fecha_devolucion >= fecha_prestamo),
    FOREIGN KEY (isbn_libro) REFERENCES Libros(isbn)
);


-- Insertar autor
INSERT INTO Autores (id, nombre, nacionalidad) 
VALUES (1, 'James Clear', 'Gringo');

-- Insertar libro válido (Año 1967, 471 páginas)
INSERT INTO Libros (isbn, titulo, anio_publicacion, paginas, id_autor) 
VALUES ('978-0307390', 'Habitos atomicos', 2018, 339, 1);

-- Insertar préstamo válido
INSERT INTO Prestamos (id, fecha_prestamo, fecha_devolucion, isbn_libro) 
VALUES (101, '2024-03-01', '2024-03-15', '978-0307390');



-- Prueba: Intenta insertar un libro con 5 páginas (debe fallar) y un préstamo con fecha de devolución anterior a la de préstamo (debe fallar).
INSERT INTO Libros (isbn, titulo, anio_publicacion, paginas, id_autor) 
VALUES ('123-456', 'Libro ejemplo', 2020, 5, 1); 

INSERT INTO Prestamos (id, fecha_prestamo, fecha_devolucion, isbn_libro) 
VALUES (102, '2024-03-10', '2024-03-01', '978-0307350');


SELECT * FROM autores;
SELECT * FROM libros;
SELECT * FROM prestamos;
