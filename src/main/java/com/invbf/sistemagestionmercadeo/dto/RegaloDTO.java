/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dto;

import com.invbf.sistemagestionmercadeo.entity.Categorias;
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
    private CategoriaDTO categoria;
    
    public RegaloDTO() {
    }

    public RegaloDTO(Regalos regalo) {
        this.id = regalo.getId();
        this.nombre = regalo.getNombre();
        this.genero = regalo.getGenero();
        this.descripcion = regalo.getDescripcion();
        this.categoria = new CategoriaDTO(regalo.getCategoria().getIdCategorias(), regalo.getCategoria().getNombre());
    }
    
    public RegaloDTO(Integer id, String nombre, String genero, String descripcion, CategoriaDTO categoria) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.descripcion = descripcion;
        this.categoria = categoria;
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
        regalo.setCategoria(new Categorias(categoria.getId()));
        regalo.setDescripcion(descripcion);
        regalo.setNombre(nombre);
        regalo.setGenero(genero);
        return regalo;
    }
    
}
