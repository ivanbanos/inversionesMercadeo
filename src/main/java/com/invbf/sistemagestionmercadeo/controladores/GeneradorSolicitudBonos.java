/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Area;
import com.invbf.sistemagestionmercadeo.entity.Bono;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Categoria;
import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Clienteblanco;
import com.invbf.sistemagestionmercadeo.entity.Controlsalidabono;
import com.invbf.sistemagestionmercadeo.entity.ControlsalidabonosHasLotesbonos;
import com.invbf.sistemagestionmercadeo.entity.ControlsalidabonosHasLotesbonosPK;
import com.invbf.sistemagestionmercadeo.entity.Lotebono;
import com.invbf.sistemagestionmercadeo.entity.Propositoentrega;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentrega;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregacliente;
import com.invbf.sistemagestionmercadeo.entity.Tipobono;
import com.invbf.sistemagestionmercadeo.entity.Tipojuego;
import com.invbf.sistemagestionmercadeo.util.CasinoBoolean;
import com.invbf.sistemagestionmercadeo.util.CategoriaBoolean;
import com.invbf.sistemagestionmercadeo.util.ClienteBlancoLotes;
import com.invbf.sistemagestionmercadeo.util.ClienteSGBDTO;
import com.invbf.sistemagestionmercadeo.util.ConvertidorConsecutivo;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import com.invbf.sistemagestionmercadeo.util.LotebonoCanti;
import com.invbf.sistemagestionmercadeo.util.MatematicaAplicada;
import com.invbf.sistemagestionmercadeo.util.Mensajes;
import com.invbf.sistemagestionmercadeo.util.MesAnno;
import com.invbf.sistemagestionmercadeo.util.Notificador;
import com.invbf.sistemagestionmercadeo.util.TipoJuegoBoolean;
import com.invbf.sistemagestionmercadeo.util.loteBonoSolicitud;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.primefaces.event.FlowEvent;

/**
 *
 * @author ivan
 */
@ManagedBean
@ViewScoped
public class GeneradorSolicitudBonos implements Serializable {

    private Solicitudentrega elemento;
    private List<Casino> casinos;
    private List<Tipobono> tiposbonos;
    private List<Propositoentrega> propositosentrega;
    private List<Area> areas;
    private List<Cliente> clientessgbs;
    private String nombres;
    private String apellidos;
    private List<Cliente> selectedClientessgbs;
    private List<ClienteSGBDTO> clientes;
    private List<ClienteSGBDTO> clientesFiltered;
    private List<Integer> clientesABorrar;
    private int mespumpe;

    private String pais;
    private String ciudad;
    private List<CasinoBoolean> casinoBooleans;
    private List<TipoJuegoBoolean> juegoBooleans;
    private List<CategoriaBoolean> categoriaBooleans;
    private boolean todoscasinos;
    private boolean todosCat;
    private boolean todostip;

    private Date fechaVencimiento;
    private int mes;
    private List<loteBonoSolicitud> loteBonoSolicitudes;

    private List<Cliente> clientesBuscados;
    private List<Cliente> clientesBuscadosSeleccionados;
    private String nombreBusqueda;
    private String apellidosBusqueda;
    private String identificacionBusqueda;
    private Clienteblanco clienteBlanco;
    private List<ClienteBlancoLotes> clientesBlancosLotes;
    private List<MesAnno> meses;
    private Date unmes;
    private Date dosmeses;
    private Date tresmeses;
    
    private Float totalMes1;
    private Float totalMes2;
    private Float totalMes3;

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public GeneradorSolicitudBonos() {
    }

