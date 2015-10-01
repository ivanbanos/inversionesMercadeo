/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.dto.BarajasCantidad;
import com.invbf.sistemagestionmercadeo.dto.SolicitudBarajasDTO;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.util.Mensajes;
import com.invbf.sistemagestionmercadeo.util.Notificador;
import java.io.IOException;
import java.io.Serializable;
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
        if (!sessionBean.perfilViewMatch("verSolicitudesBarajas")
                && !sessionBean.perfilViewMatch("solicitasBarajas")
                && !sessionBean.perfilViewMatch("entregarBarajas")
                && !sessionBean.perfilViewMatch("recibirBarajas")) {
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
        try {
            sessionBean.barajasFacade.entregarNuevasSolicitud(idOrden, sessionBean.getUsuario());
            sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se han entregado las barajas nuevas con exito", "Acta de orden #" + orden.getId()));
            Notificador.notificar(Notificador.correoSolicitudBarajaEntregada, 
                    "Se han entregado las barajas nuevas de la solicitud con el n&uacute;mero de acta "+orden.getId()+". Favor revisar la lista de solicitudes de barajas.",
                    "Se  han entregado barajas nuevas", sessionBean.getUsuario().getUsuariodetalle().getCorreo(),new Casino(orden.getId()));
            Notificador.notificar(Notificador.correoSolicitudBarajaEntregada, 
                    "Tiene pendiente por entregar las barajas usadas de la solicitud con el n&uacute;mero de acta "+orden.getId()+". Favor revisar la lista de solicitudes de barajas.",
                    "Pendiente por entregar barajas usadas", sessionBean.getUsuario().getUsuariodetalle().getCorreo(),new Casino(orden.getId()));
            
            idOrden = (Integer) sessionBean.getAttributes("solicitudBaraja");
            orden = sessionBean.barajasFacade.getSolicitud(idOrden);
            sessionBean.printMensajes();
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaSolicitudBarajas.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(verSolicitudBarajasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void recibirUsadas() {
        try {
            sessionBean.barajasFacade.recibirUsadasSolicitud(idOrden, sessionBean.getUsuario());
            sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se han recibido las barajas usadas con exito", "Acta de orden #" + orden.getId()));
            Notificador.notificar(Notificador.correoSolicitudBarajaEntregada, 
                    "Se han recibido las barajas usadas de la solicitud con el n&uacute;mero de acta "+orden.getId()+". Favor revisar la lista de solicitudes de barajas.",
                    "Se  han recibido barajas usadas", sessionBean.getUsuario().getUsuariodetalle().getCorreo(),new Casino(orden.getId()));
            
            idOrden = (Integer) sessionBean.getAttributes("solicitudBaraja");
            orden = sessionBean.barajasFacade.getSolicitud(idOrden);
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaSolicitudBarajas.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(verSolicitudBarajasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void entregarUsadas() {
        try {
            for (BarajasCantidad det : orden.getCantidades()) {
                    System.out.println("Detalle");
                    System.out.println(det.getDevueltas());
                }
            sessionBean.barajasFacade.entregarUsadasSolicitud(orden, sessionBean.getUsuario());
            sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se han entregado las barajas usadas con exito", "Acta de orden #" + orden.getId()));
            Notificador.notificar(Notificador.correoSolicitudBarajaRecibida, 
                    "Se han entregado las barajas usadas de la solicitud con el n&uacute;mero de acta "+orden.getId()+". Favor revisar la lista de solicitudes de barajas.",
                    "Se  han entregado barajas usadas", sessionBean.getUsuario().getUsuariodetalle().getCorreo(),new Casino(orden.getId()));
            
            idOrden = (Integer) sessionBean.getAttributes("solicitudBaraja");
            orden = sessionBean.barajasFacade.getSolicitud(idOrden);
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaSolicitudBarajas.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(verSolicitudBarajasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void recibirNuevas() {
        try {
            sessionBean.barajasFacade.recibirNuevasSolicitud(idOrden, sessionBean.getUsuario());
            sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se han recibido las barajas nuevas con exito", "Acta de orden #" + orden.getId()));
            Notificador.notificar(Notificador.correoSolicitudBarajaRecibida, 
                    "Se han recibido las barajas nuevas de la solicitud con el n&uacute;mero de acta "+orden.getId()+". Favor revisar la lista de solicitudes de barajas.",
                    "Se  han recibido barajas nuevas", sessionBean.getUsuario().getUsuariodetalle().getCorreo(),new Casino(orden.getId()));
            
            idOrden = (Integer) sessionBean.getAttributes("solicitudBaraja");
            orden = sessionBean.barajasFacade.getSolicitud(idOrden);
            
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaSolicitudBarajas.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(verSolicitudBarajasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
