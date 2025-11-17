/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DatabaseConnection;
import models.Microchip;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MicrochipDAO implements GenericDAO<Microchip> {
    
    // Métodos para CRUD con conexión interna 
    // Delegan a los métodos con la Connection para no duplicar código
    @Override
    public void insertar(Microchip microchip) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            insertar(microchip, conn);
        }
    }

    @Override
    public void actualizar(Microchip microchip) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            actualizar(microchip, conn);
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            eliminar(id, conn);
        }
    }

    @Override
    public Microchip getById(int id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return getById(id, conn);
        }
    }

    @Override
    public List<Microchip> getAll() throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return getAll(conn);
        }
    }
    
    public Microchip buscarPorCodigo(String codigo) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return buscarPorCodigo(codigo, conn);
        }
    }
    
    // Metodos para CRUD con conexión externa 
    // Ejecutan la lógica real con PreparedStatement
    @Override
    public void insertar(Microchip microchip, Connection conn) throws Exception {
        String sql = "INSERT INTO Microchip (eliminado, codigo, fechaImplantacion, veterinaria, observaciones) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            setMicrochipParameters(stmt, microchip);
            stmt.executeUpdate();
            setGeneratedId(stmt, microchip);
        }
    }

    @Override
    public void actualizar(Microchip microchip, Connection conn) throws Exception {
        String sql = "UPDATE Microchip SET codigo=?, fechaImplantacion=?, veterinaria=?, observaciones=? " +
                     "WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, microchip.getCodigo());
            stmt.setDate(2, microchip.getFechaImplantacion() != null ?
                    Date.valueOf(microchip.getFechaImplantacion()) : null);
            stmt.setString(3, microchip.getVeterinaria());
            stmt.setString(4, microchip.getObservaciones());
            stmt.setInt(5, microchip.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo actualizar el microchip con ID: " + microchip.getId());
            }
        }
    }

    @Override
    public void eliminar(int id, Connection conn) throws Exception {
        String sql = "UPDATE Microchip SET eliminado = TRUE WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se encontró el microchip con ID: " + id);
            }
        }
    }

    @Override
    public Microchip getById(int id, Connection conn) throws Exception {
        String sql = "SELECT * FROM Microchip WHERE id = ? AND eliminado = FALSE";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMicrochip(rs);
                }
            } 
        }
        return null;
    }

    @Override
    public List<Microchip> getAll(Connection conn) throws Exception {
        String sql = "SELECT * FROM Microchip WHERE eliminado = FALSE";
        List<Microchip> listaMicrochips = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                listaMicrochips.add(mapResultSetToMicrochip(rs));
            }
        } 
        return listaMicrochips;
    }
    
    public Microchip buscarPorCodigo(String codigo, Connection conn) throws Exception {
        String sql = "SELECT * FROM Microchip WHERE codigo = ? AND eliminado = FALSE";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMicrochip(rs);
                }
            }
        } 
        return null;
    }
    
    // Métodos auxiliares
    // Seteamos los parámetros comunes de Microchip en un PreparedStatement
    private void setMicrochipParameters(PreparedStatement stmt, Microchip microchip) throws SQLException {
        stmt.setBoolean(1, microchip.isEliminado());
        stmt.setString(2, microchip.getCodigo());
        stmt.setDate(3,
                microchip.getFechaImplantacion() != null
                        ? Date.valueOf(microchip.getFechaImplantacion())
                        : null
        );
        stmt.setString(4, microchip.getVeterinaria());
        stmt.setString(5, microchip.getObservaciones());
    }
    
    // Obtenemos el ID generado automáticamente y lo guardamos en el objeto
    private void setGeneratedId(PreparedStatement stmt, Microchip microchip) throws SQLException {
        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                microchip.setId(rs.getInt(1));
            } else {
                throw new SQLException("La inserción del microchip falló, no se obtuvo ID generado");
            }
        }
    }
    
    // Convertimos un ResultSet en un objeto Microchip
    // Evita repetir la creación de objetos en getById, getAll
    private Microchip mapResultSetToMicrochip(ResultSet rs) throws SQLException {
        return new Microchip(
            rs.getInt("id"),
            rs.getString("codigo"),
            rs.getString("observaciones"),
            rs.getString("veterinaria"),
            rs.getDate("fechaImplantacion") != null
                ? rs.getDate("fechaImplantacion").toLocalDate()
                : null
        );
    }
    
}
