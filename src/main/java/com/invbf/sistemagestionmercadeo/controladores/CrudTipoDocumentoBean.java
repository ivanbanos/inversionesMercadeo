/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Tipodocumento;
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
public class CrudTipoDocumentoBean {

    private List<Tipodocumento> lista;
    private Tipodocumento elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    private List<Tipodocumento> flista;

    public List<Tipodocumento> getFlista() {
        return flista;
    }

    public void setFlista(List<Tipodocumento> flista) {
        this.flista = flista;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudTipoDocumentoBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");
        elemento = new Tipodocumento();
        lista = sessionBean.marketingUserFacade.findAllTipoDocumentos();
    }

    public List<Tipodocumento> getLista() {
        return lista;
    }

    public void setLista(List<Tipodocumento> lista) {
        this.lista = lista;
    }

    public Tipodocumento getElemento() {
        return elemento;
    }

    public void setElemento(Tipodocumento elemento) {
        this.elemento = elemento;
    }

    public void delete() {
        sessionBean.marketingUserFacade.deleteTipoDocumentos(elemento);
        lista = sessionBean.marketingUserFacade.findAllTipoDocumentos();
        FacesUtil.addInfoMessage("Tipo documento eliminado", elemento.getNombre());
        elemento = new Tipodocumento();
    }

    public void guardar() {
        boolean opcion = sessionBean.marketingUserFacade.guardarTipoDocumentos(elemento);
        lista = sessionBean.marketingUserFacade.findAllTipoDocumentos();
        if (opcion) {
            FacesUtil.addInfoMessage("Tipo documento actualizado", elemento.getNombre());
        } else {
            FacesUtil.addInfoMessage("Tipo documento creado", elemento.getNombre());
        }
        elemento = new Tipodocumento();
    }

}
