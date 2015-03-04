/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Area;
import com.invbf.sistemagestionmercadeo.entity.Cliente;
import java.io.Serializable;


/**
 *
 * @author ivan
 */
public class ClienteSGBDTO  implements Serializable{
    private Float valorTotal;
    private Cliente clientessgb;
    private Float bono;
    private Float ultimaSol;
    private Float penultimaSol;
    private Float trasultimaSol;
    private int forma;
    

    public ClienteSGBDTO() {
    }

    public ClienteSGBDTO(Float valorTotal, Cliente clientessgb, Area areaid) {
        this.valorTotal = valorTotal;
        this.clientessgb = clientessgb;
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

    public Float getBono() {
        return bono;
    }

    public void setBono(Float bono) {
        this.bono = bono;
    }

    public Float getUltimaSol() {
        return ultimaSol;
    }

    public void setUltimaSol(Float ultimaSol) {
        this.ultimaSol = ultimaSol;
    }

    public Float getPenultimaSol() {
        return penultimaSol;
    }

    public void setPenultimaSol(Float penultimaSol) {
        this.penultimaSol = penultimaSol;
    }

    public Float getTrasultimaSol() {
        return trasultimaSol;
    }

    public void setTrasultimaSol(Float trasultimaSol) {
        this.trasultimaSol = trasultimaSol;
    }

    public int getForma() {
        return forma;
    }

    public void setForma(int forma) {
        this.forma = forma;
    }
    
}
