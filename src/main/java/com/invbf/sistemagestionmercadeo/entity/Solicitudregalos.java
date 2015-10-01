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
@Table(name = "solicitudregalos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solicitudregalos.findAll", query = "SELECT s FROM Solicitudregalos s"),
    @NamedQuery(name = "Solicitudregalos.findById", query = "SELECT s FROM Solicitudregalos s WHERE s.id = :id"),
    @NamedQuery(name = "Solicitudregalos.findByEstado", query = "SELECT s FROM Solicitudregalos s WHERE s.estado = :estado"),
    @NamedQuery(name = "Solicitudregalos.findByFechacreacion", query = "SELECT s FROM Solicitudregalos s WHERE s.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Solicitudregalos.findByFechentrega", query = "SELECT s FROM Solicitudregalos s WHERE s.fechentrega = :fechentrega"),
    @NamedQuery(name = "Solicitudregalos.findByFecharecepcion", query = "SELECT s FROM Solicitudregalos s WHERE s.fecharecepcion = :fecharecepcion"),
    @NamedQuery(name = "Solicitudregalos.findBySala", query = "SELECT s FROM Solicitudregalos s WHERE s.sala.idCasino = :casino")})
public class Solicitudregalos implements Serializable {
    @JoinColumn(name = "sala", referencedColumnName = "idCasino")
    @ManyToOne
    private Casino sala;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "estado")
    private String estado;
    @Column(name = "fechacreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;
    @Column(name = "fechentrega")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechentrega;
    @Column(name = "fecharecepcion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecharecepcion;
    @Column(name = "fechaDestruccion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAprobacion;
    @JoinColumn(name = "recibidor", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario recibidor;
    @JoinColumn(name = "destructor", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario enviador;
    @JoinColumn(name = "creador", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario creador;
    @JoinColumn(name = "aceptador", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario aceptador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "solicitudregalos")
    private List<Solicitudregalodetalle> solicitudregalodetalleList;

    public Solicitudregalos() {
    }

    public Solicitudregalos(Integer id) {
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

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public Date getFechentrega() {
        return fechentrega;
    }

    public void setFechentrega(Date fechentrega) {
        this.fechentrega = fechentrega;
    }

    public Date getFecharecepcion() {
        return fecharecepcion;
    }

    public void setFecharecepcion(Date fecharecepcion) {
        this.fecharecepcion = fecharecepcion;
    }

    public Date getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(Date fechaDestruccion) {
        this.fechaAprobacion = fechaDestruccion;
    }

    public Usuario getRecibidor() {
        return recibidor;
    }

    public void setRecibidor(Usuario recibidor) {
        this.recibidor = recibidor;
    }

    public Usuario getEnviador() {
        return enviador;
    }

    public void setEnviador(Usuario enviador) {
        this.enviador = enviador;
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

    @XmlTransient
    public List<Solicitudregalodetalle> getSolicitudregalodetalleList() {
        return solicitudregalodetalleList;
    }

    public void setSolicitudregalodetalleList(List<Solicitudregalodetalle> solicitudregalodetalleList) {
        this.solicitudregalodetalleList = solicitudregalodetalleList;
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
        if (!(object instanceof Solicitudregalos)) {
            return false;
        }
        Solicitudregalos other = (Solicitudregalos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Solicitudregalos[ id=" + id + " ]";
    }

    public Casino getSala() {
        return sala;
    }

    public void setSala(Casino sala) {
        this.sala = sala;
    }
    
}
