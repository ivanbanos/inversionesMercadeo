/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Tipojuego;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author ideacentre
 */
@FacesConverter("tipojuegocom")
public class TiposJuegosConverter implements Converter {

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        
        if (value == null) {
            return null;
        }
        // value must be of a type that can be cast to a String.
        try {
            return ((Tipojuego)value).toString();
        } catch (ClassCastException ce) {
            FacesMessage errMsg = new FacesMessage("Error al convertir valor");
            FacesContext.getCurrentInstance().addMessage(null, errMsg);
            throw new ConverterException(errMsg.getSummary());
        }
       
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Tipojuego tiposjuegos;
        tiposjuegos = new Tipojuego();
        tiposjuegos.setIdTipoJuego(Integer.parseInt(value.split(" ")[0]));
        tiposjuegos.setNombre(value.split(" ")[1]);
        return tiposjuegos;
    }
}
