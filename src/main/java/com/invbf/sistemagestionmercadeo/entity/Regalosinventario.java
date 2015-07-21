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
@Table(name = "regalosinventario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Regalosinventario.findAll", query = "SELECT r FROM Regalosinventario r"),
    @NamedQuery(name = "Regalosinventario.findById", query = "SELECT r FROM Regalosinventario r WHERE r.id = :id"),
    @NamedQuery(name = "Regalosinventario.findByCantidad", query = "SELECT r FROM Regalosinventario r WHERE r.cantidad = :cantidad")})
public class Regalosinventario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "cantidad")
    private Integer cantidad;
    @JoinColumn(name = "regalo", referencedColumnName = "id")
    @ManyToOne
    private Regalos regalo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "regalosinventario")
    private List<Ordencompraregalodetalle> ordencompraregalodetalleList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "regalosinventario")
    private List<Solicitudregalodetalle> solicitudregalodetalleList;

    public Regalosinventario() {
    }

    public Regalosinventario(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Regalos getRegalo() {
        return regalo;
    }

    public void setRegalo(Regalos regalo) {
        this.regalo = regalo;
    }

    @XmlTransient
    public List<Ordencompraregalodetalle> getOrdencompraregalodetalleList() {
        return ordencompraregalodetalleList;
    }

    public void setOrdencompraregalodetalleList(List<Ordencompraregalodetalle> ordencompraregalodetalleList) {
        this.ordencompraregalodetalleList = ordencompraregalodetalleList;
    }

    @XmlTransient
    public List<Solicitudregalodetalle> getSolicitudregalodetalleList() {
        return solicitudregalodetalleList;
    }

    public void setSolicitudregalodetalleList(List<Solicitudregalodetalle> solicitudregalodetalleList) {
        this.solicitudregalodetalleList = solicitudregalodetalleList;
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
        if (!(object instanceof Regalosinventario)) {
            return false;
        }
        Regalosinventario other = (Regalosinventario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Regalosinventario[ id=" + id + " ]";
    }
    
}
