/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author ivan
 */
@Embeddable
public class SolicitudregalodetallePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "solicitud")
    private int solicitud;
    @Basic(optional = false)
    @NotNull
    @Column(name = "inventario")
    private int inventario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cliente")
    private int cliente;

    public SolicitudregalodetallePK() {
    }

    public SolicitudregalodetallePK(int solicitud, int inventario, int cliente) {
        this.solicitud = solicitud;
        this.inventario = inventario;
        this.cliente = cliente;
    }

    public int getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(int solicitud) {
        this.solicitud = solicitud;
    }

    public int getInventario() {
        return inventario;
    }

    public void setInventario(int inventario) {
        this.inventario = inventario;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) solicitud;
        hash += (int) inventario;
        hash += (int) cliente;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SolicitudregalodetallePK)) {
            return false;
        }
        SolicitudregalodetallePK other = (SolicitudregalodetallePK) object;
        if (this.solicitud != other.solicitud) {
            return false;
        }
        if (this.inventario != other.inventario) {
            return false;
        }
        if (this.cliente != other.cliente) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.SolicitudregalodetallePK[ solicitud=" + solicitud + ", inventario=" + inventario + ", cliente=" + cliente + " ]";
    }
    
}
