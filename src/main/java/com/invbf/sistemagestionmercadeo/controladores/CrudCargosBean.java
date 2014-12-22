/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Cargo;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author ivan
 */
@ManagedBean
@ViewScoped
public class CrudCargosBean {
    private List<Cargo> lista;
    private Cargo elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudCargosBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");
        elemento = new Cargo();
        lista = new ArrayList<Cargo>();
        lista = sessionBean.adminFacade.findAllCargos();
    }

    public List<Cargo> getLista() {
        return lista;
    }
   
    public void setLista(List<Cargo> lista) {
        this.lista = lista;
}

    public Cargo getElemento() {
        return elemento;
    }

    public void setElemento(Cargo elemento) {
        this.elemento = elemento;
    }

    public void delete() {
        sessionBean.adminFacade.deleteCargos(elemento);
        lista = sessionBean.adminFacade.findAllCargos();
        FacesUtil.addInfoMessage("Cargo eliminado", elemento.getNombre());
        elemento = new Cargo();
    }

    public void guardar() {
        boolean opcion = sessionBean.adminFacade.guardarCargos(elemento);
        lista = sessionBean.adminFacade.findAllCargos();
        if (opcion) {
            FacesUtil.addInfoMessage("Cargo actualizado", elemento.getNombre());
        } else {
            FacesUtil.addInfoMessage("Cargo creado", elemento.getNombre());
        }
        elemento = new Cargo();
    }
}
