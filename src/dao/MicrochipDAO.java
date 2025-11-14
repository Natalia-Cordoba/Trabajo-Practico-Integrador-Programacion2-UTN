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
    
    @Override
    public void crear(Microchip microchip) throws Exception {
        String sql = "INSERT INTO Microchip (id, eliminado, codigo, fechaImplantacion, veterinaria, observaciones) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

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
    public void actualizar(Microchip microchip) throws Exception {
        String sql = "UPDATE Microchip SET codigo=?, fechaImplantacion=?, veterinaria=?, observaciones=? " +
                     "WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

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
    public void eliminar(int id) throws Exception {
        String sql = "UPDATE Microchip SET eliminado = TRUE WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Microchip leer(int id) throws Exception {
        String sql = "SELECT * FROM Microchip WHERE id = ? AND eliminado = FALSE";
        Microchip microchip = null;

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

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
        return microchip;
    }

    @Override
    public List<Microchip> leerTodos() throws Exception {
        String sql = "SELECT * FROM Microchip WHERE eliminado = FALSE";
        List<Microchip> listaMicrochips = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

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
