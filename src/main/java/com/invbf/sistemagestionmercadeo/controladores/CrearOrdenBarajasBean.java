/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.dto.BarajasCantidad;
import com.invbf.sistemagestionmercadeo.dto.InventarioBarajasDTO;
import com.invbf.sistemagestionmercadeo.dto.OrdenCompraBarajaDTO;
import com.invbf.sistemagestionmercadeo.util.Mensajes;
import com.invbf.sistemagestionmercadeo.util.Notificador;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
public class CrearOrdenBarajasBean implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    List<InventarioBarajasDTO> invent;
    private String observaciones;

    public CrearOrdenBarajasBean() {
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
        invent = sessionBean.barajasFacade.getBodegas();
        for (InventarioBarajasDTO iventario : invent) {
            for (BarajasCantidad baraja : iventario.getInventario()) {
                baraja.setCantidad(0);
                baraja.setCantidadR(0);
            }
        }

    }

    public void crear() {
        try {
            boolean todosCero = true;
            for (InventarioBarajasDTO inventario : invent) {
                for (BarajasCantidad baraja : inventario.getInventario()) {
                    if (baraja.getCantidad() != 0) {
                        todosCero = false;
                        break;
                    }
                }
            }
            if (todosCero) {
                sessionBean.putMensaje(new Mensajes(Mensajes.ADVERTENCIA, "Todas las cantidades estan en 0.", "No se puede guardar una solicitud con todas las cantidades en 0."));
                sessionBean.printMensajes();
            } else {
                int i = sessionBean.barajasFacade.crearOrdenBarajas(invent, sessionBean.getUsuario(), observaciones);
                sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se ha generado el requerimiento con exito", "Acta de orden #" + i));

                Notificador.notificar(Notificador.correoOrdenBarajasCreada,
                        "Se ha creado el requerimiento de compra de barajas con el n&uacute;mero de acta " + i + ". Favor revisar la lista de requerimientos de compra de barajas.",
                        "Se ha creado un requerimiento de compra de barajas", sessionBean.getUsuario().getUsuariodetalle().getCorreo());

                FacesContext.getCurrentInstance().getExternalContext().redirect("ListaOrdenesCompraBarajas.xhtml");
            }
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<InventarioBarajasDTO> getInvent() {
        return invent;
    }

    public void setInvent(List<InventarioBarajasDTO> invent) {
        this.invent = invent;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

}
