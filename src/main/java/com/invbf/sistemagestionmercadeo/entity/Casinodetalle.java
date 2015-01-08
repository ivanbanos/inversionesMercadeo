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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "CasinosDetalles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Casinodetalle.findAll", query = "SELECT c FROM Casinodetalle c"),
    @NamedQuery(name = "Casinodetalle.findByIdCasino", query = "SELECT c FROM Casinodetalle c WHERE c.idCasino = :idCasino"),
    @NamedQuery(name = "Casinodetalle.findByAbreviacion", query = "SELECT c FROM Casinodetalle c WHERE c.abreviacion = :abreviacion"),
    @NamedQuery(name = "Casinodetalle.findByCiudad", query = "SELECT c FROM Casinodetalle c WHERE c.ciudad = :ciudad"),
    @NamedQuery(name = "Casinodetalle.findByImagen", query = "SELECT c FROM Casinodetalle c WHERE c.imagen = :imagen"),
    @NamedQuery(name = "Casinodetalle.findByAbreCiudad", query = "SELECT c FROM Casinodetalle c WHERE c.abreCiudad = :abreCiudad")})
public class Casinodetalle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idCasino")
    private Integer idCasino;
    @Size(max = 45)
    @Column(name = "abreviacion")
    private String abreviacion;
    @Size(max = 45)
    @Column(name = "ciudad")
    private String ciudad;
    @Size(max = 45)
    @Column(name = "imagen")
    private String imagen;
    @Size(max = 45)
    @Column(name = "abreCiudad")
    private String abreCiudad;
    @JoinColumn(name = "idCasino", referencedColumnName = "idCasino", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Casino casino;

    public Casinodetalle() {
    }

    public Casinodetalle(Integer idCasino) {
        this.idCasino = idCasino;
    }

    public Integer getIdCasino() {
        return idCasino;
    }

    public void setIdCasino(Integer idCasino) {
        this.idCasino = idCasino;
    }

    public String getAbreviacion() {
        return abreviacion;
    }

    public void setAbreviacion(String abreviacion) {
        this.abreviacion = abreviacion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getAbreCiudad() {
        return abreCiudad;
    }

    public void setAbreCiudad(String abreCiudad) {
        this.abreCiudad = abreCiudad;
    }

    public Casino getCasino() {
        return casino;
    }

    public void setCasino(Casino casino) {
        this.casino = casino;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCasino != null ? idCasino.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Casinodetalle)) {
            return false;
        }
        Casinodetalle other = (Casinodetalle) object;
        if ((this.idCasino == null && other.idCasino != null) || (this.idCasino != null && !this.idCasino.equals(other.idCasino))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Casinodetalle[ idCasino=" + idCasino + " ]";
    }
    
}
