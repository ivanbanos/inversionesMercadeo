/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Bono;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
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
public class BonoCanjeCliente {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<Casino> casinos;
    private Casino casinoSelected;
    private List<Bono> bonosCasinoEntregados;
    private Bono elemento;
    private String nombres;
    private String apellidos;
    private String nombresc;
    private String apellidosc;
    private String correo;
    private String identificacion;
    private String consecutivo;private String telefono1;private String telefono2;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public BonoCanjeCliente() {

    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("requisiciones");
        if (!sessionBean.perfilViewMatch("Recibirbono")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        sessionBean.revisarEstadoBonos();
        casinos = sessionBean.getUsuario().getCasinoList();
        bonosCasinoEntregados = new ArrayList<Bono>();
        casinoSelected = new Casino();
    }

    public void buscarBonosValidadosPorCasino() {
        System.out.println("Buscar bonos");
        System.out.println("casino " + casinoSelected.getIdCasino());
        casinoSelected = casinos.get(casinos.indexOf(new Casino(casinoSelected.getIdCasino())));
        bonosCasinoEntregados = sessionBean.marketingUserFacade.getBonosPorAtributos("ENTREGADO CLIENTE", casinoSelected, nombres, apellidos, identificacion, consecutivo);
        bonosCasinoEntregados.addAll(sessionBean.marketingUserFacade.getBonosPorAtributos("VALIDADO", casinoSelected, nombres, apellidos, identificacion, consecutivo));
        bonosCasinoEntregados.addAll(sessionBean.marketingUserFacade.getBonosPorAtributos("AUTORIZADO", casinoSelected, nombres, apellidos, identificacion, consecutivo));
        System.out.println(bonosCasinoEntregados.size());
        elemento = new Bono();
    }

    public List<Casino> getCasinos() {
        return casinos;
    }

    public void setCasinos(List<Casino> casinos) {
        this.casinos = casinos;
    }

    public Casino getCasinoSelected() {
        return casinoSelected;
    }

    public void setCasinoSelected(Casino casinoSelected) {
        this.casinoSelected = casinoSelected;
    }

    public void canjear() {
        elemento.setEstado("CANJEADO");
        DateFormat df = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
        DateFormat df2 = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
        TimeZone timeZone = TimeZone.getTimeZone("GMT-5");
        df.setTimeZone(timeZone);
        Calendar nowDate = Calendar.getInstance();
        elemento.setFechaEntrega(nowDate.getTime());
        sessionBean.marketingUserFacade.guardaBono(elemento);
        bonosCasinoEntregados = sessionBean.marketingUserFacade.getBonosPorEstadoYCasino("ENTREGADO CLIENTE", casinoSelected);
        FacesUtil.addInfoMessage("Bono canjeado con exito", "Consecutivo " + elemento.getConsecutivo());
        buscarBonosValidadosPorCasino();

    }

    public void canjearpro() {
        elemento.setEstado("CANJEADO");
        DateFormat df = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
        DateFormat df2 = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
        TimeZone timeZone = TimeZone.getTimeZone("GMT-5");
        df.setTimeZone(timeZone);
        Calendar nowDate = Calendar.getInstance();
        elemento.setFechaEntrega(nowDate.getTime());
        sessionBean.marketingUserFacade.guardaBono(elemento);
        bonosCasinoEntregados = sessionBean.marketingUserFacade.getBonosPorEstadoYCasino("ENTREGADO CLIENTE", casinoSelected);
        FacesUtil.addInfoMessage("Bono canjeado con exito", "Consecutivo " + elemento.getConsecutivo());
        Cliente cliente = new Cliente();
        if (nombresc == null || nombresc.equals("") || apellidos == null || apellidos.equals("")) {
        } else {
            cliente.setNombres(nombresc);
            cliente.setApellidos(apellidosc);
            cliente.setCorreo(correo);
            cliente.setTelefono1(telefono1);
            cliente.setTelefono2(telefono2);
            cliente.setIdCasinoPreferencial(casinoSelected);
            sessionBean.marketingUserFacade.guardarClientesSinCategoria(cliente);
        }
        buscarBonosValidadosPorCasino();

    }

    public void validar() {
        elemento.setEstado("VALIDADO");
        elemento.setValidador(sessionBean.getUsuario());

        DateFormat df = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
        DateFormat df2 = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
        TimeZone timeZone = TimeZone.getTimeZone("GMT-5");
        df.setTimeZone(timeZone);
        Calendar nowDate = Calendar.getInstance();
        elemento.setFechaValidacion(nowDate.getTime());
        sessionBean.marketingUserFacade.guardaBono(elemento);
        bonosCasinoEntregados = sessionBean.marketingUserFacade.getBonosPorEstadoYCasino("ENTREGADO CLIENTE", casinoSelected);
        FacesUtil.addInfoMessage("Bono validado con exito", "Consecutivo " + elemento.getConsecutivo());
        buscarBonosValidadosPorCasino();

    }

    public void autorizar() {
        elemento.setEstado("AUTORIZADO");
        elemento.setAutorizador(sessionBean.getUsuario());
        DateFormat df = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
        DateFormat df2 = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
        TimeZone timeZone = TimeZone.getTimeZone("GMT-5");
        df.setTimeZone(timeZone);
        Calendar nowDate = Calendar.getInstance();
        elemento.setFechaEntrega(nowDate.getTime());
        sessionBean.marketingUserFacade.guardaBono(elemento);
        bonosCasinoEntregados = sessionBean.marketingUserFacade.getBonosPorEstadoYCasino("ENTREGADO CLIENTE", casinoSelected);
        FacesUtil.addInfoMessage("Bono autorizado con exito", "Consecutivo " + elemento.getConsecutivo());
        buscarBonosValidadosPorCasino();

    }

    public List<Bono> getBonosCasinoEntregados() {
        return bonosCasinoEntregados;
    }

    public void setBonosCasinoEntregados(List<Bono> bonosCasinoEntregados) {
        this.bonosCasinoEntregados = bonosCasinoEntregados;
    }

    public Bono getElemento() {
        return elemento;
    }

    public void setElemento(Bono elemento) {
        this.elemento = elemento;
    }

    public void buscarBonosPorCliente() {
        System.out.println("Buscar bonos");
        System.out.println("casino " + casinoSelected.getIdCasino());
        casinoSelected = casinos.get(casinos.indexOf(new Casino(casinoSelected.getIdCasino())));
        bonosCasinoEntregados = sessionBean.marketingUserFacade.getBonosPorAtributos("ENTREGADO CLIENTE", casinoSelected, nombres, apellidos, identificacion, consecutivo);
        bonosCasinoEntregados.addAll(sessionBean.marketingUserFacade.getBonosPorAtributos("VALIDADO", casinoSelected, nombres, apellidos, identificacion, consecutivo));
        bonosCasinoEntregados.addAll(sessionBean.marketingUserFacade.getBonosPorAtributos("AUTORIZADO", casinoSelected, nombres, apellidos, identificacion, consecutivo));
        System.out.println(bonosCasinoEntregados.size());
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
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

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getNombresc() {
        return nombresc;
    }

    public void setNombresc(String nombresc) {
        this.nombresc = nombresc;
    }

    public String getApellidosc() {
        return apellidosc;
    }

    public void setApellidosc(String apellidosc) {
        this.apellidosc = apellidosc;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }
    
}
