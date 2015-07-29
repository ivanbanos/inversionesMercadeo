/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ivan
 */
public class InventarioBarajasDTO implements Serializable{
    Integer id;
    String nombre;
    private List<BarajasCantidad> inventario;

    public InventarioBarajasDTO() {
        inventario = new ArrayList<BarajasCantidad>();
    }

    public InventarioBarajasDTO(Integer id) {
        this.id = id;
    }

    public List<BarajasCantidad> getInventario() {
        return inventario;
    }

    public void setInventario(List<BarajasCantidad> inventario) {
        this.inventario = inventario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final InventarioBarajasDTO other = (InventarioBarajasDTO) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}
