/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.dto.TrasladoDTO;
import com.invbf.sistemagestionmercadeo.dto.UsuarioDTO;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.util.Mensajes;
import com.invbf.sistemagestionmercadeo.util.Notificador;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
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
public class TransferenciasBarajasBean implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private TrasladoDTO item;

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

    public TransferenciasBarajasBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("barajas");
        if (!sessionBean.perfilViewMatch("verTransferencias") && !sessionBean.perfilViewMatch("enviarTransferencia") && !sessionBean.perfilViewMatch("recibirTransferencia")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        if (sessionBean.getAttributes("traslado") == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("ListaOrdenesCompraBarajasBean.xhtml");
            } catch (IOException ex) {
            }
        }
        Integer idOrden = (Integer) sessionBean.getAttributes("traslado");
        item = sessionBean.barajasFacade.getTransferencia(idOrden);
    }

    public TrasladoDTO getItem() {
        return item;
    }

    public void setItem(TrasladoDTO item) {
        this.item = item;
    }

    public void enviar() {
        item.setEnviador(new UsuarioDTO(sessionBean.getUsuario()));
        Integer id = sessionBean.barajasFacade.enviarTransferencia(item);
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Transferencia enviada con exito", "Acta N " + id + "."));
        Notificador.notificar(Notificador.correoOrdenTransferenciaEnviada, "Se ha enviado la orden de transferencias de barajas con el numero de acta " + id + " y su sala debe recibirlas. Favor revisar la lista de transferencias de barajas.", "Orden de transferencia de barajas enviada", "", new Casino(item.getSalaenviadora().getIdCasino()));

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaTransferenciasBarajas.xhtml");
        } catch (IOException ex) {
        }
    }

    public void recibir() {
        item.setRecibidor(new UsuarioDTO(sessionBean.getUsuario()));
        Integer id = sessionBean.barajasFacade.recibirTransferencia(item);
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Transferencia recibida con exito", "Acta N " + id + "."));
        Notificador.notificar(Notificador.correoOrdenTransferenciaRecibida, "Se ha recibido la orden de transferencias de barajas con el numero de acta " + id + ". Favor revisar la lista de transferencias de barajas.", "Orden de transferencia de barajas recibida", "");

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaTransferenciasBarajas.xhtml");
        } catch (IOException ex) {
        }
    }

}
