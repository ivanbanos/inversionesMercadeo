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
    private Integer id;
    private BarajasDTO baraja;
    private Integer cantidad;
    private Integer cantidadR;

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

    public BarajasCantidad(Integer id, BarajasDTO baraja, Integer cantidad, Integer cantidadR) {
        this.baraja = baraja;
        this.cantidad = cantidad;
        this.id = id;
        this.cantidadR = cantidadR;
    }
    public BarajasCantidad(Integer id, BarajasDTO baraja) {
        this.baraja = baraja;
        this.cantidad = 0;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidadR() {
        return cantidadR;
    }

    public void setCantidadR(Integer cantidadR) {
        this.cantidadR = cantidadR;
    }
    
}
