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
public class ControlsalidabonosHasLotesbonosPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "ControlSalidaBonos_id")
    private int controlSalidaBonosid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LotesBonos_id")
    private int lotesBonosid;

    public ControlsalidabonosHasLotesbonosPK() {
    }

    public ControlsalidabonosHasLotesbonosPK(int controlSalidaBonosid, int lotesBonosid) {
        this.controlSalidaBonosid = controlSalidaBonosid;
        this.lotesBonosid = lotesBonosid;
    }

    public int getControlSalidaBonosid() {
        return controlSalidaBonosid;
    }

    public void setControlSalidaBonosid(int controlSalidaBonosid) {
        this.controlSalidaBonosid = controlSalidaBonosid;
    }

    public int getLotesBonosid() {
        return lotesBonosid;
    }

    public void setLotesBonosid(int lotesBonosid) {
        this.lotesBonosid = lotesBonosid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) controlSalidaBonosid;
        hash += (int) lotesBonosid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ControlsalidabonosHasLotesbonosPK)) {
            return false;
        }
        ControlsalidabonosHasLotesbonosPK other = (ControlsalidabonosHasLotesbonosPK) object;
        if (this.controlSalidaBonosid != other.controlSalidaBonosid) {
            return false;
        }
        if (this.lotesBonosid != other.lotesBonosid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.ControlsalidabonosHasLotesbonosPK[ controlSalidaBonosid=" + controlSalidaBonosid + ", lotesBonosid=" + lotesBonosid + " ]";
    }
    
}
