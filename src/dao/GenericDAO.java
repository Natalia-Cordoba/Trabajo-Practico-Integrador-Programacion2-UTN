/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.util.List;

public interface GenericDAO<T> {

    // CRUD con conexión interna
    void crear(T entidad) throws Exception;
    T leer(int id) throws Exception;
    List<T> leerTodos() throws Exception;
    void actualizar(T entidad) throws Exception;
    void eliminar(int id) throws Exception;
    
    // CRUD con conexión externa (misma transacción)
    void crear(T entidad, Connection conn) throws Exception;
    T leer(int id, Connection conn) throws Exception;
    List<T> leerTodos(Connection conn) throws Exception;
    void actualizar(T entidad, Connection conn) throws Exception;
    void eliminar(int id, Connection conn) throws Exception;
    
}
