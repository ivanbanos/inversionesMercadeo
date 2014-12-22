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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "casinos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Casino.findAll", query = "SELECT c FROM Casino c"),
    @NamedQuery(name = "Casino.findByIdCasino", query = "SELECT c FROM Casino c WHERE c.idCasino = :idCasino"),
    @NamedQuery(name = "Casino.findByNombre", query = "SELECT c FROM Casino c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Casino.findByDireccion", query = "SELECT c FROM Casino c WHERE c.direccion = :direccion")})
public class Casino implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCasino")
    private Integer idCasino;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 45)
    @Column(name = "direccion")
    private String direccion;
    @OneToMany(mappedBy = "idCasino")
    private List<Solicitudentrega> solicitudentregaList;
    @OneToMany(mappedBy = "idCasino")
    private List<Evento> eventoList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "casino")
    private Casinodetalle casinodetalle;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "casino")
    private List<Bono> bonoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCasino")
    private List<Lotebono> lotebonoList;
    @OneToMany(mappedBy = "idCasinoPreferencial")
    private List<Cliente> clienteList;
    @OneToMany(mappedBy = "idCasino")
    private List<Usuario> usuarioList;

    public Casino() {
    }

    public Casino(Integer idCasino) {
        this.idCasino = idCasino;
    }

    public Casino(Integer idCasino, String nombre) {
        this.idCasino = idCasino;
        this.nombre = nombre;
    }

    public Integer getIdCasino() {
        return idCasino;
    }

    public void setIdCasino(Integer idCasino) {
        this.idCasino = idCasino;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @XmlTransient
    public List<Solicitudentrega> getSolicitudentregaList() {
        return solicitudentregaList;
    }

    public void setSolicitudentregaList(List<Solicitudentrega> solicitudentregaList) {
        this.solicitudentregaList = solicitudentregaList;
    }

    @XmlTransient
    public List<Evento> getEventoList() {
        return eventoList;
    }

    public void setEventoList(List<Evento> eventoList) {
        this.eventoList = eventoList;
    }

    public Casinodetalle getCasinodetalle() {
        return casinodetalle;
    }

    public void setCasinodetalle(Casinodetalle casinodetalle) {
        this.casinodetalle = casinodetalle;
    }

    @XmlTransient
    public List<Bono> getBonoList() {
        return bonoList;
    }

    public void setBonoList(List<Bono> bonoList) {
        this.bonoList = bonoList;
    }

    @XmlTransient
    public List<Lotebono> getLotebonoList() {
        return lotebonoList;
    }

    public void setLotebonoList(List<Lotebono> lotebonoList) {
        this.lotebonoList = lotebonoList;
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCasino != null ? idCasino.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Casino)) {
            return false;
        }
        Casino other = (Casino) object;
        if ((this.idCasino == null && other.idCasino != null) || (this.idCasino != null && !this.idCasino.equals(other.idCasino))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Casino[ idCasino=" + idCasino + " ]";
    }
    
}
