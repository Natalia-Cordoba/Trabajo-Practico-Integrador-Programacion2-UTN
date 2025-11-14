/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.GenericDAO;
import dao.MicrochipDAO;
import models.Microchip;
import java.util.List;

/**
 * Implementación del servicio de negocio para la entidad Microchip.
 * Aplica validaciones de negocio y garantiza unicidad del código.
 *
 * Responsabilidades:
 * - Validar datos del microchip ANTES de persistir
 * - Garantizar unicidad del código (RN-001)
 * - Implementar baja lógica (eliminado=TRUE)
 */
public class MicrochipService implements GenericService<Microchip> {

    private final MicrochipDAO microchipDAO;

    public MicrochipService(MicrochipDAO microchipDAO) {
        if (microchipDAO == null) {
            throw new IllegalArgumentException("MicrochipDAO no puede ser null");
        }
        this.microchipDAO = microchipDAO;
    }

    @Override
    public void insertar(Microchip microchip) throws Exception {
        validateMicrochip(microchip);
        validateCodigoUnique(microchip.getCodigo(), null);
        microchipDAO.insertar(microchip);
    }

    @Override
    public void actualizar(Microchip microchip) throws Exception {
        validateMicrochip(microchip);
        if (microchip.getId() <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        }
        validateCodigoUnique(microchip.getCodigo(), microchip.getId());
        microchipDAO.actualizar(microchip);
    }

    @Override
    public void eliminar(int id) throws Exception {
        if (id <= 0) throw new IllegalArgumentException("El ID debe ser mayor a 0");
        microchipDAO.eliminar(id); // baja lógica
    }

    @Override
    public Microchip getById(int id) throws Exception {
        if (id <= 0) throw new IllegalArgumentException("El ID debe ser mayor a 0");
        return microchipDAO.getById(id);
    }

    @Override
    public List<Microchip> getAll() throws Exception {
        return microchipDAO.getAll();
    }

    private void validateMicrochip(Microchip microchip) {
        if (microchip == null) throw new IllegalArgumentException("El microchip no puede ser null");
        if (microchip.getCodigo() == null || microchip.getCodigo().trim().isEmpty()) {
            throw new IllegalArgumentException("El código del microchip es obligatorio");
        }
        if (microchip.getCodigo().length() > 25) {
            throw new IllegalArgumentException("El código no puede superar 25 caracteres");
        }
    }

    private void validateCodigoUnique(String codigo, Integer microchipId) throws Exception {
        Microchip existente = microchipDAO.buscarPorCodigo(codigo);
        if (existente != null) {
            if (microchipId == null || existente.getId() != microchipId) {
                throw new IllegalArgumentException("Ya existe un microchip con el código: " + codigo);
            }
        }
    }
}

