/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "Denominaciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Denominacion.findAll", query = "SELECT d FROM Denominacion d"),
    @NamedQuery(name = "Denominacion.findById", query = "SELECT d FROM Denominacion d WHERE d.id = :id"),
    @NamedQuery(name = "Denominacion.findByValor", query = "SELECT d FROM Denominacion d WHERE d.valor = :valor")})
public class Denominacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private float valor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "denominacion")
    private List<Bono> bonoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "denominacion")
    private List<Lotebono> lotebonoList;

    public Denominacion() {
    }

    public Denominacion(Integer id) {
        this.id = id;
    }

    public Denominacion(Integer id, float valor) {
        this.id = id;
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    @XmlTransient
    public List<Bono> getBonoList() {
        return bonoList;
    }

    public void setBonoList(List<Bono> bonoList) {
        this.bonoList = bonoList;
    }

    @XmlTransient
    public List<Lotebono> getLotebonoList() {
        return lotebonoList;
    }

    public void setLotebonoList(List<Lotebono> lotebonoList) {
        this.lotebonoList = lotebonoList;
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
        if (!(object instanceof Denominacion)) {
            return false;
        }
        Denominacion other = (Denominacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Denominacion[ id=" + id + " ]";
    }
    
}
