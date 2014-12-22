/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Tipobono;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
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
public class CrudTipoBonoBean {

    private List<Tipobono> lista;
    private Tipobono elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public CrudTipoBonoBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("atributosbonos");
        lista = sessionBean.adminFacade.findAllTiposbonos();
        elemento = new Tipobono();
    }

    public List<Tipobono> getLista() {
        return lista;
    }

    public void setLista(List<Tipobono> lista) {
        this.lista = lista;
    }

    public Tipobono getElemento() {
        return elemento;
    }

    public void setElemento(Tipobono elemento) {
        this.elemento = elemento;
    }

    public void delete() {
        if (elemento.getBonoList().isEmpty() && elemento.getLotebonoList().isEmpty()) {
            sessionBean.adminFacade.deleteTiposbonos(elemento);
            lista = sessionBean.adminFacade.findAllTiposbonos();
            FacesUtil.addInfoMessage("Tipo bono eliminado", elemento.getNombre() + "");
            elemento = new Tipobono();
        } else {
            FacesUtil.addInfoMessage("Tipo bono no puede ser borrado", elemento.getNombre() + "");
        }
    }

    public void guardar() {
        boolean opcion = sessionBean.adminFacade.guardarTiposbonos(elemento);
        lista = sessionBean.adminFacade.findAllTiposbonos();
        if (opcion) {
            FacesUtil.addInfoMessage("Tipo bono actualizado", elemento.getNombre() + "");
        } else {
            FacesUtil.addInfoMessage("tipo bono creado", elemento.getNombre() + "");
        }
        elemento = new Tipobono();
    }
}
