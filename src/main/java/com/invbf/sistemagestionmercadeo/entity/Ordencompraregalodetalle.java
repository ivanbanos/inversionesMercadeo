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
@Table(name = "ordencompraregalodetalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ordencompraregalodetalle.findAll", query = "SELECT o FROM Ordencompraregalodetalle o"),
    @NamedQuery(name = "Ordencompraregalodetalle.findByOrden", query = "SELECT o FROM Ordencompraregalodetalle o WHERE o.ordencompraregalodetallePK.orden = :orden"),
    @NamedQuery(name = "Ordencompraregalodetalle.findByInventario", query = "SELECT o FROM Ordencompraregalodetalle o WHERE o.ordencompraregalodetallePK.inventario = :inventario"),
    @NamedQuery(name = "Ordencompraregalodetalle.findByCantidad", query = "SELECT o FROM Ordencompraregalodetalle o WHERE o.cantidad = :cantidad"),
    @NamedQuery(name = "Ordencompraregalodetalle.findByCantidadRecibida", query = "SELECT o FROM Ordencompraregalodetalle o WHERE o.cantidadRecibida = :cantidadRecibida")})
public class Ordencompraregalodetalle implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OrdencompraregalodetallePK ordencompraregalodetallePK;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "cantidadRecibida")
    private Integer cantidadRecibida;
    @JoinColumn(name = "orden", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Ordencompraregalos ordencompraregalos;
    @JoinColumn(name = "inventario", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Regalosinventario regalosinventario;

    public Ordencompraregalodetalle() {
    }

    public Ordencompraregalodetalle(OrdencompraregalodetallePK ordencompraregalodetallePK) {
        this.ordencompraregalodetallePK = ordencompraregalodetallePK;
    }

    public Ordencompraregalodetalle(int orden, int inventario) {
        this.ordencompraregalodetallePK = new OrdencompraregalodetallePK(orden, inventario);
    }

    public OrdencompraregalodetallePK getOrdencompraregalodetallePK() {
        return ordencompraregalodetallePK;
    }

    public void setOrdencompraregalodetallePK(OrdencompraregalodetallePK ordencompraregalodetallePK) {
        this.ordencompraregalodetallePK = ordencompraregalodetallePK;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCantidadRecibida() {
        return cantidadRecibida;
    }

    public void setCantidadRecibida(Integer cantidadRecibida) {
        this.cantidadRecibida = cantidadRecibida;
    }

    public Ordencompraregalos getOrdencompraregalos() {
        return ordencompraregalos;
    }

    public void setOrdencompraregalos(Ordencompraregalos ordencompraregalos) {
        this.ordencompraregalos = ordencompraregalos;
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
        hash += (ordencompraregalodetallePK != null ? ordencompraregalodetallePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ordencompraregalodetalle)) {
            return false;
        }
        Ordencompraregalodetalle other = (Ordencompraregalodetalle) object;
        if ((this.ordencompraregalodetallePK == null && other.ordencompraregalodetallePK != null) || (this.ordencompraregalodetallePK != null && !this.ordencompraregalodetallePK.equals(other.ordencompraregalodetallePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Ordencompraregalodetalle[ ordencompraregalodetallePK=" + ordencompraregalodetallePK + " ]";
    }
    
}
