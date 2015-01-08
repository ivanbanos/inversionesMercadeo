/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "Formularios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Formulario.findAll", query = "SELECT f FROM Formulario f"),
    @NamedQuery(name = "Formulario.findByIdFormulario", query = "SELECT f FROM Formulario f WHERE f.idFormulario = :idFormulario"),
    @NamedQuery(name = "Formulario.findByTabla", query = "SELECT f FROM Formulario f WHERE f.tabla = :tabla"),
    @NamedQuery(name = "Formulario.findByAccion", query = "SELECT f FROM Formulario f WHERE f.accion = :accion"),
    @NamedQuery(name = "Formularios.findByAccionAndTabla", query = "SELECT f FROM Formulario f WHERE f.accion = :accion AND f.tabla = :tabla")})
public class Formulario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFormulario")
    private Integer idFormulario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tabla")
    private String tabla;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "accion")
    private String accion;
    @JoinTable(name = "PerfilesFormularios", joinColumns = {
        @JoinColumn(name = "idFormulario", referencedColumnName = "idFormulario")}, inverseJoinColumns = {
        @JoinColumn(name = "idPerfil", referencedColumnName = "idPerfil")})
    @ManyToMany
    private List<Perfil> perfilList;
    @OneToMany(mappedBy = "idFormulario")
    private List<Log> logList;

    public Formulario() {
    }

    public Formulario(Integer idFormulario) {
        this.idFormulario = idFormulario;
    }

    public Formulario(Integer idFormulario, String tabla, String accion) {
        this.idFormulario = idFormulario;
        this.tabla = tabla;
        this.accion = accion;
    }

    public Integer getIdFormulario() {
        return idFormulario;
    }

    public void setIdFormulario(Integer idFormulario) {
        this.idFormulario = idFormulario;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    @XmlTransient
    public List<Perfil> getPerfilList() {
        return perfilList;
    }

    public void setPerfilList(List<Perfil> perfilList) {
        this.perfilList = perfilList;
    }

    @XmlTransient
    public List<Log> getLogList() {
        return logList;
    }

    public void setLogList(List<Log> logList) {
        this.logList = logList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFormulario != null ? idFormulario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Formulario)) {
            return false;
        }
        Formulario other = (Formulario) object;
        if ((this.idFormulario == null && other.idFormulario != null) || (this.idFormulario != null && !this.idFormulario.equals(other.idFormulario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Formulario[ idFormulario=" + idFormulario + " ]";
    }
    
    public boolean es(String tablaaccion) {
        return (tabla+accion).equals(tablaaccion);
    }
    
}
