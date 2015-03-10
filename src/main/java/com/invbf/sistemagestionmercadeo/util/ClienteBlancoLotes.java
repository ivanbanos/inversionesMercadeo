/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Clienteblanco;
import com.invbf.sistemagestionmercadeo.entity.Lotebono;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ivan
 */
public class ClienteBlancoLotes implements Serializable{

    private Clienteblanco clienteblanco;
    private List<LotebonoCanti> lotesBono;
    private Float total;

    public ClienteBlancoLotes(Clienteblanco clienteblanco, List<loteBonoSolicitud> lbs) {
        this.clienteblanco = clienteblanco;
        lotesBono = new ArrayList<LotebonoCanti>();
        for (int i = 0; i < lbs.size(); i++) {
            lotesBono.add(new LotebonoCanti(lbs.get(i).getLotesBonosid(),0));
        }

    }

    public ClienteBlancoLotes(Clienteblanco clienteblanco) {
        this.clienteblanco = clienteblanco;
    }
    

    public Clienteblanco getClienteblanco() {
        return clienteblanco;
    }

    public void setClienteblanco(Clienteblanco clienteblanco) {
        this.clienteblanco = clienteblanco;
    }

    public List<LotebonoCanti> getLotesBono() {
        return lotesBono;
    }

    public void setLotesBono(List<LotebonoCanti> lotesBono) {
        this.lotesBono = lotesBono;
    }


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.clienteblanco != null ? this.clienteblanco.hashCode() : 0);
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
        final ClienteBlancoLotes other = (ClienteBlancoLotes) obj;
        if (this.clienteblanco != other.clienteblanco && (this.clienteblanco == null || !this.clienteblanco.equals(other.clienteblanco))) {
            return false;
        }
        return true;
    }

    public Float getTotal() {
        total = 0f;
        for (LotebonoCanti lotesBono1 : lotesBono) {
            total += lotesBono1.getCantidad()*lotesBono1.getLotesBono().getDenominacion().getValor();
        }
        return total;
    }
    public Float getTotalPre() {
        total = 0f;
        for (LotebonoCanti lotesBono1 : lotesBono) {
            total += lotesBono1.getCantPre()*lotesBono1.getLotesBono().getDenominacion().getValor();
        }
        return total;
    }
    public Float getTotalA() {
        total = 0f;
        for (LotebonoCanti lotesBono1 : lotesBono) {
            total += lotesBono1.getCantA()*lotesBono1.getLotesBono().getDenominacion().getValor();
        }
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }


}
