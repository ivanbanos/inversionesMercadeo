/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dto;

import com.invbf.sistemagestionmercadeo.entity.Regalosinventario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ivan
 */
public class InventarioRegalosDTO implements Serializable {

    private List<RegalosCantidadDTO> inventario;

    public InventarioRegalosDTO() {
        inventario = new ArrayList<RegalosCantidadDTO>();
    }

    public InventarioRegalosDTO(List<Regalosinventario> listaInvenratioRegalos) {
        inventario = new ArrayList<RegalosCantidadDTO>();
        for (Regalosinventario item : listaInvenratioRegalos) {
            inventario.add(new RegalosCantidadDTO(item.getId(), new RegaloDTO(item.getRegalo()), item.getCantidad(), item.getCantidad(), item.getMin(), item.getMax()));
        }
    }

    public InventarioRegalosDTO(List<Regalosinventario> listaInvenratioRegalos, int action) {
        if (action == 1) {
            inventario = new ArrayList<RegalosCantidadDTO>();
            for (Regalosinventario item : listaInvenratioRegalos) {
                inventario.add(new RegalosCantidadDTO(item.getId(), new RegaloDTO(item.getRegalo()), item.getCantidad(), 0, 0, 0));
            }
        }
    }

    public List<RegalosCantidadDTO> getInventario() {
        return inventario;
    }

    public void setInventario(List<RegalosCantidadDTO> inventario) {
        this.inventario = inventario;
    }

}
