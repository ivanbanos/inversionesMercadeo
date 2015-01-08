/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

/**
 *
 * @author ivan
 */
public class AnalisisBono {
    private String tipoBono;
    private long aprobados;
    private long entregados;
    private long canjeados;

    public AnalisisBono() {
    }

    public AnalisisBono(String tipoBono) {
        this.tipoBono = tipoBono;
        aprobados = 0;
        entregados = 0;
        canjeados = 0;
    }

    public AnalisisBono(String tipoBono, long aprobados, long entregados, long canjeados) {
        this.tipoBono = tipoBono;
        this.aprobados = aprobados;
        this.entregados = entregados;
        this.canjeados = canjeados;
    }

    public String getTipoBono() {
        return tipoBono;
    }

    public void setTipoBono(String tipoBono) {
        this.tipoBono = tipoBono;
    }

    public long getAprobados() {
        return aprobados;
    }

    public void setAprobados(long aprobados) {
        this.aprobados = aprobados;
    }

    public long getEntregados() {
        return entregados;
    }

    public void setEntregados(long entregados) {
        this.entregados = entregados;
    }

    public long getCanjeados() {
        return canjeados;
    }

    public void setCanjeados(long canjeados) {
        this.canjeados = canjeados;
    }
    
    public float getEfectividad(){
        return (((float)canjeados)/aprobados)*100f;
    }
}
