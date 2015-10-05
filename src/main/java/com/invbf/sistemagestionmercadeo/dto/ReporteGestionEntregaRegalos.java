/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dto;

import java.io.Serializable;

/**
 *
 * @author ivan
 */
public class ReporteGestionEntregaRegalos implements Serializable {

    private int obsequiosRecibidos;
    private int pendientes;
    private int totalEntregar;
    private int totalEntregados;

    public ReporteGestionEntregaRegalos() {
        obsequiosRecibidos = 0;
        pendientes = 0;
        totalEntregar = 0;
        totalEntregados = 0;
    }

    public int getObsequiosRecibidos() {
        return obsequiosRecibidos;
    }

    public void setObsequiosRecibidos(int obsequiosRecibidos) {
        this.obsequiosRecibidos = obsequiosRecibidos;
    }

    public int getPendientes() {
        return pendientes;
    }

    public void setPendientes(int pendientes) {
        this.pendientes = pendientes;
    }

    public int getTotalEntregar() {
        return totalEntregar;
    }

    public void setTotalEntregar(int totalEntregar) {
        this.totalEntregar = totalEntregar;
    }

    public int getTotalEntregados() {
        return totalEntregados;
    }

    public void setTotalEntregados(int totalEntregados) {
        this.totalEntregados = totalEntregados;
    }

    public void sumarObsequiosRecibidos() {
        obsequiosRecibidos++;
        totalEntregar++;
    }

    public void sumarEntregados() {
        totalEntregados++;
    }
    
    public void sumarPendientes() {
        pendientes++;
        totalEntregar++;
    }
}
