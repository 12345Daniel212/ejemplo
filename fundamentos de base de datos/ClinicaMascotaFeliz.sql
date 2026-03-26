
CREATE DATABASE IF NOT EXISTS ClinicaMascotaFeliz;
USE ClinicaMascotaFeliz;

-- Tabla Propietarios con campo GENERATED
CREATE TABLE Propietarios (
    ID_Propietario INT PRIMARY KEY AUTO_INCREMENT,
    Nombre_Completo VARCHAR(100) NOT NULL,
    Telefono VARCHAR(15),
    Ficha_Contacto VARCHAR(150) AS (CONCAT(Nombre_Completo, ' - Tel: ', Telefono)) VIRTUAL
);

-- Tabla Mascotas (Padre)
CREATE TABLE Mascotas (
    Codigo_Historial INT PRIMARY KEY,
    Nombre VARCHAR(50) NOT NULL,
    Edad INT,
    Peso DECIMAL(5,2),
    ID_Propietario INT,
    CONSTRAINT fk_propietario FOREIGN KEY (ID_Propietario) REFERENCES Propietarios(ID_Propietario)
);

-- Tabla Perros con ENUM
CREATE TABLE Perros (
    Codigo_Historial INT PRIMARY KEY,
    Raza VARCHAR(50),
    Nivel_Actividad ENUM('Bajo', 'Medio', 'Alto'),
    CONSTRAINT fk_perro_historial FOREIGN KEY (Codigo_Historial) REFERENCES Mascotas(Codigo_Historial)
);

-- Tabla Gatos con SET
CREATE TABLE Gatos (
    Codigo_Historial INT PRIMARY KEY,
    Tipo_Pelaje ENUM('Largo', 'Corto', 'Sin pelo'),
    Entorno SET('Interior', 'Exterior'),
    CONSTRAINT fk_gato_historial FOREIGN KEY (Codigo_Historial) REFERENCES Mascotas(Codigo_Historial)
);

-- Tabla Veterinarios con SET
CREATE TABLE Veterinarios (
    Cedula_Profesional VARCHAR(20) PRIMARY KEY,
    Especialidad VARCHAR(50),
    Dias_Consulta SET('Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado')
);

-- Tabla Consultas con JSON
CREATE TABLE Consultas (
    ID_Consulta INT PRIMARY KEY AUTO_INCREMENT,
    Fecha DATE,
    Hora TIME,
    Diagnostico TEXT,
    Codigo_Historial INT,
    Cedula_Veterinario VARCHAR(20),
    Signos_Vitales JSON,
    CONSTRAINT fk_consulta_mascota FOREIGN KEY (Codigo_Historial) REFERENCES Mascotas(Codigo_Historial),
    CONSTRAINT fk_consulta_vet FOREIGN KEY (Cedula_Veterinario) REFERENCES Veterinarios(Cedula_Profesional)
);






-- Propietarios
INSERT INTO Propietarios (Nombre_Completo, Telefono) VALUES 
('Juan Perez', '3111234567'),
('Maria Lopez', '3229876543');

-- Mascotas
INSERT INTO Mascotas VALUES 
(1001, 'Max', 3, 15.4, 1),
(2001, 'Luna', 2, 4.2, 2);

-- Perros: Usando índice numérico para ENUM (3 = 'Alto')
INSERT INTO Perros VALUES (1001, 'Beagle', 3);
INSERT INTO Perros VALUES (1002, 'Pug', 1); -- 1 = 'Bajo'

-- Gatos: Marcado en ambos entornos simultáneamente
INSERT INTO Gatos VALUES (2001, 'Corto', 'Interior,Exterior');
INSERT INTO Gatos VALUES (2002, 'Largo', 'Interior');

-- Veterinarios
INSERT INTO Veterinarios VALUES 
('VET-9901', 'Cirugía', 'Lunes,Miércoles,Viernes'),
('VET-8802', 'Dermatología', 'Martes,Jueves');

-- Consultas: Incluyendo JSON con Signos Vitales
INSERT INTO Consultas (Fecha, Hora, Diagnostico, Codigo_Historial, Cedula_Veterinario, Signos_Vitales)
VALUES 
('2024-05-20', '10:00:00', 'Revision anual', 1001, 'VET-9901', 
 '{"temperatura": 38.2, "frecuencia_cardiaca": 75, "notas_adicionales": "Muy activo"}'),
('2024-05-21', '16:30:00', 'Alergia leve', 2001, 'VET-8802', 
 '{"temperatura": 37.8, "frecuencia_cardiaca": 110, "notas_adicionales": ""}');






-- Actualizar temperatura de la consulta 1
UPDATE Consultas 
SET Signos_Vitales = JSON_SET(Signos_Vitales, '$.temperatura', 39.0) 
WHERE ID_Consulta = 1;

-- Eliminar notas adicionales si están vacías (Consulta 2)
UPDATE Consultas 
SET Signos_Vitales = JSON_REMOVE(Signos_Vitales, '$.notas_adicionales') 
WHERE ID_Consulta = 2;



-- Ver todos los Perros
SELECT M.Nombre, M.Peso, P.Raza, P.Nivel_Actividad 
FROM Mascotas M
JOIN Perros P ON M.Codigo_Historial = P.Codigo_Historial;

-- Ver todos los Gatos
SELECT M.Nombre, G.Tipo_Pelaje, G.Entorno 
FROM Mascotas M
JOIN Gatos G ON M.Codigo_Historial = G.Codigo_Historial;

SELECT 
    Fecha, 
    Diagnostico, 
    Signos_Vitales->>'$.temperatura' AS Temperatura,
    Signos_Vitales->>'$.frecuencia_cardiaca' AS Pulso,
    Signos_Vitales->>'$.notas_adicionales' AS Notas
FROM Consultas;