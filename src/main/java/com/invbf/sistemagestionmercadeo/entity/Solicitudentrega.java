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
@Table(name = "SolicitudEntrega")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solicitudentrega.revisarestados", query = "UPDATE Solicitudentrega s SET s.estado = 'BONOS VENCIDOS. PENDIENTE POR GENERAR REPORTE' WHERE s.fechavencimientobonos < CURRENT_DATE AND s.estado != 'REPORTE DE GESTIÓN DISPONIBLE' "),
   
    @NamedQuery(name = "Solicitudentrega.vencidoSol", query = "SELECT s FROM Solicitudentrega s WHERE s.solicitante.idUsuario = :solicitante AND ( s.estado = 'BONOS VENCIDOS. PENDIENTE POR GENERAR REPORTE' OR s.estado = 'REPORTE DE GESTIÓN DISPONIBLE')"),
    @NamedQuery(name = "Solicitudentrega.vencido", query = "SELECT s FROM Solicitudentrega s WHERE ( s.estado = 'BONOS VENCIDOS. PENDIENTE POR GENERAR REPORTE' OR s.estado = 'REPORTE DE GESTIÓN DISPONIBLE')"),
    @NamedQuery(name = "Solicitudentrega.novencidoSol", query = "SELECT s FROM Solicitudentrega s WHERE s.solicitante.idUsuario = :solicitante AND ( s.estado != 'BONOS VENCIDOS. PENDIENTE POR GENERAR REPORTE' AND s.estado != 'REPORTE DE GESTIÓN DISPONIBLE')"),
    @NamedQuery(name = "Solicitudentrega.novencido", query = "SELECT s FROM Solicitudentrega s WHERE ( s.estado != 'REPORTE DE GESTIÓN DISPONIBLE')"),
    
    @NamedQuery(name = "Solicitudentrega.creados", query = "SELECT COUNT(s) FROM Solicitudentrega s WHERE s.estado = 'CREADA'"),
    @NamedQuery(name = "Solicitudentrega.preaprobados", query = "SELECT COUNT(s) FROM Solicitudentrega s WHERE s.estado = 'CREADA' OR s.estado = 'PREAPROBADA'"),
    
    
    @NamedQuery(name = "Solicitudentrega.findAll", query = "SELECT s FROM Solicitudentrega s"),
    @NamedQuery(name = "Solicitudentrega.findById", query = "SELECT s FROM Solicitudentrega s WHERE s.id = :id"),
    @NamedQuery(name = "Solicitudentrega.findByFecha", query = "SELECT s FROM Solicitudentrega s WHERE s.fecha = :fecha"),
    @NamedQuery(name = "Solicitudentrega.findBySolicitante", query = "SELECT s FROM Solicitudentrega s WHERE s.solicitante.idUsuario = :solicitante"),
    @NamedQuery(name = "Solicitudentrega.findBySolicitanteestado", query = "SELECT s FROM Solicitudentrega s WHERE s.solicitante.idUsuario = :solicitante AND s.estado = :estado"),
    @NamedQuery(name = "Solicitudentrega.findByAprobador", query = "SELECT s FROM Solicitudentrega s WHERE s.aprobador = :aprobador"),
    @NamedQuery(name = "Solicitudentrega.findByJustificacion", query = "SELECT s FROM Solicitudentrega s WHERE s.justificacion = :justificacion"),
    @NamedQuery(name = "Solicitudentrega.findByEstado", query = "SELECT s FROM Solicitudentrega s WHERE s.estado = :estado"),
    @NamedQuery(name = "Solicitudentrega.findByFormareparticrbonos", query = "SELECT s FROM Solicitudentrega s WHERE s.formareparticrbonos = :formareparticrbonos"),
    @NamedQuery(name = "Solicitudentrega.findByTotal", query = "SELECT s FROM Solicitudentrega s WHERE s.total = :total"),
    @NamedQuery(name = "Solicitudentrega.findByTotalpreaprobado", query = "SELECT s FROM Solicitudentrega s WHERE s.totalpreaprobado = :totalpreaprobado"),
    @NamedQuery(name = "Solicitudentrega.findByTotalaprobado", query = "SELECT s FROM Solicitudentrega s WHERE s.totalaprobado = :totalaprobado")})
