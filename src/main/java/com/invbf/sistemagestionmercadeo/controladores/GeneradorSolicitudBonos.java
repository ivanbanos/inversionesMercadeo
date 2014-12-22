/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Area;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Propositoentrega;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentrega;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregacliente;
import com.invbf.sistemagestionmercadeo.entity.Tipobono;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.entity.Usuariodetalle;
import com.invbf.sistemagestionmercadeo.util.ClienteSGBDTO;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
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
public class GeneradorSolicitudBonos {

    private Solicitudentrega elemento;
    private List<Casino> casinos;
    private List<Tipobono> tiposbonos;
    private List<Propositoentrega> propositosentrega;
    private List<Usuario> usuarios;
    private List<Area> areas;
    private List<Cliente> clientessgbs;
    private String nombres;
    private String apellidos;
    private List<Cliente> selectedClientessgbs;
    private List<ClienteSGBDTO> clientes;
    private List<Integer> clientesABorrar;

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public GeneradorSolicitudBonos() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("solicitudbonos");
        if (!sessionBean.perfilViewMatch("GenerarSolicitudBono")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }

        System.out.println("Buscando info de la solictud si existe");
        clientes = new ArrayList<ClienteSGBDTO>();
        clientesABorrar = new ArrayList<Integer>();
        if (sessionBean.getAttributes().containsKey("idSolicitudentrega") && (Integer) sessionBean.getAttributes().get("idSolicitudentrega") != 0) {
            Integer id = (Integer) sessionBean.getAttributes().get("idSolicitudentrega");
            elemento = sessionBean.marketingUserFacade.getSolicitudbono(id);
            for(Solicitudentregacliente sec :elemento.getSolicitudentregaclienteList()){
                clientes.add(new ClienteSGBDTO(sec.getValorTotal(), sec.getCliente(), sec.getAreaid()));
            }
        } else {
            try {
                elemento = new Solicitudentrega();
                elemento.setEstado("EN CREACION");
                DateFormat df = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
                DateFormat df2 = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
                TimeZone timeZone = TimeZone.getTimeZone("GMT-5");
                df.setTimeZone(timeZone);
                Calendar nowDate = Calendar.getInstance();
                nowDate.setTime(df2.parse(df.format(nowDate.getTime())));
                elemento.setFecha(nowDate.getTime());
                elemento.setIdCasino(sessionBean.getUsuario().getIdCasino());
                elemento.setPropositoEntrega(new Propositoentrega());
                elemento.setSolicitante(sessionBean.getUsuario());
                elemento.setTipoBono(new Tipobono());
                elemento.setSolicitudentregaclienteList(new ArrayList<Solicitudentregacliente>());
            } catch (ParseException ex) {
                Logger.getLogger(GeneradorSolicitudBonos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        casinos = sessionBean.adminFacade.findAllCasinos();
        tiposbonos = sessionBean.adminFacade.findAllTiposbonos();
        usuarios = sessionBean.adminFacade.findAllUsuarios();
        propositosentrega = sessionBean.adminFacade.findAllPropositosentrega();
        areas = sessionBean.adminFacade.findAllAreas();
        clientessgbs = new ArrayList<Cliente>();
        selectedClientessgbs = new ArrayList<Cliente>();
        busquedaClientes();

    }

    public Solicitudentrega getElemento() {
        return elemento;
    }

    public void setElemento(Solicitudentrega elemento) {

        boolean cambiocasino = this.elemento.getIdCasino().equals(elemento.getIdCasino());

        this.elemento = elemento;
        if (cambiocasino) {
            this.elemento.getSolicitudentregaclienteList().clear();
            busquedaClientes();
        }
    }

    public void guardar() {
        if (elemento.getId() == null || elemento.getId().equals(0)) {
            elemento.setEstado("CREADA");
            List<Solicitudentregacliente> solicitudentregaclienteses = new ArrayList<Solicitudentregacliente>();
            for (ClienteSGBDTO clientesGBT : clientes) {
                Solicitudentregacliente sec = new Solicitudentregacliente();
                sec.setAreaid(clientesGBT.getAreaid());
                sec.setCliente(clientesGBT.getClientessgb());
                sec.setValorTotal(clientesGBT.getValorTotal());
                solicitudentregaclienteses.add(sec);
            }
            elemento.setSolicitudentregaclienteList(solicitudentregaclienteses);
            elemento = sessionBean.marketingUserFacade.guardarSolicitudentrega(elemento, clientesABorrar);
            sessionBean.registrarlog(null, null, "Generada solicitud Usuario:" + sessionBean.getUsuario().getNombreUsuario());
            FacesUtil.addInfoMessage("Solicitud guardada con exito!", "Notificación enviada");
        } else {
            elemento.setEstado("CREADA");

            System.out.println("por que intenta guardar un area");
            
            List<Solicitudentregacliente> solicitudentregaclienteses = new ArrayList<Solicitudentregacliente>();
            for (ClienteSGBDTO clientesGBT : clientes) {
                Solicitudentregacliente sec = new Solicitudentregacliente();
                sec.setAreaid(clientesGBT.getAreaid());
                sec.setCliente(clientesGBT.getClientessgb());
                sec.setValorTotal(clientesGBT.getValorTotal());
                solicitudentregaclienteses.add(sec);
            }
            elemento.setSolicitudentregaclienteList(solicitudentregaclienteses);

            System.out.println("entremos a ver");
            sessionBean.marketingUserFacade.guardarSolicitudentrega(elemento, clientesABorrar);
            sessionBean.registrarlog(null, null, "Generada solicitud Usuario:" + sessionBean.getUsuario().getNombreUsuario());
            FacesUtil.addInfoMessage("Solicitud guardada con exito!", "Notificación enviada");
        }
        sessionBean.getAttributes().put("idSolicitudentrega", elemento.getId());
    }

    public Casino getCasinoById(Integer idCasino) {
        int casinoIndex = casinos.indexOf(new Casino(idCasino));
        if (casinoIndex != -1) {
            return casinos.get(casinoIndex);
        }
        return new Casino();
    }

    public String getNombreDeUsuario(Integer id) {
        return sessionBean.sessionFacade.getNombreDeUsuario(id);
    }

    public List<Casino> getCasinos() {
        return casinos;
    }

    public void setCasinos(List<Casino> casinos) {
        this.casinos = casinos;
    }

    public List<Tipobono> getTiposbonos() {
        return tiposbonos;
    }

    public void setTiposbonos(List<Tipobono> tiposbonos) {
        this.tiposbonos = tiposbonos;
    }

    public List<Propositoentrega> getPropositosentrega() {
        return propositosentrega;
    }

    public void setPropositosentrega(List<Propositoentrega> propositosentrega) {
        this.propositosentrega = propositosentrega;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Usuario getUsuarioById(Integer idUsuario) {
        int casinoIndex = usuarios.indexOf(new Usuario(idUsuario));
        if (casinoIndex != -1) {
            return usuarios.get(casinoIndex);
        }
        return new Usuario();
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
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

    public List<Cliente> getClientessgbs() {
        return clientessgbs;
    }

    public void setClientessgbs(List<Cliente> clientessgbs) {
        this.clientessgbs = clientessgbs;
    }

    public void busquedaClientes() {
        clientessgbs = sessionBean.adminFacade.findClientessgbByCasino(elemento.getIdCasino());
    }

    public List<Cliente> getSelectedClientessgbs() {
        return selectedClientessgbs;
    }

    public void setSelectedClientessgbs(List<Cliente> selectedClientessgbs) {
        this.selectedClientessgbs = selectedClientessgbs;
    }

    public void creadorClientesSolicitud() {
        for (Cliente selected : selectedClientessgbs) {
            boolean existe = false;
            for (ClienteSGBDTO sec : clientes) {
                if (sec.getClientessgb().equals(selected)) {
                    existe = true;
                    break;
                }
            }
            System.out.println("Este cliente, " + selected.getNombres() + " " + selected.getApellidos() + ", existes? " + existe);
            if (!existe) {

                ClienteSGBDTO sec = new ClienteSGBDTO();
                sec.setClientessgb(selected);
                sec.setAreaid(new Area());
                sec.setValorTotal(0f);
                clientes.add(sec);
            }
        }
    }

    public void quitarCliente(Integer i) {
        System.out.println("id " + i);
        for (Iterator<ClienteSGBDTO> iterator = clientes.iterator(); iterator.hasNext();) {
            ClienteSGBDTO sec = iterator.next();
            System.out.println("id de este" + sec.getClientessgb().getIdCliente());
            if (sec.getClientessgb().getIdCliente().equals(i)) {
                iterator.remove();
                clientesABorrar.add(sec.getClientessgb().getIdCliente());
                break;
            }
        }
    }

    public List<ClienteSGBDTO> getClientes() {
        return clientes;
    }

    public void setClientes(List<ClienteSGBDTO> clientes) {
        this.clientes = clientes;
    }


}
