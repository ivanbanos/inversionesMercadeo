/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Cliente;
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
@FacesConverter("clientecom")
public class ClientesConverter implements Converter, Serializable{

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        
        if (value == null) {
            return null;
        }
        // value must be of a type that can be cast to a String.
        try {
            return ((Cliente)value).toString();
        } catch (ClassCastException ce) {
            FacesMessage errMsg = new FacesMessage("Error al convertir valor");
            FacesContext.getCurrentInstance().addMessage(null, errMsg);
            throw new ConverterException(errMsg.getSummary());
        }
       
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Cliente clientes;
        clientes = new Cliente();
        clientes.setIdCliente(Integer.parseInt(value.split(" ")[0]));
        clientes.setNombres(value.split(" ")[1]);
        clientes.setApellidos(value.split(" ")[2]);
        return clientes;
    }
}
