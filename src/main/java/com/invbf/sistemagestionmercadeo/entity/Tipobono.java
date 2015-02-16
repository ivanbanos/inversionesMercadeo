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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "TiposBonos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipobono.findAll", query = "SELECT t FROM Tipobono t"),
    @NamedQuery(name = "Tipobono.findById", query = "SELECT t FROM Tipobono t WHERE t.id = :id"),
    @NamedQuery(name = "Tipobono.findByNombre", query = "SELECT t FROM Tipobono t WHERE t.nombre = :nombre")})
public class Tipobono implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoBono", fetch = FetchType.LAZY)
    private List<Solicitudentrega> solicitudentregaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipo", fetch = FetchType.LAZY)
    private List<Bono> bonoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoBono", fetch = FetchType.LAZY)
    private List<Lotebono> lotebonoList;

    public Tipobono() {
    }

    public Tipobono(Integer id) {
        this.id = id;
    }

    public Tipobono(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Solicitudentrega> getSolicitudentregaList() {
        return solicitudentregaList;
    }

    public void setSolicitudentregaList(List<Solicitudentrega> solicitudentregaList) {
        this.solicitudentregaList = solicitudentregaList;
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
        if (!(object instanceof Tipobono)) {
            return false;
        }
        Tipobono other = (Tipobono) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id + " " + nombre;
    }
    
}
