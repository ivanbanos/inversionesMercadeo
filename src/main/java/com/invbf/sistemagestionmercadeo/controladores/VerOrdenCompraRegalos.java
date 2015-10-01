/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.dto.BarajasCantidad;
import com.invbf.sistemagestionmercadeo.dto.OrdenCompraBarajaDTO;
import com.invbf.sistemagestionmercadeo.dto.OrdenCompraRegaloDTO;
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
public class VerOrdenCompraRegalos implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    private OrdenCompraRegaloDTO orden;
    private Integer idOrden;
    private String observaciones;

    public VerOrdenCompraRegalos() {
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
                FacesContext.getCurrentInstance().getExternalContext().redirect("ListaOrdenesCompraBarajas.xhtml");
            } catch (IOException ex) {
            }
        }
        idOrden = (Integer) sessionBean.getAttributes("orden");
        orden = sessionBean.regalosFacade.getOrden(idOrden);
    }

    public void generar() {
        orden.setObservaciones(orden.getObservaciones()+sessionBean.getUsuario().getNombreUsuario()+":"+observaciones+".");
        
        sessionBean.regalosFacade.crearOrden(orden, sessionBean.getUsuario());
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se ha generado el requerimiento con exito", "Acta de orden #" + orden.getId()));
        //Notificador.notificar(Notificador.correoOrdenBarajasCreada,
        //        "Se ha generado el requerimiento de compra de barajas con el n&uacute;mero de acta " + orden.getId() + ". Favor revisar la lista de ordenes de compra de barajas.",
        //        "Se ha generado un requerimiento de compra de barajas", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaRequerimientosRegalos.xhtml");
        } catch (IOException ex) {
        }
    }

    public void aprobar() {
        orden.setObservaciones(orden.getObservaciones()+sessionBean.getUsuario().getNombreUsuario()+":"+observaciones+".");
        sessionBean.regalosFacade.aprobarOrden(orden, sessionBean.getUsuario());
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se ha aprobado el requerimiento con exito", "Acta de orden #" + orden.getId()));
        //Notificador.notificar(Notificador.correoOrdenBarajasAprobada,
        //        "Se ha aprobado el requerimiento de compra de barajas con el n&uacute;mero de acta " + orden.getId() + ". Favor revisar la lista de ordenes de compra de barajas.",
        //        "Se ha aprobado un requerimiento de compra de barajas", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaRequerimientosRegalos.xhtml");
        } catch (IOException ex) {
        }
    }
    public void rechazar() {
        orden.setObservaciones(orden.getObservaciones()+sessionBean.getUsuario().getNombreUsuario()+":"+observaciones+".");
        
        sessionBean.regalosFacade.rechazarOrden(orden, sessionBean.getUsuario());
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se ha rechazado el requerimiento con exito", "Acta de orden #" + orden.getId()));
        //Notificador.notificar(Notificador.correoOrdenBarajasAprobada,
        //        "Se ha rechazado el requerimiento de compra de barajas con el n&uacute;mero de acta " + orden.getId() + ". Favor revisar la lista de ordenes de compra de barajas.",
        //        "Se ha rechazado un requerimiento de compra de barajas", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaOrdenesCompraBarajas.xhtml");
        } catch (IOException ex) {
        }
    }

    public void recibir() {
        orden.setObservaciones(orden.getObservaciones()+sessionBean.getUsuario().getNombreUsuario()+":"+observaciones+".");
        
        sessionBean.regalosFacade.recibirOrden(orden, sessionBean.getUsuario());
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se ha recibido el requerimiento con exito", "Acta de orden #" + orden.getId()));
        //Notificador.notificar(Notificador.correoOrdenBarajasRecibida,
        //        "Se ha recibido el requerimiento de compra de barajas con el n&uacute;mero de acta " + orden.getId() + ". Favor revisar la lista de ordenes de compra de barajas.",
        //        "Se ha recibido un requerimiento de compra de barajas", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaOrdenesCompraBarajas.xhtml");
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

    public OrdenCompraRegaloDTO getOrden() {
        return orden;
    }

    public void setOrden(OrdenCompraRegaloDTO orden) {
        this.orden = orden;
    }

}
