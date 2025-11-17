-- creaciondb.sql

CREATE DATABASE IF NOT EXISTS mascota_microchips;
USE mascota_microchips;

-- Desactivar temporalmente las comprobaciones de integridad referencial
SET FOREIGN_KEY_CHECKS = 0;

-- Eliminar tablas si existen
DROP TABLE IF EXISTS `mascota`;
DROP TABLE IF EXISTS `microchip`;

-- Reactivar comprobaciones de integridad referencial
SET FOREIGN_KEY_CHECKS = 1;

-- Crear tabla `microchip` primero
CREATE TABLE IF NOT EXISTS microchip (
	id BIGINT PRIMARY KEY  NOT NULL AUTO_INCREMENT,
	eliminado BOOLEAN NOT NULL,
	codigo VARCHAR(25) NOT NULL UNIQUE,
	CHECK(codigo REGEXP '^[A-Z]{4}-[0-9]{20}$'),
	fechaImplantacion DATE,
	veterinaria VARCHAR(120),
	observaciones VARCHAR(255)
);

-- Crear tabla `mascota` que referencia a `microchip`
CREATE TABLE IF NOT EXISTS mascota (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    eliminado TINYINT(1) NOT NULL,
    nombre VARCHAR(60) NOT NULL,
    especie VARCHAR(30) NOT NULL,
    raza VARCHAR(60),
    fechaNacimiento DATE,
    duenio VARCHAR(120) NOT NULL,
    microchip_id BIGINT UNIQUE,
    FOREIGN KEY (microchip_id) REFERENCES microchip(id) ON DELETE CASCADE
);

