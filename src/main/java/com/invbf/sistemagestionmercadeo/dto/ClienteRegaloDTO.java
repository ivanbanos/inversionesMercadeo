/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ivan
 */
public class ClienteRegaloDTO implements Serializable{
     private Integer idCliente;
    private String nombres;
    private String categoria;
    private String sexo;
    private RegaloDTO regalo;
    private Date cumpleanos;

    public ClienteRegaloDTO(Integer idCliente, String nombres, String categoria, String sexo, Date cumpleanos) {
        this.idCliente = idCliente;
        this.nombres = nombres;
        this.categoria = categoria;
        this.sexo = sexo;
        regalo = new RegaloDTO();
        this.cumpleanos = cumpleanos;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public RegaloDTO getRegalo() {
        return regalo;
    }

    public void setRegalo(RegaloDTO regalo) {
        this.regalo = regalo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getCumpleanos() {
        return cumpleanos;
    }

    public void setCumpleanos(Date cumpleanos) {
        this.cumpleanos = cumpleanos;
    }
    
}
