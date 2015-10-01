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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "regalos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Regalos.findAll", query = "SELECT r FROM Regalos r"),
    @NamedQuery(name = "Regalos.findById", query = "SELECT r FROM Regalos r WHERE r.id = :id"),
    @NamedQuery(name = "Regalos.findByNombre", query = "SELECT r FROM Regalos r WHERE r.nombre = :nombre"),
    @NamedQuery(name = "Regalos.findByGenero", query = "SELECT r FROM Regalos r WHERE r.genero = :genero"),
    @NamedQuery(name = "Regalos.findByDescripcion", query = "SELECT r FROM Regalos r WHERE r.descripcion = :descripcion")})
public class Regalos implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "genero")
    private String genero;
    @Size(max = 500)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 500)
    @Column(name = "foto")
    private String foto;
    @JoinColumn(name = "categoria", referencedColumnName = "idCategorias")
    @ManyToOne(optional = false)
    private Categoria categoria;
    @OneToMany(mappedBy = "regalo",cascade = CascadeType.REMOVE)
    private List<Regalosinventario> regalosinventarioList;

    public Regalos() {
    }

    public Regalos(Integer id) {
        this.id = id;
    }

    public Regalos(Integer id, String nombre, String genero) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @XmlTransient
    public List<Regalosinventario> getRegalosinventarioList() {
        return regalosinventarioList;
    }

    public void setRegalosinventarioList(List<Regalosinventario> regalosinventarioList) {
        this.regalosinventarioList = regalosinventarioList;
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
        if (!(object instanceof Regalos)) {
            return false;
        }
        Regalos other = (Regalos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Regalos[ id=" + id + " ]";
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
    
}
