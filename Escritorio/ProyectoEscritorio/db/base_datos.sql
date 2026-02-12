-- Script adaptado para MySQL

-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS `GestionHotel`;
USE `GestionHotel`;

START TRANSACTION;

-- Desactivar comprobación de claves foráneas para poder borrar tablas en cualquier orden
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `Reservas`;
DROP TABLE IF EXISTS `Nomina`;
DROP TABLE IF EXISTS `Contrasena`;
DROP TABLE IF EXISTS `Habitacion`;
DROP TABLE IF EXISTS `Usuarios`;

SET FOREIGN_KEY_CHECKS = 1;

-- Tabla: Usuarios
CREATE TABLE IF NOT EXISTS `Usuarios` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`nombre` VARCHAR(255) NOT NULL,
	`apellido_1` VARCHAR(255) NOT NULL,
	`apellido_2` VARCHAR(255) DEFAULT '------',
	`puesto` VARCHAR(255) NOT NULL,
	`dni` VARCHAR(255) NOT NULL UNIQUE,
	`telefono` INT NOT NULL UNIQUE,
	PRIMARY KEY(`id`)
) ENGINE=InnoDB;

-- Tabla: Contrasena
CREATE TABLE IF NOT EXISTS `Contrasena` (
	`id_usu` INT NOT NULL,
	`usuario` VARCHAR(255) NOT NULL UNIQUE,
	`contrasena` VARCHAR(255) NOT NULL,
	PRIMARY KEY(`id_usu`),
	CONSTRAINT `Contrasena_id_usu_fk` FOREIGN KEY(`id_usu`) REFERENCES `Usuarios`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Tabla: Habitacion
CREATE TABLE IF NOT EXISTS `Habitacion` (
	`id_habitacion` INT NOT NULL,
	`Disponible` BOOLEAN NOT NULL DEFAULT 0,
	PRIMARY KEY(`id_habitacion`)
) ENGINE=InnoDB;

-- Tabla: Nomina
CREATE TABLE IF NOT EXISTS `Nomina` (
	`id_nomina` INT NOT NULL AUTO_INCREMENT,
	`id_usu` INT NOT NULL,
	`pago` FLOAT,
	`fecha` DATETIME DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`id_nomina`),
	CONSTRAINT `Nomina_id_usu_fk` FOREIGN KEY(`id_usu`) REFERENCES `Usuarios`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Tabla: Reservas
CREATE TABLE IF NOT EXISTS `Reservas` (
	`id_reserva` INT NOT NULL AUTO_INCREMENT,
	`dni_cliente` VARCHAR(255) NOT NULL,
	`numero_habitacion` INT,
	`fecha_inicio` VARCHAR(255) NOT NULL,
	`fecha_fin` VARCHAR(255) NOT NULL,
	PRIMARY KEY(`id_reserva`),
	CONSTRAINT `Reservas_habitacion_fk` FOREIGN KEY(`numero_habitacion`) REFERENCES `Habitacion`(`id_habitacion`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Insertar datos de prueba
INSERT INTO `Usuarios` (`nombre`, `apellido_1`, `apellido_2`, `puesto`, `dni`, `telefono`) VALUES
('Juan', 'Perez', 'Garcia', 'Recepcionista', '12345678A', 600111222),
('Ana', 'Gomez', 'Lopez', 'Limpieza', '87654321B', 600333444),
('Luis', 'Martinez', 'Santos', 'Mantenimiento', '11223344C', 600555666),
('Maria', 'Lopez', 'Diaz', 'Gerente', '44332211D', 600777888),
('Carlos', 'Sanchez', 'Ruiz', 'Cocinero', '55667788E', 600999000);

INSERT INTO `Contrasena` (`id_usu`, `usuario`, `contrasena`) VALUES
(1, 'jperez', 'pass123'),
(2, 'agomez', 'pass456'),
(3, 'lmartinez', 'pass789'),
(4, 'mlopez', 'admin1'),
(5, 'csanchez', 'cocina1');

INSERT INTO `Habitacion` (`id_habitacion`, `Disponible`) VALUES
(101, 1),
(102, 0),
(103, 1),
(201, 1),
(202, 0);

INSERT INTO `Nomina` (`id_usu`, `pago`) VALUES
(1, 1200.50),
(2, 1100.00),
(3, 1300.75),
(4, 2500.00),
(5, 1400.25);

INSERT INTO `Reservas` (`dni_cliente`, `numero_habitacion`, `fecha_inicio`, `fecha_fin`) VALUES
('99887766Z', 101, '2023-10-01', '2023-10-05'),
('11223344Y', 102, '2023-11-10', '2023-11-15'),
('55667788X', 103, '2023-12-01', '2023-12-05'),
('22334455W', 201, '2024-01-10', '2024-01-15'),
('66778899V', 202, '2024-02-01', '2024-02-05');

COMMIT;
