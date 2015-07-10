/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregalote;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregalotesmaestro;
import com.invbf.sistemagestionmercadeo.util.Mensajes;
import com.invbf.sistemagestionmercadeo.util.Notificador;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
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
public class AceptarSolicitudEntregaBonosBean implements Serializable {

    private Solicitudentregalotesmaestro elemento;
    private List<Casino> casinos;
    private final HashMap<String, Long> mapLetrasValores;
    private final HashMap<Long, String> mapValoresLetras;

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public AceptarSolicitudEntregaBonosBean() {
        mapLetrasValores = new HashMap<String, Long>();
        mapLetrasValores.put("A", 1l);
        mapLetrasValores.put("B", 2l);
        mapLetrasValores.put("C", 3l);
        mapLetrasValores.put("D", 4l);
        mapLetrasValores.put("E", 5l);
        mapLetrasValores.put("F", 6l);
        mapLetrasValores.put("G", 7l);
        mapLetrasValores.put("H", 8l);
        mapLetrasValores.put("I", 9l);
        mapLetrasValores.put("J", 10l);
        mapLetrasValores.put("K", 11l);
        mapLetrasValores.put("L", 12l);
        mapLetrasValores.put("M", 13l);
        mapLetrasValores.put("N", 14l);
        mapLetrasValores.put("O", 15l);
        mapLetrasValores.put("P", 16l);
        mapLetrasValores.put("Q", 17l);
        mapLetrasValores.put("R", 18l);
        mapLetrasValores.put("S", 19l);
        mapLetrasValores.put("T", 20l);
        mapLetrasValores.put("U", 21l);
        mapLetrasValores.put("V", 22l);
        mapLetrasValores.put("W", 23l);
        mapLetrasValores.put("X", 24l);
        mapLetrasValores.put("Y", 25l);
        mapLetrasValores.put("Z", 26l);

        mapValoresLetras = new HashMap<Long, String>();
        mapValoresLetras.put(1l, "A");
        mapValoresLetras.put(2l, "B");
        mapValoresLetras.put(3l, "C");
        mapValoresLetras.put(4l, "D");
        mapValoresLetras.put(5l, "E");
        mapValoresLetras.put(6l, "F");
        mapValoresLetras.put(7l, "G");
        mapValoresLetras.put(8l, "H");
        mapValoresLetras.put(9l, "I");
        mapValoresLetras.put(10l, "J");
        mapValoresLetras.put(11l, "K");
        mapValoresLetras.put(12l, "L");
        mapValoresLetras.put(13l, "M");
        mapValoresLetras.put(14l, "N");
        mapValoresLetras.put(15l, "O");
        mapValoresLetras.put(16l, "P");
        mapValoresLetras.put(17l, "Q");
        mapValoresLetras.put(18l, "R");
        mapValoresLetras.put(19l, "S");
        mapValoresLetras.put(20l, "T");
        mapValoresLetras.put(21l, "U");
        mapValoresLetras.put(22l, "V");
        mapValoresLetras.put(23l, "W");
        mapValoresLetras.put(24l, "X");
        mapValoresLetras.put(25l, "Y");
        mapValoresLetras.put(26l, "Z");
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("lotesdebonos");

        System.out.println("Buscando info de la solictud si existe");
        if (sessionBean.getAttributes("idsolicitudentregalotes") != null && (Integer) sessionBean.getAttributes("idsolicitudentregalotes") != 0) {
            Integer id = (Integer) sessionBean.getAttributes("idsolicitudentregalotes");
            elemento = sessionBean.marketingUserFacade.getSolicitudentregalotesbono(id);

        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("ListaSolicitudLotesBonosView.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(AceptarSolicitudEntregaBonosBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        casinos = sessionBean.adminFacade.findAllCasinos();
    }

    public Solicitudentregalotesmaestro getElemento() {
        return elemento;
    }

    public void setElemento(Solicitudentregalotesmaestro elemento) {
        this.elemento = elemento;
    }

    public void aceptar() {
        try {
            elemento.setEstado("INGRESADO A INVENTARIO");
            sessionBean.marketingUserFacade.cambiarEstadoSolicitudentregabonos(elemento);
            sessionBean.removeAttribute("idsolicitudentregalotes");
            String body = "Se ha aceptado la solicitud de entrada de lotes de bono con el numero de acta " + elemento.getId();
            Notificador.notificar(Notificador.SOLICITUD_ENTREGA_LOTES_ACEPTADA, body, "Solicitud de entrada de lotes de bono aceptada", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaSolicitudLotesBonosView.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(AceptarSolicitudEntregaBonosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enviar() {
        try {
            elemento.setEstado("ENVIADO A CAJA");
            elemento = sessionBean.marketingUserFacade.guardarSolicitudentregabonos(elemento, null, 0);
            sessionBean.setAttribute("idsolicitudentregalotes", elemento.getId());
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaSolicitudLotesBonosView.xhtml");
            sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Orden de ingreso aceptada con exito!", ""));
            Notificador.notificar(Notificador.SOLICITUD_ENTREGA_LOTES_ENVIADA, "Se ha recibido un lote de bonos", "Se ha recibido un lote de bonos con el nunmero de acta " + elemento.getId() + ". DEBE PROCESARSE EL INGRESO AL INVENTARIO. Favor revisar la lista de Ordenes de ingreso de lote de bonos.", "");
        } catch (IOException ex) {
            Logger.getLogger(GeneradorSolicitudLotesBonos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void devolver() {
        try {
            elemento.setEstado("DEVUELTO");
            elemento = sessionBean.marketingUserFacade.guardarSolicitudentregabonos(elemento, null, 0);
            sessionBean.setAttribute("idsolicitudentregalotes", elemento.getId());
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaSolicitudLotesBonosView.xhtml");
            sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Orden de ingreso devuelta!", ""));
            Notificador.notificar(Notificador.REQUERIMIENTO_LOTE_DEVUELTA, "Se recibió un requerimiento de lote de bonos", "Se ha recibido un requerimiento de bonos con el nunmero de acta " + elemento.getId() + ". Favor revisar la lista de Requerimientos de lotes", "");
        } catch (IOException ex) {
            Logger.getLogger(GeneradorSolicitudLotesBonos.class.getName()).log(Level.SEVERE, null, ex);
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

    public String getConsecutivo(String hasta, Integer cantidad) {
        int cantActual = Integer.parseInt(hasta.substring(0, 4));
        String letraActual = hasta.substring(5, hasta.length());
        if (cantidad == 0) {
            return "";
        } else {

            do {
                int cantComp = 9999 - cantActual;
                if (cantidad - cantComp <= 0) {
                    cantActual += cantidad;
                    break;
                } else {
                    cantidad -= 9999 - cantActual;
                    cantActual = 0;
                    letraActual = mapValoresLetras.get(mapLetrasValores.get(letraActual) + 1);
                }
            } while (true);
            cantActual -= 1;
            if (cantActual < 100) {
                System.out.println("consecutivo: " + cantActual + "-" + letraActual);
                return "00" + cantActual + "-" + letraActual;
            } else if (cantActual < 1000) {
                System.out.println("consecutivo: " + cantActual + "-" + letraActual);
                return "0" + cantActual + "-" + letraActual;
            } else {
                System.out.println("consecutivo: " + cantActual + "-" + letraActual);
                return cantActual + "-" + letraActual;
            }
        }
    }
    

    

}
