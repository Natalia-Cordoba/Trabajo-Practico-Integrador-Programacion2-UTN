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
import models.Microchip;

public class MascotaDAO implements GenericDAO<Mascota> {
    
    // Métodos para CRUD con conexión interna 
    // Delegan a los métodos con la Connection para no duplicar código
    @Override
    public void insertar(Mascota mascota) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            insertar(mascota, conn);
        }
    }

    @Override
    public void actualizar(Mascota mascota) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            actualizar(mascota, conn);
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            eliminar(id, conn);
        }
    }

    @Override
    public Mascota getById(int id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return getById(id, conn);
        }
    }

    @Override
    public List<Mascota> getAll() throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return getAll(conn);
        }
    }
    
    // Metodos para CRUD con conexión externa 
    // Ejecutan la lógica real con PreparedStatement
    @Override
    public void insertar(Mascota mascota, Connection conn) throws Exception {
        String sql = "INSERT INTO Mascota (eliminado, nombre, especie, raza, fechaNacimiento, duenio, microchip_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setMascotaParameters(stmt, mascota);
            stmt.executeUpdate();
            setGeneratedId(stmt, mascota);
        }
    }

    @Override
    public void actualizar(Mascota mascota, Connection conn) throws Exception {
        String sql = "UPDATE Mascota SET nombre=?, especie=?, raza=?, fechaNacimiento=?, duenio=?, microchip_id=? " +
                     "WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mascota.getNombre());
            stmt.setString(2, mascota.getEspecie());
            stmt.setString(3, mascota.getRaza());
            stmt.setDate(4, Date.valueOf(mascota.getFechaNacimiento()));
            stmt.setString(5, mascota.getDuenio());
            setMicrochipId(stmt, 6, mascota.getMicrochip());
            stmt.setInt(7, mascota.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo actualizar la mascota con ID: " + mascota.getId());
            }
        }
    }

    @Override
    public void eliminar(int id, Connection conn) throws Exception {
        String sql = "UPDATE Mascota SET eliminado = TRUE WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se encontró la mascota con ID: " + id);
            }
        }
    }

    @Override
    public Mascota getById(int id, Connection conn) throws Exception {
        String sql = "SELECT * FROM Mascota WHERE id = ? AND eliminado = FALSE";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMascota(rs, conn);
                }
            } 
        }
        return null;
    }

    @Override
    public List<Mascota> getAll(Connection conn) throws Exception {
        String sql = "SELECT * FROM Mascota WHERE eliminado = FALSE";
        List<Mascota> listaMascotas = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                listaMascotas.add(mapResultSetToMascota(rs, conn));
            }
        } 
        return listaMascotas;
    }
    
    // Métodos Auxiliares
    // Seteamos los parámetros comunes de Mascota en un PreparedStatement
    private void setMascotaParameters(PreparedStatement stmt, Mascota mascota) throws SQLException {
        stmt.setBoolean(1, mascota.isEliminado());
        stmt.setString(2, mascota.getNombre());
        stmt.setString(3, mascota.getEspecie());
        stmt.setString(4, mascota.getRaza());
        stmt.setDate(5,
                mascota.getFechaNacimiento() != null
                        ? Date.valueOf(mascota.getFechaNacimiento())
                        : null
        );
        stmt.setString(6, mascota.getDuenio());
        setMicrochipId(stmt, 7, mascota.getMicrochip());
    }
    
    // Obtenemos el ID generado automáticamente y lo guardamos en el objeto
    private void setGeneratedId(PreparedStatement stmt, Mascota mascota) throws SQLException {
        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                mascota.setId(rs.getInt(1));
            } else {
                throw new SQLException("La inserción de la mascota falló, no se obtuvo ID generado");
            }
        }
    }
    
    // Asignamos el ID del microchip solo si es válido; si no guardamos NULL
    private void setMicrochipId(PreparedStatement stmt, int parameterIndex, Microchip microchip) throws SQLException {
        if (microchip != null && microchip.getId() > 0) {
            stmt.setInt(parameterIndex, microchip.getId());
        } else {
            stmt.setNull(parameterIndex, Types.INTEGER);
        }
    }
    
    // Convertimos un ResultSet en un objeto Mascota
    // Evita repetir la creación de objetos en getById, getAll
    private Mascota mapResultSetToMascota(ResultSet rs, Connection conn) throws Exception {
        Mascota mascota = new Mascota(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getString("especie"),
            rs.getString("raza"),
            rs.getDate("fechaNacimiento") != null 
                ? rs.getDate("fechaNacimiento").toLocalDate() 
                : null,
            rs.getString("duenio")
        );

        // Si la mascota tiene un microchip asociado, lo obtenemos usando la misma conexión
        int microchipId = rs.getInt("microchip_id");
        if (!rs.wasNull()) {
            MicrochipDAO microchipDAO = new MicrochipDAO();
            mascota.setMicrochip(microchipDAO.getById(microchipId, conn));
        }

        return mascota;
    }

}
