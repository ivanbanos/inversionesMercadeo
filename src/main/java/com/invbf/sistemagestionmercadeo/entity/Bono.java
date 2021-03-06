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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "Bono")
@XmlRootElement
@NamedQueries({
    
    
    
    
    @NamedQuery(name = "Bono.getReporteBonoVer", query = "SELECT b FROM Bono b WHERE b.fechaExpiracion >= :desde AND b.fechaExpiracion <= :hasta AND b.casino = :casino "),
    @NamedQuery(name = "Bono.getReporteBono", query = "SELECT b FROM Bono b WHERE b.fechaExpiracion >= :desde AND b.fechaExpiracion <= :hasta AND b.casino = :casino AND b.propositosEntregaid = :proposito"),
    
    
    @NamedQuery(name = "Bono.revisarestados", query = "UPDATE Bono b SET b.estado = 'VENCIDO' WHERE b.fechaExpiracion < CURRENT_DATE AND b.estado != 'ANULADO' AND b.estado != 'CANJEADO'"),
    
    @NamedQuery(name = "Bono.cambiarestadoBonoByControl", query = "UPDATE Bono b SET b.estado = 'DILIGENCIADO' WHERE b.controlSalidaBonosid = :control AND b.estado != 'ANULADO'"),
    @NamedQuery(name = "Bono.findAll", query = "SELECT b FROM Bono b"),
    @NamedQuery(name = "Bono.findById", query = "SELECT b FROM Bono b WHERE b.id = :id"),
    @NamedQuery(name = "Bono.findByConsecutivo", query = "SELECT b FROM Bono b WHERE b.consecutivo = :consecutivo "),
    @NamedQuery(name = "Bono.findByEstado", query = "SELECT b FROM Bono b WHERE b.estado = :estado"),
    @NamedQuery(name = "Bono.findByFechaValidacion", query = "SELECT b FROM Bono b WHERE b.fechaValidacion = :fechaValidacion"),
    @NamedQuery(name = "Bono.findByFechaExpiracion", query = "SELECT b FROM Bono b WHERE b.fechaExpiracion = :fechaExpiracion"),
    @NamedQuery(name = "Bono.findByEstadoYCasino", query = "SELECT b FROM Bono b WHERE b.estado = :estado AND b.casino = :casino"),
    @NamedQuery(name = "Bono.findByAtributos", query = "SELECT b FROM Bono b WHERE b.estado = :estado AND b.casino = :casino AND b.cliente.nombres LIKE :nombres AND b.cliente.apellidos LIKE :apellidos AND b.cliente.identificacion LIKE :identificacion AND b.consecutivo LIKE :consecutivo"),
    @NamedQuery(name = "Bono.findByFechaEntrega", query = "SELECT b FROM Bono b WHERE b.fechaEntrega = :fechaEntrega"),
    @NamedQuery(name = "Bono.findByRangoFechas", query = "SELECT b FROM Bono b WHERE b.fechaExpiracion >= :desde AND b.fechaExpiracion <= :hasta"),
    @NamedQuery(name = "Bono.findBonoEsp", query = "SELECT b FROM Bono b WHERE b.casino = :casino AND b.denominacion = :denominacion AND b.consecutivo = :consecutivo"),
    @NamedQuery(name = "Bono.findByEstadoSinJustificacion", query = "SELECT b FROM Bono b WHERE b.casino = :casino AND b.controlSalidaBonosid.solicitudEntregaid.propositoEntrega.id = :tipo AND b.estado = :estado AND (b.justificacion = NULL OR b.justificacion = '') AND (b.nombreCliente = NULL OR b.nombreCliente = '' OR b.nombreCliente = ' ') ORDER BY b.consecutivo ASC"),
    
    @NamedQuery(name = "Bono.findByRangoFechasCasino", query = "SELECT b FROM Bono b WHERE b.fechaExpiracion >= :desde AND b.fechaExpiracion <= :hasta AND b.casino = :casino")})
public class Bono implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
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
    @JoinColumn(name = "autorizador", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario autorizador;
    @JoinColumn(name = "validador", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario validador;
    @JoinColumn(name = "tipo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Tipobono tipo;
    @JoinColumn(name = "PropositosEntrega_id", referencedColumnName = "id")
    @ManyToOne
    private Propositoentrega propositosEntregaid;
    @JoinColumn(name = "Denominacion", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Denominacion denominacion;
    @JoinColumn(name = "ControlSalidaBonos_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Controlsalidabono controlSalidaBonosid;
    @JoinColumn(name = "Cliente", referencedColumnName = "idCliente")
    @ManyToOne
    private Cliente cliente;
    @JoinColumn(name = "casino", referencedColumnName = "idCasino")
    @ManyToOne(optional = false)
    private Casino casino;
    @Size(max = 90)
    @Column(name = "nombreCliente")
    private String nombreCliente;
    @Size(max = 200)
    @Column(name = "razonAnulamiento")
    private String razonAnulamiento;
    @Size(max = 400)
    @Column(name = "causadenocanje")
    private String causadenocanje;
    @Size(max = 500)
    @Column(name = "justificacion")
    private String justificacion;

    public Bono() {
    }

    public Bono(Integer id) {
        this.id = id;
    }

    public Bono(Integer id, String consecutivo) {
        this.id = id;
        this.consecutivo = consecutivo;
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

    public Tipobono getTipo() {
        return tipo;
    }

    public void setTipo(Tipobono tipo) {
        this.tipo = tipo;
    }

    public Propositoentrega getPropositosEntregaid() {
        return propositosEntregaid;
    }

    public void setPropositosEntregaid(Propositoentrega propositosEntregaid) {
        this.propositosEntregaid = propositosEntregaid;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Casino getCasino() {
        return casino;
    }

    public void setCasino(Casino casino) {
        this.casino = casino;
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
        if (!(object instanceof Bono)) {
            return false;
        }
        Bono other = (Bono) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Bono[ id=" + id + " ]";
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getRazonAnulamiento() {
        return razonAnulamiento;
    }

    public void setRazonAnulamiento(String razonAnulamiento) {
        this.razonAnulamiento = razonAnulamiento;
    }

    public String getCausadenocanje() {
        return causadenocanje;
    }

    public void setCausadenocanje(String causadenocanje) {
        this.causadenocanje = causadenocanje;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }
    
}
