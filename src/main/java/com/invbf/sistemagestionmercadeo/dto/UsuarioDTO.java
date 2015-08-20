/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dto;

import com.invbf.sistemagestionmercadeo.entity.Usuario;
import java.io.Serializable;

/**
 *
 * @author ivan
 */
public class UsuarioDTO implements Serializable {
    private Integer id;
    private String nombre;
    

    public UsuarioDTO(Usuario usuario) {
        id = usuario.getIdUsuario();
        nombre = usuario.getNombre();
    }

    public UsuarioDTO() {
        id= 0;
        nombre = "no usuario";
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
