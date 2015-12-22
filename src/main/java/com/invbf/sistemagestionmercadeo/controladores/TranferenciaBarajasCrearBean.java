/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.dto.CasinoDto;
import com.invbf.sistemagestionmercadeo.dto.InventarioBarajasDTO;
import com.invbf.sistemagestionmercadeo.dto.TrasladoDTO;
import com.invbf.sistemagestionmercadeo.dto.UsuarioDTO;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.util.Mensajes;
import com.invbf.sistemagestionmercadeo.util.Notificador;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author ivan
 */
@ManagedBean
@ViewScoped
public class TranferenciaBarajasCrearBean implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private TrasladoDTO item;
    private List<InventarioBarajasDTO> casinos;
    private int casinoenviador;
    private int casinoreceptor;

    /**
     * @return the sessionBean
     */
    public SessionBean getSessionBean() {
        return sessionBean;
    }

    /**
     * @param sessionBean the sessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public TranferenciaBarajasCrearBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("barajas");
        if (!sessionBean.perfilViewMatch("verTransferencias") && !sessionBean.perfilViewMatch("enviarTransferencia") && !sessionBean.perfilViewMatch("recibirTransferencia")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }

        item = sessionBean.barajasFacade.getTransferenciaNueva();
        casinos = sessionBean.barajasFacade.getBodegas();
        casinoenviador = 0;
        casinoreceptor = 0;
    }

    public void crearTransferencia() {
        item.setCreador(new UsuarioDTO(sessionBean.getUsuario()));
        Integer id = sessionBean.barajasFacade.guardarTransferencia(item);
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Orden de transferencia de barajas creada con exito", "Acta N " + id + "."));
        Notificador.notificar(Notificador.correoOrdenTransferenciaCreada, "Se ha creado una orden de transferencias de barajas con el numero de acta "+id+". Favor revisar la lista de transferencias de barajas y prepare el paquete para el env&iacute;o.", "Orden de transferencia de barajas creada", "", new Casino(item.getSalaenviadora().getIdCasino()));

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaTransferenciasBarajas.xhtml");
        } catch (IOException ex) {
        }
    }

    public String onFlowProcess(FlowEvent event) {
        if (event.getOldStep().equals("salas")) {
            System.out.println("Creando transferencia");
            System.out.println(casinoenviador);
            System.out.println(casinoreceptor);
            if (casinoenviador != 0 && casinoreceptor != 0) {
                if (casinoenviador != casinoreceptor) {
                    item = sessionBean.barajasFacade.getTransferenciaNueva(casinoenviador, casinoreceptor);
                } else {
                    sessionBean.putMensaje(new Mensajes(Mensajes.ADVERTENCIA, "Esta colocando como sala que envía y sala receptora la misma sala", "Debe selecionar dos salas distintas!"));
                    sessionBean.printMensajes();
                    return event.getOldStep();
                }
            } else {
                sessionBean.putMensaje(new Mensajes(Mensajes.ADVERTENCIA, "No ha colocado la sala que envia o la receptora", "Debe selecionar una sala!"));
                sessionBean.printMensajes();
                return event.getOldStep();
            }
        }
        return event.getNewStep();
    }

    public TrasladoDTO getItem() {
        return item;
    }

    public void setItem(TrasladoDTO item) {
        this.item = item;
    }

    public List<InventarioBarajasDTO> getCasinos() {
        return casinos;
    }

    public void setCasinos(List<InventarioBarajasDTO> casinos) {
        this.casinos = casinos;
    }

    public int getCasinoenviador() {
        return casinoenviador;
    }

    public void setCasinoenviador(int casinoenviador) {
        this.casinoenviador = casinoenviador;
    }

    public int getCasinoreceptor() {
        return casinoreceptor;
    }

    public void setCasinoreceptor(int casinoreceptor) {
        this.casinoreceptor = casinoreceptor;
    }

}
