/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Accion;
import java.io.Serializable;
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
@FacesConverter("accionescom")
public class accionescom implements Converter, Serializable{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Accion accion;
        accion = new Accion();
        accion.setIdAccion(Integer.parseInt(value.split(" ")[0]));
        accion.setNombre(value.split(" ")[1]);
        return accion;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }
        // value must be of a type that can be cast to a String.
        try {
            return ((Accion)value).toString();
        } catch (ClassCastException ce) {
            FacesMessage errMsg = new FacesMessage("Error al convertir valor");
            FacesContext.getCurrentInstance().addMessage(null, errMsg);
            throw new ConverterException(errMsg.getSummary());
        }
    }
    
}
