/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Bono;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Denominacion;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import com.invbf.sistemagestionmercadeo.util.Notificador;
import java.io.IOException;
import java.io.Serializable;
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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 *
 * @author ivan
 */
@ManagedBean
@ViewScoped
public class BonoCanjeCliente implements Serializable {
    
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<Casino> casinos;
    private Casino casinoSelected;
    private List<Bono> bonosCasinoEntregados;
    private List<Usuario> autorizadores;
    private Bono elemento;
    private String nombres;
    private String apellidos;
    private String nombresc;
    private String apellidosc;
    private String correo;
    private String identificacion;
    private String consecutivo;
    private String telefono1;
    private String telefono2;
    private List<com.invbf.sistemagestionmercadeo.util.Denominacion> denominaciones;
    private Denominacion denominacion;
    
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    
    public BonoCanjeCliente() {
        
    }
    
    @PostConstruct
    public void init() {
        try {
            sessionBean.checkUsuarioConectado();
            sessionBean.setActive("requisiciones");
            if (!sessionBean.perfilViewMatch("Canjearbono") && !sessionBean.perfilViewMatch("Autorizarbono") && !sessionBean.perfilViewMatch("VerbonosporAutorizar")) {
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
                } catch (IOException ex) {
                }
            }
            sessionBean.revisarEstadoBonos();
            System.out.println(sessionBean.getUsuario().getCasinoList());
            casinos = sessionBean.getUsuario().getCasinoList();
            bonosCasinoEntregados = new ArrayList<Bono>();
            casinoSelected = new Casino();
            autorizadores = new ArrayList<Usuario>();
            List<Denominacion> den = sessionBean.adminFacade.findAllDenominaciones();
            denominaciones = new ArrayList<com.invbf.sistemagestionmercadeo.util.Denominacion>();
            for (Denominacion den1 : den) {
                denominaciones.add(new com.invbf.sistemagestionmercadeo.util.Denominacion(den1.getId(), getAsString(den1.getValor())));
            }
            denominacion = new Denominacion();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void buscarBonosValidadosPorCasino() {
        System.out.println("Buscar bonos");
        System.out.println("casino " + casinoSelected.getIdCasino());
        
        casinoSelected = casinos.get(casinos.indexOf(new Casino(casinoSelected.getIdCasino())));
        bonosCasinoEntregados = sessionBean.marketingUserFacade.getBonosPorAtributos("EN SALA", casinoSelected, consecutivo, denominacion);
        
        autorizadores = sessionBean.adminFacade.findUsuariosAutorizadoresCasino(casinoSelected);
        if (bonosCasinoEntregados.isEmpty()) {
            FacesUtil.addErrorMessage("No se encontró ningun bono", "Revise la información");
        } else {
            FacesUtil.addInfoMessage("Se encontró el bono", "");
            for (Bono bono : bonosCasinoEntregados) {
                bono.setAutorizador(new Usuario());
            }
        }
        System.out.println(bonosCasinoEntregados.size());
        elemento = new Bono();
        elemento.setAutorizador(new Usuario());
        
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
        
        Calendar nowDate = Calendar.getInstance();
        elemento.setFechaEntrega(nowDate.getTime());
        elemento.setValidador(sessionBean.getUsuario());
        sessionBean.marketingUserFacade.guardaBono(elemento);
        elemento.setAutorizador(sessionBean.adminFacade.findUsuarios(elemento.getAutorizador().getIdUsuario()));
        String body = "";
        body += "El bono con consecutivo: " + elemento.getConsecutivo() + ", de denominación: " + getAsString(elemento.getDenominacion().getValor());
        body += ", asignado al cliente: " + elemento.getNombreCliente();
        body += " ha sido autorizado por " + elemento.getAutorizador().getNombre() + ".";
        Notificador.notificar(Notificador.EMAIL_CLIENTE, body, "BONO AUTORIZADO PARA CANJE", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
        
        FacesUtil.addInfoMessage("Bono canjeado con exito", "Consecutivo " + elemento.getConsecutivo());
        elemento = new Bono();
        bonosCasinoEntregados = new ArrayList<Bono>();
        elemento.setAutorizador(new Usuario());
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
        elemento = new Bono();
        bonosCasinoEntregados = new ArrayList<Bono>();
        elemento.setAutorizador(new Usuario());
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
    
    public List<Usuario> getAutorizadores() {
        return autorizadores;
    }
    
    public void setAutorizadores(List<Usuario> autorizadores) {
        this.autorizadores = autorizadores;
    }
    
    public List<com.invbf.sistemagestionmercadeo.util.Denominacion> getDenominaciones() {
        return denominaciones;
    }
    
    public void setDenominaciones(List<com.invbf.sistemagestionmercadeo.util.Denominacion> denominaciones) {
        this.denominaciones = denominaciones;
    }
    
    public Denominacion getDenominacion() {
        return denominacion;
    }
    
    public void setDenominacion(Denominacion denominacion) {
        this.denominacion = denominacion;
    }
    
    public String getAsString(Object o) {
        String numberseparated = "";
        String iPartS = "";
        boolean tieneelsimbolo = false;
        if (o instanceof Double) {
            double number = (Double) o;
            Long iPart = (long) number;
            iPartS = iPart.toString();
        } else if (o instanceof Float) {
            float number = (Float) o;
            Long iPart = (long) number;
            iPartS = iPart.toString();
        } else {
            String number;
            if (o instanceof Integer) {
                number = ((Integer) o).toString();
            } else {
                number = (String) o;
            }
            System.out.println("numero " + number);
            if (number.lastIndexOf(".") != -1) {
                iPartS = number.substring(0, number.lastIndexOf("."));
            } else {
                iPartS = number;
            }
        }
        boolean milesima = true;
        
        while (true) {
            System.out.println("asi va el numero " + numberseparated);
            System.out.println("y queda esto " + iPartS);
            if (iPartS.length() == 0) {
                break;
            } else if (iPartS.length() <= 3) {
                numberseparated = iPartS + numberseparated;
                iPartS = "";
            } else {
                if (milesima) {
                    numberseparated = "." + iPartS.substring(iPartS.length() - 3) + numberseparated;
                    iPartS = iPartS.substring(0, iPartS.length() - 3);
                    milesima = false;
                } else {
                    numberseparated = "'" + iPartS.substring(iPartS.length() - 3) + numberseparated;
                    iPartS = iPartS.substring(0, iPartS.length() - 3);
                    milesima = true;
                }
            }
        }
        System.out.println("asi quedo sin parte real " + numberseparated);
        System.out.println("asi quedo con parte real " + numberseparated);
        System.out.println();
        System.out.println();
        return numberseparated;
    }
}
