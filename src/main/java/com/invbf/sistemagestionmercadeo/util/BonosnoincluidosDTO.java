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
public class BonosnoincluidosDTO {
    private Integer id;
    private String consecutivo;

    public BonosnoincluidosDTO() {
        consecutivo = "";
    }

    public BonosnoincluidosDTO(Integer id) {
        this.id = id;
        consecutivo = "";
    }

    public BonosnoincluidosDTO(Integer id, String consecutivo) {
        this.id = id;
        this.consecutivo = consecutivo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }
    
    
}
