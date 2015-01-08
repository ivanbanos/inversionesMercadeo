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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "Acciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accion.findAll", query = "SELECT a FROM Accion a"),
    @NamedQuery(name = "Accion.findByIdAccion", query = "SELECT a FROM Accion a WHERE a.idAccion = :idAccion"),
    @NamedQuery(name = "Accion.findByNombre", query = "SELECT a FROM Accion a WHERE a.nombre = :nombre")})
public class Accion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAccion")
    private Integer idAccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @JoinTable(name = "Acciones_has_Tipostareas", joinColumns = {
        @JoinColumn(name = "idAccion", referencedColumnName = "idAccion")}, inverseJoinColumns = {
        @JoinColumn(name = "idTipoTarea", referencedColumnName = "idTipotarea")})
    @ManyToMany
    private List<Tipotarea> tipotareaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAccion")
    private List<Listasclientestareas> listasclientestareasList;

    public Accion() {
    }

    public Accion(Integer idAccion) {
        this.idAccion = idAccion;
    }

    public Accion(Integer idAccion, String nombre) {
        this.idAccion = idAccion;
        this.nombre = nombre;
    }

    public Integer getIdAccion() {
        return idAccion;
    }

    public void setIdAccion(Integer idAccion) {
        this.idAccion = idAccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Tipotarea> getTipotareaList() {
        return tipotareaList;
    }

    public void setTipotareaList(List<Tipotarea> tipotareaList) {
        this.tipotareaList = tipotareaList;
    }

    @XmlTransient
    public List<Listasclientestareas> getListasclientestareasList() {
        return listasclientestareasList;
    }

    public void setListasclientestareasList(List<Listasclientestareas> listasclientestareasList) {
        this.listasclientestareasList = listasclientestareasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccion != null ? idAccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accion)) {
            return false;
        }
        Accion other = (Accion) object;
        if ((this.idAccion == null && other.idAccion != null) || (this.idAccion != null && !this.idAccion.equals(other.idAccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Accion[ idAccion=" + idAccion + " ]";
    }
    
}
