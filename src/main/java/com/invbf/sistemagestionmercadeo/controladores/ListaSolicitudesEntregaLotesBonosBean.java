/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Solicitudentregalotesmaestro;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import java.io.IOException;
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
public class ListaSolicitudesEntregaLotesBonosBean {

    private List<Solicitudentregalotesmaestro> lista;
    private Solicitudentregalotesmaestro elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public ListaSolicitudesEntregaLotesBonosBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("lotesdebonos");
        if (!sessionBean.perfilViewMatch("SolicitudLotes")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        if(sessionBean.perfilFormMatch("SolicitudLotes", "crear")){
            lista = sessionBean.marketingUserFacade.getAllSolicitudentregalotesmaestro();
        } else {
            lista = sessionBean.marketingUserFacade.getSolicitudentregalotesmaestroNoAceptadas();
        }
        elemento = new Solicitudentregalotesmaestro();
    }

    public List<Solicitudentregalotesmaestro> getLista() {
        return lista;
    }

    public void setLista(List<Solicitudentregalotesmaestro> lista) {
        this.lista = lista;
    }

    public Solicitudentregalotesmaestro getElemento() {
        return elemento;
    }

    public void setElemento(Solicitudentregalotesmaestro elemento) {
        this.elemento = elemento;
    }

    public void delete() {
        sessionBean.marketingUserFacade.deleteSolicitudentregalotesmaestro(elemento);
        lista = sessionBean.marketingUserFacade.getAllSolicitudentregalotesmaestro();
        FacesUtil.addInfoMessage("Solicitud eliminada", "");
        elemento = new Solicitudentregalotesmaestro();

    }

    public void goSolicitud(Integer i) {
        try {
            sessionBean.getAttributes().put("idsolicitudentregalotes", i);
            FacesContext.getCurrentInstance().getExternalContext().redirect("GeneradorSolicitudLoteBono.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void goSolicitudAceptar(Integer i) {
        try {
            sessionBean.getAttributes().put("idsolicitudentregalotes", i);
            FacesContext.getCurrentInstance().getExternalContext().redirect("AceptarSolicitudEntregaBonos.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
