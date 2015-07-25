/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Solicitudentrega;
import com.invbf.sistemagestionmercadeo.entity.Vista;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author ivan
 */
@ManagedBean
@ViewScoped
public class NotificacionesBean implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<String> vistasPerfil;
    long requerimientoProduccionLotes;
    long ingresoloteinventario;
    long preaprobarsolicitud;
    long aprobarsolicitud;
    long confimacionordenretiro;
    long confirmarrecepcion;
    long diligenciar;
    long recibirsala;
    long edicioncliente;
    long pendientereporte;
    long totalPendiente;

    long ordenGenerar;
    long ordenAprobar;
    long ordenRecibir;
    long recibirBarajaCaja;

    long solicitudEntregadasNuevas;
    long solicitudEntregadasUsadas;
    long solicitudRecibirNuevas;
    long solicitudRecibirUsadas;
    long barajasPorDestruir;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public NotificacionesBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        vistasPerfil = new ArrayList<String>();
        requerimientoProduccionLotes = 0;
        ingresoloteinventario = 0;
        preaprobarsolicitud = 0;
        aprobarsolicitud = 0;
        confimacionordenretiro = 0;
        confirmarrecepcion = 0;
        diligenciar = 0;
        recibirsala = 0;
        edicioncliente = 0;
        totalPendiente = 0;
        
        ordenGenerar = 0;
        ordenAprobar = 0;
        ordenRecibir = 0;
        recibirBarajaCaja = 0;
        
        solicitudEntregadasNuevas = 0;
        solicitudEntregadasUsadas = 0;
        solicitudRecibirNuevas = 0;
        solicitudRecibirUsadas = 0;
        barajasPorDestruir = 0;
        for (Vista v : sessionBean.getUsuario().getIdPerfil().getVistaList()) {
            vistasPerfil.add(v.getNombreVista());
        }
        if (vistasPerfil.contains("ordenarRequerimineto") || vistasPerfil.contains("rechazarRequerimiento")) {
            requerimientoProduccionLotes = sessionBean.sessionFacade.getnumrequerimientosolicitado();
            totalPendiente += requerimientoProduccionLotes;
        }
        if (vistasPerfil.contains("SolicitudLotes") || vistasPerfil.contains("devolverSolLote")) {

            ingresoloteinventario = sessionBean.sessionFacade.getnumingresoloteinventario();
            totalPendiente += ingresoloteinventario;
        }
        if (vistasPerfil.contains("PreAprobarSolicitudBono")) {

            preaprobarsolicitud = sessionBean.sessionFacade.getnumpreaprobarsolicitud();
            System.out.println("Solicitudes por preaprobar: " + preaprobarsolicitud);
            totalPendiente += preaprobarsolicitud;
        }
        if (vistasPerfil.contains("AprobarSolicitudBono")) {

            aprobarsolicitud = sessionBean.sessionFacade.getnumaprobarsolicitud();
            System.out.println("Solicitudes por aprobar: " + aprobarsolicitud);
            totalPendiente += aprobarsolicitud;
        }
        if (vistasPerfil.contains("Controlsalidabonos")) {

            confimacionordenretiro = sessionBean.sessionFacade.getnumconfimacionordenretiro();
            totalPendiente += confimacionordenretiro;
        }
        if (vistasPerfil.contains("Verbonosporverificar")) {

            confirmarrecepcion = sessionBean.sessionFacade.getnumconfirmarrecepcion();
            totalPendiente += confirmarrecepcion;
        }
        if (vistasPerfil.contains("Verificarbono")) {

            diligenciar = sessionBean.sessionFacade.getnumdiligenciar();
            totalPendiente += diligenciar;
        }
        if (vistasPerfil.contains("Recibirbono")) {

            recibirsala = 0;
            List<Solicitudentrega> lista = sessionBean.marketingUserFacade.getAllSolicitudentregaCasino(sessionBean.getUsuario());
            for (Solicitudentrega sol : lista) {
                if (sol.getEstado().equals("BONOS DILIGENCIADOS")) {
                    recibirsala++;
                }
            }
            totalPendiente += recibirsala;
        }
        if (vistasPerfil.contains("GenerarSolicitudBono")) {

            pendientereporte = 0;
            List<Solicitudentrega> lista = sessionBean.marketingUserFacade.getAllSolicitudentregaSolicitanteVENC(sessionBean.getUsuario().getIdUsuario());
            for (Solicitudentrega sol : lista) {
                if (sol.getEstado().equals("BONOS VENCIDOS. PENDIENTE POR GENERAR REPORTE")) {
                    pendientereporte++;
                }
            }
            totalPendiente += pendientereporte;
        }
        if (vistasPerfil.contains("notificaciones")) {

            edicioncliente = sessionBean.sessionFacade.getnumedicioncliente();
            totalPendiente += edicioncliente;
        }

        
        
        if (vistasPerfil.contains("generarOrdenBarajas")) {

            ordenGenerar = sessionBean.sessionFacade.getOrdenesGenerar();
            totalPendiente += ordenGenerar;
        }

        if (vistasPerfil.contains("aceptarOrdenBarajas")) {

            ordenAprobar = sessionBean.sessionFacade.getOrdenesAprobar();
            totalPendiente += ordenAprobar;
        }

        if (vistasPerfil.contains("generarOrdenBarajas")) {

            ordenRecibir = sessionBean.sessionFacade.getOredenesREcibir();
            totalPendiente += ordenRecibir;
        }

        if (vistasPerfil.contains("recibirOrdenBarajas")) {
            recibirBarajaCaja = sessionBean.sessionFacade.getNumRecibirBarajasCaja(sessionBean.getUsuario());
            totalPendiente += recibirBarajaCaja;
        }

        if (vistasPerfil.contains("entregarBarajas")) {

            System.out.println("solicitudEntregadasNuevas");
            solicitudEntregadasNuevas = sessionBean.sessionFacade.getEntregarBarajanuevas(sessionBean.getUsuario());
            totalPendiente += solicitudEntregadasNuevas;
            System.out.println(solicitudEntregadasNuevas);
        }

        if (vistasPerfil.contains("entregarBarajas")) {

            System.out.println("solicitudRecibirUsadas");
            solicitudRecibirUsadas = sessionBean.sessionFacade.getRecibirusadas(sessionBean.getUsuario());
            totalPendiente += solicitudRecibirUsadas;
            System.out.println(solicitudRecibirUsadas);
        }

        if (vistasPerfil.contains("recibirBarajas")) {

            System.out.println("solicitudEntregadasUsadas");
            solicitudEntregadasUsadas = sessionBean.sessionFacade.getEntregarUsadas(sessionBean.getUsuario());
            totalPendiente += solicitudEntregadasUsadas;
            System.out.println(solicitudEntregadasUsadas);
        }

        if (vistasPerfil.contains("recibirBarajas")) {

            System.out.println("solicitudRecibirNuevas");
            solicitudRecibirNuevas = sessionBean.sessionFacade.getREcibirNuevas(sessionBean.getUsuario());
            totalPendiente += solicitudRecibirNuevas;
            System.out.println(solicitudRecibirNuevas);
        }

        if (vistasPerfil.contains("destruirBarajas")) {

            barajasPorDestruir = sessionBean.sessionFacade.getIfDestruir(sessionBean.getUsuario());
            totalPendiente += barajasPorDestruir;
        }
    }

    public List<String> getVistasPerfil() {
        return vistasPerfil;
    }

    public void setVistasPerfil(List<String> vistasPerfil) {
        this.vistasPerfil = vistasPerfil;
    }

    public long getRequerimientoProduccionLotes() {
        return requerimientoProduccionLotes;
    }

    public void setRequerimientoProduccionLotes(long requerimientoProduccionLotes) {
        this.requerimientoProduccionLotes = requerimientoProduccionLotes;
    }

    public long getIngresoloteinventario() {
        return ingresoloteinventario;
    }

    public void setIngresoloteinventario(long ingresoloteinventario) {
        this.ingresoloteinventario = ingresoloteinventario;
    }

    public long getPreaprobarsolicitud() {
        return preaprobarsolicitud;
    }

    public void setPreaprobarsolicitud(long preaprobarsolicitud) {
        this.preaprobarsolicitud = preaprobarsolicitud;
    }

    public long getAprobarsolicitud() {
        return aprobarsolicitud;
    }

    public void setAprobarsolicitud(long aprobarsolicitud) {
        this.aprobarsolicitud = aprobarsolicitud;
    }

    public long getConfimacionordenretiro() {
        return confimacionordenretiro;
    }

    public void setConfimacionordenretiro(long confimacionordenretiro) {
        this.confimacionordenretiro = confimacionordenretiro;
    }

    public long getConfirmarrecepcion() {
        return confirmarrecepcion;
    }

    public void setConfirmarrecepcion(long confirmarrecepcion) {
        this.confirmarrecepcion = confirmarrecepcion;
    }

    public long getDiligenciar() {
        return diligenciar;
    }

    public void setDiligenciar(long diligenciar) {
        this.diligenciar = diligenciar;
    }

    public long getRecibirsala() {
        return recibirsala;
    }

    public void setRecibirsala(long recibirsala) {
        this.recibirsala = recibirsala;
    }

    public long getEdicioncliente() {
        return edicioncliente;
    }

    public void setEdicioncliente(long edicioncliente) {
        this.edicioncliente = edicioncliente;
    }

    public long getTotalPendiente() {
        return totalPendiente;
    }

    public void setTotalPendiente(long totalPendiente) {
        this.totalPendiente = totalPendiente;
    }

    public long getPendientereporte() {
        return pendientereporte;
    }

    public void setPendientereporte(long pendientereporte) {
        this.pendientereporte = pendientereporte;
    }

    public long getOrdenGenerar() {
        return ordenGenerar;
    }

    public void setOrdenGenerar(long ordenGenerar) {
        this.ordenGenerar = ordenGenerar;
    }

    public long getOrdenAprobar() {
        return ordenAprobar;
    }

    public void setOrdenAprobar(long ordenAprobar) {
        this.ordenAprobar = ordenAprobar;
    }

    public long getOrdenRecibir() {
        return ordenRecibir;
    }

    public void setOrdenRecibir(long ordenRecibir) {
        this.ordenRecibir = ordenRecibir;
    }

    public long getRecibirBarajaCaja() {
        return recibirBarajaCaja;
    }

    public void setRecibirBarajaCaja(long recibirBarajaCaja) {
        this.recibirBarajaCaja = recibirBarajaCaja;
    }

    public long getSolicitudEntregadasNuevas() {
        return solicitudEntregadasNuevas;
    }

    public void setSolicitudEntregadasNuevas(long solicitudEntregadasNuevas) {
        this.solicitudEntregadasNuevas = solicitudEntregadasNuevas;
    }

    public long getSolicitudEntregadasUsadas() {
        return solicitudEntregadasUsadas;
    }

    public void setSolicitudEntregadasUsadas(long solicitudEntregadasUsadas) {
        this.solicitudEntregadasUsadas = solicitudEntregadasUsadas;
    }

    public long getSolicitudRecibirNuevas() {
        return solicitudRecibirNuevas;
    }

    public void setSolicitudRecibirNuevas(long solicitudRecibirNuevas) {
        this.solicitudRecibirNuevas = solicitudRecibirNuevas;
    }

    public long getSolicitudRecibirUsadas() {
        return solicitudRecibirUsadas;
    }

    public void setSolicitudRecibirUsadas(long solicitudRecibirUsadas) {
        this.solicitudRecibirUsadas = solicitudRecibirUsadas;
    }

    public long getBarajasPorDestruir() {
        return barajasPorDestruir;
    }

    public void setBarajasPorDestruir(long barajasPorDestruir) {
        this.barajasPorDestruir = barajasPorDestruir;
    }

}
