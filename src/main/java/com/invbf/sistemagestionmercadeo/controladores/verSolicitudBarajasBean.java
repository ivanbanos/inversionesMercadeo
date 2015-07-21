/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.dto.SolicitudBarajasDTO;
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
public class verSolicitudBarajasBean implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    

    private SolicitudBarajasDTO orden;
    private Integer idOrden;

    public verSolicitudBarajasBean() {
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
        if (sessionBean.getAttributes("solicitudBaraja") == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("ListaOrdenesCompraBarajasBean.xhtml");
            } catch (IOException ex) {
            }
        }
        sessionBean.printMensajes();
        idOrden = (Integer) sessionBean.getAttributes("solicitudBaraja");
        orden = sessionBean.barajasFacade.getSolicitud(idOrden);
        
    }

    public SolicitudBarajasDTO getOrden() {
        return orden;
    }

    public void setOrden(SolicitudBarajasDTO orden) {
        this.orden = orden;
    }

    public void entregarNuevas() {
        sessionBean.barajasFacade.entregarNuevasSolicitud(idOrden, sessionBean.getUsuario()); 
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se han entregado las barajas con exito", "Acta de orden #" + orden.getId()));
        Notificador.notificar(Notificador.correoSolicitudBarajaEntregada, 
                    "Se han entregado las barajas de la solicitud con el n&uacute;mero de acta "+orden.getId()+". Favor revisar la lista de solicitudes de barajas.", 
                    "Se  han entregado barajas", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
        
        idOrden = (Integer) sessionBean.getAttributes("solicitudBaraja");
        orden = sessionBean.barajasFacade.getSolicitud(idOrden);
        sessionBean.printMensajes();
    }

    public void recibirUsadas() {
        sessionBean.barajasFacade.recibirUsadasSolicitud(idOrden, sessionBean.getUsuario()); 
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se ha entregado la solicitud con exito", "Acta de orden #" + orden.getId()));
        Notificador.notificar(Notificador.correoSolicitudBarajaEntregada, 
                    "Se ha aprobado la solicitud de barajas con el n&uacute;mero de acta "+orden.getId()+". Favor revisar la lista de solicitudes de barajas.", 
                    "Se ha aprobado una solicitud de barajas", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
        
        idOrden = (Integer) sessionBean.getAttributes("solicitudBaraja");
        orden = sessionBean.barajasFacade.getSolicitud(idOrden);
        sessionBean.printMensajes();
    }

    public void entregarUsadas() {
        sessionBean.barajasFacade.entregarUsadasSolicitud(idOrden, sessionBean.getUsuario());
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se ha recibido la solicitud con exito", "Acta de orden #" + orden.getId()));
        Notificador.notificar(Notificador.correoSolicitudBarajaRecibida, 
                    "Se ha recibido la solicitud de barajas con el n&uacute;mero de acta "+orden.getId()+". Favor revisar la lista de solicitudes de barajas.", 
                    "Se ha recibido una solicitud de barajas", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
        
        idOrden = (Integer) sessionBean.getAttributes("solicitudBaraja");
        orden = sessionBean.barajasFacade.getSolicitud(idOrden);
        sessionBean.printMensajes();
    }
    
    public void recibirNuevas() {
        sessionBean.barajasFacade.recibirNuevasSolicitud(idOrden, sessionBean.getUsuario());
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se ha recibido la solicitud con exito", "Acta de orden #" + orden.getId()));
        Notificador.notificar(Notificador.correoSolicitudBarajaRecibida, 
                    "Se ha recibido la solicitud de barajas con el n&uacute;mero de acta "+orden.getId()+". Favor revisar la lista de solicitudes de barajas.", 
                    "Se ha recibido una solicitud de barajas", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
        
        idOrden = (Integer) sessionBean.getAttributes("solicitudBaraja");
        orden = sessionBean.barajasFacade.getSolicitud(idOrden);
        sessionBean.printMensajes();
    }
}
