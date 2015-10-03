/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.dto.OrdenCompraRegaloDTO;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
public class ListaRequerimientoRegalosBean implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    private List<OrdenCompraRegaloDTO> lista;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public ListaRequerimientoRegalosBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("regalos");
        if (!sessionBean.perfilViewMatch("verOrdenRegalo")
                && !sessionBean.perfilViewMatch("generarOrdenRegalo")
                && !sessionBean.perfilViewMatch("aprobarOrdenRegalo")
                && !sessionBean.perfilViewMatch("recibirOrdenRegalo")
                && !sessionBean.perfilViewMatch("ingresarOrdenRegalo")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        if (sessionBean.perfilViewMatch("verOrdenRegalo")) {
            lista = sessionBean.regalosFacade.getOrdenesCompra();
        } else {
            lista = sessionBean.regalosFacade.getOrdenesCompra(sessionBean.getUsuario());

        }
        Collections.reverse(lista);
        sessionBean.printMensajes();

    }

    public List<OrdenCompraRegaloDTO> getLista() {
        return lista;
    }

    public void setLista(List<OrdenCompraRegaloDTO> lista) {
        this.lista = lista;
    }

    public void goOrdenaprobarr(Integer i, String estado) {
        try {
            sessionBean.setAttribute("orden", i);
            FacesContext.getCurrentInstance().getExternalContext().redirect("OrdenAprobarRegalos.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void goOrdengenerar(Integer i, String estado) {
        try {
            sessionBean.setAttribute("orden", i);
            FacesContext.getCurrentInstance().getExternalContext().redirect("OrdenGenerarRegalos.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void goOrdenrecibircaja(Integer i, String estado) {
        try {
            sessionBean.setAttribute("orden", i);
            FacesContext.getCurrentInstance().getExternalContext().redirect("OrdenRecibirRegalos.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void goOrdeningresarcaja(Integer i, String estado) {
        try {
            sessionBean.setAttribute("orden", i);
            FacesContext.getCurrentInstance().getExternalContext().redirect("OrdenIngresarRegalos.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void goOrdenCrear() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("crearOrdenRegalos.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void goOrdenver(Integer i, String estado) {
        try {
            sessionBean.setAttribute("orden", i);
            FacesContext.getCurrentInstance().getExternalContext().redirect("verOrdenBarajas.xhtml");

        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void goOrdenvernadaMas(Integer i, String estado) {
        try {
            sessionBean.setAttribute("orden", i);
            FacesContext.getCurrentInstance().getExternalContext().redirect("VerOrdenRegalos.xhtml");

        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
