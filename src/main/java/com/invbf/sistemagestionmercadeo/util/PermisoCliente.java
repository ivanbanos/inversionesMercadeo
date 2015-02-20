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
}
