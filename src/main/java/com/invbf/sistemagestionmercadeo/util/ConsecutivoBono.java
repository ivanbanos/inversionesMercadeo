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
    private Integer idDenominacion;
    private Integer idCliente;
    private String nombreClietne;

    public ConsecutivoBono(Integer id) {
        this.id = id;
    }

    public ConsecutivoBono(Integer id, String consecutivo, Float denominacion, Integer idDenominacion) {
        this.id = id;
        this.consecutivo = consecutivo;
        this.denominacion = denominacion;
        this.idDenominacion = idDenominacion;
    }

    public ConsecutivoBono(Integer id, String consecutivo, Float denominacion, Integer idDenominacion, Integer idCliente, String nombreClietne) {
        this.id = id;
        this.consecutivo = consecutivo;
        this.denominacion = denominacion;
        this.idDenominacion = idDenominacion;
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

    public Integer getIdDenominacion() {
        return idDenominacion;
    }

    public void setIdDenominacion(Integer idDenominacion) {
        this.idDenominacion = idDenominacion;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final ConsecutivoBono other = (ConsecutivoBono) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}