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
@Table(name = "Permisos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Permiso.findAll", query = "SELECT p FROM Permiso p"),
    @NamedQuery(name = "Permiso.findByIdAcciones", query = "SELECT p FROM Permiso p WHERE p.idAcciones = :idAcciones"),
    @NamedQuery(name = "Permiso.findByTipo", query = "SELECT p FROM Permiso p WHERE p.tipo = :tipo"),
    @NamedQuery(name = "Permiso.findById", query = "SELECT p FROM Permiso p WHERE p.id = :id"),
    @NamedQuery(name = "Permiso.findByTabla", query = "SELECT p FROM Permiso p WHERE p.tabla = :tabla"),
    @NamedQuery(name = "Permiso.findByCampo", query = "SELECT p FROM Permiso p WHERE p.campo = :campo"),
    @NamedQuery(name = "Permiso.findByNuevoValor", query = "SELECT p FROM Permiso p WHERE p.nuevoValor = :nuevoValor"),
    @NamedQuery(name = "Permiso.findByDetalleNuevoValor", query = "SELECT p FROM Permiso p WHERE p.detalleNuevoValor = :detalleNuevoValor"),
    @NamedQuery(name = "Permiso.findByValorActual", query = "SELECT p FROM Permiso p WHERE p.valorActual = :valorActual"),
    @NamedQuery(name = "Permiso.findByDetalleValorActual", query = "SELECT p FROM Permiso p WHERE p.detalleValorActual = :detalleValorActual"),
    @NamedQuery(name = "Permiso.findByObservaciones", query = "SELECT p FROM Permiso p WHERE p.observaciones = :observaciones")})
public class Permiso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAcciones")
    private Integer idAcciones;
    @Size(max = 45)
    @Column(name = "tipo")
    private String tipo;
    @Size(max = 45)
    @Column(name = "id")
    private String id;
    @Size(max = 45)
    @Column(name = "tabla")
    private String tabla;
    @Size(max = 45)
    @Column(name = "campo")
    private String campo;
    @Size(max = 45)
    @Column(name = "nuevoValor")
    private String nuevoValor;
    @Size(max = 45)
    @Column(name = "detalleNuevoValor")
    private String detalleNuevoValor;
    @Size(max = 45)
    @Column(name = "valorActual")
    private String valorActual;
    @Size(max = 45)
    @Column(name = "detalleValorActual")
    private String detalleValorActual;
    @Size(max = 400)
    @Column(name = "observaciones")
    private String observaciones;

    public Permiso() {
    }

    public Permiso(String tipo, String id, String tabla, String campo, String nuevoValor, String detalleNuevoValor, String valorActual, String detalleValorActual, String observaciones) {
        this.tipo = tipo;
        this.id = id;
        this.tabla = tabla;
        this.campo = campo;
        this.nuevoValor = nuevoValor;
        this.detalleNuevoValor = detalleNuevoValor;
        this.valorActual = valorActual;
        this.detalleValorActual = detalleValorActual;
        this.observaciones = observaciones;
    }

    public Permiso(Integer idAcciones) {
        this.idAcciones = idAcciones;
    }

    public Integer getIdAcciones() {
        return idAcciones;
    }

    public void setIdAcciones(Integer idAcciones) {
        this.idAcciones = idAcciones;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getNuevoValor() {
        return nuevoValor;
    }

    public void setNuevoValor(String nuevoValor) {
        this.nuevoValor = nuevoValor;
    }

    public String getDetalleNuevoValor() {
        return detalleNuevoValor;
    }

    public void setDetalleNuevoValor(String detalleNuevoValor) {
        this.detalleNuevoValor = detalleNuevoValor;
    }

    public String getValorActual() {
        return valorActual;
    }

    public void setValorActual(String valorActual) {
        this.valorActual = valorActual;
    }

    public String getDetalleValorActual() {
        return detalleValorActual;
    }

    public void setDetalleValorActual(String detalleValorActual) {
        this.detalleValorActual = detalleValorActual;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAcciones != null ? idAcciones.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Permiso)) {
            return false;
        }
        Permiso other = (Permiso) object;
        if ((this.idAcciones == null && other.idAcciones != null) || (this.idAcciones != null && !this.idAcciones.equals(other.idAcciones))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Permiso[ idAcciones=" + idAcciones + " ]";
    }
    
}
