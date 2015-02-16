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
import javax.persistence.FetchType;
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
@Table(name = "ClientesAtributos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clienteatributo.findAll", query = "SELECT c FROM Clienteatributo c"),
    @NamedQuery(name = "Clienteatributo.findByIdCliente", query = "SELECT c FROM Clienteatributo c WHERE c.clienteatributoPK.idCliente = :idCliente"),
    @NamedQuery(name = "Clienteatributo.findByIdAtributo", query = "SELECT c FROM Clienteatributo c WHERE c.clienteatributoPK.idAtributo = :idAtributo"),
    @NamedQuery(name = "Clienteatributo.findByValor", query = "SELECT c FROM Clienteatributo c WHERE c.valor = :valor"),
    @NamedQuery(name = "Clienteatributo.findByValorfecha", query = "SELECT c FROM Clienteatributo c WHERE c.valorfecha = :valorfecha")})
public class Clienteatributo implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ClienteatributoPK clienteatributoPK;
    @Size(max = 45)
    @Column(name = "valor")
    private String valor;
    @Column(name = "valorfecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date valorfecha;
    @JoinColumn(name = "idCliente", referencedColumnName = "idCliente", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cliente cliente;
    @JoinColumn(name = "idAtributo", referencedColumnName = "idAtributo", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Atributo atributo;

    public Clienteatributo() {
    }

    public Clienteatributo(ClienteatributoPK clienteatributoPK) {
        this.clienteatributoPK = clienteatributoPK;
    }

    public Clienteatributo(int idCliente, int idAtributo) {
        this.clienteatributoPK = new ClienteatributoPK(idCliente, idAtributo);
    }

    public ClienteatributoPK getClienteatributoPK() {
        return clienteatributoPK;
    }

    public void setClienteatributoPK(ClienteatributoPK clienteatributoPK) {
        this.clienteatributoPK = clienteatributoPK;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Date getValorfecha() {
        return valorfecha;
    }

    public void setValorfecha(Date valorfecha) {
        this.valorfecha = valorfecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Atributo getAtributo() {
        return atributo;
    }

    public void setAtributo(Atributo atributo) {
        this.atributo = atributo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clienteatributoPK != null ? clienteatributoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clienteatributo)) {
            return false;
        }
        Clienteatributo other = (Clienteatributo) object;
        if ((this.clienteatributoPK == null && other.clienteatributoPK != null) || (this.clienteatributoPK != null && !this.clienteatributoPK.equals(other.clienteatributoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Clienteatributo[ clienteatributoPK=" + clienteatributoPK + " ]";
    }
    
}
