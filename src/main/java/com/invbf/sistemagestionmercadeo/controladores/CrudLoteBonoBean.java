/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Bononofisico;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Denominacion;
import com.invbf.sistemagestionmercadeo.entity.Lotebono;
import com.invbf.sistemagestionmercadeo.entity.Tipobono;
import com.invbf.sistemagestionmercadeo.exceptions.ExistenBonosFisicosException;
import com.invbf.sistemagestionmercadeo.exceptions.LoteBonosExistenteException;
import com.invbf.sistemagestionmercadeo.util.ConvertidorConsecutivo;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import com.invbf.sistemagestionmercadeo.util.LoteBonoDTO;
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
public class CrudLoteBonoBean  implements Serializable{

    private List<LoteBonoDTO> lista;
    private List<LoteBonoDTO> listaFiltrada;
    private Lotebono elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    private List<Casino> casinos;
    private List<Denominacion> denominaciones;
    private List<Tipobono> tiposbonos;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public CrudLoteBonoBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("lotesdebonos");
        if (!sessionBean.perfilViewMatch("LoteBono")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        List<Lotebono> lotes = sessionBean.marketingUserFacade.getAllLotesBonos();
        lista = new ArrayList<LoteBonoDTO>();
        for (Lotebono lote : lotes) {
            lista.add(new LoteBonoDTO(lote.getId(), lote.getDesde(), lote.getHasta(), lote.getBononofisicoList(), lote.getDenominacion(), lote.getTipoBono(), lote.getIdCasino()));
        }
        elemento = new Lotebono();
        elemento.setDenominacion(new Denominacion());
        elemento.setTipoBono(new Tipobono());
        elemento.setIdCasino(new Casino());
        elemento.setBononofisicoList(new ArrayList<Bononofisico>());
        casinos = sessionBean.adminFacade.findAllCasinos();
        denominaciones = sessionBean.adminFacade.findAllDenominaciones();
        tiposbonos = sessionBean.adminFacade.findAllTiposbonos();
    }

    public List<LoteBonoDTO> getLista() {
        return lista;
    }

    public void setLista(List<LoteBonoDTO> lista) {
        this.lista = lista;
    }

    public Lotebono getElemento() {
        return elemento;
    }

    public void setElemento(Lotebono elemento) {
        this.elemento = elemento;
    }

    public void delete() {
        try {
            sessionBean.marketingUserFacade.borrarLote(elemento);
            FacesUtil.addInfoMessage("Lote eliminado con exito!", "");
            List<Lotebono> lotes = sessionBean.marketingUserFacade.getAllLotesBonos();
            lista = new ArrayList<LoteBonoDTO>();
            for (Lotebono lote : lotes) {
                lista.add(new LoteBonoDTO(lote.getId(), lote.getDesde(), lote.getHasta(), lote.getBononofisicoList(), lote.getDenominacion(), lote.getTipoBono(), lote.getIdCasino()));
            }
            elemento = new Lotebono();
            elemento.setDenominacion(new Denominacion());
            elemento.setTipoBono(new Tipobono());
        } catch (ExistenBonosFisicosException ex) {
            FacesUtil.addErrorMessage("No se eliminó el lote de bonos", "Existen bonos fisicos");
        }
    }

    public void guardar() {
        try {
            elemento.setDesde("0000-A");
            elemento.setHasta("0000-A");
            sessionBean.marketingUserFacade.guardarLote(elemento);
            FacesUtil.addInfoMessage("Lote creado con exito!", "");
            List<Lotebono> lotes = sessionBean.marketingUserFacade.getAllLotesBonos();
            lista = new ArrayList<LoteBonoDTO>();
            for (Lotebono lote : lotes) {
                lista.add(new LoteBonoDTO(lote.getId(), lote.getDesde(), lote.getHasta(), lote.getBononofisicoList(), lote.getDenominacion(), lote.getTipoBono(), lote.getIdCasino()));
            }
            elemento = new Lotebono();
            elemento.setDenominacion(new Denominacion());
            elemento.setTipoBono(new Tipobono());
            elemento.setIdCasino(new Casino());
            elemento.setBononofisicoList(new ArrayList<Bononofisico>());
        } catch (LoteBonosExistenteException ex) {
            FacesUtil.addErrorMessage("No se pudo crear el lote de bonos", "Existe un lote de bonos con la misma combinacion de casino, denominación y tipo de bono");
        }
    }

    public Casino getCasinoById(Integer idCasino) {
        int casinoIndex = casinos.indexOf(new Casino(idCasino));
        if (casinoIndex != -1) {
            return casinos.get(casinoIndex);
        }
        return new Casino();
    }

    public List<Casino> getCasinos() {
        return casinos;
    }

    public void setCasinos(List<Casino> casinos) {
        this.casinos = casinos;
    }

    public List<Denominacion> getDenominaciones() {
        return denominaciones;
    }

    public void setDenominaciones(List<Denominacion> denominaciones) {
        this.denominaciones = denominaciones;
    }

    public List<Tipobono> getTiposbonos() {
        return tiposbonos;
    }

    public void setTiposbonos(List<Tipobono> tiposbonos) {
        this.tiposbonos = tiposbonos;
    }

    public List<LoteBonoDTO> getListaFiltrada() {
        return listaFiltrada;
    }

    public void setListaFiltrada(List<LoteBonoDTO> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }

}
