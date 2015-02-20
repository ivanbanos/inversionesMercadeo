/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Bono;
import java.io.Serializable;

/**
 *
 * @author ivan
 */
public class AnalisisBono  implements Serializable{

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

    public float getEfectividad() {
        return (((float) entregados) / aprobados) * 100f;
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
        totales += 1;
        if (bono.getEstado().equals("EN SALA") || bono.getEstado().equals("POR VERIFICAR") || bono.getEstado().equals("VERIFICADO") || bono.getEstado().equals("ENTREGADO")) {
            aprobados++;
        }
        if (bono.getEstado().equals("ENTREGADO CLIENTE") || bono.getEstado().equals("VALIDADO") || bono.getEstado().equals("AUTORIZADO")) {
            aprobados++;
            entregados++;
        }
        if (bono.getEstado().equals("CANJEADO")) {
            aprobados++;
            entregados++;
            canjeados++;
        }
        if (bono.getEstado().equals("VENCIDO")) {
            vencidos++;
        }
    }

    public float getEfectividadCAnjeados() {
        if (aprobados != 0) {
            return (((float) canjeados) / aprobados) * 100f;
        } else {
            return 0;
        }
    }

    public long getNocanjeados() {
        return entregados - canjeados;
    }

    public float getEfectividadNoCAnjeados() {
        if (entregados != 0) {
            return ((entregados - canjeados) * 100f) / entregados;
        } else {
            return 0;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.tipoBono != null ? this.tipoBono.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AnalisisBono other = (AnalisisBono) obj;
        if ((this.tipoBono == null) ? (other.tipoBono != null) : !this.tipoBono.equals(other.tipoBono)) {
            return false;
        }
        return true;
    }

}
