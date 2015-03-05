/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Lotebono;
import java.io.Serializable;

/**
 *
 * @author ivan
 */
public class LotebonoCanti implements Serializable{
    private Lotebono lotesBono;
    private int cantidad;

    public LotebonoCanti() {
    }

    public LotebonoCanti(Lotebono lotesBono, int cantidad) {
        this.lotesBono = lotesBono;
        this.cantidad = cantidad;
    }

    public Lotebono getLotesBono() {
        return lotesBono;
    }

    public void setLotesBono(Lotebono lotesBono) {
        this.lotesBono = lotesBono;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
}
