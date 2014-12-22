/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Propositoentrega;
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
public class CrudPropositos {
    private List<Propositoentrega> lista;
    private Propositoentrega elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public CrudPropositos() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("atributosbonos");
        lista = sessionBean.adminFacade.findAllPropositosentrega();
        elemento = new Propositoentrega();
    }

    public List<Propositoentrega> getLista() {
        return lista;
    }

    public void setLista(List<Propositoentrega> lista) {
        this.lista = lista;
    }

    public Propositoentrega getElemento() {
        return elemento;
    }

    public void setElemento(Propositoentrega elemento) {
        this.elemento = elemento;
    }

    public void delete() {
        if (elemento.getSolicitudentregaList().isEmpty() ) {
            sessionBean.adminFacade.deletePropositosentrega(elemento);
            lista = sessionBean.adminFacade.findAllPropositosentrega();
            FacesUtil.addInfoMessage("Proposito de entrega eliminado", elemento.getNombre()+ "");
            elemento = new Propositoentrega();
        } else {
            FacesUtil.addInfoMessage("Proposito de entrega no puede ser borrado", elemento.getNombre()+ "");
        }
    }

    public void guardar() {
        boolean opcion = sessionBean.adminFacade.guardarPropositosentrega(elemento);
        lista = sessionBean.adminFacade.findAllPropositosentrega();
        if (opcion) {
            FacesUtil.addInfoMessage("Proposito de entrega actualizado", elemento.getNombre()+ "");
        } else {
            FacesUtil.addInfoMessage("Proposito de entrega creado", elemento.getNombre()+ "");
        }
        elemento = new Propositoentrega();
    }
}
