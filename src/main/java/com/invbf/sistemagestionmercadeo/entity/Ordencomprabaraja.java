/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "ordencomprabaraja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ordencomprabaraja.findAll", query = "SELECT o FROM Ordencomprabaraja o"),
    @NamedQuery(name = "Ordencomprabaraja.findById", query = "SELECT o FROM Ordencomprabaraja o WHERE o.id = :id"),
    @NamedQuery(name = "Ordencomprabaraja.findByEsatdo", query = "SELECT o FROM Ordencomprabaraja o WHERE o.esatdo = :esatdo"),
    @NamedQuery(name = "Ordencomprabaraja.findByFechaCreacion", query = "SELECT o FROM Ordencomprabaraja o WHERE o.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Ordencomprabaraja.findByFechaAceptada", query = "SELECT o FROM Ordencomprabaraja o WHERE o.fechaAceptada = :fechaAceptada"),
    @NamedQuery(name = "Ordencomprabaraja.findByFechaRecibida", query = "SELECT o FROM Ordencomprabaraja o WHERE o.fechaRecibida = :fechaRecibida")})
public class Ordencomprabaraja implements Serializable {
    @JoinColumn(name = "recibidor", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario recibidor;
    @JoinColumn(name = "creador", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario creador;
    @JoinColumn(name = "aceptador", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario aceptador;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "Esatdo")
    private String esatdo;
    @Column(name = "fechaCreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "fechaAceptada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAceptada;
    @Column(name = "fechaRecibida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRecibida;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ordencomprabaraja")
    private List<Ordencomprabarajadetalle> ordencomprabarajadetalleList;

    public Ordencomprabaraja() {
    }

    public Ordencomprabaraja(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEsatdo() {
        return esatdo;
    }

    public void setEsatdo(String esatdo) {
        this.esatdo = esatdo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaAceptada() {
        return fechaAceptada;
    }

    public void setFechaAceptada(Date fechaAceptada) {
        this.fechaAceptada = fechaAceptada;
    }

    public Date getFechaRecibida() {
        return fechaRecibida;
    }

    public void setFechaRecibida(Date fechaRecibida) {
        this.fechaRecibida = fechaRecibida;
    }

    @XmlTransient
    public List<Ordencomprabarajadetalle> getOrdencomprabarajadetalleList() {
        return ordencomprabarajadetalleList;
    }

    public void setOrdencomprabarajadetalleList(List<Ordencomprabarajadetalle> ordencomprabarajadetalleList) {
        this.ordencomprabarajadetalleList = ordencomprabarajadetalleList;
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
        if (!(object instanceof Ordencomprabaraja)) {
            return false;
        }
        Ordencomprabaraja other = (Ordencomprabaraja) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.controladores.Ordencomprabaraja[ id=" + id + " ]";
    }

    public Usuario getRecibidor() {
        return recibidor;
    }

    public void setRecibidor(Usuario recibidor) {
        this.recibidor = recibidor;
    }

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public Usuario getAceptador() {
        return aceptador;
    }

    public void setAceptador(Usuario aceptador) {
        this.aceptador = aceptador;
    }
    
}
