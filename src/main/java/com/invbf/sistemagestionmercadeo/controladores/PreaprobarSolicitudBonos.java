/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Area;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Clienteblanco;
import com.invbf.sistemagestionmercadeo.entity.Controlsalidabono;
import com.invbf.sistemagestionmercadeo.entity.ControlsalidabonosHasLotesbonos;
import com.invbf.sistemagestionmercadeo.entity.Lotebono;
import com.invbf.sistemagestionmercadeo.entity.Propositoentrega;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentrega;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregacliente;
import com.invbf.sistemagestionmercadeo.entity.Tipobono;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.entity.Usuariodetalle;
import com.invbf.sistemagestionmercadeo.util.ClienteBlancoLotes;
import com.invbf.sistemagestionmercadeo.util.ClienteMonto;
import com.invbf.sistemagestionmercadeo.util.ClienteSGBDTO;
import com.invbf.sistemagestionmercadeo.util.ConvertidorConsecutivo;
import com.invbf.sistemagestionmercadeo.util.DenoinacionCant;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import com.invbf.sistemagestionmercadeo.util.LotebonoCanti;
import com.invbf.sistemagestionmercadeo.util.MatematicaAplicada;
import com.invbf.sistemagestionmercadeo.util.Mensajes;
import com.invbf.sistemagestionmercadeo.util.Notificador;
import com.invbf.sistemagestionmercadeo.util.loteBonoSolicitud;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class PreaprobarSolicitudBonos implements Serializable {

    private List<ClienteBlancoLotes> clientesBlancosLotes;
    private List<loteBonoSolicitud> loteBonoSolicitudes;
    private List<ClienteSGBDTO> clientes;
    private List<ClienteSGBDTO> clientesFiltered;
    private Controlsalidabono control;
    private Solicitudentrega elemento;
    private int tipo;

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public PreaprobarSolicitudBonos() {
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
        if (sessionBean.getAttributes("idSolicitudentrega") != null && (Integer) sessionBean.getAttributes("idSolicitudentrega") != 0) {
            Integer id = (Integer) sessionBean.getAttributes("idSolicitudentrega");
            elemento = sessionBean.marketingUserFacade.getSolicitudbono(id);
            System.out.println("PROPOSITO " + elemento.getPropositoEntrega().getNombre());
            control = elemento.getControlsalidabonoList().get(0);
            clientes = new ArrayList<ClienteSGBDTO>();
            loteBonoSolicitudes = new ArrayList<loteBonoSolicitud>();
            clientesBlancosLotes = new ArrayList<ClienteBlancoLotes>();
            System.out.println("PROPOSITO" + elemento.getPropositoEntrega().getNombre());
            //Llenar IF Promocion
            if (elemento.getPropositoEntrega().getNombre().equals("PROMOCIÓN")) {
                tipo = 1;
                List<ControlsalidabonosHasLotesbonos> loteb = control.getControlsalidabonosHasLotesbonosList();
                for (ControlsalidabonosHasLotesbonos loteb1 : loteb) {
                    loteBonoSolicitud lbs = new loteBonoSolicitud();
                    lbs.setCantidad(loteb1.getCantidad());
                    lbs.setCantA(loteb1.getCantA());
                    lbs.setCantPre(loteb1.getCantPre());
                    lbs.setLotesBonosid(loteb1.getLotebono());
                    loteBonoSolicitudes.add(lbs);
                }
            }

            //Llenar IF Caso especial
            if (elemento.getPropositoEntrega().getNombre().equals("FIDELIZACIÓN")) {
                tipo = 2;
                List<Solicitudentregacliente> l = elemento.getSolicitudentregaclienteList();
                for (Solicitudentregacliente cliente : l) {
                    ClienteSGBDTO clie = new ClienteSGBDTO();
                    clie.setAprobado(cliente.getValorAprobado());
                    clie.setBono(Float.parseFloat(cliente.getCliente().getBonoFidelizacion()));
                    clie.setClientessgb(cliente.getCliente());
                    clie.setForma(cliente.getEntrega());
                    clie.setPreaprobado(cliente.getValorPreAprobado());
                    clie.setValorTotal(cliente.getValorTotal());
                    if (cliente.getCliente().getSolicitudentregaclienteList() != null && cliente.getCliente().getSolicitudentregaclienteList().size() > 0) {
                        clie.setUltimaSol(cliente.getCliente().getSolicitudentregaclienteList().get(cliente.getCliente().getSolicitudentregaclienteList().size() - 1).getValorAprobado());
                    }
                    if (cliente.getCliente().getSolicitudentregaclienteList() != null && cliente.getCliente().getSolicitudentregaclienteList().size() > 1) {
                        clie.setPenultimaSol(cliente.getCliente().getSolicitudentregaclienteList().get(cliente.getCliente().getSolicitudentregaclienteList().size() - 2).getValorAprobado());
                    }
                    if (cliente.getCliente().getSolicitudentregaclienteList() != null && cliente.getCliente().getSolicitudentregaclienteList().size() > 2) {
                        clie.setTrasultimaSol(cliente.getCliente().getSolicitudentregaclienteList().get(cliente.getCliente().getSolicitudentregaclienteList().size() - 3).getValorAprobado());
                    }

                }
            }

            //Llenar IF Fidelizacion
            if (elemento.getPropositoEntrega().getNombre().equals("CASOS ESPECIALES")) {
                tipo = 3;
                List<ControlsalidabonosHasLotesbonos> loteb = control.getControlsalidabonosHasLotesbonosList();
                for (ControlsalidabonosHasLotesbonos loteb1 : loteb) {
                    List<Clienteblanco> blancoLoteses = loteb1.getClienteblancoList();
                    for (Clienteblanco blancoLotese : blancoLoteses) {
                        boolean noexite = true;
                        for (ClienteBlancoLotes cl : clientesBlancosLotes) {
                            if (cl.getClienteblanco().getNombres().equals(blancoLotese.getNombres()) && cl.getClienteblanco().getApellidos().equals(blancoLotese.getApellidos())) {
                                noexite = false;
                                LotebonoCanti lbc = new LotebonoCanti(loteb1.getLotebono(), blancoLotese.getCantidad(), blancoLotese.getCantPre(), blancoLotese.getCantA(), blancoLotese.getId());
                                cl.getLotesBono().add(lbc);
                            }
                        }
                        if (noexite) {
                            ClienteBlancoLotes cbl = new ClienteBlancoLotes(blancoLotese);
                            LotebonoCanti lbc = new LotebonoCanti(loteb1.getLotebono(), blancoLotese.getCantidad(), blancoLotese.getCantPre(), blancoLotese.getCantA(), blancoLotese.getId());
                            cbl.getLotesBono().add(lbc);
                            clientesBlancosLotes.add(cbl);
                        }
                    }
                }
            }

            System.out.println("TIPO" + tipo);
        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(PreaprobarSolicitudBonos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public Solicitudentrega getElemento() {
        return elemento;
    }

    public void setElemento(Solicitudentrega elemento) {
        this.elemento = elemento;
    }

    public void guardar() {
        guardar:
        {
            try {

                elemento.setEstado("PREAPROBADA");
                if (elemento.getTipoBono().getNombre().equals("PROMOCIONAL") && elemento.getPropositoEntrega().getNombre().equals("PROMOCIÓN")) {
                    List<ControlsalidabonosHasLotesbonos> csl = new ArrayList<ControlsalidabonosHasLotesbonos>();
                    for (loteBonoSolicitud lbs : loteBonoSolicitudes) {
                        ControlsalidabonosHasLotesbonos element = new ControlsalidabonosHasLotesbonos();
                        element.setCantidad(lbs.getCantidad());
                        element.setCantA(lbs.getCantidad());
                        element.setCantPre(lbs.getCantidad());
                        element.setControlsalidabono(control);
                        element.setLotebono(lbs.getLotesBonosid());
                        csl.add(element);
                    }
                    control.setControlsalidabonosHasLotesbonosList(csl);
                } else if (elemento.getTipoBono().getNombre().equals("NO PROMOCIONAL") && elemento.getPropositoEntrega().getNombre().equals("FIDELIZACIÓN")) {

                    List<Solicitudentregacliente> solicitudentregaclienteses = new ArrayList<Solicitudentregacliente>();
                    for (ClienteSGBDTO clientesGBT : clientes) {
                        if (clientesGBT.getValorTotal() != 0) {
                            Solicitudentregacliente sec = new Solicitudentregacliente();
                            sec.setCliente(clientesGBT.getClientessgb());
                            sec.setValorTotal(clientesGBT.getValorTotal());
                            sec.setValorPreAprobado(clientesGBT.getValorTotal());
                            sec.setValorAprobado(clientesGBT.getValorTotal());
                            sec.setEntrega(clientesGBT.getForma());
                            solicitudentregaclienteses.add(sec);
                        }
                    }
                    elemento.setSolicitudentregaclienteList(solicitudentregaclienteses);

                } else if (elemento.getTipoBono().getNombre().equals("NO PROMOCIONAL") && elemento.getPropositoEntrega().getNombre().equals("CASOS ESPECIALES")) {

                    List<ControlsalidabonosHasLotesbonos> csl = new ArrayList<ControlsalidabonosHasLotesbonos>();
                    for (loteBonoSolicitud lbs : loteBonoSolicitudes) {
                        ControlsalidabonosHasLotesbonos element = new ControlsalidabonosHasLotesbonos();
                        element.setCantidad(lbs.getCantidad());
                        element.setCantPre(lbs.getCantidad());
                        element.setCantA(lbs.getCantidad());
                        element.setControlsalidabono(control);
                        element.setLotebono(lbs.getLotesBonosid());
                        element.setClienteblancoList(new ArrayList<Clienteblanco>());
                        csl.add(element);
                    }
                    for (ClienteBlancoLotes cbl : clientesBlancosLotes) {
                        for (LotebonoCanti lb : cbl.getLotesBono()) {
                            for (ControlsalidabonosHasLotesbonos csl1 : csl) {
                                if (csl1.getLotebono().equals(lb.getLotesBono())) {
                                    Clienteblanco cb = new Clienteblanco();
                                    cb.setApellidos(cbl.getClienteblanco().getApellidos());
                                    cb.setNombres(cbl.getClienteblanco().getNombres());
                                    cb.setCantidad(lb.getCantidad());
                                    cb.setCantPre(lb.getCantidad());
                                    cb.setCantA(lb.getCantidad());
                                    cb.setIdCliente(cbl.getClienteblanco().getIdCliente().getIdCliente() == null ? null : cbl.getClienteblanco().getIdCliente());
                                    cb.setJustificacion(cbl.getClienteblanco().getJustificacion());

                                    csl1.getClienteblancoList().add(cb);
                                    csl1.setCantidad(csl1.getCantidad() + lb.getCantidad());
                                    csl1.setCantA(csl1.getCantidad() + lb.getCantidad());
                                    csl1.setCantPre(csl1.getCantidad() + lb.getCantidad());
                                }
                            }
                        }
                    }

                    control.setControlsalidabonosHasLotesbonosList(csl);

                } else {
                    FacesUtil.addErrorMessage("Seleccionó un proposito y un tipo de bono incompatible", "");
                }
                elemento = sessionBean.marketingUserFacade.guardarSolicitudentrega(elemento, new ArrayList<Integer>());
                String body = "Se ha creado una solicitud de bonos con el número de acta " + elemento.getId()
                        + ".\nPor favor revisar la Lista de solicitudes de bonos.";

                control.setEstado("PRESOLICITADA");
                control.setFechavencimientobonos(elemento.getFechavencimientobonos());
                control.setSolicitudEntregaid(elemento);
                sessionBean.marketingUserFacade.guardarControlSalidaBonos(control, false);
                Notificador.notificar(Notificador.SOLICITUD_BONOS_GENERADA, body, "Solicitud de bonos generada", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
                sessionBean.registrarlog(null, null, "Generada solicitud Usuario:" + sessionBean.getUsuario().getNombreUsuario());
                sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Solicitud generada con exito!", "Notificación enviada"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("ListaSolicitudBono.xhtml");
                FacesUtil.addInfoMessage("Solicitud guardada con exito!", "Notificación enviada");
                sessionBean.setAttribute("idSolicitudentrega", elemento.getId());
            } catch (IOException ex) {
                Logger.getLogger(GeneradorSolicitudBonos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List<ClienteBlancoLotes> getClientesBlancosLotes() {
        return clientesBlancosLotes;
    }

    public void setClientesBlancosLotes(List<ClienteBlancoLotes> clientesBlancosLotes) {
        this.clientesBlancosLotes = clientesBlancosLotes;
    }

    public List<loteBonoSolicitud> getLoteBonoSolicitudes() {
        return loteBonoSolicitudes;
    }

    public void setLoteBonoSolicitudes(List<loteBonoSolicitud> loteBonoSolicitudes) {
        this.loteBonoSolicitudes = loteBonoSolicitudes;
    }

    public List<ClienteSGBDTO> getClientes() {
        return clientes;
    }

    public void setClientes(List<ClienteSGBDTO> clientes) {
        this.clientes = clientes;
    }

    public List<ClienteSGBDTO> getClientesFiltered() {
        return clientesFiltered;
    }

    public void setClientesFiltered(List<ClienteSGBDTO> clientesFiltered) {
        this.clientesFiltered = clientesFiltered;
    }

    public Controlsalidabono getControl() {
        return control;
    }

    public void setControl(Controlsalidabono control) {
        this.control = control;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public static String getCantidad(String desde, String hasta) {
        System.out.println("desde" + desde);
        System.out.println("hasta" + hasta);
        return (ConvertidorConsecutivo.getNumeroFromConsecutivo(hasta) - ConvertidorConsecutivo.getNumeroFromConsecutivo(desde)) + "";
    }
}
