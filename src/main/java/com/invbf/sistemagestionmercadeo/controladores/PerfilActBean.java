/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Formulario;
import com.invbf.sistemagestionmercadeo.entity.Perfil;
import com.invbf.sistemagestionmercadeo.entity.Vista;
import com.invbf.sistemagestionmercadeo.exceptions.PerfilExistenteException;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import com.invbf.sistemagestionmercadeo.util.FormularioBoolean;
import com.invbf.sistemagestionmercadeo.util.VistaBoolean;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class PerfilActBean implements Serializable{

    private Perfil elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private VistaBoolean vistaUsuario;
    private FormularioBoolean agregarUsuario;
    private FormularioBoolean actualizarUsuario;
    private FormularioBoolean eliminarUsuario;
    private VistaBoolean vistaTareas;
    private FormularioBoolean agregarTareas;
    private FormularioBoolean actualizarTareas;
    private FormularioBoolean eliminarTareas;
    private VistaBoolean vistaPerfiles;
    private FormularioBoolean agregarPerfiles;
    private FormularioBoolean actualizarPerfiles;
    private FormularioBoolean eliminarPerfiles;
    private VistaBoolean vistaVistas;
    private FormularioBoolean agregarVistas;
    private FormularioBoolean actualizarVistas;
    private FormularioBoolean eliminarVistas;
    private VistaBoolean vistaForm;
    private FormularioBoolean agregarForm;
    private FormularioBoolean actualizarForm;
    private FormularioBoolean eliminarForm;
    private VistaBoolean vistaCasinos;
    private FormularioBoolean agregarCasinos;
    private FormularioBoolean actualizarCasinos;
    private FormularioBoolean eliminarCasinos;
    private VistaBoolean vistaEventos;
    private FormularioBoolean agregarEventos;
    private FormularioBoolean actualizarEventos;
    private FormularioBoolean eliminarEventos;
    private VistaBoolean vistaClientes;
    private FormularioBoolean agregarClientes;
    private FormularioBoolean actualizarClientes;
    private FormularioBoolean eliminarClientes;
    private VistaBoolean vistaAtributos;
    private FormularioBoolean agregarAtributos;
    private FormularioBoolean actualizarAtributos;
    private FormularioBoolean eliminarAtributos;
    private VistaBoolean vistaTipo;
    private FormularioBoolean agregarTipo;
    private FormularioBoolean actualizarTipo;
    private FormularioBoolean eliminarTipo;
    private VistaBoolean vistaCat;
    private FormularioBoolean agregarCat;
    private FormularioBoolean actualizarCat;
    private FormularioBoolean eliminarCat;
    private VistaBoolean vistaAcciones;
    private FormularioBoolean agregarAcciones;
    private FormularioBoolean actualizarAcciones;
    private FormularioBoolean eliminarAcciones;
    private VistaBoolean vistaTipoevento;
    private FormularioBoolean agregarTipoevento;
    private FormularioBoolean actualizarTipoevento;
    private FormularioBoolean eliminarTipoevento;
    private VistaBoolean vistaEvMarketing;
    private VistaBoolean vistaEvHostess;
    private VistaBoolean vistaReportes;
    private VistaBoolean vistaConfiguraciones;
    private VistaBoolean vistaNotificacion;
    private VistaBoolean cupofidelizacion;
    private VistaBoolean vistatipoDocumento;
    private FormularioBoolean agregartipoDocumento;
    private FormularioBoolean actualizartipoDocumento;
    private FormularioBoolean eliminartipoDocumento;
    private VistaBoolean vistaCargo;
    private FormularioBoolean agregarCargo;
    private FormularioBoolean actualizarCargo;
    private FormularioBoolean eliminarCargo;
    private VistaBoolean vistaDenominacion;
    private FormularioBoolean agregarDenominacion;
    private FormularioBoolean actualizarDenominacion;
    private FormularioBoolean eliminarDenominacion;
    private VistaBoolean vistaAcceso;
    private FormularioBoolean agregarAcceso;
    private FormularioBoolean actualizarAcceso;
    private FormularioBoolean eliminarAcceso;
    private VistaBoolean vistaTipoBono;
    private FormularioBoolean agregarTipoBono;
    private FormularioBoolean actualizarTipoBono;
    private FormularioBoolean eliminarTipoBono;
    private VistaBoolean logs;
    private VistaBoolean aceptarSolictudLotesBons;
    private VistaBoolean vistaSolicitudesLotes;
    private FormularioBoolean agregarSolicitudesLotes;
    private FormularioBoolean actualizarSolicitudesLotes;
    private FormularioBoolean eliminarSolicitudesLotes;
    private VistaBoolean vistaLoteBono;
    private FormularioBoolean agregarLoteBono;
    private FormularioBoolean eliminarLoteBono;

    private VistaBoolean vistaClientesSGB;
    private VistaBoolean vertodoslosclientes;
    private FormularioBoolean agregarClientesSGB;
    private FormularioBoolean actualizarClientesSGB;
    private FormularioBoolean eliminarClientesSGB;
    private VistaBoolean vistaPropositos;
    private FormularioBoolean agregarPropositos;
    private FormularioBoolean actualizarPropositos;
    private FormularioBoolean eliminarPropositos;
    private VistaBoolean vistaAreas;
    private FormularioBoolean agregarAreas;
    private FormularioBoolean actualizarAreas;
    private FormularioBoolean eliminarAreas;
    private VistaBoolean vistaPeticionesCupo;
    private FormularioBoolean agregarPeticionesCupo;
    private FormularioBoolean eliminarPeticionesCupo;
    private VistaBoolean generarSolicitudBono;
    private VistaBoolean preAprobarSolicitudBono;
    private VistaBoolean aprobarSolicitudBono;
    private VistaBoolean actSolicitudSalida;
    private VistaBoolean aceprtarSolicitudSalida;
    private VistaBoolean controlSalidaBonos;
    
    private VistaBoolean bonosVerificarVer;
    private VistaBoolean bonosVerificarEjecutar;
    private VistaBoolean bonosValidarVer;
    private VistaBoolean bonosValidarEjecutar;
    private VistaBoolean bonosAutorizarVer;
    private VistaBoolean bonosAutorizarEjecutar;
    private VistaBoolean bonosEntregarCajaVer;
    private VistaBoolean bonosEntregarCajaEjecutar;
    private VistaBoolean bonosRecibirVer;
    private VistaBoolean bonosRecibirEjecutar;
    private VistaBoolean bonosEntregarClienteVer;
    private VistaBoolean bonosEntregarClienteEjecutar;
    private VistaBoolean bonoCanjearVer;
    private VistaBoolean bonoCanjearEjecutar;

    private VistaBoolean solicitudCambioCupo;
    private VistaBoolean Mailcliente;
    private VistaBoolean bonosretiradosCorreo;
    private VistaBoolean verInventarioBarajas;
    
    private VistaBoolean veripregistradas;
    private VistaBoolean entrarsinregistro;
    
    private VistaBoolean verTodosCasinos;
    private VistaBoolean cambioEstadoBono;
    
    
    private VistaBoolean correoRequerimientoLoteCreado;
    private VistaBoolean correoRequerimeintoLoteOrdenado;
    private VistaBoolean correoRequerimientoLoteRechazado;
    private VistaBoolean correoRequerimentoLoteRecibido;
    private VistaBoolean correoSolicitudEntradaValidada;
    private VistaBoolean correoSolicitudEntradaDevuelta;
    private VistaBoolean devolverSolLote;
    private VistaBoolean enviarSolLote;
    private VistaBoolean rechazarRequerimiento;
    private VistaBoolean ordenarRequerimiento;
    private VistaBoolean crearRequerimiento;
    private VistaBoolean verRequerimiento;
    
    private VistaBoolean entregalotesgeneradacorreo;
    private VistaBoolean entregalotesaceptadacorreo;
    private VistaBoolean solicitudbonosgeneradacorreo;
    private VistaBoolean solicitudbonospreaprobadacorreo;
    private VistaBoolean solicitudaprobadacorreo;
    private VistaBoolean senalbusquedacorreo;
    private VistaBoolean entregarbonocajacorreo;
    private VistaBoolean recibirbonocorreo;
    private VistaBoolean notificacionescorreo;
    private VistaBoolean vistaSolicitudesCasino;
    
    
    private VistaBoolean vistaBarajas;
    private FormularioBoolean agregarBarajas;
    private FormularioBoolean actualizarBarajas;
    private FormularioBoolean eliminarBarajas;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public PerfilActBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");
        if (!sessionBean.perfilViewMatch("Perfiles")) {
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }

        if (sessionBean.getAttributes("idPerfil")==null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("AdministradorAtributosSistema.xhtml");
            } catch (IOException ex) {
            }
        }
        elemento = sessionBean.adminFacade.findPerfil((Integer) sessionBean.getAttributes("idPerfil"));
        acomodaropciones();

    }

    public Perfil getElemento() {
        return elemento;
    }

    public void setElemento(Perfil elemento) {
        this.elemento = elemento;
    }

    public void guardar() {
        try {
            System.out.println("empezando");
            acomodaropcionesdevuelta();

            System.out.println("acomodo");
            sessionBean.adminFacade.guardarPerfiles(elemento);

            System.out.println("guardo");
            sessionBean.actualizarUsuario();
            sessionBean.removeAttribute("idPerfil");
            FacesUtil.addInfoMessage("Perfil actualizado", elemento.getNombre());
            FacesContext.getCurrentInstance().getExternalContext().redirect("AdministradorAtributosSistema.xhtml");
        } catch (PerfilExistenteException ex) {
            System.out.println(ex);
        } catch (IOException ex) {

            System.out.println(ex);
        }
    }

    private void acomodaropciones() {
        List<Vista> vistas = sessionBean.adminFacade.findAllVistas();
        List<Formulario> formularios = sessionBean.adminFacade.findAllFormularios();
        for (Formulario f : formularios) {

            if (f.getAccion().equals("crear")) {
                if (f.getTabla().equals("Barajas")) {

                    if (elemento.getFormularioList().contains(f)) {
                        agregarBarajas = new FormularioBoolean(f, true);
                    } else {
                        agregarBarajas = new FormularioBoolean(f, false);
                    }
                }if (f.getTabla().equals("PeticionesCupo")) {

                    if (elemento.getFormularioList().contains(f)) {
                        agregarPeticionesCupo = new FormularioBoolean(f, true);
                    } else {
                        agregarPeticionesCupo = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Areas")) {

                    if (elemento.getFormularioList().contains(f)) {
                        agregarAreas = new FormularioBoolean(f, true);
                    } else {
                        agregarAreas = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Propositos")) {

                    if (elemento.getFormularioList().contains(f)) {
                        agregarPropositos = new FormularioBoolean(f, true);
                    } else {
                        agregarPropositos = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("ClientesSGB")) {

                    if (elemento.getFormularioList().contains(f)) {
                        agregarClientesSGB = new FormularioBoolean(f, true);
                    } else {
                        agregarClientesSGB = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("LoteBono")) {

                    if (elemento.getFormularioList().contains(f)) {
                        agregarLoteBono = new FormularioBoolean(f, true);
                    } else {
                        agregarLoteBono = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("SolicitudLotes")) {
                    if (elemento.getFormularioList().contains(f)) {
                        agregarSolicitudesLotes = new FormularioBoolean(f, true);
                    } else {
                        agregarSolicitudesLotes = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("acceso")) {
                    if (elemento.getFormularioList().contains(f)) {
                        agregarAcceso = new FormularioBoolean(f, true);
                    } else {
                        agregarAcceso = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("TipoBono")) {
                    if (elemento.getFormularioList().contains(f)) {
                        agregarTipoBono = new FormularioBoolean(f, true);
                    } else {
                        agregarTipoBono = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("denominaciones")) {
                    if (elemento.getFormularioList().contains(f)) {
                        agregarDenominacion = new FormularioBoolean(f, true);
                    } else {
                        agregarDenominacion = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("tipodocumento")) {
                    if (elemento.getFormularioList().contains(f)) {
                        agregartipoDocumento = new FormularioBoolean(f, true);
                    } else {
                        agregartipoDocumento = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("cargo")) {
                    if (elemento.getFormularioList().contains(f)) {
                        agregarCargo = new FormularioBoolean(f, true);
                    } else {
                        agregarCargo = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Tipotareas")) {
                    if (elemento.getFormularioList().contains(f)) {
                        agregarTipoevento = new FormularioBoolean(f, true);
                    } else {
                        agregarTipoevento = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Tareas")) {
                    if (elemento.getFormularioList().contains(f)) {
                        agregarTareas = new FormularioBoolean(f, true);
                    } else {
                        agregarTareas = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Usuarios")) {
                    if (elemento.getFormularioList().contains(f)) {
                        agregarUsuario = new FormularioBoolean(f, true);
                    } else {
                        agregarUsuario = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Perfiles")) {
                    if (elemento.getFormularioList().contains(f)) {
                        agregarPerfiles = new FormularioBoolean(f, true);
                    } else {
                        agregarPerfiles = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Vistas")) {
                    if (elemento.getFormularioList().contains(f)) {
                        agregarVistas = new FormularioBoolean(f, true);
                    } else {
                        agregarVistas = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Formularios")) {
                    if (elemento.getFormularioList().contains(f)) {
                        agregarForm = new FormularioBoolean(f, true);
                    } else {
                        agregarForm = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Casinos")) {
                    if (elemento.getFormularioList().contains(f)) {
                        agregarCasinos = new FormularioBoolean(f, true);
                    } else {
                        agregarCasinos = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Eventos")) {
                    if (elemento.getFormularioList().contains(f)) {
                        agregarEventos = new FormularioBoolean(f, true);
                    } else {
                        agregarEventos = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Clientes")) {
                    if (elemento.getFormularioList().contains(f)) {
                        agregarClientes = new FormularioBoolean(f, true);
                    } else {
                        agregarClientes = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Atributos")) {
                    if (elemento.getFormularioList().contains(f)) {
                        agregarAtributos = new FormularioBoolean(f, true);
                    } else {
                        agregarAtributos = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("TiposJuegos")) {
                    if (elemento.getFormularioList().contains(f)) {
                        agregarTipo = new FormularioBoolean(f, true);
                    } else {
                        agregarTipo = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Categorias")) {
                    if (elemento.getFormularioList().contains(f)) {
                        agregarCat = new FormularioBoolean(f, true);
                    } else {
                        agregarCat = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Acciones")) {
                    if (elemento.getFormularioList().contains(f)) {
                        agregarAcciones = new FormularioBoolean(f, true);
                    } else {
                        agregarAcciones = new FormularioBoolean(f, false);
                    }
                }
            }
            if (f.getAccion().equals("actualizar")) {
                if (f.getTabla().equals("Areas")) {

                    if (elemento.getFormularioList().contains(f)) {
                        actualizarAreas = new FormularioBoolean(f, true);
                    } else {
                        actualizarAreas = new FormularioBoolean(f, false);
                    }
                }if (f.getTabla().equals("Barajas")) {

                    if (elemento.getFormularioList().contains(f)) {
                        actualizarBarajas = new FormularioBoolean(f, true);
                    } else {
                        actualizarBarajas = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Propositos")) {

                    if (elemento.getFormularioList().contains(f)) {
                        actualizarPropositos = new FormularioBoolean(f, true);
                    } else {
                        actualizarPropositos = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("ClientesSGB")) {

                    if (elemento.getFormularioList().contains(f)) {
                        actualizarClientesSGB = new FormularioBoolean(f, true);
                    } else {
                        actualizarClientesSGB = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("SolicitudLotes")) {
                    if (elemento.getFormularioList().contains(f)) {
                        actualizarSolicitudesLotes = new FormularioBoolean(f, true);
                    } else {
                        actualizarSolicitudesLotes = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("acceso")) {
                    if (elemento.getFormularioList().contains(f)) {
                        actualizarAcceso = new FormularioBoolean(f, true);
                    } else {
                        actualizarAcceso = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("TipoBono")) {
                    if (elemento.getFormularioList().contains(f)) {
                        actualizarTipoBono = new FormularioBoolean(f, true);
                    } else {
                        actualizarTipoBono = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("denominaciones")) {
                    if (elemento.getFormularioList().contains(f)) {
                        actualizarDenominacion = new FormularioBoolean(f, true);
                    } else {
                        actualizarDenominacion = new FormularioBoolean(f, false);
                    }
                }

                if (f.getTabla().equals("tipodocumento")) {
                    if (elemento.getFormularioList().contains(f)) {
                        actualizartipoDocumento = new FormularioBoolean(f, true);
                    } else {
                        actualizartipoDocumento = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("cargo")) {
                    if (elemento.getFormularioList().contains(f)) {
                        actualizarCargo = new FormularioBoolean(f, true);
                    } else {
                        actualizarCargo = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Tipotareas")) {
                    if (elemento.getFormularioList().contains(f)) {
                        actualizarTipoevento = new FormularioBoolean(f, true);
                    } else {
                        actualizarTipoevento = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Tareas")) {
                    if (elemento.getFormularioList().contains(f)) {
                        actualizarTareas = new FormularioBoolean(f, true);
                    } else {
                        actualizarTareas = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Usuarios")) {
                    if (elemento.getFormularioList().contains(f)) {
                        actualizarUsuario = new FormularioBoolean(f, true);
                    } else {
                        actualizarUsuario = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Perfiles")) {
                    if (elemento.getFormularioList().contains(f)) {
                        actualizarPerfiles = new FormularioBoolean(f, true);
                    } else {
                        actualizarPerfiles = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Vistas")) {
                    if (elemento.getFormularioList().contains(f)) {
                        actualizarVistas = new FormularioBoolean(f, true);
                    } else {
                        actualizarVistas = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Formularios")) {
                    if (elemento.getFormularioList().contains(f)) {
                        actualizarForm = new FormularioBoolean(f, true);
                    } else {
                        actualizarForm = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Casinos")) {
                    if (elemento.getFormularioList().contains(f)) {
                        actualizarCasinos = new FormularioBoolean(f, true);
                    } else {
                        actualizarCasinos = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Eventos")) {
                    if (elemento.getFormularioList().contains(f)) {
                        actualizarEventos = new FormularioBoolean(f, true);
                    } else {
                        actualizarEventos = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Clientes")) {
                    if (elemento.getFormularioList().contains(f)) {
                        actualizarClientes = new FormularioBoolean(f, true);
                    } else {
                        actualizarClientes = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Atributos")) {
                    if (elemento.getFormularioList().contains(f)) {
                        actualizarAtributos = new FormularioBoolean(f, true);
                    } else {
                        actualizarAtributos = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("TiposJuegos")) {
                    if (elemento.getFormularioList().contains(f)) {
                        actualizarTipo = new FormularioBoolean(f, true);
                    } else {
                        actualizarTipo = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Categorias")) {
                    if (elemento.getFormularioList().contains(f)) {
                        actualizarCat = new FormularioBoolean(f, true);
                    } else {
                        actualizarCat = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Acciones")) {
                    if (elemento.getFormularioList().contains(f)) {
                        actualizarAcciones = new FormularioBoolean(f, true);
                    } else {
                        actualizarAcciones = new FormularioBoolean(f, false);
                    }
                }
            }
            if (f.getAccion().equals("eliminar")) {
                if (f.getTabla().equals("PeticionesCupo")) {

                    if (elemento.getFormularioList().contains(f)) {
                        eliminarPeticionesCupo = new FormularioBoolean(f, true);
                    } else {
                        eliminarPeticionesCupo = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Barajas")) {

                    if (elemento.getFormularioList().contains(f)) {
                        eliminarBarajas = new FormularioBoolean(f, true);
                    } else {
                        eliminarBarajas = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Areas")) {

                    if (elemento.getFormularioList().contains(f)) {
                        eliminarAreas = new FormularioBoolean(f, true);
                    } else {
                        eliminarAreas = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Propositos")) {

                    if (elemento.getFormularioList().contains(f)) {
                        eliminarPropositos = new FormularioBoolean(f, true);
                    } else {
                        eliminarPropositos = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("ClientesSGB")) {

                    if (elemento.getFormularioList().contains(f)) {
                        eliminarClientesSGB = new FormularioBoolean(f, true);
                    } else {
                        eliminarClientesSGB = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("SolicitudLotes")) {
                    if (elemento.getFormularioList().contains(f)) {
                        eliminarSolicitudesLotes = new FormularioBoolean(f, true);
                    } else {
                        eliminarSolicitudesLotes = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("LoteBono")) {
                    if (elemento.getFormularioList().contains(f)) {
                        eliminarLoteBono = new FormularioBoolean(f, true);
                    } else {
                        eliminarLoteBono = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("acceso")) {
                    if (elemento.getFormularioList().contains(f)) {
                        eliminarAcceso = new FormularioBoolean(f, true);
                    } else {
                        eliminarAcceso = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("TipoBono")) {
                    if (elemento.getFormularioList().contains(f)) {
                        eliminarTipoBono = new FormularioBoolean(f, true);
                    } else {
                        eliminarTipoBono = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("denominaciones")) {
                    if (elemento.getFormularioList().contains(f)) {
                        eliminarDenominacion = new FormularioBoolean(f, true);
                    } else {
                        eliminarDenominacion = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("tipodocumento")) {
                    if (elemento.getFormularioList().contains(f)) {
                        eliminartipoDocumento = new FormularioBoolean(f, true);
                    } else {
                        eliminartipoDocumento = new FormularioBoolean(f, false);
                    }
                }

                if (f.getTabla().equals("cargo")) {
                    if (elemento.getFormularioList().contains(f)) {
                        eliminarCargo = new FormularioBoolean(f, true);
                    } else {
                        eliminarCargo = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Tipotareas")) {
                    if (elemento.getFormularioList().contains(f)) {
                        eliminarTipoevento = new FormularioBoolean(f, true);
                    } else {
                        eliminarTipoevento = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Tareas")) {
                    if (elemento.getFormularioList().contains(f)) {
                        eliminarTareas = new FormularioBoolean(f, true);
                    } else {
                        eliminarTareas = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Usuarios")) {
                    if (elemento.getFormularioList().contains(f)) {
                        eliminarUsuario = new FormularioBoolean(f, true);
                    } else {
                        eliminarUsuario = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Perfiles")) {
                    if (elemento.getFormularioList().contains(f)) {
                        eliminarPerfiles = new FormularioBoolean(f, true);
                    } else {
                        eliminarPerfiles = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Vistas")) {
                    if (elemento.getFormularioList().contains(f)) {
                        eliminarVistas = new FormularioBoolean(f, true);
                    } else {
                        eliminarVistas = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Formularios")) {
                    if (elemento.getFormularioList().contains(f)) {
                        eliminarForm = new FormularioBoolean(f, true);
                    } else {
                        eliminarForm = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Casinos")) {
                    if (elemento.getFormularioList().contains(f)) {
                        eliminarCasinos = new FormularioBoolean(f, true);
                    } else {
                        eliminarCasinos = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Eventos")) {
                    if (elemento.getFormularioList().contains(f)) {
                        eliminarEventos = new FormularioBoolean(f, true);
                    } else {
                        eliminarEventos = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Clientes")) {
                    if (elemento.getFormularioList().contains(f)) {
                        eliminarClientes = new FormularioBoolean(f, true);
                    } else {
                        eliminarClientes = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Atributos")) {
                    if (elemento.getFormularioList().contains(f)) {
                        eliminarAtributos = new FormularioBoolean(f, true);
                    } else {
                        eliminarAtributos = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("TiposJuegos")) {
                    if (elemento.getFormularioList().contains(f)) {
                        eliminarTipo = new FormularioBoolean(f, true);
                    } else {
                        eliminarTipo = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Categorias")) {
                    if (elemento.getFormularioList().contains(f)) {
                        eliminarCat = new FormularioBoolean(f, true);
                    } else {
                        eliminarCat = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Acciones")) {
                    if (elemento.getFormularioList().contains(f)) {
                        eliminarAcciones = new FormularioBoolean(f, true);
                    } else {
                        eliminarAcciones = new FormularioBoolean(f, false);
                    }
                }
            }

        }
        for (Vista v : vistas) {
            if (v.getNombreVista().equals("Verbonosporverificar")) {
                if (elemento.getVistaList().contains(v)) {
                    bonosVerificarVer = new VistaBoolean(v, true);
                } else {
                    bonosVerificarVer = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("Verificarbono")) {
                if (elemento.getVistaList().contains(v)) {
                    bonosVerificarEjecutar = new VistaBoolean(v, true);
                } else {
                    bonosVerificarEjecutar = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Verbonosporvalidar")) {
                if (elemento.getVistaList().contains(v)) {
                    bonosValidarVer = new VistaBoolean(v, true);
                } else {
                    bonosValidarVer = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("Validarbono")) {
                if (elemento.getVistaList().contains(v)) {
                    bonosValidarEjecutar = new VistaBoolean(v, true);
                } else {
                    bonosValidarEjecutar = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Verbonosporautorizar")) {
                if (elemento.getVistaList().contains(v)) {
                    bonosAutorizarVer = new VistaBoolean(v, true);
                } else {
                    bonosAutorizarVer = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("Autorizarbono")) {
                if (elemento.getVistaList().contains(v)) {
                    bonosAutorizarEjecutar = new VistaBoolean(v, true);
                } else {
                    bonosAutorizarEjecutar = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Verbonosporentregarcaja")) {
                if (elemento.getVistaList().contains(v)) {
                    bonosEntregarCajaVer = new VistaBoolean(v, true);
                } else {
                    bonosEntregarCajaVer = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("Entregarbonocaja")) {
                if (elemento.getVistaList().contains(v)) {
                    bonosEntregarCajaEjecutar = new VistaBoolean(v, true);
                } else {
                    bonosEntregarCajaEjecutar = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Verbonosporrecibir")) {
                if (elemento.getVistaList().contains(v)) {
                    bonosRecibirVer = new VistaBoolean(v, true);
                } else {
                    bonosRecibirVer = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("Recibirbono")) {
                if (elemento.getVistaList().contains(v)) {
                    bonosRecibirEjecutar = new VistaBoolean(v, true);
                } else {
                    bonosRecibirEjecutar = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Verbonosporentregarcliente")) {
                if (elemento.getVistaList().contains(v)) {
                    bonosEntregarClienteVer = new VistaBoolean(v, true);
                } else {
                    bonosEntregarClienteVer = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("Entregarbonocliente")) {
                if (elemento.getVistaList().contains(v)) {
                    bonosEntregarClienteEjecutar = new VistaBoolean(v, true);
                } else {
                    bonosEntregarClienteEjecutar = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Verbonosporcanjear")) {
                if (elemento.getVistaList().contains(v)) {
                    bonoCanjearVer = new VistaBoolean(v, true);
                } else {
                    bonoCanjearVer = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("Canjearbono")) {
                if (elemento.getVistaList().contains(v)) {
                    bonoCanjearEjecutar = new VistaBoolean(v, true);
                } else {
                    bonoCanjearEjecutar = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("SolicitudCambioCupo")) {
                if (elemento.getVistaList().contains(v)) {
                    solicitudCambioCupo = new VistaBoolean(v, true);
                } else {
                    solicitudCambioCupo = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("Mailcliente")) {
                if (elemento.getVistaList().contains(v)) {
                    Mailcliente = new VistaBoolean(v, true);
                } else {
                    Mailcliente = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("bonosretiradosCorreo")) {
                if (elemento.getVistaList().contains(v)) {
                    bonosretiradosCorreo = new VistaBoolean(v, true);
                } else {
                    bonosretiradosCorreo = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("verInventarioBarajas")) {
                if (elemento.getVistaList().contains(v)) {
                    verInventarioBarajas = new VistaBoolean(v, true);
                } else {
                    verInventarioBarajas = new VistaBoolean(v, false);
                }
            } if (v.getNombreVista().equals("veripregistradas")) {
                if (elemento.getVistaList().contains(v)) {
                    veripregistradas = new VistaBoolean(v, true);
                } else {
                    veripregistradas = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("entrarsinregistro")) {
                if (elemento.getVistaList().contains(v)) {
                    entrarsinregistro = new VistaBoolean(v, true);
                } else {
                    entrarsinregistro = new VistaBoolean(v, false);
                }
            }
                    if (v.getNombreVista().equals("VerTodosCasinos")) {
                if (elemento.getVistaList().contains(v)) {
                    verTodosCasinos = new VistaBoolean(v, true);
                } else {
                    verTodosCasinos = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("cambioEstadoBono")) {
                if (elemento.getVistaList().contains(v)) {
                    cambioEstadoBono = new VistaBoolean(v, true);
                } else {
                    cambioEstadoBono = new VistaBoolean(v, false);
                }
            }
            
            
            
            if (v.getNombreVista().equals("entregalotesgeneradacorreo")) {
                if (elemento.getVistaList().contains(v)) {
                    entregalotesgeneradacorreo = new VistaBoolean(v, true);
                } else {
                    entregalotesgeneradacorreo = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("entregalotesaceptadacorreo")) {
                if (elemento.getVistaList().contains(v)) {
                    entregalotesaceptadacorreo = new VistaBoolean(v, true);
                } else {
                    entregalotesaceptadacorreo = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("solicitudbonosgeneradacorreo")) {
                if (elemento.getVistaList().contains(v)) {
                    solicitudbonosgeneradacorreo = new VistaBoolean(v, true);
                } else {
                    solicitudbonosgeneradacorreo = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("solicitudbonospreaprobadacorreo")) {
                if (elemento.getVistaList().contains(v)) {
                    solicitudbonospreaprobadacorreo = new VistaBoolean(v, true);
                } else {
                    solicitudbonospreaprobadacorreo = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("solicitudaprobadacorreo")) {
                if (elemento.getVistaList().contains(v)) {
                    solicitudaprobadacorreo = new VistaBoolean(v, true);
                } else {
                    solicitudaprobadacorreo = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("senalbusquedacorreo")) {
                if (elemento.getVistaList().contains(v)) {
                    senalbusquedacorreo = new VistaBoolean(v, true);
                } else {
                    senalbusquedacorreo = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("entregarbonocajacorreo")) {
                if (elemento.getVistaList().contains(v)) {
                    entregarbonocajacorreo = new VistaBoolean(v, true);
                } else {
                    entregarbonocajacorreo = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("recibirbonocorreo")) {
                if (elemento.getVistaList().contains(v)) {
                    recibirbonocorreo = new VistaBoolean(v, true);
                } else {
                    recibirbonocorreo = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("notificacionescorreo")) {
                if (elemento.getVistaList().contains(v)) {
                    notificacionescorreo = new VistaBoolean(v, true);
                } else {
                    notificacionescorreo = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("vistaSolicitudesCasino")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaSolicitudesCasino = new VistaBoolean(v, true);
                } else {
                    vistaSolicitudesCasino = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("verBarajas")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaBarajas = new VistaBoolean(v, true);
                } else {
                    vistaBarajas = new VistaBoolean(v, false);
                }
            }
            
            
            if (v.getNombreVista().equals("correoRequerimientoLoteCreado")) {
                if (elemento.getVistaList().contains(v)) {
                    correoRequerimientoLoteCreado = new VistaBoolean(v, true);
                } else {
                    correoRequerimientoLoteCreado = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("correoRequerimeintoLoteOrdenado")) {
                if (elemento.getVistaList().contains(v)) {
                    correoRequerimeintoLoteOrdenado = new VistaBoolean(v, true);
                } else {
                    correoRequerimeintoLoteOrdenado = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("correoRequerimientoLoteRechazado")) {
                if (elemento.getVistaList().contains(v)) {
                    correoRequerimientoLoteRechazado = new VistaBoolean(v, true);
                } else {
                    correoRequerimientoLoteRechazado = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("correoRequerimentoLoteRecibido")) {
                if (elemento.getVistaList().contains(v)) {
                    correoRequerimentoLoteRecibido = new VistaBoolean(v, true);
                } else {
                    correoRequerimentoLoteRecibido = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("correoSolicitudEntradaValidada")) {
                if (elemento.getVistaList().contains(v)) {
                    correoSolicitudEntradaValidada = new VistaBoolean(v, true);
                } else {
                    correoSolicitudEntradaValidada = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("correoSolicitudEntradaDevuelta")) {
                if (elemento.getVistaList().contains(v)) {
                    correoSolicitudEntradaDevuelta = new VistaBoolean(v, true);
                } else {
                    correoSolicitudEntradaDevuelta = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("devolverSolLote")) {
                if (elemento.getVistaList().contains(v)) {
                    devolverSolLote = new VistaBoolean(v, true);
                } else {
                    devolverSolLote = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("enviarSolLote")) {
                if (elemento.getVistaList().contains(v)) {
                    enviarSolLote = new VistaBoolean(v, true);
                } else {
                    enviarSolLote = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("rechazarRequerimiento")) {
                if (elemento.getVistaList().contains(v)) {
                    rechazarRequerimiento = new VistaBoolean(v, true);
                } else {
                    rechazarRequerimiento = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("ordenarRequerimiento")) {
                if (elemento.getVistaList().contains(v)) {
                    ordenarRequerimiento = new VistaBoolean(v, true);
                } else {
                    ordenarRequerimiento = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("crearRequerimiento")) {
                if (elemento.getVistaList().contains(v)) {
                    crearRequerimiento = new VistaBoolean(v, true);
                } else {
                    crearRequerimiento = new VistaBoolean(v, false);
                }
            }if (v.getNombreVista().equals("verRequerimiento")) {
                if (elemento.getVistaList().contains(v)) {
                    verRequerimiento = new VistaBoolean(v, true);
                } else {
                    verRequerimiento = new VistaBoolean(v, false);
                }
            }
            
            
            
            
            if (v.getNombreVista().equals("ActSolicitudSalida")) {
                if (elemento.getVistaList().contains(v)) {
                    actSolicitudSalida = new VistaBoolean(v, true);
                } else {
                    actSolicitudSalida = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("AceptarSolicitudSalida")) {
                if (elemento.getVistaList().contains(v)) {
                    aceprtarSolicitudSalida = new VistaBoolean(v, true);
                } else {
                    aceprtarSolicitudSalida = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Controlsalidabonos")) {
                if (elemento.getVistaList().contains(v)) {
                    controlSalidaBonos = new VistaBoolean(v, true);
                } else {
                    controlSalidaBonos = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("verTodosClientes")) {
                if (elemento.getVistaList().contains(v)) {
                    vertodoslosclientes = new VistaBoolean(v, true);
                } else {
                    vertodoslosclientes = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("GenerarSolicitudBono")) {
                if (elemento.getVistaList().contains(v)) {
                    generarSolicitudBono = new VistaBoolean(v, true);
                } else {
                    generarSolicitudBono = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("PreAprobarSolicitudBono")) {
                if (elemento.getVistaList().contains(v)) {
                    preAprobarSolicitudBono = new VistaBoolean(v, true);
                } else {
                    preAprobarSolicitudBono = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("AprobarSolicitudBono")) {
                if (elemento.getVistaList().contains(v)) {
                    aprobarSolicitudBono = new VistaBoolean(v, true);
                } else {
                    aprobarSolicitudBono = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("ClientesSGB")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaClientesSGB = new VistaBoolean(v, true);
                } else {
                    vistaClientesSGB = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Propositos")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaPropositos = new VistaBoolean(v, true);
                } else {
                    vistaPropositos = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Areas")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaAreas = new VistaBoolean(v, true);
                } else {
                    vistaAreas = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("PeticionesCupo")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaPeticionesCupo = new VistaBoolean(v, true);
                } else {
                    vistaPeticionesCupo = new VistaBoolean(v, false);
                }
            }
            
            if (v.getNombreVista().equals("TipoBono")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaTipoBono = new VistaBoolean(v, true);
                } else {
                    vistaTipoBono = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("denominaciones")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaDenominacion = new VistaBoolean(v, true);
                } else {
                    vistaDenominacion = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("acceso")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaAcceso = new VistaBoolean(v, true);
                } else {
                    vistaAcceso = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Tipotareas")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaTipoevento = new VistaBoolean(v, true);
                } else {
                    vistaTipoevento = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Tareas")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaTareas = new VistaBoolean(v, true);
                } else {
                    vistaTareas = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Usuarios")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaUsuario = new VistaBoolean(v, true);
                } else {
                    vistaUsuario = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Perfiles")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaPerfiles = new VistaBoolean(v, true);
                } else {
                    vistaPerfiles = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Vistas")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaVistas = new VistaBoolean(v, true);
                } else {
                    vistaVistas = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Formularios")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaForm = new VistaBoolean(v, true);
                } else {
                    vistaForm = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Casinos")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaCasinos = new VistaBoolean(v, true);
                } else {
                    vistaCasinos = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Eventos")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaEventos = new VistaBoolean(v, true);
                } else {
                    vistaEventos = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Clientes")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaClientes = new VistaBoolean(v, true);
                } else {
                    vistaClientes = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Atributos")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaAtributos = new VistaBoolean(v, true);
                } else {
                    vistaAtributos = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("TipoJuego")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaTipo = new VistaBoolean(v, true);
                } else {
                    vistaTipo = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Categorias")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaCat = new VistaBoolean(v, true);
                } else {
                    vistaCat = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Acciones")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaAcciones = new VistaBoolean(v, true);
                } else {
                    vistaAcciones = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("ManejadorEventosMarketing")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaEvMarketing = new VistaBoolean(v, true);
                } else {
                    vistaEvMarketing = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("ManejadorEventosHostess")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaEvHostess = new VistaBoolean(v, true);
                } else {
                    vistaEvHostess = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Reportes")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaReportes = new VistaBoolean(v, true);
                } else {
                    vistaReportes = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("ConfiguracionesGenerales")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaConfiguraciones = new VistaBoolean(v, true);
                } else {
                    vistaConfiguraciones = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("cupofidelizacion")) {
                if (elemento.getVistaList().contains(v)) {
                    cupofidelizacion = new VistaBoolean(v, true);
                } else {
                    cupofidelizacion = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("tipodocumento")) {
                if (elemento.getVistaList().contains(v)) {
                    vistatipoDocumento = new VistaBoolean(v, true);
                } else {
                    vistatipoDocumento = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("notificaciones")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaNotificacion = new VistaBoolean(v, true);
                } else {
                    vistaNotificacion = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("logs")) {
                if (elemento.getVistaList().contains(v)) {
                    logs = new VistaBoolean(v, true);
                } else {
                    logs = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("AceptarSolicitudLotesBonos")) {
                if (elemento.getVistaList().contains(v)) {
                    aceptarSolictudLotesBons = new VistaBoolean(v, true);
                } else {
                    aceptarSolictudLotesBons = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("SolicitudLotes")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaSolicitudesLotes = new VistaBoolean(v, true);
                } else {
                    vistaSolicitudesLotes = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("LoteBono")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaLoteBono = new VistaBoolean(v, true);
                } else {
                    vistaLoteBono = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("cargo")) {
                if (elemento.getVistaList().contains(v)) {
                    vistaCargo = new VistaBoolean(v, true);
                } else {
                    vistaCargo = new VistaBoolean(v, false);
                }
            }
        }
    }

    private void acomodaropcionesdevuelta() {
        elemento.getVistaList().clear();
        int count = 0;
        if (!actSolicitudSalida.isSelected()) {
            count++;
        } else {
            elemento.getVistaList().add(actSolicitudSalida.getVista());
        }
        if (!aceprtarSolicitudSalida.isSelected()) {
            count++;
        } else {
            elemento.getVistaList().add(aceprtarSolicitudSalida.getVista());
        }
        if (!controlSalidaBonos.isSelected()) {
            count++;
        } else {
            elemento.getVistaList().add(controlSalidaBonos.getVista());
        }
        if (!vistaDenominacion.isSelected()) {
            count++;
        } else {
            elemento.getVistaList().add(vistaDenominacion.getVista());
        }
        if (!vistaTipoBono.isSelected()) {
            count++;
        } else {
            elemento.getVistaList().add(vistaTipoBono.getVista());
        }
        if (count < 3) {
            elemento.getVistaList().add(sessionBean.adminFacade.findVistasByNombre("atributosbonos"));
        }
        count = 0;
        if (!vistaUsuario.isSelected()) {
            count++;
        } else {
            elemento.getVistaList().add(vistaUsuario.getVista());
        }
        if (!vistaPerfiles.isSelected()) {
            count++;
        } else {
            elemento.getVistaList().add(vistaPerfiles.getVista());
        }
        if (!vistaCargo.isSelected()) {
            count++;
        } else {
            elemento.getVistaList().add(vistaCargo.getVista());
        }
        if (!vistaVistas.isSelected()) {
            count++;
        } else {
            elemento.getVistaList().add(vistaVistas.getVista());
        }
        if (!vistaForm.isSelected()) {
            count++;
        } else {
            elemento.getVistaList().add(vistaForm.getVista());
        }
        if (!vistaAcceso.isSelected()) {
            count++;
        } else {
            elemento.getVistaList().add(vistaAcceso.getVista());
        }
        if (count < 6) {
            elemento.getVistaList().add(sessionBean.adminFacade.findVistasByNombre("AtributosSistema"));
        }
        if (vistaEventos.isSelected()) {
            elemento.getVistaList().add(vistaEventos.getVista());
        }
        if (vistaTareas.isSelected()) {
            elemento.getVistaList().add(vistaTareas.getVista());
        }
        if (vistaClientes.isSelected()) {
            elemento.getVistaList().add(vistaClientes.getVista());
        }
        
        if (bonosValidarVer.isSelected()) {
            elemento.getVistaList().add(bonosValidarVer.getVista());
        }
        if (bonosValidarEjecutar.isSelected()) {
            elemento.getVistaList().add(bonosValidarEjecutar.getVista());
        }
        
        
        
        if (notificacionescorreo.isSelected()) {
            elemento.getVistaList().add(notificacionescorreo.getVista());
        }
        if (vistaSolicitudesCasino.isSelected()) {
            elemento.getVistaList().add(vistaSolicitudesCasino.getVista());
        }
        if (vistaBarajas.isSelected()) {
            elemento.getVistaList().add(vistaBarajas.getVista());
        }
        
        if (recibirbonocorreo.isSelected()) {
            elemento.getVistaList().add(recibirbonocorreo.getVista());
        }
        
        if (entregarbonocajacorreo.isSelected()) {
            elemento.getVistaList().add(entregarbonocajacorreo.getVista());
        }
        
        if (senalbusquedacorreo.isSelected()) {
            elemento.getVistaList().add(senalbusquedacorreo.getVista());
        }
        
        if (solicitudaprobadacorreo.isSelected()) {
            elemento.getVistaList().add(solicitudaprobadacorreo.getVista());
        }
        
        if (solicitudbonospreaprobadacorreo.isSelected()) {
            elemento.getVistaList().add(solicitudbonospreaprobadacorreo.getVista());
        }
        
        if (solicitudbonosgeneradacorreo.isSelected()) {
            elemento.getVistaList().add(solicitudbonosgeneradacorreo.getVista());
        }
        
        if (entregalotesaceptadacorreo.isSelected()) {
            elemento.getVistaList().add(entregalotesaceptadacorreo.getVista());
        }
        
        if (entregalotesgeneradacorreo.isSelected()) {
            elemento.getVistaList().add(entregalotesgeneradacorreo.getVista());
        }
        
        
        
        
        
        if (correoRequerimientoLoteCreado.isSelected()) {
            elemento.getVistaList().add(correoRequerimientoLoteCreado.getVista());
        }
        if (correoRequerimeintoLoteOrdenado.isSelected()) {
            elemento.getVistaList().add(correoRequerimeintoLoteOrdenado.getVista());
        }
        if (correoRequerimientoLoteRechazado.isSelected()) {
            elemento.getVistaList().add(correoRequerimientoLoteRechazado.getVista());
        }
        if (correoRequerimentoLoteRecibido.isSelected()) {
            elemento.getVistaList().add(correoRequerimentoLoteRecibido.getVista());
        }
        if (correoSolicitudEntradaValidada.isSelected()) {
            elemento.getVistaList().add(correoSolicitudEntradaValidada.getVista());
        }
        if (correoSolicitudEntradaDevuelta.isSelected()) {
            elemento.getVistaList().add(correoSolicitudEntradaDevuelta.getVista());
        }
        if (devolverSolLote.isSelected()) {
            elemento.getVistaList().add(devolverSolLote.getVista());
        }
        if (enviarSolLote.isSelected()) {
            elemento.getVistaList().add(enviarSolLote.getVista());
        }
        if (rechazarRequerimiento.isSelected()) {
            elemento.getVistaList().add(rechazarRequerimiento.getVista());
        }
        if (ordenarRequerimiento.isSelected()) {
            elemento.getVistaList().add(ordenarRequerimiento.getVista());
        }
        if (crearRequerimiento.isSelected()) {
            elemento.getVistaList().add(crearRequerimiento.getVista());
        }
        if (verRequerimiento.isSelected()) {
            elemento.getVistaList().add(verRequerimiento.getVista());
        }
        
        
        
        if (bonosAutorizarVer.isSelected()) {
            elemento.getVistaList().add(bonosAutorizarVer.getVista());
        }
        if (bonosAutorizarEjecutar.isSelected()) {
            elemento.getVistaList().add(bonosAutorizarEjecutar.getVista());
        }
        
        if (bonosVerificarVer.isSelected()) {
            elemento.getVistaList().add(bonosVerificarVer.getVista());
        }
        if (bonosVerificarEjecutar.isSelected()) {
            elemento.getVistaList().add(bonosVerificarEjecutar.getVista());
        }
        if (bonosEntregarCajaVer.isSelected()) {
            elemento.getVistaList().add(bonosEntregarCajaVer.getVista());
        }
        if (bonosEntregarCajaEjecutar.isSelected()) {
            elemento.getVistaList().add(bonosEntregarCajaEjecutar.getVista());
        }
        if (bonosRecibirVer.isSelected()) {
            elemento.getVistaList().add(bonosRecibirVer.getVista());
        }
        if (bonosRecibirEjecutar.isSelected()) {
            elemento.getVistaList().add(bonosRecibirEjecutar.getVista());
        }
        if (bonosEntregarClienteVer.isSelected()) {
            elemento.getVistaList().add(bonosEntregarClienteVer.getVista());
        }
        if (bonosEntregarClienteEjecutar.isSelected()) {
            elemento.getVistaList().add(bonosEntregarClienteEjecutar.getVista());
        }
        if (bonoCanjearVer.isSelected()) {
            elemento.getVistaList().add(bonoCanjearVer.getVista());
        }
        if (bonoCanjearEjecutar.isSelected()) {
            elemento.getVistaList().add(bonoCanjearEjecutar.getVista());
        }
        if (solicitudCambioCupo.isSelected()) {
            elemento.getVistaList().add(solicitudCambioCupo.getVista());
        }
        if (Mailcliente.isSelected()) {
            elemento.getVistaList().add(Mailcliente.getVista());
        }
        if (bonosretiradosCorreo.isSelected()) {
            elemento.getVistaList().add(bonosretiradosCorreo.getVista());
        }
        if (verInventarioBarajas.isSelected()) {
            elemento.getVistaList().add(verInventarioBarajas.getVista());
        }
        if (entrarsinregistro.isSelected()) {
            elemento.getVistaList().add(entrarsinregistro.getVista());
        }
        if (veripregistradas.isSelected()) {
            elemento.getVistaList().add(veripregistradas.getVista());
        }
        if (verTodosCasinos.isSelected()) {
            elemento.getVistaList().add(verTodosCasinos.getVista());
        }
        if (cambioEstadoBono.isSelected()) {
            elemento.getVistaList().add(cambioEstadoBono.getVista());
        }
        
        count = 0;
        if (!vistaCasinos.isSelected()) {
            count++;
        } else {
            elemento.getVistaList().add(vistaCasinos.getVista());
        }
        if (!vistaAtributos.isSelected()) {
            count++;
        } else {
            elemento.getVistaList().add(vistaAtributos.getVista());
        }
        if (!vistaTipo.isSelected()) {
            count++;
        } else {
            elemento.getVistaList().add(vistaTipo.getVista());
        }
        if (!vistaTipoevento.isSelected()) {
            count++;
        } else {
            elemento.getVistaList().add(vistaTipoevento.getVista());
        }
        if (!vistaCat.isSelected()) {
            count++;
        } else {
            elemento.getVistaList().add(vistaCat.getVista());
        }
        if (!vistaAcciones.isSelected()) {
            count++;
        } else {
            elemento.getVistaList().add(vistaAcciones.getVista());
        }
        if (!vistatipoDocumento.isSelected()) {
            count++;
        } else {
            elemento.getVistaList().add(vistatipoDocumento.getVista());
        }

        if (count < 7) {
            elemento.getVistaList().add(sessionBean.adminFacade.findVistasByNombre("AtributosMarketing"));
        }

        if (vertodoslosclientes.isSelected()) {
            elemento.getVistaList().add(vertodoslosclientes.getVista());
        }
        if (generarSolicitudBono.isSelected()) {
            elemento.getVistaList().add(generarSolicitudBono.getVista());
        }
        if (vistaClientesSGB.isSelected()) {
            elemento.getVistaList().add(vistaClientesSGB.getVista());
        }
        if (vistaPropositos.isSelected()) {
            elemento.getVistaList().add(vistaPropositos.getVista());
        }
        if (vistaAreas.isSelected()) {
            elemento.getVistaList().add(vistaAreas.getVista());
        }
        if (preAprobarSolicitudBono.isSelected()) {
            elemento.getVistaList().add(preAprobarSolicitudBono.getVista());
        }
        if (aprobarSolicitudBono.isSelected()) {
            elemento.getVistaList().add(aprobarSolicitudBono.getVista());
        }
        if (vistaPeticionesCupo.isSelected()) {
            elemento.getVistaList().add(vistaPeticionesCupo.getVista());
        }
        if (vistaEvMarketing.isSelected()) {
            elemento.getVistaList().add(vistaEvMarketing.getVista());
        }
        if (vistaEvHostess.isSelected()) {
            elemento.getVistaList().add(vistaEvHostess.getVista());
        }
        if (vistaReportes.isSelected()) {
            elemento.getVistaList().add(vistaReportes.getVista());
        }
        if (vistaConfiguraciones.isSelected()) {
            elemento.getVistaList().add(vistaConfiguraciones.getVista());
        }
        if (cupofidelizacion.isSelected()) {
            elemento.getVistaList().add(cupofidelizacion.getVista());
        }
        if (vistaNotificacion.isSelected()) {
            elemento.getVistaList().add(vistaNotificacion.getVista());
        }
        if (logs.isSelected()) {
            elemento.getVistaList().add(logs.getVista());
        }
        if (aceptarSolictudLotesBons.isSelected()) {
            elemento.getVistaList().add(aceptarSolictudLotesBons.getVista());
        }
        if (vistaSolicitudesLotes.isSelected()) {
            elemento.getVistaList().add(vistaSolicitudesLotes.getVista());
        }
        if (vistaLoteBono.isSelected()) {
            elemento.getVistaList().add(vistaLoteBono.getVista());
        }

        elemento.getFormularioList().clear();
        if (agregarClientesSGB.isSelected()) {
            elemento.getFormularioList().add(agregarClientesSGB.getFormulario());
        }
        if (actualizarClientesSGB.isSelected()) {
            elemento.getFormularioList().add(actualizarClientesSGB.getFormulario());
        }
        if (eliminarClientesSGB.isSelected()) {
            elemento.getFormularioList().add(eliminarClientesSGB.getFormulario());
        }
        if (agregarPropositos.isSelected()) {
            elemento.getFormularioList().add(agregarPropositos.getFormulario());
        }
        if (actualizarPropositos.isSelected()) {
            elemento.getFormularioList().add(actualizarPropositos.getFormulario());
        }
        if (eliminarPropositos.isSelected()) {
            elemento.getFormularioList().add(eliminarPropositos.getFormulario());
        }
        if (agregarAreas.isSelected()) {
            elemento.getFormularioList().add(agregarAreas.getFormulario());
        }
        if (actualizarAreas.isSelected()) {
            elemento.getFormularioList().add(actualizarAreas.getFormulario());
        }
        if (eliminarAreas.isSelected()) {
            elemento.getFormularioList().add(eliminarAreas.getFormulario());
        }
        if (agregarPeticionesCupo.isSelected()) {
            elemento.getFormularioList().add(agregarPeticionesCupo.getFormulario());
        }
        if (eliminarPeticionesCupo.isSelected()) {
            elemento.getFormularioList().add(eliminarPeticionesCupo.getFormulario());
        }
        if (eliminarAcceso.isSelected()) {
            elemento.getFormularioList().add(eliminarAcceso.getFormulario());
        }
        if (actualizarAcceso.isSelected()) {
            elemento.getFormularioList().add(actualizarAcceso.getFormulario());
        }
        if (agregarAcceso.isSelected()) {
            elemento.getFormularioList().add(agregarAcceso.getFormulario());
        }
        if (agregarTipoBono.isSelected()) {
            elemento.getFormularioList().add(agregarTipoBono.getFormulario());
        }
        if (actualizarTipoBono.isSelected()) {
            elemento.getFormularioList().add(actualizarTipoBono.getFormulario());
        }
        if (eliminarTipoBono.isSelected()) {
            elemento.getFormularioList().add(eliminarTipoBono.getFormulario());
        }
        if (agregarDenominacion.isSelected()) {
            elemento.getFormularioList().add(agregarDenominacion.getFormulario());
        }
        if (actualizarDenominacion.isSelected()) {
            elemento.getFormularioList().add(actualizarDenominacion.getFormulario());
        }
        if (eliminarDenominacion.isSelected()) {
            elemento.getFormularioList().add(eliminarDenominacion.getFormulario());
        }
        if (agregartipoDocumento.isSelected()) {
            elemento.getFormularioList().add(agregartipoDocumento.getFormulario());
        }
        if (actualizartipoDocumento.isSelected()) {
            elemento.getFormularioList().add(actualizartipoDocumento.getFormulario());
        }
        if (eliminartipoDocumento.isSelected()) {
            elemento.getFormularioList().add(eliminartipoDocumento.getFormulario());
        }
        if (agregarCargo.isSelected()) {
            elemento.getFormularioList().add(agregarCargo.getFormulario());
        }
        if (actualizarCargo.isSelected()) {
            elemento.getFormularioList().add(actualizarCargo.getFormulario());
        }
        if (eliminarCargo.isSelected()) {
            elemento.getFormularioList().add(eliminarCargo.getFormulario());
        }
        if (agregarUsuario.isSelected()) {
            elemento.getFormularioList().add(agregarUsuario.getFormulario());
        }
        if (actualizarUsuario.isSelected()) {
            elemento.getFormularioList().add(actualizarUsuario.getFormulario());
        }
        if (eliminarUsuario.isSelected()) {
            elemento.getFormularioList().add(eliminarUsuario.getFormulario());
        }

        if (agregarTipoevento.isSelected()) {
            elemento.getFormularioList().add(agregarTipoevento.getFormulario());
        }
        if (actualizarTipoevento.isSelected()) {
            elemento.getFormularioList().add(actualizarTipoevento.getFormulario());
        }
        if (eliminarTipoevento.isSelected()) {
            elemento.getFormularioList().add(eliminarTipoevento.getFormulario());
        }

        if (agregarTareas.isSelected()) {
            elemento.getFormularioList().add(agregarTareas.getFormulario());
        }
        if (actualizarTareas.isSelected()) {
            elemento.getFormularioList().add(actualizarTareas.getFormulario());
        }
        if (eliminarTareas.isSelected()) {
            elemento.getFormularioList().add(eliminarTareas.getFormulario());
        }
        if (agregarPerfiles.isSelected()) {
            elemento.getFormularioList().add(agregarPerfiles.getFormulario());
        }
        if (actualizarPerfiles.isSelected()) {
            elemento.getFormularioList().add(actualizarPerfiles.getFormulario());
        }
        if (eliminarPerfiles.isSelected()) {
            elemento.getFormularioList().add(eliminarPerfiles.getFormulario());
        }
        if (agregarVistas.isSelected()) {
            elemento.getFormularioList().add(agregarVistas.getFormulario());
        }
        if (actualizarVistas.isSelected()) {
            elemento.getFormularioList().add(actualizarVistas.getFormulario());
        }
        if (eliminarVistas.isSelected()) {
            elemento.getFormularioList().add(eliminarVistas.getFormulario());
        }
        if (agregarForm.isSelected()) {
            elemento.getFormularioList().add(agregarForm.getFormulario());
        }
        if (actualizarForm.isSelected()) {
            elemento.getFormularioList().add(actualizarForm.getFormulario());
        }
        if (eliminarForm.isSelected()) {
            elemento.getFormularioList().add(eliminarForm.getFormulario());
        }
        if (agregarCasinos.isSelected()) {
            elemento.getFormularioList().add(agregarCasinos.getFormulario());
        }
        if (actualizarCasinos.isSelected()) {
            elemento.getFormularioList().add(actualizarCasinos.getFormulario());
        }
        if (eliminarCasinos.isSelected()) {
            elemento.getFormularioList().add(eliminarCasinos.getFormulario());
        }
        if (agregarEventos.isSelected()) {
            elemento.getFormularioList().add(agregarEventos.getFormulario());
        }
        if (actualizarEventos.isSelected()) {
            elemento.getFormularioList().add(actualizarEventos.getFormulario());
        }
        if (eliminarEventos.isSelected()) {
            elemento.getFormularioList().add(eliminarEventos.getFormulario());
        }
        if (agregarClientes.isSelected()) {
            elemento.getFormularioList().add(agregarClientes.getFormulario());
        }
        if (actualizarClientes.isSelected()) {
            elemento.getFormularioList().add(actualizarClientes.getFormulario());
        }
        if (eliminarClientes.isSelected()) {
            elemento.getFormularioList().add(eliminarClientes.getFormulario());
        }
        if (agregarAtributos.isSelected()) {
            elemento.getFormularioList().add(agregarAtributos.getFormulario());
        }
        if (actualizarAtributos.isSelected()) {
            elemento.getFormularioList().add(actualizarAtributos.getFormulario());
        }
        if (eliminarAtributos.isSelected()) {
            elemento.getFormularioList().add(eliminarAtributos.getFormulario());
        }
        if (agregarTipo.isSelected()) {
            elemento.getFormularioList().add(agregarTipo.getFormulario());
        }
        if (actualizarTipo.isSelected()) {
            elemento.getFormularioList().add(actualizarTipo.getFormulario());
        }
        if (eliminarTipo.isSelected()) {
            elemento.getFormularioList().add(eliminarTipo.getFormulario());
        }
        if (agregarCat.isSelected()) {
            elemento.getFormularioList().add(agregarCat.getFormulario());
        }
        if (actualizarCat.isSelected()) {
            elemento.getFormularioList().add(actualizarCat.getFormulario());
        }
        if (eliminarCat.isSelected()) {
            elemento.getFormularioList().add(eliminarCat.getFormulario());
        }
        if (agregarAcciones.isSelected()) {
            elemento.getFormularioList().add(agregarAcciones.getFormulario());
        }
        if (actualizarAcciones.isSelected()) {
            elemento.getFormularioList().add(actualizarAcciones.getFormulario());
        }
        if (eliminarAcciones.isSelected()) {
            elemento.getFormularioList().add(eliminarAcciones.getFormulario());
        }
        if (agregarSolicitudesLotes.isSelected()) {
            elemento.getFormularioList().add(agregarSolicitudesLotes.getFormulario());
        }
        if (actualizarSolicitudesLotes.isSelected()) {
            elemento.getFormularioList().add(actualizarSolicitudesLotes.getFormulario());
        }
        if (eliminarSolicitudesLotes.isSelected()) {
            elemento.getFormularioList().add(eliminarSolicitudesLotes.getFormulario());
        }
        if (agregarBarajas.isSelected()) {
            elemento.getFormularioList().add(agregarBarajas.getFormulario());
        }
        if (actualizarBarajas.isSelected()) {
            elemento.getFormularioList().add(actualizarBarajas.getFormulario());
        }
        if (eliminarBarajas.isSelected()) {
            elemento.getFormularioList().add(eliminarBarajas.getFormulario());
        }
        if (agregarLoteBono.isSelected()) {
            elemento.getFormularioList().add(agregarLoteBono.getFormulario());
        }
        if (eliminarLoteBono.isSelected()) {
            elemento.getFormularioList().add(eliminarLoteBono.getFormulario());
        }

    }

    public VistaBoolean getVistaUsuario() {
        return vistaUsuario;
    }

    public void setVistaUsuario(VistaBoolean vistaUsuario) {
        this.vistaUsuario = vistaUsuario;
    }

    public VistaBoolean getVistaTareas() {
        return vistaTareas;
    }

    public void setVistaTareas(VistaBoolean vistaTareas) {
        this.vistaTareas = vistaTareas;
    }

    public VistaBoolean getVistaPerfiles() {
        return vistaPerfiles;
    }

    public void setVistaPerfiles(VistaBoolean vistaPerfiles) {
        this.vistaPerfiles = vistaPerfiles;
    }

    public VistaBoolean getVistaVistas() {
        return vistaVistas;
    }

    public void setVistaVistas(VistaBoolean vistaVistas) {
        this.vistaVistas = vistaVistas;
    }

    public VistaBoolean getVistaForm() {
        return vistaForm;
    }

    public void setVistaForm(VistaBoolean vistaForm) {
        this.vistaForm = vistaForm;
    }

    public VistaBoolean getVistaCasinos() {
        return vistaCasinos;
    }

    public void setVistaCasinos(VistaBoolean vistaCasinos) {
        this.vistaCasinos = vistaCasinos;
    }

    public VistaBoolean getVistaEventos() {
        return vistaEventos;
    }

    public void setVistaEventos(VistaBoolean vistaEventos) {
        this.vistaEventos = vistaEventos;
    }

    public VistaBoolean getVistaClientes() {
        return vistaClientes;
    }

    public void setVistaClientes(VistaBoolean vistaClientes) {
        this.vistaClientes = vistaClientes;
    }

    public VistaBoolean getVistaAtributos() {
        return vistaAtributos;
    }

    public void setVistaAtributos(VistaBoolean vistaAtributos) {
        this.vistaAtributos = vistaAtributos;
    }

    public VistaBoolean getVistaTipo() {
        return vistaTipo;
    }

    public void setVistaTipo(VistaBoolean vistaTipo) {
        this.vistaTipo = vistaTipo;
    }

    public VistaBoolean getVistaCat() {
        return vistaCat;
    }

    public void setVistaCat(VistaBoolean vistaCat) {
        this.vistaCat = vistaCat;
    }

    public VistaBoolean getVistaAcciones() {
        return vistaAcciones;
    }

    public void setVistaAcciones(VistaBoolean vistaAcciones) {
        this.vistaAcciones = vistaAcciones;
    }

    public VistaBoolean getVistaTipoevento() {
        return vistaTipoevento;
    }

    public void setVistaTipoevento(VistaBoolean vistaTipoevento) {
        this.vistaTipoevento = vistaTipoevento;
    }

    public VistaBoolean getVistaEvMarketing() {
        return vistaEvMarketing;
    }

    public void setVistaEvMarketing(VistaBoolean vistaEvMarketing) {
        this.vistaEvMarketing = vistaEvMarketing;
    }

    public VistaBoolean getVistaEvHostess() {
        return vistaEvHostess;
    }

    public void setVistaEvHostess(VistaBoolean vistaEvHostess) {
        this.vistaEvHostess = vistaEvHostess;
    }

    public VistaBoolean getVistaReportes() {
        return vistaReportes;
    }

    public void setVistaReportes(VistaBoolean vistaReportes) {
        this.vistaReportes = vistaReportes;
    }

    public VistaBoolean getVistaConfiguraciones() {
        return vistaConfiguraciones;
    }

    public void setVistaConfiguraciones(VistaBoolean vistaConfiguraciones) {
        this.vistaConfiguraciones = vistaConfiguraciones;
    }

    public VistaBoolean getVistaNotificacion() {
        return vistaNotificacion;
    }

    public void setVistaNotificacion(VistaBoolean vistaNotificacion) {
        this.vistaNotificacion = vistaNotificacion;
    }

    public VistaBoolean getCupofidelizacion() {
        return cupofidelizacion;
    }

    public void setCupofidelizacion(VistaBoolean cupofidelizacion) {
        this.cupofidelizacion = cupofidelizacion;
    }

    public VistaBoolean getVistatipoDocumento() {
        return vistatipoDocumento;
    }

    public void setVistatipoDocumento(VistaBoolean vistatipoDocumento) {
        this.vistatipoDocumento = vistatipoDocumento;
    }

    public VistaBoolean getVistaCargo() {
        return vistaCargo;
    }

    public void setVistaCargo(VistaBoolean vistaCargo) {
        this.vistaCargo = vistaCargo;
    }

    public VistaBoolean getLogs() {
        return logs;
    }

    public void setLogs(VistaBoolean logs) {
        this.logs = logs;
    }

    public VistaBoolean getAceptarSolictudLotesBons() {
        return aceptarSolictudLotesBons;
    }

    public void setAceptarSolictudLotesBons(VistaBoolean aceptarSolictudLotesBons) {
        this.aceptarSolictudLotesBons = aceptarSolictudLotesBons;
    }

    public VistaBoolean getVistaSolicitudesLotes() {
        return vistaSolicitudesLotes;
    }

    public void setVistaSolicitudesLotes(VistaBoolean vistaSolicitudesLotes) {
        this.vistaSolicitudesLotes = vistaSolicitudesLotes;
    }

    public FormularioBoolean getAgregarSolicitudesLotes() {
        return agregarSolicitudesLotes;
    }

    public void setAgregarSolicitudesLotes(FormularioBoolean agregarSolicitudesLotes) {
        this.agregarSolicitudesLotes = agregarSolicitudesLotes;
    }

    public FormularioBoolean getActualizarSolicitudesLotes() {
        return actualizarSolicitudesLotes;
    }

    public void setActualizarSolicitudesLotes(FormularioBoolean actualizarSolicitudesLotes) {
        this.actualizarSolicitudesLotes = actualizarSolicitudesLotes;
    }

    public FormularioBoolean getEliminarSolicitudesLotes() {
        return eliminarSolicitudesLotes;
    }

    public void setEliminarSolicitudesLotes(FormularioBoolean eliminarSolicitudesLotes) {
        this.eliminarSolicitudesLotes = eliminarSolicitudesLotes;
    }

    public FormularioBoolean getAgregarUsuario() {
        return agregarUsuario;
    }

    public void setAgregarUsuario(FormularioBoolean agregarUsuario) {
        this.agregarUsuario = agregarUsuario;
    }

    public FormularioBoolean getActualizarUsuario() {
        return actualizarUsuario;
    }

    public void setActualizarUsuario(FormularioBoolean actualizarUsuario) {
        this.actualizarUsuario = actualizarUsuario;
    }

    public FormularioBoolean getEliminarUsuario() {
        return eliminarUsuario;
    }

    public void setEliminarUsuario(FormularioBoolean eliminarUsuario) {
        this.eliminarUsuario = eliminarUsuario;
    }

    public FormularioBoolean getAgregarTareas() {
        return agregarTareas;
    }

    public void setAgregarTareas(FormularioBoolean agregarTareas) {
        this.agregarTareas = agregarTareas;
    }

    public FormularioBoolean getActualizarTareas() {
        return actualizarTareas;
    }

    public void setActualizarTareas(FormularioBoolean actualizarTareas) {
        this.actualizarTareas = actualizarTareas;
    }

    public FormularioBoolean getEliminarTareas() {
        return eliminarTareas;
    }

    public void setEliminarTareas(FormularioBoolean eliminarTareas) {
        this.eliminarTareas = eliminarTareas;
    }

    public FormularioBoolean getAgregarPerfiles() {
        return agregarPerfiles;
    }

    public void setAgregarPerfiles(FormularioBoolean agregarPerfiles) {
        this.agregarPerfiles = agregarPerfiles;
    }

    public FormularioBoolean getActualizarPerfiles() {
        return actualizarPerfiles;
    }

    public void setActualizarPerfiles(FormularioBoolean actualizarPerfiles) {
        this.actualizarPerfiles = actualizarPerfiles;
    }

    public FormularioBoolean getEliminarPerfiles() {
        return eliminarPerfiles;
    }

    public void setEliminarPerfiles(FormularioBoolean eliminarPerfiles) {
        this.eliminarPerfiles = eliminarPerfiles;
    }

    public FormularioBoolean getAgregarVistas() {
        return agregarVistas;
    }

    public void setAgregarVistas(FormularioBoolean agregarVistas) {
        this.agregarVistas = agregarVistas;
    }

    public FormularioBoolean getActualizarVistas() {
        return actualizarVistas;
    }

    public void setActualizarVistas(FormularioBoolean actualizarVistas) {
        this.actualizarVistas = actualizarVistas;
    }

    public FormularioBoolean getEliminarVistas() {
        return eliminarVistas;
    }

    public void setEliminarVistas(FormularioBoolean eliminarVistas) {
        this.eliminarVistas = eliminarVistas;
    }

    public FormularioBoolean getAgregarForm() {
        return agregarForm;
    }

    public void setAgregarForm(FormularioBoolean agregarForm) {
        this.agregarForm = agregarForm;
    }

    public FormularioBoolean getActualizarForm() {
        return actualizarForm;
    }

    public void setActualizarForm(FormularioBoolean actualizarForm) {
        this.actualizarForm = actualizarForm;
    }

    public FormularioBoolean getEliminarForm() {
        return eliminarForm;
    }

    public void setEliminarForm(FormularioBoolean eliminarForm) {
        this.eliminarForm = eliminarForm;
    }

    public FormularioBoolean getAgregarCasinos() {
        return agregarCasinos;
    }

    public void setAgregarCasinos(FormularioBoolean agregarCasinos) {
        this.agregarCasinos = agregarCasinos;
    }

    public FormularioBoolean getActualizarCasinos() {
        return actualizarCasinos;
    }

    public void setActualizarCasinos(FormularioBoolean actualizarCasinos) {
        this.actualizarCasinos = actualizarCasinos;
    }

    public FormularioBoolean getEliminarCasinos() {
        return eliminarCasinos;
    }

    public void setEliminarCasinos(FormularioBoolean eliminarCasinos) {
        this.eliminarCasinos = eliminarCasinos;
    }

    public FormularioBoolean getAgregarEventos() {
        return agregarEventos;
    }

    public void setAgregarEventos(FormularioBoolean agregarEventos) {
        this.agregarEventos = agregarEventos;
    }

    public FormularioBoolean getActualizarEventos() {
        return actualizarEventos;
    }

    public void setActualizarEventos(FormularioBoolean actualizarEventos) {
        this.actualizarEventos = actualizarEventos;
    }

    public FormularioBoolean getEliminarEventos() {
        return eliminarEventos;
    }

    public void setEliminarEventos(FormularioBoolean eliminarEventos) {
        this.eliminarEventos = eliminarEventos;
    }

    public FormularioBoolean getAgregarClientes() {
        return agregarClientes;
    }

    public void setAgregarClientes(FormularioBoolean agregarClientes) {
        this.agregarClientes = agregarClientes;
    }

    public FormularioBoolean getActualizarClientes() {
        return actualizarClientes;
    }

    public void setActualizarClientes(FormularioBoolean actualizarClientes) {
        this.actualizarClientes = actualizarClientes;
    }

    public FormularioBoolean getEliminarClientes() {
        return eliminarClientes;
    }

    public void setEliminarClientes(FormularioBoolean eliminarClientes) {
        this.eliminarClientes = eliminarClientes;
    }

    public FormularioBoolean getAgregarAtributos() {
        return agregarAtributos;
    }

    public void setAgregarAtributos(FormularioBoolean agregarAtributos) {
        this.agregarAtributos = agregarAtributos;
    }

    public FormularioBoolean getActualizarAtributos() {
        return actualizarAtributos;
    }

    public void setActualizarAtributos(FormularioBoolean actualizarAtributos) {
        this.actualizarAtributos = actualizarAtributos;
    }

    public FormularioBoolean getEliminarAtributos() {
        return eliminarAtributos;
    }

    public void setEliminarAtributos(FormularioBoolean eliminarAtributos) {
        this.eliminarAtributos = eliminarAtributos;
    }

    public FormularioBoolean getAgregarTipo() {
        return agregarTipo;
    }

    public void setAgregarTipo(FormularioBoolean agregarTipo) {
        this.agregarTipo = agregarTipo;
    }

    public FormularioBoolean getActualizarTipo() {
        return actualizarTipo;
    }

    public void setActualizarTipo(FormularioBoolean actualizarTipo) {
        this.actualizarTipo = actualizarTipo;
    }

    public FormularioBoolean getEliminarTipo() {
        return eliminarTipo;
    }

    public void setEliminarTipo(FormularioBoolean eliminarTipo) {
        this.eliminarTipo = eliminarTipo;
    }

    public FormularioBoolean getAgregarCat() {
        return agregarCat;
    }

    public void setAgregarCat(FormularioBoolean agregarCat) {
        this.agregarCat = agregarCat;
    }

    public FormularioBoolean getActualizarCat() {
        return actualizarCat;
    }

    public void setActualizarCat(FormularioBoolean actualizarCat) {
        this.actualizarCat = actualizarCat;
    }

    public FormularioBoolean getEliminarCat() {
        return eliminarCat;
    }

    public void setEliminarCat(FormularioBoolean eliminarCat) {
        this.eliminarCat = eliminarCat;
    }

    public FormularioBoolean getAgregarAcciones() {
        return agregarAcciones;
    }

    public void setAgregarAcciones(FormularioBoolean agregarAcciones) {
        this.agregarAcciones = agregarAcciones;
    }

    public FormularioBoolean getActualizarAcciones() {
        return actualizarAcciones;
    }

    public void setActualizarAcciones(FormularioBoolean actualizarAcciones) {
        this.actualizarAcciones = actualizarAcciones;
    }

    public FormularioBoolean getEliminarAcciones() {
        return eliminarAcciones;
    }

    public void setEliminarAcciones(FormularioBoolean eliminarAcciones) {
        this.eliminarAcciones = eliminarAcciones;
    }

    public FormularioBoolean getAgregarTipoevento() {
        return agregarTipoevento;
    }

    public void setAgregarTipoevento(FormularioBoolean agregarTipoevento) {
        this.agregarTipoevento = agregarTipoevento;
    }

    public FormularioBoolean getActualizarTipoevento() {
        return actualizarTipoevento;
    }

    public void setActualizarTipoevento(FormularioBoolean actualizarTipoevento) {
        this.actualizarTipoevento = actualizarTipoevento;
    }

    public FormularioBoolean getEliminarTipoevento() {
        return eliminarTipoevento;
    }

    public void setEliminarTipoevento(FormularioBoolean eliminarTipoevento) {
        this.eliminarTipoevento = eliminarTipoevento;
    }

    public FormularioBoolean getAgregartipoDocumento() {
        return agregartipoDocumento;
    }

    public void setAgregartipoDocumento(FormularioBoolean agregartipoDocumento) {
        this.agregartipoDocumento = agregartipoDocumento;
    }

    public FormularioBoolean getActualizartipoDocumento() {
        return actualizartipoDocumento;
    }

    public void setActualizartipoDocumento(FormularioBoolean actualizartipoDocumento) {
        this.actualizartipoDocumento = actualizartipoDocumento;
    }

    public FormularioBoolean getEliminartipoDocumento() {
        return eliminartipoDocumento;
    }

    public void setEliminartipoDocumento(FormularioBoolean eliminartipoDocumento) {
        this.eliminartipoDocumento = eliminartipoDocumento;
    }

    public FormularioBoolean getAgregarCargo() {
        return agregarCargo;
    }

    public void setAgregarCargo(FormularioBoolean agregarCargo) {
        this.agregarCargo = agregarCargo;
    }

    public FormularioBoolean getActualizarCargo() {
        return actualizarCargo;
    }

    public void setActualizarCargo(FormularioBoolean actualizarCargo) {
        this.actualizarCargo = actualizarCargo;
    }

    public FormularioBoolean getEliminarCargo() {
        return eliminarCargo;
    }

    public void setEliminarCargo(FormularioBoolean eliminarCargo) {
        this.eliminarCargo = eliminarCargo;
    }

    public VistaBoolean getVistaDenominacion() {
        return vistaDenominacion;
    }

    public void setVistaDenominacion(VistaBoolean vistaDenominacion) {
        this.vistaDenominacion = vistaDenominacion;
    }

    public FormularioBoolean getAgregarDenominacion() {
        return agregarDenominacion;
    }

    public void setAgregarDenominacion(FormularioBoolean agregarDenominacion) {
        this.agregarDenominacion = agregarDenominacion;
    }

    public FormularioBoolean getActualizarDenominacion() {
        return actualizarDenominacion;
    }

    public void setActualizarDenominacion(FormularioBoolean actualizarDenominacion) {
        this.actualizarDenominacion = actualizarDenominacion;
    }

    public FormularioBoolean getEliminarDenominacion() {
        return eliminarDenominacion;
    }

    public void setEliminarDenominacion(FormularioBoolean eliminarDenominacion) {
        this.eliminarDenominacion = eliminarDenominacion;
    }

    public VistaBoolean getVistaTipoBono() {
        return vistaTipoBono;
    }

    public void setVistaTipoBono(VistaBoolean vistaTipoBono) {
        this.vistaTipoBono = vistaTipoBono;
    }

    public FormularioBoolean getAgregarTipoBono() {
        return agregarTipoBono;
    }

    public void setAgregarTipoBono(FormularioBoolean agregarTipoBono) {
        this.agregarTipoBono = agregarTipoBono;
    }

    public FormularioBoolean getActualizarTipoBono() {
        return actualizarTipoBono;
    }

    public void setActualizarTipoBono(FormularioBoolean actualizarTipoBono) {
        this.actualizarTipoBono = actualizarTipoBono;
    }

    public FormularioBoolean getEliminarTipoBono() {
        return eliminarTipoBono;
    }

    public void setEliminarTipoBono(FormularioBoolean eliminarTipoBono) {
        this.eliminarTipoBono = eliminarTipoBono;
    }

    public VistaBoolean getVistaAcceso() {
        return vistaAcceso;
    }

    public void setVistaAcceso(VistaBoolean vistaAcceso) {
        this.vistaAcceso = vistaAcceso;
    }

    public FormularioBoolean getAgregarAcceso() {
        return agregarAcceso;
    }

    public void setAgregarAcceso(FormularioBoolean agregarAcceso) {
        this.agregarAcceso = agregarAcceso;
    }

    public FormularioBoolean getActualizarAcceso() {
        return actualizarAcceso;
    }

    public void setActualizarAcceso(FormularioBoolean actualizarAcceso) {
        this.actualizarAcceso = actualizarAcceso;
    }

    public FormularioBoolean getEliminarAcceso() {
        return eliminarAcceso;
    }

    public void setEliminarAcceso(FormularioBoolean eliminarAcceso) {
        this.eliminarAcceso = eliminarAcceso;
    }

    public VistaBoolean getVistaLoteBono() {
        return vistaLoteBono;
    }

    public void setVistaLoteBono(VistaBoolean vistaLoteBono) {
        this.vistaLoteBono = vistaLoteBono;
    }

    public FormularioBoolean getAgregarLoteBono() {
        return agregarLoteBono;
    }

    public void setAgregarLoteBono(FormularioBoolean agregarLoteBono) {
        this.agregarLoteBono = agregarLoteBono;
    }

    public FormularioBoolean getEliminarLoteBono() {
        return eliminarLoteBono;
    }

    public void setEliminarLoteBono(FormularioBoolean eliminarLoteBono) {
        this.eliminarLoteBono = eliminarLoteBono;
    }

    public VistaBoolean getVistaClientesSGB() {
        return vistaClientesSGB;
    }

    public void setVistaClientesSGB(VistaBoolean vistaClientesSGB) {
        this.vistaClientesSGB = vistaClientesSGB;
    }

    public FormularioBoolean getAgregarClientesSGB() {
        return agregarClientesSGB;
    }

    public void setAgregarClientesSGB(FormularioBoolean agregarClientesSGB) {
        this.agregarClientesSGB = agregarClientesSGB;
    }

    public FormularioBoolean getActualizarClientesSGB() {
        return actualizarClientesSGB;
    }

    public void setActualizarClientesSGB(FormularioBoolean actualizarClientesSGB) {
        this.actualizarClientesSGB = actualizarClientesSGB;
    }

    public FormularioBoolean getEliminarClientesSGB() {
        return eliminarClientesSGB;
    }

    public void setEliminarClientesSGB(FormularioBoolean eliminarClientesSGB) {
        this.eliminarClientesSGB = eliminarClientesSGB;
    }

    public VistaBoolean getVistaPropositos() {
        return vistaPropositos;
    }

    public void setVistaPropositos(VistaBoolean vistaPropositos) {
        this.vistaPropositos = vistaPropositos;
    }

    public FormularioBoolean getAgregarPropositos() {
        return agregarPropositos;
    }

    public void setAgregarPropositos(FormularioBoolean agregarPropositos) {
        this.agregarPropositos = agregarPropositos;
    }

    public FormularioBoolean getActualizarPropositos() {
        return actualizarPropositos;
    }

    public void setActualizarPropositos(FormularioBoolean actualizarPropositos) {
        this.actualizarPropositos = actualizarPropositos;
    }

    public FormularioBoolean getEliminarPropositos() {
        return eliminarPropositos;
    }

    public void setEliminarPropositos(FormularioBoolean eliminarPropositos) {
        this.eliminarPropositos = eliminarPropositos;
    }

    public VistaBoolean getVistaAreas() {
        return vistaAreas;
    }

    public void setVistaAreas(VistaBoolean vistaAreas) {
        this.vistaAreas = vistaAreas;
    }

    public FormularioBoolean getAgregarAreas() {
        return agregarAreas;
    }

    public void setAgregarAreas(FormularioBoolean agregarAreas) {
        this.agregarAreas = agregarAreas;
    }

    public FormularioBoolean getActualizarAreas() {
        return actualizarAreas;
    }

    public void setActualizarAreas(FormularioBoolean actualizarAreas) {
        this.actualizarAreas = actualizarAreas;
    }

    public FormularioBoolean getEliminarAreas() {
        return eliminarAreas;
    }

    public void setEliminarAreas(FormularioBoolean eliminarAreas) {
        this.eliminarAreas = eliminarAreas;
    }

    public VistaBoolean getVistaPeticionesCupo() {
        return vistaPeticionesCupo;
    }

    public void setVistaPeticionesCupo(VistaBoolean vistaPeticionesCupo) {
        this.vistaPeticionesCupo = vistaPeticionesCupo;
    }

    public FormularioBoolean getAgregarPeticionesCupo() {
        return agregarPeticionesCupo;
    }

    public void setAgregarPeticionesCupo(FormularioBoolean agregarPeticionesCupo) {
        this.agregarPeticionesCupo = agregarPeticionesCupo;
    }

    public FormularioBoolean getEliminarPeticionesCupo() {
        return eliminarPeticionesCupo;
    }

    public void setEliminarPeticionesCupo(FormularioBoolean eliminarPeticionesCupo) {
        this.eliminarPeticionesCupo = eliminarPeticionesCupo;
    }

    public VistaBoolean getGenerarSolicitudBono() {
        return generarSolicitudBono;
    }

    public void setGenerarSolicitudBono(VistaBoolean generarSolicitudBono) {
        this.generarSolicitudBono = generarSolicitudBono;
    }

    public VistaBoolean getPreAprobarSolicitudBono() {
        return preAprobarSolicitudBono;
    }

    public void setPreAprobarSolicitudBono(VistaBoolean preAprobarSolicitudBono) {
        this.preAprobarSolicitudBono = preAprobarSolicitudBono;
    }

    public VistaBoolean getAprobarSolicitudBono() {
        return aprobarSolicitudBono;
    }

    public void setAprobarSolicitudBono(VistaBoolean aprobarSolicitudBono) {
        this.aprobarSolicitudBono = aprobarSolicitudBono;
    }

    public VistaBoolean getVertodoslosclientes() {
        return vertodoslosclientes;
    }

    public void setVertodoslosclientes(VistaBoolean vertodoslosclientes) {
        this.vertodoslosclientes = vertodoslosclientes;
    }

    public VistaBoolean getActSolicitudSalida() {
        return actSolicitudSalida;
    }

    public void setActSolicitudSalida(VistaBoolean actSolicitudSalida) {
        this.actSolicitudSalida = actSolicitudSalida;
    }

    public VistaBoolean getAceprtarSolicitudSalida() {
        return aceprtarSolicitudSalida;
    }

    public void setAceprtarSolicitudSalida(VistaBoolean aceprtarSolicitudSalida) {
        this.aceprtarSolicitudSalida = aceprtarSolicitudSalida;
    }

    public VistaBoolean getControlSalidaBonos() {
        return controlSalidaBonos;
    }

    public void setControlSalidaBonos(VistaBoolean controlSalidaBonos) {
        this.controlSalidaBonos = controlSalidaBonos;
    }

    public VistaBoolean getBonosValidarVer() {
        return bonosValidarVer;
    }

    public void setBonosValidarVer(VistaBoolean bonosValidarVer) {
        this.bonosValidarVer = bonosValidarVer;
    }

    public VistaBoolean getBonosValidarEjecutar() {
        return bonosValidarEjecutar;
    }

    public void setBonosValidarEjecutar(VistaBoolean bonosValidarEjecutar) {
        this.bonosValidarEjecutar = bonosValidarEjecutar;
    }

    public VistaBoolean getBonosEntregarCajaVer() {
        return bonosEntregarCajaVer;
    }

    public void setBonosEntregarCajaVer(VistaBoolean bonosEntregarCajaVer) {
        this.bonosEntregarCajaVer = bonosEntregarCajaVer;
    }

    public VistaBoolean getBonosEntregarCajaEjecutar() {
        return bonosEntregarCajaEjecutar;
    }

    public void setBonosEntregarCajaEjecutar(VistaBoolean bonosEntregarCajaEjecutar) {
        this.bonosEntregarCajaEjecutar = bonosEntregarCajaEjecutar;
    }

    public VistaBoolean getBonosRecibirVer() {
        return bonosRecibirVer;
    }

    public void setBonosRecibirVer(VistaBoolean bonosRecibirVer) {
        this.bonosRecibirVer = bonosRecibirVer;
    }

    public VistaBoolean getBonosRecibirEjecutar() {
        return bonosRecibirEjecutar;
    }

    public void setBonosRecibirEjecutar(VistaBoolean bonosRecibirEjecutar) {
        this.bonosRecibirEjecutar = bonosRecibirEjecutar;
    }

    public VistaBoolean getBonosEntregarClienteVer() {
        return bonosEntregarClienteVer;
    }

    public void setBonosEntregarClienteVer(VistaBoolean bonosEntregarClienteVer) {
        this.bonosEntregarClienteVer = bonosEntregarClienteVer;
    }

    public VistaBoolean getBonosEntregarClienteEjecutar() {
        return bonosEntregarClienteEjecutar;
    }

    public void setBonosEntregarClienteEjecutar(VistaBoolean bonosEntregarClienteEjecutar) {
        this.bonosEntregarClienteEjecutar = bonosEntregarClienteEjecutar;
    }

    public VistaBoolean getBonoCanjearVer() {
        return bonoCanjearVer;
    }

    public void setBonoCanjearVer(VistaBoolean bonoCanjearVer) {
        this.bonoCanjearVer = bonoCanjearVer;
    }

    public VistaBoolean getBonoCanjearEjecutar() {
        return bonoCanjearEjecutar;
    }

    public void setBonoCanjearEjecutar(VistaBoolean bonoCanjearEjecutar) {
        this.bonoCanjearEjecutar = bonoCanjearEjecutar;
    }

    public VistaBoolean getSolicitudCambioCupo() {
        return solicitudCambioCupo;
    }

    public void setSolicitudCambioCupo(VistaBoolean solicitudCambioCupo) {
        this.solicitudCambioCupo = solicitudCambioCupo;
    }

    public VistaBoolean getMailcliente() {
        return Mailcliente;
    }

    public void setMailcliente(VistaBoolean Mailcliente) {
        this.Mailcliente = Mailcliente;
    }

    public VistaBoolean getVerTodosCasinos() {
        return verTodosCasinos;
    }

    public void setVerTodosCasinos(VistaBoolean verTodosCasinos) {
        this.verTodosCasinos = verTodosCasinos;
    }

    public VistaBoolean getCambioEstadoBono() {
        return cambioEstadoBono;
    }

    public void setCambioEstadoBono(VistaBoolean cambioEstadoBono) {
        this.cambioEstadoBono = cambioEstadoBono;
    }

    public VistaBoolean getBonosVerificarVer() {
        return bonosVerificarVer;
    }

    public void setBonosVerificarVer(VistaBoolean bonosVerificarVer) {
        this.bonosVerificarVer = bonosVerificarVer;
    }

    public VistaBoolean getBonosVerificarEjecutar() {
        return bonosVerificarEjecutar;
    }

    public void setBonosVerificarEjecutar(VistaBoolean bonosVerificarEjecutar) {
        this.bonosVerificarEjecutar = bonosVerificarEjecutar;
    }

    public VistaBoolean getBonosAutorizarVer() {
        return bonosAutorizarVer;
    }

    public void setBonosAutorizarVer(VistaBoolean bonosAutorizarVer) {
        this.bonosAutorizarVer = bonosAutorizarVer;
    }

    public VistaBoolean getBonosAutorizarEjecutar() {
        return bonosAutorizarEjecutar;
    }

    public void setBonosAutorizarEjecutar(VistaBoolean bonosAutorizarEjecutar) {
        this.bonosAutorizarEjecutar = bonosAutorizarEjecutar;
    }

    public VistaBoolean getEntregalotesgeneradacorreo() {
        return entregalotesgeneradacorreo;
    }

    public void setEntregalotesgeneradacorreo(VistaBoolean entregalotesgeneradacorreo) {
        this.entregalotesgeneradacorreo = entregalotesgeneradacorreo;
    }

    public VistaBoolean getEntregalotesaceptadacorreo() {
        return entregalotesaceptadacorreo;
    }

    public void setEntregalotesaceptadacorreo(VistaBoolean entregalotesaceptadacorreo) {
        this.entregalotesaceptadacorreo = entregalotesaceptadacorreo;
    }

    public VistaBoolean getSolicitudbonosgeneradacorreo() {
        return solicitudbonosgeneradacorreo;
    }

    public void setSolicitudbonosgeneradacorreo(VistaBoolean solicitudbonosgeneradacorreo) {
        this.solicitudbonosgeneradacorreo = solicitudbonosgeneradacorreo;
    }

    public VistaBoolean getSolicitudbonospreaprobadacorreo() {
        return solicitudbonospreaprobadacorreo;
    }

    public void setSolicitudbonospreaprobadacorreo(VistaBoolean solicitudbonospreaprobadacorreo) {
        this.solicitudbonospreaprobadacorreo = solicitudbonospreaprobadacorreo;
    }

    public VistaBoolean getSolicitudaprobadacorreo() {
        return solicitudaprobadacorreo;
    }

    public void setSolicitudaprobadacorreo(VistaBoolean solicitudaprobadacorreo) {
        this.solicitudaprobadacorreo = solicitudaprobadacorreo;
    }

    public VistaBoolean getSenalbusquedacorreo() {
        return senalbusquedacorreo;
    }

    public void setSenalbusquedacorreo(VistaBoolean senalbusquedacorreo) {
        this.senalbusquedacorreo = senalbusquedacorreo;
    }

    public VistaBoolean getEntregarbonocajacorreo() {
        return entregarbonocajacorreo;
    }

    public void setEntregarbonocajacorreo(VistaBoolean entregarbonocajacorreo) {
        this.entregarbonocajacorreo = entregarbonocajacorreo;
    }

    public VistaBoolean getRecibirbonocorreo() {
        return recibirbonocorreo;
    }

    public void setRecibirbonocorreo(VistaBoolean recibirbonocorreo) {
        this.recibirbonocorreo = recibirbonocorreo;
    }

    public VistaBoolean getNotificacionescorreo() {
        return notificacionescorreo;
    }

    public void setNotificacionescorreo(VistaBoolean notificacionescorreo) {
        this.notificacionescorreo = notificacionescorreo;
    }

    public VistaBoolean getCorreoRequerimientoLoteCreado() {
        return correoRequerimientoLoteCreado;
    }

    public void setCorreoRequerimientoLoteCreado(VistaBoolean correoRequerimientoLoteCreado) {
        this.correoRequerimientoLoteCreado = correoRequerimientoLoteCreado;
    }

    public VistaBoolean getCorreoRequerimeintoLoteOrdenado() {
        return correoRequerimeintoLoteOrdenado;
    }

    public void setCorreoRequerimeintoLoteOrdenado(VistaBoolean correoRequerimeintoLoteOrdenado) {
        this.correoRequerimeintoLoteOrdenado = correoRequerimeintoLoteOrdenado;
    }

    public VistaBoolean getCorreoRequerimientoLoteRechazado() {
        return correoRequerimientoLoteRechazado;
    }

    public void setCorreoRequerimientoLoteRechazado(VistaBoolean correoRequerimientoLoteRechazado) {
        this.correoRequerimientoLoteRechazado = correoRequerimientoLoteRechazado;
    }

    public VistaBoolean getCorreoRequerimentoLoteRecibido() {
        return correoRequerimentoLoteRecibido;
    }

    public void setCorreoRequerimentoLoteRecibido(VistaBoolean correoRequerimentoLoteRecibido) {
        this.correoRequerimentoLoteRecibido = correoRequerimentoLoteRecibido;
    }

    public VistaBoolean getCorreoSolicitudEntradaValidada() {
        return correoSolicitudEntradaValidada;
    }

    public void setCorreoSolicitudEntradaValidada(VistaBoolean correoSolicitudEntradaValidada) {
        this.correoSolicitudEntradaValidada = correoSolicitudEntradaValidada;
    }

    public VistaBoolean getCorreoSolicitudEntradaDevuelta() {
        return correoSolicitudEntradaDevuelta;
    }

    public void setCorreoSolicitudEntradaDevuelta(VistaBoolean correoSolicitudEntradaDevuelta) {
        this.correoSolicitudEntradaDevuelta = correoSolicitudEntradaDevuelta;
    }

    public VistaBoolean getDevolverSolLote() {
        return devolverSolLote;
    }

    public void setDevolverSolLote(VistaBoolean devolverSolLote) {
        this.devolverSolLote = devolverSolLote;
    }

    public VistaBoolean getEnviarSolLote() {
        return enviarSolLote;
    }

    public void setEnviarSolLote(VistaBoolean enviarSolLote) {
        this.enviarSolLote = enviarSolLote;
    }

    public VistaBoolean getRechazarRequerimiento() {
        return rechazarRequerimiento;
    }

    public void setRechazarRequerimiento(VistaBoolean rechazarRequerimiento) {
        this.rechazarRequerimiento = rechazarRequerimiento;
    }

    public VistaBoolean getOrdenarRequerimiento() {
        return ordenarRequerimiento;
    }

    public void setOrdenarRequerimiento(VistaBoolean ordenarRequerimiento) {
        this.ordenarRequerimiento = ordenarRequerimiento;
    }

    public VistaBoolean getCrearRequerimiento() {
        return crearRequerimiento;
    }

    public void setCrearRequerimiento(VistaBoolean crearRequerimiento) {
        this.crearRequerimiento = crearRequerimiento;
    }

    public VistaBoolean getVerRequerimiento() {
        return verRequerimiento;
    }

    public void setVerRequerimiento(VistaBoolean verRequerimiento) {
        this.verRequerimiento = verRequerimiento;
    }

    public VistaBoolean getBonosretiradosCorreo() {
        return bonosretiradosCorreo;
    }

    public void setBonosretiradosCorreo(VistaBoolean bonosretiradosCorreo) {
        this.bonosretiradosCorreo = bonosretiradosCorreo;
    }

    public VistaBoolean getVistaSolicitudesCasino() {
        return vistaSolicitudesCasino;
    }

    public void setVistaSolicitudesCasino(VistaBoolean vistaSolicitudesCasino) {
        this.vistaSolicitudesCasino = vistaSolicitudesCasino;
    }

    public VistaBoolean getVeripregistradas() {
        return veripregistradas;
    }

    public void setVeripregistradas(VistaBoolean veripregistradas) {
        this.veripregistradas = veripregistradas;
    }

    public VistaBoolean getEntrarsinregistro() {
        return entrarsinregistro;
    }

    public void setEntrarsinregistro(VistaBoolean entrarsinregistro) {
        this.entrarsinregistro = entrarsinregistro;
    }

    public VistaBoolean getVistaBarajas() {
        return vistaBarajas;
    }

    public void setVistaBarajas(VistaBoolean vistaBarajas) {
        this.vistaBarajas = vistaBarajas;
    }

    public FormularioBoolean getAgregarBarajas() {
        return agregarBarajas;
    }

    public void setAgregarBarajas(FormularioBoolean agregarBarajas) {
        this.agregarBarajas = agregarBarajas;
    }

    public FormularioBoolean getActualizarBarajas() {
        return actualizarBarajas;
    }

    public void setActualizarBarajas(FormularioBoolean actualizarBarajas) {
        this.actualizarBarajas = actualizarBarajas;
    }

    public FormularioBoolean getEliminarBarajas() {
        return eliminarBarajas;
    }

    public void setEliminarBarajas(FormularioBoolean eliminarBarajas) {
        this.eliminarBarajas = eliminarBarajas;
    }

    public VistaBoolean getVerInventarioBarajas() {
        return verInventarioBarajas;
    }

    public void setVerInventarioBarajas(VistaBoolean verInventarioBarajas) {
        this.verInventarioBarajas = verInventarioBarajas;
    }

}
