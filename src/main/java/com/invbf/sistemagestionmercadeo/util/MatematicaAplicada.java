/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

/**
 *
 * @author ivan
 */
public class MatematicaAplicada {

    public static boolean sePuedeLleagar(Float num, Float... multiplos) {
        while (num>0) {
            Float dividir = 0f;
            for (Float multiplo : multiplos) {
                if (num % multiplo == 0) {
                    return true;
                } else {
                    if (num > multiplo) {
                        dividir = multiplo;
                    }
                }
            }
            if (dividir == 0) {
                return false;
            } else{
                num -= dividir;
            }
        }
        return false;
    }
}
