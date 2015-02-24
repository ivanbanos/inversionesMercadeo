/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Area;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Categoria;
import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Lotebono;
import com.invbf.sistemagestionmercadeo.entity.Propositoentrega;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentrega;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregacliente;
import com.invbf.sistemagestionmercadeo.entity.Tipobono;
import com.invbf.sistemagestionmercadeo.entity.Tipojuego;
import com.invbf.sistemagestionmercadeo.util.CasinoBoolean;
import com.invbf.sistemagestionmercadeo.util.CategoriaBoolean;
import com.invbf.sistemagestionmercadeo.util.ClienteSGBDTO;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import com.invbf.sistemagestionmercadeo.util.MatematicaAplicada;
import com.invbf.sistemagestionmercadeo.util.Notificador;
import com.invbf.sistemagestionmercadeo.util.TipoJuegoBoolean;
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
public class GeneradorSolicitudBonos implements Serializable{

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

    private String pais;
    private String ciudad;
    private List<CasinoBoolean> casinoBooleans;
    private List<TipoJuegoBoolean> juegoBooleans;
    private List<CategoriaBoolean> categoriaBooleans;
    private boolean todoscasinos;
    private boolean todosCat;
    private boolean todostip;

    private Date fechaVencimiento;

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public GeneradorSolicitudBonos() {
    }

