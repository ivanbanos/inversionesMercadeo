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
import com.invbf.sistemagestionmercadeo.util.PropositosBoolean;
import com.invbf.sistemagestionmercadeo.util.TipoBonoBoolean;
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
public class ReporteMovimientoBonosBean implements Serializable{

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<CasinoBoolean> casinos;
    private List<PropositosBoolean> propositos;
    private List<TipoBonoBoolean> tipos;
    private List<Integer> anos;
    private Integer ano;
    private Integer mes;
    private List<Bono> bonos;

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
        Calendar now = Calendar.getInstance();
        Calendar monthago = Calendar.getInstance();
        monthago.add(Calendar.MONTH, 1);
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
        for(int i = 2015; i<=c.get(Calendar.YEAR) ; i++){
            anos.add(i);
        }
        buscarBonos();
    }

    public void buscarBonosPorCasinosYFecha() {

        
        System.gc();
    }

    private void buscarBonos() {
        bonos = sessionBean.marketingUserFacade.getBonosporCasinoPropositoTipoFecha(casinos, propositos, tipos, ano, mes);
    }

    
}
