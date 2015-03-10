/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Tipobono;
import java.io.Serializable;

/**
 *
 * @author ivan
 */
public class TipoBonoBoolean implements Serializable{
    private Tipobono tipo;
    private boolean selected;

    public TipoBonoBoolean() {
    }

    public TipoBonoBoolean(Tipobono tipo, boolean selected) {
        this.tipo = tipo;
        this.selected = selected;
    }

    public Tipobono getTipo() {
        return tipo;
    }

    public void setTipo(Tipobono tipo) {
        this.tipo = tipo;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
