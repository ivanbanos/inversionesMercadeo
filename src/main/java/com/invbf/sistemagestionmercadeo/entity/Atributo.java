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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "Atributos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Atributo.findAll", query = "SELECT a FROM Atributo a"),
    @NamedQuery(name = "Atributo.findByIdAtributo", query = "SELECT a FROM Atributo a WHERE a.idAtributo = :idAtributo"),
    @NamedQuery(name = "Atributo.findByNombre", query = "SELECT a FROM Atributo a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Atributo.findByTipoDato", query = "SELECT a FROM Atributo a WHERE a.tipoDato = :tipoDato")})
public class Atributo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAtributo")
    private Integer idAtributo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tipoDato")
    private String tipoDato;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "atributo")
    private List<Clienteatributo> clienteatributoList;

    public Atributo() {
    }

    public Atributo(Integer idAtributo) {
        this.idAtributo = idAtributo;
    }

    public Atributo(Integer idAtributo, String nombre, String tipoDato) {
        this.idAtributo = idAtributo;
        this.nombre = nombre;
        this.tipoDato = tipoDato;
    }

    public Integer getIdAtributo() {
        return idAtributo;
    }

    public void setIdAtributo(Integer idAtributo) {
        this.idAtributo = idAtributo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    @XmlTransient
    public List<Clienteatributo> getClienteatributoList() {
        return clienteatributoList;
    }

    public void setClienteatributoList(List<Clienteatributo> clienteatributoList) {
        this.clienteatributoList = clienteatributoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAtributo != null ? idAtributo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Atributo)) {
            return false;
        }
        Atributo other = (Atributo) object;
        if ((this.idAtributo == null && other.idAtributo != null) || (this.idAtributo != null && !this.idAtributo.equals(other.idAtributo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Atributo[ idAtributo=" + idAtributo + " ]";
    }
    
}
