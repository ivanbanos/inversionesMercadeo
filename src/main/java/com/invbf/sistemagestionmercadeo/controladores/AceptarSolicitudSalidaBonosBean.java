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
import com.invbf.sistemagestionmercadeo.entity.Lotebono;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentrega;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.entity.Usuariodetalle;
import com.invbf.sistemagestionmercadeo.exceptions.LoteBonosExistenteException;
import com.invbf.sistemagestionmercadeo.util.ConvertidorConsecutivo;
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
public class AceptarSolicitudSalidaBonosBean {
    
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private Controlsalidabono elemento;
    private List<LoteBonoCant> loteBonoCants;
    private float total;
    private Usuario usuario;
    private Usuariodetalle usuariosdetalles;
    private List<Casino> casinos;
    
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    
    public AceptarSolicitudSalidaBonosBean() {
    }
    
    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("salidadebonos");
        if (!sessionBean.perfilViewMatch("AceptarSolicitudSalida")) {
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
        if (elemento.getSolicitante().getIdUsuario() == null || elemento.getSolicitante().getIdUsuario() == 0) {
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
        loteBonoCants = new ArrayList<LoteBonoCant>();
        List<ControlsalidabonosHasLotesbonos> controlsalidabonosHasLotesbonoses = elemento.getControlsalidabonosHasLotesbonosList();
        for (ControlsalidabonosHasLotesbonos controlsalidabonosHasLotesbonos : controlsalidabonosHasLotesbonoses) {
            loteBonoCants.add(new LoteBonoCant(controlsalidabonosHasLotesbonos.getLotebono(), controlsalidabonosHasLotesbonos.getCantidad()));
            total += controlsalidabonosHasLotesbonos.getCantidad()*controlsalidabonosHasLotesbonos.getLotebono().getDenominacion().getValor();
        }
        
        casinos = sessionBean.adminFacade.findAllCasinos();
    }
    
    public Controlsalidabono getElemento() {
        return elemento;
    }
    
    public void setElemento(Controlsalidabono elemento) {
        this.elemento = elemento;
    }
    
    public void guardar() {
        elemento.setEstado("ACEPTADA");
        crearBonos();
        sessionBean.marketingUserFacade.guardarControlSalidaBonos(elemento);
        FacesUtil.addInfoMessage("Se aceptó la solicitud!", "Notificación enviada");
        
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
    
    public Casino getCasinoById(Integer idCasino) {
        int casinoIndex = casinos.indexOf(new Casino(idCasino));
        if (casinoIndex != -1) {
            return casinos.get(casinoIndex);
        }
        return new Casino();
    }
    
    public List<LoteBonoCant> getLoteBonoCants() {
        return loteBonoCants;
    }
    
    public void setLoteBonoCants(List<LoteBonoCant> loteBonoCants) {
        this.loteBonoCants = loteBonoCants;
    }
    
    public float getTotal() {
        return total;
    }
    
    public void setTotal(float total) {
        this.total = total;
    }
    
    private void crearBonos() {
        List<ControlsalidabonosHasLotesbonos> bonosControlSalida = elemento.getControlsalidabonosHasLotesbonosList();
        List<Bono> bonosAGuardar = new ArrayList<Bono>();
        System.out.println(elemento.getSolicitudEntregaid().getIdCasino());
        Casino casino = sessionBean.adminFacade.findCasino(elemento.getSolicitudEntregaid().getIdCasino().getIdCasino());
        List<Lotebono> lotes = sessionBean.marketingUserFacade.getLotesBonosCasinoTipoBono(elemento.getSolicitudEntregaid().getIdCasino().getIdCasino(), elemento.getSolicitudEntregaid().getTipoBono());
        for (Lotebono lote : lotes) {
            try {
                System.out.println(lote.getDenominacion().getValor());
                String desde = lote.getDesde();
                System.out.println("Desde "+desde);
                for (ControlsalidabonosHasLotesbonos control : bonosControlSalida) {
                    
                    if (control.getLotebono().equals(lote)) {
                        
                        System.out.println("Cantidad de Bonos "+control.getCantidad());
                        System.out.println("Cantidad de Bonos "+control.getCantidad());
                        for (int i = control.getCantidad(); i > 0; i--) {
                            System.out.println(i);
                            while (true) {
                                boolean seencontro = false;
                                for (Bononofisico bnf : lote.getBononofisicoList()) {
                                    System.out.println("Comprobando "+bnf.getConsecutivo()+" "+desde);
                                    if (bnf.getConsecutivo().equals(desde)) {
                                        desde = sumeUno(desde);
                                        seencontro = true;
                                        break;
                                    }
                                }
                                if (!seencontro) {
                                    break;
                                }
                            }
                            Bono b = new Bono();
                            b.setCasino(casino);
                            b.setDenominacion(lote.getDenominacion());
                            b.setTipo(lote.getTipoBono());
                            b.setEstado("POR VALIDAR");
                            b.setFechaExpiracion(elemento.getFechavencimientobonos());
                            b.setControlSalidaBonosid(elemento);
                            if (lote.getTipoBono().getNombre().equals("NO PROMOCIONAL")) {
                                System.out.println(casino.getCasinodetalle().getAbreCiudad());
                                b.setConsecutivo("PRO-"+casino.getCasinodetalle().getAbreCiudad()+desde);
                            } else {
                                b.setConsecutivo(casino.getCasinodetalle().getAbreviacion()+"-"+casino.getCasinodetalle().getAbreCiudad()+desde);
                            }
                            bonosAGuardar.add(b);
                            System.out.println("Comprobar que pasa con "+desde);
                            desde = sumeUno(desde);
                        }
                    }
                }
                lote.setDesde(desde);
                sessionBean.marketingUserFacade.guardarLote(lote);
            } catch (LoteBonosExistenteException ex) {
                Logger.getLogger(AceptarSolicitudSalidaBonosBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        sessionBean.marketingUserFacade.guardarBonos(bonosAGuardar);
    }
    
    private String sumeUno(String desde) {
        return ConvertidorConsecutivo.sumarUno(desde);
    }
    
}
