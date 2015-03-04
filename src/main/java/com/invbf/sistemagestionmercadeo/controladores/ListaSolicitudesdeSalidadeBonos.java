/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Controlsalidabono;
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
public class ListaSolicitudesdeSalidadeBonos implements Serializable{

    private List<Controlsalidabono> lista;
    private List<Controlsalidabono> listaFiltrada;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public ListaSolicitudesdeSalidadeBonos() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("salidadebonos");
        if (!sessionBean.perfilViewMatch("Controlsalidabonos")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        lista = sessionBean.marketingUserFacade.getAllControlsalidabonos();
        Collections.reverse(lista);
        System.out.println(lista.size());
    }

    public List<Controlsalidabono> getLista() {
        return lista;
    }

    public void setLista(List<Controlsalidabono> lista) {
        this.lista = lista;
    }

    public void goSolicitud(Integer i) {
        try {
            sessionBean.setAttribute("idsolicitudsalida", i);
            FacesContext.getCurrentInstance().getExternalContext().redirect("GeneradorSolicitudControlsalidabonos.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void goSolicitudAceptar(Integer i) {
        try {
            sessionBean.setAttribute("idsolicitudsalida", i);
            FacesContext.getCurrentInstance().getExternalContext().redirect("AceptarSolicitudControlsalidabonos.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void goSolicitudValidarBonos(Integer i){
        try {
            sessionBean.setAttribute("idsolicitudsalida", i);
            FacesContext.getCurrentInstance().getExternalContext().redirect("BonosValidarView.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Controlsalidabono> getListaFiltrada() {
        return listaFiltrada;
    }

    public void setListaFiltrada(List<Controlsalidabono> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }
    
    
}
