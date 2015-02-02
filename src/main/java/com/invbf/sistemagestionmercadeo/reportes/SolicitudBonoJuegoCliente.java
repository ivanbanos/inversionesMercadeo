/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.reportes;

/**
 *
 * @author ivan
 */
public class SolicitudBonoJuegoCliente {
    String item;
    String nombre;
    String total;
    String area;

    public SolicitudBonoJuegoCliente() {
    }

    public SolicitudBonoJuegoCliente(String item, String nombre, String total, String area) {
        this.item = item;
        this.nombre = nombre;
        this.total = total;
        this.area = area;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
    
}