    @PostConstruct
    public void init() {
        Calendar c2 = Calendar.getInstance();
        c2.add(Calendar.MONTH, -1);
        unmes = new Date(c2.getTime().getTime());
        c2.add(Calendar.MONTH, -1);
        dosmeses = new Date(c2.getTime().getTime());
        c2.add(Calendar.MONTH, -1);
        tresmeses = new Date(c2.getTime().getTime());
        loteBonoSolicitudes = new ArrayList<loteBonoSolicitud>();
        clientes = new ArrayList<ClienteSGBDTO>();
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("solicitudbonos");
        if (!sessionBean.perfilViewMatch("GenerarSolicitudBono")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }

        System.out.println("Buscando info de la solictud si existe");
        clientes = new ArrayList<ClienteSGBDTO>();
        clientesABorrar = new ArrayList<Integer>();
        if (sessionBean.getAttributes("idSolicitudentrega") != null && (Integer) sessionBean.getAttributes("idSolicitudentrega") != 0) {
            Integer id = (Integer) sessionBean.getAttributes("idSolicitudentrega");
            elemento = sessionBean.marketingUserFacade.getSolicitudbono(id);
            for (Solicitudentregacliente sec : elemento.getSolicitudentregaclienteList()) {
                clientes.add(new ClienteSGBDTO(sec.getValorTotal(), sec.getCliente(), sec.getAreaid()));
            }
        } else {
            try {
                elemento = new Solicitudentrega();
                elemento.setEstado("EN CREACION");
                DateFormat df = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
                DateFormat df2 = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
                TimeZone timeZone = TimeZone.getTimeZone("GMT-5");
                df.setTimeZone(timeZone);
                Calendar nowDate = Calendar.getInstance();
                nowDate.setTime(df2.parse(df.format(nowDate.getTime())));
                elemento.setFecha(nowDate.getTime());
                elemento.setPropositoEntrega(new Propositoentrega());
                elemento.setSolicitante(sessionBean.getUsuario());
                elemento.setTipoBono(new Tipobono());
                elemento.setSolicitudentregaclienteList(new ArrayList<Solicitudentregacliente>());
                elemento.setIdCasino(new Casino());
            } catch (ParseException ex) {
                Logger.getLogger(GeneradorSolicitudBonos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        casinos = sessionBean.getUsuario().getCasinoList();
        tiposbonos = sessionBean.adminFacade.findAllTiposbonos();
        propositosentrega = sessionBean.adminFacade.findAllPropositosentrega();

        areas = sessionBean.adminFacade.findAllAreas();
        clientessgbs = new ArrayList<Cliente>();
        selectedClientessgbs = new ArrayList<Cliente>();

        List<Tipojuego> tipoJuegos = sessionBean.marketingUserFacade.findAllTiposjuegos();
        List<Categoria> categorias = sessionBean.marketingUserFacade.findAllCategorias();
        juegoBooleans = new ArrayList<TipoJuegoBoolean>();
        casinoBooleans = new ArrayList<CasinoBoolean>();
        categoriaBooleans = new ArrayList<CategoriaBoolean>();
        for (Tipojuego tipoJuego : tipoJuegos) {
            juegoBooleans.add(new TipoJuegoBoolean(tipoJuego, false));
        }
        for (Casino casinob : casinos) {
            casinoBooleans.add(new CasinoBoolean(casinob, false));
        }
        for (Categoria categoria : categorias) {
            categoriaBooleans.add(new CategoriaBoolean(categoria, false));
        }
        Calendar c = Calendar.getInstance();
        mes = c.get(Calendar.MONTH);
        mespumpe = 12;
        int anio = c.get(Calendar.YEAR);
        meses = new ArrayList<MesAnno>();
        switch (mes) {
            case 0:
                meses.add(new MesAnno(0, "Enero " + anio));
                meses.add(new MesAnno(1, "Febrero " + anio));
                break;
            case 1:
                meses.add(new MesAnno(1, "Febrero " + anio));
                meses.add(new MesAnno(2, "Marzo " + anio));
                break;
            case 2:
                meses.add(new MesAnno(2, "Marzo " + anio));
                meses.add(new MesAnno(3, "Abril " + anio));
                break;
            case 3:
                meses.add(new MesAnno(3, "Abril " + anio));
                meses.add(new MesAnno(4, "Mayo " + anio));
                break;
            case 4:
                meses.add(new MesAnno(4, "Mayo " + anio));
                meses.add(new MesAnno(5, "Junio " + anio));
                break;
            case 5:
                meses.add(new MesAnno(5, "Junio " + anio));
                meses.add(new MesAnno(6, "Julio " + anio));
                break;
            case 6:
                meses.add(new MesAnno(6, "Julio " + anio));
                meses.add(new MesAnno(7, "Agosto " + anio));
                break;
            case 7:
                meses.add(new MesAnno(7, "Agosto " + anio));
                meses.add(new MesAnno(8, "Septiembre " + anio));
                break;
            case 8:
                meses.add(new MesAnno(8, "Septiembre " + anio));
                meses.add(new MesAnno(9, "Octubre " + anio));
                break;
            case 9:
                meses.add(new MesAnno(9, "Octubre " + anio));
                meses.add(new MesAnno(10, "Noviembre " + anio));
                break;
            case 10:
                meses.add(new MesAnno(10, "Noviembre " + anio));
                meses.add(new MesAnno(11, "Diciembre " + anio));
                break;
            case 11:
                meses.add(new MesAnno(11, "Diciembre " + anio));
                meses.add(new MesAnno(0, "Enero " + (anio + 1)));
                break;

        }

        clientesBuscados = new ArrayList<Cliente>();
        clientesBuscadosSeleccionados = new ArrayList<Cliente>();
        clienteBlanco = new Clienteblanco();
        clienteBlanco.setIdCliente(new Cliente());
        clientesBlancosLotes = new ArrayList<ClienteBlancoLotes>();
    }

    public Solicitudentrega getElemento() {
        return elemento;
    }

    public void setElemento(Solicitudentrega elemento) {

        boolean cambiocasino = this.elemento.getIdCasino().equals(elemento.getIdCasino());

        this.elemento = elemento;
        if (cambiocasino) {
            this.elemento.getSolicitudentregaclienteList().clear();
            busquedaClientes();
        }
    }

    public void guardar() {
        guardar:
        {
            try {
                Controlsalidabono control = new Controlsalidabono();
                Calendar c = Calendar.getInstance();
                int act = c.get(Calendar.MONTH);
                if (act > mes) {
                    c.set(Calendar.YEAR, c.get(Calendar.YEAR) + 1);
                }
                c.set(Calendar.MONTH, mes);
                c.add(Calendar.MONTH, 1);
                c.set(Calendar.DAY_OF_MONTH, 1);
                c.add(Calendar.DATE, -1);
                elemento.setFechavencimientobonos(c.getTime());

                elemento.setEstado("CREADA");
                if (elemento.getTipoBono().getNombre().equals("PROMOCIONAL") && elemento.getPropositoEntrega().getNombre().equals("PROMOCIÓN")) {
                    List<ControlsalidabonosHasLotesbonos> csl = new ArrayList<ControlsalidabonosHasLotesbonos>();
                    for (loteBonoSolicitud lbs : loteBonoSolicitudes) {
                        ControlsalidabonosHasLotesbonos element = new ControlsalidabonosHasLotesbonos();
                        element.setCantidad(lbs.getCantidad());
                        element.setCantA(lbs.getCantidad());
                        element.setCantPre(lbs.getCantidad());
                        element.setControlsalidabono(control);
                        element.setLotebono(lbs.getLotesBonosid());
                        csl.add(element);
                    }
                    control.setControlsalidabonosHasLotesbonosList(csl);
                } else if (elemento.getTipoBono().getNombre().equals("NO PROMOCIONAL") && elemento.getPropositoEntrega().getNombre().equals("FIDELIZACIÓN")) {

                    List<Solicitudentregacliente> solicitudentregaclienteses = new ArrayList<Solicitudentregacliente>();
                    for (ClienteSGBDTO clientesGBT : clientes) {
                        if (clientesGBT.getValorTotal() != 0) {
                            Solicitudentregacliente sec = new Solicitudentregacliente();
                            sec.setCliente(clientesGBT.getClientessgb());
                            sec.setValorTotal(clientesGBT.getValorTotal());
                            sec.setValorPreAprobado(clientesGBT.getValorTotal());
                            sec.setValorAprobado(clientesGBT.getValorTotal());
                            sec.setEntrega(clientesGBT.getForma());
                            solicitudentregaclienteses.add(sec);
                        }
                    }
                    elemento.setSolicitudentregaclienteList(solicitudentregaclienteses);

                    List<ControlsalidabonosHasLotesbonos> csl = new ArrayList<ControlsalidabonosHasLotesbonos>();
                    for (loteBonoSolicitud lbs : loteBonoSolicitudes) {
                        ControlsalidabonosHasLotesbonos element = new ControlsalidabonosHasLotesbonos();
                        element.setCantidad(lbs.getCantidad());
                        element.setCantPre(lbs.getCantidad());
                        element.setCantA(lbs.getCantidad());
                        element.setControlsalidabono(control);
                        element.setLotebono(lbs.getLotesBonosid());
                        element.setClienteblancoList(new ArrayList<Clienteblanco>());
                        csl.add(element);
                    }

                    control.setControlsalidabonosHasLotesbonosList(csl);
                } else if (elemento.getTipoBono().getNombre().equals("NO PROMOCIONAL") && elemento.getPropositoEntrega().getNombre().equals("CASOS ESPECIALES")) {
                    List<ControlsalidabonosHasLotesbonos> csl = new ArrayList<ControlsalidabonosHasLotesbonos>();
                    for (loteBonoSolicitud lbs : loteBonoSolicitudes) {
                        ControlsalidabonosHasLotesbonos element = new ControlsalidabonosHasLotesbonos();
                        element.setCantidad(lbs.getCantidad());
                        element.setCantPre(lbs.getCantidad());
                        element.setCantA(lbs.getCantidad());
                        element.setControlsalidabono(control);
                        element.setLotebono(lbs.getLotesBonosid());
                        element.setClienteblancoList(new ArrayList<Clienteblanco>());
                        csl.add(element);
                    }
                    for (ClienteBlancoLotes cbl : clientesBlancosLotes) {
                        System.out.println(cbl.getLotesBono().size());
                        for (LotebonoCanti lb : cbl.getLotesBono()) {
                            for (ControlsalidabonosHasLotesbonos csl1 : csl) {
                                if (csl1.getLotebono().equals(lb.getLotesBono())) {
                                    Clienteblanco cb = new Clienteblanco();
                                    cb.setApellidos(cbl.getClienteblanco().getApellidos());
                                    cb.setNombres(cbl.getClienteblanco().getNombres());
                                    cb.setCantidad(lb.getCantidad());
                                    cb.setCantPre(lb.getCantidad());
                                    cb.setCantA(lb.getCantidad());
                                    cb.setIdCliente(cbl.getClienteblanco().getIdCliente().getIdCliente() == null ? null : cbl.getClienteblanco().getIdCliente());
                                    cb.setJustificacion(cbl.getClienteblanco().getJustificacion());

                                    csl1.getClienteblancoList().add(cb);
                                    csl1.setCantidad(csl1.getCantidad() + lb.getCantidad());
                                    csl1.setCantA(csl1.getCantidad() + lb.getCantidad());
                                    csl1.setCantPre(csl1.getCantidad() + lb.getCantidad());
                                }
                            }
                        }
                    }

                    control.setControlsalidabonosHasLotesbonosList(csl);

                } else {
                    FacesUtil.addErrorMessage("Seleccionó un proposito y un tipo de bono incompatible", "");
                }
                elemento = sessionBean.marketingUserFacade.guardarSolicitudentrega(elemento, clientesABorrar);
                String body = "Se ha creado una solicitud de bonos con el número de acta " + elemento.getId()
                        + ".\nPor favor revisar la Lista de solicitudes de bonos.";

                control.setEstado("PRESOLICITADA");
                control.setFechavencimientobonos(elemento.getFechavencimientobonos());
                control.setSolicitudEntregaid(elemento);
                control = sessionBean.marketingUserFacade.guardarControlSalidaBonos(control, false);
                Notificador.notificar(Notificador.SOLICITUD_BONOS_GENERADA, body, "Solicitud de bonos generada", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
                sessionBean.registrarlog(null, null, "Generada solicitud Usuario:" + sessionBean.getUsuario().getNombreUsuario());
                sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Solicitud generada con exito!", "Notificación enviada"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("ListaSolicitudBono.xhtml");
                FacesUtil.addInfoMessage("Solicitud guardada con exito!", "Notificación enviada");
                sessionBean.setAttribute("idSolicitudentrega", elemento.getId());
            } catch (IOException ex) {
                Logger.getLogger(GeneradorSolicitudBonos.class.getName()).log(Level.SEVERE, null, ex);
            }
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

    public List<Casino> getCasinos() {
        return casinos;
    }

    public void setCasinos(List<Casino> casinos) {
        this.casinos = casinos;
    }

    public List<Tipobono> getTiposbonos() {
        return tiposbonos;
    }

    public void setTiposbonos(List<Tipobono> tiposbonos) {
        this.tiposbonos = tiposbonos;
    }

    public List<Propositoentrega> getPropositosentrega() {
        return propositosentrega;
    }

    public void setPropositosentrega(List<Propositoentrega> propositosentrega) {
        this.propositosentrega = propositosentrega;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public List<Cliente> getClientessgbs() {
        return clientessgbs;
    }

    public void setClientessgbs(List<Cliente> clientessgbs) {
        this.clientessgbs = clientessgbs;
    }

    public void busquedaClientes() {

        System.out.println("entra");
        clientessgbs = sessionBean.marketingUserFacade.findAllClientesCasinosConCupo(elemento.getIdCasino());

        boolean noCatselected = true;
        boolean noTipselected = true;
        boolean noCasselected = true;
        for (CasinoBoolean cb : casinoBooleans) {
            if (todoscasinos) {
                cb.setSelected(true);
                continue;
            }
            if (cb.isSelected()) {
                noCasselected = false;
                break;
            }
        }
        for (CategoriaBoolean cb : categoriaBooleans) {
            if (todosCat) {
                cb.setSelected(true);
                continue;
            }
            if (cb.isSelected()) {
                noCatselected = false;
                break;
            }
        }
        for (TipoJuegoBoolean tjb : juegoBooleans) {
            if (todostip) {
                tjb.setSelected(true);
                continue;
            }
            if (tjb.isSelected()) {
                noTipselected = false;
                break;
            }
        }

        for (Iterator<Cliente> it = clientessgbs.iterator(); it.hasNext();) {
            Cliente cliente = it.next();

            boolean siCategoria = false;
            boolean siTipoJuego = false;
            boolean siCasino = false;
            if (cliente.getIdCasinoPreferencial().equals(elemento.getIdCasino())) {
                siCasino = true;
                break;
            }
            if (noCatselected) {
                siCategoria = true;
            } else {
                for (CategoriaBoolean cb : categoriaBooleans) {
                    if (cb.isSelected()) {
                        if (cliente.getIdCategorias().equals(cb.getCategoria())) {
                            siCategoria = true;
                            break;
                        }
                    }
                }
            }
            if (noTipselected) {
                siTipoJuego = true;
            } else {
                for (TipoJuegoBoolean tjb : juegoBooleans) {
                    if (tjb.isSelected()) {
                        for (Tipojuego tj : cliente.getTipojuegoList()) {
                            if (tj.equals(tjb.getTipoJuego())) {
                                siTipoJuego = true;
                                break;
                            }
                        }
                    }
                }
            }

            if (!siCategoria) {
                it.remove();
            }
            if (!siTipoJuego) {
                it.remove();
            }
            if (!siCasino) {
                it.remove();
            }
            if (ciudad != null && !ciudad.equals("")) {
                if (!cliente.getCiudad().contains(ciudad)) {
                    it.remove();
                }
            }
            if (pais != null && !pais.equals("")) {
                if (!cliente.getPais().contains(pais)) {
                    it.remove();
                }
            }

        }
        System.out.println(clientessgbs.size());
        clientessgbs = sessionBean.marketingUserFacade.findAllClientesCasinosConCupo(elemento.getIdCasino());
        creadorClientesSolicitud();
    }

    public List<Cliente> getSelectedClientessgbs() {
        return selectedClientessgbs;
    }

    public void setSelectedClientessgbs(List<Cliente> selectedClientessgbs) {
        this.selectedClientessgbs = selectedClientessgbs;
    }

    public void creadorClientesSolicitud() {
        totalMes1=0f;
        totalMes2=0f;
        totalMes3=0f;
        for (Cliente selected : clientessgbs) {
            boolean existe = false;
            for (ClienteSGBDTO sec : clientes) {
                if (sec.getClientessgb().equals(selected)) {
                    existe = true;
                    break;
                }
            }
            System.out.println("Este cliente, " + selected.getNombres() + " " + selected.getApellidos() + ", existes? " + existe);
            if (!existe) {
                
                ClienteSGBDTO sec = new ClienteSGBDTO();
                sec.setClientessgb(selected);
                sec.setBono(Float.parseFloat(selected.getBonoFidelizacion()));
                sec.setValorTotal(Float.parseFloat(selected.getBonoFidelizacion()));
                System.out.println(selected.getSolicitudentregaclienteList() != null ? selected.getSolicitudentregaclienteList().size() : "nulo");
                if (selected.getSolicitudentregaclienteList() != null && selected.getSolicitudentregaclienteList().size() > 0) {
                    Float valor = 0f;
                    int idSol = selected.getSolicitudentregaclienteList().get(selected.getSolicitudentregaclienteList().size() - 1).getSolicitudentrega().getId();
                    for (Bono b : selected.getBonoList()) {
                        if (b.getControlSalidaBonosid().getSolicitudEntregaid().getId() == idSol && b.getEstado().equals("CANJEADO")) {
                            valor += b.getDenominacion().getValor();
                        }
                    }
                    sec.setUltimaSol(valor);
                    totalMes1+=valor;
                }
                if (selected.getSolicitudentregaclienteList() != null && selected.getSolicitudentregaclienteList().size() > 1) {
                    Float valor = 0f;
                    int idSol = selected.getSolicitudentregaclienteList().get(selected.getSolicitudentregaclienteList().size() - 2).getSolicitudentrega().getId();
                    for (Bono b : selected.getBonoList()) {
                        if (b.getControlSalidaBonosid().getSolicitudEntregaid().getId() == idSol && b.getEstado().equals("CANJEADO")) {
                            valor += b.getDenominacion().getValor();
                        }
                    }
                    sec.setPenultimaSol(valor);
                    totalMes2+=valor;
                }
                if (selected.getSolicitudentregaclienteList() != null && selected.getSolicitudentregaclienteList().size() > 2) {
                    Float valor = 0f;
                    int idSol = selected.getSolicitudentregaclienteList().get(selected.getSolicitudentregaclienteList().size() - 3).getSolicitudentrega().getId();
                    for (Bono b : selected.getBonoList()) {
                        if (b.getControlSalidaBonosid().getSolicitudEntregaid().getId() == idSol && b.getEstado().equals("CANJEADO")) {
                            valor += b.getDenominacion().getValor();
                        }
                    }
                    sec.setTrasultimaSol(valor);
                    totalMes3+=valor;
                }

                clientes.add(sec);
            }
        }
        System.out.println(clientes.size());
    }

    public void quitarCliente(Integer i) {
        System.out.println("id " + i);
        for (Iterator<ClienteSGBDTO> iterator = clientes.iterator(); iterator.hasNext();) {
            ClienteSGBDTO sec = iterator.next();
            System.out.println("id de este" + sec.getClientessgb().getIdCliente());
            if (sec.getClientessgb().getIdCliente().equals(i)) {
                iterator.remove();
                clientesABorrar.add(sec.getClientessgb().getIdCliente());
                break;
            }
        }
    }

    public List<ClienteSGBDTO> getClientes() {
        return clientes;
    }

    public void setClientes(List<ClienteSGBDTO> clientes) {
        this.clientes = clientes;
    }

    public List<Integer> getClientesABorrar() {
        return clientesABorrar;
    }

    public void setClientesABorrar(List<Integer> clientesABorrar) {
        this.clientesABorrar = clientesABorrar;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public List<CasinoBoolean> getCasinoBooleans() {
        return casinoBooleans;
    }

    public void setCasinoBooleans(List<CasinoBoolean> casinoBooleans) {
        this.casinoBooleans = casinoBooleans;
    }

    public List<TipoJuegoBoolean> getJuegoBooleans() {
        return juegoBooleans;
    }

    public void setJuegoBooleans(List<TipoJuegoBoolean> juegoBooleans) {
        this.juegoBooleans = juegoBooleans;
    }

    public List<CategoriaBoolean> getCategoriaBooleans() {
        return categoriaBooleans;
    }

    public void setCategoriaBooleans(List<CategoriaBoolean> categoriaBooleans) {
        this.categoriaBooleans = categoriaBooleans;
    }

    public boolean isTodoscasinos() {
        return todoscasinos;
    }

    public void setTodoscasinos(boolean todoscasinos) {
        this.todoscasinos = todoscasinos;
    }

    public boolean isTodosCat() {
        return todosCat;
    }

    public void setTodosCat(boolean todosCat) {
        this.todosCat = todosCat;
    }

    public boolean isTodostip() {
        return todostip;
    }

    public void setTodostip(boolean todostip) {
        this.todostip = todostip;
    }

    public String onFlowProcess(FlowEvent event) {
        elemento.setTipoBono(sessionBean.adminFacade.findTipobono(elemento.getTipoBono().getId()));
        elemento.setPropositoEntrega(sessionBean.adminFacade.findProposito(elemento.getPropositoEntrega().getId()));
        if (elemento.getIdCasino().getIdCasino() != null) {
            for (Casino casino : casinos) {
                if (elemento.getIdCasino().getIdCasino().equals(casino.getIdCasino())) {
                    elemento.setIdCasino(casino);
                    break;
                }
            }
        }

        if (elemento.getTipoBono().getNombre().equals("PROMOCIONAL") && elemento.getPropositoEntrega().getNombre().equals("PROMOCIÓN")) {
            if (event.getOldStep().equals("general")) {
                List<Lotebono> lotesbonoses = sessionBean.marketingUserFacade.getLotesBonosByCasinoPromo(elemento.getIdCasino());
                loteBonoSolicitudes = new ArrayList<loteBonoSolicitud>();
                for (Lotebono lotesbonose : lotesbonoses) {
                    loteBonoSolicitudes.add(new loteBonoSolicitud(lotesbonose));
                }
                System.out.println(loteBonoSolicitudes.size());
                return "promocional";
            }
            if (event.getOldStep().equals("confirmar")) {
                return "promocional";
            }
            if (event.getOldStep().equals("promocional")) {
                if (event.getNewStep().equals("casosesp")) {
                    return "general";
                } else {
                    boolean isempty = true;
                    for (loteBonoSolicitud lbs : loteBonoSolicitudes) {
                        if (lbs.getCantidad() != 0) {
                            isempty = false;
                        }
                    }
                    if (isempty) {
                        FacesUtil.addErrorMessage("No se puede guardar la solicitud", "No se puede crear una solicitud sin contenido");
                        return "promocional";
                    }
                    return "confirmar";
                }
            }
        } else if (elemento.getTipoBono().getNombre().equals("NO PROMOCIONAL") && elemento.getPropositoEntrega().getNombre().equals("FIDELIZACIÓN")) {
            if (event.getOldStep().equals("general")) {

                clientessgbs = sessionBean.marketingUserFacade.findAllClientesCasinosConCupo(elemento.getIdCasino());
                creadorClientesSolicitud();
                return "fidelizacion";
            }
            if (event.getOldStep().equals("confirmar")) {
                return "fidelizacion";
            }
            if (event.getOldStep().equals("fidelizacion")) {
                elemento.setJustificacion("Fidelización de Clientes.");
                List<Lotebono> lotesbonoses = sessionBean.marketingUserFacade.getLotesBonosByCasinoNoPromo(elemento.getIdCasino());
                loteBonoSolicitudes = new ArrayList<loteBonoSolicitud>();
                for (Lotebono lotesbonose : lotesbonoses) {
                    loteBonoSolicitudes.add(new loteBonoSolicitud(lotesbonose));
                }
                List<Lotebono> lotes = sessionBean.marketingUserFacade.getLotesBonosCasinoTipoBono(elemento.getIdCasino().getIdCasino(), elemento.getTipoBono());
                Float[] denominaciones = new Float[lotes.size()];
                int i = 0;
                for (Lotebono lote : lotes) {
                    denominaciones[i] = lote.getDenominacion().getValor();
                    i++;
                }
                for (ClienteSGBDTO clientesGBT : clientes) {
                    if (clientesGBT.getValorTotal() != 0) {
                        if (MatematicaAplicada.sePuedeLleagar(clientesGBT.getValorTotal(), denominaciones)) {
                        } else {
                            FacesUtil.addErrorMessage("No se puede guardar la solicitud", "monto de clientes no es congruente con las denominaciones");
                            return "fidelizacion";
                        }
                    }
                }
                if (event.getNewStep().equals("general")) {
                    return "general";
                } else {
                    boolean isempty = true;
                    for (ClienteSGBDTO clientesGBT : clientes) {
                        if (clientesGBT.getValorTotal() != 0) {
                            isempty = false;
                        }
                    }
                    if (isempty) {
                        FacesUtil.addErrorMessage("No se puede guardar la solicitud", "No se puede crear una solicitud sin contenido");
                        return "fidelizacion";
                    }
                    return "confirmar";
                }
            }
        } else if (elemento.getTipoBono().getNombre().equals("NO PROMOCIONAL") && elemento.getPropositoEntrega().getNombre().equals("CASOS ESPECIALES")) {
            if (event.getOldStep().equals("general")) {
                List<Lotebono> lotesbonoses = sessionBean.marketingUserFacade.getLotesBonosByCasinoNoPromo(elemento.getIdCasino());
                loteBonoSolicitudes = new ArrayList<loteBonoSolicitud>();
                for (Lotebono lotesbonose : lotesbonoses) {
                    loteBonoSolicitudes.add(new loteBonoSolicitud(lotesbonose));
                }
                System.out.println(loteBonoSolicitudes.size());
                return "casosesp";
            }
            if (event.getOldStep().equals("confirmar")) {
                return "casosesp";
            }
            if (event.getOldStep().equals("casosesp")) {
                if (event.getNewStep().equals("fidelizacion")) {
                    return "general";
                } else {
                    boolean isempty = true;
                    for (ClienteBlancoLotes cbl : clientesBlancosLotes) {
                        for (LotebonoCanti lb : cbl.getLotesBono()) {
                            if (lb.getCantidad() != 0) {
                                isempty = false;
                            }
                        }
                    }
                    if (isempty) {
                        FacesUtil.addErrorMessage("No se puede guardar la solicitud", "No se puede crear una solicitud sin contenido");
                        return "casosesp";
                    }
                    return "confirmar";
                }
            }
        } else {
            FacesUtil.addErrorMessage("Seleccionó un proposito y un tipo de bono incompatible", "");
        }

        return event.getOldStep();

    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Float getTotal() {
        Float total = 0f;
        for (ClienteSGBDTO sec : clientes) {
            System.out.println(sec.getValorTotal());
            total += sec.getValorTotal();
        }
        return total;
    }

    public List<ClienteSGBDTO> getClientesFiltered() {
        return clientesFiltered;
    }

    public void setClientesFiltered(List<ClienteSGBDTO> clientesFiltered) {
        this.clientesFiltered = clientesFiltered;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public List<loteBonoSolicitud> getLoteBonoSolicitudes() {
        return loteBonoSolicitudes;
    }

    public void setLoteBonoSolicitudes(List<loteBonoSolicitud> loteBonoSolicitudes) {
        this.loteBonoSolicitudes = loteBonoSolicitudes;
    }

    public static String getCantidad(String desde, String hasta) {
        System.out.println("desde" + desde);
        System.out.println("hasta" + hasta);
        return (ConvertidorConsecutivo.getNumeroFromConsecutivo(hasta) - ConvertidorConsecutivo.getNumeroFromConsecutivo(desde)) + "";
    }

    public List<Cliente> getClientesBuscados() {
        return clientesBuscados;
    }

    public void setClientesBuscados(List<Cliente> clientesBuscados) {
        this.clientesBuscados = clientesBuscados;
    }

    public String getNombreBusqueda() {
        return nombreBusqueda;
    }

    public void setNombreBusqueda(String nombreBusqueda) {
        this.nombreBusqueda = nombreBusqueda;
    }

    public String getApellidosBusqueda() {
        return apellidosBusqueda;
    }

    public void setApellidosBusqueda(String apellidosBusqueda) {
        this.apellidosBusqueda = apellidosBusqueda;
    }

    public String getIdentificacionBusqueda() {
        return identificacionBusqueda;
    }

    public void setIdentificacionBusqueda(String identificacionBusqueda) {
        this.identificacionBusqueda = identificacionBusqueda;
    }

    public void BuscarClientes() {
        clientesBuscados = sessionBean.marketingUserFacade.findAllClientesCasinos(elemento.getIdCasino(), nombreBusqueda, apellidosBusqueda, identificacionBusqueda, null,"");
        for (Iterator<Cliente> it = clientesBuscados.iterator(); it.hasNext();) {
            Cliente cliente = it.next();

            if (mespumpe != 12) {
                Calendar fecha = Calendar.getInstance();
                if (cliente.getCumpleanos() == null) {
                    it.remove();
                    continue;
                }
                fecha.setTime(cliente.getCumpleanos());
                if (fecha.get(Calendar.MONTH) != mespumpe) {
                    it.remove();
                }
            }
        }
        FacesUtil.addInfoMessage("Clientes encontrados", "");

    }

    public void selectCliente() {
        clienteBlanco.setApellidos(clienteBlanco.getApellidos().toUpperCase());
        clienteBlanco.setNombres(clienteBlanco.getNombres().toUpperCase());
        clientesBlancosLotes.add(new ClienteBlancoLotes(clienteBlanco, loteBonoSolicitudes));

        clienteBlanco = new Clienteblanco();
        clienteBlanco.setIdCliente(new Cliente());

    }

    public void RemoveCliente() {
        clientesBlancosLotes.remove(new ClienteBlancoLotes(clienteBlanco));
    }

    public Clienteblanco getClienteBlanco() {
        return clienteBlanco;
    }

    public void setClienteBlanco(Clienteblanco clienteBlanco) {
        this.clienteBlanco = clienteBlanco;
    }

    public void crearClienteBlanco() {
        for (Cliente clienteSeleccionado : clientesBuscadosSeleccionados) {
            clienteBlanco = new Clienteblanco();
            clienteBlanco.setIdCliente(clienteSeleccionado);
            clienteBlanco.setNombres(clienteSeleccionado.getNombres());
            clienteBlanco.setApellidos(clienteSeleccionado.getApellidos());
            clientesBlancosLotes.add(new ClienteBlancoLotes(clienteBlanco, loteBonoSolicitudes));
            clienteBlanco = new Clienteblanco();
            clienteBlanco.setIdCliente(new Cliente());

        }
    }

    public List<ClienteBlancoLotes> getClientesBlancosLotes() {
        return clientesBlancosLotes;
    }

    public void setClientesBlancosLotes(List<ClienteBlancoLotes> clientesBlancosLotes) {
        this.clientesBlancosLotes = clientesBlancosLotes;
    }

    public List<MesAnno> getMeses() {
        return meses;
    }

    public void setMeses(List<MesAnno> meses) {
        this.meses = meses;
    }

    public float getTotalpromo() {
        float total = 0;
        for (loteBonoSolicitud lbs : loteBonoSolicitudes) {
            total += lbs.getCantidad() * lbs.getLotesBonosid().getDenominacion().getValor();
        }
        return total;
    }

    public int getMespumpe() {
        return mespumpe;
    }

    public void setMespumpe(int mespumpe) {
        this.mespumpe = mespumpe;
    }

    public List<Cliente> getClientesBuscadosSeleccionados() {
        return clientesBuscadosSeleccionados;
    }

    public void setClientesBuscadosSeleccionados(List<Cliente> clientesBuscadosSeleccionados) {
        this.clientesBuscadosSeleccionados = clientesBuscadosSeleccionados;
    }

    public Date getUnmes() {
        return unmes;
    }

    public void setUnmes(Date unmes) {
        this.unmes = unmes;
    }

    public Date getDosmeses() {
        return dosmeses;
    }

    public void setDosmeses(Date dosmeses) {
        this.dosmeses = dosmeses;
    }

    public Date getTresmeses() {
        return tresmeses;
    }

    public void setTresmeses(Date tresmeses) {
        this.tresmeses = tresmeses;
    }

    public Float getTotalMes1() {
        return totalMes1;
    }

    public void setTotalMes1(Float totalMes1) {
        this.totalMes1 = totalMes1;
    }

    public Float getTotalMes2() {
        return totalMes2;
    }

    public void setTotalMes2(Float totalMes2) {
        this.totalMes2 = totalMes2;
    }

    public Float getTotalMes3() {
        return totalMes3;
    }

    public void setTotalMes3(Float totalMes3) {
        this.totalMes3 = totalMes3;
    }

}
