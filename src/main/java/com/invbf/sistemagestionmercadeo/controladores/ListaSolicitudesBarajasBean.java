/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.dto.BarajasDTO;
import com.invbf.sistemagestionmercadeo.dto.OrdenCompraBarajaDTO;
import com.invbf.sistemagestionmercadeo.dto.SolicitudBarajasDTO;
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
import org.eclipse.persistence.internal.core.helper.CoreClassConstants;

/**
 *
 * @author ivan
 */
@ManagedBean
@ViewScoped
public class ListaSolicitudesBarajasBean implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    
    private List<SolicitudBarajasDTO> lista;
    

    public ListaSolicitudesBarajasBean() {
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
        sessionBean.setActive("barajas");
        if (!sessionBean.perfilViewMatch("verSolicitudesBarajas") 
                && !sessionBean.perfilViewMatch("solicitasBarajas") 
                && !sessionBean.perfilViewMatch("entregarBarajas")
                && !sessionBean.perfilViewMatch("recibirBarajas")
                && !sessionBean.perfilViewMatch("destruirBarajas")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        lista = sessionBean.barajasFacade.getSolicitudesBarajas(sessionBean.perfilViewMatch("verSolicitudesBarajas"), sessionBean.getUsuario());
        Collections.reverse(lista);
        sessionBean.printMensajes();
    }

    public List<SolicitudBarajasDTO> getLista() {
        return lista;
    }

    public void setLista(List<SolicitudBarajasDTO> lista) {
        this.lista = lista;
    }
    
    public void goOrdenCrear() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("CrearSolicitudBarajas.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void goOrdenver(Integer i) {
        try {
            sessionBean.setAttribute("solicitudBaraja", i);
            FacesContext.getCurrentInstance().getExternalContext().redirect("VerSolicitudBarajas.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void goOrdenversala(Integer i) {
        try {
            sessionBean.setAttribute("solicitudBaraja", i);
            FacesContext.getCurrentInstance().getExternalContext().redirect("verSolicitudBarajaRecibir.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void goOrdenversalanadaMas(Integer i) {
        try {
            sessionBean.setAttribute("solicitudBaraja", i);
            FacesContext.getCurrentInstance().getExternalContext().redirect("verSolicitudBarajanadaMas.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
