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
@Table(name = "ListasClientesTareas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Listasclientestareas.findAll", query = "SELECT l FROM Listasclientestareas l"),
    @NamedQuery(name = "Listasclientestareas.findByIdTarea", query = "SELECT l FROM Listasclientestareas l WHERE l.listasclientestareasPK.idTarea = :idTarea"),
    @NamedQuery(name = "Listasclientestareas.findByIdCliente", query = "SELECT l FROM Listasclientestareas l WHERE l.listasclientestareasPK.idCliente = :idCliente"),
    @NamedQuery(name = "Listasclientestareas.findByObservaciones", query = "SELECT l FROM Listasclientestareas l WHERE l.observaciones = :observaciones"),
    @NamedQuery(name = "Listasclientestareas.findByFechaAtencion", query = "SELECT l FROM Listasclientestareas l WHERE l.fechaAtencion = :fechaAtencion"),
    @NamedQuery(name = "Listasclientestareas.findByIdTareaInicial", query = "SELECT l FROM Listasclientestareas l WHERE l.listasclientestareasPK.idTarea = :idTarea AND l.idAccion.nombre = 'INICIAL' ORDER BY l.count ASC"),
    @NamedQuery(name = "Listasclientestareas.findByCount", query = "SELECT l FROM Listasclientestareas l WHERE l.count = :count")})
public class Listasclientestareas implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ListasclientestareasPK listasclientestareasPK;
    @Size(max = 500)
    @Column(name = "Observaciones")
    private String observaciones;
    @Column(name = "fechaAtencion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAtencion;
    @Column(name = "count")
    private Integer count;
    @JoinColumn(name = "Usuario", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario usuario;
    @JoinColumn(name = "idAccion", referencedColumnName = "idAccion")
    @ManyToOne(optional = false)
    private Accion idAccion;
    @JoinColumn(name = "idTarea", referencedColumnName = "idTarea", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Tarea tarea;
    @JoinColumn(name = "idCliente", referencedColumnName = "idCliente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cliente cliente;

    public Listasclientestareas() {
    }

    public Listasclientestareas(ListasclientestareasPK listasclientestareasPK) {
        this.listasclientestareasPK = listasclientestareasPK;
    }

    public Listasclientestareas(int idTarea, int idCliente) {
        this.listasclientestareasPK = new ListasclientestareasPK(idTarea, idCliente);
    }

    public ListasclientestareasPK getListasclientestareasPK() {
        return listasclientestareasPK;
    }

    public void setListasclientestareasPK(ListasclientestareasPK listasclientestareasPK) {
        this.listasclientestareasPK = listasclientestareasPK;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(Date fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Accion getIdAccion() {
        return idAccion;
    }

    public void setIdAccion(Accion idAccion) {
        this.idAccion = idAccion;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (listasclientestareasPK != null ? listasclientestareasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Listasclientestareas)) {
            return false;
        }
        Listasclientestareas other = (Listasclientestareas) object;
        if ((this.listasclientestareasPK == null && other.listasclientestareasPK != null) || (this.listasclientestareasPK != null && !this.listasclientestareasPK.equals(other.listasclientestareasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Listasclientestareas[ listasclientestareasPK=" + listasclientestareasPK + " ]";
    }
    
}
