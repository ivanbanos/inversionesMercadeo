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
public class OrdencompraregalodetallePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "orden")
    private int orden;
    @Basic(optional = false)
    @NotNull
    @Column(name = "inventario")
    private int inventario;

    public OrdencompraregalodetallePK() {
    }

    public OrdencompraregalodetallePK(int orden, int inventario) {
        this.orden = orden;
        this.inventario = inventario;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public int getInventario() {
        return inventario;
    }

    public void setInventario(int inventario) {
        this.inventario = inventario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) orden;
        hash += (int) inventario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdencompraregalodetallePK)) {
            return false;
        }
        OrdencompraregalodetallePK other = (OrdencompraregalodetallePK) object;
        if (this.orden != other.orden) {
            return false;
        }
        if (this.inventario != other.inventario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.OrdencompraregalodetallePK[ orden=" + orden + ", inventario=" + inventario + " ]";
    }
    
}
