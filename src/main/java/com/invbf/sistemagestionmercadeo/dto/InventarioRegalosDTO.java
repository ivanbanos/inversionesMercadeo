/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ivan
 */
public class InventarioRegalosDTO {
    private List<RegalosCantidadDTO> inventario;

    public InventarioRegalosDTO() {
        inventario = new ArrayList<RegalosCantidadDTO>();
    }

    public List<RegalosCantidadDTO> getInventario() {
        return inventario;
    }

    public void setInventario(List<RegalosCantidadDTO> inventario) {
        this.inventario = inventario;
    }
    
}
