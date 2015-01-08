/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.entity;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "SolicitudEntregaLotes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solicitudentregalote.findAll", query = "SELECT s FROM Solicitudentregalote s"),
    @NamedQuery(name = "Solicitudentregalote.findById", query = "SELECT s FROM Solicitudentregalote s WHERE s.id = :id"),
    @NamedQuery(name = "Solicitudentregalote.findByCantidad", query = "SELECT s FROM Solicitudentregalote s WHERE s.cantidad = :cantidad")})
public class Solicitudentregalote implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "cantidad")
    private Integer cantidad;
    @JoinColumn(name = "LotesBonos_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Lotebono lotesBonosid;
    @JoinColumn(name = "SolicitudEntregaLotesMaestro", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Solicitudentregalotesmaestro solicitudEntregaLotesMaestro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "solicitudEntregaLotesid")
    private List<Bononoincluido> bononoincluidoList;

    public Solicitudentregalote() {
    }

    public Solicitudentregalote(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Lotebono getLotesBonosid() {
        return lotesBonosid;
    }

    public void setLotesBonosid(Lotebono lotesBonosid) {
        this.lotesBonosid = lotesBonosid;
    }

    public Solicitudentregalotesmaestro getSolicitudEntregaLotesMaestro() {
        return solicitudEntregaLotesMaestro;
    }

    public void setSolicitudEntregaLotesMaestro(Solicitudentregalotesmaestro solicitudEntregaLotesMaestro) {
        this.solicitudEntregaLotesMaestro = solicitudEntregaLotesMaestro;
    }

    @XmlTransient
    public List<Bononoincluido> getBononoincluidoList() {
        return bononoincluidoList;
    }

    public void setBononoincluidoList(List<Bononoincluido> bononoincluidoList) {
        this.bononoincluidoList = bononoincluidoList;
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
        if (!(object instanceof Solicitudentregalote)) {
            return false;
        }
        Solicitudentregalote other = (Solicitudentregalote) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Solicitudentregalote[ id=" + id + " ]";
    }
    
}
