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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "destruccionbarajasmaestro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Destruccionbarajasmaestro.findAll", query = "SELECT d FROM Destruccionbarajasmaestro d"),
    @NamedQuery(name = "Destruccionbarajasmaestro.findById", query = "SELECT d FROM Destruccionbarajasmaestro d WHERE d.id = :id"),
    @NamedQuery(name = "Destruccionbarajasmaestro.findByFechaDestruccion", query = "SELECT d FROM Destruccionbarajasmaestro d WHERE d.fechaDestruccion = :fechaDestruccion")})
public class Destruccionbarajasmaestro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fechaDestruccion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDestruccion;
    @OneToMany(mappedBy = "acta")
    private List<Actasdestruccionbarajas> actasdestruccionbarajasList;
    @JoinColumn(name = "usuario", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario usuario;

    public Destruccionbarajasmaestro() {
    }

    public Destruccionbarajasmaestro(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaDestruccion() {
        return fechaDestruccion;
    }

    public void setFechaDestruccion(Date fechaDestruccion) {
        this.fechaDestruccion = fechaDestruccion;
    }

    @XmlTransient
    public List<Actasdestruccionbarajas> getActasdestruccionbarajasList() {
        return actasdestruccionbarajasList;
    }

    public void setActasdestruccionbarajasList(List<Actasdestruccionbarajas> actasdestruccionbarajasList) {
        this.actasdestruccionbarajasList = actasdestruccionbarajasList;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
        if (!(object instanceof Destruccionbarajasmaestro)) {
            return false;
        }
        Destruccionbarajasmaestro other = (Destruccionbarajasmaestro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Destruccionbarajasmaestro[ id=" + id + " ]";
    }
    
}
