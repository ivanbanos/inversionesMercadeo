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
public class ClienteatributoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "idCliente")
    private int idCliente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idAtributo")
    private int idAtributo;

    public ClienteatributoPK() {
    }

    public ClienteatributoPK(int idCliente, int idAtributo) {
        this.idCliente = idCliente;
        this.idAtributo = idAtributo;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdAtributo() {
        return idAtributo;
    }

    public void setIdAtributo(int idAtributo) {
        this.idAtributo = idAtributo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idCliente;
        hash += (int) idAtributo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClienteatributoPK)) {
            return false;
        }
        ClienteatributoPK other = (ClienteatributoPK) object;
        if (this.idCliente != other.idCliente) {
            return false;
        }
        if (this.idAtributo != other.idAtributo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.ClienteatributoPK[ idCliente=" + idCliente + ", idAtributo=" + idAtributo + " ]";
    }
    
}
