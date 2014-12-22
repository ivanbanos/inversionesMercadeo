/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Lotebono;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ivan
 */
public class ClienteMonto {

    private String nombre;
    private Float monto;
    private List<DenoinacionCant> denominacionCant;
    private Integer id;

    public ClienteMonto() {
    }

    public ClienteMonto(Integer id, String nombre, Float monto, List<Lotebono> lotes) {
        this.nombre = nombre;
        this.monto = monto;
        this.id = id;
        denominacionCant = new ArrayList<DenoinacionCant>();
        for (Lotebono lote : lotes) {
            denominacionCant.add(new DenoinacionCant(lote.getDenominacion(), 0));
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public List<DenoinacionCant> getDenominacionCant() {
        return denominacionCant;
    }

    public void setDenominacionCant(List<DenoinacionCant> denominacionCant) {
        this.denominacionCant = denominacionCant;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getIsOk() {
        float cantidad = 0;
        for (DenoinacionCant d : denominacionCant) {
            cantidad += d.getCantidad() * d.getDenomiancion().getValor();
        }
        return monto == cantidad;
    }
}
