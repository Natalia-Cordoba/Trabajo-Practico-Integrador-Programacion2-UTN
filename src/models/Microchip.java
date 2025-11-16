/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.time.LocalDate;

public class Microchip extends Base {
    private String codigo;
    private LocalDate fechaImplantacion;
    private String veterinaria;
    private String observaciones;

    public Microchip(int id, String codigo, String observaciones, String veterinaria, LocalDate fechaImplantacion) {
        super(id, false);
        this.codigo = codigo;
        this.observaciones = observaciones;
        this.veterinaria = veterinaria;
        this.fechaImplantacion = fechaImplantacion;
    }

    public Microchip(){
        super();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        if (codigo != null && !codigo.trim().isEmpty()) {
            this.codigo = codigo;
        }
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        if (observaciones != null && !observaciones.trim().isEmpty()) {
            this.observaciones = observaciones;
        }
    }

    public String getVeterinaria() {
        return veterinaria;
    }

    public void setVeterinaria(String veterinaria) {
        if (veterinaria != null && !veterinaria.trim().isEmpty()) {
            this.veterinaria = veterinaria;
        }

    }

    public LocalDate getFechaImplantacion() {
        return fechaImplantacion;
    }

    public void setFechaImplantacion(String fechaImplantacion) {
        LocalDate fecha;
        if (fechaImplantacion != null && !fechaImplantacion.trim().isEmpty()) {
            try {
                fecha = LocalDate.parse(fechaImplantacion);
            } catch (Exception e) {
                throw new IllegalArgumentException("Formato de fecha inválido. Use AAAA-MM-DD.");
            }

            this.fechaImplantacion = fecha;
        }
    }

    @Override
    public String toString() {
        return "Microchip{" +
                "codigo='" + codigo + '\'' +
                ", fechaImplantacion=" + fechaImplantacion +
                ", veterinaria='" + veterinaria + '\'' +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}
