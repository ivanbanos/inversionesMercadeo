/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author ivan
 */
@FacesConverter("numberSeparationConverter")
public class NumberSeparationConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string.lastIndexOf(".") != -1) {
            String parteFlotante = string.substring(string.lastIndexOf("."));
            string = string.substring(0, string.lastIndexOf("."));
            string = string.replace('.', '_');
            string = string.replace('\'', '_');
            return Float.parseFloat(string + parteFlotante);
        } else {
            return Float.parseFloat(string);
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        String numberseparated = "";
        String iPartS = "";
        boolean tieneelsimbolo = false;
        if (o instanceof Double) {
            double number = (Double) o;
            Long iPart = (long) number;
            iPartS = iPart.toString();
        } else if (o instanceof Float) {
            float number = (Float) o;
            Long iPart = (long) number;
            iPartS = iPart.toString();
        } else {
            String number;
            if (o instanceof Integer) {
                number = ((Integer) o).toString();
            } else {
                number = (String) o;
            }
            if (number.lastIndexOf(".") != -1) {
                iPartS = number.substring(0, number.lastIndexOf("."));
            } else {
                iPartS = number;
            }
        }
        boolean milesima = true;

        while (true) {
            if (iPartS.length() == 0) {
                break;
            } else if (iPartS.length() <= 3) {
                numberseparated = iPartS + numberseparated;
                iPartS = "";
            } else {
                if (milesima) {
                    numberseparated = "." + iPartS.substring(iPartS.length() - 3) + numberseparated;
                    iPartS = iPartS.substring(0, iPartS.length() - 3);
                    milesima = false;
                } else {
                    numberseparated = "'" + iPartS.substring(iPartS.length() - 3) + numberseparated;
                    iPartS = iPartS.substring(0, iPartS.length() - 3);
                    milesima = true;
                }
            }
        }
        return numberseparated;
    }

}
