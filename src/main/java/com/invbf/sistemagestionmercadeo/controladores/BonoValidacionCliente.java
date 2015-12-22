/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Bono;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author ivan
 */
@ManagedBean
@ViewScoped
public class BonoValidacionCliente implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<Casino> casinos;
    private Casino casinoSelected;
    private List<Bono> bonosSinCliente;
    private String justificacion;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public BonoValidacionCliente() {

    }

    @PostConstruct
    public void init() {
        try {
            sessionBean.checkUsuarioConectado();
            sessionBean.setActive("requisiciones");
            if (!sessionBean.perfilViewMatch("validarBonosCliente")) {
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
                } catch (IOException ex) {
                }
            }
            sessionBean.revisarEstadoBonos();
            casinos = sessionBean.getUsuario().getCasinoList();
            bonosSinCliente = new ArrayList<Bono>();
            casinoSelected = new Casino();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void buscarBonosValidadosPorCasino() {
        System.out.println("Buscar bonos");
        System.out.println("casino " + casinoSelected.getIdCasino());

        casinoSelected = casinos.get(casinos.indexOf(new Casino(casinoSelected.getIdCasino())));
        bonosSinCliente = sessionBean.marketingUserFacade.getBonosSinCliente(casinoSelected);
        if (bonosSinCliente.isEmpty()) {
            FacesUtil.addErrorMessage("No se encontró ningun bono", "");
        } else {
        }
        System.out.println(bonosSinCliente.size());
    }

    public void justificarBonos() {
        boolean justificar = false;
        boolean existeJus = justificacion != null && !justificacion.equals("");
        for (Bono b : bonosSinCliente) {
            if (b.getNombreCliente() != null && !b.getNombreCliente().equals("")) {
                justificar = true;
            }
        }
        if (justificar && existeJus) {
            sessionBean.marketingUserFacade.justificarBonos(bonosSinCliente, justificacion);
            bonosSinCliente = new ArrayList<Bono>();
            casinoSelected = new Casino();
            justificacion = "";
            FacesUtil.addInfoMessage("Bonos justificados con exito!", "");
        } else {
            FacesUtil.addErrorMessage("Debe colocar una justificación", "No se ha justificado");
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

    public List<Bono> getBonosSinCliente() {
        return bonosSinCliente;
    }

    public void setBonosSinCliente(List<Bono> bonosSinCliente) {
        this.bonosSinCliente = bonosSinCliente;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public String getAsString(Object o) {
        String numberseparated = "";
        String iPartS = "";
        boolean tieneelsimbolo = false;
        if (o instanceof Double) {
            double number = (Double) o;
            Long iPart = (long) number;
            iPartS = iPart.toString();
        } else if (o instanceof Float) {
            float number = (Float) o;
            Long iPart = (long) number;
            iPartS = iPart.toString();
        } else {
            String number;
            if (o instanceof Integer) {
                number = ((Integer) o).toString();
            } else {
                number = (String) o;
            }
            System.out.println("numero " + number);
            if (number.lastIndexOf(".") != -1) {
                iPartS = number.substring(0, number.lastIndexOf("."));
            } else {
                iPartS = number;
            }
        }
        boolean milesima = true;

        while (true) {
            System.out.println("asi va el numero " + numberseparated);
            System.out.println("y queda esto " + iPartS);
            if (iPartS.length() == 0) {
                break;
            } else if (iPartS.length() <= 3) {
                numberseparated = iPartS + numberseparated;
                iPartS = "";
            } else {
                if (milesima) {
                    numberseparated = "." + iPartS.substring(iPartS.length() - 3) + numberseparated;
                    iPartS = iPartS.substring(0, iPartS.length() - 3);
                    milesima = false;
                } else {
                    numberseparated = "'" + iPartS.substring(iPartS.length() - 3) + numberseparated;
                    iPartS = iPartS.substring(0, iPartS.length() - 3);
                    milesima = true;
                }
            }
        }
        System.out.println("asi quedo sin parte real " + numberseparated);
        System.out.println("asi quedo con parte real " + numberseparated);
        System.out.println();
        System.out.println();
        return numberseparated;
    }
}
