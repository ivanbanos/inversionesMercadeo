/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Accion;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Categoria;
import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Evento;
import com.invbf.sistemagestionmercadeo.entity.Listasclientestareas;
import com.invbf.sistemagestionmercadeo.entity.Tarea;
import com.invbf.sistemagestionmercadeo.entity.Tipojuego;
import com.invbf.sistemagestionmercadeo.entity.Tipotarea;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.util.CasinoBoolean;
import com.invbf.sistemagestionmercadeo.util.CategoriaBoolean;
import com.invbf.sistemagestionmercadeo.util.ClienteDTO;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import com.invbf.sistemagestionmercadeo.util.Notificador;
import static com.invbf.sistemagestionmercadeo.util.Notificador.AVISO_TAREA_ASIGNADA;
import com.invbf.sistemagestionmercadeo.util.TipoJuegoBoolean;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class TareaAccionBean implements Serializable {

    public void upload() {
        filename = file.getFileName().replace(" ", "");
        System.out.println(filename.replace(" ", ""));
        sessionBean.marketingUserFacade.guardarImagen(file.getContents(), filename);
        sessionBean.setAttribute("imagen", file.getContents());
        System.out.println("File name: " + filename);
        file = null;

    }

    public void validateFile(FacesContext ctx,
            UIComponent comp,
            Object value) {
        List<FacesMessage> msgs = new ArrayList<FacesMessage>();
        Part file = (Part) value;
        if (file.getSize() > 1024) {
            msgs.add(new FacesMessage("file too big"));
        }
        if (!"text/plain".equals(file.getContentType())) {
            msgs.add(new FacesMessage("not a text file"));
        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }

    private Tarea elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<Tipotarea> tipotareas;
    private Evento evento;
    private List<Usuario> usuarioses;
    private List<CategoriaBoolean> categoriasBoolean;
    private List<TipoJuegoBoolean> tipoJuegosBoolean;
    private DualListModel<Usuario> todosusuarioses;
    private boolean todoscat;
    private boolean todostip;
    private String asunto;
    private String cuerpo;
    private boolean enviarcorreo;
    private boolean skip;
    private List<CasinoBoolean> casinoBooleans;
    private boolean todoscasinos;
    private List<ClienteDTO> clientesSelected;
    private List<ClienteDTO> clientes;
    private UploadedFile file;
    private String filename;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public TareaAccionBean() {
        cuerpo = "";
    }

    @PostConstruct
    public void init() {
        clientesSelected = new ArrayList<ClienteDTO>();
        clientes = new ArrayList<ClienteDTO>();
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("tareas");
        if (!sessionBean.perfilViewMatch("Tareas")) {
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }

        if (sessionBean.getAttributes("idTarea") == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("tareas.xhtml");
            } catch (IOException ex) {
            }
        }
        List<Categoria> categorias = sessionBean.marketingUserFacade.findAllCategorias();
        List<Tipojuego> tipoJuegos = sessionBean.marketingUserFacade.findAllTiposjuegos();
        categoriasBoolean = new ArrayList<CategoriaBoolean>();
        tipoJuegosBoolean = new ArrayList<TipoJuegoBoolean>();
        for (Tipojuego tipoJuego : tipoJuegos) {
            tipoJuegosBoolean.add(new TipoJuegoBoolean(tipoJuego, false));
        }
        for (Categoria categoria : categorias) {
            categoriasBoolean.add(new CategoriaBoolean(categoria, false));
        }
        if (((Integer) sessionBean.getAttributes("idTarea")) == 0) {
            elemento = new Tarea();
            if (sessionBean.getAttributes("idEvento") != null) {
                evento = sessionBean.marketingUserFacade.findEvento((Integer) sessionBean.getAttributes("idEvento"));
                elemento.setIdEvento(evento);
            }
            elemento.setEstado("Ninguno");
            elemento.setTipo(new Tipotarea(0));
            usuarioses = sessionBean.adminFacade.findAllUsuariosHostess();
            elemento.setListasclientestareasList(new ArrayList<Listasclientestareas>());
            elemento.setUsuarioList(new ArrayList<Usuario>());
            todosusuarioses = new DualListModel<Usuario>(usuarioses, elemento.getUsuarioList());
        } else {
            elemento = sessionBean.marketingUserFacade.findTarea((Integer) sessionBean.getAttributes("idTarea"));
            evento = elemento.getIdEvento();
            if (elemento.getCategorias() == null || elemento.getCategorias().equals("")) {
                elemento.setCategorias("");
            } else {
                for (String s : elemento.getCategorias().split(" ")) {
                    for (CategoriaBoolean cb : categoriasBoolean) {
                        if (cb.compareIdCategorias(Integer.parseInt(s))) {
                            cb.setSelected(true);
                        }
                    }
                }
            }
            if (elemento.getTiposdejuegos() == null || elemento.getTiposdejuegos().equals("")) {
                elemento.setTiposdejuegos("");
            } else {
                for (String s : elemento.getTiposdejuegos().split(" ")) {
                    for (TipoJuegoBoolean tjb : tipoJuegosBoolean) {
                        if (tjb.compareIdTipoJuego(Integer.parseInt(s))) {
                            tjb.setSelected(true);
                        }
                    }
                }
            }
            usuarioses = sessionBean.adminFacade.findAllUsuariosHostess();
            for (Usuario u : elemento.getUsuarioList()) {
                if (usuarioses.contains(u)) {
                    usuarioses.remove(u);
                }
            }
            todosusuarioses = new DualListModel<Usuario>(usuarioses, elemento.getUsuarioList());
        }
        if (sessionBean.getAttributes("tipo") != null) {
            elemento.setTipo(sessionBean.marketingUserFacade.findTipoTarea((Integer) sessionBean.getAttributes("tipo")));
        }
        if (elemento.getTipo() == null) {
            elemento.setTipo(new Tipotarea());
        }
        List<Casino> casinos = sessionBean.getUsuario().getCasinoList();
        casinoBooleans = new ArrayList<CasinoBoolean>();
        for (Casino casinob : casinos) {
            casinoBooleans.add(new CasinoBoolean(casinob, true));
        }
        tipotareas = sessionBean.marketingUserFacade.findAllTipotarea();
        skip = false;
    }

    public Tarea getElemento() {
        return elemento;
    }

    public void setElemento(Tarea elemento) {
        this.elemento = elemento;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public void sacarCliente() {
        for (Iterator<ClienteDTO> iterator = clientesSelected.iterator(); iterator.hasNext();) {
            ClienteDTO next = iterator.next();
            clientes.remove(next);
            iterator.remove();
        }
    }

    public void createClientes() {

        clientesSelected = new ArrayList<ClienteDTO>();

        ArrayList<ClienteDTO> al = new ArrayList<ClienteDTO>();
        List<Cliente> todosclienteses = new ArrayList<Cliente>();
        for (CasinoBoolean casino : casinoBooleans) {
            if (casino.isSelected()) {
                todosclienteses.addAll(sessionBean.marketingUserFacade.findAllClientesCasinos(casino.getCasino(), "", "", "", null, ""));
            }
        }
        boolean noCatselected = true;
        boolean noTipselected = true;
        for (CategoriaBoolean cb : categoriasBoolean) {
            if (todoscat) {
                cb.setSelected(true);
                continue;
            }
            if (cb.isSelected()) {
                noCatselected = false;
                break;
            }
        }
        for (TipoJuegoBoolean tjb : tipoJuegosBoolean) {
            if (todostip) {
                tjb.setSelected(true);
                continue;
            }
            if (tjb.isSelected()) {
                noTipselected = false;
                break;
            }
        }
        for (Cliente c : todosclienteses) {
            if (elemento.getTipo().getIdTipotarea() != 3 || (elemento.getTipo().getIdTipotarea() == 3 && c.getSendEmail() == 1)) {
                boolean siCategoria = false;
                boolean siTipoJuego = false;
                if (noCatselected) {
                    siCategoria = true;
                } else {
                    for (CategoriaBoolean cb : categoriasBoolean) {
                        if (cb.isSelected()) {
                            if (c.getIdCategorias().equals(cb.getCategoria())) {
                                siCategoria = true;
                                break;
                            }
                        }
                    }
                }
                if (noTipselected) {
                    siTipoJuego = true;
                } else {
                    for (TipoJuegoBoolean tjb : tipoJuegosBoolean) {
                        if (tjb.isSelected()) {
                            for (Tipojuego tj : c.getTipojuegoList()) {
                                if (tj.equals(tjb.getTipoJuego())) {
                                    siTipoJuego = true;
                                    break;
                                }
                            }
                        }
                    }
                }
                if (siCategoria && siTipoJuego) {
                    System.out.println("Entra Cliente");
                    clientes.add(new ClienteDTO(c));
                } else {
                    if (elemento.getIdTarea() != null) {

                        clientes.add(new ClienteDTO(c));
                    }
                }
            }
            elemento.setCategorias("");
            for (CategoriaBoolean cb : categoriasBoolean) {
                if (cb.isSelected()) {
                    elemento.setCategorias(elemento.getCategorias() + cb.getCategoria().getIdCategorias() + " ");
                }
            }

            elemento.setTiposdejuegos("");
            for (TipoJuegoBoolean tjb : tipoJuegosBoolean) {
                if (tjb.isSelected()) {
                    elemento.setTiposdejuegos(elemento.getTiposdejuegos() + tjb.getTipoJuego().getIdTipoJuego() + " ");
                }
            }
        }
    }

    public void enviarEmail() {
        upload();
        guardar();
    }

    public void guardar() {
        guardar:
        {
            if (elemento.getTipo() == null || elemento.getTipo().getIdTipotarea() == 0) {
                FacesUtil.addErrorMessage("Elemento no creado", "Debe seleccionar un tipo de tarea");
                break guardar;
            }
            Calendar fechainicio = Calendar.getInstance();
            Calendar fechafinal = Calendar.getInstance();

            Calendar nowDate = Calendar.getInstance();
            if (!elemento.getTipo().getNombre().equals("EMAIL")) {
                fechainicio.setTime(elemento.getFechaInicio());
                fechafinal.setTime(elemento.getFechaFinalizacion());
            }

            elemento.setFechaInicio(nowDate.getTime());
            elemento.setFechaFinalizacion(nowDate.getTime());
            System.out.println(fechainicio.getTime());
            System.out.println(fechafinal.getTime());

            System.out.println(nowDate.getTime());
            if (elemento.getIdTarea() == null || elemento.getIdTarea() == 0) {
                if (fechafinal.before(fechainicio)) {
                    FacesUtil.addErrorMessage("Fechas incorrectas", "Fecha final antes de la fecha inicial");
                    break guardar;
                }
            }
            elemento.setEstado("POR INICIAR");
            if (fechainicio.before(nowDate)) {
                elemento.setEstado("ACTIVO");
            }
            if (fechafinal.before(nowDate)) {
                elemento.setEstado("VENCIDO");
            }
            sessionBean.registrarlog("actualizar", "Eventos", "Tarea guardada " + elemento.getNombre());

            elemento.setUsuarioList(todosusuarioses.getTarget());

            sessionBean.actualizarUsuario();

            usuarioses = sessionBean.adminFacade.findAllUsuariosHostess();
            for (Usuario u : elemento.getUsuarioList()) {
                if (usuarioses.contains(u)) {
                    usuarioses.remove(u);
                }
            }
            Accion estadoscliente = sessionBean.marketingUserFacade.findByNombreAccion("INICIAL");
            todosusuarioses = new DualListModel<Usuario>(usuarioses, elemento.getUsuarioList());
            elemento.setListasclientestareasList(new ArrayList<Listasclientestareas>());
            for (ClienteDTO cdto : clientes) {
                Listasclientestareas lct = new Listasclientestareas();
                lct.setIdAccion(estadoscliente);
                lct.setTarea(elemento);
                lct.setCliente(new Cliente(cdto.getIdCliente()));
                lct.setCount(0);
                elemento.getListasclientestareasList().add(lct);
            }
            if (elemento.getTipo().getNombre().equals("EMAIL")) {
                elemento.setEstado("VENCIDO");
            }
            elemento = sessionBean.marketingUserFacade.guardarTarea(elemento);
            if (elemento.getTipo().getNombre().equals("EMAIL")) {
                enviarCorreo();
            } else {
                for (Usuario s : elemento.getUsuarioList()) {
                    Notificador.notificar(Notificador.AVISO_TAREA_ASIGNADA, "Se le ha asignado la tarea " + elemento.getNombre() + " para ser ejecutada.", "Tarea Asignada", s.getUsuariodetalle().getCorreo());
                }
            }
            goBack();
            FacesUtil.addInfoMessage("Tarea guardado con exito", elemento.getNombre());

        }
    }

    public List<Tipotarea> getTipotareas() {
        return tipotareas;
    }

    public void setTipotareas(List<Tipotarea> tipotareas) {
        this.tipotareas = tipotareas;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public List<CategoriaBoolean> getCategoriasBoolean() {
        return categoriasBoolean;
    }

    public void setCategoriasBoolean(List<CategoriaBoolean> categoriasBoolean) {
        this.categoriasBoolean = categoriasBoolean;
    }

    public List<TipoJuegoBoolean> getTipoJuegosBoolean() {
        return tipoJuegosBoolean;
    }

    public void setTipoJuegosBoolean(List<TipoJuegoBoolean> tipoJuegosBoolean) {
        this.tipoJuegosBoolean = tipoJuegosBoolean;
    }

    public DualListModel<Usuario> getTodosusuarioses() {
        return todosusuarioses;
    }

    public void setTodosusuarioses(DualListModel<Usuario> todosusuarioses) {
        this.todosusuarioses = todosusuarioses;
    }

    public void enviarCorreo() {
        sessionBean.sessionFacade.enviarCorreo(elemento, asunto, cuerpo, filename);
    }

    public boolean isTodoscat() {
        return todoscat;
    }

    public void setTodoscat(boolean todoscat) {
        this.todoscat = todoscat;
    }

    public boolean isTodostip() {
        return todostip;
    }

    public void setTodostip(boolean todostip) {
        this.todostip = todostip;
    }

    public void goBack() {
        try {
            if (sessionBean.getAttributes("from").equals("evento")) {
                sessionBean.setAttribute("idEvento", elemento.getIdEvento().getIdEvento());
                FacesContext.getCurrentInstance().getExternalContext().redirect("MarketingEventoManejadorView.xhtml");
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect("tareas.xhtml");
            }
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(TareaAccionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public long getConteo() {
        return clientes.size();
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public boolean isEnviarcorreo() {
        return enviarcorreo;
    }

    public void setEnviarcorreo(boolean enviarcorreo) {
        this.enviarcorreo = enviarcorreo;
    }

    public String onFlowProcess(FlowEvent event) {

        if (elemento.getTipo().getNombre() == null) {
            elemento.setTipo(sessionBean.marketingUserFacade.findTipoTarea(elemento.getTipo().getIdTipotarea()));
        }
        if (event.getNewStep().equals("correp")) {
            if (!elemento.getTipo().getNombre().equals("EMAIL")) {
                if (event.getOldStep().equals("clientes")) {
                    return "general";
                } else {
                    return "clientes";
                }
            }
        }
        if (event.getNewStep().equals("dattarea")) {
            if (elemento.getTipo().getNombre().equals("EMAIL")) {
                if (event.getOldStep().equals("clientes")) {
                    return "tipotarea";
                } else {
                    return "clientes";
                }
            }
        }

        return event.getNewStep();
    }

    public void quitarCliente(Integer cliente) {
        List<Listasclientestareas> lcts = elemento.getListasclientestareasList();
        for (Iterator<Listasclientestareas> iterator = lcts.iterator(); iterator.hasNext();) {
            Listasclientestareas lct = iterator.next();
            if (lct.getCliente().getIdCliente() == cliente.intValue()) {
                iterator.remove();
            }
        }

    }

    public List<CasinoBoolean> getCasinoBooleans() {
        return casinoBooleans;
    }

    public void setCasinoBooleans(List<CasinoBoolean> casinoBooleans) {
        this.casinoBooleans = casinoBooleans;
    }

    public boolean isTodoscasinos() {
        return todoscasinos;
    }

    public void setTodoscasinos(boolean todoscasinos) {
        this.todoscasinos = todoscasinos;
    }

    public List<Usuario> getUsuarioses() {
        return usuarioses;
    }

    public void setUsuarioses(List<Usuario> usuarioses) {
        this.usuarioses = usuarioses;
    }

    public List<ClienteDTO> getClientesSelected() {
        return clientesSelected;
    }

    public void setClientesSelected(List<ClienteDTO> clientesSelected) {
        this.clientesSelected = clientesSelected;
    }

    public List<ClienteDTO> getClientes() {
        return clientes;
    }

    public void setClientes(List<ClienteDTO> clientes) {
        this.clientes = clientes;
    }

    public void handleFileUpload(FileUploadEvent event) {
        if (event != null) {
            file = event.getFile();
            sessionBean.marketingUserFacade.guardarImagen(file.getContents(), file.getFileName());
            sessionBean.setAttribute("imagen", file.getContents());
            filename = file.getFileName();

            System.out.println("File name: " + file.getFileName());
        }
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void enviarAPagina() {
        elemento.setTipo(sessionBean.marketingUserFacade.findTipoTarea(elemento.getTipo().getIdTipotarea()));
        try {
            sessionBean.setAttribute("tipo", elemento.getTipo().getIdTipotarea());
            if (elemento.getTipo().getNombre().equals("EMAIL")) {

                FacesContext.getCurrentInstance().getExternalContext().redirect("TareaAccionEmail.xhtml");

            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect("TareaAccionOtros.xhtml");
            }
        } catch (IOException ex) {
            Logger.getLogger(TareaAccionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
