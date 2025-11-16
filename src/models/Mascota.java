/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.time.LocalDate;

public class Mascota extends Base {
    private String nombre;
    private String especie;
    private String raza;
    private LocalDate fechaNacimiento;
    private String duenio;
    private Microchip microchip;

    public Mascota(int id, String nombre, String especie, String raza, LocalDate fechaNacimiento, String duenio) {
        super(id, false);
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.duenio = duenio;
    }

    public Mascota() {
        super();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre;
        }
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        if (especie != null && !especie.trim().isEmpty()) {
            this.especie = especie;
        }
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        if (raza != null && !raza.trim().isEmpty()) {
            this.raza = raza;
        }
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        LocalDate fecha;
        if (fechaNacimiento != null && !fechaNacimiento.trim().isEmpty()) {
            try {
                fecha = LocalDate.parse(fechaNacimiento);
            } catch (Exception e) {
                throw new IllegalArgumentException("Formato de fecha inválido. Use AAAA-MM-DD.");
            }

            this.fechaNacimiento = fecha;
        }

    }

    public String getDuenio() {
        return duenio;
    }

    public void setDuenio(String duenio) {
        if (duenio != null && !duenio.trim().isEmpty()) {
            this.duenio = duenio;
        }
    }

    public Microchip getMicrochip() {
        return microchip;
    }

    public void setMicrochip(Microchip microchip) {
        this.microchip = microchip;
    }    
    
    @Override
    public String toString() {
        return "Mascota{" +
                "nombre='" + nombre + '\'' +
                ", especie='" + especie + '\'' +
                ", raza='" + raza + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", duenio='" + duenio + '\'' +
                ", microchip=" + microchip +
                '}';
    }
}
