/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Bono;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
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
public class BonosRecibirBean  implements Serializable{

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<Casino> casinos;
    private Casino casinoSelected;
    private List<Bono> bonosCasinoEntregados;
    private List<Bono> bonosCasinoEntregadosSelected;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public BonosRecibirBean() {

    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("requisiciones");
        if (!sessionBean.perfilViewMatch("Recibirbono")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        sessionBean.revisarEstadoBonos();
        casinos = sessionBean.getUsuario().getCasinoList();
        bonosCasinoEntregados = new ArrayList<Bono>();
        bonosCasinoEntregadosSelected = new ArrayList<Bono>();
        casinoSelected = new Casino();
    }

    public void buscarBonosValidadosPorCasino() {
        System.out.println("Buscar bonos");
        System.out.println("casino " + casinoSelected.getIdCasino());
        casinoSelected = casinos.get(casinos.indexOf(new Casino(casinoSelected.getIdCasino())));
        bonosCasinoEntregados = sessionBean.marketingUserFacade.getBonosPorEstadoYCasino("ENTREGADO", casinoSelected);
        System.out.println(bonosCasinoEntregados.size());
        if (bonosCasinoEntregados.isEmpty()) {
            FacesUtil.addWarnMessage("No existen bono por recibir", "");
        }
    }

    public List<Casino> getCasinos() {
        return casinos;
    }

    public void setCasinos(List<Casino> casinos) {
        this.casinos = casinos;
    }

    public Casino getCasinoSelected() {
        return casinoSelected;
    }

    public void setCasinoSelected(Casino casinoSelected) {
        this.casinoSelected = casinoSelected;
    }

    public void recibir() {
        if (!bonosCasinoEntregadosSelected.isEmpty()) {
            for (Bono bono : bonosCasinoEntregadosSelected) {
                bono.setEstado("EN SALA");
            }
            sessionBean.marketingUserFacade.guardarBonos(bonosCasinoEntregadosSelected);
            bonosCasinoEntregados = sessionBean.marketingUserFacade.getBonosPorEstadoYCasino("ENTREGADO", casinoSelected);
            bonosCasinoEntregadosSelected = new ArrayList<Bono>();
            String body = "Se han cambiado el estado de algunos bonos. Nuevo estado = RECIBIDO";
            Notificador.notificar(Notificador.SOLICITUD_RECIBO_BONOS, body, "Estado de bonos cambiado a recibidos", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
            FacesUtil.addInfoMessage("Estado de bonos cambiado", "Nuevo estado: RECIBIDO");
        }
    }

    public List<Bono> getBonosCasinoEntregados() {
        return bonosCasinoEntregados;
    }

    public void setBonosCasinoEntregados(List<Bono> bonosCasinoEntregados) {
        this.bonosCasinoEntregados = bonosCasinoEntregados;
    }

    public List<Bono> getBonosCasinoEntregadosSelected() {
        return bonosCasinoEntregadosSelected;
    }

    public void setBonosCasinoEntregadosSelected(List<Bono> bonosCasinoEntregadosSelected) {
        this.bonosCasinoEntregadosSelected = bonosCasinoEntregadosSelected;
    }

}
