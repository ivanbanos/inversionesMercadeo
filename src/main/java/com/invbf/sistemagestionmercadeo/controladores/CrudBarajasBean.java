/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.dto.BarajasDTO;
import com.invbf.sistemagestionmercadeo.dto.MaterialesDTO;
import com.invbf.sistemagestionmercadeo.entity.Barajas;
import com.invbf.sistemagestionmercadeo.entity.Materialesbarajas;
import com.invbf.sistemagestionmercadeo.util.Mensajes;
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
public class CrudBarajasBean implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<BarajasDTO> lista;
    private BarajasDTO elemento;
    private List<MaterialesDTO> materiales;
    private MaterialesDTO material;

    /**
     * @return the sessionBean
     */
    public SessionBean getSessionBean() {
        return sessionBean;
    }

    /**
     * @param sessionBean the sessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public CrudBarajasBean() {

    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("barajas");
        if (!sessionBean.perfilViewMatch("verBarajas")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        inicializar();
    }

    public List<BarajasDTO> getLista() {
        return lista;
    }

    public void setLista(List<BarajasDTO> lista) {
        this.lista = lista;
    }

    public BarajasDTO getElemento() {
        return elemento;
    }

    public void setElemento(BarajasDTO elemento) {
        this.elemento = elemento;
    }

    public List<MaterialesDTO> getMateriales() {
        return materiales;
    }

    public void setMateriales(List<MaterialesDTO> materiales) {
        this.materiales = materiales;
    }

    public MaterialesDTO getMaterial() {
        return material;
    }

    public void setMaterial(MaterialesDTO material) {
        this.material = material;
    }

    public void eliminarMaterial() {
        material = sessionBean.barajasFacade.deleteMaterial(material);
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "OK!", "Material de barajas " + material.getNombre() + " eliminado con exito!"));

        inicializar();
    }

    public void agregarMaterial() {
        material.setId(null);
        material = sessionBean.barajasFacade.addMaterial(material);
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "OK!", "Material de barajas " + material.getNombre() + " Agregado con exito!"));

        inicializar();
    }

    public void actualizarBaraja() {
        elemento = sessionBean.barajasFacade.addBaraja(elemento);
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "OK!", "Baraja Agregada con exito!"));
        inicializar();
    }

    public void eliminarBaraja() {
        if (elemento.getId() == null) {
            sessionBean.putMensaje(new Mensajes(Mensajes.ADVERTENCIA, "Advertencia!", "Debe seleccionar una baraja primero"));
        } else {
            elemento = sessionBean.barajasFacade.deleteBaraja(elemento);
            sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "OK!", "Baraja eliminada con exito!"));
        }
        inicializar();
    }

    public void nuevaBaraja() {
        inicializar();
    }

    private void inicializar() {
        material = new MaterialesDTO();
        elemento = new BarajasDTO();
        elemento.setMaterial(new MaterialesDTO());
        lista = sessionBean.barajasFacade.getListaBarajas();
        materiales = sessionBean.barajasFacade.getListaMateriales();
        sessionBean.printMensajes();
    }
}
