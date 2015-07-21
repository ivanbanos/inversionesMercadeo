/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.dto.OrdenCompraBarajaDTO;
import com.invbf.sistemagestionmercadeo.util.Mensajes;
import com.invbf.sistemagestionmercadeo.util.Notificador;
import java.io.IOException;
import java.io.Serializable;
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
public class VerOrdenCompraBarajas implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    private OrdenCompraBarajaDTO orden;
    private Integer idOrden;

    public VerOrdenCompraBarajas() {
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
                && !sessionBean.perfilViewMatch("aceptarOrdenBarajas")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        if (sessionBean.getAttributes("orden") == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("ListaOrdenesCompraBarajasBean.xhtml");
            } catch (IOException ex) {
            }
        }
        idOrden = (Integer) sessionBean.getAttributes("orden");
        orden = sessionBean.barajasFacade.getOrden(idOrden);
    }

    public OrdenCompraBarajaDTO getOrden() {
        return orden;
    }

    public void setOrden(OrdenCompraBarajaDTO orden) {
        this.orden = orden;
    }

    public void aprobarOrden() {
        sessionBean.barajasFacade.aprobarOrden(idOrden, sessionBean.getUsuario());
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se ha aprobado la orden con exito", "Acta de orden #" + orden.getId()));
        Notificador.notificar(Notificador.correoOrdenBarajasAprobada,
                "Se ha aprobado la orden de compra de barajas con el n&uacute;mero de acta " + orden.getId() + ". Favor revisar la lista de ordenes de compra de barajas.",
                "Se ha aprobado una orden de compra de barajas", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaOrdenesCompraBarajas.xhtml");
        } catch (IOException ex) {
        }
    }

    public void recibirOrden() {
        sessionBean.barajasFacade.recibirOrden(idOrden, sessionBean.getUsuario());
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se ha recibido la orden con exito", "Acta de orden #" + orden.getId()));
        Notificador.notificar(Notificador.correoOrdenBarajasRecibida,
                "Se ha recibido la orden de compra de barajas con el n&uacute;mero de acta " + orden.getId() + ". Favor revisar la lista de ordenes de compra de barajas.",
                "Se ha recibido una orden de compra de barajas", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaOrdenesCompraBarajas.xhtml");
        } catch (IOException ex) {
        }
    }
}
