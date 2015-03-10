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
public class Denominacion implements Serializable{
    private Integer id;
    private String valor;

    public Denominacion(Integer id, String valor) {
        this.id = id;
        this.valor = valor;
    }

    public Denominacion() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
