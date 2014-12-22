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
public class Notificador {
    
    public static final int SOLICITUD_ENTREGA_LOTES_GENERADA = 1;

    public static void notificar(int tipo) {
        switch(tipo){
            case SOLICITUD_ENTREGA_LOTES_GENERADA:
                break;
        }
    }

}
