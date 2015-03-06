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
public class MesAnno implements Serializable {
    
    private int mes;
    private String mesaano;

    public MesAnno() {
    }

    public MesAnno(int mes, String mesaano) {
        this.mes = mes;
        this.mesaano = mesaano;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public String getMesaano() {
        return mesaano;
    }

    public void setMesaano(String mesaano) {
        this.mesaano = mesaano;
    }
    
    
}
