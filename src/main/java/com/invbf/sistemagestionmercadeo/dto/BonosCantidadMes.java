/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author ivan
 */
public class BonosCantidadMes implements Serializable {

    private String casino;
    private String denominacion;
    private String tipo;
    private List<MesesCant> mesesCant;

    public BonosCantidadMes(String casino, String denominacion, String tipo) {
        this.casino = casino;
        this.denominacion = denominacion;
        this.tipo = tipo;
        this.mesesCant = new ArrayList<MesesCant>();
    }

    public BonosCantidadMes() {
    }

    public String getCasino() {
        return casino;
    }

    public void setCasino(String casino) {
        this.casino = casino;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void sumarCantidad(int mes, int anio) {
        mes++;
        if (!mesesCant.contains(new MesesCant(("" + mes)+"/" + anio,0,0))) {
            mesesCant.add(new MesesCant(("" + mes)+"/" + anio,0,0));
        }
        mesesCant.get(mesesCant.indexOf(new MesesCant(("" + mes)+"/" + anio,0,0))).sumar();
    }

    public List<MesesCant> getMesesCant() {
        return mesesCant;
    }

    public void setMesesCant(List<MesesCant> mesesCant) {
        this.mesesCant = mesesCant;
    }


    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.casino != null ? this.casino.hashCode() : 0);
        hash = 17 * hash + (this.denominacion != null ? this.denominacion.hashCode() : 0);
        hash = 17 * hash + (this.tipo != null ? this.tipo.hashCode() : 0);
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
        final BonosCantidadMes other = (BonosCantidadMes) obj;
        if ((this.casino == null) ? (other.casino != null) : !this.casino.equals(other.casino)) {
            return false;
        }
        if ((this.denominacion == null) ? (other.denominacion != null) : !this.denominacion.equals(other.denominacion)) {
            return false;
        }
        if ((this.tipo == null) ? (other.tipo != null) : !this.tipo.equals(other.tipo)) {
            return false;
        }
        return true;
    }

}
