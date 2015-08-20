/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.dto.BarajasCantidad;
import com.invbf.sistemagestionmercadeo.dto.InventarioRegalosDTO;
import com.invbf.sistemagestionmercadeo.dto.RegalosCantidadDTO;
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
public class ConfiguracionInventarioRegalosBean implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private InventarioRegalosDTO inventario;

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    /**
     * @param sessionBean the sessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public ConfiguracionInventarioRegalosBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("regalos");
        if (!sessionBean.perfilViewMatch("configuracionInvRegalo") && !sessionBean.perfilViewMatch("verInvRegalo")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        inventario = sessionBean.regalosFacade.getInventario();
        sessionBean.printMensajes();
    }

    public InventarioRegalosDTO getInventario() {
        return inventario;
    }

    public void setInventario(InventarioRegalosDTO inventario) {
        this.inventario = inventario;
    }

    public void guardarCambios() {
        boolean guardar = true;
        for (RegalosCantidadDTO regalo : inventario.getInventario()) {
            if (regalo.getMin() == 0 || regalo.getMax() == 0 || regalo.getMin() >= regalo.getMax()) {
                guardar = false;
                break;
            }
        }
        if (guardar) {
            sessionBean.regalosFacade.guardarInventario(inventario);
            sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Cambios guardados con exito!", ""));
        } else {
            sessionBean.putMensaje(new Mensajes(Mensajes.ERROR, "ERROR!", "Maximos y minimos no pueden estar en cero!"));
        }
        sessionBean.printMensajes();
    }
}
