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
    private int cantPre;
    private int cantA;
    private Integer id;

    public LotebonoCanti() {
    }

    public LotebonoCanti(Lotebono lotesBono, int cantidad) {
        this.lotesBono = lotesBono;
        this.cantidad = cantidad;
    }

    public LotebonoCanti(Lotebono lotesBono, int cantidad, int cantPre, int cantA, Integer id) {
        this.lotesBono = lotesBono;
        this.cantidad = cantidad;
        this.cantPre = cantPre;
        this.cantA = cantA;
        this.id = id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCantPre() {
        return cantPre;
    }

    public void setCantPre(int cantPre) {
        this.cantPre = cantPre;
    }

    public int getCantA() {
        return cantA;
    }

    public void setCantA(int cantA) {
        this.cantA = cantA;
    }
    
}
