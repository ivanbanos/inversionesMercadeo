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
public class RegalosCantidadDTO implements Serializable {
    private Integer id;
    private RegaloDTO regalo;
    private Integer cantidad;
    private Integer cantidadR;
    private Integer cantidadAprobada;
    private Integer min;
    private Integer max;
    private ClienteDTO cliente;
    private String estado;

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public RegalosCantidadDTO() {
    }

    public RegalosCantidadDTO(Integer id) {
        this.id = id;
    }

    public RegalosCantidadDTO(Integer id, RegaloDTO regalo, Integer cantidad, Integer cantidadR, Integer min, Integer max) {
        this.regalo = regalo;
        this.cantidad = cantidad;
        this.id = id;
        this.cantidadR = cantidadR;
        this.min = min;
        this.max = max;
    }
    public RegalosCantidadDTO(Integer id, RegaloDTO regalo) {
        this.regalo = regalo;
        this.cantidad = 0;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidadR() {
        return cantidadR;
    }

    public void setCantidadR(Integer cantidadR) {
        this.cantidadR = cantidadR;
    }

    public RegaloDTO getRegalo() {
        return regalo;
    }

    public void setRegalo(RegaloDTO regalo) {
        this.regalo = regalo;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getCantidadAprobada() {
        return cantidadAprobada;
    }

    public void setCantidadAprobada(Integer cantidadAprobada) {
        this.cantidadAprobada = cantidadAprobada;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final RegalosCantidadDTO other = (RegalosCantidadDTO) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
