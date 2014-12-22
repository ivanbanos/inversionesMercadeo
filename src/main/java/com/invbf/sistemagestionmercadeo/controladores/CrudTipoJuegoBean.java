/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Tipojuego;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
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
public class CrudTipoJuegoBean {

    private List<Tipojuego> lista;
    private Tipojuego elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    private List<Tipojuego> flista;

    public List<Tipojuego> getFlista() {
        return flista;
    }

    public void setFlista(List<Tipojuego> flista) {
        this.flista = flista;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudTipoJuegoBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");
        elemento = new Tipojuego();
        lista = sessionBean.marketingUserFacade.findAllTiposjuegos();
    }

    public List<Tipojuego> getLista() {
        return lista;
    }

    public void setLista(List<Tipojuego> lista) {
        this.lista = lista;
    }

    public Tipojuego getElemento() {
        return elemento;
    }

    public void setElemento(Tipojuego elemento) {
        this.elemento = elemento;
    }

    public void delete() {
        sessionBean.marketingUserFacade.deleteTiposjuegos(elemento);
        lista = sessionBean.marketingUserFacade.findAllTiposjuegos();
        FacesUtil.addInfoMessage("Tipo de juegos eliminado", elemento.getNombre());
        elemento = new Tipojuego();
    }

    public void guardar() {
        boolean opcion = sessionBean.marketingUserFacade.guardarTiposjuegos(elemento);
        lista = sessionBean.marketingUserFacade.findAllTiposjuegos();

        if (opcion) {
            FacesUtil.addInfoMessage("Tipo de juegos actualizado", elemento.getNombre());
        } else {
            FacesUtil.addInfoMessage("Tipo de juegos creado", elemento.getNombre());
        }
        elemento = new Tipojuego();
    }

}
