/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Bono;
import com.invbf.sistemagestionmercadeo.entity.Clienteblanco;
import com.invbf.sistemagestionmercadeo.entity.Controlsalidabono;
import com.invbf.sistemagestionmercadeo.entity.ControlsalidabonosHasLotesbonos;
import com.invbf.sistemagestionmercadeo.entity.ControlsalidabonosHasLotesbonosHasClientes;
import com.invbf.sistemagestionmercadeo.entity.ControlsalidabonosHasLotesbonosPK;
import com.invbf.sistemagestionmercadeo.entity.Lotebono;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentrega;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregacliente;
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
import com.invbf.sistemagestionmercadeo.util.estadisticaBonos;
import com.invbf.sistemagestionmercadeo.util.loteBonoSolicitud;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
public class GeneracionBean implements Serializable {

    private List<ClienteBlancoLotes> clientesBlancosLotes;
    private List<loteBonoSolicitud> loteBonoSolicitudes;
    private List<ClienteSGBDTO> clientes;
    private List<ClienteSGBDTO> clientesFiltered;
    private Controlsalidabono control;
    private Solicitudentrega elemento;
    private int tipo;
    private Float totalEntregar;
    private estadisticaBonos estadistica;
    private List<Bono> bonos;

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public GeneracionBean() {
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
                    clientes.add(clie);
                }
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

