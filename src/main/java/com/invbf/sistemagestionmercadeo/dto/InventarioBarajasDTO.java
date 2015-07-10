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
    private List<BarajasCantidad> inventario;

    public InventarioBarajasDTO() {
        inventario = new ArrayList<BarajasCantidad>();
    }

    public List<BarajasCantidad> getInventario() {
        return inventario;
    }

    public void setInventario(List<BarajasCantidad> inventario) {
        this.inventario = inventario;
    }
    
}
