/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Clienteblanco;
import com.invbf.sistemagestionmercadeo.entity.Lotebono;
import java.util.List;

/**
 *
 * @author ivan
 */
public class ClienteBlancoLotes {

    private Clienteblanco clienteblanco;
    private Lotebono[] lotesBono;
    private int[] cantidad;

    public ClienteBlancoLotes(Clienteblanco clienteblanco, List<loteBonoSolicitud> lbs) {
        this.clienteblanco = clienteblanco;
        lotesBono = new Lotebono[lbs.size()];
        cantidad = new int[lbs.size()];
        for (int i = 0; i < lbs.size(); i++) {
            lotesBono[i]= lbs.get(i).getLotesBonosid();
            cantidad[i]=0;
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

    public Lotebono[] getLotesBono() {
        return lotesBono;
    }

    public void setLotesBono(Lotebono[] lotesBono) {
        this.lotesBono = lotesBono;
    }

    public int[] getCantidad() {
        return cantidad;
    }

    public void setCantidad(int[] cantidad) {
        this.cantidad = cantidad;
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


}
