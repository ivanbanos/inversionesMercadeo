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
@Table(name = "ordencompraregalos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ordencompraregalos.findAll", query = "SELECT o FROM Ordencompraregalos o"),
    @NamedQuery(name = "Ordencompraregalos.findById", query = "SELECT o FROM Ordencompraregalos o WHERE o.id = :id"),
    @NamedQuery(name = "Ordencompraregalos.findByEstado", query = "SELECT o FROM Ordencompraregalos o WHERE o.estado = :estado"),
    @NamedQuery(name = "Ordencompraregalos.findByFechaCreacion", query = "SELECT o FROM Ordencompraregalos o WHERE o.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Ordencompraregalos.findByFechaAceptada", query = "SELECT o FROM Ordencompraregalos o WHERE o.fechaAceptada = :fechaAceptada"),
    @NamedQuery(name = "Ordencompraregalos.findByFechaRecibida", query = "SELECT o FROM Ordencompraregalos o WHERE o.fechaRecibida = :fechaRecibida"),
    @NamedQuery(name = "Ordencompraregalos.findByFechaVerificacion", query = "SELECT o FROM Ordencompraregalos o WHERE o.fechaVerificacion = :fechaVerificacion")})
public class Ordencompraregalos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "Estado")
    private String estado;
    @Size(max = 1000)
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "fechaCreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "fechaAceptada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAceptada;
    @Column(name = "fechaRecibida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRecibida;
    @Column(name = "fechaVerificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVerificacion;
    @JoinColumn(name = "recibidor", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario recibidor;
    @JoinColumn(name = "creador", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario creador;
    @JoinColumn(name = "aceptador", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario aceptador;
    @JoinColumn(name = "verificador", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario verificador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ordencompraregalos")
    private List<Ordencompraregalodetalle> ordencompraregalodetalleList;

    public Ordencompraregalos() {
    }

    public Ordencompraregalos(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public Date getFechaVerificacion() {
        return fechaVerificacion;
    }

    public void setFechaVerificacion(Date fechaVerificacion) {
        this.fechaVerificacion = fechaVerificacion;
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

    public Usuario getVerificador() {
        return verificador;
    }

    public void setVerificador(Usuario verificador) {
        this.verificador = verificador;
    }

    @XmlTransient
    public List<Ordencompraregalodetalle> getOrdencompraregalodetalleList() {
        return ordencompraregalodetalleList;
    }

    public void setOrdencompraregalodetalleList(List<Ordencompraregalodetalle> ordencompraregalodetalleList) {
        this.ordencompraregalodetalleList = ordencompraregalodetalleList;
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
        if (!(object instanceof Ordencompraregalos)) {
            return false;
        }
        Ordencompraregalos other = (Ordencompraregalos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Ordencompraregalos[ id=" + id + " ]";
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
}
