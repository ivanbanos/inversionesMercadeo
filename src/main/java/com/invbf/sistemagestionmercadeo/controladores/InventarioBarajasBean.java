/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.dto.CasinoDto;
import com.invbf.sistemagestionmercadeo.dto.InventarioBarajasDTO;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.util.CasinoBoolean;
import com.invbf.sistemagestionmercadeo.util.Mensajes;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
public class InventarioBarajasBean implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private InventarioBarajasDTO inventario;
    private List<CasinoBoolean> casinos;

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

    public InventarioBarajasBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("barajas");
        if (!sessionBean.perfilViewMatch("verBodegas")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        if (sessionBean.getAttributes("bodega") == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("ListaOrdenesCompraBarajasBean.xhtml");
            } catch (IOException ex) {
            }
        }
        Integer bodega = (Integer) sessionBean.getAttributes("bodega");
        inventario = sessionBean.barajasFacade.getInventario(bodega);
        casinos = new ArrayList<CasinoBoolean>();
        List<Casino> casinose = sessionBean.adminFacade.findAllCasinos();
        for (Casino casinoe : casinose) {
            CasinoBoolean casino = new CasinoBoolean(casinoe, false);
            for(CasinoDto casinodto : inventario.getCasinos()){
                if(casinodto.getIdCasino().equals(casinoe.getIdCasino())){
                    casino.setSelected(true);
                }
            }
            casinos.add(casino);
        }
        sessionBean.printMensajes();
    }

    public InventarioBarajasDTO getInventario() {
        return inventario;
    }

    public void setInventario(InventarioBarajasDTO inventario) {
        this.inventario = inventario;
    }

    public List<CasinoBoolean> getCasinos() {
        return casinos;
    }

    public void setCasinos(List<CasinoBoolean> casinos) {
        this.casinos = casinos;
    }

    public void guardarCambios(){
        sessionBean.barajasFacade.guardarBodega(inventario,casinos);
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Cambios guardados con exito!", ""));
    }
}
