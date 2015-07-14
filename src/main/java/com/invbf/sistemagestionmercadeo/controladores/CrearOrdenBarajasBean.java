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
    
    private InventarioBarajasDTO inventario;
    

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
        inventario = new InventarioBarajasDTO();
        InventarioBarajasDTO invent = sessionBean.barajasFacade.getInventario();
        for(BarajasCantidad baraja :invent.getInventario()){
            inventario.getInventario().add(new BarajasCantidad(baraja.getId(),baraja.getBaraja()));
        }
    }
    
    public void crear() {
        try {
            int i = sessionBean.barajasFacade.crearOrdenBarajas(inventario, sessionBean.getUsuario());
            sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se ha generado la orden con exito", "Acta de orden #"+i));
            
            Notificador.notificar(Notificador.correoOrdenBarajasCreada, 
                    "Se ha creado la orden de compra de barajas con el n&uacute;mero de acta "+i+". Favor revisar la lista de ordenes de compra de barajas.", 
                    "Se ha creado una orden de compra de barajas", sessionBean.getUsuario().getUsuariodetalle().getCorreo());

            
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaOrdenesCompraBarajas.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListaSolicitudesEntregaLotesBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public InventarioBarajasDTO getInventario() {
        return inventario;
    }

    public void setInventario(InventarioBarajasDTO inventario) {
        this.inventario = inventario;
    }
    
}
