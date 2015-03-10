/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "ControlSalidaBonos_has_LotesBonos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ControlsalidabonosHasLotesbonos.findAll", query = "SELECT c FROM ControlsalidabonosHasLotesbonos c"),
    @NamedQuery(name = "ControlsalidabonosHasLotesbonos.findByControlSalidaBonosid", query = "SELECT c FROM ControlsalidabonosHasLotesbonos c WHERE c.controlsalidabonosHasLotesbonosPK.controlSalidaBonosid = :controlSalidaBonosid"),
    @NamedQuery(name = "ControlsalidabonosHasLotesbonos.findByLotesBonosid", query = "SELECT c FROM ControlsalidabonosHasLotesbonos c WHERE c.controlsalidabonosHasLotesbonosPK.lotesBonosid = :lotesBonosid"),
    @NamedQuery(name = "ControlsalidabonosHasLotesbonos.findByCantidad", query = "SELECT c FROM ControlsalidabonosHasLotesbonos c WHERE c.cantidad = :cantidad")})
public class ControlsalidabonosHasLotesbonos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ControlsalidabonosHasLotesbonosPK controlsalidabonosHasLotesbonosPK;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "cantPre")
    private Integer cantPre;
    @Column(name = "cantA")
    private Integer cantA;
    @Column(name = "desde")
    private String desde;
    @Column(name = "hasta")
    private String hasta;
    @JoinColumn(name = "LotesBonos_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Lotebono lotebono;
    @JoinColumn(name = "ControlSalidaBonos_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Controlsalidabono controlsalidabono;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "controlsalidabonosHasLotesbonos")
    private List<ControlsalidabonosHasLotesbonosHasClientes> controlsalidabonosHasLotesbonosHasClientesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "controlsalidabonosHasLotesbonos", fetch = FetchType.EAGER)
    private List<Clienteblanco> clienteblancoList;


    public ControlsalidabonosHasLotesbonos() {
    }

    public ControlsalidabonosHasLotesbonos(ControlsalidabonosHasLotesbonosPK controlsalidabonosHasLotesbonosPK) {
        this.controlsalidabonosHasLotesbonosPK = controlsalidabonosHasLotesbonosPK;
    }

    public ControlsalidabonosHasLotesbonos(int controlSalidaBonosid, int lotesBonosid) {
        this.controlsalidabonosHasLotesbonosPK = new ControlsalidabonosHasLotesbonosPK(controlSalidaBonosid, lotesBonosid);
    }

    public ControlsalidabonosHasLotesbonosPK getControlsalidabonosHasLotesbonosPK() {
        return controlsalidabonosHasLotesbonosPK;
    }

    public void setControlsalidabonosHasLotesbonosPK(ControlsalidabonosHasLotesbonosPK controlsalidabonosHasLotesbonosPK) {
        this.controlsalidabonosHasLotesbonosPK = controlsalidabonosHasLotesbonosPK;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Lotebono getLotebono() {
        return lotebono;
    }

    public void setLotebono(Lotebono lotebono) {
        this.lotebono = lotebono;
    }

    public Controlsalidabono getControlsalidabono() {
        return controlsalidabono;
    }

    public void setControlsalidabono(Controlsalidabono controlsalidabono) {
        this.controlsalidabono = controlsalidabono;
    }

    @XmlTransient
    public List<ControlsalidabonosHasLotesbonosHasClientes> getControlsalidabonosHasLotesbonosHasClientesList() {
        return controlsalidabonosHasLotesbonosHasClientesList;
    }

    public void setControlsalidabonosHasLotesbonosHasClientesList(List<ControlsalidabonosHasLotesbonosHasClientes> controlsalidabonosHasLotesbonosHasClientesList) {
        this.controlsalidabonosHasLotesbonosHasClientesList = controlsalidabonosHasLotesbonosHasClientesList;
    }
    
    @XmlTransient
    public List<Clienteblanco> getClienteblancoList() {
        return clienteblancoList;
    }

    public void setClienteblancoList(List<Clienteblanco> clienteblancoList) {
        this.clienteblancoList = clienteblancoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (controlsalidabonosHasLotesbonosPK != null ? controlsalidabonosHasLotesbonosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ControlsalidabonosHasLotesbonos)) {
            return false;
        }
        ControlsalidabonosHasLotesbonos other = (ControlsalidabonosHasLotesbonos) object;
        if ((this.controlsalidabonosHasLotesbonosPK == null && other.controlsalidabonosHasLotesbonosPK != null) || (this.controlsalidabonosHasLotesbonosPK != null && !this.controlsalidabonosHasLotesbonosPK.equals(other.controlsalidabonosHasLotesbonosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.ControlsalidabonosHasLotesbonos[ controlsalidabonosHasLotesbonosPK=" + controlsalidabonosHasLotesbonosPK + " ]";
    }

    public Integer getCantPre() {
        return cantPre;
    }

    public void setCantPre(Integer cantPre) {
        this.cantPre = cantPre;
    }

    public Integer getCantA() {
        return cantA;
    }

    public void setCantA(Integer cantA) {
        this.cantA = cantA;
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

}
