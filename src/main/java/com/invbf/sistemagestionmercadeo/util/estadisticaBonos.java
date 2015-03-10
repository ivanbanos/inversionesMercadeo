/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import java.io.Serializable;

/**
 *
 * @author ivan
 */
public class estadisticaBonos implements Serializable{
    private float montoSolicitado;
    private int bonosSolocitados;
    private float montoAprobado;
    private int bonosAprobados;
    private float montoCanjeado;
    private int bonosCanjeados;
    private float efectividad;

    public estadisticaBonos() {
    }

    public float getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(float montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
    }

    public int getBonosSolocitados() {
        return bonosSolocitados;
    }

    public void setBonosSolocitados(int bonosSolocitados) {
        this.bonosSolocitados = bonosSolocitados;
    }

    public float getMontoAprobado() {
        return montoAprobado;
    }

    public void setMontoAprobado(float montoAprobado) {
        this.montoAprobado = montoAprobado;
    }

    public int getBonosAprobados() {
        return bonosAprobados;
    }

    public void setBonosAprobados(int bonosAprobados) {
        this.bonosAprobados = bonosAprobados;
    }

    public float getMontoCanjeado() {
        return montoCanjeado;
    }

    public void setMontoCanjeado(float montoCanjeado) {
        this.montoCanjeado = montoCanjeado;
    }

    public int getBonosCanjeados() {
        return bonosCanjeados;
    }

    public void setBonosCanjeados(int bonosCanjeados) {
        this.bonosCanjeados = bonosCanjeados;
    }

    public float getEfectividad() {
        return efectividad;
    }

    public void setEfectividad() {
        this.efectividad = (float) (bonosAprobados==0?0:(((bonosCanjeados*1.0)/bonosAprobados)*100));
    }
    
    
}
