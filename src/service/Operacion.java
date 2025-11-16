package service;

/**
 * Servicio de negocio para Mascota.
 * Coordina operaciones con Microchip de forma transaccional.
 *
 * Responsabilidades:
 * - Validar datos de la mascota (raza, dueño, fechaNacimiento)
 * - Garantizar relación 1→1 con Microchip
 * - Manejar transacciones compuestas: crear mascota + crear microchip
 */


public enum Operacion {
    INSERTAR, ACTUALIZAR
}
