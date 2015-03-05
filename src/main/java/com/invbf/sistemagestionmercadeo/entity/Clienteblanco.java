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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "ClienteBlanco")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clienteblanco.findAll", query = "SELECT c FROM Clienteblanco c"),
    @NamedQuery(name = "Clienteblanco.findById", query = "SELECT c FROM Clienteblanco c WHERE c.id = :id"),
    @NamedQuery(name = "Clienteblanco.findByCantidad", query = "SELECT c FROM Clienteblanco c WHERE c.cantidad = :cantidad"),
    @NamedQuery(name = "Clienteblanco.findByJustificacion", query = "SELECT c FROM Clienteblanco c WHERE c.justificacion = :justificacion"),
    @NamedQuery(name = "Clienteblanco.findByNombres", query = "SELECT c FROM Clienteblanco c WHERE c.nombres = :nombres"),
    @NamedQuery(name = "Clienteblanco.findByApellidos", query = "SELECT c FROM Clienteblanco c WHERE c.apellidos = :apellidos")})
public class Clienteblanco implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "cantPre")
    private Integer cantPre;
    @Column(name = "cantA")
    private Integer cantA;

    @Column(name = "justificacion")
    private String justificacion;
    @Column(name = "Nombres")
    private String nombres;
    @Column(name = "Apellidos")
    private String apellidos;
    @JoinColumns({
        @JoinColumn(name = "ControlSalidaBonos_has_LotesBonos_ControlSalidaBonos_id", referencedColumnName = "ControlSalidaBonos_id"),
        @JoinColumn(name = "ControlSalidaBonos_has_LotesBonos_LotesBonos_id", referencedColumnName = "LotesBonos_id")})
    @ManyToOne(optional = false)
    private ControlsalidabonosHasLotesbonos controlsalidabonosHasLotesbonos;
    
    @JoinColumn(name = "idCliente", referencedColumnName = "idCliente")
    @ManyToOne
    private Cliente idCliente;

    public Clienteblanco() {
    }

    public Clienteblanco(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public ControlsalidabonosHasLotesbonos getControlsalidabonosHasLotesbonos() {
        return controlsalidabonosHasLotesbonos;
    }

    public void setControlsalidabonosHasLotesbonos(ControlsalidabonosHasLotesbonos controlsalidabonosHasLotesbonos) {
        this.controlsalidabonosHasLotesbonos = controlsalidabonosHasLotesbonos;
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
        if (!(object instanceof Clienteblanco)) {
            return false;
        }
        Clienteblanco other = (Clienteblanco) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "otrasentities.Clienteblanco[ id=" + id + " ]";
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
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
    
}
