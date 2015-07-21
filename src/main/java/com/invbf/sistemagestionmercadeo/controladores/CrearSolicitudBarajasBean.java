/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.dto.BarajasCantidad;
import com.invbf.sistemagestionmercadeo.dto.InventarioBarajasDTO;
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
public class CrearSolicitudBarajasBean implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    InventarioBarajasDTO invent;

    List<InventarioBarajasDTO> lista;

    public CrearSolicitudBarajasBean() {
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
        if (sessionBean.getUsuario().getCasinoList() == null || sessionBean.getUsuario().getCasinoList().isEmpty()) {
            lista = new ArrayList<InventarioBarajasDTO>();
        } else {
            lista = sessionBean.barajasFacade.getBodegasParaSol(sessionBean.getUsuario());
        }
        invent = new InventarioBarajasDTO();
    }

    public void crear() {
        try {
            int i = sessionBean.barajasFacade.crearSolicitudBarajas(invent, sessionBean.getUsuario());
            sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se ha generado la solicitud con exito", "Acta de orden #" + i));
            Notificador.notificar(Notificador.correoSolicitudBarajaCreada,
                    "Se ha creado la solicitud de barajas con el n&uacute;mero de acta " + i + ". Favor revisar la lista de solicitudes de barajas.",
                    "Se ha creado una solicitud de barajas", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaSolicitudBarajas.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        invent = new InventarioBarajasDTO();

    }

    public InventarioBarajasDTO getInvent() {
        return invent;
    }

    public void setInvent(InventarioBarajasDTO invent) {

        this.invent = invent;
    }

    public List<InventarioBarajasDTO> getLista() {
        return lista;
    }

    public void setLista(List<InventarioBarajasDTO> lista) {
        this.lista = lista;
    }

    public void getinv() {
        System.err.println(invent.getId());
        for (InventarioBarajasDTO lista1 : lista) {
            System.err.println(lista1.getId());

        }
        this.invent = lista.get(lista.indexOf(new InventarioBarajasDTO(invent.getId())));
    }
}
