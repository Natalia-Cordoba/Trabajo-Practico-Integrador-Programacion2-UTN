/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DatabaseConnection;
import models.Mascota;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MascotaDAO implements GenericDAO<Mascota> {
    
    public void crear(Mascota mascota) throws Exception {
        String sql = "INSERT INTO Mascota (id, eliminado, nombre, especie, raza, fechaNacimiento, duenio, microchip_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, mascota.getId());
            stmt.setBoolean(2, mascota.isEliminado());
            stmt.setString(3, mascota.getNombre());
            stmt.setString(4, mascota.getEspecie());
            stmt.setString(5, mascota.getRaza());
            stmt.setDate(6, mascota.getFechaNacimiento() != null ?
                    Date.valueOf(mascota.getFechaNacimiento()) : null);
            stmt.setString(7, mascota.getDuenio());
            stmt.setObject(8, mascota.getMicrochip() != null ? mascota.getMicrochip().getId() : null);

            stmt.executeUpdate();
        }
    }

    public void actualizar(Mascota mascota) throws Exception {
        String sql = "UPDATE Mascota SET nombre=?, especie=?, raza=?, fechaNacimiento=?, duenio=?, microchip_id=? " +
                     "WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mascota.getNombre());
            stmt.setString(2, mascota.getEspecie());
            stmt.setString(3, mascota.getRaza());
            stmt.setDate(4, mascota.getFechaNacimiento() != null ?
                    Date.valueOf(mascota.getFechaNacimiento()) : null);
            stmt.setString(5, mascota.getDuenio());
            stmt.setObject(6, mascota.getMicrochip() != null ? mascota.getMicrochip().getId() : null);
            stmt.setInt(7, mascota.getId());

            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws Exception {
        String sql = "UPDATE Mascota SET eliminado = TRUE WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Mascota leer(int id) throws Exception {
        String sql = "SELECT * FROM Mascota WHERE id = ? AND eliminado = FALSE";

        Mascota mascota = null;

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                mascota = new Mascota(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("especie"),
                        rs.getString("raza"),
                        rs.getDate("fechaNacimiento") != null ?
                                rs.getDate("fechaNacimiento").toLocalDate() : null,
                        rs.getString("duenio")
                );

                int microchipId = rs.getInt("microchip_id");
                if (!rs.wasNull()) {
                    MicrochipDAO microchipDAO = new MicrochipDAO();
                    mascota.setMicrochip(microchipDAO.leer(microchipId));
                }
            }
        }
        return mascota;
    }

    public List<Mascota> leerTodos() throws Exception {
        String sql = "SELECT * FROM Mascota WHERE eliminado = FALSE";
        List<Mascota> listaMascotas = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Mascota mascota = new Mascota(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("especie"),
                        rs.getString("raza"),
                        rs.getDate("fechaNacimiento") != null ?
                                rs.getDate("fechaNacimiento").toLocalDate() : null,
                        rs.getString("duenio")
                );
                
                int microchipId = rs.getInt("microchip_id");
                if (!rs.wasNull()) {
                    MicrochipDAO microchipDAO = new MicrochipDAO();
                    mascota.setMicrochip(microchipDAO.leer(microchipId));
                }
                
                listaMascotas.add(mascota);
            }
        }
        return listaMascotas;
    }
    
}
