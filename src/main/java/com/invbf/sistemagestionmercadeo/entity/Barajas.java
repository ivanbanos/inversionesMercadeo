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
@Table(name = "barajas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Barajas.findAll", query = "SELECT b FROM Barajas b"),
    @NamedQuery(name = "Barajas.findById", query = "SELECT b FROM Barajas b WHERE b.id = :id"),
    @NamedQuery(name = "Barajas.findByColor", query = "SELECT b FROM Barajas b WHERE b.color = :color"),
    @NamedQuery(name = "Barajas.findByMarca", query = "SELECT b FROM Barajas b WHERE b.marca = :marca"),
    @NamedQuery(name = "Barajas.findByValorpromedio", query = "SELECT b FROM Barajas b WHERE b.valorpromedio = :valorpromedio")})
public class Barajas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "color")
    private String color;
    @Basic(optional = false)
    @Size(min = 1, max = 100)
    @Column(name = "marca")
    private String marca;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorpromedio")
    private Float valorpromedio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "baraja")
    private List<Inventarobarajas> inventarobarajasList;
    @JoinColumn(name = "material", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Materialesbarajas material;

    public Barajas() {
    }

    public Barajas(Integer id) {
        this.id = id;
    }

    public Barajas(Integer id, String color, String marca) {
        this.id = id;
        this.color = color;
        this.marca = marca;
    }

    public Barajas(Integer id, String color, String marca, Float valorpromedio, Materialesbarajas material) {
        this.id = id;
        this.color = color;
        this.marca = marca;
        this.valorpromedio = valorpromedio;
        this.material = material;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Float getValorpromedio() {
        return valorpromedio;
    }

    public void setValorpromedio(Float valorpromedio) {
        this.valorpromedio = valorpromedio;
    }

    @XmlTransient
    public List<Inventarobarajas> getInventarobarajasList() {
        return inventarobarajasList;
    }

    public void setInventarobarajasList(List<Inventarobarajas> inventarobarajasList) {
        this.inventarobarajasList = inventarobarajasList;
    }

    public Materialesbarajas getMaterial() {
        return material;
    }

    public void setMaterial(Materialesbarajas material) {
        this.material = material;
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
        if (!(object instanceof Barajas)) {
            return false;
        }
        Barajas other = (Barajas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Barajas[ id=" + id + " ]";
    }
    
}
