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
public class ListasclientestareasPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "idTarea")
    private int idTarea;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idCliente")
    private int idCliente;

    public ListasclientestareasPK() {
    }

    public ListasclientestareasPK(int idTarea, int idCliente) {
        this.idTarea = idTarea;
        this.idCliente = idCliente;
    }

    public int getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idTarea;
        hash += (int) idCliente;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListasclientestareasPK)) {
            return false;
        }
        ListasclientestareasPK other = (ListasclientestareasPK) object;
        if (this.idTarea != other.idTarea) {
            return false;
        }
        if (this.idCliente != other.idCliente) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.ListasclientestareasPK[ idTarea=" + idTarea + ", idCliente=" + idCliente + " ]";
    }
    
}
