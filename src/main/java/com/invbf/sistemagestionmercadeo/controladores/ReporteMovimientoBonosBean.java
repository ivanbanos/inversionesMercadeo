/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Bono;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Propositoentrega;
import com.invbf.sistemagestionmercadeo.entity.Tipobono;
import com.invbf.sistemagestionmercadeo.util.AnalisisBono;
import com.invbf.sistemagestionmercadeo.util.CasinoBoolean;
import com.invbf.sistemagestionmercadeo.util.ClienteSGBDTO;
import com.invbf.sistemagestionmercadeo.util.Mensajes;
import com.invbf.sistemagestionmercadeo.util.PropositosBoolean;
import com.invbf.sistemagestionmercadeo.util.TipoBonoBoolean;
import com.invbf.sistemagestionmercadeo.util.estadisticaBonos;
import com.invbf.sistemagestionmercadeo.util.loteBonoSolicitud;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
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
public class ReporteMovimientoBonosBean implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<CasinoBoolean> casinos;
    private List<PropositosBoolean> propositos;
    private List<TipoBonoBoolean> tipos;
    private List<Integer> anos;
    private Integer ano;
    private Integer mes;
    private Integer annodesde;
    private Integer mesdesde;
    private List<Bono> bonos;
    private List<Bono> vencidos;

    private estadisticaBonos estadistica;

    private List<AnalisisBono> promocional;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("reportes");

        if (!sessionBean.perfilViewMatch("Reportes")) {
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        sessionBean.revisarEstadoBonos();
        List<Casino> casinosNormales = sessionBean.adminFacade.findAllCasinos();
        casinos = new ArrayList<CasinoBoolean>();
        for (Casino casinosNormale : casinosNormales) {
            casinos.add(new CasinoBoolean(casinosNormale, true));
        }
        List<Propositoentrega> proposotos = sessionBean.adminFacade.findAllPropositosentrega();
        propositos = new ArrayList<PropositosBoolean>();
        for (Propositoentrega proposito : proposotos) {
            propositos.add(new PropositosBoolean(proposito, true));
        }
        List<Tipobono> tipobonos = sessionBean.adminFacade.findAllTiposbonos();
        tipos = new ArrayList<TipoBonoBoolean>();
        for (Tipobono tipo : tipobonos) {
            tipos.add(new TipoBonoBoolean(tipo, true));
        }
        anos = new ArrayList<Integer>();
        Calendar c = Calendar.getInstance();
        ano = c.get(Calendar.YEAR);
        mes = c.get(Calendar.MONTH);
        for (int i = 2015; i <= c.get(Calendar.YEAR); i++) {
            anos.add(i);
        }
        annodesde = ano;
        mesdesde = mes;
        buscarBonos();
    }

    public void buscarBonosPorCasinosYFecha() {

        System.gc();
    }

    public void buscarBonos() {
        if (annodesde > ano || ((annodesde == ano) && (mesdesde > mes))) {
            sessionBean.putMensaje(new Mensajes(Mensajes.ERROR, "Error en las fechas", "La fecha desde debe ser menor a la fecha hasta"));
        } else {
            bonos = sessionBean.marketingUserFacade.getBonosporCasinoPropositoTipoFecha(casinos, propositos, tipos, ano, mes, annodesde, mesdesde);
            resolverbonos();
        }
    }

    public List<CasinoBoolean> getCasinos() {
        return casinos;
    }

    public void setCasinos(List<CasinoBoolean> casinos) {
        this.casinos = casinos;
    }

    public List<PropositosBoolean> getPropositos() {
        return propositos;
    }

    public void setPropositos(List<PropositosBoolean> propositos) {
        this.propositos = propositos;
    }

    public List<TipoBonoBoolean> getTipos() {
        return tipos;
    }

    public void setTipos(List<TipoBonoBoolean> tipos) {
        this.tipos = tipos;
    }

    public List<Integer> getAnos() {
        return anos;
    }

    public void setAnos(List<Integer> anos) {
        this.anos = anos;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAnnodesde() {
        return annodesde;
    }

    public void setAnnodesde(Integer annodesde) {
        this.annodesde = annodesde;
    }

    public Integer getMesdesde() {
        return mesdesde;
    }

    public void setMesdesde(Integer mesdesde) {
        this.mesdesde = mesdesde;
    }

    public List<Bono> getBonos() {
        return bonos;
    }

    public void setBonos(List<Bono> bonos) {
        this.bonos = bonos;
    }

    public List<AnalisisBono> getPromocional() {
        return promocional;
    }

    public void setPromocional(List<AnalisisBono> promocional) {
        this.promocional = promocional;
    }

    private void resolverbonos() {
        estadistica = new estadisticaBonos();
        estadistica.setBonosAprobados(0);
        estadistica.setBonosCanjeados(0);
        estadistica.setMontoAprobado(0);
        estadistica.setMontoCanjeado(0);
        estadistica.setMontoSolicitado(0);
        vencidos = new ArrayList<Bono>();
        for (Bono bono : bonos) {
            if (!bono.getEstado().equals("ANULADO")) {

                estadistica.setBonosAprobados(estadistica.getBonosAprobados() + 1);
                estadistica.setMontoAprobado(estadistica.getMontoAprobado() + (bono.getDenominacion().getValor()));
                if (bono.getEstado().equals("CANJEADO")) {
                    estadistica.setBonosCanjeados(estadistica.getBonosCanjeados() + 1);
                    estadistica.setMontoCanjeado(estadistica.getMontoCanjeado() + (bono.getDenominacion().getValor()));

                } else {
                    vencidos.add(bono);
                }

            }
        }
        estadistica.setEfectividad();
    }

    public estadisticaBonos getEstadistica() {
        return estadistica;
    }

    public void setEstadistica(estadisticaBonos estadistica) {
        this.estadistica = estadistica;
    }

    public List<Bono> getVencidos() {
        return vencidos;
    }

    public void setVencidos(List<Bono> vencidos) {
        this.vencidos = vencidos;
    }

}
