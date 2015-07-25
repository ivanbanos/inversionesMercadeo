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
public class MesesCant implements Serializable{

    private String mesanio;
    private long cantidad;
    private long cantidad2;

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
        final MesesCant other = (MesesCant) obj;
        if ((this.mesanio == null) ? (other.mesanio != null) : !this.mesanio.equals(other.mesanio)) {
            return false;
        }
        return true;
    }

    public MesesCant() {
    }

    public MesesCant(String mesanio, long cantidad, long cantidad2) {
        this.mesanio = mesanio;
        this.cantidad = cantidad;
        this.cantidad2 = cantidad2;
    }
    
    public long getCantidad2() {
        return cantidad2;
    }

    public void setCantidad2(long cantidad2) {
        this.cantidad2 = cantidad2;
    }

    public String getMesanio() {
        return mesanio;
    }

    public void setMesanio(String mesanio) {
        this.mesanio = mesanio;
    }

    public long getCantidad() {
        return cantidad;
    }

    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }

    void sumar() {
        cantidad++;
    }
    void sumar2() {
        cantidad2++;
    }
}
