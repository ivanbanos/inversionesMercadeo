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
public class GenerarRequerimientoRegalosBean implements Serializable {

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

    public GenerarRequerimientoRegalosBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("regalos");
        if (!sessionBean.perfilViewMatch("generarOrdenBarajas")) {
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

    public void crearRequerimientoDeOrdenCompra() {
        boolean guardar = false;
        for (RegalosCantidadDTO regalo : inventario.getInventario()) {
            if (regalo.getCantidad()!= 0 ) {
                guardar = true;
                break;
            }
        }
        if (guardar) {
            try {
                sessionBean.regalosFacade.generarOrdenRegalos(inventario, sessionBean.getUsuario());
                sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Requerimeinto generado con exito!", ""));
                
                FacesContext.getCurrentInstance().getExternalContext().redirect("ListaRequerimientosRegalos.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(GenerarRequerimientoRegalosBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            sessionBean.putMensaje(new Mensajes(Mensajes.ERROR, "ERROR!", "No puede crear un requerimiento de regalos con todas las cantidades en 0!"));
        }
        sessionBean.printMensajes();
    }
}
