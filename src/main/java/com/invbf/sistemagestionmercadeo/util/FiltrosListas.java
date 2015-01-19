/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;


import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Categoria;
import com.invbf.sistemagestionmercadeo.entity.Denominacion;
import com.invbf.sistemagestionmercadeo.entity.Perfil;
import com.invbf.sistemagestionmercadeo.entity.Propositoentrega;
import com.invbf.sistemagestionmercadeo.entity.Tipobono;
import com.invbf.sistemagestionmercadeo.entity.Tipodocumento;
import com.invbf.sistemagestionmercadeo.entity.Tipotarea;
import com.invbf.sistemagestionmercadeo.facade.AdminFacade;
import com.invbf.sistemagestionmercadeo.facade.MarketingUserFacade;
import com.invbf.sistemagestionmercadeo.facade.impl.AdminFacadeImpl;
import com.invbf.sistemagestionmercadeo.facade.impl.MarketingUserFacadeImpl;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Celula4
 */
@ManagedBean
@ViewScoped
public class FiltrosListas {

    AdminFacade adminFacade;
    MarketingUserFacade marketingUserFacade;
    List<String> listValues;
    

    public FiltrosListas() {
    }

    @PostConstruct
    public void init() {
        adminFacade = new AdminFacadeImpl();
        marketingUserFacade = new MarketingUserFacadeImpl();
        listValues = new ArrayList<String>();
    }

    public List<String> getPerfiles() {
        List<String> lista = new ArrayList<String>();
        List<Perfil> perfiles = adminFacade.findAllPerfiles();
        for (Perfil p : perfiles) {
            lista.add(p.getNombre());
        }
        return lista;
    }

    public List<String> getCategorias() {
        List<String> lista = new ArrayList<String>();
        List<Categoria> perfiles = marketingUserFacade.findAllCategorias();
        for (Categoria p : perfiles) {
            lista.add(p.getNombre());
        }
        return lista;
    }

    public List<String> getEstados() {
        List<String> lista = new ArrayList<String>();
        lista.add("POR INICIAR");
        lista.add("ACTIVO");
        lista.add("VENCIDO");
        return lista;
    }

    public List<String> getTipostareas() {
        List<String> lista = new ArrayList<String>();
        List<Tipotarea> perfiles = marketingUserFacade.findAllTipotarea();
        for (Tipotarea p : perfiles) {
            lista.add(p.getNombre());
        }
        return lista;
    }

    public List<String> getTipoident() {
        List<String> lista = new ArrayList<String>();
        List<Tipodocumento> perfiles = marketingUserFacade.findAllTipoDocumentos();
        for (Tipodocumento p : perfiles) {
            lista.add(p.getNombre());
        }
        return lista;

    }
    
    public List<String> getPropositos() {
        List<String> lista = new ArrayList<String>();
        List<Propositoentrega> propositoentregas = adminFacade.findAllPropositosentrega();
        for (Propositoentrega p : propositoentregas) {
            lista.add(p.getNombre());
        }
        return lista;
    }
    
    public List<String> getTipobono() {
        List<String> lista = new ArrayList<String>();
        List<Tipobono> tipobonos = adminFacade.findAllTiposbonos();
        for (Tipobono tb : tipobonos) {
            lista.add(tb.getNombre());
        }
        return lista;
    }
    
    public List<String> getCasinos(){
        List<String> lista = new ArrayList<String>();
        List<Casino> casinos = adminFacade.findAllCasinos();
        for (Casino casino : casinos) {
            lista.add(casino.getNombre());
        }
        return lista;
    }
    
    public List<String> getDenominaciones(){
        List<String> lista = new ArrayList<String>();
        List<Denominacion> denominaciones = adminFacade.findAllDenominaciones();
        for (Denominacion denominacion : denominaciones) {
            lista.add(denominacion.getValor()+"");
        }
        return lista;
    }
    
    public List<String> getEstadoSolicitudBonos(){
        List<String> lista = new ArrayList<String>();
        lista.add("APROBADA");
        lista.add("CREADA");
        lista.add("PREAPROBADA");
        return lista;
    }
    
    public List<String> getEstadoOrdenEntrada(){
        List<String> lista = new ArrayList<String>();
        lista.add("CREADA");
        lista.add("SOLICITADA");
        lista.add("ACEPTADA");
        return lista;
    }
    
    public List<String> getEstadoOrdenSalida(){
        List<String> lista = new ArrayList<String>();
        lista.add("CREADA");
        lista.add("ACEPTADA");
        return lista;
    }

    public List<String> getListValues() {
        return listValues;
    }

    public void setListValues(List<String> listValues) {
        this.listValues = listValues;
    }
    
}
