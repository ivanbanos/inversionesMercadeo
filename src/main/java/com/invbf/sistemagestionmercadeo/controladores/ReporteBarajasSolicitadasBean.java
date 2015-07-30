/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.dto.BonosAprobadosCanjeados;
import com.invbf.sistemagestionmercadeo.dto.MesesCantFloat;
import com.invbf.sistemagestionmercadeo.dto.SolicitudesPorMes;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.util.CasinoBoolean;
import com.invbf.sistemagestionmercadeo.util.Mensajes;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
public class ReporteBarajasSolicitadasBean implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<Integer> anos;
    private Integer ano;
    private Integer mes;
    private Integer annodesde;
    private Integer mesdesde;
    private List<SolicitudesPorMes> solicitudes;
    private long max;

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

        anos = new ArrayList<Integer>();
        Calendar c = Calendar.getInstance();
        ano = c.get(Calendar.YEAR);
        mes = c.get(Calendar.MONTH);
        for (int i = 2015; i <= c.get(Calendar.YEAR); i++) {
            anos.add(i);
        }
        annodesde = ano;
        mesdesde = mes;
        buscarSolicitudes();
    }

    public void buscarBonosPorCasinosYFecha() {

        System.gc();
    }

    public void buscarSolicitudes() {
        if (annodesde > ano || ((annodesde == ano) && (mesdesde > mes))) {
            sessionBean.putMensaje(new Mensajes(Mensajes.ERROR, "Error en las fechas", "La fecha desde debe ser menor a la fecha hasta"));
        } else {
            solicitudes = sessionBean.barajasFacade.getSolicitudesSolicitudesMes(ano, mes, annodesde, mesdesde);
        }
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

    public List<SolicitudesPorMes> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<SolicitudesPorMes> solicitudes) {
        this.solicitudes = solicitudes;
    }

}
