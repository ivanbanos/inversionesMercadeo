/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Bono;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import java.io.IOException;
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
public class BonoEntregaCliente {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<Casino> casinos;
    private Casino casinoSelected;
    private List<Bono> bonosCasinoEntregados;
    private List<Bono> bonosCasinoEntregadosSelected;
    private String nombres;
    private String apellidos;
    private String identificacion;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public BonoEntregaCliente() {

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
        bonosCasinoEntregados = sessionBean.marketingUserFacade.getBonosPorEstadoYCasino("EN SALA", casinoSelected);
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

    public void entregar() {

        if (!bonosCasinoEntregadosSelected.isEmpty()) {
            for (Bono bono : bonosCasinoEntregadosSelected) {
                bono.setEstado("ENTREGADO CLIENTE");
            }
            sessionBean.marketingUserFacade.guardarBonos(bonosCasinoEntregadosSelected);
            bonosCasinoEntregados = sessionBean.marketingUserFacade.getBonosPorEstadoYCasino("EN SALA", casinoSelected);
            bonosCasinoEntregadosSelected = new ArrayList<Bono>();
            FacesUtil.addInfoMessage("Entregados "+bonosCasinoEntregadosSelected.size()+" bonos", "");
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

    public void buscarBonosPorCliente() {
        System.out.println("Buscar bonos");
        System.out.println("casino " + casinoSelected.getIdCasino());
        casinoSelected = casinos.get(casinos.indexOf(new Casino(casinoSelected.getIdCasino())));
        bonosCasinoEntregados = sessionBean.marketingUserFacade.getBonosPorAtributos("EN SALA", casinoSelected, nombres, apellidos, identificacion, "");
        System.out.println(bonosCasinoEntregados.size());
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

}
