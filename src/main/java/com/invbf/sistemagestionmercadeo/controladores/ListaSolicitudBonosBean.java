/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentrega;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
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
public class ListaSolicitudBonosBean implements Serializable{

    private List<Solicitudentrega> lista;
    private List<Solicitudentrega> listaFiltrada;
    private Solicitudentrega elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    
    private List<Casino> casinos;
    private List<Usuario> usuarios;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public ListaSolicitudBonosBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("solicitudbonos");
        if (!sessionBean.perfilViewMatch("GenerarSolicitudBono") && !sessionBean.perfilViewMatch("PreAprobarSolicitudBono") && !sessionBean.perfilViewMatch("AprobarSolicitudBono")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        if (sessionBean.perfilViewMatch("AprobarSolicitudBono") || sessionBean.perfilViewMatch("PreAprobarSolicitudBono")) {

            lista = sessionBean.marketingUserFacade.getAllSolicitudentrega();
        } else {
            lista = sessionBean.marketingUserFacade.getAllSolicitudentregaSolicitante(sessionBean.getUsuario().getIdUsuario());
        }
        Collections.reverse(lista);
        casinos = sessionBean.adminFacade.findAllCasinos();
        usuarios = sessionBean.adminFacade.findAllUsuarios();
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

    public void delete() {
        sessionBean.marketingUserFacade.deleteSolicitudentrega(elemento);
        FacesUtil.addInfoMessage("Solicitud eliminada", "");
        elemento = new Solicitudentrega();

        if (sessionBean.perfilViewMatch("AprobarSolicitudBono") || sessionBean.perfilViewMatch("PreAprobarSolicitudBono")) {
            lista = sessionBean.marketingUserFacade.getAllSolicitudentrega();
        } else {
            lista = sessionBean.marketingUserFacade.getAllSolicitudentregaSolicitante(sessionBean.getUsuario().getIdUsuario());
        }
    }

    public void goSolicitud(Integer i) {
        try {
            sessionBean.setAttribute("idSolicitudentrega", i);
            FacesContext.getCurrentInstance().getExternalContext().redirect("GeneradorSolicitudBonos.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void goSolicitudAceptar(Integer i) {
        try {
            sessionBean.setAttribute("idSolicitudentrega", i);
            FacesContext.getCurrentInstance().getExternalContext().redirect("AprobarSolicitudBonos.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void goSolicitudpreAceptar(Integer i) {
        try {
            sessionBean.setAttribute("idSolicitudentrega", i);
            FacesContext.getCurrentInstance().getExternalContext().redirect("PreAprobarSolicitudBonos.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public Casino getCasinoById(Integer idCasino) {
        int casinoIndex = casinos.indexOf(new Casino(idCasino));
        if (casinoIndex != -1) {
            return casinos.get(casinoIndex);
        }
        return new Casino();
    }
    public Usuario getUsuarioById(Integer idUsuario) {
        int casinoIndex = usuarios.indexOf(new Usuario(idUsuario));
        if (casinoIndex != -1) {
            return usuarios.get(casinoIndex);
        }
        return new Usuario();
    }

    public List<Solicitudentrega> getListaFiltrada() {
        return listaFiltrada;
    }

    public void setListaFiltrada(List<Solicitudentrega> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }
    
}
