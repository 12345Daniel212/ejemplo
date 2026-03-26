CREATE DATABASE IF NOT EXISTS Ejercicio;
USE Ejercicio;

DROP TABLE IF EXISTS Receta, Cita, Medicamento, Medico, Paciente;

-- 1. Paciente
CREATE TABLE Paciente (
    id_paciente INT PRIMARY KEY AUTO_INCREMENT,
    nombre_paciente VARCHAR(50),
    tipo_sangre ENUM('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-') NOT NULL
);

-- 2. Medico
CREATE TABLE Medico (
    id_medico INT PRIMARY KEY,
    nombre_medico VARCHAR(50),
    especialidad ENUM('General', 'Pediatría', 'Cardiología', 'Neurología', 'Ginecología')
);

-- 3. Medicamento (Corregido el tipo de dato de una vez)
CREATE TABLE Medicamento (
    id_medicamento INT PRIMARY KEY,
    nombre_medicamento VARCHAR(50),
    costo_medicamento DECIMAL(10,2) CHECK (costo_medicamento > 0)
);

-- 4. Cita
CREATE TABLE Cita (
    id INT PRIMARY KEY,
    id_paciente INT NOT NULL,
    id_medico INT NOT NULL,
    fechaCita TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_paciente FOREIGN KEY (id_paciente) REFERENCES Paciente(id_paciente),
    CONSTRAINT fk_medico FOREIGN KEY (id_medico) REFERENCES Medico(id_medico)
);

-- 5. Receta (Corregido el CHECK y la columna cantidad)
CREATE TABLE Receta (
    id_receta INT PRIMARY KEY,
    id_cita INT NOT NULL,
    id_medicamento INT NOT NULL,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    CONSTRAINT fk_cita FOREIGN KEY (id_cita) REFERENCES Cita(id),
    CONSTRAINT fk_medicamento FOREIGN KEY (id_medicamento) REFERENCES Medicamento(id_medicamento)
);

-- INSERTS (Con datos modificados) --

INSERT INTO Paciente (nombre_paciente, tipo_sangre) VALUES
('alan mozo', 'O+'),
('Carlos Slim', 'A-'),
('Anna', 'AB+');

INSERT INTO Medico (id_medico, nombre_medico, especialidad) VALUES
(1, 'Dr. Arturo elias', 'General'),
(2, 'Dra. Alanna', 'Cardiología'),
(3, 'Dr. Gael Garcia Marques', 'Neurología');

INSERT INTO Medicamento (id_medicamento, nombre_medicamento, costo_medicamento) VALUES
(101, 'Paracetamol 1 kilo', 25.50),
(102, 'Ibuprofeno 400mg', 45.00),
(103, 'Amoxicilinas pro premium 500mg', 120.00);

INSERT INTO Cita (id, id_paciente, id_medico, fechaCita) VALUES
(1001, 1, 1, '2026-08-10 09:00:00'),
(1002, 2, 2, '2026-09-12 11:30:00'),
(1003, 3, 3, '2026-10-05 16:45:00');

INSERT INTO Receta (id_receta, id_cita, id_medicamento, cantidad) VALUES
(5001, 1001, 101, 1),
(5002, 1002, 102, 2),
(5003, 1003, 103, 1);

-- Consultas para verificar los datos insertados
SELECT * FROM Paciente;
SELECT * FROM Medico;
SELECT * FROM Medicamento;
SELECT * FROM Cita;
SELECT * FROM Receta;