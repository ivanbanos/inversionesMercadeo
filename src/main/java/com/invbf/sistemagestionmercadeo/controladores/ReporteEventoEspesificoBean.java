/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Evento;
import com.invbf.sistemagestionmercadeo.entity.Tarea;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DualListModel;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class ReporteEventoEspesificoBean implements Serializable{
    private Evento elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<Cliente> clienteses;
    private List<Cliente> clientesesEvento;
    private List<Usuario> usuarioses;
    private DualListModel<Cliente> todosclienteses;
    private DualListModel<Usuario> todosusuarioses;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    private List<Evento> flista;

    public List<Evento> getFlista() {
        return flista;
    }

    public void setFlista(List<Evento> flista) {
        this.flista = flista;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public ReporteEventoEspesificoBean() {
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

        if (sessionBean.getAttributes("idEvento")==null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("eventos.xhtml");
            } catch (IOException ex) {
            }
        }
        elemento = sessionBean.marketingUserFacade.findEvento((Integer) sessionBean.getAttributes("idEvento"));
        sessionBean.checkEstadoTarea();
        clienteses = sessionBean.marketingUserFacade.findAllClientes();
        usuarioses = sessionBean.adminFacade.findAllUsuariosHostess();
        clientesesEvento = new ArrayList<Cliente>();
        todosclienteses = new DualListModel<Cliente>(clienteses, clientesesEvento);
    }

    public Evento getElemento() {
        return elemento;
    }

    public void setElemento(Evento elemento) {
        this.elemento = elemento;
    }

    public DualListModel<Cliente> getTodosclienteses() {
        return todosclienteses;
    }

    public void setTodosclienteses(DualListModel<Cliente> todosclienteses) {
        this.todosclienteses = todosclienteses;
    }

    public DualListModel<Usuario> getTodosusuarioses() {
        return todosusuarioses;
    }

    public void setTodosusuarioses(DualListModel<Usuario> todosusuarioses) {
        this.todosusuarioses = todosusuarioses;
    }

    public void goTareaReporte(int id) {
        try {
            sessionBean.setAttribute("idTarea", new Integer(id));
            FacesContext.getCurrentInstance().getExternalContext().redirect("ReporteTareaEspesifica.xhtml");
        } catch (IOException ex) {
        }
    }
}
