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
import com.invbf.sistemagestionmercadeo.util.Notificador;
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
    private String observaciones;

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
        if (!sessionBean.perfilViewMatch("generarOrdenRegalo")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        inventario = sessionBean.regalosFacade.getInventarioRequerimiento();
        
        sessionBean.printMensajes();
    }

    public InventarioRegalosDTO getInventario() {
        return inventario;
    }

    public void setInventario(InventarioRegalosDTO inventario) {
        this.inventario = inventario;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
                observaciones=sessionBean.getUsuario().getNombreUsuario()+": "+observaciones+"\n";
                int i = sessionBean.regalosFacade.generarOrdenRegalos(inventario, observaciones, sessionBean.getUsuario());
                sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Requerimeinto generado con exito!", ""));
                Notificador.notificar(Notificador.correoRegaloOrdenCreada,
                "Se ha generado el requerimiento de compra de regalos con el n&uacute;mero de acta " + i + ". Favor revisar la lista de requerimientos de compra de regalos.",
                "Se ha generado un requerimiento de compra de regalos", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
        
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
