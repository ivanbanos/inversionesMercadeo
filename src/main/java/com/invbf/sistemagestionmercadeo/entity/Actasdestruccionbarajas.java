/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "actasdestruccionbarajas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Actasdestruccionbarajas.findAll", query = "SELECT a FROM Actasdestruccionbarajas a"),
    @NamedQuery(name = "Actasdestruccionbarajas.findById", query = "SELECT a FROM Actasdestruccionbarajas a WHERE a.id = :id"),
    @NamedQuery(name = "Actasdestruccionbarajas.findByCantidad", query = "SELECT a FROM Actasdestruccionbarajas a WHERE a.cantidad = :cantidad")})
public class Actasdestruccionbarajas implements Serializable {
    @JoinColumn(name = "acta", referencedColumnName = "id")
    @ManyToOne
    private Destruccionbarajasmaestro acta;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "cantidad")
    private Integer cantidad;
    @JoinColumn(name = "inventario", referencedColumnName = "id")
    @ManyToOne
    private Inventarobarajas inventario;

    public Actasdestruccionbarajas() {
    }

    public Actasdestruccionbarajas(Integer id) {
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

    public Inventarobarajas getInventario() {
        return inventario;
    }

    public void setInventario(Inventarobarajas inventario) {
        this.inventario = inventario;
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
        if (!(object instanceof Actasdestruccionbarajas)) {
            return false;
        }
        Actasdestruccionbarajas other = (Actasdestruccionbarajas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Actasdestruccionbarajas[ id=" + id + " ]";
    }

    public Destruccionbarajasmaestro getActa() {
        return acta;
    }

    public void setActa(Destruccionbarajasmaestro acta) {
        this.acta = acta;
    }
    
}
