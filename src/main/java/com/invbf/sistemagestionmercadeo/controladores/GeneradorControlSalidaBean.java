/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Controlsalidabono;
import com.invbf.sistemagestionmercadeo.entity.ControlsalidabonosHasLotesbonos;
import com.invbf.sistemagestionmercadeo.entity.ControlsalidabonosHasLotesbonosPK;
import com.invbf.sistemagestionmercadeo.entity.Lotebono;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentrega;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregacliente;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.entity.Usuariodetalle;
import com.invbf.sistemagestionmercadeo.util.ClienteMonto;
import com.invbf.sistemagestionmercadeo.util.DenoinacionCant;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
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
public class GeneradorControlSalidaBean {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private Controlsalidabono elemento;
    private List<ClienteMonto> clientesMontos;
    private List<Lotebono> lotesSol;
    private Usuario usuario;
    private Usuariodetalle usuariosdetalles;
    private List<Casino> casinos;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public GeneradorControlSalidaBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("salidadebonos");
        if (!sessionBean.perfilViewMatch("ActSolicitudSalida")) {
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
        if (elemento.getSolicitante() == null || elemento.getSolicitante().getIdUsuario() == 0) {
            try {
                elemento.setSolicitante(sessionBean.getUsuario());
                DateFormat df = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
                DateFormat df2 = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
                TimeZone timeZone = TimeZone.getTimeZone("GMT-5");
                df.setTimeZone(timeZone);
                Calendar nowDate = Calendar.getInstance();
                nowDate.setTime(df2.parse(df.format(nowDate.getTime())));
                elemento.setFecha(nowDate.getTime());
            } catch (ParseException ex) {
                Logger.getLogger(GeneradorControlSalidaBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        usuario = elemento.getSolicitante();
        usuariosdetalles = elemento.getSolicitante().getUsuariodetalle();
        Solicitudentrega sol = elemento.getSolicitudEntregaid();
        List<Solicitudentregacliente> solec = sol.getSolicitudentregaclienteList();
        lotesSol = sessionBean.marketingUserFacade.getLotesBonosCasinoTipoBono(sol.getIdCasino().getIdCasino(), sol.getTipoBono());
        clientesMontos = new ArrayList<ClienteMonto>();
        for (Solicitudentregacliente solec1 : solec) {
            clientesMontos.add(new ClienteMonto(solec1.getCliente().getIdCliente(), solec1.getCliente().getNombres() + " " + solec1.getCliente().getApellidos(), solec1.getValorTotal(), lotesSol));
        }

        casinos = sessionBean.adminFacade.findAllCasinos();
    }

    public Controlsalidabono getElemento() {
        return elemento;
    }

    public void setElemento(Controlsalidabono elemento) {
        this.elemento = elemento;
    }

    public List<ClienteMonto> getClientesMontos() {
        return clientesMontos;
    }

    public void setClientesMontos(List<ClienteMonto> clientesMontos) {
        this.clientesMontos = clientesMontos;
    }

    public void guardar() {
        boolean isNotOk = false;
        List<ControlsalidabonosHasLotesbonos> controlsalidabonosHasLotesbonoses = new ArrayList<ControlsalidabonosHasLotesbonos>();

        for (ClienteMonto cm : clientesMontos) {
            if (!cm.getIsOk()) {
                isNotOk = true;
                break;
            }
            for (Lotebono lb : lotesSol) {
                ControlsalidabonosHasLotesbonos cslb = new ControlsalidabonosHasLotesbonos();
                cslb.setCantidad((cm.getDenominacionCant().get(cm.getDenominacionCant().indexOf(new DenoinacionCant(lb.getDenominacion())))).getCantidad() + "");
                cslb.setCliente(new Cliente(cm.getId()));
                cslb.setControlsalidabono(elemento);
                cslb.setLotebono(lb);
                cslb.setControlsalidabonosHasLotesbonosPK(new ControlsalidabonosHasLotesbonosPK(elemento.getId(), lb.getId(), cm.getId()));
                controlsalidabonosHasLotesbonoses.add(cslb);
            }
        }
        if (!isNotOk) {
            elemento.setControlsalidabonosHasLotesbonosList(controlsalidabonosHasLotesbonoses);
            elemento.setEstado("SOLICITADA");
            sessionBean.marketingUserFacade.guardarControlSalidaBonos(elemento);
            FacesUtil.addInfoMessage("Se generó la solicitud con exito!", "Notificación enviada");
        } else {
            FacesUtil.addErrorMessage("No se puede guardar la solicitud!","Revise que los bonos asignados a los clientes concuerden con el monto");
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Lotebono> getLotesSol() {
        return lotesSol;
    }

    public void setLotesSol(List<Lotebono> lotesSol) {
        this.lotesSol = lotesSol;
    }

    public Usuariodetalle getUsuariosdetalles() {
        return usuariosdetalles;
    }

    public void setUsuariosdetalles(Usuariodetalle usuariosdetalles) {
        this.usuariosdetalles = usuariosdetalles;
    }

    public Casino getCasinoById(Integer idCasino) {
        int casinoIndex = casinos.indexOf(new Casino(idCasino));
        if (casinoIndex != -1) {
            return casinos.get(casinoIndex);
        }
        return new Casino();
    }
}
