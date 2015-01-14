/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.dao.TipostareasDao;
import com.invbf.sistemagestionmercadeo.entity.Tipotarea;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author ivan
 */

@FacesConverter("tipoTarea")
public class tipoTareaConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return TipostareasDao.findBynombre(string);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Tipotarea t = (Tipotarea)o;
        return t.getNombre();
    }
    
}
