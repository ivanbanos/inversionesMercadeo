/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Bono;
import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Clienteblanco;
import com.invbf.sistemagestionmercadeo.entity.Controlsalidabono;
import com.invbf.sistemagestionmercadeo.entity.ControlsalidabonosHasLotesbonos;
import com.invbf.sistemagestionmercadeo.entity.ControlsalidabonosHasLotesbonosHasClientes;
import com.invbf.sistemagestionmercadeo.entity.Denominacion;
import com.invbf.sistemagestionmercadeo.util.ClienteMonto;
import com.invbf.sistemagestionmercadeo.util.ConsecutivoBono;
import com.invbf.sistemagestionmercadeo.util.DenoinacionCant;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import com.invbf.sistemagestionmercadeo.util.Mensajes;
import com.invbf.sistemagestionmercadeo.util.Notificador;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
public class BonoValidarBean implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private Controlsalidabono elemento;
    private List<ClienteMonto> clientes;
    private List<ConsecutivoBono> bonosPorAsignar;
    private List<ConsecutivoBono> bonosSelected;
    private List<ClienteMonto> clientesNecesitanDenominacion;
    private Integer idBono;
    private Integer idCliente;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public BonoValidarBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("requisiciones");
        if (!sessionBean.perfilViewMatch("Verificarbono")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }

        Integer id = (Integer) sessionBean.getAttributes("idsolicitudsalida");
        if (id != null && id != 0) {
            elemento = sessionBean.marketingUserFacade.getSolicitudSalida(id);
        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        sessionBean.revisarEstadoBonos();
        clientes = new ArrayList<ClienteMonto>();
        System.out.println("Proposito " + elemento.getSolicitudEntregaid().getPropositoEntrega());
        if (elemento.getSolicitudEntregaid().getPropositoEntrega().getNombre().equals("CASOS ESPECIALES")) {
            System.out.println("Tamanio " + elemento.getControlsalidabonosHasLotesbonosList().size());
            for (ControlsalidabonosHasLotesbonos lotebono : elemento.getControlsalidabonosHasLotesbonosList()) {
                System.out.println("Tamanio " + lotebono.getClienteblancoList().size());
                for (Clienteblanco CoentrolSalidaLotecliente : lotebono.getClienteblancoList()) {
                    ClienteMonto cliente;
                    System.out.println(CoentrolSalidaLotecliente.getNombres());
                    System.out.println(CoentrolSalidaLotecliente.getNombres());
                    if (clientes.contains(new ClienteMonto(CoentrolSalidaLotecliente.getId()))) {
                        cliente = clientes.get(clientes.indexOf(new ClienteMonto(CoentrolSalidaLotecliente.getId())));
                    } else {
                        cliente = new ClienteMonto(CoentrolSalidaLotecliente.getId(),
                                CoentrolSalidaLotecliente.getNombres() + " " + CoentrolSalidaLotecliente.getApellidos());
                        clientes.add(cliente);
                    }
                    cliente.getDenominacionCant().add(new DenoinacionCant(lotebono.getLotebono(), CoentrolSalidaLotecliente.getCantA()));
                    cliente.setNuevoMonto();
                }
            }
            bonosPorAsignar = new ArrayList<ConsecutivoBono>();
            for (Bono bono : elemento.getBonoList()) {
                System.out.println(bono.getDenominacion().getValor());
                ConsecutivoBono cb = new ConsecutivoBono(bono.getId(), bono.getConsecutivo(), bono.getDenominacion().getValor(), bono.getDenominacion().getId());
                bonosPorAsignar.add(cb);
                for (ClienteMonto cm : clientes) {
                    System.out.println(cm.getNombre());
                    if (cm.haveLeftDenomination(cb.getIdDenominacion())) {
                        cm.restarBono(new Denominacion(cb.getIdDenominacion()));
                        cb.setNombreClietne(cm.getNombre());
                        break;
                    }
                }
            }
        } else {
            for (ControlsalidabonosHasLotesbonos lotebono : elemento.getControlsalidabonosHasLotesbonosList()) {
                for (ControlsalidabonosHasLotesbonosHasClientes CoentrolSalidaLotecliente : lotebono.getControlsalidabonosHasLotesbonosHasClientesList()) {
                    ClienteMonto cliente;
                    if (clientes.contains(new ClienteMonto(CoentrolSalidaLotecliente.getCliente().getIdCliente()))) {
                        cliente = clientes.get(clientes.indexOf(new ClienteMonto(CoentrolSalidaLotecliente.getCliente().getIdCliente())));
                    } else {
                        cliente = new ClienteMonto(CoentrolSalidaLotecliente.getCliente().getIdCliente(),
                                CoentrolSalidaLotecliente.getCliente().getNombres() + " " + CoentrolSalidaLotecliente.getCliente().getApellidos());
                        clientes.add(cliente);
                    }
                    cliente.getDenominacionCant().add(new DenoinacionCant(lotebono.getLotebono(), CoentrolSalidaLotecliente.getCantidad()));
                    cliente.setNuevoMonto();
                }
            }
            bonosPorAsignar = new ArrayList<ConsecutivoBono>();
            for (Bono bono : elemento.getBonoList()) {
                if (bono.getCliente() == null) {
                    System.out.println(bono.getDenominacion().getValor());
                    ConsecutivoBono cb = new ConsecutivoBono(bono.getId(), bono.getConsecutivo(), bono.getDenominacion().getValor(), bono.getDenominacion().getId());
                    bonosPorAsignar.add(cb);
                    for (ClienteMonto cm : clientes) {
                        if (cm.haveLeftDenomination(cb.getIdDenominacion())) {
                            cm.restarBono(new Denominacion(cb.getIdDenominacion()));
                            cb.setIdCliente(cm.getId());
                            cb.setNombreClietne(cm.getNombre());
                            break;
                        }
                    }
                } else {
                    if (bono.getEstado().equals("POR VERIFICAR")) {
                        bonosPorAsignar.add(new ConsecutivoBono(bono.getId(), bono.getConsecutivo(), bono.getDenominacion().getValor(), bono.getDenominacion().getId(), bono.getCliente().getIdCliente(), bono.getCliente().getNombres() + " " + bono.getCliente().getApellidos()));

                        ClienteMonto cliente = clientes.get(clientes.indexOf(new ClienteMonto(bono.getCliente().getIdCliente())));
                        cliente.restarBono(bono.getDenominacion());
                    }
                }
            }
        }

        clientesNecesitanDenominacion = new ArrayList<ClienteMonto>();

    }

    public Controlsalidabono getElemento() {
        return elemento;
    }

    public void setElemento(Controlsalidabono elemento) {
        this.elemento = elemento;
    }

    public List<ClienteMonto> getClientes() {
        return clientes;
    }

    public void setClientes(List<ClienteMonto> clientes) {
        this.clientes = clientes;
    }

    public List<ConsecutivoBono> getBonosPorAsignar() {
        return bonosPorAsignar;
    }

    public void setBonosPorAsignar(List<ConsecutivoBono> bonosPorAsignar) {
        this.bonosPorAsignar = bonosPorAsignar;
    }

    public void buscarClientesQueNecesiteBonoDeDenominacion(Integer idBono) {
        System.out.println(idBono);
        this.idBono = idBono;
        clientesNecesitanDenominacion = new ArrayList<ClienteMonto>();
        ConsecutivoBono cb = bonosPorAsignar.get(bonosPorAsignar.indexOf(new ConsecutivoBono(idBono)));
        for (ClienteMonto cm : clientes) {
            if (cm.haveLeftDenomination(cb.getIdDenominacion())) {
                clientesNecesitanDenominacion.add(cm);
            }
        }

    }

    public void asignarBonoCliente() {
        if (idCliente != null || idCliente != 0) {
            clientesNecesitanDenominacion = new ArrayList<ClienteMonto>();

            ConsecutivoBono cb = bonosPorAsignar.get(bonosPorAsignar.indexOf(new ConsecutivoBono(idBono)));
            ClienteMonto cm = clientes.get(clientes.indexOf(new ClienteMonto(idCliente)));
            cm.restarBono(new Denominacion(cb.getIdDenominacion()));
            cb.setIdCliente(cm.getId());
            cb.setNombreClietne(cm.getNombre());
            idCliente = null;
        }
    }

    public void sacarBonoCliente() {
        clientesNecesitanDenominacion = new ArrayList<ClienteMonto>();
        ConsecutivoBono cb = bonosPorAsignar.get(bonosPorAsignar.indexOf(new ConsecutivoBono(idBono)));
        System.out.println(cb.getIdCliente());
        ClienteMonto cm = null;
        for (ClienteMonto cliente : clientes) {
            if (cliente.getId().equals(cb.getIdCliente())) {
                cm = cliente;
                break;
            }
        }
        if (cm != null) {
            cm.sumarBono(new Denominacion(cb.getIdDenominacion()));
            cb.setIdCliente(null);
            cb.setNombreClietne(null);
            idCliente = null;
        }
    }

    public void guardarCambiosBonos() {
        guardar:
        {
            List<ConsecutivoBono> cb = new ArrayList<ConsecutivoBono>();
            System.out.println("Entra a validar");
            for (ConsecutivoBono bono : bonosSelected) {
                if (!bono.getRazonAnulamineto().equals("")) {
                    FacesUtil.addErrorMessage("ERROR!", "No puede colocarle motivo de anulación a un bono que está validado");
                    break guardar;
                }
                System.out.println("entra bono");
                if (!elemento.getSolicitudEntregaid().getPropositoEntrega().getNombre().equals("CASOS ESPECIALES")) {
                    elemento.getBonoList().get(elemento.getBonoList().indexOf(new Bono(bono.getId()))).setCliente(new Cliente(bono.getIdCliente()));
                }
                elemento.getBonoList().get(elemento.getBonoList().indexOf(new Bono(bono.getId()))).setNombreCliente(bono.getNombreClietne());
                elemento.getBonoList().get(elemento.getBonoList().indexOf(new Bono(bono.getId()))).setEstado("DILIGENCIADO");
            }
            for (ConsecutivoBono bono : bonosPorAsignar) {
                if (!elemento.getBonoList().get(elemento.getBonoList().indexOf(new Bono(bono.getId()))).getEstado().equals("DILIGENCIADO")) {
                    if (bono.getRazonAnulamineto().equals("")) {

                        FacesUtil.addErrorMessage("ERROR!", "Tiene que colocarle motivo de anulación a un bono que está validado");
                        break guardar;
                    } else {
                        cb.add(bono);
                        if (!elemento.getSolicitudEntregaid().getPropositoEntrega().getNombre().equals("CASOS ESPECIALES")) {
                            elemento.getBonoList().get(elemento.getBonoList().indexOf(new Bono(bono.getId()))).setCliente(new Cliente(bono.getIdCliente()));
                        }
                        elemento.getBonoList().get(elemento.getBonoList().indexOf(new Bono(bono.getId()))).setNombreCliente(bono.getNombreClietne());
                        elemento.getBonoList().get(elemento.getBonoList().indexOf(new Bono(bono.getId()))).setEstado("ANULADO");
                        elemento.getBonoList().get(elemento.getBonoList().indexOf(new Bono(bono.getId()))).setRazonAnulamiento(bono.getRazonAnulamineto());
                    }
                }
            }
            if (!cb.isEmpty()) {
                String body = "<div><Label>Los bonos aquí listados han sido anulados durante el proceso de diligenciamiento:</label><br /></div>";
                body += "<div><br /><table border=\"1\" style=\"width:100%\"><tr>"
                        + "<th>Consecutivo</th>"
                        + "<th>Denominación</th>"
                        + "<th>Cliente</th>"
                        + "<th>Motivo de anulaci&oacute;n</th>"
                        + "</tr>";
                for (ConsecutivoBono cbe : cb) {
                    body += "<tr>";
                    body += "<td align=\"center\">" + cbe.getConsecutivo() + "</td>";
                    body += "<td align=\"center\">" + cbe.getDenominacion() + "</td>";
                    if(cbe.getNombreClietne() ==null){
                        cbe.setNombreClietne("");
                    }
                    body += "<td align=\"center\">" + cbe.getNombreClietne() + "</td>";
                    body += "<td align=\"center\">" + cbe.getRazonAnulamineto() + "</td>";
                    body += "</tr>";
                }
                body += "</table><br /></div>";
                Notificador.notificar(Notificador.EMAIL_CLIENTE, body, "Existen Bonos anulados", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
            }
            elemento.getSolicitudEntregaid().setEstado("BONOS DILIGENCIADOS");
            elemento.setEstado("BONOS DILIGENCIADOS");
            sessionBean.marketingUserFacade.saveBonos(elemento, sessionBean.getUsuario().getIdUsuario());
            FacesUtil.addInfoMessage("Bonos asignados con exito", "Se diligenciarón " + bonosSelected.size() + " bonos");
            sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Bonos diligenciados y/o anulados con exito", "Se diligenciarón " + bonosSelected.size() + " bonos"));
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("ListaSolicitudSalidaBonos.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(AceptarSolicitudSalidaBonosBean.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public List<ClienteMonto> getClientesNecesitanDenominacion() {
        return clientesNecesitanDenominacion;
    }

    public void setClientesNecesitanDenominacion(List<ClienteMonto> clientesNecesitanDenominacion) {
        this.clientesNecesitanDenominacion = clientesNecesitanDenominacion;
    }

    public Integer getIdBono() {
        return idBono;
    }

    public void setIdBono(Integer idBono) {
        this.idBono = idBono;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public List<ConsecutivoBono> getBonosSelected() {
        return bonosSelected;
    }

    public void setBonosSelected(List<ConsecutivoBono> bonosSelected) {
        this.bonosSelected = bonosSelected;
    }

}
