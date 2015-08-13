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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "trasladobarajas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trasladobarajas.findAll", query = "SELECT t FROM Trasladobarajas t"),
    @NamedQuery(name = "Trasladobarajas.findById", query = "SELECT t FROM Trasladobarajas t WHERE t.id = :id"),
    @NamedQuery(name = "Trasladobarajas.findByEstado", query = "SELECT t FROM Trasladobarajas t WHERE t.estado = :estado"),
    @NamedQuery(name = "Trasladobarajas.findByFechacreada", query = "SELECT t FROM Trasladobarajas t WHERE t.fechacreada = :fechacreada"),
    @NamedQuery(name = "Trasladobarajas.findByFechaenviada", query = "SELECT t FROM Trasladobarajas t WHERE t.fechaenviada = :fechaenviada"),
    @NamedQuery(name = "Trasladobarajas.findByFecharecibida", query = "SELECT t FROM Trasladobarajas t WHERE t.salareceptora.idCasino = :fecharecibida")})
public class Trasladobarajas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "estado")
    private String estado;
    @Column(name = "fechacreada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreada;
    @Column(name = "fechaenviada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaenviada;
    @Column(name = "fecharecibida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecharecibida;
    @JoinColumn(name = "salaenviadora", referencedColumnName = "idCasino")
    @ManyToOne
    private Casino salaenviadora;
    @JoinColumn(name = "salareceptora", referencedColumnName = "idCasino")
    @ManyToOne
    private Casino salareceptora;
    @JoinColumn(name = "creador", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario creador;
    @JoinColumn(name = "enviador", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario enviador;
    @JoinColumn(name = "recibidor", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario recibidor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trasladobarajas")
    private List<Trasladobarajadetalle> trasladobarajadetalleList;

    public Trasladobarajas() {
    }

    public Trasladobarajas(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechacreada() {
        return fechacreada;
    }

    public void setFechacreada(Date fechacreada) {
        this.fechacreada = fechacreada;
    }

    public Date getFechaenviada() {
        return fechaenviada;
    }

    public void setFechaenviada(Date fechaenviada) {
        this.fechaenviada = fechaenviada;
    }

    public Date getFecharecibida() {
        return fecharecibida;
    }

    public void setFecharecibida(Date fecharecibida) {
        this.fecharecibida = fecharecibida;
    }

    public Casino getSalaenviadora() {
        return salaenviadora;
    }

    public void setSalaenviadora(Casino salaenviadora) {
        this.salaenviadora = salaenviadora;
    }

    public Casino getSalareceptora() {
        return salareceptora;
    }

    public void setSalareceptora(Casino salareceptora) {
        this.salareceptora = salareceptora;
    }

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public Usuario getEnviador() {
        return enviador;
    }

    public void setEnviador(Usuario enviador) {
        this.enviador = enviador;
    }

    public Usuario getRecibidor() {
        return recibidor;
    }

    public void setRecibidor(Usuario recibidor) {
        this.recibidor = recibidor;
    }

    @XmlTransient
    public List<Trasladobarajadetalle> getTrasladobarajadetalleList() {
        return trasladobarajadetalleList;
    }

    public void setTrasladobarajadetalleList(List<Trasladobarajadetalle> trasladobarajadetalleList) {
        this.trasladobarajadetalleList = trasladobarajadetalleList;
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
        if (!(object instanceof Trasladobarajas)) {
            return false;
        }
        Trasladobarajas other = (Trasladobarajas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Trasladobarajas[ id=" + id + " ]";
    }
    
}
