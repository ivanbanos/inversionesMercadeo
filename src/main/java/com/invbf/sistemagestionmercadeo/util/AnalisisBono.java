/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Bono;

/**
 *
 * @author ivan
 */
public class AnalisisBono {
    private String tipoBono;
    private long aprobados;
    private long entregados;
    private long canjeados;
    private long totales;
    private long vencidos;

    public AnalisisBono() {
    }

    public AnalisisBono(String tipoBono) {
        this.tipoBono = tipoBono;
        aprobados = 0;
        entregados = 0;
        canjeados = 0;
        totales = 0;
        vencidos = 0;
    }

    public AnalisisBono(String tipoBono, long aprobados, long entregados, long canjeados, long totales, long vencidos) {
        this.tipoBono = tipoBono;
        this.aprobados = aprobados;
        this.entregados = entregados;
        this.canjeados = canjeados;
        this.totales = totales;
        this.vencidos = vencidos;
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

    public long getTotales() {
        return totales;
    }

    public void setTotales(long totales) {
        this.totales = totales;
    }

    public long getVencidos() {
        return vencidos;
    }

    public void setVencidos(long vencidos) {
        this.vencidos = vencidos;
    }

    public void addBono(Bono bono) {
        totales +=1;
        if(bono.getEstado().equals("EN SALA")||bono.getEstado().equals("POR VALIDAR")||bono.getEstado().equals("VALIDADO")||bono.getEstado().equals("ENTREGADO")){
            aprobados++;
        }
        if(bono.getEstado().equals("ENTREGADO CLIENTE")){
            aprobados++;
            entregados++;
        }
        if(bono.getEstado().equals("CANJEADO")){
            aprobados++;
            entregados++;
            canjeados++;
        }
        if(bono.getEstado().equals("VENCIDO")){
            vencidos++;
        }
    }
}
