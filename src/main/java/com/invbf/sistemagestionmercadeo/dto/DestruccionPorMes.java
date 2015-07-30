/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ivan
 */
public class DestruccionPorMes implements Serializable{
    private List<ActaDestruccionDTO> solicitudes;
    private String mesanio;
    private int mes;
    private int anio;
    
    public DestruccionPorMes() {
    }

    public DestruccionPorMes(int mes, int anio) {
        this.mesanio = getMes(mes)+"/"+anio;
        solicitudes = new ArrayList<ActaDestruccionDTO>();
        this.mes = mes;
        this.anio = anio;
    }
    
    private String getMes(int mes){
        switch(mes){
            case 0:
                return "Ene";
            case 1:
                return "Feb";
            case 2:
                return "Mar";
            case 3:
                return "Abr";
            case 4:
                return "May";
            case 5:
                return "Jun";
            case 6:
                return "Jul";
            case 7:
                return "Ago";
            case 8:
                return "Sep";
            case 9:
                return "Oct";
            case 10:
                return "Nov";
            case 11:
                return "Dic";
            default:
                return mes+"";
        }
    }

    public List<ActaDestruccionDTO> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<ActaDestruccionDTO> solicitudes) {
        this.solicitudes = solicitudes;
    }

    public String getMesanio() {
        return mesanio;
    }

    public void setMesanio(String mesanio) {
        this.mesanio = mesanio;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.mesanio != null ? this.mesanio.hashCode() : 0);
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
        final DestruccionPorMes other = (DestruccionPorMes) obj;
        if ((this.mesanio == null) ? (other.mesanio != null) : !this.mesanio.equals(other.mesanio)) {
            return false;
        }
        return true;
    }
    
}
