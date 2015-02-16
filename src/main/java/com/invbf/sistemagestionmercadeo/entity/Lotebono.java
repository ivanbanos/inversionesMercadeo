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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "LotesBonos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lotebono.findAll", query = "SELECT l FROM Lotebono l"),
    @NamedQuery(name = "Lotebono.findById", query = "SELECT l FROM Lotebono l WHERE l.id = :id"),
    @NamedQuery(name = "Lotebono.findByDesde", query = "SELECT l FROM Lotebono l WHERE l.desde = :desde"),
    @NamedQuery(name = "Lotebono.findByHasta", query = "SELECT l FROM Lotebono l WHERE l.hasta = :hasta"),
    @NamedQuery(name = "Lotesbonos.findByIdCasino", query = "SELECT l FROM Lotebono l WHERE l.idCasino = :idCasino"),
    @NamedQuery(name = "getexistesnte", query = "SELECT l FROM Lotebono l WHERE l.idCasino = :idCasino AND l.denominacion = :denominacion AND l.tipoBono = :tipoBono"),
    @NamedQuery(name = "getbyCasinoTipobono", query = "SELECT l FROM Lotebono l WHERE l.idCasino.idCasino = :idCasino AND l.tipoBono = :tipoBono")})
public class Lotebono implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "desde")
    private String desde;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "hasta")
    private String hasta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lotesBonosid", fetch = FetchType.LAZY)
    private List<Solicitudentregalote> solicitudentregaloteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lotesBonosid", fetch = FetchType.LAZY)
    private List<Bononofisico> bononofisicoList;
    @JoinColumn(name = "TipoBono", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Tipobono tipoBono;
    @JoinColumn(name = "Denominacion", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Denominacion denominacion;
    @JoinColumn(name = "idCasino", referencedColumnName = "idCasino")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Casino idCasino;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lotebono", fetch = FetchType.LAZY)
    private List<ControlsalidabonosHasLotesbonos> controlsalidabonosHasLotesbonosList;

    public Lotebono() {
    }

    public Lotebono(Integer id) {
        this.id = id;
    }

    public Lotebono(Integer id, String desde, String hasta) {
        this.id = id;
        this.desde = desde;
        this.hasta = hasta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesde() {
        return desde;
    }

    public void setDesde(String desde) {
        this.desde = desde;
    }

    public String getHasta() {
        return hasta;
    }

    public void setHasta(String hasta) {
        this.hasta = hasta;
    }

    @XmlTransient
    public List<Solicitudentregalote> getSolicitudentregaloteList() {
        return solicitudentregaloteList;
    }

    public void setSolicitudentregaloteList(List<Solicitudentregalote> solicitudentregaloteList) {
        this.solicitudentregaloteList = solicitudentregaloteList;
    }

    @XmlTransient
    public List<Bononofisico> getBononofisicoList() {
        return bononofisicoList;
    }

    public void setBononofisicoList(List<Bononofisico> bononofisicoList) {
        this.bononofisicoList = bononofisicoList;
    }

    public Tipobono getTipoBono() {
        return tipoBono;
    }

    public void setTipoBono(Tipobono tipoBono) {
        this.tipoBono = tipoBono;
    }

    public Denominacion getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(Denominacion denominacion) {
        this.denominacion = denominacion;
    }

    public Casino getIdCasino() {
        return idCasino;
    }

    public void setIdCasino(Casino idCasino) {
        this.idCasino = idCasino;
    }

    @XmlTransient
    public List<ControlsalidabonosHasLotesbonos> getControlsalidabonosHasLotesbonosList() {
        return controlsalidabonosHasLotesbonosList;
    }

    public void setControlsalidabonosHasLotesbonosList(List<ControlsalidabonosHasLotesbonos> controlsalidabonosHasLotesbonosList) {
        this.controlsalidabonosHasLotesbonosList = controlsalidabonosHasLotesbonosList;
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
        if (!(object instanceof Lotebono)) {
            return false;
        }
        Lotebono other = (Lotebono) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Lotebono[ id=" + id + " ]";
    }
    
}
