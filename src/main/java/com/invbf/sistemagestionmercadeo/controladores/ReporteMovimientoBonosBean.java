/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Bono;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.util.AnalisisBono;
import com.invbf.sistemagestionmercadeo.util.CasinoBoolean;
import com.invbf.sistemagestionmercadeo.util.PropositosBoolean;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
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
public class ReporteMovimientoBonosBean {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<Bono> bonosAnalizar;
    private List<CasinoBoolean> casinos;
    private Date hasta;
    private Date desde;
    private List<PropositosBoolean> propositos;
    private String nombre;
    private String apellidos;
    private String identificacion;
    private boolean todoscas;

    private List<AnalisisBono> promocional;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("reportes");
        
        if (!sessionBean.perfilViewMatch("Reportes")) {
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        sessionBean.revisarEstadoBonos();
        Calendar now = Calendar.getInstance();
        Calendar monthago = Calendar.getInstance();
        monthago.add(Calendar.MONTH, -1);
        hasta = now.getTime();
        desde = monthago.getTime();
        bonosAnalizar = sessionBean.marketingUserFacade.getAllBonosFecha(desde, hasta);
        List<Casino> casinosNormales = sessionBean.adminFacade.findAllCasinos();
        casinos = new ArrayList<CasinoBoolean>();
        for (Casino casinosNormale : casinosNormales) {
            casinos.add(new CasinoBoolean(casinosNormale, true));
        }
        analizarBonos();
    }

    public void buscarBonosPorCasinosYFecha() {
        bonosAnalizar = new ArrayList<Bono>();

        bonosAnalizar = sessionBean.marketingUserFacade.getAllBonosFecha(desde, hasta);
        for (Iterator<Bono> iterator = bonosAnalizar.iterator(); iterator.hasNext();) {
            Bono next = iterator.next();
            if (nombre != null && !nombre.equals("")) {
                if (!next.getCliente().getNombres().contains(nombre.toUpperCase())) {
                    iterator.remove();
                    continue;
                }
            }
            if (apellidos != null && !apellidos.equals("")) {
                if (!next.getCliente().getApellidos().contains(apellidos.toUpperCase())) {
                    iterator.remove();
                    continue;
                }
            }
            boolean ok = false;
            boolean oneselected = false;
            for (CasinoBoolean casino : casinos) {
                if (casino.isSelected()) {
                    oneselected = true;
                    if (casino.getCasino().getIdCasino().equals(next.getCasino().getIdCasino()) ){
                        ok = true;
                    }
                }
            }
            if(!ok&&oneselected){
                iterator.remove();
            }
        }
        analizarBonos();
    }

    public List<Bono> getBonosAnalizar() {
        return bonosAnalizar;
    }

    public void setBonosAnalizar(List<Bono> bonosAnalizar) {
        this.bonosAnalizar = bonosAnalizar;
    }

    public List<CasinoBoolean> getCasinos() {
        return casinos;
    }

    public void setCasinos(List<CasinoBoolean> casinos) {
        this.casinos = casinos;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public List<AnalisisBono> getPromocional() {
        return promocional;
    }

    public void setPromocional(List<AnalisisBono> promocional) {
        this.promocional = promocional;
    }

    private void analizarBonos() {
        promocional = new ArrayList<AnalisisBono>();
        for (Bono bono : bonosAnalizar) {
            if (!promocional.contains(new AnalisisBono(bono.getPropositosEntregaid().getNombre()))) {
                promocional.add(new AnalisisBono(bono.getPropositosEntregaid().getNombre()));
            }
            AnalisisBono ab = promocional.get(promocional.indexOf(new AnalisisBono(bono.getPropositosEntregaid().getNombre())));
            ab.addBono(bono);
        }
    }

    public List<PropositosBoolean> getPropositos() {
        return propositos;
    }

    public void setPropositos(List<PropositosBoolean> propositos) {
        this.propositos = propositos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public boolean isTodoscas() {
        return todoscas;
    }

    public void setTodoscas(boolean todoscas) {
        this.todoscas = todoscas;
    }

}
