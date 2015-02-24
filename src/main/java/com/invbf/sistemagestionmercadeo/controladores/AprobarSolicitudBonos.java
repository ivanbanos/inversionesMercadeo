/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Area;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Controlsalidabono;
import com.invbf.sistemagestionmercadeo.entity.ControlsalidabonosHasLotesbonos;
import com.invbf.sistemagestionmercadeo.entity.ControlsalidabonosHasLotesbonosHasClientes;
import com.invbf.sistemagestionmercadeo.entity.ControlsalidabonosHasLotesbonosPK;
import com.invbf.sistemagestionmercadeo.entity.Lotebono;
import com.invbf.sistemagestionmercadeo.entity.Propositoentrega;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentrega;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregacliente;
import com.invbf.sistemagestionmercadeo.entity.Tipobono;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.entity.Usuariodetalle;
import com.invbf.sistemagestionmercadeo.util.ClienteMonto;
import com.invbf.sistemagestionmercadeo.util.DenoinacionCant;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import com.invbf.sistemagestionmercadeo.util.MatematicaAplicada;
import com.invbf.sistemagestionmercadeo.util.Notificador;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
public class AprobarSolicitudBonos implements Serializable {

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
    private List<Solicitudentregacliente> solicitudentregaclienteses;

    private Controlsalidabono control;
    private List<ClienteMonto> clientesMontos;
    private List<Lotebono> lotesSol;
    private Usuario usuario;
    private Usuariodetalle usuariosdetalles;
    private Float totalEntregar;
    private List<DenoinacionCant> denominacionCant;

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public AprobarSolicitudBonos() {
    }

