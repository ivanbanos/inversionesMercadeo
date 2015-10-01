/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.dto.RegaloCantidad;
import com.invbf.sistemagestionmercadeo.dto.RegalosCantidadDTO;
import com.invbf.sistemagestionmercadeo.dto.SolicitudRegaloDTO;
import com.invbf.sistemagestionmercadeo.util.Mensajes;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
public class RegaloVerSolicitud implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    private SolicitudRegaloDTO solicitud;
    private Integer idOrden;
    private String observaciones;
    private List<RegaloCantidad> regalocantidad;

    public RegaloVerSolicitud() {
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
        sessionBean.setActive("regalos");
        if (!sessionBean.perfilViewMatch("RegaloAprobarSolicitud")
                && !sessionBean.perfilViewMatch("RegaloEnviarSolicitud")
                && !sessionBean.perfilViewMatch("RegaloRecibirSolicitud")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        if (sessionBean.getAttributes("SolicitudRegalo") == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("RegaloListaSolicitudes.xhtml");
            } catch (IOException ex) {
            }
        }
        idOrden = (Integer) sessionBean.getAttributes("SolicitudRegalo");
        solicitud = sessionBean.regalosFacade.getSolicitud(idOrden);
        regalocantidad = new ArrayList<RegaloCantidad>();
        for (RegalosCantidadDTO regalo : solicitud.getCantidades()) {
            RegaloCantidad regaloC = new RegaloCantidad(regalo.getRegalo(), 1);
            if (regalocantidad.contains(regaloC)) {
                regalocantidad.get(regalocantidad.indexOf(regaloC)).sumarUno();
            } else {
                regalocantidad.add(regaloC);
            }
        }
    }

    public void aprobar() {
        sessionBean.regalosFacade.aprobarSolicitud(solicitud, sessionBean.getUsuario());
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se ha aprobado la solicitud con exito", "Acta de solicitud #" + solicitud.getId()));
        //Notificador.notificar(Notificador.correoOrdenBarajasAprobada,
        //        "Se ha aprobado el requerimiento de compra de barajas con el n&uacute;mero de acta " + orden.getId() + ". Favor revisar la lista de ordenes de compra de barajas.",
        //        "Se ha aprobado un requerimiento de compra de barajas", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("RegaloListaSolicitudes.xhtml");
        } catch (IOException ex) {
        }
    }

    public void rechazar() {
        sessionBean.regalosFacade.rechazarSolicitud(solicitud, sessionBean.getUsuario());
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se ha rechazado la solicitud con exito", "Acta de solicitud #" + solicitud.getId()));
        //Notificador.notificar(Notificador.correoOrdenBarajasAprobada,
        //        "Se ha rechazado el requerimiento de compra de barajas con el n&uacute;mero de acta " + orden.getId() + ". Favor revisar la lista de ordenes de compra de barajas.",
        //        "Se ha rechazado un requerimiento de compra de barajas", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("RegaloListaSolicitudes.xhtml");
        } catch (IOException ex) {
        }
    }

    public void recibir() {
        sessionBean.regalosFacade.recibirSolicitud(solicitud, sessionBean.getUsuario());
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se han recibido los regalos con exito", "Acta de solicitud #" + solicitud.getId()));
        //Notificador.notificar(Notificador.correoOrdenBarajasRecibida,
        //        "Se ha recibido el requerimiento de compra de barajas con el n&uacute;mero de acta " + orden.getId() + ". Favor revisar la lista de ordenes de compra de barajas.",
        //        "Se ha recibido un requerimiento de compra de barajas", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("RegaloListaSolicitudes.xhtml");
        } catch (IOException ex) {
        }
    }

    public void enviar() {
        sessionBean.regalosFacade.enviarSolicitud(solicitud, sessionBean.getUsuario());
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se han enviado los regalos con exito", "Acta de solicitud #" + solicitud.getId()));
        //Notificador.notificar(Notificador.correoOrdenBarajasRecibida,
        //        "Se ha recibido el requerimiento de compra de barajas con el n&uacute;mero de acta " + orden.getId() + ". Favor revisar la lista de ordenes de compra de barajas.",
        //        "Se ha recibido un requerimiento de compra de barajas", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("RegaloListaSolicitudes.xhtml");
        } catch (IOException ex) {
        }
    }

    public Integer getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Integer idOrden) {
        this.idOrden = idOrden;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public SolicitudRegaloDTO getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudRegaloDTO solicitud) {
        this.solicitud = solicitud;
    }

    public List<RegaloCantidad> getRegalocantidad() {
        return regalocantidad;
    }

    public void setRegalocantidad(List<RegaloCantidad> regalocantidad) {
        this.regalocantidad = regalocantidad;
    }

}
