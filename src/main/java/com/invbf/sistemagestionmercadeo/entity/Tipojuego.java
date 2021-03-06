/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "TiposJuegos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipojuego.findAll", query = "SELECT t FROM Tipojuego t"),
    @NamedQuery(name = "Tipojuego.findByIdTipoJuego", query = "SELECT t FROM Tipojuego t WHERE t.idTipoJuego = :idTipoJuego"),
    @NamedQuery(name = "Tipojuego.findByNombre", query = "SELECT t FROM Tipojuego t WHERE t.nombre = :nombre")})
public class Tipojuego implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTipoJuego")
    private Integer idTipoJuego;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @ManyToMany(mappedBy = "tipojuegoList")
    private List<Cliente> clienteList;

    public Tipojuego() {
    }

    public Tipojuego(Integer idTipoJuego) {
        this.idTipoJuego = idTipoJuego;
    }

    public Tipojuego(Integer idTipoJuego, String nombre) {
        this.idTipoJuego = idTipoJuego;
        this.nombre = nombre;
    }

    public Integer getIdTipoJuego() {
        return idTipoJuego;
    }

    public void setIdTipoJuego(Integer idTipoJuego) {
        this.idTipoJuego = idTipoJuego;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoJuego != null ? idTipoJuego.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipojuego)) {
            return false;
        }
        Tipojuego other = (Tipojuego) object;
        if ((this.idTipoJuego == null && other.idTipoJuego != null) || (this.idTipoJuego != null && !this.idTipoJuego.equals(other.idTipoJuego))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idTipoJuego+" "+nombre;
    }
    
}
