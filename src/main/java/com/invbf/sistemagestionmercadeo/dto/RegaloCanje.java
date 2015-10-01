/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dto;

import com.invbf.sistemagestionmercadeo.util.ClienteDTO;
import java.io.Serializable;

/**
 *
 * @author ivan
 */
public class RegaloCanje implements Serializable{
    RegaloDTO regalo;
    ClienteDTO cliente;
    int solicitud;

    public RegaloDTO getRegalo() {
        return regalo;
    }

    public void setRegalo(RegaloDTO regalo) {
        this.regalo = regalo;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public int getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(int solicitud) {
        this.solicitud = solicitud;
    }

    public RegaloCanje(RegaloDTO regalo, ClienteDTO cliente, int solicitud) {
        this.regalo = regalo;
        this.cliente = cliente;
        this.solicitud = solicitud;
    }


    public RegaloCanje() {
        regalo = new RegaloDTO();
        cliente = new ClienteDTO();
        
    }
    
}
