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
public class RegaloCantidad implements Serializable{
    private RegaloDTO regalo;
    private int cantidad;

    public RegaloCantidad() {
    }

    public RegaloCantidad(RegaloDTO regalo, int cantidad) {
        this.regalo = regalo;
        this.cantidad = cantidad;
    }

    public RegaloDTO getRegalo() {
        return regalo;
    }

    public void setRegalo(RegaloDTO regalo) {
        this.regalo = regalo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.regalo != null ? this.regalo.hashCode() : 0);
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
        final RegaloCantidad other = (RegaloCantidad) obj;
        if (this.regalo != other.regalo && (this.regalo == null || !this.regalo.equals(other.regalo))) {
            return false;
        }
        return true;
    }

    public void sumarUno() {
        cantidad++;
    }
    
}