public class Solicitudentrega implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 400)
    @Column(name = "justificacion")
    private String justificacion;
    @Size(max = 45)
    @Column(name = "estado")
    private String estado;
    @Column(name = "formareparticrbonos")
    private Integer formareparticrbonos;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total")
    private Float total;
    @Column(name = "totalpreaprobado")
    private Float totalpreaprobado;
    @Column(name = "totalaprobado")
    private Float totalaprobado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "solicitudentrega", fetch = FetchType.EAGER)
    private List<Solicitudentregacliente> solicitudentregaclienteList;
    @JoinColumn(name = "aprobador", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario aprobador;
    @JoinColumn(name = "solicitante", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario solicitante;
    @JoinColumn(name = "TipoBono", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Tipobono tipoBono;
    @JoinColumn(name = "PropositoEntrega", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Propositoentrega propositoEntrega;
    @JoinColumn(name = "idCasino", referencedColumnName = "idCasino")
    @ManyToOne
    private Casino idCasino;
    @OneToMany(mappedBy = "solicitudEntregaid", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Controlsalidabono> controlsalidabonoList;
    @Column(name = "fechavencimientobonos")
    @Temporal(TemporalType.DATE)
    private Date fechavencimientobonos;

    public Solicitudentrega() {
    }

    public Solicitudentrega(Integer id) {
        this.id = id;
    }

    public Solicitudentrega(Integer id, Date fecha) {
        this.id = id;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getFormareparticrbonos() {
        return formareparticrbonos;
    }

    public void setFormareparticrbonos(Integer formareparticrbonos) {
        this.formareparticrbonos = formareparticrbonos;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Float getTotalpreaprobado() {
        return totalpreaprobado;
    }

    public void setTotalpreaprobado(Float totalpreaprobado) {
        this.totalpreaprobado = totalpreaprobado;
    }

    public Float getTotalaprobado() {
        return totalaprobado;
    }

    public void setTotalaprobado(Float totalaprobado) {
        this.totalaprobado = totalaprobado;
    }

    @XmlTransient
    public List<Solicitudentregacliente> getSolicitudentregaclienteList() {
        return solicitudentregaclienteList;
    }

    public void setSolicitudentregaclienteList(List<Solicitudentregacliente> solicitudentregaclienteList) {
        this.solicitudentregaclienteList = solicitudentregaclienteList;
    }

    public Usuario getAprobador() {
        return aprobador;
    }

    public void setAprobador(Usuario aprobador) {
        this.aprobador = aprobador;
    }

    public Usuario getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Usuario solicitante) {
        this.solicitante = solicitante;
    }

    public Tipobono getTipoBono() {
        return tipoBono;
    }

    public void setTipoBono(Tipobono tipoBono) {
        this.tipoBono = tipoBono;
    }

    public Propositoentrega getPropositoEntrega() {
        return propositoEntrega;
    }

    public void setPropositoEntrega(Propositoentrega propositoEntrega) {
        this.propositoEntrega = propositoEntrega;
    }

    public Casino getIdCasino() {
        return idCasino;
    }

    public void setIdCasino(Casino idCasino) {
        this.idCasino = idCasino;
    }

    @XmlTransient
    public List<Controlsalidabono> getControlsalidabonoList() {
        return controlsalidabonoList;
    }

    public void setControlsalidabonoList(List<Controlsalidabono> controlsalidabonoList) {
        this.controlsalidabonoList = controlsalidabonoList;
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
        if (!(object instanceof Solicitudentrega)) {
            return false;
        }
        Solicitudentrega other = (Solicitudentrega) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Solicitudentrega[ id=" + id + " ]";
    }

    public Date getFechavencimientobonos() {
        return fechavencimientobonos;
    }

    public void setFechavencimientobonos(Date fechavencimientobonos) {
        this.fechavencimientobonos = fechavencimientobonos;
    }

    
}
