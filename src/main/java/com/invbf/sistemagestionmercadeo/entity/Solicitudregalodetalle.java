/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "solicitudregalodetalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solicitudregalodetalle.findAll", query = "SELECT s FROM Solicitudregalodetalle s"),
    @NamedQuery(name = "Solicitudregalodetalle.findBySolicitud", query = "SELECT s FROM Solicitudregalodetalle s WHERE s.solicitudregalodetallePK.solicitud = :solicitud"),
    @NamedQuery(name = "Solicitudregalodetalle.findByInventario", query = "SELECT s FROM Solicitudregalodetalle s WHERE s.solicitudregalodetallePK.inventario = :inventario"),
    @NamedQuery(name = "Solicitudregalodetalle.findByCliente", query = "SELECT s FROM Solicitudregalodetalle s WHERE s.solicitudregalodetallePK.cliente = :cliente"),
    @NamedQuery(name = "Solicitudregalodetalle.findByCantidad", query = "SELECT s FROM Solicitudregalodetalle s WHERE s.cantidad = :cantidad"),
    @NamedQuery(name = "Solicitudregalodetalle.findByCantidadArpobada", query = "SELECT s FROM Solicitudregalodetalle s WHERE s.cantidadArpobada = :cantidadArpobada"),
    @NamedQuery(name = "Solicitudregalodetalle.findByEstado", query = "SELECT s FROM Solicitudregalodetalle s WHERE s.estado = :estado"),
    @NamedQuery(name = "Solicitudregalodetalle.findBySala", query = "SELECT s FROM Solicitudregalodetalle s WHERE s.estado = 'EN SALA' AND s.solicitudregalos.sala.idCasino = :sala AND (s.cliente.nombres like :buscar OR s.cliente.apellidos like :buscar OR s.cliente.identificacion like :buscar)")})
public class Solicitudregalodetalle implements Serializable {
    @Column(name = "fechaEntrega")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntrega;
    @JoinColumn(name = "entregador", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario entregador;
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SolicitudregalodetallePK solicitudregalodetallePK;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "cantidadArpobada")
    private Integer cantidadArpobada;
    @Size(max = 45)
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "cliente", referencedColumnName = "idCliente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cliente cliente;
    @JoinColumn(name = "inventario", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Regalosinventario regalosinventario;
    @JoinColumn(name = "solicitud", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Solicitudregalos solicitudregalos;

    public Solicitudregalodetalle() {
    }

    public Solicitudregalodetalle(SolicitudregalodetallePK solicitudregalodetallePK) {
        this.solicitudregalodetallePK = solicitudregalodetallePK;
    }

    public Solicitudregalodetalle(int solicitud, int inventario, int cliente) {
        this.solicitudregalodetallePK = new SolicitudregalodetallePK(solicitud, inventario, cliente);
    }

    public SolicitudregalodetallePK getSolicitudregalodetallePK() {
        return solicitudregalodetallePK;
    }

    public void setSolicitudregalodetallePK(SolicitudregalodetallePK solicitudregalodetallePK) {
        this.solicitudregalodetallePK = solicitudregalodetallePK;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCantidadArpobada() {
        return cantidadArpobada;
    }

    public void setCantidadArpobada(Integer cantidadArpobada) {
        this.cantidadArpobada = cantidadArpobada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente getClientes() {
        return cliente;
    }

    public void setClientes(Cliente cliente) {
        this.cliente = cliente;
    }

    public Regalosinventario getRegalosinventario() {
        return regalosinventario;
    }

    public void setRegalosinventario(Regalosinventario regalosinventario) {
        this.regalosinventario = regalosinventario;
    }

    public Solicitudregalos getSolicitudregalos() {
        return solicitudregalos;
    }

    public void setSolicitudregalos(Solicitudregalos solicitudregalos) {
        this.solicitudregalos = solicitudregalos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (solicitudregalodetallePK != null ? solicitudregalodetallePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Solicitudregalodetalle)) {
            return false;
        }
        Solicitudregalodetalle other = (Solicitudregalodetalle) object;
        if ((this.solicitudregalodetallePK == null && other.solicitudregalodetallePK != null) || (this.solicitudregalodetallePK != null && !this.solicitudregalodetallePK.equals(other.solicitudregalodetallePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Solicitudregalodetalle[ solicitudregalodetallePK=" + solicitudregalodetallePK + " ]";
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Usuario getEntregador() {
        return entregador;
    }

    public void setEntregador(Usuario entregador) {
        this.entregador = entregador;
    }
    
}
