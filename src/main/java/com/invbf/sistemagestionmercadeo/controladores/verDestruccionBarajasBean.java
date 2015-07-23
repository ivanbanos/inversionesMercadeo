/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.dto.ActaDestruccionDTO;
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
public class verDestruccionBarajasBean implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    ActaDestruccionDTO acta;
    private Integer idOrden;

    public verDestruccionBarajasBean() {
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
        sessionBean.printMensajes();
        idOrden = (Integer) sessionBean.getAttributes("destruirbarajas");
        if (idOrden == null) {
            acta = sessionBean.barajasFacade.getBodegasParaDes(sessionBean.getUsuario());
            
        } else {
            acta = sessionBean.barajasFacade.getBodegasParaDesPorId(sessionBean.getUsuario(), idOrden);
        }
    }

    public void destruir() {
        idOrden = sessionBean.barajasFacade.destruir(acta, sessionBean.getUsuario());
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se han entregado las barajas con exito", "Acta de orden #" + idOrden));
        Notificador.notificar(Notificador.correoSolicitudBarajaEntregada,
                "Se han entregado las barajas de la solicitud con el n&uacute;mero de acta " + idOrden + ". Favor revisar la lista de solicitudes de barajas.",
                "Se  han entregado barajas", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
        acta = sessionBean.barajasFacade.getBodegasParaDesPorId(sessionBean.getUsuario(), idOrden);
        
        sessionBean.printMensajes();
    }

    public ActaDestruccionDTO getActa() {
        return acta;
    }

    public void setActa(ActaDestruccionDTO acta) {
        this.acta = acta;
    }


    public Integer getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Integer idOrden) {
        this.idOrden = idOrden;
    }

    
}
