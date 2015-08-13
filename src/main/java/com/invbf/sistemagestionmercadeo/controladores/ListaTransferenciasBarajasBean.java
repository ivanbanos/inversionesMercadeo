/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.dto.TrasladoDTO;
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
public class ListaTransferenciasBarajasBean implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<TrasladoDTO> items;

    /**
     * @return the sessionBean
     */
    public SessionBean getSessionBean() {
        return sessionBean;
    }

    /**
     * @param sessionBean the sessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public ListaTransferenciasBarajasBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("barajas");
        if (!sessionBean.perfilViewMatch("verTransferencias")  && !sessionBean.perfilViewMatch("enviarTransferencia") && !sessionBean.perfilViewMatch("recibirTransferencia")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        if (sessionBean.perfilViewMatch("verTransferencias")) {
            items = sessionBean.barajasFacade.getTransferencias();
        } else {
            items = sessionBean.barajasFacade.getTransferencias(sessionBean.getUsuario());
        }
        sessionBean.printMensajes();
    }

    public void goTraslado(Integer id) {
        try {
            sessionBean.setAttribute("traslado", id);
            FacesContext.getCurrentInstance().getExternalContext().redirect("TransferenciaBarajas.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void goVer(Integer id) {
        try {
            sessionBean.setAttribute("traslado", id);
            FacesContext.getCurrentInstance().getExternalContext().redirect("TransferenciaBarajasVer.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void goCrearTraslado(Integer id) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("TransferenciaBarajasCrear.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<TrasladoDTO> getItems() {
        return items;
    }

    public void setItems(List<TrasladoDTO> items) {
        this.items = items;
    }


}
