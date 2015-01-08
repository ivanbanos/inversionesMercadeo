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
@Table(name = "TiposDocumento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipodocumento.findAll", query = "SELECT t FROM Tipodocumento t"),
    @NamedQuery(name = "Tipodocumento.findByIdTipoDocumento", query = "SELECT t FROM Tipodocumento t WHERE t.idTipoDocumento = :idTipoDocumento"),
    @NamedQuery(name = "Tipodocumento.findByNombre", query = "SELECT t FROM Tipodocumento t WHERE t.nombre = :nombre")})
public class Tipodocumento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTipoDocumento")
    private Integer idTipoDocumento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "idTipoDocumento")
    private List<Cliente> clienteList;

    public Tipodocumento() {
    }

    public Tipodocumento(Integer idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public Tipodocumento(Integer idTipoDocumento, String nombre) {
        this.idTipoDocumento = idTipoDocumento;
        this.nombre = nombre;
    }

    public Integer getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(Integer idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
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
        hash += (idTipoDocumento != null ? idTipoDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipodocumento)) {
            return false;
        }
        Tipodocumento other = (Tipodocumento) object;
        if ((this.idTipoDocumento == null && other.idTipoDocumento != null) || (this.idTipoDocumento != null && !this.idTipoDocumento.equals(other.idTipoDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Tipodocumento[ idTipoDocumento=" + idTipoDocumento + " ]";
    }
    
}
