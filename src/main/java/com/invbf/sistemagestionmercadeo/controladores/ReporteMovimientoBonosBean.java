/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Bono;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.util.AnalisisBono;
import com.invbf.sistemagestionmercadeo.util.CasinoBoolean;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class ReporteMovimientoBonosBean {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<Bono> bonosAnalizar;
    private List<CasinoBoolean> casinos;
    private Date hasta;
    private Date desde;
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
        Calendar now = Calendar.getInstance();
        Calendar monthago = Calendar.getInstance();
        monthago.add(Calendar.MONTH, -1);
        hasta = now.getTime();
        desde = monthago.getTime();
        bonosAnalizar = sessionBean.marketingUserFacade.getAllBonos();
        List<Casino> casinosNormales = sessionBean.adminFacade.findAllCasinos();
        casinos = new ArrayList<CasinoBoolean>();
        for (Casino casinosNormale : casinosNormales) {
            casinos.add(new CasinoBoolean(casinosNormale, true));
        }
        analizarBonos();
    }

    public void buscarBonosPorCasinosYFecha() {
        bonosAnalizar = new ArrayList<Bono>();
        for (CasinoBoolean casino : casinos) {
            if (casino.isSelected()) {
                bonosAnalizar.addAll(sessionBean.managerUserFacade.getBonosPorFechasYCasinos(desde, hasta, casino.getCasino()));
            }
        }
        analizarBonos();
    }

    public List<Bono> getBonosAnalizar() {
        return bonosAnalizar;
    }

    public void setBonosAnalizar(List<Bono> bonosAnalizar) {
        this.bonosAnalizar = bonosAnalizar;
    }

    public List<CasinoBoolean> getCasinos() {
        return casinos;
    }

    public void setCasinos(List<CasinoBoolean> casinos) {
        this.casinos = casinos;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public List<AnalisisBono> getPromocional() {
        return promocional;
    }

    public void setPromocional(List<AnalisisBono> promocional) {
        this.promocional = promocional;
    }

    private void analizarBonos() {
        promocional = new ArrayList<AnalisisBono>();
        for (Bono bono : bonosAnalizar) {
            if(!promocional.contains(new AnalisisBono(bono.getPropositosEntregaid().getNombre()))){
                promocional.add(new AnalisisBono(bono.getPropositosEntregaid().getNombre()));
            }
            AnalisisBono ab = promocional.get(promocional.indexOf(new AnalisisBono(bono.getPropositosEntregaid().getNombre())));
            ab.addBono(bono);
        }
    }

}
