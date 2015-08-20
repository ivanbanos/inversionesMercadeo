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
public class RegalosCantidadDTO implements Serializable {
    private Integer id;
    private RegaloDTO regalo;
    private Integer cantidad;
    private Integer cantidadR;
    private Integer min;
    private Integer max;

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public RegalosCantidadDTO() {
    }

    public RegalosCantidadDTO(Integer id, RegaloDTO regalo, Integer cantidad, Integer cantidadR, Integer min, Integer max) {
        this.regalo = regalo;
        this.cantidad = cantidad;
        this.id = id;
        this.cantidadR = cantidadR;
        this.min = min;
        this.max = max;
    }
    public RegalosCantidadDTO(Integer id, RegaloDTO regalo) {
        this.regalo = regalo;
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

    public RegaloDTO getRegalo() {
        return regalo;
    }

    public void setRegalo(RegaloDTO regalo) {
        this.regalo = regalo;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }
    
}
