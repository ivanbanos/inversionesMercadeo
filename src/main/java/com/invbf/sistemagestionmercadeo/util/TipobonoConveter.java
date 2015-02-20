/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Tipobono;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author ivan
 */
@FacesConverter("tipobonoConverter")
public class TipobonoConveter implements Converter, Serializable {

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        if (value == null) {
            return null;
        }
        // value must be of a type that can be cast to a String.
        try {
            if (value instanceof Tipobono) {
                return ((Tipobono) value).toString();
            }
            return "";
        } catch (ClassCastException ce) {
            FacesMessage errMsg = new FacesMessage("Error al convertir valor");
            FacesContext.getCurrentInstance().addMessage(null, errMsg);
            throw new ConverterException(errMsg.getSummary());
        }

    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Tipobono tipobono;
        tipobono = new Tipobono();
        if (value.split(" ").length == 2 || value.split(" ").length == 3) {
            tipobono.setId(Integer.parseInt(value.split(" ")[0]));
            tipobono.setNombre(value.split(" ")[1]);
            if (value.split(" ").length == 3) {
                tipobono.setNombre(tipobono.getNombre() + " " + value.split(" ")[2]);
            }
        }
        return tipobono;
    }
}
