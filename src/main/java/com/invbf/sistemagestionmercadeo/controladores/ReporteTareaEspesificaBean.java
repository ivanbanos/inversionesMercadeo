package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Accion;
import com.invbf.sistemagestionmercadeo.entity.Listasclientestareas;
import com.invbf.sistemagestionmercadeo.entity.Tarea;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.util.AccionConteo;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
public class ReporteTareaEspesificaBean implements Serializable {

    private Tarea elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<String> acciones;
    private List<AccionConteo> accionesConteo;
    private List<AccionConteo> hostessConteo;
    private long totalClientes;
    private long totalRevisados;
    
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public ReporteTareaEspesificaBean() {
    }
    
    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("reportes");
        if (!sessionBean.perfilViewMatch("Reportes")) {
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        
        if (sessionBean.getAttributes("idTarea") == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("ReporteTareas.xhtml");
            } catch (IOException ex) {
            }
        }
        elemento = sessionBean.marketingUserFacade.findTarea((Integer) sessionBean.getAttributes("idTarea"));
        acciones = new ArrayList<String>();
        for (Accion a : elemento.getTipo().getAccionList()) {
            acciones.add(a.getNombre());
        }
        accionesConteo = new ArrayList<AccionConteo>();
        hostessConteo = new ArrayList<AccionConteo>();
        totalClientes = 0;
        totalRevisados = 0;
        for (Usuario u : elemento.getUsuarioList()) {
            hostessConteo.add(new AccionConteo(u.getNombreUsuario(), 0));
        }
        for (Accion a : elemento.getTipo().getAccionList()) {
            accionesConteo.add(new AccionConteo(a.getNombre(), 0));
        }
        for (Listasclientestareas lct : elemento.getListasclientestareasList()) {
            totalClientes++;
            if (!lct.getIdAccion().getNombre().equals("INICIAL")) {
                totalRevisados++;
                if (accionesConteo.contains(new AccionConteo(lct.getIdAccion().getNombre(), 0))) {
                    AccionConteo ac = accionesConteo.get(accionesConteo.indexOf(new AccionConteo(lct.getIdAccion().getNombre(), 0)));
                    ac.setCantidad(ac.getCantidad() + 1);
                } else {
                    accionesConteo.add(new AccionConteo(lct.getIdAccion().getNombre(), 1));
                }
                
                if (hostessConteo.contains(new AccionConteo(lct.getUsuario().getNombreUsuario(), 0))) {
                    AccionConteo ac = hostessConteo.get(hostessConteo.indexOf(new AccionConteo(lct.getUsuario().getNombreUsuario(), 0)));
                    ac.setCantidad(ac.getCantidad() + 1);
                } else {
                    hostessConteo.add(new AccionConteo(lct.getUsuario().getNombreUsuario(), 1));
                }
                
                if (elemento.getTipo().getNombre().equals("EMAILL")) {
                    if (hostessConteo.contains(new AccionConteo(lct.getUsuario().getNombreUsuario(), 0))) {
                        AccionConteo ac = hostessConteo.get(hostessConteo.indexOf(new AccionConteo(lct.getUsuario().getNombreUsuario(), 0)));
                        ac.setCantidad(ac.getCantidad() + 1);
                    } else {
                        hostessConteo.add(new AccionConteo(lct.getUsuario().getNombreUsuario(), 1));
                    }
                }
            }
        }
    }
    
    public Tarea getElemento() {
        return elemento;
    }
    
    public void setElemento(Tarea elemento) {
        this.elemento = elemento;
    }
    
    public List<String> getAcciones() {
        return acciones;
    }
    
    public void setAcciones(List<String> acciones) {
        this.acciones = acciones;
    }
    
    public List<AccionConteo> getAccionesConteo() {
        return accionesConteo;
    }
    
    public void setAccionesConteo(List<AccionConteo> accionesConteo) {
        this.accionesConteo = accionesConteo;
    }
    
    public List<AccionConteo> getHostessConteo() {
        return hostessConteo;
    }
    
    public void setHostessConteo(List<AccionConteo> hostessConteo) {
        this.hostessConteo = hostessConteo;
    }
    
    public long getTotalClientes() {
        return totalClientes;
    }
    
    public void setTotalClientes(long totalClientes) {
        this.totalClientes = totalClientes;
    }
    
    public long getTotalRevisados() {
        return totalRevisados;
    }
    
    public void setTotalRevisados(long totalRevisados) {
        this.totalRevisados = totalRevisados;
    }
    
    public void goCliente(int id, String observaciones) {
        try {
            sessionBean.setAttribute("idCliente", new Integer(id));
            sessionBean.setAttribute("observaciones", observaciones);
            FacesContext.getCurrentInstance().getExternalContext().redirect("ClientesAct.xhtml");
        } catch (IOException ex) {
        }
    }
}
