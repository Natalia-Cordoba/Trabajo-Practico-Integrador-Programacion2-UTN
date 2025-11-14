/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.GenericDAO;
import models.Mascota;

import java.sql.Connection;
import java.util.List;

/**
 * Servicio de negocio para Mascota.
 * Coordina operaciones con Microchip de forma transaccional.
 *
 * Responsabilidades:
 * - Validar datos de la mascota (raza, dueño, fechaNacimiento)
 * - Garantizar relación 1→1 con Microchip
 * - Manejar transacciones compuestas: crear mascota + crear microchip
 */
public class MascotaService implements GenericService<Mascota> {

    private final GenericDAO<Mascota> mascotaDAO;
    private final MicrochipService microchipService;
    private final Connection connection; // conexión compartida

    public MascotaService(GenericDAO<Mascota> mascotaDAO,
                              MicrochipService microchipService,
                              Connection connection) {
        if (mascotaDAO == null || microchipService == null || connection == null) {
            throw new IllegalArgumentException("Dependencias no pueden ser null");
        }
        this.mascotaDAO = mascotaDAO;
        this.microchipService = microchipService;
        this.connection = connection;
    }

    @Override
    public void insertar(Mascota mascota) throws Exception {
        validateMascota(mascota);

        try {
            connection.setAutoCommit(false);

            // Validar regla 1→1: una mascota no puede tener más de un microchip
            if (mascota.getMicrochip() != null) {
                if (mascota.getMicrochip().getId() == 0) {
                    microchipService.insertar(mascota.getMicrochip());
                } else {
                    microchipService.actualizar(mascota.getMicrochip());
                }
            }

            mascotaDAO.insertar(mascota);
            connection.commit();

        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public void actualizar(Mascota mascota) throws Exception {
        validateMascota(mascota);
        if (mascota.getId() <= 0) {
            throw new IllegalArgumentException("El ID de la mascota debe ser mayor a 0");
        }
        mascotaDAO.actualizar(mascota);
    }

    @Override
    public void eliminar(int id) throws Exception {
        if (id <= 0) throw new IllegalArgumentException("El ID debe ser mayor a 0");
        mascotaDAO.eliminar(id);
    }

    @Override
    public Mascota getById(int id) throws Exception {
        if (id <= 0) throw new IllegalArgumentException("El ID debe ser mayor a 0");
        return mascotaDAO.getById(id);
    }

    @Override
    public List<Mascota> getAll() throws Exception {
        return mascotaDAO.getAll();
    }

    private void validateMascota(Mascota mascota) {
        if (mascota == null) throw new IllegalArgumentException("La mascota no puede ser null");
        if (mascota.getRaza() != null && mascota.getRaza().length() > 60) {
            throw new IllegalArgumentException("La raza no puede superar 60 caracteres");
        }
        if (mascota.getDuenio() == null || mascota.getDuenio().trim().isEmpty()) {
            throw new IllegalArgumentException("El dueño es obligatorio");
        }
        if (mascota.getDuenio().length() > 120) {
            throw new IllegalArgumentException("El nombre del dueño no puede superar 120 caracteres");
        }
    }
}
