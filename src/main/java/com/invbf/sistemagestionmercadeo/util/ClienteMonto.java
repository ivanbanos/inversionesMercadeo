/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Bono;
import com.invbf.sistemagestionmercadeo.entity.Denominacion;
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
    private Float normal;
    private Float preaprobado;
    private Float aprobado;
    private List<DenoinacionCant> denominacionCant;
    private Integer id;

    public ClienteMonto() {
    }

    public ClienteMonto(Integer id) {
        this.id = id;
    }

    public ClienteMonto(Integer id, String nombre, Float monto, List<Lotebono> lotes, Integer formaentrega, Float normal, Float preaprobado, Float aprobado) {
        this.nombre = nombre;
        this.monto = monto;
        this.normal = normal;
        this.preaprobado = preaprobado;
        this.aprobado = aprobado;
        this.id = id;
        if (formaentrega == 1) {
            denominacionCant = MatematicaAplicada.getBonosAsignadosDEnominacinesGrandes(lotes, monto);
        } else {
            denominacionCant = MatematicaAplicada.getBonosAsignadosDEnominacinesGrandes(lotes, monto);
        }
    }

    public ClienteMonto(Integer id, String nombre) {
        this.nombre = nombre;
        this.id = id;
        denominacionCant = new ArrayList<DenoinacionCant>();
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
            cantidad += d.getCantidad() * d.getDenomiancion().getDenominacion().getValor();
        }
        return monto == cantidad;
    }

    public Float getNormal() {
        return normal;
    }

    public void setNormal(Float normal) {
        this.normal = normal;
    }

    public Float getPreaprobado() {
        return preaprobado;
    }

    public void setPreaprobado(Float preaprobado) {
        this.preaprobado = preaprobado;
    }

    public Float getAprobado() {
        return aprobado;
    }

    public void setAprobado(Float aprobado) {
        this.aprobado = aprobado;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final ClienteMonto other = (ClienteMonto) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public void restarBono(Denominacion denominacion) {
        for (DenoinacionCant denomcant : denominacionCant) {
            if (denomcant.getDenomiancion().getDenominacion().equals(denominacion)) {
                denomcant.resUnoCantidad();
            }
        }
    }
    public void sumarBono(Denominacion denominacion) {
        for (DenoinacionCant denomcant : denominacionCant) {
            if (denomcant.getDenomiancion().getDenominacion().equals(denominacion)) {
                denomcant.sumUnoCantidad();
            }
        }
    }

    public void setNuevoMonto() {
        monto = 0f;
        for (DenoinacionCant denominacionCant1 : denominacionCant) {
            monto += denominacionCant1.getCantidad() * denominacionCant1.getDenomiancion().getDenominacion().getValor();
        }
    }

    public boolean haveLeftDenomination(Integer idDenominacion) {
        for (DenoinacionCant denomcant : denominacionCant) {
            if (denomcant.getDenomiancion().getDenominacion().equals(new Denominacion(idDenominacion))) {
                if (denomcant.getCantidad() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

}
