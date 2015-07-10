/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "inventarobarajas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inventarobarajas.findAll", query = "SELECT i FROM Inventarobarajas i"),
    @NamedQuery(name = "Inventarobarajas.findById", query = "SELECT i FROM Inventarobarajas i WHERE i.id = :id"),
    @NamedQuery(name = "Inventarobarajas.findByCantidadbarajas", query = "SELECT i FROM Inventarobarajas i WHERE i.cantidadbarajas = :cantidadbarajas")})
public class Inventarobarajas implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventarobarajas")
    private List<Solicitudbarajadetalle> solicitudbarajadetalleList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventarobarajas")
    private List<Ordencomprabarajadetalle> ordencomprabarajadetalleList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "cantidadbarajas")
    private Integer cantidadbarajas;
    @JoinColumn(name = "baraja", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Barajas baraja;

    public Inventarobarajas() {
    }

    public Inventarobarajas(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidadbarajas() {
        return cantidadbarajas;
    }

    public void setCantidadbarajas(Integer cantidadbarajas) {
        this.cantidadbarajas = cantidadbarajas;
    }

    public Barajas getBaraja() {
        return baraja;
    }

    public void setBaraja(Barajas baraja) {
        this.baraja = baraja;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inventarobarajas)) {
            return false;
        }
        Inventarobarajas other = (Inventarobarajas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Inventarobarajas[ id=" + id + " ]";
    }


    @XmlTransient
    public List<Solicitudbarajadetalle> getSolicitudbarajadetalleList() {
        return solicitudbarajadetalleList;
    }

    public void setSolicitudbarajadetalleList(List<Solicitudbarajadetalle> solicitudbarajadetalleList) {
        this.solicitudbarajadetalleList = solicitudbarajadetalleList;
    }

    @XmlTransient
    public List<Ordencomprabarajadetalle> getOrdencomprabarajadetalleList() {
        return ordencomprabarajadetalleList;
    }

    public void setOrdencomprabarajadetalleList(List<Ordencomprabarajadetalle> ordencomprabarajadetalleList) {
        this.ordencomprabarajadetalleList = ordencomprabarajadetalleList;
    }
    
}
