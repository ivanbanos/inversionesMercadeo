/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dto;

import java.io.Serializable;

/**
 *
 * @author ivan
 */
public class MesesCantFloat implements Serializable{

    private String mesanio;
    private float cantidad;
    private float cantidad2;
    private int mes;
    private int anio;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.mesanio != null ? this.mesanio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MesesCantFloat other = (MesesCantFloat) obj;
        if ((this.mesanio == null) ? (other.mesanio != null) : !this.mesanio.equals(other.mesanio)) {
            return false;
        }
        return true;
    }

    public MesesCantFloat() {
    }

    public MesesCantFloat(int mes, int anio, long cantidad, long cantidad2) {
        this.mesanio = getMes(mes)+"/"+anio;
        this.cantidad = cantidad;
        this.cantidad2 = cantidad2;
        this.mes = mes;
        this.anio = anio;
    }
    
    public float getCantidad2() {
        return cantidad2;
    }

    public void setCantidad2(float cantidad2) {
        this.cantidad2 = cantidad2;
    }

    public String getMesanio() {
        return mesanio;
    }

    public void setMesanio(String mesanio) {
        this.mesanio = mesanio;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    void sumar(float cant) {
        System.out.println("cant"+cant);
        cantidad+=cant;
    }
    void sumar2(float cant) {
        System.out.println("cant"+cant);
        cantidad2+=cant;
    }
    
    private String getMes(int mes){
        switch(mes){
            case 1:
                return "Ene";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Abr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Ago";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dic";
            default:
                return mes+"";
        }
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }
}
