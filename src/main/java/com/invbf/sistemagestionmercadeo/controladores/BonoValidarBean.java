/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Bono;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
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
public class BonoValidarBean {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<Bono> bonosPorValidar;
    private List<Bono> bonosSeleccionados;
    private List<Casino> casinos;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public BonoValidarBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("bonos");
        if (!sessionBean.perfilViewMatch("Verbonosporvalidar")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        if (sessionBean.perfilViewMatch("verTodosClientes")) {
            bonosPorValidar = sessionBean.marketingUserFacade.getBonosPorEstado("POR VALIDAR");
        } else {
            bonosPorValidar = sessionBean.marketingUserFacade.getBonosPorEstadoYCasino("POR VALIDAR", sessionBean.getUsuario().getIdCasino());
        }
        bonosSeleccionados = new ArrayList<Bono>();
        casinos = sessionBean.adminFacade.findAllCasinos();
    }

    public List<Bono> getBonosPorValidar() {
        return bonosPorValidar;
    }

    public void setBonosPorValidar(List<Bono> bonosPorValidar) {
        this.bonosPorValidar = bonosPorValidar;
    }

    public List<Bono> getBonosSeleccionados() {
        return bonosSeleccionados;
    }

    public void setBonosSeleccionados(List<Bono> bonosSeleccionados) {
        this.bonosSeleccionados = bonosSeleccionados;
    }

    public Casino getCasinoById(Integer idCasino) {
        int casinoIndex = casinos.indexOf(new Casino(idCasino));
        if (casinoIndex != -1) {
            return casinos.get(casinoIndex);
        }
        return new Casino();
    }
    
    public void validar(){
        try {
            DateFormat df = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
            DateFormat df2 = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
            TimeZone timeZone = TimeZone.getTimeZone("GMT-5");
            df.setTimeZone(timeZone);
            Calendar nowDate = Calendar.getInstance();
            nowDate.setTime(df2.parse(df.format(nowDate.getTime())));
            for (Bono bono : bonosSeleccionados) {
                bono.setEstado("VALIDADO");
                bono.setFechaValidacion(nowDate.getTime());
                bono.setValidador(sessionBean.getUsuario());
            }
            sessionBean.marketingUserFacade.editarBonos(bonosSeleccionados);
            if (sessionBean.perfilViewMatch("verTodosClientes")) {
                bonosPorValidar = sessionBean.marketingUserFacade.getBonosPorEstado("POR VALIDAR");
            } else {
                bonosPorValidar = sessionBean.marketingUserFacade.getBonosPorEstadoYCasino("POR VALIDAR", sessionBean.getUsuario().getIdCasino());
            }
            bonosSeleccionados = new ArrayList<Bono>();
        } catch (ParseException ex) {
            Logger.getLogger(BonoValidarBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
