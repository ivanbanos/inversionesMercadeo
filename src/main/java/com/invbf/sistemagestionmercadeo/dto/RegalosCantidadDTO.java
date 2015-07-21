/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dto;

/**
 *
 * @author ivan
 */
public class RegalosCantidadDTO {
    private Integer id;
    private RegaloDTO regalo;
    private Integer cantidad;
    private Integer cantidadR;

    public RegaloDTO getBaraja() {
        return regalo;
    }

    public void setBaraja(RegaloDTO regalo) {
        this.regalo = regalo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public RegalosCantidadDTO() {
    }

    public RegalosCantidadDTO(Integer id, RegaloDTO regalo, Integer cantidad, Integer cantidadR) {
        this.regalo = regalo;
        this.cantidad = cantidad;
        this.id = id;
        this.cantidadR = cantidadR;
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
}
