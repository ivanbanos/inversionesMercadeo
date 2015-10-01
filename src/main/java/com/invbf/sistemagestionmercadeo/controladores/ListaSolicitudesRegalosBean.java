/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.dto.SolicitudRegaloDTO;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
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
public class ListaSolicitudesRegalosBean implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    
    private List<SolicitudRegaloDTO> lista;
    

    public ListaSolicitudesRegalosBean() {
    }

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("regalos");
        if (!sessionBean.perfilViewMatch("RegaloCrearSolicitud") 
                && !sessionBean.perfilViewMatch("RegaloAprobarSolicitud") 
                && !sessionBean.perfilViewMatch("RegaloEnviarSolicitud")
                && !sessionBean.perfilViewMatch("RegaloRecibirSolicitud")
                && !sessionBean.perfilViewMatch("RegaloVerSolicitud")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        lista = sessionBean.regalosFacade.getSolicitudesRegalos(sessionBean.perfilViewMatch("RegaloVerSolicitud"), sessionBean.getUsuario());
        Collections.reverse(lista);
        sessionBean.printMensajes();
    }

    public List<SolicitudRegaloDTO> getLista() {
        return lista;
    }

    public void setLista(List<SolicitudRegaloDTO> lista) {
        this.lista = lista;
    }
    
    public void goOrdenCrear() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("RegaloCrearSolicitud.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void goOrdenver(Integer i) {
        try {
            sessionBean.setAttribute("SolicitudRegalo", i);
            FacesContext.getCurrentInstance().getExternalContext().redirect("RegaloVerSolicitud.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void goOrdenaprobar(Integer i) {
        try {
            sessionBean.setAttribute("SolicitudRegalo", i);
            FacesContext.getCurrentInstance().getExternalContext().redirect("RegaloAprobarSolicitud.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void goOrdenenviar(Integer i) {
        try {
            sessionBean.setAttribute("SolicitudRegalo", i);
            FacesContext.getCurrentInstance().getExternalContext().redirect("RegaloEnviarSolicitud.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void goOrdenrecibir(Integer i) {
        try {
            sessionBean.setAttribute("SolicitudRegalo", i);
            FacesContext.getCurrentInstance().getExternalContext().redirect("RegaloRecibirSolicitud.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
