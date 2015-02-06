/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Propositoentrega;

/**
 *
 * @author ivan
 */
public class PropositosBoolean {
    private Propositoentrega propositoentrega;
    private boolean selected;

    public PropositosBoolean() {
    }

    public PropositosBoolean(Propositoentrega propositoentrega, boolean selected) {
        this.propositoentrega = propositoentrega;
        this.selected = selected;
    }

    public Propositoentrega getPropositoentrega() {
        return propositoentrega;
    }

    public void setPropositoentrega(Propositoentrega propositoentrega) {
        this.propositoentrega = propositoentrega;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
}
