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
    private Integer uso;
    private Integer pordestruir;
    private Integer destruidas;
    private Integer max;
    private Integer min;
    private String bodega;
    private Integer devueltas;
    private Integer cantidadActual;
    private Integer totalASumar;

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

    public BarajasCantidad(Integer id, BarajasDTO baraja, Integer cantidad, Integer cantidadR, Integer uso, Integer pordestruir, Integer destruidas, Integer max, Integer min, String bodega, Integer devueltas) {
        this.id = id;
        this.baraja = baraja;
        this.cantidad = cantidad;
        this.cantidadR = cantidadR;
        this.uso = uso;
        this.pordestruir = pordestruir;
        this.destruidas = destruidas;
        this.max = max;
        this.min = min;
        this.bodega = bodega;
        this.devueltas = devueltas;
    }

    public Integer getUso() {
        return uso;
    }

    public void setUso(Integer uso) {
        this.uso = uso;
    }

    public Integer getPordestruir() {
        return pordestruir;
    }

    public void setPordestruir(Integer pordestruir) {
        this.pordestruir = pordestruir;
    }

    public Integer getDestruidas() {
        return destruidas;
    }

    public void setDestruidas(Integer destruidas) {
        this.destruidas = destruidas;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
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

    public String getBodega() {
        return bodega;
    }

    public void setBodega(String bodega) {
        this.bodega = bodega;
    }

    public Integer getDevueltas() {
        return devueltas;
    }

    public void setDevueltas(Integer devueltas) {
        System.out.println("Setting "+devueltas);
        this.devueltas = devueltas;
        System.out.println("Setted "+this.devueltas);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final BarajasCantidad other = (BarajasCantidad) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public Integer getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(Integer cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public Integer getTotalASumar() {
        return totalASumar;
    }

    public void setTotalASumar(Integer totalASumar) {
        this.totalASumar = totalASumar;
    }
    
}
