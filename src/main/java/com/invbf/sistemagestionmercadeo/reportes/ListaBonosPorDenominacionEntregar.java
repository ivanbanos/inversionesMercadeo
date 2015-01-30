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
public class ListaBonosPorDenominacionEntregar {
    private String cantidad;
    private String denominacion;
    private String del;
    private String al;
    private String saladejuego;

    public ListaBonosPorDenominacionEntregar(String cantidad, String denominacion, String del, String al, String saladejuego) {
        this.cantidad = cantidad;
        this.denominacion = denominacion;
        this.del = del;
        this.al = al;
        this.saladejuego = saladejuego;
    }

    public ListaBonosPorDenominacionEntregar() {
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getDel() {
        return del;
    }

    public void setDel(String del) {
        this.del = del;
    }

    public String getAl() {
        return al;
    }

    public void setAl(String al) {
        this.al = al;
    }

    public String getSaladejuego() {
        return saladejuego;
    }

    public void setSaladejuego(String saladejuego) {
        this.saladejuego = saladejuego;
    }
}
