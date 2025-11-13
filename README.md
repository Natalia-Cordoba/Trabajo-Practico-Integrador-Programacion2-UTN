# Trabajo-Práctico-Integrador-Programación2-UTN

## Descripción 
Trabajo Final Integrador de Programación II – Sistema de gestión de mascotas con microchip. Desarrollado en Java con conexión a base de datos MySQL. Incluye scripts SQL, diagrama UML, informe técnico y guía de ejecución.

## Video Explicativo

[Ver video explicativo](poner después el link al video)

---

## Integrantes Grupo 98

- **Yohanna Díaz Monroy** – Comisión 13  
- **Belén Grosso** – Comisión 15  
- **Rocío Moyano** – Comisión 18  
- **Natalia Córdoba** – Comisión 7  

**Carrera:** Tecnicatura Universitaria en Programación – UTN  
**Materia:** Programación II  
**Año:** 2025

 ---

## Descripción del dominio elegido
El dominio elegido es Mascota y Microchip. Una mascota conoce a su microchip, pero el microchip no conoce a la mascota.
Es una asociación con una conexión débil entre las clases que son independientes; pueden existir por si mismas:
Puede existir una mascota sin microchip y un microchip que aun no fue implantado en una mascota.
La eliminación de los registros es lógica, se setea el campo eliminado en TRUE y se excluye de la consulta de microchips o mascotas activas.


## Requisitos #WIP
- Programa de manejo de bases de datos sql. Por ejemplo: mySQL Workbench, xampp y myPhp Admin. Los scripts de creacion de la DB fueron testeados en mySQL Workbench
- Terminal o Consola para ejecutar los comandos de la app

## Pasos para crear la base de datos a partir del .sql
Paso 1) Ejecutar el script creaciondb.sql
PAso 2) Ejecutar el script datos_de_prueba.sql para insertar mascotas y microchips

El script creaciondb.sql contiene los siguientes sql statements:
1) Creación de la base de datos con nombre mascota_microchips
2) Creaación de tabla Microchip primero
3) Creación de la tabla Mascota, referenciada por la tabla Microchip
Este script es idempotente, es decir, cada vez que se ejecuta se obtiene el mismo resultado. Esto es posible ya que se incluyen los siguientes comandos:

SET FOREIGN_KEY_CHECKS = 0; Para desactivar temporalmente las comprobaciones de integridad referencial

DROP TABLE IF EXISTS `mascota`; DROP TABLE IF EXISTS `microchip`; Eliminar tablas si existen

SET FOREIGN_KEY_CHECKS = 1; Reactivar comprobaciones de integridad referencial

CREATE TABLE IF NOT EXISTS Microchip ... IF NOT EXISTS solo crea la tabla si no existe en la DB.

El script datos_de_prueba.sql contiene 5 mascotas con 5 microchips asociados. Todos los registros están activos, ninguno aparece como eliminado.
Todas las mascotas tienen microchip y todos los microchips están referenciados en estas mascotas.

## Instrucciones para compilar y ejecutar
