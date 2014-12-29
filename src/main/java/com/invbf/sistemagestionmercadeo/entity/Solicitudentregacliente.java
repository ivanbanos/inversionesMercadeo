/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "solicitudentregaclientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solicitudentregacliente.findAll", query = "SELECT s FROM Solicitudentregacliente s"),
    @NamedQuery(name = "Solicitudentregacliente.findBySolicitudEntregaid", query = "SELECT s FROM Solicitudentregacliente s WHERE s.solicitudentregaclientePK.solicitudEntregaid = :solicitudEntregaid"),
    @NamedQuery(name = "Solicitudentregacliente.findByClientesid", query = "SELECT s FROM Solicitudentregacliente s WHERE s.solicitudentregaclientePK.clientesid = :clientesid"),
    @NamedQuery(name = "Solicitudentregacliente.findByValorTotal", query = "SELECT s FROM Solicitudentregacliente s WHERE s.valorTotal = :valorTotal"),
    @NamedQuery(name = "Solicitudentregacliente.findByObservaciones", query = "SELECT s FROM Solicitudentregacliente s WHERE s.observaciones = :observaciones")})
public class Solicitudentregacliente implements Serializable {
    @Column(name = "valorPreAprobado")
    private Float valorPreAprobado;
    @Column(name = "valorAprobado")
    private Float valorAprobado;
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SolicitudentregaclientePK solicitudentregaclientePK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorTotal")
    private Float valorTotal;
    @Size(max = 45)
    @Column(name = "observaciones")
    private String observaciones;
    @JoinColumn(name = "SolicitudEntregaid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Solicitudentrega solicitudentrega;
    @JoinColumn(name = "Areaid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Area areaid;
    @JoinColumn(name = "Clientesid", referencedColumnName = "idCliente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cliente cliente;

    public Solicitudentregacliente() {
    }

    public Solicitudentregacliente(SolicitudentregaclientePK solicitudentregaclientePK) {
        this.solicitudentregaclientePK = solicitudentregaclientePK;
    }

    public Solicitudentregacliente(int solicitudEntregaid, int clientesid) {
        this.solicitudentregaclientePK = new SolicitudentregaclientePK(solicitudEntregaid, clientesid);
    }

    public SolicitudentregaclientePK getSolicitudentregaclientePK() {
        return solicitudentregaclientePK;
    }

    public void setSolicitudentregaclientePK(SolicitudentregaclientePK solicitudentregaclientePK) {
        this.solicitudentregaclientePK = solicitudentregaclientePK;
    }

    public Float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Solicitudentrega getSolicitudentrega() {
        return solicitudentrega;
    }

    public void setSolicitudentrega(Solicitudentrega solicitudentrega) {
        this.solicitudentrega = solicitudentrega;
    }

    public Area getAreaid() {
        return areaid;
    }

    public void setAreaid(Area areaid) {
        this.areaid = areaid;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (solicitudentregaclientePK != null ? solicitudentregaclientePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Solicitudentregacliente)) {
            return false;
        }
        Solicitudentregacliente other = (Solicitudentregacliente) object;
        if ((this.solicitudentregaclientePK == null && other.solicitudentregaclientePK != null) || (this.solicitudentregaclientePK != null && !this.solicitudentregaclientePK.equals(other.solicitudentregaclientePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Solicitudentregacliente[ solicitudentregaclientePK=" + solicitudentregaclientePK + " ]";
    }

    public Float getValorPreAprobado() {
        return valorPreAprobado;
    }

    public void setValorPreAprobado(Float valorPreAprobado) {
        this.valorPreAprobado = valorPreAprobado;
    }

    public Float getValorAprobado() {
        return valorAprobado;
    }

    public void setValorAprobado(Float valorAprobado) {
        this.valorAprobado = valorAprobado;
    }
    
}
