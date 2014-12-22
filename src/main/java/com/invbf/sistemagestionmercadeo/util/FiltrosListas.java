/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;


import com.invbf.sistemagestionmercadeo.entity.Categoria;
import com.invbf.sistemagestionmercadeo.entity.Perfil;
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
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Celula4
 */
@ManagedBean
@RequestScoped
public class FiltrosListas {

    AdminFacade adminFacade;
    MarketingUserFacade marketingUserFacade;

    public FiltrosListas() {
    }

    @PostConstruct
    public void init() {
        adminFacade = new AdminFacadeImpl();
        marketingUserFacade = new MarketingUserFacadeImpl();
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
}
