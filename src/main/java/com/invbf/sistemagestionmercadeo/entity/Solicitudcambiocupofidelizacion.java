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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "solicitudcambiocupofidelizacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solicitudcambiocupofidelizacion.findAll", query = "SELECT s FROM Solicitudcambiocupofidelizacion s"),
    @NamedQuery(name = "Solicitudcambiocupofidelizacion.findById", query = "SELECT s FROM Solicitudcambiocupofidelizacion s WHERE s.id = :id"),
    @NamedQuery(name = "Solicitudcambiocupofidelizacion.findByNuevoValor", query = "SELECT s FROM Solicitudcambiocupofidelizacion s WHERE s.nuevoValor = :nuevoValor")})
public class Solicitudcambiocupofidelizacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nuevoValor")
    private float nuevoValor;
    @JoinColumn(name = "Solicitante", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false)
    private Usuario solicitante;
    @JoinColumn(name = "idCliente", referencedColumnName = "idCliente")
    @ManyToOne(optional = false)
    private Cliente idCliente;

    public Solicitudcambiocupofidelizacion() {
    }

    public Solicitudcambiocupofidelizacion(Integer id) {
        this.id = id;
    }

    public Solicitudcambiocupofidelizacion(Integer id, float nuevoValor) {
        this.id = id;
        this.nuevoValor = nuevoValor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getNuevoValor() {
        return nuevoValor;
    }

    public void setNuevoValor(float nuevoValor) {
        this.nuevoValor = nuevoValor;
    }

    public Usuario getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Usuario solicitante) {
        this.solicitante = solicitante;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
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
        if (!(object instanceof Solicitudcambiocupofidelizacion)) {
            return false;
        }
        Solicitudcambiocupofidelizacion other = (Solicitudcambiocupofidelizacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Solicitudcambiocupofidelizacion[ id=" + id + " ]";
    }
    
}
