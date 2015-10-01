/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "casinos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Casinos.findAll", query = "SELECT c FROM Casinos c"),
    @NamedQuery(name = "Casinos.findByIdCasino", query = "SELECT c FROM Casinos c WHERE c.idCasino = :idCasino"),
    @NamedQuery(name = "Casinos.findByNombre", query = "SELECT c FROM Casinos c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Casinos.findByDireccion", query = "SELECT c FROM Casinos c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "Casinos.findByCorreo", query = "SELECT c FROM Casinos c WHERE c.correo = :correo")})
public class Casinos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCasino")
    private Integer idCasino;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 45)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 45)
    @Column(name = "correo")
    private String correo;
    @OneToMany(mappedBy = "idCasinoPreferencial")
    private Collection<Clientes> clientesCollection;

    public Casinos() {
    }

    public Casinos(Integer idCasino) {
        this.idCasino = idCasino;
    }

    public Casinos(Integer idCasino, String nombre) {
        this.idCasino = idCasino;
        this.nombre = nombre;
    }

    public Integer getIdCasino() {
        return idCasino;
    }

    public void setIdCasino(Integer idCasino) {
        this.idCasino = idCasino;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @XmlTransient
    public Collection<Clientes> getClientesCollection() {
        return clientesCollection;
    }

    public void setClientesCollection(Collection<Clientes> clientesCollection) {
        this.clientesCollection = clientesCollection;
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
        if (!(object instanceof Casinos)) {
            return false;
        }
        Casinos other = (Casinos) object;
        if ((this.idCasino == null && other.idCasino != null) || (this.idCasino != null && !this.idCasino.equals(other.idCasino))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Casinos[ idCasino=" + idCasino + " ]";
    }
    
}
