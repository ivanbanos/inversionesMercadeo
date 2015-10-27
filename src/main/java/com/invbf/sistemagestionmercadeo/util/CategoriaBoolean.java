/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Categoria;
import java.io.Serializable;


/**
 *
 * @author ideacentre
 */
public class CategoriaBoolean  implements Serializable{
    private Categoria categoria;
    private boolean selected;

    public CategoriaBoolean() {
    }

    public CategoriaBoolean(Categoria categoria, boolean isSelected) {
        this.categoria = categoria;
        this.selected = isSelected;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    public boolean compareIdCategorias(Integer categoria){
        if ((this.categoria.getIdCategorias() == null || !this.categoria.getIdCategorias().equals(categoria))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (this.categoria != null ? this.categoria.hashCode() : 0);
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
        final CategoriaBoolean other = (CategoriaBoolean) obj;
        if (this.categoria != other.categoria && (this.categoria == null || !this.categoria.equals(other.categoria))) {
            return false;
        }
        return true;
    }
    
}
