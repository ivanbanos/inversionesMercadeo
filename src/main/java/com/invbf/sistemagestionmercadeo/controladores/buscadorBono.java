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
import com.invbf.sistemagestionmercadeo.entity.Solicitudentrega;
import com.invbf.sistemagestionmercadeo.entity.Tipobono;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import com.invbf.sistemagestionmercadeo.util.MesAnno;
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
import javax.rmi.CORBA.Util;

/**
 *
 * @author ivan
 */
@ManagedBean
@ViewScoped
public class buscadorBono implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<Casino> casinos;
    private List<Denominacion> denominaciones;
    private Casino casinoSelected;
    private Denominacion denoinacionSelected;
    private Bono bono;
    private String consecutivo;
    private Integer numeroActa;
    private Solicitudentrega solicitudentrega;

    private List<MesAnno> meses;
    private int mes;

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
        Calendar c = Calendar.getInstance();
        mes = c.get(Calendar.MONTH);
        int anio = c.get(Calendar.YEAR);
        meses = new ArrayList<MesAnno>();
        switch (mes) {
            case 0:
                meses.add(new MesAnno(0, "Enero " + anio));
                meses.add(new MesAnno(1, "Febrero " + anio));
                break;
            case 1:
                meses.add(new MesAnno(1, "Febrero " + anio));
                meses.add(new MesAnno(2, "Marzo " + anio));
                break;
            case 2:
                meses.add(new MesAnno(2, "Marzo " + anio));
                meses.add(new MesAnno(3, "Abril " + anio));
                break;
            case 3:
                meses.add(new MesAnno(3, "Abril " + anio));
                meses.add(new MesAnno(4, "Mayo " + anio));
                break;
            case 4:
                meses.add(new MesAnno(4, "Mayo " + anio));
                meses.add(new MesAnno(5, "Junio " + anio));
                break;
            case 5:
                meses.add(new MesAnno(5, "Junio " + anio));
                meses.add(new MesAnno(6, "Julio " + anio));
                break;
            case 6:
                meses.add(new MesAnno(6, "Julio " + anio));
                meses.add(new MesAnno(7, "Agosto " + anio));
                break;
            case 7:
                meses.add(new MesAnno(7, "Agosto " + anio));
                meses.add(new MesAnno(8, "Septiembre " + anio));
                break;
            case 8:
                meses.add(new MesAnno(8, "Septiembre " + anio));
                meses.add(new MesAnno(9, "Octubre " + anio));
                break;
            case 9:
                meses.add(new MesAnno(9, "Octubre " + anio));
                meses.add(new MesAnno(10, "Noviembre " + anio));
                break;
            case 10:
                meses.add(new MesAnno(10, "Noviembre " + anio));
                meses.add(new MesAnno(11, "Diciembre " + anio));
                break;
            case 11:
                meses.add(new MesAnno(11, "Diciembre " + anio));
                meses.add(new MesAnno(0, "Enero " + (anio + 1)));
                break;

        }
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

    public void buscarSolicitud() {
        solicitudentrega = sessionBean.marketingUserFacade.getSolicitudbono(numeroActa);
        if (solicitudentrega == null) {
            FacesUtil.addErrorMessage("No se encontró solicitud con número de acta " + numeroActa, "");
        } else if (solicitudentrega.getEstado().equals("BONOS RECIBIDOS EN SALA")
                || solicitudentrega.getEstado().equals("BONOS VENCIDOS. PENDIENTE POR GENERAR REPORTE")
                || solicitudentrega.getEstado().equals("REPORTE DE GESTIÓN DISPONIBLE")) {

            FacesUtil.addErrorMessage("Solicitud encontrada, pero no se puede realizar cambio.", "");
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

    public Integer getNumeroActa() {
        return numeroActa;
    }

    public void setNumeroActa(Integer numeroActa) {
        this.numeroActa = numeroActa;
    }

    public Solicitudentrega getSolicitudentrega() {
        return solicitudentrega;
    }

    public void setSolicitudentrega(Solicitudentrega solicitudentrega) {
        this.solicitudentrega = solicitudentrega;
    }

    public List<MesAnno> getMeses() {
        return meses;
    }

    public void setMeses(List<MesAnno> meses) {
        this.meses = meses;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public void cambiarSol() {
        System.out.println(mes);
        Calendar c = Calendar.getInstance();
        int act = c.get(Calendar.MONTH);
        if (act > mes) {
            c.set(Calendar.YEAR, c.get(Calendar.YEAR) + 1);
        }
        c.set(Calendar.MONTH, mes);
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.DATE, -1);
        solicitudentrega.setFechavencimientobonos(c.getTime());
        sessionBean.marketingUserFacade.cambiarFechaSolicitud(solicitudentrega);
        FacesUtil.addInfoMessage("Fecha de  solicitud de bonos cambiada", "");
    }
}
