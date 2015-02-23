/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import java.io.Serializable;

/**
 *
 * @author ivan
 */
public class Mensajes implements Serializable{
    private int tipo;
    private String mensaje;
    private String detalle;
    public static final int ERROR = 1;
    public static final int ADVERTENCIA = 2;
    public static final int INFORMACION = 3;

    public Mensajes() {
    }

    public Mensajes(int tipo, String mensaje, String detalle) {
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.detalle = detalle;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
    
}
