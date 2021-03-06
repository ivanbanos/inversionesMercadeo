/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.dto.BarajasDTO;
import com.invbf.sistemagestionmercadeo.dto.OrdenCompraBarajaDTO;
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
import org.eclipse.persistence.internal.core.helper.CoreClassConstants;

/**
 *
 * @author ivan
 */
@ManagedBean
@ViewScoped
public class ListaOrdenesCompraBarajasBean implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    private List<OrdenCompraBarajaDTO> lista;

    public ListaOrdenesCompraBarajasBean() {
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
        if (!sessionBean.perfilViewMatch("recibirOrdenBarajas")
                && !sessionBean.perfilViewMatch("generarOrdenBarajas")
                && !sessionBean.perfilViewMatch("aceptarOrdenBarajas")
                && !sessionBean.perfilViewMatch("ingresarOrdenBarajas")
                && !sessionBean.perfilViewMatch("verRequerimientosBarajas")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        if (sessionBean.perfilViewMatch("verRequerimientosBarajas") ||sessionBean.perfilViewMatch("generarOrdenBarajas") || sessionBean.perfilViewMatch("aceptarOrdenBarajas")) {
            lista = sessionBean.barajasFacade.getOrdenesCompra();
        } else {
            if (sessionBean.perfilViewMatch("recibirOrdenBarajas")||sessionBean.perfilViewMatch("ingresarOrdenBarajas")) {
                lista = sessionBean.barajasFacade.getOrdenesCompra(sessionBean.getUsuario());
            } else {
                lista = new ArrayList<OrdenCompraBarajaDTO>();
            }
        }
        Collections.reverse(lista);
        sessionBean.printMensajes();
    }

    public List<OrdenCompraBarajaDTO> getLista() {
        return lista;
    }

    public void setLista(List<OrdenCompraBarajaDTO> lista) {
        this.lista = lista;
    }

    public void goOrdenrecibircaja(Integer i, String estado) {
        try {
            sessionBean.setAttribute("orden", i);
            FacesContext.getCurrentInstance().getExternalContext().redirect("recibirOrdenCaja.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void goOrdenCrear() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("crearOrdenBarajas.xhtml");
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
            FacesContext.getCurrentInstance().getExternalContext().redirect("verOrdenBarajasNadaMAs.xhtml");

        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
