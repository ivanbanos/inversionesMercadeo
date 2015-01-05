/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Bono;
import com.invbf.sistemagestionmercadeo.entity.Bononofisico;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Controlsalidabono;
import com.invbf.sistemagestionmercadeo.entity.ControlsalidabonosHasLotesbonos;
import com.invbf.sistemagestionmercadeo.entity.ControlsalidabonosHasLotesbonosHasClientes;
import com.invbf.sistemagestionmercadeo.entity.Lotebono;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentrega;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.entity.Usuariodetalle;
import com.invbf.sistemagestionmercadeo.exceptions.LoteBonosExistenteException;
import com.invbf.sistemagestionmercadeo.util.ClienteMonto;
import com.invbf.sistemagestionmercadeo.util.ConsecutivoBono;
import com.invbf.sistemagestionmercadeo.util.ConvertidorConsecutivo;
import com.invbf.sistemagestionmercadeo.util.DenoinacionCant;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import com.invbf.sistemagestionmercadeo.util.LoteBonoCant;
import java.io.IOException;
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
public class BonoValidarBean {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private Controlsalidabono elemento;
    private List<ClienteMonto> clientes;
    private List<ConsecutivoBono> bonosPorAsignar;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public BonoValidarBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("salidadebonos");
        if (!sessionBean.perfilViewMatch("Validarbono")) {
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
        clientes = new ArrayList<ClienteMonto>();
        for (ControlsalidabonosHasLotesbonos lotebono : elemento.getControlsalidabonosHasLotesbonosList()) {
            for (ControlsalidabonosHasLotesbonosHasClientes CoentrolSalidaLotecliente : lotebono.getControlsalidabonosHasLotesbonosHasClientesList()) {
                ClienteMonto cliente;
                if (clientes.contains(new ClienteMonto(CoentrolSalidaLotecliente.getCliente().getIdCliente()))) {
                    cliente = clientes.get(clientes.indexOf(new ClienteMonto(CoentrolSalidaLotecliente.getCliente().getIdCliente())));
                } else {
                    cliente = new ClienteMonto(CoentrolSalidaLotecliente.getCliente().getIdCliente(), 
                            CoentrolSalidaLotecliente.getCliente().getNombres()+" "+CoentrolSalidaLotecliente.getCliente().getApellidos());
                    clientes.add(cliente);
                }
                cliente.getDenominacionCant().add(new DenoinacionCant(lotebono.getLotebono(), CoentrolSalidaLotecliente.getCantidad()));
                cliente.setNuevoMonto();
            }
        }
        bonosPorAsignar = new ArrayList<ConsecutivoBono>();
        for(Bono bono: elemento.getBonoList()){
            if(bono.getCliente()==null){
                System.out.println(bono.getDenominacion().getValor());
                bonosPorAsignar.add(new ConsecutivoBono(bono.getId(), bono.getConsecutivo(),bono.getDenominacion().getValor()));
            }
            else {
                bonosPorAsignar.add(new ConsecutivoBono(bono.getId(), bono.getConsecutivo(), bono.getDenominacion().getValor(), bono.getCliente().getIdCliente(), bono.getCliente().getNombres()+" "+bono.getCliente().getApellidos()));
                ClienteMonto cliente = clientes.get(clientes.indexOf(new ClienteMonto(bono.getCliente().getIdCliente())));
                cliente.restarBono(bono);
            }
        }
        
        
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

    public void asignarBonoCliente(){}
}
