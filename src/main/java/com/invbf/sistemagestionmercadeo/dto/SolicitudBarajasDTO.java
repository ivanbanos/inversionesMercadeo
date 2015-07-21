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
public class SolicitudBarajasDTO implements Serializable {

    private Integer id;
    private String estado;
    private Date fechaCreacion;
    private Date fechaAceptada;
    private Date fechaRecibida;
    private Date fechaDestruccion;
    private Date entregadasnuevas;
    private Date entregadasusadas;
    private Date recibidasnuevas;
    private Date recibidasusadas;
    private String usuarioCreado;
    private String usuarioAceptador;
    private String usuarioREcibidor;
    private String usuarioDestructor;
    private List<BarajasCantidad> cantidades;

    public SolicitudBarajasDTO() {
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

    public Date getFechaDestruccion() {
        return fechaDestruccion;
    }

    public void setFechaDestruccion(Date fechaDestruccion) {
        this.fechaDestruccion = fechaDestruccion;
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

    public String getUsuarioDestructor() {
        return usuarioDestructor;
    }

    public void setUsuarioDestructor(String usuarioDestructor) {
        this.usuarioDestructor = usuarioDestructor;
    }

    public List<BarajasCantidad> getCantidades() {
        return cantidades;
    }

    public void setCantidades(List<BarajasCantidad> cantidades) {
        this.cantidades = cantidades;
    }

    public int getCantidadBarajas() {
        int cantitadBarajas = 0;
        for (BarajasCantidad cantidad : cantidades) {
            cantitadBarajas += cantidad.getCantidad();
        }
        return cantitadBarajas;
    }

    public Date getEntregadasnuevas() {
        return entregadasnuevas;
    }

    public void setEntregadasnuevas(Date entregadasnuevas) {
        this.entregadasnuevas = entregadasnuevas;
    }

    public Date getEntregadasusadas() {
        return entregadasusadas;
    }

    public void setEntregadasusadas(Date entregadasusadas) {
        this.entregadasusadas = entregadasusadas;
    }

    public Date getRecibidasnuevas() {
        return recibidasnuevas;
    }

    public void setRecibidasnuevas(Date recibidasnuevas) {
        this.recibidasnuevas = recibidasnuevas;
    }

    public Date getRecibidasusadas() {
        return recibidasusadas;
    }

    public void setRecibidasusadas(Date recibidasusadas) {
        this.recibidasusadas = recibidasusadas;
    }
    
}