            //Llenar IF Fidelizacion
            if (elemento.getPropositoEntrega().getNombre().equals("CASOS ESPECIALES")) {
                tipo = 3;
                List<ControlsalidabonosHasLotesbonos> loteb = control.getControlsalidabonosHasLotesbonosList();
                for (ControlsalidabonosHasLotesbonos loteb1 : loteb) {
                    loteBonoSolicitud lbs = new loteBonoSolicitud();
                    lbs.setCantidad(loteb1.getCantidad());
                    lbs.setCantA(loteb1.getCantA());
                    lbs.setCantPre(loteb1.getCantPre());
                    lbs.setLotesBonosid(loteb1.getLotebono());
                    loteBonoSolicitudes.add(lbs);
                }
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
                            cbl.setLotesBono(new ArrayList<LotebonoCanti>());
                            cbl.getLotesBono().add(lbc);
                            clientesBlancosLotes.add(cbl);
                        }
                    }
                }
            }

            System.out.println("TIPO" + tipo);

            resolverbonos();

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
                System.out.println(elemento.getPropositoEntrega().getNombre());
                if (elemento.getTipoBono().getNombre().equals("PROMOCIONAL") && elemento.getPropositoEntrega().getNombre().equals("PROMOCIÓN")) {
                    List<ControlsalidabonosHasLotesbonos> csl = new ArrayList<ControlsalidabonosHasLotesbonos>();
                    for (loteBonoSolicitud lbs : loteBonoSolicitudes) {
                        ControlsalidabonosHasLotesbonos element = new ControlsalidabonosHasLotesbonos();
                        element.setCantidad(lbs.getCantidad());
                        element.setCantA(lbs.getCantPre());
                        element.setCantPre(lbs.getCantPre());
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
                            sec.setValorPreAprobado(clientesGBT.getPreaprobado());
                            sec.setValorAprobado(clientesGBT.getPreaprobado());
                            sec.setEntrega(clientesGBT.getForma());
                            solicitudentregaclienteses.add(sec);
                        }
                    }
                    elemento.setSolicitudentregaclienteList(solicitudentregaclienteses);

                    control.setControlsalidabonosHasLotesbonosList(new ArrayList<ControlsalidabonosHasLotesbonos>());
                    for (loteBonoSolicitud lb : loteBonoSolicitudes) {
                        ControlsalidabonosHasLotesbonos cslb = new ControlsalidabonosHasLotesbonos();
                        cslb.setCantidad(0);
                        cslb.setControlsalidabono(control);
                        cslb.setLotebono(lb.getLotesBonosid());
                        cslb.setControlsalidabonosHasLotesbonosPK(new ControlsalidabonosHasLotesbonosPK(control.getId(), lb.getLotesBonosid().getId()));
                        control.getControlsalidabonosHasLotesbonosList().add(cslb);
                        cslb.setControlsalidabonosHasLotesbonosHasClientesList(new ArrayList<ControlsalidabonosHasLotesbonosHasClientes>());
                    }
                    Float[] denominaciones = new Float[loteBonoSolicitudes.size()];
                    int i = 0;
                    for (loteBonoSolicitud lote : loteBonoSolicitudes) {
                        denominaciones[i] = lote.getLotesBonosid().getDenominacion().getValor();
                        i++;
                    }
                    List<ClienteMonto> clientesMontos = new ArrayList<ClienteMonto>(0);
                    totalEntregar = 0f;
                    List<Lotebono> lotesSol = sessionBean.marketingUserFacade.getLotesBonosCasinoTipoBono(elemento.getIdCasino().getIdCasino(), elemento.getTipoBono());
                    List<DenoinacionCant> denominacionCant = new ArrayList<DenoinacionCant>();
                    for (Lotebono lb : lotesSol) {
                        denominacionCant.add(new DenoinacionCant(lb));
                    }
                    for (Solicitudentregacliente solec1 : elemento.getSolicitudentregaclienteList()) {
                        Float monto = solec1.getValorTotal();
                        if (solec1.getValorPreAprobado() != null && solec1.getValorPreAprobado() != 0) {
                            monto = solec1.getValorPreAprobado();
                        } else {
                            solec1.setValorPreAprobado(monto);
                        }
                        if (solec1.getValorAprobado() != null && solec1.getValorAprobado() != 0) {
                            monto = solec1.getValorAprobado();
                        } else {
                            solec1.setValorAprobado(monto);
                        }
                        totalEntregar += monto;
                        ClienteMonto cliente = new ClienteMonto(solec1.getCliente().getIdCliente(), solec1.getCliente().getNombres() + " " + solec1.getCliente().getApellidos(), monto, lotesSol, 1, solec1.getValorTotal(), solec1.getValorPreAprobado(), solec1.getValorAprobado());
                        clientesMontos.add(cliente);
                        List<DenoinacionCant> listClientes = cliente.getDenominacionCant();
                        for (DenoinacionCant listCliente : listClientes) {
                            denominacionCant.get(denominacionCant.indexOf(listCliente)).sumCantidad(listCliente.getCantidad());
                        }
                    }

                    boolean isNotOk = false;
                    List<ControlsalidabonosHasLotesbonosHasClientes> controlsalidabonosHasLotesbonosHasClienteses = new ArrayList<ControlsalidabonosHasLotesbonosHasClientes>();

                    for (ClienteMonto cm : clientesMontos) {
                        if (!MatematicaAplicada.sePuedeLleagar(cm.getAprobado(), denominaciones)) {

                            isNotOk = true;
                        }
                        for (DenoinacionCant cant : cm.getDenominacionCant()) {
                            ControlsalidabonosHasLotesbonosHasClientes hasClientes = new ControlsalidabonosHasLotesbonosHasClientes(control.getId(), cant.getDenomiancion().getId(), cm.getId());
                            hasClientes.setCantidad(cant.getCantidad());
                            controlsalidabonosHasLotesbonosHasClienteses.add(hasClientes);
                        }

                    }

                } else if (elemento.getTipoBono().getNombre().equals("NO PROMOCIONAL") && elemento.getPropositoEntrega().getNombre().equals("CASOS ESPECIALES")) {
                    System.out.println("Entre");
                    System.out.println("Entre");
                    System.out.println("Entre");
                    System.out.println("Entre");
                    System.out.println("Entre");

                    List<ControlsalidabonosHasLotesbonos> csl = control.getControlsalidabonosHasLotesbonosList();
                    for (ControlsalidabonosHasLotesbonos csl1 : csl) {

                        csl1.setCantidad(0);
                        csl1.setCantA(0);
                        csl1.setCantPre(0);
                    }
                    for (ClienteBlancoLotes cbl : clientesBlancosLotes) {
                        System.out.println("Tamanio 1" + cbl.getLotesBono().size());

                        for (LotebonoCanti lb : cbl.getLotesBono()) {

                            System.out.println("Tamanio 1" + csl.size());
                            System.out.println("Id cliente" + lb.getId());
                            for (ControlsalidabonosHasLotesbonos csl1 : csl) {
                                System.out.println(csl1.getClienteblancoList().size());
                                System.out.println(csl1.getClienteblancoList().contains(new Clienteblanco(lb.getId())));
                                if (csl1.getClienteblancoList().contains(new Clienteblanco(lb.getId()))) {
                                    Clienteblanco cb = csl1.getClienteblancoList().get(csl1.getClienteblancoList().indexOf(new Clienteblanco(lb.getId())));
                                    cb.setCantPre(lb.getCantPre());
                                    cb.setCantA(lb.getCantPre());
                                    csl1.getClienteblancoList().add(cb);
                                    csl1.setCantidad(csl1.getCantidad() + lb.getCantidad());
                                    csl1.setCantA(csl1.getCantPre() + lb.getCantPre());
                                    csl1.setCantPre(csl1.getCantPre() + lb.getCantPre());
                                }

                            }
                        }
                    }

                    control.setControlsalidabonosHasLotesbonosList(csl);

                } else {
                    FacesUtil.addErrorMessage("Seleccionó un proposito y un tipo de bono incompatible", "");
                }
                elemento = sessionBean.marketingUserFacade.guardarSolicitudentrega(elemento, new ArrayList<Integer>());
                String body = "Se ha preaprobado la solicitud de bonos con el número de acta " + elemento.getId()
                        + ".\nPor favor revisar la Lista de solicitudes de bonos.";

                control.setEstado("PRESOLICITADA");
                control.setFechavencimientobonos(elemento.getFechavencimientobonos());
                control.setSolicitudEntregaid(elemento);
                sessionBean.marketingUserFacade.guardarControlSalidaBonos(control, false);
                Notificador.notificar(Notificador.SOLICITUD_BONOS_PREAPROBADA, body, "Solicitud de bonos preaprobada", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
                sessionBean.registrarlog(null, null, "Generada solicitud Usuario:" + sessionBean.getUsuario().getNombreUsuario());
                sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Solicitud preaprobada con exito!", "Notificación enviada"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("ListaSolicitudBono.xhtml");
                FacesUtil.addInfoMessage("Solicitud guardada con exito!", "Notificación enviada");
                sessionBean.setAttribute("idSolicitudentrega", elemento.getId());
            } catch (IOException ex) {
                Logger.getLogger(GeneradorSolicitudBonos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void guardarApro() {
        guardar:
        {
            try {

                elemento.setEstado("APROBADA");
                System.out.println(elemento.getPropositoEntrega().getNombre());
                if (elemento.getTipoBono().getNombre().equals("PROMOCIONAL") && elemento.getPropositoEntrega().getNombre().equals("PROMOCIÓN")) {
                    List<ControlsalidabonosHasLotesbonos> csl = new ArrayList<ControlsalidabonosHasLotesbonos>();
                    for (loteBonoSolicitud lbs : loteBonoSolicitudes) {
                        ControlsalidabonosHasLotesbonos element = new ControlsalidabonosHasLotesbonos();
                        element.setCantidad(lbs.getCantidad());
                        element.setCantA(lbs.getCantPre());
                        element.setCantPre(lbs.getCantA());
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
                            sec.setValorPreAprobado(clientesGBT.getPreaprobado());
                            sec.setValorAprobado(clientesGBT.getPreaprobado());
                            sec.setEntrega(clientesGBT.getForma());
                            solicitudentregaclienteses.add(sec);
                        }
                    }
                    elemento.setSolicitudentregaclienteList(solicitudentregaclienteses);

                    control.setControlsalidabonosHasLotesbonosList(new ArrayList<ControlsalidabonosHasLotesbonos>());
                    for (loteBonoSolicitud lb : loteBonoSolicitudes) {
                        ControlsalidabonosHasLotesbonos cslb = new ControlsalidabonosHasLotesbonos();
                        cslb.setCantidad(0);
                        cslb.setControlsalidabono(control);
                        cslb.setLotebono(lb.getLotesBonosid());
                        cslb.setControlsalidabonosHasLotesbonosPK(new ControlsalidabonosHasLotesbonosPK(control.getId(), lb.getLotesBonosid().getId()));
                        control.getControlsalidabonosHasLotesbonosList().add(cslb);
                        cslb.setControlsalidabonosHasLotesbonosHasClientesList(new ArrayList<ControlsalidabonosHasLotesbonosHasClientes>());
                    }
                    Float[] denominaciones = new Float[loteBonoSolicitudes.size()];
                    int i = 0;
                    for (loteBonoSolicitud lote : loteBonoSolicitudes) {
                        System.out.println("Lotes" + lote.getLotesBonosid().getDenominacion().getValor());
                        denominaciones[i] = lote.getLotesBonosid().getDenominacion().getValor();
                        i++;
                    }
                    List<ClienteMonto> clientesMontos = new ArrayList<ClienteMonto>(0);
                    totalEntregar = 0f;
                    List<Lotebono> lotesSol = sessionBean.marketingUserFacade.getLotesBonosCasinoTipoBono(elemento.getIdCasino().getIdCasino(), elemento.getTipoBono());
                    List<DenoinacionCant> denominacionCant = new ArrayList<DenoinacionCant>();
                    for (Lotebono lb : lotesSol) {
                        denominacionCant.add(new DenoinacionCant(lb));
                    }
                    for (Solicitudentregacliente solec1 : elemento.getSolicitudentregaclienteList()) {
                        Float monto = solec1.getValorTotal();
                        if (solec1.getValorPreAprobado() != null && solec1.getValorPreAprobado() != 0) {
                            monto = solec1.getValorPreAprobado();
                        } else {
                            solec1.setValorPreAprobado(monto);
                        }
                        if (solec1.getValorAprobado() != null && solec1.getValorAprobado() != 0) {
                            monto = solec1.getValorAprobado();
                        } else {
                            solec1.setValorAprobado(monto);
                        }
                        totalEntregar += monto;
                        ClienteMonto cliente = new ClienteMonto(solec1.getCliente().getIdCliente(), solec1.getCliente().getNombres() + " " + solec1.getCliente().getApellidos(), monto, lotesSol, 1, solec1.getValorTotal(), solec1.getValorPreAprobado(), solec1.getValorAprobado());
                        clientesMontos.add(cliente);
                        List<DenoinacionCant> listClientes = cliente.getDenominacionCant();
                        for (DenoinacionCant listCliente : listClientes) {
                            denominacionCant.get(denominacionCant.indexOf(listCliente)).sumCantidad(listCliente.getCantidad());
                        }
                    }

                    boolean isNotOk = false;
                    List<ControlsalidabonosHasLotesbonosHasClientes> controlsalidabonosHasLotesbonosHasClienteses = new ArrayList<ControlsalidabonosHasLotesbonosHasClientes>();

                    for (ClienteMonto cm : clientesMontos) {
                        if (!MatematicaAplicada.sePuedeLleagar(cm.getAprobado(), denominaciones)) {

                            isNotOk = true;
                        }
                        for (DenoinacionCant cant : cm.getDenominacionCant()) {
                            ControlsalidabonosHasLotesbonosHasClientes hasClientes = new ControlsalidabonosHasLotesbonosHasClientes(control.getId(), cant.getDenomiancion().getId(), cm.getId());
                            hasClientes.setCantidad(cant.getCantidad());
                            controlsalidabonosHasLotesbonosHasClienteses.add(hasClientes);
                        }

                    }
                    if (!isNotOk) {

                        for (ControlsalidabonosHasLotesbonos chl : control.getControlsalidabonosHasLotesbonosList()) {
                            chl.setCantidad(null);
                        }
                        for (ControlsalidabonosHasLotesbonosHasClientes chlhc : controlsalidabonosHasLotesbonosHasClienteses) {
                            System.out.println("id chclh " + chlhc.getControlsalidabonosHasLotesbonosHasClientesPK().getControlSalidaBonoshasLotesBonosLotesBonosid());
                            ControlsalidabonosHasLotesbonos chl = new ControlsalidabonosHasLotesbonos(control.getId(), chlhc.getControlsalidabonosHasLotesbonosHasClientesPK().getControlSalidaBonoshasLotesBonosLotesBonosid());

                            System.out.println("id chclh " + control.getControlsalidabonosHasLotesbonosList().indexOf(chl));
                            chl = control.getControlsalidabonosHasLotesbonosList().get(control.getControlsalidabonosHasLotesbonosList().indexOf(chl));
                            if (chl.getCantidad() == null) {
                                chl.setCantidad(chlhc.getCantidad());
                            } else {
                                chl.setCantidad(chlhc.getCantidad() + chl.getCantidad());
                            }
                            chl.getControlsalidabonosHasLotesbonosHasClientesList().add(chlhc);
                        }
                    } else {
                        FacesUtil.addErrorMessage("No se puede guardar la solicitud!", "Revise que los bonos asignados a los clientes concuerden con el monto");
                        break guardar;
                    }

                } else if (elemento.getTipoBono().getNombre().equals("NO PROMOCIONAL") && elemento.getPropositoEntrega().getNombre().equals("CASOS ESPECIALES")) {
                    System.out.println("Entre");
                    System.out.println("Entre");
                    System.out.println("Entre");
                    System.out.println("Entre");
                    System.out.println("Entre");

                    List<ControlsalidabonosHasLotesbonos> csl = control.getControlsalidabonosHasLotesbonosList();
                    for (ControlsalidabonosHasLotesbonos csl1 : csl) {

                        csl1.setCantidad(0);
                        csl1.setCantA(0);
                        csl1.setCantPre(0);
                    }
                    for (ClienteBlancoLotes cbl : clientesBlancosLotes) {
                        System.out.println("Tamanio 1" + cbl.getLotesBono().size());

                        for (LotebonoCanti lb : cbl.getLotesBono()) {

                            System.out.println("Tamanio 1" + csl.size());
                            System.out.println("Id cliente" + lb.getId());
                            for (ControlsalidabonosHasLotesbonos csl1 : csl) {
                                System.out.println(csl1.getClienteblancoList().size());
                                System.out.println(csl1.getClienteblancoList().contains(new Clienteblanco(lb.getId())));
                                if (csl1.getClienteblancoList().contains(new Clienteblanco(lb.getId()))) {
                                    Clienteblanco cb = csl1.getClienteblancoList().get(csl1.getClienteblancoList().indexOf(new Clienteblanco(lb.getId())));
                                    cb.setCantPre(lb.getCantPre());
                                    cb.setCantA(lb.getCantA());
                                    csl1.getClienteblancoList().add(cb);
                                    csl1.setCantidad(csl1.getCantidad() + lb.getCantidad());
                                    csl1.setCantA(csl1.getCantA() + lb.getCantA());
                                    csl1.setCantPre(csl1.getCantPre() + lb.getCantPre());
                                }

                            }
                        }
                    }

                    control.setControlsalidabonosHasLotesbonosList(csl);

                } else {
                    FacesUtil.addErrorMessage("Seleccionó un proposito y un tipo de bono incompatible", "");
                }
                elemento = sessionBean.marketingUserFacade.guardarSolicitudentrega(elemento, new ArrayList<Integer>());
                String body = "Se ha aprobado la solicitud de bonos con el número de acta " + elemento.getId()
                        + ".\nPor favor revisar la Lista de solicitudes de bonos.";

                control.setEstado("PENDIENTE POR PROCESAR");
                control.setFecha(new Date());
                control.setFechavencimientobonos(elemento.getFechavencimientobonos());
                control.setSolicitudEntregaid(elemento);
                sessionBean.marketingUserFacade.guardarControlSalidaBonos(control, false);
                Notificador.notificar(Notificador.SOLICITUD_CONTROL_SALIDA_GENERADA, body, "Solicitud de bonos aprobada", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
                sessionBean.registrarlog(null, null, "Generada solicitud Usuario:" + sessionBean.getUsuario().getNombreUsuario());
                String body2 = "Se ha generado una Orden de retiro de bonos de inventario con número de acta " + elemento.getId()
                        + ".\nPor favor revisar la Lista de Ordenes de retiro de bonos de inventario.";
                Notificador.notificar(Notificador.SOLICITUD_CONTROL_SALIDA_SOLICITADA, body2, "Orden de retiro de bonos de inventario generada", sessionBean.getUsuario().getUsuariodetalle().getCorreo());

                sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Solicitud aprobada con exito!", "Notificación enviada"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("ListaSolicitudBono.xhtml");
                FacesUtil.addInfoMessage("Solicitud guardada con exito!", "Notificación enviada");
                sessionBean.setAttribute("idSolicitudentrega", elemento.getId());

            } catch (IOException ex) {
                Logger.getLogger(GeneradorSolicitudBonos.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void recibirBonosEnSala() {
        try {
            control.getSolicitudEntregaid().setEstado("BONOS RECIBIDOS EN SALA");
            for (Bono b : control.getBonoList()) {
                if (b.getEstado().equals("DILIGENCIADO")) {
                    b.setEstado("EN SALA");
                }
            }
            sessionBean.marketingUserFacade.saveBonos(control, tipo);
            sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Bonos recibidos en sala", ""));
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaSolicitudBono.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(PreaprobarSolicitudBonos.class.getName()).log(Level.SEVERE, null, ex);
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

    public Float getTotal() {
        Float total = 0f;
        for (ClienteSGBDTO sec : clientes) {
            System.out.println(sec.getValorTotal());
            total += sec.getValorTotal();
        }
        return total;
    }

    public Float getTotalPre() {
        Float total = 0f;
        for (ClienteSGBDTO sec : clientes) {
            System.out.println(sec.getPreaprobado());
            total += sec.getPreaprobado();
        }
        return total;
    }

    public Float getTotalA() {
        Float total = 0f;
        for (ClienteSGBDTO sec : clientes) {
            System.out.println(sec.getAprobado());
            total += sec.getAprobado();
        }
        return total;
    }

    public static String getCantidad(String desde, String hasta) {
        System.out.println("desde" + desde);
        System.out.println("hasta" + hasta);
        return (ConvertidorConsecutivo.getNumeroFromConsecutivo(hasta) - ConvertidorConsecutivo.getNumeroFromConsecutivo(desde)) + "";
    }

    public float getTotalprepromo() {
        float total = 0;
        for (loteBonoSolicitud lbs : loteBonoSolicitudes) {
            total += lbs.getCantPre() * lbs.getLotesBonosid().getDenominacion().getValor();
        }
        return total;
    }

    public float getTotalapromo() {
        float total = 0;
        for (loteBonoSolicitud lbs : loteBonoSolicitudes) {
            total += lbs.getCantA() * lbs.getLotesBonosid().getDenominacion().getValor();
        }
        return total;
    }

    private void resolverbonos() {
        estadistica = new estadisticaBonos();
        estadistica.setBonosAprobados(0);
        estadistica.setBonosCanjeados(0);
        estadistica.setMontoAprobado(0);
        estadistica.setMontoCanjeado(0);
        estadistica.setMontoSolicitado(0);
        bonos = new ArrayList<Bono>();
        for (Bono bono : elemento.getControlsalidabonoList().get(0).getBonoList()) {
            if (!bono.getEstado().equals("ANULADO")) {

                estadistica.setBonosAprobados(estadistica.getBonosAprobados() + 1);
                estadistica.setMontoAprobado(estadistica.getMontoAprobado() + (bono.getDenominacion().getValor()));
                if (bono.getEstado().equals("CANJEADO")) {
                    estadistica.setBonosCanjeados(estadistica.getBonosCanjeados() + 1);
                    estadistica.setMontoCanjeado(estadistica.getMontoCanjeado() + (bono.getDenominacion().getValor()));

                } else {
                    bonos.add(bono);
                }

            }
        }
        estadistica.setEfectividad();
        if (elemento.getTipoBono().getNombre().equals("NO PROMOCIONAL") && elemento.getPropositoEntrega().getNombre().equals("FIDELIZACIÓN")) {
            for (ClienteSGBDTO cliente : clientes) {
                estadistica.setMontoSolicitado(estadistica.getMontoSolicitado() + cliente.getValorTotal());
            }
        } else {
            estadistica.setBonosSolocitados(0);
            for (loteBonoSolicitud lbs : loteBonoSolicitudes) {
                estadistica.setBonosSolocitados(estadistica.getBonosSolocitados() + lbs.getCantidad());
                estadistica.setMontoSolicitado(estadistica.getMontoSolicitado() + (lbs.getCantidad() * lbs.getLotesBonosid().getDenominacion().getValor()));

            }

        }
    }

    public Float getTotalEntregar() {
        return totalEntregar;
    }

    public void setTotalEntregar(Float totalEntregar) {
        this.totalEntregar = totalEntregar;
    }

    public estadisticaBonos getEstadistica() {
        return estadistica;
    }

    public void setEstadistica(estadisticaBonos estadistica) {
        this.estadistica = estadistica;
    }

    public List<Bono> getBonos() {
        return bonos;
    }

    public void setBonos(List<Bono> bonos) {
        this.bonos = bonos;
    }

    public void cerrar() {
        cerrar:
        {
            try {
                for (Bono bono : bonos) {
                    if (bono.getCausadenocanje() == null || bono.getCausadenocanje().equals("")) {
                        FacesUtil.addErrorMessage("Debe colocar una causa de no canje", "");
                        
                        break cerrar;
                    }
                }
                elemento.setEstado("REPORTE DE GESTIÓN DISPONIBLE");
                sessionBean.marketingUserFacade.cerrarSol(elemento, bonos);
                
                sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Reporte generado", ""));
                FacesContext.getCurrentInstance().getExternalContext().redirect("ListaSolicitudBono.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(GeneracionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
