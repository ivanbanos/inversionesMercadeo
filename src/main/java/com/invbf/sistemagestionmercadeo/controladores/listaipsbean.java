/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.CommputadorRegistrado;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ivan
 */
@ManagedBean
@ViewScoped
public class listaipsbean implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    private List<CommputadorRegistrado> lista;
    private CommputadorRegistrado elemento;

    public listaipsbean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        if (!sessionBean.perfilViewMatch("veripregistradas")) {
            try {
                System.out.println("No lo coje");
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        lista = sessionBean.adminFacade.getComputadoresRegistrados();
        elemento = new CommputadorRegistrado();
        elemento.setUsuariosidUsuario(new Usuario());
    }

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public void eliminarComputador() {
        elemento.setEstado("INACTIVO");
        sessionBean.adminFacade.saveMaq(elemento);
        FacesUtil.addInfoMessage("Ip/Usuario desactivado con exito!", "");
        lista = sessionBean.adminFacade.getComputadoresRegistrados();
        elemento = new CommputadorRegistrado();
        elemento.setUsuariosidUsuario(new Usuario());
    }

    public void editarComputador() {
        elemento.setEstado("ACTIVO");
        sessionBean.adminFacade.saveMaq(elemento);
        FacesUtil.addInfoMessage("Ip/Usuario activado con exito!", "");
        lista = sessionBean.adminFacade.getComputadoresRegistrados();
        elemento = new CommputadorRegistrado();
        elemento.setUsuariosidUsuario(new Usuario());
    }

    public void borrarComputador() {
        sessionBean.adminFacade.deleteMaq(elemento);
        FacesUtil.addInfoMessage("Ip/Usuario eliminado con exito!", "");
        lista = sessionBean.adminFacade.getComputadoresRegistrados();
        elemento = new CommputadorRegistrado();
        elemento.setUsuariosidUsuario(new Usuario());
    }

    public List<CommputadorRegistrado> getLista() {
        return lista;
    }

    public void setLista(List<CommputadorRegistrado> lista) {
        this.lista = lista;
    }

    public CommputadorRegistrado getElemento() {
        return elemento;
    }

    public void setElemento(CommputadorRegistrado elemento) {
        this.elemento = elemento;
    }

}
