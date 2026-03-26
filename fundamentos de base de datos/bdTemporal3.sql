-- Seleccionamos la base de datos
USE bdTemporal3;

-- 1. Crear tabla de clientes
CREATE TABLE clientes (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nombreCliente VARCHAR(30) NOT NULL UNIQUE,
    ciudad VARCHAR(30),
    telefono VARCHAR(30)
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 2. Crear tabla de productos (Integrada con las restricciones de la foto)
CREATE TABLE IF NOT EXISTS productos (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nombreProducto VARCHAR(30) NOT NULL UNIQUE,
    costo DECIMAL(8,2) NOT NULL DEFAULT 0,
    precioVenta DECIMAL(8,2) NOT NULL DEFAULT 0,
    existencia DECIMAL(8,2) NOT NULL DEFAULT 0,

    -- RESTRICCIÓN 1: El costo debe ser mayor a 0
    CONSTRAINT chk_costo_positivo CHECK (costo > 0),

    -- RESTRICCIÓN 2: La existencia no puede ser negativa
    CONSTRAINT chk_stock_no_negativo CHECK (existencia >= 0),

    -- RESTRICCIÓN 3: El precio de venta debe ser mayor al costo
    CONSTRAINT chk_precio_mayor_costo CHECK (precioVenta > costo)

) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 3. Crear tabla detalleVenta
CREATE TABLE IF NOT EXISTS detalleVenta (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    fechaVenta DATE NOT NULL,
    idClientes INT NOT NULL,
    idProductos INT NOT NULL,
    cantidadVendida DECIMAL(8,2) NOT NULL,
    costo DECIMAL(8,2) NOT NULL DEFAULT 0,
    precioVenta DECIMAL(8,2),

    CONSTRAINT FkDetalleVentaClientes 
    FOREIGN KEY (idClientes) 
    REFERENCES clientes(id) 
    ON DELETE NO ACTION,

    CONSTRAINT FkDetalleVentaProductos 
    FOREIGN KEY (idProductos) 
    REFERENCES productos(id)
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Prueba de borrado
DELETE FROM clientes WHERE id=1;