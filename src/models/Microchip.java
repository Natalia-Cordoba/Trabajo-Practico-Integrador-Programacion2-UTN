/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.time.LocalDate;

/**
 *
 * @author natal
 */
public class Microchip extends Base {
    private String codigo;
    private java.time.LocalDate fechaImplantacion;
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
        this.codigo = codigo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getVeterinaria() {
        return veterinaria;
    }

    public void setVeterinaria(String veterinaria) {
        this.veterinaria = veterinaria;
    }

    public LocalDate getFechaImplantacion() {
        return fechaImplantacion;
    }

    public void setFechaImplantacion(LocalDate fechaImplantacion) {
        this.fechaImplantacion = fechaImplantacion;
    }

}
