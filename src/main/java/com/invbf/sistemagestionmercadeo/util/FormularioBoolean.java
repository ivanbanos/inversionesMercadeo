/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Formulario;
import java.io.Serializable;


/**
 *
 * @author ivan
 */
public class FormularioBoolean  implements Serializable{
    private Formulario formulario;
    private boolean selected;

    public FormularioBoolean() {
    }

    public FormularioBoolean(Formulario formulario, boolean selected) {
        this.formulario = formulario;
        this.selected = selected;
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
            
}
