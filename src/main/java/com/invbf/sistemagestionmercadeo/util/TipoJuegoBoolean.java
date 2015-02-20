/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Tipojuego;
import java.io.Serializable;


/**
 *
 * @author ideacentre
 */
public class TipoJuegoBoolean  implements Serializable{
    private Tipojuego tipoJuego;
    private boolean selected;

    public TipoJuegoBoolean() {
    }

    public TipoJuegoBoolean(Tipojuego tipoJuego, boolean isSelected) {
        this.tipoJuego = tipoJuego;
        this.selected = isSelected;
    }

    public Tipojuego getTipoJuego() {
        return tipoJuego;
    }

    public void setTipoJuego(Tipojuego tipoJuego) {
        this.tipoJuego = tipoJuego;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    public boolean compareIdTipoJuego(Integer categoria){
        if ((this.tipoJuego.getIdTipoJuego() == null || !this.tipoJuego.getIdTipoJuego().equals(categoria))) {
            return false;
        }
        return true;
    }
    
}
