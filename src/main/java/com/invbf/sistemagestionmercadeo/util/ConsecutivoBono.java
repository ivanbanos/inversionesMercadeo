/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

/**
 *
 * @author ivan
 */
public class ConsecutivoBono {
    private Integer id;
    private String consecutivo;
    private Float denominacion;
    private Integer idCliente;
    private String nombreClietne;

    public ConsecutivoBono(Integer id) {
        this.id = id;
    }

    public ConsecutivoBono(Integer id, String consecutivo, Float denominacion) {
        this.id = id;
        this.consecutivo = consecutivo;
        this.denominacion = denominacion;
    }

    public ConsecutivoBono(Integer id, String consecutivo, Float denominacion, Integer idCliente, String nombreClietne) {
        this.id = id;
        this.consecutivo = consecutivo;
        this.denominacion = denominacion;
        this.idCliente = idCliente;
        this.nombreClietne = nombreClietne;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public Float getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(Float denominacion) {
        this.denominacion = denominacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreClietne() {
        return nombreClietne;
    }

    public void setNombreClietne(String nombreClietne) {
        this.nombreClietne = nombreClietne;
    }
}