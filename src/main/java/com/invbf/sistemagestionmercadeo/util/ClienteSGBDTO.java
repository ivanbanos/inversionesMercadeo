/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Area;
import com.invbf.sistemagestionmercadeo.entity.Cliente;


/**
 *
 * @author ivan
 */
public class ClienteSGBDTO {
    private Float valorTotal;
    private Cliente clientessgb;
    private Area areaid;

    public ClienteSGBDTO() {
    }

    public ClienteSGBDTO(Float valorTotal, Cliente clientessgb, Area areaid) {
        this.valorTotal = valorTotal;
        this.clientessgb = clientessgb;
        this.areaid = areaid;
    }

    public Float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Cliente getClientessgb() {
        return clientessgb;
    }

    public void setClientessgb(Cliente clientessgb) {
        this.clientessgb = clientessgb;
    }

    public Area getAreaid() {
        return areaid;
    }

    public void setAreaid(Area areaid) {
        this.areaid = areaid;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.clientessgb != null ? this.clientessgb.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ClienteSGBDTO other = (ClienteSGBDTO) obj;
        if (this.clientessgb != other.clientessgb && (this.clientessgb == null || !this.clientessgb.equals(other.clientessgb))) {
            return false;
        }
        return true;
    }    
    
}
