/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.dto.CasinoDto;
import com.invbf.sistemagestionmercadeo.dto.ClienteRegaloDTO;
import com.invbf.sistemagestionmercadeo.dto.InventarioRegalosDTO;
import com.invbf.sistemagestionmercadeo.dto.RegalosCantidadDTO;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Categoria;
import com.invbf.sistemagestionmercadeo.util.CategoriaBoolean;
import com.invbf.sistemagestionmercadeo.util.ClienteDTO;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import com.invbf.sistemagestionmercadeo.util.Mensajes;
import com.invbf.sistemagestionmercadeo.util.Notificador;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class RegalosCrearSolicitudBean implements Serializable{

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    List<CasinoDto> casinos;

    private List<ClienteRegaloDTO> lista;
    private List<CategoriaBoolean> categoriaBooleans;
    private Integer mes;
    private Integer dia;
    private String sexo;
    private CasinoDto casino;

    private InventarioRegalosDTO inventario;

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public RegalosCrearSolicitudBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("regalos");
        if (!sessionBean.perfilViewMatch("RegaloCrearSolicitud")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        casinos = new ArrayList<CasinoDto>();
        for (Casino casinod : sessionBean.getUsuario().getCasinoList()) {
            casinos.add(new CasinoDto(casinod));
        }

        List<Categoria> listacategorias = sessionBean.marketingUserFacade.findAllCategorias();
        categoriaBooleans = new ArrayList<CategoriaBoolean>();

        for (Categoria categoria : listacategorias) {
            categoriaBooleans.add(new CategoriaBoolean(categoria, false));
        }
        mes = 12;
        casino = new CasinoDto();
        inventario = sessionBean.regalosFacade.getInventarioParaSolicitud();
    }

    public List<CasinoDto> getCasinos() {
        return casinos;
    }

    public void setCasinos(List<CasinoDto> casinos) {
        this.casinos = casinos;
    }

    public List<ClienteRegaloDTO> getLista() {
        return lista;
    }

    public void setLista(List<ClienteRegaloDTO> lista) {
        this.lista = lista;
    }

    public List<CategoriaBoolean> getCategoriaBooleans() {
        return categoriaBooleans;
    }

    public void setCategoriaBooleans(List<CategoriaBoolean> categoriaBooleans) {
        this.categoriaBooleans = categoriaBooleans;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public CasinoDto getCasino() {
        return casino;
    }

    public void setCasino(CasinoDto casino) {
        this.casino = casino;
    }

    public InventarioRegalosDTO getInventario() {
        return inventario;
    }

    public void setInventario(InventarioRegalosDTO inventario) {
        this.inventario = inventario;
    }

    public void busquedaAvanzada() {
        lista = new ArrayList<ClienteRegaloDTO>();
        lista.addAll(sessionBean.regalosFacade.findAllClientesCasinos(casino, categoriaBooleans, mes, sexo));
        FacesUtil.addInfoMessage("Clientes filtrados!", "Cantidad " + lista.size());
        System.gc();
    }

    public void crearSolicitud() {
        try {
            sessionBean.regalosFacade.generarSolicitudRegalos(casino, lista, sessionBean.getUsuario());
            sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Solicitud generada con exito!", ""));
Notificador.notificar(Notificador.correoRegaloSolicitudCreada,
                "Se ha creado la solicitud de obsequios. Favor revisar la lista de solicitudes de obsequios.",
                "Se ha creado la solicitud de obsequios", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
        
            FacesContext.getCurrentInstance().getExternalContext().redirect("RegaloListaSolicitudes.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(GenerarRequerimientoRegalosBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
