/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Permiso;
import com.invbf.sistemagestionmercadeo.entity.Tipodocumento;
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
public class SolicitudCambioCupoFidelizacionBean {

    private List<Cliente> clientes;
    private Cliente clienteAttr;
    private List<Tipodocumento> tiposdocumentos;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private Cliente elemento;
    private String viejo;
    private String observaciones;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public SolicitudCambioCupoFidelizacionBean() {
    }

    @PostConstruct
    public void init() {
        clientes = new ArrayList<Cliente>();
        clienteAttr = new Cliente();
        clienteAttr.setIdTipoDocumento(new Tipodocumento());
        tiposdocumentos
                = sessionBean.marketingUserFacade.findAllTipoDocumentos();
        elemento = new Cliente();

    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Cliente getClienteAttr() {
        return clienteAttr;
    }

    public void setClienteAttr(Cliente clienteAttr) {
        this.clienteAttr = clienteAttr;
    }

    public List<Tipodocumento> getTiposdocumentos() {
        return tiposdocumentos;
    }

    public void setTiposdocumentos(List<Tipodocumento> tiposdocumentos) {
        this.tiposdocumentos = tiposdocumentos;
    }

    public void burcarClientes() {
        System.out.println("Buscando");
        clientes = sessionBean.marketingUserFacade.buscarClientes(clienteAttr);

        System.out.println(clientes.size() + " Clientes encontrados");
    }

    public void cambiarCupo() {
        sessionBean.managerUserFacade.addPermiso(new Permiso("EDITAR",
                elemento.getIdCliente().toString(), "CLIENTE",
                "bonoFidelizacion", elemento.getBonoFidelizacion(),
                elemento.getBonoFidelizacion(), viejo, viejo, observaciones));
    }

    public Cliente getElemento() {
        return elemento;
    }

    public void setElemento(Cliente elemento) {
        this.elemento = elemento;
        if (elemento.getBonoFidelizacion() != null) {
            viejo = elemento.getBonoFidelizacion();
        } else {
            viejo = "0";
        }
    }

    public String getViejo() {
        return viejo;
    }

    public void setViejo(String viejo) {
        this.viejo = viejo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

}
