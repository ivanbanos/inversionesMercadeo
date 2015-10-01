/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dto;

import com.invbf.sistemagestionmercadeo.entity.Categoria;
import com.invbf.sistemagestionmercadeo.entity.Regalos;
import java.io.Serializable;

/**
 *
 * @author ivan
 */
public class RegaloDTO implements Serializable {
    
    private Integer id;
    private String nombre;
    private String genero;
    private String descripcion;
    private String fileName;
    private CategoriaDTO categoria;
    
    public RegaloDTO() {
    }

    public RegaloDTO(Regalos regalo) {
        this.id = regalo.getId();
        this.nombre = regalo.getNombre();
        this.genero = regalo.getGenero();
        this.descripcion = regalo.getDescripcion();
        this.categoria = new CategoriaDTO(regalo.getCategoria().getIdCategorias(), regalo.getCategoria().getNombre());
    this.fileName = regalo.getFoto();
    }
    
    public RegaloDTO(Integer id, String nombre, String genero, String descripcion, CategoriaDTO categoria, String fileName) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.fileName = fileName;
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
    
    public String getGenero() {
        return genero;
    }
    
    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public CategoriaDTO getCategoria() {
        return categoria;
    }
    
    public void setCategoria(CategoriaDTO categoria) {
        this.categoria = categoria;
    }
    
    public Regalos getRegalo() {
        Regalos regalo = new Regalos();
        regalo.setId(id);
        regalo.setCategoria(new Categoria(categoria.getId()));
        regalo.setDescripcion(descripcion);
        regalo.setNombre(nombre);
        regalo.setGenero(genero);
        regalo.setFoto(fileName);
        return regalo;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final RegaloDTO other = (RegaloDTO) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
}
