/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Accion;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class CrudAccionesBean  implements Serializable{

    private List<Accion> lista;
    private Accion elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    private List<Accion> flista;

    public List<Accion> getFlista() {
        return flista;
    }

    public void setFlista(List<Accion> flista) {
        this.flista = flista;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudAccionesBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");
        elemento = new Accion();
        lista = sessionBean.marketingUserFacade.findAllAcciones();
    }

    public List<Accion> getLista() {
        return lista;
    }

    public void setLista(List<Accion> lista) {
        this.lista = lista;
    }

    public Accion getElemento() {
        return elemento;
    }

    public void setElemento(Accion elemento) {
        this.elemento = elemento;
    }

    public void delete() {
        sessionBean.marketingUserFacade.deleteAccion(elemento);
        lista = sessionBean.marketingUserFacade.findAllAcciones();
        FacesUtil.addInfoMessage("Accion eliminada", elemento.getNombre());
        elemento = new Accion();
    }

    public void guardar() {
        boolean opcion = sessionBean.marketingUserFacade.guardarAccion(elemento);
        lista = sessionBean.marketingUserFacade.findAllAcciones();
        if (opcion) {
            FacesUtil.addInfoMessage("Estado actualizado", elemento.getNombre());
        } else {
            FacesUtil.addInfoMessage("Estado creado", elemento.getNombre());
        }
        elemento = new Accion();
    }

}
