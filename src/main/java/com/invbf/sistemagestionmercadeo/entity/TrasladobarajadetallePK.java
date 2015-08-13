/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author ivan
 */
@Embeddable
public class TrasladobarajadetallePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "traslado")
    private int traslado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "baraja")
    private int baraja;

    public TrasladobarajadetallePK() {
    }

    public TrasladobarajadetallePK(int traslado, int baraja) {
        this.traslado = traslado;
        this.baraja = baraja;
    }

    public int getTraslado() {
        return traslado;
    }

    public void setTraslado(int traslado) {
        this.traslado = traslado;
    }

    public int getBaraja() {
        return baraja;
    }

    public void setBaraja(int baraja) {
        this.baraja = baraja;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) traslado;
        hash += (int) baraja;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrasladobarajadetallePK)) {
            return false;
        }
        TrasladobarajadetallePK other = (TrasladobarajadetallePK) object;
        if (this.traslado != other.traslado) {
            return false;
        }
        if (this.baraja != other.baraja) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.TrasladobarajadetallePK[ traslado=" + traslado + ", baraja=" + baraja + " ]";
    }
    
}
