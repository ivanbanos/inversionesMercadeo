/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.dto.ClienteRegaloDTO;
import com.invbf.sistemagestionmercadeo.dto.RegaloCanje;
import com.invbf.sistemagestionmercadeo.util.Mensajes;
import com.invbf.sistemagestionmercadeo.util.Notificador;
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
public class RegaloEntregarBean implements Serializable {

    private String buscar;
    private List<RegaloCanje> regalos;
    private RegaloCanje regalo;

    public RegaloEntregarBean() {
    }

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("regalos");
        if (!sessionBean.perfilViewMatch("RegaloCanjear")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        regalos = new ArrayList<RegaloCanje>();
        regalo = new RegaloCanje();
    }

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    public List<RegaloCanje> getRegalos() {
        return regalos;
    }

    public void setRegalos(List<RegaloCanje> regalos) {
        this.regalos = regalos;
    }

    public void buscarRegalos() {
        System.out.println("Buscando regalos");
        regalos = sessionBean.regalosFacade.buscarRegalos(buscar, sessionBean.getUsuario());
    }

    public RegaloCanje getRegalo() {
        return regalo;
    }

    public void setRegalo(RegaloCanje regalo) {
        this.regalo = regalo;
    }

    public void setRegaloById(int i) {
        System.out.println("id" + i);
        regalo = regalos.get(i);
    }

    public void entregarRegalo() {
        System.out.println("id" + regalo.getRegalo().getNombre());
        sessionBean.regalosFacade.entregar(regalo, sessionBean.getUsuario());
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Se ha entregado el regalos con exito", "Regalo " + regalo.getRegalo().getNombre() + " Cliente " + regalo.getCliente().getNombres() + " " + regalo.getCliente().getApellidos()));
        Notificador.notificar(Notificador.correoRegaloEntregado,
                "Se ha entregado el regalo "+regalo.getRegalo().getNombre()+" de "+regalo.getCliente().getNombres()+" "+regalo.getCliente().getApellidos()+" con exito.",
                "Se ha entregado un regalo", sessionBean.getUsuario().getUsuariodetalle().getCorreo());

        regalos = new ArrayList<RegaloCanje>();
        regalo = new RegaloCanje();
    }
}
