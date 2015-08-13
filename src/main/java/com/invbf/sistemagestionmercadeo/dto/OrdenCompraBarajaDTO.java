/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ivan
 */
public class OrdenCompraBarajaDTO implements Serializable {
    private Integer id;
    private String estado;
    private Date fechaCreacion;
    private Date fechaAceptada;
    private Date fechaRecibida;
    private String usuarioCreado;
    private String usuarioAceptador;
    private String usuarioREcibidor;
    private String observaciones;
    private List<BarajasCantidad> cantidades;

    public OrdenCompraBarajaDTO() {
        cantidades = new ArrayList<BarajasCantidad>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaAceptada() {
        return fechaAceptada;
    }

    public void setFechaAceptada(Date fechaAceptada) {
        this.fechaAceptada = fechaAceptada;
    }

    public Date getFechaRecibida() {
        return fechaRecibida;
    }

    public void setFechaRecibida(Date fechaRecibida) {
        this.fechaRecibida = fechaRecibida;
    }

    public List<BarajasCantidad> getCantidades() {
        return cantidades;
    }

    public void setCantidades(List<BarajasCantidad> cantidades) {
        this.cantidades = cantidades;
    }

    public String getUsuarioCreado() {
        return usuarioCreado;
    }

    public void setUsuarioCreado(String usuarioCreado) {
        this.usuarioCreado = usuarioCreado;
    }

    public String getUsuarioAceptador() {
        return usuarioAceptador;
    }

    public void setUsuarioAceptador(String usuarioAceptador) {
        this.usuarioAceptador = usuarioAceptador;
    }

    public String getUsuarioREcibidor() {
        return usuarioREcibidor;
    }

    public void setUsuarioREcibidor(String usuarioREcibidor) {
        this.usuarioREcibidor = usuarioREcibidor;
    }
    
    public int getCantidadBarajas() {
        int cantitadBarajas = 0;
        for (BarajasCantidad cantidad : cantidades) {
            cantitadBarajas += cantidad.getCantidad();
        }
        return cantitadBarajas;
    }
    public int getCantidadBarajasR() {
        int cantitadBarajas = 0;
        for (BarajasCantidad cantidad : cantidades) {
            cantitadBarajas += cantidad.getCantidadR();
        }
        return cantitadBarajas;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
}
