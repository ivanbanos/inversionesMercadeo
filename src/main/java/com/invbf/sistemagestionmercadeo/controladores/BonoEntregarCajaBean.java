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
public class BonoEntregarCajaBean  implements Serializable{

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<Casino> casinos;
    private Casino casinoSelected;
    private List<Bono> bonosCasinoPorEntregar;
    private List<Bono> bonosCasinoPorEntregarSelected;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public BonoEntregarCajaBean() {

    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("requisiciones");
        if (!sessionBean.perfilViewMatch("Entregarbonocaja")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        sessionBean.revisarEstadoBonos();
        casinos = sessionBean.marketingUserFacade.findAllCasinos();
        bonosCasinoPorEntregar = new ArrayList<Bono>();
        bonosCasinoPorEntregarSelected = new ArrayList<Bono>();
        casinoSelected = new Casino();
    }

    public void buscarBonosValidadosPorCasino() {
        System.out.println("Buscar bonos");
        System.out.println("casino " + casinoSelected.getIdCasino());
        casinoSelected = casinos.get(casinos.indexOf(new Casino(casinoSelected.getIdCasino())));
        bonosCasinoPorEntregar = sessionBean.marketingUserFacade.getBonosPorEstadoYCasino("VERIFICADO", casinoSelected);
        System.out.println(bonosCasinoPorEntregar.size());
        if (bonosCasinoPorEntregar.isEmpty()) {
            FacesUtil.addWarnMessage("No existen bono por entregar", "");
        }
    }

    public List<Casino> getCasinos() {
        return casinos;
    }

    public void setCasinos(List<Casino> casinos) {
        this.casinos = casinos;
    }

    public List<Bono> getBonosCasinoPorEntregar() {
        return bonosCasinoPorEntregar;
    }

    public void setBonosCasinoPorEntregar(List<Bono> bonosCasinoPorEntregar) {
        this.bonosCasinoPorEntregar = bonosCasinoPorEntregar;
    }

    public Casino getCasinoSelected() {
        return casinoSelected;
    }

    public void setCasinoSelected(Casino casinoSelected) {
        this.casinoSelected = casinoSelected;
    }

    public List<Bono> getBonosCasinoPorEntregarSelected() {
        return bonosCasinoPorEntregarSelected;
    }

    public void setBonosCasinoPorEntregarSelected(List<Bono> bonosCasinoPorEntregarSelected) {
        this.bonosCasinoPorEntregarSelected = bonosCasinoPorEntregarSelected;
    }

    public void entregar() {
        if (!bonosCasinoPorEntregarSelected.isEmpty()) {
            for (Bono bono : bonosCasinoPorEntregarSelected) {
                bono.setEstado("ENTREGADO");
            }
            sessionBean.marketingUserFacade.guardarBonos(bonosCasinoPorEntregarSelected);
            bonosCasinoPorEntregar = sessionBean.marketingUserFacade.getBonosPorEstadoYCasino("VERIFICADO", casinoSelected);
            bonosCasinoPorEntregarSelected = new ArrayList<Bono>();

            String body = "Se han cambiado el estado de algunos bonos. Nuevo estado = ENTREGADO.";
            Notificador.notificar(Notificador.SOLICITUD_ENTREGA_BONOS, body, "Estado de bonos cambiado a entregados", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
            FacesUtil.addInfoMessage("Estado de bonos cambiado", "Nuevo estado: ENTREGADO");
        }
    }
}
