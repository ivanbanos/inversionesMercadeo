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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "BonosNoIncluidos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bononoincluido.findAll", query = "SELECT b FROM Bononoincluido b"),
    @NamedQuery(name = "Bononoincluido.findById", query = "SELECT b FROM Bononoincluido b WHERE b.id = :id"),
    @NamedQuery(name = "Bononoincluido.findByConsecutivo", query = "SELECT b FROM Bononoincluido b WHERE b.consecutivo = :consecutivo"),
    @NamedQuery(name = "Bononoincluido.findByLotesBonos", query = "SELECT b FROM Bononoincluido b WHERE b.lotesBonos = :lotesBonos")})
public class Bononoincluido implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "consecutivo")
    private String consecutivo;
    @Size(max = 45)
    @Column(name = "LotesBonos")
    private String lotesBonos;
    @JoinColumn(name = "SolicitudEntregaLotes_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Solicitudentregalote solicitudEntregaLotesid;

    public Bononoincluido() {
    }

    public Bononoincluido(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getLotesBonos() {
        return lotesBonos;
    }

    public void setLotesBonos(String lotesBonos) {
        this.lotesBonos = lotesBonos;
    }

    public Solicitudentregalote getSolicitudEntregaLotesid() {
        return solicitudEntregaLotesid;
    }

    public void setSolicitudEntregaLotesid(Solicitudentregalote solicitudEntregaLotesid) {
        this.solicitudEntregaLotesid = solicitudEntregaLotesid;
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
        if (!(object instanceof Bononoincluido)) {
            return false;
        }
        Bononoincluido other = (Bononoincluido) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Bononoincluido[ id=" + id + " ]";
    }
    
}
