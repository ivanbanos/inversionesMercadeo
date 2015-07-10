/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Tarea;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
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
public class TareasHostessBean implements Serializable{

    private List<Tarea> lista;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    private List<Tarea> flista;

    public List<Tarea> getFlista() {
        return flista;
    }

    public void setFlista(List<Tarea> flista) {
        this.flista = flista;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public TareasHostessBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("eventoshostess");
        if (!sessionBean.perfilViewMatch("ManejadorEventosHostess")) {
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        sessionBean.removeAttribute("idTarea");
        sessionBean.removeAttribute("isLeavingTarea");
        lista = sessionBean.marketingUserFacade.findAllTareas();
        sessionBean.checkEstadoTarea();
        Iterator<Tarea> iterator = lista.iterator();
        while (iterator.hasNext()) {
            Tarea e = iterator.next();
            if (!e.getEstado().equals("ACTIVO") || e.getTipo().getNombre().equals("EMAIL") || !e.getUsuarioList().contains(sessionBean.getUsuario())) {
                iterator.remove();
            }
        }

    }

    public List<Tarea> getLista() {
        return lista;
    }

    public void setLista(List<Tarea> lista) {
        this.lista = lista;
    }

    public void goEvento(int id) {
        try {
            sessionBean.setAttribute("idTarea", id);
            Tarea t = sessionBean.marketingUserFacade.findTarea(id);
            if (t.getIdEvento() != null) {
                sessionBean.setAttribute("imagen", sessionBean.getImage(new Integer(id)));
            }
            
        sessionBean.setAttribute("isComingTarea",false);
            FacesContext.getCurrentInstance().getExternalContext().redirect("HostessEventoManejadorView.xhtml");
        } catch (IOException ex) {
        }
    }
}