    @PostConstruct
    public void init() {
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
        if (sessionBean.getAttributes("idSolicitudentrega")!=null && (Integer) sessionBean.getAttributes("idSolicitudentrega") != 0) {
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
            List<Lotebono> lotes = sessionBean.marketingUserFacade.getLotesBonosCasinoTipoBono(elemento.getIdCasino().getIdCasino(), elemento.getTipoBono());
            Float[] denominaciones = new Float[lotes.size()];
            int i = 0;
            for (Lotebono lote : lotes) {
                denominaciones[i] = lote.getDenominacion().getValor();
                i++;
            }

            if (elemento.getId() == null || elemento.getId().equals(0)) {
                elemento.setEstado("CREADA");
                List<Solicitudentregacliente> solicitudentregaclienteses = new ArrayList<Solicitudentregacliente>();
                for (ClienteSGBDTO clientesGBT : clientes) {
                    if (clientesGBT.getValorTotal() != 0) {
                        if (clientesGBT.getAreaid() == null) {
                            FacesUtil.addErrorMessage("Existen clientes con monto, sin el area ingresada", "Asegurece de que todos los clientes tengan el area selecionada");
                            break guardar;
                        }
                        Solicitudentregacliente sec = new Solicitudentregacliente();
                        sec.setAreaid(clientesGBT.getAreaid());
                        sec.setCliente(clientesGBT.getClientessgb());
                        sec.setValorTotal(clientesGBT.getValorTotal());
                        sec.setValorPreAprobado(clientesGBT.getValorTotal());
                        sec.setValorAprobado(clientesGBT.getValorTotal());
                        solicitudentregaclienteses.add(sec);
                    }
                }
                elemento.setSolicitudentregaclienteList(solicitudentregaclienteses);
                elemento = sessionBean.marketingUserFacade.guardarSolicitudentrega(elemento, clientesABorrar);
                String body = "Se ha creado una solicitud de bonos con el número de acta " + elemento.getId()
                        + ".\nPor favor revisar la pagina de Lista de solicitudes de bonos.";
                Notificador.notificar(Notificador.SOLICITUD_BONOS_GENERADA, body, "Solicitud de bonos generada", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
                sessionBean.registrarlog(null, null, "Generada solicitud Usuario:" + sessionBean.getUsuario().getNombreUsuario());
                FacesUtil.addInfoMessage("Solicitud guardada con exito!", "Notificación enviada");
            } else {
                try {

                    System.out.println("por que intenta guardar un area");

                    List<Solicitudentregacliente> solicitudentregaclienteses = new ArrayList<Solicitudentregacliente>();
                    for (ClienteSGBDTO clientesGBT : clientes) {
                        if (clientesGBT.getValorTotal() != 0) {
                            if (MatematicaAplicada.sePuedeLleagar(clientesGBT.getValorTotal(), denominaciones)) {
                                Solicitudentregacliente sec = new Solicitudentregacliente();
                                sec.setAreaid(clientesGBT.getAreaid());
                                sec.setCliente(clientesGBT.getClientessgb());
                                sec.setValorTotal(clientesGBT.getValorTotal());
                                sec.setValorPreAprobado(clientesGBT.getValorTotal());
                                sec.setValorAprobado(clientesGBT.getValorTotal());
                                solicitudentregaclienteses.add(sec);

                            } else {
                                FacesUtil.addErrorMessage("No se puede guardar la solicitud", "monto de clientes no es congruente con las denominaciones");
                                break guardar;
                            }
                        }
                    }
                    elemento.setSolicitudentregaclienteList(solicitudentregaclienteses);
                    elemento.setFormareparticrbonos(1);

                    System.out.println("entremos a ver");
                    sessionBean.marketingUserFacade.guardarSolicitudentrega(elemento, clientesABorrar);
                    sessionBean.registrarlog(null, null, "Generada solicitud Usuario:" + sessionBean.getUsuario().getNombreUsuario());

                    elemento.setSolicitante(sessionBean.getUsuario());
                    if (elemento.getControlsalidabonoList().isEmpty()) {
                        sessionBean.marketingUserFacade.crearSolicitudSalidaBonos(elemento, false);
                    }
                    FacesUtil.addInfoMessage("Solicitud guardada con exito!", "Notificación enviada");
                    FacesContext.getCurrentInstance().getExternalContext().redirect("ListaSolicitudBono.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(GeneradorSolicitudBonos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            sessionBean.setAttribute("idSolicitudentrega", elemento.getId());
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
        clientessgbs = sessionBean.marketingUserFacade.findAllClientesCasinos(elemento.getIdCasino(),"","","",null);

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

        creadorClientesSolicitud();
    }

    public List<Cliente> getSelectedClientessgbs() {
        return selectedClientessgbs;
    }

    public void setSelectedClientessgbs(List<Cliente> selectedClientessgbs) {
        this.selectedClientessgbs = selectedClientessgbs;
    }

    public void creadorClientesSolicitud() {
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
                sec.setAreaid(new Area());
                sec.setValorTotal(0f);
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
        System.out.println(elemento.getTipoBono().getId());
        System.out.println(elemento.getTipoBono().getNombre());

        elemento.setTipoBono(sessionBean.adminFacade.findTipobono(elemento.getTipoBono().getId()));
        System.out.println(elemento.getTipoBono().getNombre());
        System.out.println(elemento.getTipoBono().getNombre().equals("PROMOCIONAL"));
        if (elemento.getTipoBono().getNombre().equals("PROMOCIONAL")) {
            propositosentrega = sessionBean.adminFacade.findAllPropositosentrega();
            Propositoentrega p = new Propositoentrega();
            for (Propositoentrega next : propositosentrega) {
                if (next.getNombre().equals("PROMOCIÓN")) {
                    p = next;
                    break;
                }
            }
            System.out.println("Id proposito entrega " + p.getId());
            elemento.setPropositoEntrega(p);
        } else {
            for (Iterator<Propositoentrega> iterator = propositosentrega.iterator(); iterator.hasNext();) {
                Propositoentrega next = iterator.next();
                if (next.getNombre().equals("PROMOCIÓN")) {
                    iterator.remove();
                    break;
                }
            }
        }
        if(elemento.getPropositoEntrega().getId()!=null){
            for (Propositoentrega p : propositosentrega) {
                if(elemento.getPropositoEntrega().getId().equals(p.getId())){
                    elemento.setPropositoEntrega(p);
                } 
            }
        }
        if (elemento.getTipoBono().getNombre().equals("PROMOCIONAL")) {
            if (event.getNewStep().equals("nopromo")) {
                if (event.getOldStep().equals("general")) {
                    return "promo";
                } else {
                    return "general";
                }
            }
        } else {
            if (event.getNewStep().equals("promo")) {
                if (event.getOldStep().equals("nopromo")) {
                    return "confirmar";
                } else {
                    return "nopromo";
                }
            }
        }
        return event.getNewStep();
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

}
