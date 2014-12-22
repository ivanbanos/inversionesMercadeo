/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Bononoincluido;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Lotebono;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregalote;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregalotesmaestro;
import com.invbf.sistemagestionmercadeo.util.BonosnoincluidosDTO;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import com.invbf.sistemagestionmercadeo.util.loteBonoSolicitud;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
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
public class GeneradorSolicitudLotesBonos {

    private Solicitudentregalotesmaestro elemento;
    private List<Casino> casinos;
    private List<loteBonoSolicitud> loteBonoSolicitudes;

    private final HashMap<String, Long> mapLetrasValores;
    private final HashMap<Long, String> mapValoresLetras;

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public GeneradorSolicitudLotesBonos() {
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
        if (!sessionBean.perfilFormMatch("SolicitudLotes", "crear")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        if (sessionBean.getAttributes().containsKey("lotesbonosnoincluidos")) {
            loteBonoSolicitudes = (List<loteBonoSolicitud>) sessionBean.getAttributes().get("lotesbonosnoincluidos");
            sessionBean.getAttributes().remove("lotesbonosnoincluidos");
        } else {
            loteBonoSolicitudes = new ArrayList<loteBonoSolicitud>();
        }
        System.out.println("Buscando info de la solictud si existe");
        if (sessionBean.getAttributes().containsKey("idsolicitudentregalotes") && (Integer) sessionBean.getAttributes().get("idsolicitudentregalotes") != 0) {
            Integer id = (Integer) sessionBean.getAttributes().get("idsolicitudentregalotes");
            elemento = sessionBean.marketingUserFacade.getSolicitudentregalotesbono(id);
            if (loteBonoSolicitudes.isEmpty()) {
                for (Solicitudentregalote sel : elemento.getSolicitudentregaloteList()) {
                    List<BonosnoincluidosDTO> bniDtos = new ArrayList<BonosnoincluidosDTO>();
                    for (Bononoincluido bni : sel.getBononoincluidoList()) {
                        bniDtos.add(new BonosnoincluidosDTO(bni.getId(), bni.getConsecutivo()));
                    }
                    loteBonoSolicitudes.add(new loteBonoSolicitud(sel.getId(), sel.getCantidad(), sel.getLotesBonosid(), bniDtos));
                }
            }
        } else {
            elemento = new Solicitudentregalotesmaestro();
            elemento.setSolicitudentregaloteList(new ArrayList<Solicitudentregalote>(0));
            List<Lotebono> lotesbonoses = sessionBean.marketingUserFacade.getAllLotesBonos();
            List<Solicitudentregalote> solicitudesentregalotes = new ArrayList<Solicitudentregalote>();
            for (Lotebono lotesbonose : lotesbonoses) {
                loteBonoSolicitudes.add(new loteBonoSolicitud(lotesbonose));
            }
            elemento.setSolicitudentregaloteList(solicitudesentregalotes);
            System.out.println("Buscando Casinos");
            System.out.println("Encontrados Casinos");
        }

        casinos = sessionBean.adminFacade.findAllCasinos();
    }

    public Solicitudentregalotesmaestro getElemento() {
        return elemento;
    }

    public void setElemento(Solicitudentregalotesmaestro elemento) {
        this.elemento = elemento;
    }

    public void guardar() {
        try {
            elemento.setRemitente(sessionBean.getUsuario());
            elemento.setEstado("CREADA");
            DateFormat df = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
            DateFormat df2 = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
            TimeZone timeZone = TimeZone.getTimeZone("GMT-5");
            df.setTimeZone(timeZone);
            Calendar nowDate = Calendar.getInstance();
            nowDate.setTime(df2.parse(df.format(nowDate.getTime())));
            elemento.setFecha(nowDate.getTime());
            elemento.getSolicitudentregaloteList().clear();
            List<Integer> listaBonosReincluidos = new ArrayList<Integer>();
            for (loteBonoSolicitud lotes : loteBonoSolicitudes) {
                if (lotes.getCantidad() != 0) {
                    Solicitudentregalote sel = lotes.getSolicitudEntregaLote();
                    elemento.getSolicitudentregaloteList().add(sel);
                    listaBonosReincluidos.addAll(lotes.getBonosReincluidos());
                }
            }
            sessionBean.marketingUserFacade.guardarSolicitudentregabonos(elemento, listaBonosReincluidos);
            sessionBean.registrarlog(null, null, "Generada solicitud Usuario:" + sessionBean.getUsuario().getNombreUsuario());
            sessionBean.getAttributes().put("idsolicitudentregalotes", elemento.getId());
            FacesContext.getCurrentInstance().getExternalContext().redirect("GeneradorSolicitudLoteBono.xhtml");
            FacesUtil.addInfoMessage("Solicitud guardada con exito!", "Notificación enviada");
        } catch (ParseException ex) {
            Logger.getLogger(GeneradorSolicitudLotesBonos.class.getName()).log(Level.SEVERE, null, ex);
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

    public void addBonoNoIncluido(Integer i) {
        System.out.println("entra al menos" + loteBonoSolicitudes.get(i).getBonosnoincluidosList().size());
        BonosnoincluidosDTO bni = new BonosnoincluidosDTO();
        loteBonoSolicitudes.get(i).getBonosnoincluidosList().add(bni);
        sessionBean.getAttributes().put("lotesbonosnoincluidos", loteBonoSolicitudes);
    }

    private boolean isBonoDentro(Bononoincluido next, Solicitudentregalote solicitud) {
        String consecutivo = next.getConsecutivo();
        String desde = solicitud.getLotesBonosid().getDesde();
        System.out.println(consecutivo);
        System.out.println(desde);
        Integer cantidad = solicitud.getCantidad();
        Long consecutivonumero = getNumeroFromConsecutivo(consecutivo);
        Long desdenumero = getNumeroFromConsecutivo(desde);
        System.out.println(desdenumero);
        System.out.println(consecutivonumero);
        System.out.println(cantidad);
        return desdenumero <= consecutivonumero && consecutivonumero <= desdenumero + cantidad;
    }

    private Long getNumeroFromConsecutivo(String consecutivo) {
        long numerofrom = Long.parseLong(consecutivo.substring(0, 4));
        long total = numerofrom;
        Long cantidad = 0l;
        int factor = 0;
        String letra = consecutivo.substring(5, consecutivo.length());
        for (int i = letra.length() - 1; i >= 0; i--) {
            cantidad += (long) (Math.pow(26, factor)) * mapLetrasValores.get(letra.substring(i, i + 1));
            factor += 1;

        }
        return (cantidad * 10000) + total;
    }

    public List<loteBonoSolicitud> getLoteBonoSolicitudes() {
        return loteBonoSolicitudes;
    }

    public void setLoteBonoSolicitudes(List<loteBonoSolicitud> loteBonoSolicitudes) {
        this.loteBonoSolicitudes = loteBonoSolicitudes;
    }

}
