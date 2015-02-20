/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Denominacion;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import java.io.Serializable;
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
public class CrudDenominacionesBonosBean  implements Serializable{

    private List<Denominacion> lista;
    private Denominacion elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public CrudDenominacionesBonosBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("atributosbonos");
        lista = sessionBean.adminFacade.findAllDenominaciones();
        elemento = new Denominacion();
    }

    public List<Denominacion> getLista() {
        return lista;
    }

    public void setLista(List<Denominacion> lista) {
        this.lista = lista;
    }

    public Denominacion getElemento() {
        return elemento;
    }

    public void setElemento(Denominacion elemento) {
        this.elemento = elemento;
    }

    public void delete() {
        if (elemento.getBonoList().isEmpty() && elemento.getLotebonoList().isEmpty()) {
            sessionBean.adminFacade.deleteDenominacion(elemento);
            lista = sessionBean.adminFacade.findAllDenominaciones();
            FacesUtil.addInfoMessage("Denominación eliminada", elemento.getValor() + "");
            elemento = new Denominacion();
        } else {
            FacesUtil.addInfoMessage("Denominación no puede ser borrado", elemento.getValor() + "");
        }
    }

    public void guardar() {
        boolean opcion = sessionBean.adminFacade.guardarDenominacion(elemento);
        lista = sessionBean.adminFacade.findAllDenominaciones();
        if (opcion) {
            FacesUtil.addInfoMessage("Denominción actualizada", elemento.getValor() + "");
        } else {
            FacesUtil.addInfoMessage("Denominación creada", elemento.getValor() + "");
        }
        elemento = new Denominacion();
    }
}
