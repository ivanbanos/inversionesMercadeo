/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregalote;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregalotesmaestro;
import com.invbf.sistemagestionmercadeo.util.Notificador;
import java.io.IOException;
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
public class AceptarSolicitudEntregaBonosBean {

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
        if (!sessionBean.perfilViewMatch("AceptarSolicitudLotesBonos")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }

        System.out.println("Buscando info de la solictud si existe");
        if (sessionBean.getAttributes().containsKey("idsolicitudentregalotes") && (Integer) sessionBean.getAttributes().get("idsolicitudentregalotes") != 0) {
            Integer id = (Integer) sessionBean.getAttributes().get("idsolicitudentregalotes");
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
            for (Solicitudentregalote sol : elemento.getSolicitudentregaloteList()) {
                sol.getLotesBonosid().setHasta(getConsecutivo(sol.getLotesBonosid().getHasta(), sol.getCantidad()));
                sessionBean.marketingUserFacade.editLoteBono(sol.getLotesBonosid(), sol.getBononoincluidoList());
            }
            elemento.setEstado("ACEPTADA");
            sessionBean.marketingUserFacade.cambiarEstadoSolicitudentregabonos(elemento);
            sessionBean.getAttributes().remove("idsolicitudentregalotes");
            String body = "Se a aceptado la solicitud de entrada de lotes de bono con el ID "+elemento.getId();
            Notificador.notificar(Notificador.SOLICITUD_ENTREGA_LOTES_ACEPTADA, body, "Solicitud de entrada de lotes de bono aceptada");
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaSolicitudLotesBonosView.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(AceptarSolicitudEntregaBonosBean.class.getName()).log(Level.SEVERE, null, ex);
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
        long numerofrom = Long.parseLong(hasta.substring(0, 4));
        long total = numerofrom + cantidad;
        String letra = hasta.substring(5, hasta.length());
        letra = sumarLetra(letra, total);
        total = total % 10000;
        if (total == 0) {
            System.out.println("consecutivo: "+ total + "-" + letra);
            return "000" + total + "-" + letra;
        } else if (total < 100) {
            System.out.println("consecutivo: "+ total + "-" + letra);
            return "00" + total + "-" + letra;
        } else if (total < 1000) {
            System.out.println("consecutivo: "+ total + "-" + letra);
            return "0" + total + "-" + letra;
        } else {
            System.out.println("consecutivo: "+ total + "-" + letra);
            return total + "-" + letra;
        }
    }

    private String sumarLetra(String letra, long total) {
        int factor = 0;
        long cantidad = 0l;
        String nuevaletra = "";
        for (int i = letra.length() - 1; i >= 0; i--) {
            cantidad += (long)(Math.pow(26, factor)) * mapLetrasValores.get(letra.substring(i, i + 1));
            factor += 1;

        }
        cantidad = (long)(cantidad + (total / 10000));
        String letraactual;
        while (cantidad != 0) {
            nuevaletra = mapValoresLetras.get(cantidad % 26) + nuevaletra;
            letraactual = mapValoresLetras.get(cantidad % 26);
            cantidad = (Long)cantidad / 26;
            if (letraactual.equals("Z") && cantidad == 1) {
                break;

            }
        }
        return nuevaletra;
    }

}
