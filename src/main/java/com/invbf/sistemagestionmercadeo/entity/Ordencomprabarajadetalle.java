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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "ordencomprabarajadetalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ordencomprabarajadetalle.findAll", query = "SELECT o FROM Ordencomprabarajadetalle o"),
    @NamedQuery(name = "Ordencomprabarajadetalle.findByOrden", query = "SELECT o FROM Ordencomprabarajadetalle o WHERE o.ordencomprabarajadetallePK.orden = :orden"),
    @NamedQuery(name = "Ordencomprabarajadetalle.findByInventario", query = "SELECT o FROM Ordencomprabarajadetalle o WHERE o.ordencomprabarajadetallePK.inventario = :inventario"),
    @NamedQuery(name = "Ordencomprabarajadetalle.findByCantidad", query = "SELECT o FROM Ordencomprabarajadetalle o WHERE o.cantidad = :cantidad"),
    @NamedQuery(name = "Ordencomprabarajadetalle.findByCantidadAprobada", query = "SELECT o FROM Ordencomprabarajadetalle o WHERE o.cantidadAprobada = :cantidadAprobada"),
    @NamedQuery(name = "Ordencomprabarajadetalle.getByOrdenOrderByMaterial", query = "SELECT o FROM Ordencomprabarajadetalle o WHERE o.ordencomprabaraja.id = :orden ORDER BY o.inventarobarajas.baraja.material")})
public class Ordencomprabarajadetalle implements Serializable {
    @Column(name = "fecharecibida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecharecibida;
    @JoinColumn(name = "recibidor", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario recibidor;
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OrdencomprabarajadetallePK ordencomprabarajadetallePK;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "cantidadAprobada")
    private Integer cantidadAprobada;
    @Column(name = "cantidadRecibida")
    private Integer cantidadRecibida;
    @JoinColumn(name = "inventario", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Inventarobarajas inventarobarajas;
    @JoinColumn(name = "orden", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Ordencomprabaraja ordencomprabaraja;

    public Ordencomprabarajadetalle() {
    }

    public Ordencomprabarajadetalle(OrdencomprabarajadetallePK ordencomprabarajadetallePK) {
        this.ordencomprabarajadetallePK = ordencomprabarajadetallePK;
    }

    public Ordencomprabarajadetalle(int orden, int inventario) {
        this.ordencomprabarajadetallePK = new OrdencomprabarajadetallePK(orden, inventario);
    }

    public OrdencomprabarajadetallePK getOrdencomprabarajadetallePK() {
        return ordencomprabarajadetallePK;
    }

    public void setOrdencomprabarajadetallePK(OrdencomprabarajadetallePK ordencomprabarajadetallePK) {
        this.ordencomprabarajadetallePK = ordencomprabarajadetallePK;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCantidadAprobada() {
        return cantidadAprobada;
    }

    public void setCantidadAprobada(Integer cantidadAprobada) {
        this.cantidadAprobada = cantidadAprobada;
    }

    public Inventarobarajas getInventarobarajas() {
        return inventarobarajas;
    }

    public void setInventarobarajas(Inventarobarajas inventarobarajas) {
        this.inventarobarajas = inventarobarajas;
    }

    public Ordencomprabaraja getOrdencomprabaraja() {
        return ordencomprabaraja;
    }

    public void setOrdencomprabaraja(Ordencomprabaraja ordencomprabaraja) {
        this.ordencomprabaraja = ordencomprabaraja;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ordencomprabarajadetallePK != null ? ordencomprabarajadetallePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ordencomprabarajadetalle)) {
            return false;
        }
        Ordencomprabarajadetalle other = (Ordencomprabarajadetalle) object;
        if ((this.ordencomprabarajadetallePK == null && other.ordencomprabarajadetallePK != null) || (this.ordencomprabarajadetallePK != null && !this.ordencomprabarajadetallePK.equals(other.ordencomprabarajadetallePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.controladores.Ordencomprabarajadetalle[ ordencomprabarajadetallePK=" + ordencomprabarajadetallePK + " ]";
    }

    public Date getFecharecibida() {
        return fecharecibida;
    }

    public void setFecharecibida(Date fecharecibida) {
        this.fecharecibida = fecharecibida;
    }

    public Usuario getRecibidor() {
        return recibidor;
    }

    public void setRecibidor(Usuario recibidor) {
        this.recibidor = recibidor;
    }

    public Integer getCantidadRecibida() {
        return cantidadRecibida;
    }

    public void setCantidadRecibida(Integer cantidadRecibida) {
        this.cantidadRecibida = cantidadRecibida;
    }
    
}
