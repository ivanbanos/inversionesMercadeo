/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author ivan
 */
@FacesConverter("numberSeparationConverter")
public class NumberSeparationConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        String parteFlotante = string.substring(string.lastIndexOf("."));
        string = string.substring(0, string.lastIndexOf("."));
        string = string.replace('.', '_');
        string = string.replace('\'', '_');
        return Float.parseFloat(string + parteFlotante);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        String numberseparated = "";
        String iPartS = "";
        if (o instanceof Double) {
            double number = (Double) o;
            Long iPart = (long) number;
            iPartS = iPart.toString();
        } else if (o instanceof Float) {
            float number = (Float) o;
            Long iPart = (long) number;
            iPartS = iPart.toString();
        } else {
            String number = (String) o;
            System.out.println("numero " + number);
            if (number.lastIndexOf(".") != -1) {
                iPartS = number.substring(0, number.lastIndexOf("."));
            } else {
                iPartS = number;
            }
        }
        boolean milesima = true;

        while (true) {
            System.out.println("asi va el numero " + numberseparated);
            System.out.println("y queda esto " + iPartS);
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
        System.out.println("asi quedo sin parte real " + numberseparated);
        System.out.println("asi quedo con parte real " + numberseparated);
        System.out.println();
        System.out.println();
        return numberseparated;
    }

}
