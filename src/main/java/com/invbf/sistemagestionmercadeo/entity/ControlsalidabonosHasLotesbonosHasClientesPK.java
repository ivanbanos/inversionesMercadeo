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
public class ControlsalidabonosHasLotesbonosHasClientesPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "ControlSalidaBonos_has_LotesBonos_ControlSalidaBonos_id")
    private int controlSalidaBonoshasLotesBonosControlSalidaBonosid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ControlSalidaBonos_has_LotesBonos_LotesBonos_id")
    private int controlSalidaBonoshasLotesBonosLotesBonosid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idCliente")
    private int idCliente;

    public ControlsalidabonosHasLotesbonosHasClientesPK() {
    }

    public ControlsalidabonosHasLotesbonosHasClientesPK(int controlSalidaBonoshasLotesBonosControlSalidaBonosid, int controlSalidaBonoshasLotesBonosLotesBonosid, int idCliente) {
        this.controlSalidaBonoshasLotesBonosControlSalidaBonosid = controlSalidaBonoshasLotesBonosControlSalidaBonosid;
        this.controlSalidaBonoshasLotesBonosLotesBonosid = controlSalidaBonoshasLotesBonosLotesBonosid;
        this.idCliente = idCliente;
    }

    public int getControlSalidaBonoshasLotesBonosControlSalidaBonosid() {
        return controlSalidaBonoshasLotesBonosControlSalidaBonosid;
    }

    public void setControlSalidaBonoshasLotesBonosControlSalidaBonosid(int controlSalidaBonoshasLotesBonosControlSalidaBonosid) {
        this.controlSalidaBonoshasLotesBonosControlSalidaBonosid = controlSalidaBonoshasLotesBonosControlSalidaBonosid;
    }

    public int getControlSalidaBonoshasLotesBonosLotesBonosid() {
        return controlSalidaBonoshasLotesBonosLotesBonosid;
    }

    public void setControlSalidaBonoshasLotesBonosLotesBonosid(int controlSalidaBonoshasLotesBonosLotesBonosid) {
        this.controlSalidaBonoshasLotesBonosLotesBonosid = controlSalidaBonoshasLotesBonosLotesBonosid;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) controlSalidaBonoshasLotesBonosControlSalidaBonosid;
        hash += (int) controlSalidaBonoshasLotesBonosLotesBonosid;
        hash += (int) idCliente;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ControlsalidabonosHasLotesbonosHasClientesPK)) {
            return false;
        }
        ControlsalidabonosHasLotesbonosHasClientesPK other = (ControlsalidabonosHasLotesbonosHasClientesPK) object;
        if (this.controlSalidaBonoshasLotesBonosControlSalidaBonosid != other.controlSalidaBonoshasLotesBonosControlSalidaBonosid) {
            return false;
        }
        if (this.controlSalidaBonoshasLotesBonosLotesBonosid != other.controlSalidaBonoshasLotesBonosLotesBonosid) {
            return false;
        }
        if (this.idCliente != other.idCliente) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.ControlsalidabonosHasLotesbonosHasClientesPK[ controlSalidaBonoshasLotesBonosControlSalidaBonosid=" + controlSalidaBonoshasLotesBonosControlSalidaBonosid + ", controlSalidaBonoshasLotesBonosLotesBonosid=" + controlSalidaBonoshasLotesBonosLotesBonosid + ", idCliente=" + idCliente + " ]";
    }
    
}
