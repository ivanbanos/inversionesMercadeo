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
    
}
