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
import com.invbf.sistemagestionmercadeo.util.ConvertidorConsecutivo;
import com.invbf.sistemagestionmercadeo.util.LoteBonoCant;
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
public class AceptarSolicitudSalidaBonosBean implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private Controlsalidabono elemento;
    private List<LoteBonoCant> loteBonoCants;
    private float total;
    private Usuario usuario;
    private Usuariodetalle usuariosdetalles;
    private List<Casino> casinos;
    private List<Bono> bonosAGuardar;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public AceptarSolicitudSalidaBonosBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("salidadebonos");
        if (!sessionBean.perfilViewMatch("AceptarSolicitudSalida") && !sessionBean.perfilViewMatch("Verbonosporverificar")) {
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
        if (elemento.getSolicitante() == null || elemento.getSolicitante().getIdUsuario() == null || elemento.getSolicitante().getIdUsuario() == 0) {

            elemento.setSolicitante(sessionBean.getUsuario());
            elemento.setFecha(elemento.getSolicitudEntregaid().getFecha());
        }
        usuario = elemento.getSolicitante();
        usuariosdetalles = elemento.getSolicitante().getUsuariodetalle();
        Solicitudentrega sol = elemento.getSolicitudEntregaid();
        loteBonoCants = new ArrayList<LoteBonoCant>();
        List<ControlsalidabonosHasLotesbonos> controlsalidabonosHasLotesbonoses = elemento.getControlsalidabonosHasLotesbonosList();

        for (ControlsalidabonosHasLotesbonos controlsalidabonosHasLotesbonos : controlsalidabonosHasLotesbonoses) {
            if (elemento.getSolicitudEntregaid().getPropositoEntrega().getNombre().equals("FIDELIZACIÓN")) {
                controlsalidabonosHasLotesbonos.setCantidad(0);
                controlsalidabonosHasLotesbonos.setCantA(0);
                controlsalidabonosHasLotesbonos.setCantPre(0);
                for (ControlsalidabonosHasLotesbonosHasClientes cshlohc : controlsalidabonosHasLotesbonos.getControlsalidabonosHasLotesbonosHasClientesList()) {
                    controlsalidabonosHasLotesbonos.setCantidad(controlsalidabonosHasLotesbonos.getCantidad() + cshlohc.getCantidad());
                    controlsalidabonosHasLotesbonos.setCantPre(controlsalidabonosHasLotesbonos.getCantPre() + cshlohc.getCantidad());
                    controlsalidabonosHasLotesbonos.setCantA(controlsalidabonosHasLotesbonos.getCantA() + cshlohc.getCantidad());
                }
            }
            LoteBonoCant ltc = new LoteBonoCant(controlsalidabonosHasLotesbonos.getLotebono(), controlsalidabonosHasLotesbonos.getCantA());
            if (elemento.getEstado().equals("PENDIENTE POR PROCESAR")) {
                ltc.setDesde(sumeUno(controlsalidabonosHasLotesbonos.getLotebono().getDesde()));
                String hasta = ltc.getDesde();
                for (int i = 0; i < controlsalidabonosHasLotesbonos.getCantA(); i++) {
                    System.out.println(i);
                    while (true) {
                        boolean seencontro = false;
                        for (Bononofisico bnf : controlsalidabonosHasLotesbonos.getLotebono().getBononofisicoList()) {
                            System.out.println("Comprobando " + bnf.getConsecutivo() + " " + hasta);
                            if (bnf.getConsecutivo().equals(hasta)) {
                                hasta = sumeUno(hasta);
                                seencontro = true;
                                break;
                            }
                        }
                        if (!seencontro) {
                            break;
                        }
                    }
                    hasta = sumeUno(hasta);
                }
                hasta = resteUno(hasta);
                if (ltc.getCantidad() != 0) {
                    ltc.setHasta(hasta);
                } else {
                    ltc.setHasta("");
                }
                total += controlsalidabonosHasLotesbonos.getCantA() * controlsalidabonosHasLotesbonos.getLotebono().getDenominacion().getValor();
                controlsalidabonosHasLotesbonos.setDesde(ltc.getDesde());
                controlsalidabonosHasLotesbonos.setHasta(ltc.getHasta());

            } else {
                ltc.setDesde(controlsalidabonosHasLotesbonos.getDesde());
                ltc.setHasta(controlsalidabonosHasLotesbonos.getHasta());
            }
            loteBonoCants.add(ltc);
        }
        casinos = sessionBean.adminFacade.findAllCasinos();
    }

    public Controlsalidabono getElemento() {
        return elemento;
    }

    public void setElemento(Controlsalidabono elemento) {
        this.elemento = elemento;
    }

    public void retirar() {
        elemento.setEstado("BONOS LISTOS PARA SER DILIGENCIADOS");
        elemento.getSolicitudEntregaid().setEstado("BONOS LISTOS PARA SER DILIGENCIADOS");
        bonosAGuardar = new ArrayList<Bono>();
        crearBonos();
        sessionBean.marketingUserFacade.guardarControlSalidaBonosLista(elemento, true, bonosAGuardar);
        String cuerpo = "Orden de retiro de bonos de inventario con número de acta " + elemento.getId() + " procesada. Los bonos se encuentran listos para ser diligenciados.";
        String titulo = "Orden de retiro de bonos de inventario procesada";
        Notificador.notificar(Notificador.SOLICITUD_CONTROL_SALIDA_APROBADA, cuerpo, titulo, "");
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Bonos retirados de inventario!", "Notificación enviada"));
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaSolicitudSalidaBonos.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(AceptarSolicitudSalidaBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void recoger() {
        elemento.setEstado("BONOS EN PROCESO DE DILIGENCIAMIENTO");
        elemento.getSolicitudEntregaid().setEstado("BONOS EN PROCESO DE DILIGENCIAMIENTO");
        sessionBean.marketingUserFacade.guardarControlSalidaBonosLista(elemento, true, new ArrayList<Bono>());
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Bonos recibidos para diligenciamiento!", ""));
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaSolicitudSalidaBonos.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(AceptarSolicitudSalidaBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        System.out.println(elemento.getSolicitudEntregaid().getIdCasino());
        Casino casino = sessionBean.adminFacade.findCasino(elemento.getSolicitudEntregaid().getIdCasino().getIdCasino());
        List<Lotebono> lotes = sessionBean.marketingUserFacade.getLotesBonosCasinoTipoBono(elemento.getSolicitudEntregaid().getIdCasino().getIdCasino(), elemento.getSolicitudEntregaid().getTipoBono());
        for (Lotebono lote : lotes) {
            try {
                System.out.println(lote.getDenominacion().getValor());
                String desde = sumeUno(lote.getDesde());
                System.out.println("Desde " + desde);
                for (ControlsalidabonosHasLotesbonos control : bonosControlSalida) {

                    if (control.getLotebono().equals(lote)) {

                        System.out.println("Cantidad de Bonos " + control.getCantidad());
                        System.out.println("Cantidad de Bonos " + control.getCantidad());
                        for (int i = control.getCantA(); i > 0; i--) {
                            System.out.println(i);
                            while (true) {
                                boolean seencontro = false;
                                for (Bononofisico bnf : lote.getBononofisicoList()) {
                                    System.out.println("Comprobando " + bnf.getConsecutivo() + " " + desde);
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
                            b.setFechaExpiracion(elemento.getFechavencimientobonos());
                            b.setControlSalidaBonosid(elemento);
                            if (lote.getTipoBono().getNombre().equals("PROMOCIONAL")) {
                                System.out.println(casino.getCasinodetalle().getAbreCiudad());
                                b.setConsecutivo(casino.getCasinodetalle().getCiudad() + "-" + desde);
                                b.setEstado("POR VERIFICAR");
                            } else {
                                b.setConsecutivo(casino.getCasinodetalle().getAbrenopromo() + "-" + desde);
                                b.setEstado("POR VERIFICAR");
                            }
                            b.setPropositosEntregaid(elemento.getSolicitudEntregaid().getPropositoEntrega());
                            bonosAGuardar.add(b);
                            System.out.println("Comprobar que pasa con " + desde);
                            desde = sumeUno(desde);
                        }
                    }
                }
                lote.setDesde(resteUno(desde));
                sessionBean.marketingUserFacade.guardarLote(lote);
            } catch (LoteBonosExistenteException ex) {
                Logger.getLogger(AceptarSolicitudSalidaBonosBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Lotebono lote : lotes) {
            if (ConvertidorConsecutivo.getCantidadInt(lote.getDesde(), lote.getHasta()) < 1000) {
                 Notificador.notificar(Notificador.INVENTARIO_EN_PROBLEMA, "ALERTA INVENTARIO DE BONOS HA ALCANZADO UN NIVEL MINIMO", "El lote de bonos "+lote.getTipoBono().getNombre() +", de denominaci&oacute;n "+lote.getDenominacion().getValor()+", de la sala "+lote.getIdCasino().getNombre()+"  ha alcanzado el nivel m&iacute;nimo establecido en el inventario. Debe revisarse el stock para generar requerimiento de producción de lotes.", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
        
            }
        }

    }

    private String sumeUno(String desde) {
        return ConvertidorConsecutivo.sumarUno(desde);
    }

    private String resteUno(String hasta) {
        return ConvertidorConsecutivo.restarUno(hasta);
    }

    public void volver() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaSolicitudSalidaBonos.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(VistaRequerimientoLoteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
