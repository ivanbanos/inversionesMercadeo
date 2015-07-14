package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.controladores.SessionBean;
import com.invbf.sistemagestionmercadeo.entity.Bononoincluido;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregalote;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregalotesmaestro;
import com.invbf.sistemagestionmercadeo.util.BonosnoincluidosDTO;
import com.invbf.sistemagestionmercadeo.util.ConvertidorConsecutivo;
import com.invbf.sistemagestionmercadeo.util.Mensajes;
import com.invbf.sistemagestionmercadeo.util.Notificador;
import com.invbf.sistemagestionmercadeo.util.loteBonoSolicitud;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.StreamedContent;

@ManagedBean
@ViewScoped
public class VistaRequerimientoLoteBean implements Serializable {

    private Solicitudentregalotesmaestro elemento;
    private List<Casino> casinos;
    private List<loteBonoSolicitud> loteBonoSolicitudes;
    private Casino casinoSelected;

    private final HashMap<String, Long> mapLetrasValores;
    private final HashMap<Long, String> mapValoresLetras;
    private StreamedContent file;

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public VistaRequerimientoLoteBean() {
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
            loteBonoSolicitudes = new ArrayList<loteBonoSolicitud>();
            for (Solicitudentregalote sol : elemento.getSolicitudentregaloteList()) {
                loteBonoSolicitudes.add(new loteBonoSolicitud(sol.getId(), sol.getCantidad(), sol.getLotesBonosid(), null));
            }

        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(VistaRequerimientoLoteBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        sessionBean.printMensajes();
    }

    public Solicitudentregalotesmaestro getElemento() {
        return elemento;
    }

    public void setElemento(Solicitudentregalotesmaestro elemento) {
        this.elemento = elemento;
    }

    public void ordenar() {
        try {

            elemento.setEstado("EN PROCESO");
            elemento = sessionBean.marketingUserFacade.guardarSolicitudentregabonos(elemento, null, 0);
            sessionBean.setAttribute("idsolicitudentregalotes", elemento.getId());
            sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Requerimiento aprobado con exito!", ""));
            String body = "<div><Label>El siguiente lote de bonos ha sido aprobado por el gerente general para su producci&oacute;n:</label><br /></div>";
            body += "<div><br /><table border=\"1\" style=\"width:100%\"><tr><th>Cantidad</th><th>Desde</th><th>Hasta</th><th>Denominaci&oacute;n</th><th>Tipo de bono</th></tr>";
            for (loteBonoSolicitud lote : loteBonoSolicitudes) {
                body += "<tr>";
                body += "<td align=\"center\">" + lote.getCantidad() + "</td>";
                if (lote.getCantidad() != 0) {
                    if (lote.getLotesBonosid().getTipoBono().getNombre().equals("PROMOCIONAL")) {
                        body += "<td align=\"center\">" + lote.getLotesBonosid().getIdCasino().getCasinodetalle().getCiudad() + "-" + lote.getDesde() + "</td>";
                        body += "<td align=\"center\">" + lote.getLotesBonosid().getIdCasino().getCasinodetalle().getCiudad() + "-" + lote.getHasta() + "</td>";
                    } else {
                        body += "<td align=\"center\">" + lote.getLotesBonosid().getIdCasino().getCasinodetalle().getAbrenopromo() + "-" + lote.getDesde() + "</td>";
                        body += "<td align=\"center\">" + lote.getLotesBonosid().getIdCasino().getCasinodetalle().getAbrenopromo() + "-" + lote.getHasta() + "</td>";
                    }
                } else {
                    body += "<td></td>";
                    body += "<td></td>";
                }
                body += "<td align=\"center\">" + getAsString(lote.getLotesBonosid().getDenominacion().getValor()) + "</td>";
                body += "<td>" + lote.getLotesBonosid().getTipoBono().getNombre() + "</td>";
                body += "</tr>";
            }
            body += "</table><br /></div>";
            body += "<div><br /><Label>El administrador debe proceder a la contrataci&oacute;n con el proveedor seleccionado.</label><br /></div>";
            Notificador.notificar(Notificador.REQUERIMIENTO_LOTE_ORDENADO, body, "Requerimiento de lote de bonos aprobado", "");
            FacesContext.getCurrentInstance().getExternalContext().redirect("RequerimientosProduccionLotes.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(GeneradorSolicitudLotesBonos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void rechazar() {
        try {
            sessionBean.marketingUserFacade.borrarSolicitudentregabonos(elemento, null, 0);
            sessionBean.setAttribute("idsolicitudentregalotes", elemento.getId());
            sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Requerimeinto rechazado!", ""));
            Notificador.notificar(Notificador.REQUERIMIENTO_LOTE_RECHAZADO, "Se ha rechazado un requerimiento de bonos con el nunmero de acta " + elemento.getId() + ". Favor revisar la lista de Requerimientos de lotes", "Se rechazó un requerimiento de lote de bonos", "");

            FacesContext.getCurrentInstance().getExternalContext().redirect("RequerimientosProduccionLotes.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(GeneradorSolicitudLotesBonos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void recibir() {
        try {
            elemento.setEstado("LOTE RECIBIDO");
            
            for (Solicitudentregalote sol : elemento.getSolicitudentregaloteList()) {
                sol.getLotesBonosid().setHasta(ConvertidorConsecutivo.sumarCantidad(sol.getLotesBonosid().getHasta(), sol.getCantidad()+1));
                sessionBean.marketingUserFacade.editLoteBono(sol.getLotesBonosid(), sol.getBononoincluidoList());
            }
            elemento = sessionBean.marketingUserFacade.guardarSolicitudentregabonos(elemento, null, 0);
            sessionBean.setAttribute("idsolicitudentregalotes", elemento.getId());
            sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Requerimiento ordenado con exito!", ""));
            Notificador.notificar(Notificador.REQUERIMIENTO_LOTE_RECIBIDO, "Se recibió un requerimiento de lote de bonos", "Se ha recibido un requerimiento de bonos con el nunmero de acta " + elemento.getId() + ". Favor revisar la lista de Requerimientos de lotes", "");

            FacesContext.getCurrentInstance().getExternalContext().redirect("RequerimientosProduccionLotes.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(GeneradorSolicitudLotesBonos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getNombreDeUsuario(Integer id) {
        return sessionBean.sessionFacade.getNombreDeUsuario(id);
    }

    public List<loteBonoSolicitud> getLoteBonoSolicitudes() {
        return loteBonoSolicitudes;
    }

    public void setLoteBonoSolicitudes(List<loteBonoSolicitud> loteBonoSolicitudes) {
        this.loteBonoSolicitudes = loteBonoSolicitudes;
    }

    public List<Casino> getCasinos() {
        return casinos;
    }

    public void setCasinos(List<Casino> casinos) {
        this.casinos = casinos;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public Casino getCasinoSelected() {
        return casinoSelected;
    }

    public void setCasinoSelected(Casino casinoSelected) {
        this.casinoSelected = casinoSelected;
    }

    public void volver() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("RequerimientosProduccionLotes.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(VistaRequerimientoLoteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getAsString(Object o) {
        String numberseparated = "";
        String iPartS = "";
        boolean tieneelsimbolo = false;
        if (o instanceof Double) {
            double number = (Double) o;
            Long iPart = (long) number;
            iPartS = iPart.toString();
        } else if (o instanceof Float) {
            float number = (Float) o;
            Long iPart = (long) number;
            iPartS = iPart.toString();
        } else {
            String number;
            if (o instanceof Integer) {
                number = ((Integer) o).toString();
            } else {
                number = (String) o;
            }
            System.out.println("numero " + number);
            if (number.lastIndexOf(".") != -1) {
                iPartS = number.substring(0, number.lastIndexOf("."));
            } else {
                iPartS = number;
            }
        }
        boolean milesima = true;

        while (true) {
            System.out.println("asi va el numero " + numberseparated);
            System.out.println("y queda esto " + iPartS);
            if (iPartS.length() == 0) {
                break;
            } else if (iPartS.length() <= 3) {
                numberseparated = iPartS + numberseparated;
                iPartS = "";
            } else {
                if (milesima) {
                    numberseparated = "." + iPartS.substring(iPartS.length() - 3) + numberseparated;
                    iPartS = iPartS.substring(0, iPartS.length() - 3);
                    milesima = false;
                } else {
                    numberseparated = "'" + iPartS.substring(iPartS.length() - 3) + numberseparated;
                    iPartS = iPartS.substring(0, iPartS.length() - 3);
                    milesima = true;
                }
            }
        }
        System.out.println("asi quedo sin parte real " + numberseparated);
        System.out.println("asi quedo con parte real " + numberseparated);
        System.out.println();
        System.out.println();
        return numberseparated;
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
