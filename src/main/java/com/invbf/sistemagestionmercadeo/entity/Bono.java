/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "bono")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bono.findAll", query = "SELECT b FROM Bono b"),
    @NamedQuery(name = "Bono.findByConsecutivo", query = "SELECT b FROM Bono b WHERE b.consecutivo = :consecutivo"),
    @NamedQuery(name = "Bono.findByEstado", query = "SELECT b FROM Bono b WHERE b.estado = :estado"),
    @NamedQuery(name = "Bono.findByEstadoYCasino", query = "SELECT b FROM Bono b WHERE b.estado = :estado AND b.casino = :casino"),
    @NamedQuery(name = "Bono.findByFechaValidacion", query = "SELECT b FROM Bono b WHERE b.fechaValidacion = :fechaValidacion"),
    @NamedQuery(name = "Bono.findByFechaExpiracion", query = "SELECT b FROM Bono b WHERE b.fechaExpiracion = :fechaExpiracion"),
    @NamedQuery(name = "Bono.findByFechaEntrega", query = "SELECT b FROM Bono b WHERE b.fechaEntrega = :fechaEntrega"),
    @NamedQuery(name = "Bono.findByValidador", query = "SELECT b FROM Bono b WHERE b.validador = :validador"),
    @NamedQuery(name = "Bono.findByAutorizador", query = "SELECT b FROM Bono b WHERE b.autorizador = :autorizador")})
public class Bono implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "consecutivo")
    private String consecutivo;
    @Size(max = 45)
    @Column(name = "estado")
    private String estado;
    @Column(name = "fechaValidacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaValidacion;
    @Column(name = "fechaExpiracion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaExpiracion;
    @Column(name = "fechaEntrega")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntrega;
    @JoinColumn(name = "Denominacion", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Denominacion denominacion;
    @JoinColumn(name = "ControlSalidaBonos_id", referencedColumnName = "id")
    @ManyToOne
    private Controlsalidabono controlSalidaBonosid;
    @JoinColumn(name = "tipo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Tipobono tipo;
    @JoinColumn(name = "autorizador", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario autorizador;
    @JoinColumn(name = "validador", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario validador;
    @JoinColumn(name = "casino", referencedColumnName = "idCasino")
    @ManyToOne(optional = false)
    private Casino casino;
    @JoinColumn(name = "Cliente", referencedColumnName = "idCliente")
    @ManyToOne(optional = false)
    private Cliente cliente;

    public Bono() {
    }

    public Bono(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaValidacion() {
        return fechaValidacion;
    }

    public void setFechaValidacion(Date fechaValidacion) {
        this.fechaValidacion = fechaValidacion;
    }

    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Denominacion getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(Denominacion denominacion) {
        this.denominacion = denominacion;
    }

    public Controlsalidabono getControlSalidaBonosid() {
        return controlSalidaBonosid;
    }

    public void setControlSalidaBonosid(Controlsalidabono controlSalidaBonosid) {
        this.controlSalidaBonosid = controlSalidaBonosid;
    }

    public Tipobono getTipo() {
        return tipo;
    }

    public void setTipo(Tipobono tipo) {
        this.tipo = tipo;
    }

    public Usuario getAutorizador() {
        return autorizador;
    }

    public void setAutorizador(Usuario autorizador) {
        this.autorizador = autorizador;
    }

    public Usuario getValidador() {
        return validador;
    }

    public void setValidador(Usuario validador) {
        this.validador = validador;
    }

    public Casino getCasino() {
        return casino;
    }

    public void setCasino(Casino casino) {
        this.casino = casino;
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
        hash += (consecutivo != null ? consecutivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bono)) {
            return false;
        }
        Bono other = (Bono) object;
        if ((this.consecutivo == null && other.consecutivo != null) || (this.consecutivo != null && !this.consecutivo.equals(other.consecutivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Bono[ consecutivo=" + consecutivo + " ]";
    }
    
}
