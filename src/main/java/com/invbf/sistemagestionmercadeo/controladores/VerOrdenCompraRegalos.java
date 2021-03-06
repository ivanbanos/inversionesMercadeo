/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.dto.BarajasCantidad;
import com.invbf.sistemagestionmercadeo.dto.OrdenCompraBarajaDTO;
import com.invbf.sistemagestionmercadeo.dto.OrdenCompraRegaloDTO;
import com.invbf.sistemagestionmercadeo.dto.RegalosCantidadDTO;
import com.invbf.sistemagestionmercadeo.entity.Casino;
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
    private String observaciones2;

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
        if (!sessionBean.perfilViewMatch("aprobarOrdenRegalo")
                && !sessionBean.perfilViewMatch("recibirOrdenRegalo")
                && !sessionBean.perfilViewMatch("verOrdenRegalo")
                && !sessionBean.perfilViewMatch("ingresarOrdenRegalo")) {
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
        sessionBean.printMensajes();
    }

    public void generar() {
        orden.setObservaciones(orden.getObservaciones() + sessionBean.getUsuario().getNombreUsuario() + ":" + observaciones + ".");

        sessionBean.regalosFacade.crearOrden(orden, sessionBean.getUsuario());
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se ha generado el requerimiento con exito", "Acta de orden #" + orden.getId()));
        Notificador.notificar(Notificador.correoRegaloOrdenCreada,
                "Se ha generado el requerimiento de compra de regalos con el n&uacute;mero de acta " + orden.getId() + ". Favor revisar la lista de ordenes de compra de regalos.",
                "Se ha generado un requerimiento de compra de regalos", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaRequerimientosRegalos.xhtml");
        } catch (IOException ex) {
        }
    }

    public void aprobar() {
        orden.setObservaciones(orden.getObservaciones() + sessionBean.getUsuario().getNombreUsuario() + ":" + observaciones);
        sessionBean.regalosFacade.aprobarOrden(orden, sessionBean.getUsuario());
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se ha aprobado el requerimiento con exito", "Acta de orden #" + orden.getId()));
        String body = "Se ha aprobado el requerimiento de compra de obsequios con el n&uacute;mero de acta " + orden.getId();
        
        body += "<br /><table style=\"border: 1px solid black;\">";
        body += "<tr><th style=\"border: 1px solid black;\">Obsequio</th><th style=\"border: 1px solid black;\">Descripci&oacute;n</th><th style=\"border: 1px solid black;\">Imagen</th><th style=\"border: 1px solid black;\">Cantidad</th></tr>";
        for(RegalosCantidadDTO regalo : orden.getCantidades()){
        
        body += "<tr>"
                + "<td style=\"border: 1px solid black;\">"+regalo.getRegalo().getNombre()+"</td>"
                + "<td style=\"border: 1px solid black;\">"+regalo.getRegalo().getDescripcion()+"</td>"
                + "<td style=\"border: 1px solid black;\"><img src=\"http://regalos.ibfcolombia.com/"+regalo.getRegalo().getFileName()+"\" alt=\"Regalo\" style=\"width:100px;\"></td>"
                + "<td style=\"border: 1px solid black;\">"+regalo.getCantidadAprobada()+"</td></tr>";
        }
        body += "</table>";
        
        
        body+= "<br />Observaciones: "+orden.getObservaciones()+"<br/>Favor revisar la lista de ordenes de compra de regalos.";
        
        Notificador.notificar(Notificador.correoRegaloOrdenAprobada,
                body,
                "Se ha aprobado un requerimiento de compra de regalos", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaRequerimientosRegalos.xhtml");
        } catch (IOException ex) {
        }
    }

    public void rechazar() {
        orden.setObservaciones(orden.getObservaciones() + sessionBean.getUsuario().getNombreUsuario() + ":" + observaciones + ".");

        sessionBean.regalosFacade.rechazarOrden(orden, sessionBean.getUsuario());
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se ha rechazado el requerimiento con exito", "Acta de orden #" + orden.getId()));
        Notificador.notificar(Notificador.correoRegaloOrdenAprobada,
                "Se ha rechazado el requerimiento de compra de regalos con el n&uacute;mero de acta " + orden.getId() + ". Favor revisar la lista de ordenes de compra de regalos.",
                "Se ha rechazado un requerimiento de compra de regalos", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaRequerimientosRegalos.xhtml");
        } catch (IOException ex) {
        }
    }

    public void recibir() {
        boolean seguarda = true;
        for (RegalosCantidadDTO regalos : orden.getCantidades()) {
            if (regalos.getCantidadAprobada().intValue() != regalos.getCantidadR() && (regalos.getJustificacion() == null || "".equals(regalos.getJustificacion()))) {
                seguarda = false;

            }
        }
        if (seguarda) {

            orden.setObservaciones(orden.getObservaciones() + sessionBean.getUsuario().getNombreUsuario() + ":" + observaciones + ".");

            sessionBean.regalosFacade.recibirOrden(orden, sessionBean.getUsuario());
            sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se ha recibido el requerimiento con exito", "Acta de orden #" + orden.getId()));
            Notificador.notificar(Notificador.correoRegaloOrdenRecibida,
                    "Se ha recibido el requerimiento de compra de regalos con el n&uacute;mero de acta " + orden.getId() + ". Favor revisar la lista de ordenes de compra de regalos.",
                    "Se ha recibido un requerimiento de compra de regalos", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("ListaRequerimientosRegalos.xhtml");
            } catch (IOException ex) {
            }
        } else {
            sessionBean.putMensaje(new Mensajes(Mensajes.ERROR, "Error con las cantidades recibidas", "Si recibió menos cantidad que las aprobadas, debe colocar una justificación."));
            sessionBean.printMensajes();
        }
    }

    public void ingresar() {
        orden.setObservaciones(orden.getObservaciones() + sessionBean.getUsuario().getNombreUsuario() + ":" + observaciones + ".");

        sessionBean.regalosFacade.ingresarOrden(orden, sessionBean.getUsuario());
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se ha recibido el requerimiento con exito", "Acta de orden #" + orden.getId()));
        Notificador.notificar(Notificador.correoRegaloOrdenRecibida,
                "Se ha recibido el requerimiento de compra de regalos con el n&uacute;mero de acta " + orden.getId() + ". Favor revisar la lista de ordenes de compra de regalos.",
                "Se ha recibido un requerimiento de compra de regalos", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaRequerimientosRegalos.xhtml");
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

    public String getObservaciones2() {
        return observaciones2;
    }

    public void setObservaciones2(String observaciones2) {
        this.observaciones2 = observaciones2;
    }

}
