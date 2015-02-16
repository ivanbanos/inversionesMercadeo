/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "ControlSalidaBonos_has_LotesBonos_has_Clientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ControlsalidabonosHasLotesbonosHasClientes.findAll", query = "SELECT c FROM ControlsalidabonosHasLotesbonosHasClientes c"),
    @NamedQuery(name = "ControlsalidabonosHasLotesbonosHasClientes.findByControlSalidaBonoshasLotesBonosControlSalidaBonosid", query = "SELECT c FROM ControlsalidabonosHasLotesbonosHasClientes c WHERE c.controlsalidabonosHasLotesbonosHasClientesPK.controlSalidaBonoshasLotesBonosControlSalidaBonosid = :controlSalidaBonoshasLotesBonosControlSalidaBonosid"),
    @NamedQuery(name = "ControlsalidabonosHasLotesbonosHasClientes.findByControlSalidaBonoshasLotesBonosLotesBonosid", query = "SELECT c FROM ControlsalidabonosHasLotesbonosHasClientes c WHERE c.controlsalidabonosHasLotesbonosHasClientesPK.controlSalidaBonoshasLotesBonosLotesBonosid = :controlSalidaBonoshasLotesBonosLotesBonosid"),
    @NamedQuery(name = "ControlsalidabonosHasLotesbonosHasClientes.findByIdCliente", query = "SELECT c FROM ControlsalidabonosHasLotesbonosHasClientes c WHERE c.controlsalidabonosHasLotesbonosHasClientesPK.idCliente = :idCliente"),
    @NamedQuery(name = "ControlsalidabonosHasLotesbonosHasClientes.findByCantidad", query = "SELECT c FROM ControlsalidabonosHasLotesbonosHasClientes c WHERE c.cantidad = :cantidad")})
public class ControlsalidabonosHasLotesbonosHasClientes implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ControlsalidabonosHasLotesbonosHasClientesPK controlsalidabonosHasLotesbonosHasClientesPK;
    @Column(name = "cantidad")
    private Integer cantidad;
    @JoinColumns({
        @JoinColumn(name = "ControlSalidaBonos_has_LotesBonos_ControlSalidaBonos_id", referencedColumnName = "ControlSalidaBonos_id", insertable = false, updatable = false),
        @JoinColumn(name = "ControlSalidaBonos_has_LotesBonos_LotesBonos_id", referencedColumnName = "LotesBonos_id", insertable = false, updatable = false)})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ControlsalidabonosHasLotesbonos controlsalidabonosHasLotesbonos;
    @JoinColumn(name = "idCliente", referencedColumnName = "idCliente", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cliente cliente;

    public ControlsalidabonosHasLotesbonosHasClientes() {
    }

    public ControlsalidabonosHasLotesbonosHasClientes(ControlsalidabonosHasLotesbonosHasClientesPK controlsalidabonosHasLotesbonosHasClientesPK) {
        this.controlsalidabonosHasLotesbonosHasClientesPK = controlsalidabonosHasLotesbonosHasClientesPK;
    }

    public ControlsalidabonosHasLotesbonosHasClientes(int controlSalidaBonoshasLotesBonosControlSalidaBonosid, int controlSalidaBonoshasLotesBonosLotesBonosid, int idCliente) {
        this.controlsalidabonosHasLotesbonosHasClientesPK = new ControlsalidabonosHasLotesbonosHasClientesPK(controlSalidaBonoshasLotesBonosControlSalidaBonosid, controlSalidaBonoshasLotesBonosLotesBonosid, idCliente);
    }

    public ControlsalidabonosHasLotesbonosHasClientesPK getControlsalidabonosHasLotesbonosHasClientesPK() {
        return controlsalidabonosHasLotesbonosHasClientesPK;
    }

    public void setControlsalidabonosHasLotesbonosHasClientesPK(ControlsalidabonosHasLotesbonosHasClientesPK controlsalidabonosHasLotesbonosHasClientesPK) {
        this.controlsalidabonosHasLotesbonosHasClientesPK = controlsalidabonosHasLotesbonosHasClientesPK;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public ControlsalidabonosHasLotesbonos getControlsalidabonosHasLotesbonos() {
        return controlsalidabonosHasLotesbonos;
    }

    public void setControlsalidabonosHasLotesbonos(ControlsalidabonosHasLotesbonos controlsalidabonosHasLotesbonos) {
        this.controlsalidabonosHasLotesbonos = controlsalidabonosHasLotesbonos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (controlsalidabonosHasLotesbonosHasClientesPK != null ? controlsalidabonosHasLotesbonosHasClientesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ControlsalidabonosHasLotesbonosHasClientes)) {
            return false;
        }
        ControlsalidabonosHasLotesbonosHasClientes other = (ControlsalidabonosHasLotesbonosHasClientes) object;
        if ((this.controlsalidabonosHasLotesbonosHasClientesPK == null && other.controlsalidabonosHasLotesbonosHasClientesPK != null) || (this.controlsalidabonosHasLotesbonosHasClientesPK != null && !this.controlsalidabonosHasLotesbonosHasClientesPK.equals(other.controlsalidabonosHasLotesbonosHasClientesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.ControlsalidabonosHasLotesbonosHasClientes[ controlsalidabonosHasLotesbonosHasClientesPK=" + controlsalidabonosHasLotesbonosHasClientesPK + " ]";
    }
    
}
