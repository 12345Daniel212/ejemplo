-- ==========================================
-- 1. CREACIÓN DE LA BASE DE DATOS
-- ==========================================
CREATE DATABASE IF NOT EXISTS AlgebraRelacional;
USE AlgebraRelacional;
-- ==========================================
-- 2. CREACIÓN DE TABLAS (DDL)
-- ==========================================
CREATE TABLE Estudiantes (
    id_estudiante INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    semestre INT NOT NULL
) ENGINE = InnoDB;
CREATE TABLE Profesores (
    id_profesor INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL
) ENGINE = InnoDB;
CREATE TABLE Materias (
    id_materia INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL
) ENGINE = InnoDB;
-- Tabla Intermedia Estudiante-Materia (Muchos a Muchos)
CREATE TABLE Cursar (
    id_cursar INT PRIMARY KEY AUTO_INCREMENT,
    id_estudiante INT NOT NULL,
    id_materia INT NOT NULL,
    FOREIGN KEY (id_estudiante) REFERENCES Estudiantes(id_estudiante),
    FOREIGN KEY (id_materia) REFERENCES Materias(id_materia)
) ENGINE = InnoDB;
-- Tabla Intermedia Profesor-Materia (Muchos a Muchos)
CREATE TABLE Asignar (
    id_asignar INT PRIMARY KEY AUTO_INCREMENT,
    id_profesor INT NOT NULL,
    id_materia INT NOT NULL,
    FOREIGN KEY (id_profesor) REFERENCES Profesores(id_profesor),
    FOREIGN KEY (id_materia) REFERENCES Materias(id_materia)
) ENGINE = InnoDB;
-- ==========================================
-- 3. INSERCIÓN DE DATOS DE PRUEBA (DML)
-- ==========================================
INSERT INTO Estudiantes (nombre, semestre)
VALUES ('Ana', 1),
    ('Beto', 1),
    ('Carlos', 2),
    ('Diana', 2),
    ('Erick', 3),
    ('Fernanda', 3);
INSERT INTO Profesores (nombre)
VALUES ('Dr. House'),
    ('Dra. Cuddy'),
    ('Prof. X');
INSERT INTO Materias (nombre)
VALUES ('Matematicas'),
    ('Fisica'),
    ('Quimica');
-- 4. Asignaciones (Datos para operaciones de conjuntos)
-- Ana y Beto cursan Matematicas y Fisica (Intersección)
INSERT INTO Cursar (id_estudiante, id_materia)
VALUES (1, 1),
    (1, 2),
    (2, 1),
    (2, 2);
-- Los demás cursan otras materias
INSERT INTO Cursar (id_estudiante, id_materia)
VALUES (3, 3),
    (4, 3),
    (5, 1),
    (6, 2);
-- Dr. Cuddy y Dr. House dan clases (Unión parcial de profesores)
INSERT INTO Asignar (id_profesor, id_materia)
VALUES (2, 1),
    (2, 2),
    (2, 3),
    (1, 1);
-- Prof X da una materia que nadie cursa (Para probar Diferencia)
INSERT INTO Asignar (id_profesor, id_materia)
VALUES (3, 3);
-- ==========================================
-- 4. PRUEBAS ADICIONALES Y "FANTASMAS"
-- ==========================================
-- Ver IDs actuales
SELECT *
FROM Materias;
-- Asignaciones fantasma para probar límites de JOINS
INSERT INTO Asignar (id_profesor, id_materia)
VALUES (1, 4);
INSERT INTO Cursar (id_estudiante, id_materia)
VALUES (1, 5);
-- Ejemplo de eliminación
DELETE FROM Cursar
WHERE id_estudiante = 3
    AND id_materia = 1;
-- ==========================================
-- 5. CONSULTAS DE ÁLGEBRA RELACIONAL
-- ==========================================
-- A. Materias que tienen profesor asignado
SELECT nombre
FROM Materias
WHERE id_materia IN (
        SELECT id_materia
        FROM Asignar
    );
-- B. Materias que tienen alumnos inscritos
SELECT nombre
FROM Materias
WHERE id_materia IN (
        SELECT id_materia
        FROM Cursar
    );
-- C. Conjunto B: Materias con profesor pero SIN alumnos (Diferencia vía JOIN)
SELECT DISTINCT M.nombre AS Materia_Sin_Alumnos
FROM Asignar A
    JOIN Materias M ON A.id_materia = M.id_materia
    LEFT JOIN Cursar C ON A.id_materia = C.id_materia
WHERE C.id_cursar IS NULL;
-- D. Misma solución usando Subconsultas (Diferencia lógica)
SELECT nombre AS Materia_Sin_Alumnos_Anidado
FROM Materias
WHERE id_materia IN (
        SELECT id_materia
        FROM Asignar
    )
    AND id_materia NOT IN (
        SELECT id_materia
        FROM Cursar
    );
-- E. PRODUCTO CARTESIANO (Combinar todo con todo)
/* Concepto: El director quiere ver todas las combinaciones posibles de Profesores y Materias */
SELECT P.nombre AS Profesor,
    M.nombre AS Materia
FROM Profesores P
    CROSS JOIN Materias M;
-- F. REUNIÓN (JOIN con condición común)
/* Concepto: Ver qué materias está cursando realmente cada alumno */
SELECT E.nombre AS Estudiante,
    M.nombre AS Materia_Cursada
FROM Estudiantes E
    JOIN Cursar C ON E.id_estudiante = C.id_estudiante
    JOIN Materias M ON C.id_materia = M.id_materia;
-- --- REUNIÓN (Continuación) ---
/*
 Concepto: Combinar filas basadas en una condición común.
 Escenario: Quiero ver qué materias está cursando realmente cada alumno.
 */
SELECT E.nombre AS Estudiante,
    M.nombre AS Materia_Cursada
FROM Estudiantes E
    JOIN Cursar C ON E.id_estudiante = C.id_estudiante
    JOIN Materias M ON C.id_materia = M.id_materia
ORDER BY E.nombre;
-- Misma solución con select anidado (o producto cartesiano filtrado)
SELECT Estudiantes.nombre AS Estudiante,
    Materias.nombre AS Materia
FROM Materias,
    Cursar,
    Estudiantes
WHERE Cursar.id_estudiante = Estudiantes.id_estudiante
    AND Cursar.id_materia = Materias.id_materia;
-- --- DIVISIÓN ---
/*
 Concepto: (conjunto A dividido por conjunto B) Todos los B deben estar en A.
 Escenario: Quiero saber qué estudiantes cursan la materia con ID = 1.
 */
SELECT DISTINCT E.nombre AS 'Estudiante que cursa la materia 1'
FROM Estudiantes E
    JOIN Cursar C ON E.id_estudiante = C.id_estudiante
WHERE C.id_materia = 1;
-- Misma solución con select anidado
SELECT nombre AS 'Estudiante que cursa la materia 1'
FROM Estudiantes
WHERE id_estudiante IN (
        SELECT id_estudiante
        FROM Cursar
        WHERE id_materia = 1
    );
-- Si quiero saber qué estudiantes cursan la Materia Matemáticas (por nombre)
SELECT DISTINCT E.nombre
FROM Estudiantes E
    JOIN Cursar C ON E.id_estudiante = C.id_estudiante
    JOIN Materias M ON C.id_materia = M.id_materia
WHERE M.nombre = 'Matematicas';