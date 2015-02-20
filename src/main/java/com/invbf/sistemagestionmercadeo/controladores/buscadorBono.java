/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Bono;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Denominacion;
import com.invbf.sistemagestionmercadeo.entity.Tipobono;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import java.io.IOException;
import java.io.Serializable;
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
public class buscadorBono implements Serializable{

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<Casino> casinos;
    private List<Denominacion> denominaciones;
    private Casino casinoSelected;
    private Denominacion denoinacionSelected;
    private Bono bono;
    private String consecutivo;

    public buscadorBono() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");
        if (!sessionBean.perfilViewMatch("cambioEstadoBono")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        casinos = sessionBean.adminFacade.findAllCasinos();
        denominaciones = sessionBean.adminFacade.findAllDenominaciones();
        bono = new Bono();
        bono.setCasino(new Casino());
        bono.setValidador(new Usuario());
        bono.setAutorizador(new Usuario());
        bono.setCliente(new Cliente());
        bono.setDenominacion(new Denominacion());
        bono.setTipo(new Tipobono());
        casinoSelected = new Casino();
        denoinacionSelected = new Denominacion();

    }

    public void buscarBono() {
        System.out.println("Bono buscando");
        bono = sessionBean.adminFacade.buscarBono(casinoSelected, denoinacionSelected, consecutivo);
        if (bono == null) {
            bono = new Bono();
            bono.setCasino(new Casino());
            bono.setValidador(new Usuario());
            bono.setAutorizador(new Usuario());
            bono.setCliente(new Cliente());
            bono.setDenominacion(new Denominacion());
            FacesUtil.addErrorMessage("Bono no encontrado", "No existe bono con esos parametros");
        } else {
            FacesUtil.addInfoMessage("Bono encontrado");
        }
    }

    public void cambiarEstadoBono(String estado) {
        bono.setEstado(estado);
        sessionBean.adminFacade.guardarBono(bono);
        FacesUtil.addInfoMessage("Bono" + bono.getConsecutivo() + " guardado con exito!", "Nuevo estado " + estado);
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public List<Casino> getCasinos() {
        return casinos;
    }

    public void setCasinos(List<Casino> casinos) {
        this.casinos = casinos;
    }

    public List<Denominacion> getDenominaciones() {
        return denominaciones;
    }

    public void setDenominaciones(List<Denominacion> denominaciones) {
        this.denominaciones = denominaciones;
    }

    public Bono getBono() {
        return bono;
    }

    public void setBono(Bono bono) {
        this.bono = bono;
    }

    public Casino getCasinoSelected() {
        return casinoSelected;
    }

    public void setCasinoSelected(Casino casinoSelected) {
        this.casinoSelected = casinoSelected;
    }

    public Denominacion getDenoinacionSelected() {
        return denoinacionSelected;
    }

    public void setDenoinacionSelected(Denominacion denoinacionSelected) {
        this.denoinacionSelected = denoinacionSelected;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

}
