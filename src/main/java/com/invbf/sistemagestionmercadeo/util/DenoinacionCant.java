/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Lotebono;


/**
 *
 * @author ivan
 */
public class DenoinacionCant {

    private Lotebono denomiancion;
    private int cantidad;

    public DenoinacionCant() {
    }

    public DenoinacionCant(Lotebono denomiancion) {
        this.denomiancion = denomiancion;
        cantidad =0;
    }

    public DenoinacionCant(Lotebono denomiancion, int cantidad) {
        this.denomiancion = denomiancion;
        this.cantidad = cantidad;
    }

    public Lotebono getDenomiancion() {
        return denomiancion;
    }

    public void setDenomiancion(Lotebono denomiancion) {
        this.denomiancion = denomiancion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.denomiancion != null ? this.denomiancion.hashCode() : 0);
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
        final DenoinacionCant other = (DenoinacionCant) obj;
        if (this.denomiancion != other.denomiancion && (this.denomiancion == null || !this.denomiancion.equals(other.denomiancion))) {
            return false;
        }
        return true;
    }

    public void sumCantidad(int cantidad) {
        this.cantidad += cantidad;
    }

}
