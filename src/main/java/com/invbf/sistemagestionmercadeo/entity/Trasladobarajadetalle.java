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
@Table(name = "trasladobarajadetalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trasladobarajadetalle.findAll", query = "SELECT t FROM Trasladobarajadetalle t"),
    @NamedQuery(name = "Trasladobarajadetalle.findByTraslado", query = "SELECT t FROM Trasladobarajadetalle t WHERE t.trasladobarajadetallePK.traslado = :traslado"),
    @NamedQuery(name = "Trasladobarajadetalle.findByBaraja", query = "SELECT t FROM Trasladobarajadetalle t WHERE t.trasladobarajadetallePK.baraja = :baraja"),
    @NamedQuery(name = "Trasladobarajadetalle.findByCantidad", query = "SELECT t FROM Trasladobarajadetalle t WHERE t.cantidad = :cantidad")})
public class Trasladobarajadetalle implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TrasladobarajadetallePK trasladobarajadetallePK;
    @Column(name = "cantidad")
    private Integer cantidad;
    @JoinColumn(name = "traslado", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Trasladobarajas trasladobarajas;
    @JoinColumn(name = "baraja", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Barajas barajas;

    public Trasladobarajadetalle() {
    }

    public Trasladobarajadetalle(TrasladobarajadetallePK trasladobarajadetallePK) {
        this.trasladobarajadetallePK = trasladobarajadetallePK;
    }

    public Trasladobarajadetalle(int traslado, int baraja) {
        this.trasladobarajadetallePK = new TrasladobarajadetallePK(traslado, baraja);
    }

    public TrasladobarajadetallePK getTrasladobarajadetallePK() {
        return trasladobarajadetallePK;
    }

    public void setTrasladobarajadetallePK(TrasladobarajadetallePK trasladobarajadetallePK) {
        this.trasladobarajadetallePK = trasladobarajadetallePK;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Trasladobarajas getTrasladobarajas() {
        return trasladobarajas;
    }

    public void setTrasladobarajas(Trasladobarajas trasladobarajas) {
        this.trasladobarajas = trasladobarajas;
    }

    public Barajas getBarajas() {
        return barajas;
    }

    public void setBarajas(Barajas barajas) {
        this.barajas = barajas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trasladobarajadetallePK != null ? trasladobarajadetallePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trasladobarajadetalle)) {
            return false;
        }
        Trasladobarajadetalle other = (Trasladobarajadetalle) object;
        if ((this.trasladobarajadetallePK == null && other.trasladobarajadetallePK != null) || (this.trasladobarajadetallePK != null && !this.trasladobarajadetallePK.equals(other.trasladobarajadetallePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Trasladobarajadetalle[ trasladobarajadetallePK=" + trasladobarajadetallePK + " ]";
    }
    
}
