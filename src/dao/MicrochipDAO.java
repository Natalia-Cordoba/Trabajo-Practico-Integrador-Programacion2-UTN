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
    
    // Métodos para conexión interna 
    // Delegan a los métodos con la Connection para no duplicar código
    @Override
    public void crear(Microchip microchip) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            crear(microchip, conn);
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
    public Microchip leer(int id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return leer(id, conn);
        }
    }

    @Override
    public List<Microchip> leerTodos() throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return leerTodos(conn);
        }
    }
    
    // Metodos para conexión externa 
    // Ejecuta la lógica real con PreparedStatement
    @Override
    public void crear(Microchip microchip, Connection conn) throws Exception {
        String sql = "INSERT INTO Microchip (id, eliminado, codigo, fechaImplantacion, veterinaria, observaciones) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, microchip.getId());
            stmt.setBoolean(2, microchip.isEliminado());
            stmt.setString(3, microchip.getCodigo());
            stmt.setDate(4, microchip.getFechaImplantacion() != null ?
                    Date.valueOf(microchip.getFechaImplantacion()) : null);
            stmt.setString(5, microchip.getVeterinaria());
            stmt.setString(6, microchip.getObservaciones());

            stmt.executeUpdate();
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

            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id, Connection conn) throws Exception {
        String sql = "UPDATE Microchip SET eliminado = TRUE WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Microchip leer(int id, Connection conn) throws Exception {
        String sql = "SELECT * FROM Microchip WHERE id = ? AND eliminado = FALSE";
        Microchip microchip = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    microchip = new Microchip(
                            rs.getInt("id"),
                            rs.getString("codigo"),
                            rs.getString("observaciones"),
                            rs.getString("veterinaria"),
                            rs.getDate("fechaImplantacion").toLocalDate()
                    );
                }
            }
        }
        return microchip;
    }

    @Override
    public List<Microchip> leerTodos(Connection conn) throws Exception {
        String sql = "SELECT * FROM Microchip WHERE eliminado = FALSE";
        List<Microchip> listaMicrochips = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Microchip microchip = new Microchip(
                        rs.getInt("id"),
                        rs.getString("codigo"),
                        rs.getString("observaciones"),
                        rs.getString("veterinaria"),
                        rs.getDate("fechaImplantacion").toLocalDate()
                );
                listaMicrochips.add(microchip);
            }
        }
        return listaMicrochips;
    }
    
}
