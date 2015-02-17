/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.dao.ConfiguracionDao;
import com.invbf.sistemagestionmercadeo.entity.Configuracion;
import com.invbf.sistemagestionmercadeo.entity.Evento;
import com.invbf.sistemagestionmercadeo.entity.Formulario;
import com.invbf.sistemagestionmercadeo.entity.Tarea;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.entity.Vista;
import com.invbf.sistemagestionmercadeo.exceptions.ClavesNoConcuerdanException;
import com.invbf.sistemagestionmercadeo.exceptions.UsuarioInactivoException;
import com.invbf.sistemagestionmercadeo.exceptions.UsuarioNoConectadoException;
import com.invbf.sistemagestionmercadeo.exceptions.UsuarioNoExisteException;
import com.invbf.sistemagestionmercadeo.exceptions.UsuarioSinAccesoalSistemaException;
import com.invbf.sistemagestionmercadeo.facade.AdminFacade;
import com.invbf.sistemagestionmercadeo.facade.HostessFacade;
import com.invbf.sistemagestionmercadeo.facade.ManagerUserFacade;
import com.invbf.sistemagestionmercadeo.facade.MarketingUserFacade;
import com.invbf.sistemagestionmercadeo.facade.SystemFacade;
import com.invbf.sistemagestionmercadeo.facade.impl.AdminFacadeImpl;
import com.invbf.sistemagestionmercadeo.facade.impl.HostessFacadeImpl;
import com.invbf.sistemagestionmercadeo.facade.impl.ManagerUserFacadeImpl;
import com.invbf.sistemagestionmercadeo.facade.impl.MarketingUserFacadeImpl;
import com.invbf.sistemagestionmercadeo.facade.impl.SystemFacadeImpl;
import com.invbf.sistemagestionmercadeo.observer.Observer;
import com.invbf.sistemagestionmercadeo.observer.Subject;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
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
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author ideacentre
 */
@ManagedBean(name = "sessionBean")
@SessionScoped
public class SessionBean implements Serializable, Subject {

    SystemFacade sessionFacade;
    AdminFacade adminFacade;
    MarketingUserFacade marketingUserFacade;
    HostessFacade hostessFacade;
    ManagerUserFacade managerUserFacade;
    private Usuario usuario;//Almacena el objeto usuario de la session
    private HashMap<String, Object> Attributes;
    private List<Observer> observers;
    private int paginacion;
    private String active;
    private String ruta;
    @ManagedProperty("#{applicationContainer}")
    private ApplicationContainer container;

    public void setContainer(ApplicationContainer container) {
        this.container = container;
    }

    /**
     * Creates a new instance of SessionFlowumiUtil
     */
    public SessionBean() {
    }

