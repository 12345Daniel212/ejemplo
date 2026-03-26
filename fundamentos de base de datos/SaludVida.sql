CREATE DATABASE SaludVida;
USE SaludVida;

CREATE TABLE Medicos (
    id_medico INT PRIMARY KEY,
    nombre_medico VARCHAR(100) NOT NULL,
    especialidad ENUM('Cardiologia', 'Pediatria', 'Traumatología', 'Medicina General', 'Neurología') NOT NULL
);

CREATE TABLE Pacientes (
    id_paciente INT PRIMARY KEY,
    nombre_paciente VARCHAR(100) NOT NULL,
    alergias SET('Penicilina', 'Lactosa', 'Polvo', 'Ninguna', 'Mariscos', 'Nueces')
);

CREATE TABLE Medicamentos (
    id_medicamento INT PRIMARY KEY,
    nombre_medicamento VARCHAR(100) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    CONSTRAINT chk_precio_positivo CHECK (precio > 0)
);

CREATE TABLE Consultas (
    id_consulta INT PRIMARY KEY,
    fecha_consulta DATETIME NOT NULL,
    diagnostico TEXT,
    id_paciente INT,
    id_medico INT,
    FOREIGN KEY (id_paciente) REFERENCES Pacientes(id_paciente),
    FOREIGN KEY (id_medico) REFERENCES Medicos(id_medico)
);

CREATE TABLE Recetas (
    id_receta INT PRIMARY KEY,
    dosis VARCHAR(100),
    id_consulta INT,
    id_medicamento INT,
    FOREIGN KEY (id_consulta) REFERENCES Consultas(id_consulta),
    FOREIGN KEY (id_medicamento) REFERENCES Medicamentos(id_medicamento)
);

INSERT INTO Medicos VALUES (01, 'Dr. Daniel Rodriguez', 'Traumatología');
INSERT INTO Pacientes VALUES (01, 'Leonardo Robles', 'Ninguna');
INSERT INTO Medicamentos VALUES (01, 'Paracetamol', -35.99);
INSERT INTO Consultas VALUES (01, '2026-03-18 10:10:00', 'Dolor de cabeza y no puede respirar bien', 01, 01);
INSERT INTO Recetas VALUES (01, '1K cada 4 horas', 01, 01);

select * from Medicos;
select * from Pacientes;
select * from Medicamentos;
select * from Consultas;
select * from Recetas;