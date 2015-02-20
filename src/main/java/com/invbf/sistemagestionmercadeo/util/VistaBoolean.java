/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Vista;
import java.io.Serializable;


/**
 *
 * @author ivan
 */
public class VistaBoolean  implements Serializable{
    private Vista vista;
    private boolean selected;

    public VistaBoolean() {
    }

    public VistaBoolean(Vista vista, boolean selected) {
        this.vista = vista;
        this.selected = selected;
    }

    public Vista getVista() {
        return vista;
    }

    public void setVista(Vista vista) {
        this.vista = vista;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
}
