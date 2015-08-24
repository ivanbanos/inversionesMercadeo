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
public class ConsumoBarajas implements Serializable {

    private BarajasDTO baraja;
    private List<MesesCant> mesesCant;

    public ConsumoBarajas(BarajasDTO baraja) {
        this.baraja = baraja;
        this.mesesCant = new ArrayList<MesesCant>(0);
    }

    public ConsumoBarajas() {
    }

    public void sumarCantidad(int mes, int anio, Integer cantidad) {
        mes++;
        if (!mesesCant.contains(new MesesCant(mes, anio, 0, 0))) {
            mesesCant.add(new MesesCant(mes, anio, 0, 0));
        }
        mesesCant.get(mesesCant.indexOf(new MesesCant(mes, anio, 0, 0))).setCantidad(mesesCant.get(mesesCant.indexOf(new MesesCant(mes, anio, 0, 0))).getCantidad()+cantidad);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.baraja != null ? this.baraja.hashCode() : 0);
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
        final ConsumoBarajas other = (ConsumoBarajas) obj;
        if (this.baraja != other.baraja && (this.baraja == null || !this.baraja.equals(other.baraja))) {
            return false;
        }
        return true;
    }

    public BarajasDTO getBaraja() {
        return baraja;
    }

    public void setBaraja(BarajasDTO baraja) {
        this.baraja = baraja;
    }

    public List<MesesCant> getMesesCant() {
        return mesesCant;
    }

    public void setMesesCant(List<MesesCant> mesesCant) {
        this.mesesCant = mesesCant;
    }

}
