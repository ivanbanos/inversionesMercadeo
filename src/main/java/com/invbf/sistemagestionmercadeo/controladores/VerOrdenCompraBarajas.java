/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.dto.BarajasCantidad;
import com.invbf.sistemagestionmercadeo.dto.OrdenCompraBarajaDTO;
import com.invbf.sistemagestionmercadeo.dto.RegalosCantidadDTO;
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
    private Float total;
    private String observaciones;

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
                FacesContext.getCurrentInstance().getExternalContext().redirect("ListaOrdenesCompraBarajas.xhtml");
            } catch (IOException ex) {
            }
        }
        idOrden = (Integer) sessionBean.getAttributes("orden");
        orden = sessionBean.barajasFacade.getOrden(idOrden);
        total = 0f;
        for (BarajasCantidad baraja : orden.getCantidades()) {
            if (baraja.getBaraja().getValorpromedio() != null) {
                total = baraja.getCantidad() * baraja.getBaraja().getValorpromedio();
            }
        }
    }

    public OrdenCompraBarajaDTO getOrden() {
        return orden;
    }

    public void setOrden(OrdenCompraBarajaDTO orden) {
        this.orden = orden;
    }

    public void crearrOrden() {
        orden.setObservaciones(orden.getObservaciones() + sessionBean.getUsuario().getNombreUsuario() + ":" + observaciones + ".");

        sessionBean.barajasFacade.crearOrden(orden, sessionBean.getUsuario());
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se ha generado el requerimiento con exito", "Acta de orden #" + orden.getId()));
        Notificador.notificar(Notificador.correoOrdenBarajasCreada,
                "Se ha generado el requerimiento de compra de barajas con el n&uacute;mero de acta " + orden.getId() + ". Favor revisar la lista de ordenes de compra de barajas.",
                "Se ha generado un requerimiento de compra de barajas", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaOrdenesCompraBarajas.xhtml");
        } catch (IOException ex) {
        }
    }

    public void aprobarOrden() {
        orden.setObservaciones(orden.getObservaciones() + sessionBean.getUsuario().getNombreUsuario() + ":" + observaciones + ".");
        sessionBean.barajasFacade.aprobarOrden(orden, sessionBean.getUsuario());
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se ha aprobado el requerimiento con exito", "Acta de orden #" + orden.getId()));
        String body = "Se ha aprobado el requerimiento de compra de barajas con el n&uacute;mero de acta " + orden.getId() + ". ";
        
        body += "<br /><table style=\"border: 1px solid black;\">";
        body += "<tr><th style=\"border: 1px solid black;\">Baraja</th><th style=\"border: 1px solid black;\">Cantidad</th></tr>";
        for(BarajasCantidad baraja : orden.getCantidades()){
        
        body += "<tr>"
                + "<td style=\"border: 1px solid black;\">"+baraja.getBaraja().getMaterial().getNombre()+" "+baraja.getBaraja().getMaterial().getDescripcion()==null?"":baraja.getBaraja().getMaterial().getDescripcion()+" "+baraja.getBaraja().getColor()+"</td>"
                + "<td style=\"border: 1px solid black;\">"+baraja.getCantidadR()+"</td></tr>";
        }
        body += "</table>";
        body+= "<br />Observaciones: "+orden.getObservaciones()+"<br/>Favor revisar la lista de ordenes de compra de barajas.";
        
        

        Notificador.notificar(Notificador.correoOrdenBarajasAprobada,
                body,
                "Se ha aprobado un requerimiento de compra de barajas", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaOrdenesCompraBarajas.xhtml");
        } catch (IOException ex) {
        }
    }

    public void rechazarOrden() {
        orden.setObservaciones(orden.getObservaciones() + sessionBean.getUsuario().getNombreUsuario() + ":" + observaciones + ".");

        sessionBean.barajasFacade.rechazarOrden(orden, sessionBean.getUsuario());
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se ha rechazado el requerimiento con exito", "Acta de orden #" + orden.getId()));
        Notificador.notificar(Notificador.correoOrdenBarajasAprobada,
                "Se ha rechazado el requerimiento de compra de barajas con el n&uacute;mero de acta " + orden.getId() + ". Favor revisar la lista de ordenes de compra de barajas.",
                "Se ha rechazado un requerimiento de compra de barajas", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaOrdenesCompraBarajas.xhtml");
        } catch (IOException ex) {
        }
    }

    public void recibirOrden() {
        orden.setObservaciones(orden.getObservaciones() + sessionBean.getUsuario().getNombreUsuario() + ":" + observaciones + ".");

        sessionBean.barajasFacade.recibirOrden(idOrden, sessionBean.getUsuario());
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se ha recibido el requerimiento con exito", "Acta de orden #" + orden.getId()));
        Notificador.notificar(Notificador.correoOrdenBarajasRecibida,
                "Se ha recibido el requerimiento de compra de barajas con el n&uacute;mero de acta " + orden.getId() + ". Favor revisar la lista de ordenes de compra de barajas.",
                "Se ha recibido un requerimiento de compra de barajas", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
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

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

}
