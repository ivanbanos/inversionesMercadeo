/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Bono;
import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Controlsalidabono;
import com.invbf.sistemagestionmercadeo.entity.ControlsalidabonosHasLotesbonos;
import com.invbf.sistemagestionmercadeo.entity.ControlsalidabonosHasLotesbonosHasClientes;
import com.invbf.sistemagestionmercadeo.entity.Denominacion;
import com.invbf.sistemagestionmercadeo.util.ClienteMonto;
import com.invbf.sistemagestionmercadeo.util.ConsecutivoBono;
import com.invbf.sistemagestionmercadeo.util.DenoinacionCant;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import java.io.IOException;
import java.util.ArrayList;
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
public class BonoValidarBean {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private Controlsalidabono elemento;
    private List<ClienteMonto> clientes;
    private List<ConsecutivoBono> bonosPorAsignar;
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

        Integer id = (Integer) sessionBean.getAttributes().get("idsolicitudsalida");
        if (sessionBean.getAttributes().containsKey("idsolicitudsalida") && (Integer) sessionBean.getAttributes().get("idsolicitudsalida") != 0) {
            elemento = sessionBean.marketingUserFacade.getSolicitudSalida(id);
        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        sessionBean.revisarEstadoBonos();
        clientes = new ArrayList<ClienteMonto>();
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
                bonosPorAsignar.add(new ConsecutivoBono(bono.getId(), bono.getConsecutivo(), bono.getDenominacion().getValor(), bono.getDenominacion().getId(), bono.getCliente().getIdCliente(), bono.getCliente().getNombres() + " " + bono.getCliente().getApellidos()));
                ClienteMonto cliente = clientes.get(clientes.indexOf(new ClienteMonto(bono.getCliente().getIdCliente())));
                cliente.restarBono(bono.getDenominacion());
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
        clientesNecesitanDenominacion = new ArrayList<ClienteMonto>();
        ConsecutivoBono cb = bonosPorAsignar.get(bonosPorAsignar.indexOf(new ConsecutivoBono(idBono)));
        ClienteMonto cm = clientes.get(clientes.indexOf(new ClienteMonto(idCliente)));
        cm.restarBono(new Denominacion(cb.getIdDenominacion()));
        cb.setIdCliente(cm.getId());
        cb.setNombreClietne(cm.getNombre());
        idCliente = null;

    }

    public void guardarCambiosBonos() {
        System.out.println("Entra a validar");
        for (ConsecutivoBono bono : bonosPorAsignar) {
            System.out.println("entra bono");
            elemento.getBonoList().get(elemento.getBonoList().indexOf(new Bono(bono.getId()))).setCliente(new Cliente(bono.getIdCliente()));
        }
        FacesUtil.addInfoMessage("Bonos asignados con exito", "Se asignaron " + bonosPorAsignar.size() + " bonos");
        sessionBean.marketingUserFacade.saveBonos(elemento, sessionBean.getUsuario().getIdUsuario());
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

}
