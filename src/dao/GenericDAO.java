/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.util.List;

public interface GenericDAO<T> {

    // CRUD con conexión interna
    void insertar(T entidad) throws Exception;
    T getById(int id) throws Exception;
    List<T> getAll() throws Exception;
    void actualizar(T entidad) throws Exception;
    void eliminar(int id) throws Exception;
    
    // CRUD con conexión externa (misma transacción)
    void insertar(T entidad, Connection conn) throws Exception;
    T getById(int id, Connection conn) throws Exception;
    List<T> getAll(Connection conn) throws Exception;
    void actualizar(T entidad, Connection conn) throws Exception;
    void eliminar(int id, Connection conn) throws Exception;

}
