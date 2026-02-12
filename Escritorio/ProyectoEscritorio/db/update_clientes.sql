-- Crear tabla Clientes
CREATE TABLE IF NOT EXISTS `Clientes` (
    `dni` VARCHAR(255) NOT NULL,
    `nombre` VARCHAR(255) NOT NULL,
    `apellidos` VARCHAR(255) NOT NULL,
    PRIMARY KEY(`dni`)
) ENGINE=InnoDB;

-- Insertar clientes para las reservas existentes
INSERT IGNORE INTO `Clientes` (`dni`, `nombre`, `apellidos`) VALUES
('99887766Z', 'Rodrigo', 'Montoya Pastor'), -- Dueño del proyecto ;)
('11223344Y', 'Pepito', 'Perez'), 
('55667788X', 'Maria', 'Garcia'),
('22334455W', 'Juan', 'Lopez'),
('66778899V', 'Laura', 'Sanchez');

-- (Opcional) Añadir FK si se desea integridad referencial estricta ahora
-- ALTER TABLE `Reservas` ADD CONSTRAINT `Reservas_Clientes_fk` FOREIGN KEY (`dni_cliente`) REFERENCES `Clientes`(`dni`) ON DELETE CASCADE ON UPDATE CASCADE;