    @PostConstruct
    public void init() {
        sessionFacade = new SystemFacadeImpl();
        adminFacade = new AdminFacadeImpl();
        marketingUserFacade = new MarketingUserFacadeImpl();
        hostessFacade = new HostessFacadeImpl();
        managerUserFacade = new ManagerUserFacadeImpl();
        usuario = new Usuario();
        Attributes = new HashMap<String, Object>();
        observers = new ArrayList<Observer>();
        Configuracion configuracion = sessionFacade.getConfiguracionByName("paginacion");
        if (configuracion == null) {
            paginacion = 10;
        } else {
            paginacion = Integer.parseInt(configuracion.getValor());
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String Conectar() {
        try {
            if (container.isUsuarioConectado(usuario.getNombreUsuario())) {
                FacesUtil.addErrorMessage("No se puede conectar", "Usuario ya inició sesión");
            } else { 
                usuario = sessionFacade.iniciarSession(usuario);

                sessionFacade.registrarlog(null, null, "Inicio de sesion del usuario " + usuario.getNombreUsuario(), usuario);
                active = "inicio";
                ruta = "/Inicio";
                container.conectarUsuario(usuario.getNombreUsuario());
                return "/pages/index.xhtml";
            }
        } catch (ClavesNoConcuerdanException ex) {
            FacesUtil.addErrorMessage("Usuario no conectado", ex.getMessage());
            usuario = new Usuario();
        } catch (UsuarioNoExisteException ex) {
            FacesUtil.addErrorMessage("Usuario no conectado", ex.getMessage());
            usuario = new Usuario();
        } catch (UsuarioNoConectadoException ex) {
            FacesUtil.addErrorMessage("Usuario no conectado", ex.getMessage());
            usuario = new Usuario();
        } catch (UsuarioInactivoException ex) {
            FacesUtil.addErrorMessage("Usuario inactivo", ex.getMessage());
            usuario = new Usuario();
        } catch (UsuarioSinAccesoalSistemaException ex) {
            FacesUtil.addErrorMessage("Usuario sin acceso", ex.getMessage());
            usuario = new Usuario();
        }
        return "";
    }

    @PreDestroy
    public void destruir() {
        System.out.println("Destruyendo el bean");
        container.desconectarUsuario(usuario.getNombreUsuario());
    }

    public String Desconectar() {
        container.desconectarUsuario(usuario.getNombreUsuario());
        usuario = new Usuario();
        return "/pages/InicioSession.xhtml";
    }

    public boolean perfilViewMatch(String vista) {
        if (usuario == null || usuario.getIdPerfil() == null || usuario.getIdPerfil().getVistaList() == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        } else {
            List<Vista> vistasUsuario = usuario.getIdPerfil().getVistaList();
            for (Vista v : vistasUsuario) {
                if (v.getNombreVista().equals(vista)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean perfilFormMatch(String tabla, String accion) {
        if (usuario == null || usuario.getIdPerfil() == null || usuario.getIdPerfil().getFormularioList() == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        } else {
            for (Formulario f : usuario.getIdPerfil().getFormularioList()) {
                if (f.es(tabla + accion)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void actualizarUsuario() {
        usuario = sessionFacade.actualizarUsuario(usuario);
    }

    public HashMap<String, Object> getAttributes() {
        return Attributes;
    }

    public void setAttributes(HashMap<String, Object> Attributes) {
        this.Attributes = Attributes;
    }

    public void goInicio() {
        try {
            if (usuario.getIdPerfil().getNombre().equals("Administrador")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioAdministrador.xhtml");
            } else if (usuario.getIdPerfil().getNombre().equals("Marketing")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioMarketing.xhtml");
            } else if (usuario.getIdPerfil().getNombre().equals("Hostess")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioHostess.xhtml");
            } else if (usuario.getIdPerfil().getNombre().equals("Gerente")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioGerente.xhtml");
            }
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObserver(String tabla) {
        Iterator<Observer> i = observers.iterator();
        while (i.hasNext()) {
            Observer o = i.next();
            if (o == null) {
                i.remove();
            } else {
                if (tabla.equals("Perfiles") && o instanceof CrudUsuariosBean) {
                    o.update();
                }
            }

        }
    }

    public void registrarlog(String accion, String tabla, String mensaje) {
        sessionFacade.registrarlog(accion, tabla, mensaje, usuario);
    }

    public int getPaginacion() {
        return paginacion;
    }

    public void setPaginacion(int paginacion) {
        this.paginacion = paginacion;
    }

    public void checkUsuarioConectado() {
        if (usuario == null
                || usuario.getIdUsuario() == null
                || usuario.getIdUsuario() <= 0) {
            try {
                System.out.println("No lo coje");
                Desconectar();
                FacesUtil.addErrorMessage("Session finalizada", "No existe usuario conectado");
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String isActive(String pestaña) {
        if (active == null) {
            return "navbar-text";
        }
        if (active.equals(pestaña)) {
            return "navbar-text-selected";
        } else {
            return "navbar-text";
        }
    }

    public boolean isNotActive(String pestaña) {
        if (active == null) {
            return true;
        }
        return !active.equals(pestaña);
    }

    public String go(String page) {
        if (page.equals("inicio")) {
            active = "inicio";
            ruta = "/Inicio";
            return "/pages/index.xhtml";
        } else if (page.equals("AtributosSistema")) {
            active = "configuracion";
            ruta = "/Configuración/Sistema";
            return "/pages/AdministradorAtributosSistema.xhtml";
        } else if (page.equals("AtributosMarketing")) {
            active = "configuracion";
            ruta = "/Configuración/Atributos";
            return "/pages/AdministradorAtributosMarketing.xhtml";
        } else if (page.equals("ConfiguracionesGenerales")) {
            active = "configuracion";
            ruta = "/Configuración/General";
            return "/pages/ConfiguracionesGenerales.xhtml";
        } else if (page.equals("clientes")) {
            active = "clientes";
            ruta = "/Clientes/Cleintes";
            return "/pages/clientes.xhtml";
        } else if (page.equals("eventos")) {
            active = "clientes";
            ruta = "/Clientes/Eventos";
            return "/pages/eventos.xhtml";
        } else if (page.equals("eventoshostess")) {
            active = "eventoshostess";
            ruta = "/Cleintes/Manejador de tareas";
            return "/pages/tareasHostess.xhtml";
        } else if (page.equals("reportesclientes")) {
            active = "reportes";
            ruta = "/Reportes/Clientes";
            return "/pages/Reporteclientes.xhtml";
        } else if (page.equals("reportestareas")) {
            active = "reportes";
            ruta = "/Reportes/Tareas";
            return "/pages/ReporteTareas.xhtml";
        } else if (page.equals("reporteseventos")) {
            active = "reportes";
            ruta = "/Reportes/Eventos";
            return "/pages/Reporteeventos.xhtml";
        } else if (page.equals("cuenta")) {
            active = "cuenta";
            ruta = "/Cuenta";
            return "/pages/CuentaUsuarios.xhtml";
        } else if (page.equals("tareas")) {
            active = "clientes";
            ruta = "/Clientes/Tareas";
            return "/pages/tareas.xhtml";
        } else if (page.equals("notificaciones")) {
            active = "requisiciones";
            ruta = "/Requisiciones/Cambios en usuarios";
            return "/pages/notificaciones.xhtml";
        } else if (page.equals("SolicitudCambioCupo")) {
            active = "requisiciones";
            return "/pages/SolicitudCambioCupoFidelizacion.xhtml";
        } else if (page.equals("logs")) {
            active = "configuracion";
            ruta = "/Configuración/Logs";
            return "/pages/logs.xhtml";
        } else if (page.equals("atributosbonos")) {
            active = "configuracion";
            ruta = "/Configuración/Atributos de Bonos";
            return "/pages/AtributosBonos.xhtml";
        } else if (page.equals("GenerarSolicitudLotesBonos")) {
            active = "requisiciones";
            ruta = "/Requisiciones/Entrada de lotes de bonos";
            return "/pages/GeneradorSolicitudLoteBono.xhtml";
        } else if (page.equals("listasolicitudlotes")) {
            active = "requisiciones";
            ruta = "/Requisiciones/Entrada de lotes de bonos";
            return "/pages/ListaSolicitudLotesBonosView.xhtml";
        } else if (page.equals("LoteBono")) {
            active = "inventario";
            ruta = "/Inventario/Stock de Bonos";
            return "/pages/AdminLotesBonos.xhtml";
        } else if (page.equals("solicitudesbonos")) {
            active = "requisiciones";
            ruta = "/Requisiciones/Solicitud de bonos";
            return "/pages/ListaSolicitudBono.xhtml";
        } else if (page.equals("ControlSalidaBono")) {
            active = "requisiciones";
            ruta = "/Requisiciones/Salida de Bonos de Caja";
            return "/pages/ListaSolicitudSalidaBonos.xhtml";
        } else if (page.equals("Verbonosporvalidar")) {
            active = "requisiciones";
            return "/pages/BonosValidarView.xhtml";
        } else if (page.equals("Verbonosporentregarcaja")) {
            active = "requisiciones";
            ruta = "/Requisiciones/Bonos por entregar a caja";
            return "/pages/BonosEntregarCajaView.xhtml";
        } else if (page.equals("Verbonosporrecibir")) {
            active = "requisiciones";
            ruta = "/Requisiciones/Bonos por recibir en caja";
            return "/pages/BonosRecibirView.xhtml";
        } else if (page.equals("Verbonosporentregarcliente")) {
            active = "inventario";
            ruta = "/Inventario/Bonos por entregar a clientes";
            return "/pages/BonosEntregarClienteView.xhtml";
        } else if (page.equals("Verbonosporcanjear")) {
            active = "inventario";
            ruta = "/Inventario/Bonos por canjear";
            return "/pages/BonosCanjearView.xhtml";
        } else if (page.equals("reportesbonos")) {
            active = "reportes";
            ruta = "/Reportes/Movimiento de bonos";
            return "/pages/ReporteMovimientoBono.xhtml";
        }
        return "/pages/InicioSession.xhtml";
    }

    public void checkEstadoTarea(Tarea tarea) {
        try {
            Calendar fechainicio = Calendar.getInstance();
            Calendar fechafinal = Calendar.getInstance();

            DateFormat df = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
            DateFormat df2 = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
            TimeZone timeZone = TimeZone.getTimeZone("GMT-5");
            df.setTimeZone(timeZone);
            Calendar nowDate = Calendar.getInstance();
            nowDate.setTime(df2.parse(df.format(nowDate.getTime())));
            fechainicio.setTime(tarea.getFechaInicio());
            fechafinal.setTime(tarea.getFechaFinalizacion());
            tarea.setEstado("POR INICIAR");
            if (fechainicio.before(nowDate)) {
                tarea.setEstado("ACTIVO");
            }
            if (fechafinal.before(nowDate)) {
                tarea.setEstado("VENCIDO");
            }
        } catch (ParseException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void obtenerUsuario(Integer idUsuario) {
        usuario = sessionFacade.getUsuario(idUsuario);
    }

    public SystemFacade getSessionFacade() {
        return sessionFacade;
    }

    public void setSessionFacade(SystemFacade sessionFacade) {
        this.sessionFacade = sessionFacade;
    }

    public AdminFacade getAdminFacade() {
        return adminFacade;
    }

    public void setAdminFacade(AdminFacade adminFacade) {
        this.adminFacade = adminFacade;
    }

    public ManagerUserFacade getManagerUserFacade() {
        return managerUserFacade;
    }

    public void setManagerUserFacade(ManagerUserFacade managerUserFacade) {
        this.managerUserFacade = managerUserFacade;
    }

    public MarketingUserFacade getMarketingUserFacade() {
        return marketingUserFacade;
    }

    public void setMarketingUserFacade(MarketingUserFacade marketingUserFacade) {
        this.marketingUserFacade = marketingUserFacade;
    }

    public HostessFacade getHostessFacade() {
        return hostessFacade;
    }

    public void setHostessFacade(HostessFacade hostessFacade) {
        this.hostessFacade = hostessFacade;
    }

    byte[] getImage(Integer integer) {
        FTPClient client = new FTPClient();
        byte[] bytesArray = null;
        Evento ev = marketingUserFacade.findEvento(integer);
        if (ev != null && ev.getImagen() != null && !ev.getImagen().equals("")) {
            String remoteFile2 = ev.getImagen();
            try {
                String sFTP = ConfiguracionDao.findByNombre("FTP").getValor();
                String sUser = ConfiguracionDao.findByNombre("FTPuser").getValor();
                String sPassword = ConfiguracionDao.findByNombre("FTPpassword").getValor();

                client.connect(sFTP);
                boolean login = client.login(sUser, sPassword);

                int reply = client.getReplyCode();

                System.out.println("Respuesta recibida de conexión FTP:" + reply);

                if (FTPReply.isPositiveCompletion(reply)) {
                    System.out.println("Conectado Satisfactoriamente");
                } else {
                    System.out.println("Imposible conectarse al servidor");
                }
                client.changeWorkingDirectory("/home/easl4284/public_html/imagenes");
                client.setFileType(FTP.BINARY_FILE_TYPE);

                InputStream inputStream = client.retrieveFileStream(remoteFile2);
                bytesArray = IOUtils.toByteArray(inputStream);

                boolean success = client.completePendingCommand();
                if (success) {
                    System.out.println("File has been downloaded successfully.");
                }
                inputStream.close();

            } catch (FileNotFoundException ex) {
                System.out.println(ex);
            } catch (IOException ex) {

                System.out.println(ex);
            } finally {
                try {
                    client.logout();
                    client.disconnect();
                } catch (IOException ex) {

                    System.out.println(ex);
                }
            }

        }
        return bytesArray;
    }

    public List<Observer> getObservers() {
        return observers;
    }

    public void setObservers(List<Observer> observers) {
        this.observers = observers;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public void revisarEstadoBonos() {
        adminFacade.revisarBonos();
    }

}
