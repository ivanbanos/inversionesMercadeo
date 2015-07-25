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
public class BonosAprobadosCanjeados implements Serializable {

    private String casino;
    private List<MesesCantFloat> mesesCant;

    public BonosAprobadosCanjeados(String casino) {
        this.casino = casino;
        this.mesesCant = new ArrayList<MesesCantFloat>(0);
    }

    public BonosAprobadosCanjeados() {
    }

    public String getCasino() {
        return casino;
    }

    public void setCasino(String casino) {
        this.casino = casino;
    }

    public void sumarCantidad(int mes, int anio, float cant) {
        mes++;
        if (!mesesCant.contains(new MesesCantFloat(("" + mes)+"/" + anio,0,0))) {
            mesesCant.add(new MesesCantFloat(("" + mes)+"/" + anio,0,0));
        }
        mesesCant.get(mesesCant.indexOf(new MesesCantFloat(("" + mes)+"/" + anio,0,0))).sumar(cant);
    }
    
    public void sumarCantidad2(int mes, int anio, float cant) {
        mes++;
        if (!mesesCant.contains(new MesesCantFloat(("" + mes)+"/" + anio,0,0))) {
            mesesCant.add(new MesesCantFloat(("" + mes)+"/" + anio,0,0));
        }
        mesesCant.get(mesesCant.indexOf(new MesesCantFloat(("" + mes)+"/" + anio,0,0))).sumar(cant);
        mesesCant.get(mesesCant.indexOf(new MesesCantFloat(("" + mes)+"/" + anio,0,0))).sumar2(cant);
    }

    public List<MesesCantFloat> getMesesCant() {
        return mesesCant;
    }

    public void setMesesCant(List<MesesCantFloat> mesesCant) {
        this.mesesCant = mesesCant;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.casino != null ? this.casino.hashCode() : 0);
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
        final BonosAprobadosCanjeados other = (BonosAprobadosCanjeados) obj;
        if ((this.casino == null) ? (other.casino != null) : !this.casino.equals(other.casino)) {
            return false;
        }
        return true;
    }

}
