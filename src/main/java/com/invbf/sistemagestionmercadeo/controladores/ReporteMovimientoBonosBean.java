/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Bono;
import java.io.IOException;
import java.util.Calendar;
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
        bonosAnalizar = sessionBean.managerUserFacade.getBonosPorFechas(monthago.getTime(), now.getTime());
    }

    public List<Bono> getBonosAnalizar() {
        return bonosAnalizar;
    }

    public void setBonosAnalizar(List<Bono> bonosAnalizar) {
        this.bonosAnalizar = bonosAnalizar;
    }
    
}
