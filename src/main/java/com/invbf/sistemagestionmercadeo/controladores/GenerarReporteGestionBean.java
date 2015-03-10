/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Solicitudentrega;
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
public class GenerarReporteGestionBean implements Serializable {

    private List<Solicitudentrega> lista;
    private Solicitudentrega elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public GenerarReporteGestionBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("solicitudbonos");
        if (!sessionBean.perfilViewMatch("GenerarSolicitudBono")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        sessionBean.marketingUserFacade.verificarEstadoSolicitudes();
        
        if (sessionBean.perfilViewMatch("AprobarSolicitudBono") || sessionBean.perfilViewMatch("PreAprobarSolicitudBono")) {

            lista = sessionBean.marketingUserFacade.getAllSolicitudentregaVENC();
        } else {
        lista = sessionBean.marketingUserFacade.getAllSolicitudentregaSolicitanteVENC(sessionBean.getUsuario().getIdUsuario());
        }
        
        Collections.reverse(lista);
        sessionBean.printMensajes();
    }

    public List<Solicitudentrega> getLista() {
        return lista;
    }

    public void setLista(List<Solicitudentrega> lista) {
        this.lista = lista;
    }

    public Solicitudentrega getElemento() {
        return elemento;
    }

    public void setElemento(Solicitudentrega elemento) {
        this.elemento = elemento;
    }

    public void goSolicitudver(Integer i) {
        try {
            sessionBean.setAttribute("idSolicitudentrega", i);
            FacesContext.getCurrentInstance().getExternalContext().redirect("vistaGeneracionReporte.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
