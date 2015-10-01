/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Permiso;
import java.io.Serializable;


/**
 *
 * @author ideacentre
 */
public class PermisoCliente  implements Serializable{
    private Permiso permiso;
    private Cliente cliente;

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public PermisoCliente(Permiso permiso, Cliente cliente) {
        this.permiso = permiso;
        this.cliente = cliente;
    }

    public PermisoCliente() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + (this.permiso != null ? this.permiso.hashCode() : 0);
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
        final PermisoCliente other = (PermisoCliente) obj;
        if (this.permiso != other.permiso && (this.permiso == null || !this.permiso.equals(other.permiso))) {
            return false;
        }
        return true;
    }
    
}
