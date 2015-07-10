/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.controladores.SessionBean;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregalotesmaestro;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import java.io.IOException;
import java.io.Serializable;
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
public class ListaRequerimientoLotes implements Serializable {

    private List<Solicitudentregalotesmaestro> lista;
    private List<Solicitudentregalotesmaestro> listaFiltrada;
    private Solicitudentregalotesmaestro elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public ListaRequerimientoLotes() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("requisiciones");
        
        lista = sessionBean.marketingUserFacade.getRequeriemntosLotes();
        elemento = new Solicitudentregalotesmaestro();
        sessionBean.printMensajes();
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
        lista = sessionBean.marketingUserFacade.getRequeriemntosLotes();
        FacesUtil.addInfoMessage("Solicitud eliminada", "");
        elemento = new Solicitudentregalotesmaestro();

    }

    public void goRequerimiento(Integer id) {
        try {
                sessionBean.setAttribute("idsolicitudentregalotes", id);
                FacesContext.getCurrentInstance().getExternalContext().redirect("RequerimientoLote.xhtml");
            
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void goRequerimiento() {
        try {
                sessionBean.setAttribute("idsolicitudentregalotes", 0);
                FacesContext.getCurrentInstance().getExternalContext().redirect("GeneradorRequerimientoLote.xhtml");
            
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void goSolicitudAceptar(Integer i) {
        try {
            sessionBean.setAttribute("idsolicitudentregalotes", i);
            FacesContext.getCurrentInstance().getExternalContext().redirect("RequerimientoLote.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void goSolicitudValidarBonos(Integer i) {
        try {
            sessionBean.setAttribute("idsolicitudentregalotes", i);
            FacesContext.getCurrentInstance().getExternalContext().redirect("BonosValidarView.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Solicitudentregalotesmaestro> getListaFiltrada() {
        return listaFiltrada;
    }

    public void setListaFiltrada(List<Solicitudentregalotesmaestro> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }

}
