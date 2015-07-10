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
@Table(name = "solicitudbarajas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solicitudbarajas.findAll", query = "SELECT s FROM Solicitudbarajas s"),
    @NamedQuery(name = "Solicitudbarajas.findById", query = "SELECT s FROM Solicitudbarajas s WHERE s.id = :id"),
    @NamedQuery(name = "Solicitudbarajas.findByEstado", query = "SELECT s FROM Solicitudbarajas s WHERE s.estado = :estado"),
    @NamedQuery(name = "Solicitudbarajas.findByFechacreacion", query = "SELECT s FROM Solicitudbarajas s WHERE s.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Solicitudbarajas.findByFechentrega", query = "SELECT s FROM Solicitudbarajas s WHERE s.fechentrega = :fechentrega"),
    @NamedQuery(name = "Solicitudbarajas.findByFecharecepcion", query = "SELECT s FROM Solicitudbarajas s WHERE s.fecharecepcion = :fecharecepcion")})
public class Solicitudbarajas implements Serializable {
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "solicitudbarajas")
    private List<Solicitudbarajadetalle> solicitudbarajadetalleList;

    public Solicitudbarajas() {
    }

    public Solicitudbarajas(Integer id) {
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

    @XmlTransient
    public List<Solicitudbarajadetalle> getSolicitudbarajadetalleList() {
        return solicitudbarajadetalleList;
    }

    public void setSolicitudbarajadetalleList(List<Solicitudbarajadetalle> solicitudbarajadetalleList) {
        this.solicitudbarajadetalleList = solicitudbarajadetalleList;
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
        if (!(object instanceof Solicitudbarajas)) {
            return false;
        }
        Solicitudbarajas other = (Solicitudbarajas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.controladores.Solicitudbarajas[ id=" + id + " ]";
    }
    
}
