/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dto;

import java.io.Serializable;

/**
 *
 * @author ivan
 */
public class BarajasDTO implements Serializable {
    private Integer id;
    private String color;
    private String marca;
    private Float valorpromedio;
    MaterialesDTO material;

    public BarajasDTO() {
    }

    public BarajasDTO(Integer id, String color, String marca, Float valorpromedio, MaterialesDTO material) {
        this.id = id;
        this.color = color;
        this.marca = marca;
        this.valorpromedio = valorpromedio;
        this.material = material;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Float getValorpromedio() {
        return valorpromedio;
    }

    public void setValorpromedio(Float valorpromedio) {
        this.valorpromedio = valorpromedio;
    }

    public MaterialesDTO getMaterial() {
        return material;
    }

    public void setMaterial(MaterialesDTO material) {
        this.material = material;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final BarajasDTO other = (BarajasDTO) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  color + " " + marca + " " + material.getNombre();
    }
    
}
