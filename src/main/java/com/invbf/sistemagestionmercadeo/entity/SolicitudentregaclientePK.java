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
public class SolicitudentregaclientePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "SolicitudEntregaid")
    private int solicitudEntregaid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Clientesid")
    private int clientesid;

    public SolicitudentregaclientePK() {
    }

    public SolicitudentregaclientePK(int solicitudEntregaid, int clientesid) {
        this.solicitudEntregaid = solicitudEntregaid;
        this.clientesid = clientesid;
    }

    public int getSolicitudEntregaid() {
        return solicitudEntregaid;
    }

    public void setSolicitudEntregaid(int solicitudEntregaid) {
        this.solicitudEntregaid = solicitudEntregaid;
    }

    public int getClientesid() {
        return clientesid;
    }

    public void setClientesid(int clientesid) {
        this.clientesid = clientesid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) solicitudEntregaid;
        hash += (int) clientesid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SolicitudentregaclientePK)) {
            return false;
        }
        SolicitudentregaclientePK other = (SolicitudentregaclientePK) object;
        if (this.solicitudEntregaid != other.solicitudEntregaid) {
            return false;
        }
        if (this.clientesid != other.clientesid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.SolicitudentregaclientePK[ solicitudEntregaid=" + solicitudEntregaid + ", clientesid=" + clientesid + " ]";
    }
    
}
