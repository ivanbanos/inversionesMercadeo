/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "solicitudbarajadetalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solicitudbarajadetalle.findAll", query = "SELECT s FROM Solicitudbarajadetalle s"),
    @NamedQuery(name = "Solicitudbarajadetalle.findBySolicitud", query = "SELECT s FROM Solicitudbarajadetalle s WHERE s.solicitudbarajadetallePK.solicitud = :solicitud"),
    @NamedQuery(name = "Solicitudbarajadetalle.findByInventario", query = "SELECT s FROM Solicitudbarajadetalle s WHERE s.solicitudbarajadetallePK.inventario = :inventario"),
    @NamedQuery(name = "Solicitudbarajadetalle.findByCantidad", query = "SELECT s FROM Solicitudbarajadetalle s WHERE s.cantidad = :cantidad")})
public class Solicitudbarajadetalle implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SolicitudbarajadetallePK solicitudbarajadetallePK;
    @Column(name = "cantidad")
    private Integer cantidad;
    @JoinColumn(name = "inventario", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Inventarobarajas inventarobarajas;
    @JoinColumn(name = "solicitud", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Solicitudbarajas solicitudbarajas;

    public Solicitudbarajadetalle() {
    }

    public Solicitudbarajadetalle(SolicitudbarajadetallePK solicitudbarajadetallePK) {
        this.solicitudbarajadetallePK = solicitudbarajadetallePK;
    }

    public Solicitudbarajadetalle(int solicitud, int inventario) {
        this.solicitudbarajadetallePK = new SolicitudbarajadetallePK(solicitud, inventario);
    }

    public SolicitudbarajadetallePK getSolicitudbarajadetallePK() {
        return solicitudbarajadetallePK;
    }

    public void setSolicitudbarajadetallePK(SolicitudbarajadetallePK solicitudbarajadetallePK) {
        this.solicitudbarajadetallePK = solicitudbarajadetallePK;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Inventarobarajas getInventarobarajas() {
        return inventarobarajas;
    }

    public void setInventarobarajas(Inventarobarajas inventarobarajas) {
        this.inventarobarajas = inventarobarajas;
    }

    public Solicitudbarajas getSolicitudbarajas() {
        return solicitudbarajas;
    }

    public void setSolicitudbarajas(Solicitudbarajas solicitudbarajas) {
        this.solicitudbarajas = solicitudbarajas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (solicitudbarajadetallePK != null ? solicitudbarajadetallePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Solicitudbarajadetalle)) {
            return false;
        }
        Solicitudbarajadetalle other = (Solicitudbarajadetalle) object;
        if ((this.solicitudbarajadetallePK == null && other.solicitudbarajadetallePK != null) || (this.solicitudbarajadetallePK != null && !this.solicitudbarajadetallePK.equals(other.solicitudbarajadetallePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.controladores.Solicitudbarajadetalle[ solicitudbarajadetallePK=" + solicitudbarajadetallePK + " ]";
    }
    
}
