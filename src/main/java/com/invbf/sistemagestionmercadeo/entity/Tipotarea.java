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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "Tipostareas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipotarea.findAll", query = "SELECT t FROM Tipotarea t"),
    @NamedQuery(name = "Tipotarea.findByIdTipotarea", query = "SELECT t FROM Tipotarea t WHERE t.idTipotarea = :idTipotarea"),
    @NamedQuery(name = "Tipotarea.findByNombre", query = "SELECT t FROM Tipotarea t WHERE t.nombre = :nombre")})
public class Tipotarea implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTipotarea")
    private Integer idTipotarea;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @ManyToMany(mappedBy = "tipotareaList")
    private List<Accion> accionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipo")
    private List<Tarea> tareaList;

    public Tipotarea() {
    }

    public Tipotarea(Integer idTipotarea) {
        this.idTipotarea = idTipotarea;
    }

    public Integer getIdTipotarea() {
        return idTipotarea;
    }

    public void setIdTipotarea(Integer idTipotarea) {
        this.idTipotarea = idTipotarea;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Accion> getAccionList() {
        return accionList;
    }

    public void setAccionList(List<Accion> accionList) {
        this.accionList = accionList;
    }

    @XmlTransient
    public List<Tarea> getTareaList() {
        return tareaList;
    }

    public void setTareaList(List<Tarea> tareaList) {
        this.tareaList = tareaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipotarea != null ? idTipotarea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipotarea)) {
            return false;
        }
        Tipotarea other = (Tipotarea) object;
        if ((this.idTipotarea == null && other.idTipotarea != null) || (this.idTipotarea != null && !this.idTipotarea.equals(other.idTipotarea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Tipotarea[ idTipotarea=" + idTipotarea + " ]";
    }
    
}
