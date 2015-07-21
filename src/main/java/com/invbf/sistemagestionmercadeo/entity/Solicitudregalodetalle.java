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
@Table(name = "solicitudregalodetalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solicitudregalodetalle.findAll", query = "SELECT s FROM Solicitudregalodetalle s"),
    @NamedQuery(name = "Solicitudregalodetalle.findBySolicitud", query = "SELECT s FROM Solicitudregalodetalle s WHERE s.solicitudregalodetallePK.solicitud = :solicitud"),
    @NamedQuery(name = "Solicitudregalodetalle.findByInventario", query = "SELECT s FROM Solicitudregalodetalle s WHERE s.solicitudregalodetallePK.inventario = :inventario"),
    @NamedQuery(name = "Solicitudregalodetalle.findByCantidad", query = "SELECT s FROM Solicitudregalodetalle s WHERE s.cantidad = :cantidad")})
public class Solicitudregalodetalle implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SolicitudregalodetallePK solicitudregalodetallePK;
    @Column(name = "cantidad")
    private Integer cantidad;
    @JoinColumn(name = "solicitud", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Solicitudregalos solicitudregalos;
    @JoinColumn(name = "inventario", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Regalosinventario regalosinventario;

    public Solicitudregalodetalle() {
    }

    public Solicitudregalodetalle(SolicitudregalodetallePK solicitudregalodetallePK) {
        this.solicitudregalodetallePK = solicitudregalodetallePK;
    }

    public Solicitudregalodetalle(int solicitud, int inventario) {
        this.solicitudregalodetallePK = new SolicitudregalodetallePK(solicitud, inventario);
    }

    public SolicitudregalodetallePK getSolicitudregalodetallePK() {
        return solicitudregalodetallePK;
    }

    public void setSolicitudregalodetallePK(SolicitudregalodetallePK solicitudregalodetallePK) {
        this.solicitudregalodetallePK = solicitudregalodetallePK;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Solicitudregalos getSolicitudregalos() {
        return solicitudregalos;
    }

    public void setSolicitudregalos(Solicitudregalos solicitudregalos) {
        this.solicitudregalos = solicitudregalos;
    }

    public Regalosinventario getRegalosinventario() {
        return regalosinventario;
    }

    public void setRegalosinventario(Regalosinventario regalosinventario) {
        this.regalosinventario = regalosinventario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (solicitudregalodetallePK != null ? solicitudregalodetallePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Solicitudregalodetalle)) {
            return false;
        }
        Solicitudregalodetalle other = (Solicitudregalodetalle) object;
        if ((this.solicitudregalodetallePK == null && other.solicitudregalodetallePK != null) || (this.solicitudregalodetallePK != null && !this.solicitudregalodetallePK.equals(other.solicitudregalodetallePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Solicitudregalodetalle[ solicitudregalodetallePK=" + solicitudregalodetallePK + " ]";
    }
    
}
