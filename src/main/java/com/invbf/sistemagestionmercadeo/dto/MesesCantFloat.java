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

    public MesesCantFloat(String mesanio, long cantidad, long cantidad2) {
        this.mesanio = mesanio;
        this.cantidad = cantidad;
        this.cantidad2 = cantidad2;
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
}
