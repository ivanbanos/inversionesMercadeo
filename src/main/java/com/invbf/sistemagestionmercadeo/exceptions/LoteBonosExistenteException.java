/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.exceptions;

/**
 *
 * @author ivan
 */
public class LoteBonosExistenteException extends Exception {

    /**
     * Creates a new instance of <code>LoteBonosExistenteException</code>
     * without detail message.
     */
    public LoteBonosExistenteException() {
    }

    /**
     * Constructs an instance of <code>LoteBonosExistenteException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public LoteBonosExistenteException(String msg) {
        super(msg);
    }
}
