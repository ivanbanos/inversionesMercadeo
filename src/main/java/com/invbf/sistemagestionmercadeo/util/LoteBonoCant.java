/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Lotebono;
import java.util.List;


/**
 *
 * @author ivan
 */
public class LoteBonoCant {
    private Lotebono lote;
    private long cantidad;
    private String desde;
    private String hasta;
    private List<String>nofisicos;

    public LoteBonoCant() {
    }

    public LoteBonoCant(Lotebono lote) {
        this.lote = lote;
        cantidad = 0;
    }

    public LoteBonoCant(Lotebono lote, long cantidad) {
        this.lote = lote;
        this.cantidad = cantidad;
    }

    public Lotebono getLote() {
        return lote;
    }

    public void setLote(Lotebono lote) {
        this.lote = lote;
    }

    public long getCantidad() {
        return cantidad;
    }

    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.lote != null ? this.lote.hashCode() : 0);
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
        final LoteBonoCant other = (LoteBonoCant) obj;
        if (this.lote != other.lote && (this.lote == null || !this.lote.equals(other.lote))) {
            return false;
        }
        return true;
    }

    public void sumCantidad(long cantidad) {
        this.cantidad += cantidad;
    }
    
    public float getTotal(){
        return cantidad * lote.getDenominacion().getValor();
    }

    public String getDesde() {
        return desde;
    }

    public void setDesde(String desde) {
        this.desde = desde;
    }

    public String getHasta() {
        return hasta;
    }

    public void setHasta(String hasta) {
        this.hasta = hasta;
    }

    public List<String> getNofisicos() {
        return nofisicos;
    }

    public void setNofisicos(List<String> nofisicos) {
        this.nofisicos = nofisicos;
    }
    
}