    @PostConstruct
    public void init() {
        try {
            sessionBean.checkUsuarioConectado();
            sessionBean.setActive("solicitudbonos");
            if (!sessionBean.perfilViewMatch("GenerarSolicitudBono")) {
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
                } catch (IOException ex) {
                }
            }

            System.out.println("Buscando info de la solictud si existe");
            if (sessionBean.getAttributes("idSolicitudentrega") != null && (Integer) sessionBean.getAttributes("idSolicitudentrega") != 0) {
                Integer id = (Integer) sessionBean.getAttributes("idSolicitudentrega");
                elemento = sessionBean.marketingUserFacade.getSolicitudbono(id);
                solicitudentregaclienteses = elemento.getSolicitudentregaclienteList();
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
                    elemento.setPropositoEntrega(new Propositoentrega());
                    elemento.setSolicitante(sessionBean.getUsuario());
                    elemento.setTipoBono(new Tipobono());
                    elemento.setSolicitudentregaclienteList(new ArrayList<Solicitudentregacliente>());
                    solicitudentregaclienteses = new ArrayList<Solicitudentregacliente>();
                } catch (ParseException ex) {
                    Logger.getLogger(GeneradorSolicitudBonos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            casinos = sessionBean.getUsuario().getCasinoList();
            tiposbonos = sessionBean.adminFacade.findAllTiposbonos();
            usuarios = sessionBean.adminFacade.findAllUsuarios();
            propositosentrega = sessionBean.adminFacade.findAllPropositosentrega();
            areas = sessionBean.adminFacade.findAllAreas();
            clientessgbs = new ArrayList<Cliente>();
            selectedClientessgbs = new ArrayList<Cliente>();
            busquedaClientes();
            if (!elemento.getControlsalidabonoList().isEmpty()) {
                control = elemento.getControlsalidabonoList().get(0);
                DateFormat df = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
                DateFormat df2 = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
                TimeZone timeZone = TimeZone.getTimeZone("GMT-5");
                df.setTimeZone(timeZone);
                Calendar nowDate = Calendar.getInstance();
                nowDate.setTime(df2.parse(df.format(nowDate.getTime())));
                control.setFecha(nowDate.getTime());
            }

            List<Solicitudentregacliente> solec = elemento.getSolicitudentregaclienteList();
            lotesSol = sessionBean.marketingUserFacade.getLotesBonosCasinoTipoBono(elemento.getIdCasino().getIdCasino(), elemento.getTipoBono());
            System.out.println("Tamano de la lista de lotes " + lotesSol.size());
            clientesMontos = new ArrayList<ClienteMonto>(0);
            if (control.getSolicitudEntregaid().getTipoBono().getNombre().equals("PROMOCIONAL")) {
                totalEntregar = control.getSolicitudEntregaid().getTotal();
                if (control.getSolicitudEntregaid().getTotalpreaprobado() != 0) {
                    totalEntregar = control.getSolicitudEntregaid().getTotalpreaprobado();
                }
                if (control.getSolicitudEntregaid().getTotalaprobado() != 0) {
                    totalEntregar = control.getSolicitudEntregaid().getTotalaprobado();
                }
                denominacionCant = MatematicaAplicada.getBonosAsignadosDEnominacinesGrandes(lotesSol, totalEntregar);
            } else {
                totalEntregar = 0f;
                denominacionCant = new ArrayList<DenoinacionCant>();
                for (Lotebono lb : lotesSol) {
                    denominacionCant.add(new DenoinacionCant(lb));
                }
                for (Solicitudentregacliente solec1 : solec) {
                    Float monto = solec1.getValorTotal();
                    if (solec1.getValorPreAprobado() != null && solec1.getValorPreAprobado() != 0) {
                        monto = solec1.getValorPreAprobado();
                    }
                    if (solec1.getValorAprobado() != null && solec1.getValorAprobado() != 0) {
                        monto = solec1.getValorAprobado();
                    }
                    totalEntregar += monto;
                    ClienteMonto cliente = new ClienteMonto(solec1.getCliente().getIdCliente(), solec1.getCliente().getNombres() + " " + solec1.getCliente().getApellidos(), monto, lotesSol, elemento.getFormareparticrbonos(), solec1.getValorTotal(), solec1.getValorPreAprobado(), solec1.getValorAprobado());
                    clientesMontos.add(cliente);
                    List<DenoinacionCant> listClientes = cliente.getDenominacionCant();
                    for (DenoinacionCant listCliente : listClientes) {
                        denominacionCant.get(denominacionCant.indexOf(listCliente)).sumCantidad(listCliente.getCantidad());
                    }
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(AprobarSolicitudBonos.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        elemento.setEstado("APROBADA");
        elemento.setSolicitudentregaclienteList(solicitudentregaclienteses);
        sessionBean.marketingUserFacade.cambiarEstadoSolicitudentrega(elemento);
        sessionBean.registrarlog(null, null, "Aprobada solicitud Usuario:" + sessionBean.getUsuario().getNombreUsuario());
        FacesUtil.addInfoMessage("Solicitud aprobada con exito!", "");

        boolean isNotOk = false;
        List<ControlsalidabonosHasLotesbonos> controlsalidabonosHasLotesbonoses = new ArrayList<ControlsalidabonosHasLotesbonos>();
        List<ControlsalidabonosHasLotesbonosHasClientes> controlsalidabonosHasLotesbonosHasClienteses = new ArrayList<ControlsalidabonosHasLotesbonosHasClientes>();
        if (!elemento.getControlsalidabonoList().isEmpty()) {
            control = elemento.getControlsalidabonoList().get(0);
        } else {
            control = new Controlsalidabono();
        }
        if (control.getSolicitudEntregaid().getTipoBono().getNombre().equals("PROMOCIONAL")) {
            for (DenoinacionCant den : denominacionCant) {
                ControlsalidabonosHasLotesbonos cslb = new ControlsalidabonosHasLotesbonos();
                cslb.setCantidad(0);
                cslb.setControlsalidabono(control);
                cslb.setLotebono(den.getDenomiancion());
                cslb.setCantidad(den.getCantidad());
                cslb.setControlsalidabonosHasLotesbonosPK(new ControlsalidabonosHasLotesbonosPK(elemento.getId(), den.getDenomiancion().getId()));
                cslb.setControlsalidabonosHasLotesbonosHasClientesList(new ArrayList<ControlsalidabonosHasLotesbonosHasClientes>());
                controlsalidabonosHasLotesbonoses.add(cslb);
            }

            control.setControlsalidabonosHasLotesbonosList(controlsalidabonosHasLotesbonoses);
            control.setEstado("SOLICITADA");
            control.setSolicitudEntregaid(elemento);
            sessionBean.marketingUserFacade.guardarControlSalidaBonos(control, false);

            FacesUtil.addInfoMessage("Se generó la solicitud con exito!", "Notificación enviada");
        } else {
            for (Lotebono lb : lotesSol) {
                ControlsalidabonosHasLotesbonos cslb = new ControlsalidabonosHasLotesbonos();
                cslb.setCantidad(0);
                cslb.setControlsalidabono(control);
                cslb.setLotebono(lb);
                cslb.setControlsalidabonosHasLotesbonosPK(new ControlsalidabonosHasLotesbonosPK(control.getId(), lb.getId()));
                controlsalidabonosHasLotesbonoses.add(cslb);
                cslb.setControlsalidabonosHasLotesbonosHasClientesList(new ArrayList<ControlsalidabonosHasLotesbonosHasClientes>());
            }
            for (ClienteMonto cm : clientesMontos) {
                if (!cm.getIsOk()) {
                    isNotOk = true;
                    break;
                }
                for (DenoinacionCant cant : cm.getDenominacionCant()) {
                    ControlsalidabonosHasLotesbonosHasClientes hasClientes = new ControlsalidabonosHasLotesbonosHasClientes(control.getId(), cant.getDenomiancion().getId(), cm.getId());
                    hasClientes.setCantidad(cant.getCantidad());
                    controlsalidabonosHasLotesbonosHasClienteses.add(hasClientes);
                }

            }
            if (!isNotOk) {
                try {
                    for (ControlsalidabonosHasLotesbonosHasClientes chlhc : controlsalidabonosHasLotesbonosHasClienteses) {
                        System.out.println("id chclh " + chlhc.getControlsalidabonosHasLotesbonosHasClientesPK().getControlSalidaBonoshasLotesBonosLotesBonosid());
                        ControlsalidabonosHasLotesbonos chl = new ControlsalidabonosHasLotesbonos(control.getId(), chlhc.getControlsalidabonosHasLotesbonosHasClientesPK().getControlSalidaBonoshasLotesBonosLotesBonosid());

                        System.out.println("id chclh " + controlsalidabonosHasLotesbonoses.indexOf(chl));
                        chl = controlsalidabonosHasLotesbonoses.get(controlsalidabonosHasLotesbonoses.indexOf(chl));
                        if (chl.getCantidad() == null) {
                            chl.setCantidad(chlhc.getCantidad());
                        } else {
                            chl.setCantidad(chlhc.getCantidad() + chl.getCantidad());
                        }
                        chl.getControlsalidabonosHasLotesbonosHasClientesList().add(chlhc);
                    }
                    control.setControlsalidabonosHasLotesbonosList(controlsalidabonosHasLotesbonoses);
                    control.setEstado("SOLICITADA");
                    control.setSolicitudEntregaid(elemento);
                    sessionBean.marketingUserFacade.guardarControlSalidaBonos(control, false);
                    String body = "Se ha aprobado la solicitud de bonos con el número de acta " + elemento.getId()
                            + ".\nPor favor revisar la pagina de Lista de solicitudes de salida de bonos.";
                    Notificador.notificar(Notificador.SOLICITUD_CONTROL_SALIDA_GENERADA, body, "Se ha aprobado la solicitud de bonos.", sessionBean.getUsuario().getUsuariodetalle().getCorreo());

                    FacesContext.getCurrentInstance().getExternalContext().redirect("ListaSolicitudBono.xhtml");
                    FacesUtil.addInfoMessage("Se generó la solicitud con exito!", "Notificación enviada");
                } catch (IOException ex) {
                    Logger.getLogger(AprobarSolicitudBonos.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                FacesUtil.addErrorMessage("No se puede guardar la solicitud!", "Revise que los bonos asignados a los clientes concuerden con el monto");
            }
        }
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
        clientessgbs = sessionBean.marketingUserFacade.findAllClientesCasinos(elemento.getIdCasino(), "", "", "", null);
    }

    public List<Cliente> getSelectedClientessgbs() {
        return selectedClientessgbs;
    }

    public void setSelectedClientessgbs(List<Cliente> selectedClientessgbs) {
        this.selectedClientessgbs = selectedClientessgbs;
    }

    public List<Solicitudentregacliente> getSolicitudentregaclienteses() {
        return solicitudentregaclienteses;
    }

    public void setSolicitudentregaclienteses(List<Solicitudentregacliente> solicitudentregaclienteses) {
        this.solicitudentregaclienteses = solicitudentregaclienteses;
    }

    public void setSalatoCliente(Integer idSala, Integer indexCliente) {
        this.solicitudentregaclienteses.get(indexCliente).setAreaid(new Area(idSala));
    }

    public Float getTotal() {
        Float total = 0f;
        for (Solicitudentregacliente sec : solicitudentregaclienteses) {
            System.out.println(sec.getValorTotal());
            total += sec.getValorTotal();
        }
        return total;
    }

    public Float getPreTotal() {
        Float total = 0f;
        for (Solicitudentregacliente sec : solicitudentregaclienteses) {
            System.out.println(sec.getValorTotal());
            total += sec.getValorPreAprobado();
        }
        return total;
    }

    public Float getAprTotal() {
        Float total = 0f;
        for (Solicitudentregacliente sec : solicitudentregaclienteses) {
            System.out.println(sec.getValorTotal());
            total += sec.getValorAprobado();
        }
        return total;
    }

    public Controlsalidabono getControl() {
        return control;
    }

    public void setControl(Controlsalidabono control) {
        this.control = control;
    }

    public List<ClienteMonto> getClientesMontos() {
        return clientesMontos;
    }

    public void setClientesMontos(List<ClienteMonto> clientesMontos) {
        this.clientesMontos = clientesMontos;
    }

    public List<Lotebono> getLotesSol() {
        return lotesSol;
    }

    public void setLotesSol(List<Lotebono> lotesSol) {
        this.lotesSol = lotesSol;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuariodetalle getUsuariosdetalles() {
        return usuariosdetalles;
    }

    public void setUsuariosdetalles(Usuariodetalle usuariosdetalles) {
        this.usuariosdetalles = usuariosdetalles;
    }

    public Float getTotalEntregar() {
        return totalEntregar;
    }

    public void setTotalEntregar(Float totalEntregar) {
        this.totalEntregar = totalEntregar;
    }

    public List<DenoinacionCant> getDenominacionCant() {
        return denominacionCant;
    }

    public void setDenominacionCant(List<DenoinacionCant> denominacionCant) {
        this.denominacionCant = denominacionCant;
    }

}
