/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Acceso;


/**
 *
 * @author ivan
 */
public class AccesoBoolean {
    Acceso acceso;
    Boolean selected;

    public AccesoBoolean() {
    }

    public AccesoBoolean(Acceso acceso, Boolean selected) {
        this.acceso = acceso;
        this.selected = selected;
    }

    public Acceso getAcceso() {
        return acceso;
    }

    public void setAcceso(Acceso acceso) {
        this.acceso = acceso;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
    
}
