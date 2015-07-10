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
public class BarajasCantidad implements Serializable{
    private BarajasDTO baraja;
    private Integer cantidad;

    public BarajasDTO getBaraja() {
        return baraja;
    }

    public void setBaraja(BarajasDTO baraja) {
        this.baraja = baraja;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BarajasCantidad() {
    }

    public BarajasCantidad(BarajasDTO baraja, Integer cantidad) {
        this.baraja = baraja;
        this.cantidad = cantidad;
    }
}
