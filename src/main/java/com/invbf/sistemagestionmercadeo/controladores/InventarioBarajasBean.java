/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.dto.BarajasCantidad;
import com.invbf.sistemagestionmercadeo.dto.InventarioBarajasDTO;
import com.invbf.sistemagestionmercadeo.util.Mensajes;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ivan
 */
@ManagedBean
@ViewScoped
public class InventarioBarajasBean implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private InventarioBarajasDTO inventario;

    /**
     * @return the sessionBean
     */
    public SessionBean getSessionBean() {
        return sessionBean;
    }

    /**
     * @param sessionBean the sessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public InventarioBarajasBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("barajas");
        if (!sessionBean.perfilViewMatch("verBodegas")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        if (sessionBean.getAttributes("bodega") == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("ListaOrdenesCompraBarajasBean.xhtml");
            } catch (IOException ex) {
            }
        }
        Integer bodega = (Integer) sessionBean.getAttributes("bodega");
        inventario = sessionBean.barajasFacade.getInventario(bodega);
        if (inventario.getInventario() == null || inventario.getInventario().isEmpty()) {
            sessionBean.barajasFacade.crearBodega(inventario.getId());
            inventario = sessionBean.barajasFacade.getInventario(bodega);
        }
        sessionBean.printMensajes();
    }

    public InventarioBarajasDTO getInventario() {
        return inventario;
    }

    public void setInventario(InventarioBarajasDTO inventario) {
        this.inventario = inventario;
    }

    public void guardarCambios() {
        boolean guardar = true;
        for (BarajasCantidad barajas : inventario.getInventario()) {
            if (barajas.getMin() == 0 || barajas.getMax() == 0 || barajas.getMin() >= barajas.getMax()) {
                guardar = false;
                break;
            }
        }
        if (guardar) {
            try {
                sessionBean.barajasFacade.guardarBodega(inventario);
                sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Cambios guardados con exito!", ""));
                FacesContext.getCurrentInstance().getExternalContext().redirect("ListaBodegas.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(InventarioBarajasBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            sessionBean.putMensaje(new Mensajes(Mensajes.ERROR, "ERROR!", "Maximos y minimos no pueden estar en cero!"));
            sessionBean.printMensajes();
        }
    }
}
