/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Bono;
import com.invbf.sistemagestionmercadeo.entity.Cargo;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.CommputadorRegistrado;
import com.invbf.sistemagestionmercadeo.entity.Controlsalidabono;
import com.invbf.sistemagestionmercadeo.entity.Listasclientestareas;
import com.invbf.sistemagestionmercadeo.entity.Log;
import com.invbf.sistemagestionmercadeo.entity.Perfil;
import com.invbf.sistemagestionmercadeo.entity.Solicitudcambiocupofidelizacion;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentrega;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregalotesmaestro;
import com.invbf.sistemagestionmercadeo.entity.Tarea;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.entity.Usuariodetalle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ivan
 */
public class UsuarioDTO implements Serializable {
    private Integer idUsuario;
    private String nombreUsuario;
    private String contrasena;
    private String estado;
    private List<CasinoBoolean> casinos;
    private Usuariodetalle usuariodetalle;
    private Perfil idPerfil;

    public UsuarioDTO() {
    }
    
    public UsuarioDTO(Usuario u, List<Casino> casinos){
        idUsuario  = u.getIdUsuario();
        nombreUsuario  = u.getNombreUsuario();
        contrasena  = u.getContrasena();
        estado  = u.getEstado();
        usuariodetalle  = u.getUsuariodetalle();
        if(usuariodetalle.getIdcargo()==null){
            usuariodetalle.setIdcargo(new Cargo());
        }
        idPerfil  = u.getIdPerfil();
        this.casinos = new ArrayList<CasinoBoolean>();
        for (Casino casino : casinos) {
            if (u.getCasinoList().contains(casino)) {
                this.casinos.add(new CasinoBoolean(casino, true));
            } else {
                this.casinos.add(new CasinoBoolean(casino, false));
            }
        }
    }
    public UsuarioDTO(List<Casino> casinos){
        this.casinos = new ArrayList<CasinoBoolean>();
        for (Casino casino : casinos) {
                this.casinos.add(new CasinoBoolean(casino, false));
            
        }
    }
    
    public Usuario getUsuarioWithId(){
        Usuario u = new Usuario(idUsuario);
        u.setUsuariodetalle(usuariodetalle);
        return u;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<CasinoBoolean> getCasinos() {
        return casinos;
    }

    public void setCasinos(List<CasinoBoolean> casinos) {
        this.casinos = casinos;
    }

    public Usuariodetalle getUsuariodetalle() {
        return usuariodetalle;
    }

    public void setUsuariodetalle(Usuariodetalle usuariodetalle) {
        this.usuariodetalle = usuariodetalle;
    }

    public Perfil getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Perfil idPerfil) {
        this.idPerfil = idPerfil;
    }
    
}
