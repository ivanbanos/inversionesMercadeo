/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Cargo;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Perfil;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.entity.Usuariodetalle;
import com.invbf.sistemagestionmercadeo.exceptions.NombreUsuarioExistenteException;
import com.invbf.sistemagestionmercadeo.facade.impl.AdminFacadeImpl;
import com.invbf.sistemagestionmercadeo.observer.Observer;
import com.invbf.sistemagestionmercadeo.util.CasinoBoolean;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import com.invbf.sistemagestionmercadeo.util.UsuarioDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class CrudUsuariosBean implements Observer, Serializable {

    private List<UsuarioDTO> lista;
    private UsuarioDTO elemento;
    private List<Perfil> listaperfiles;
    private List<Cargo> cargos;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private String contrasena;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    private List<Usuario> flista;

    public List<Usuario> getFlista() {
        return flista;
    }

    public void setFlista(List<Usuario> flista) {
        this.flista = flista;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudUsuariosBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.adminFacade = new AdminFacadeImpl();
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");

        setNuevoUsuario();
        List<Usuario> usuarios = sessionBean.adminFacade.findAllUsuarios();
        lista = new ArrayList<UsuarioDTO>();
        List<Casino> casinosn = sessionBean.marketingUserFacade.findAllCasinos();
        for (Usuario usuario : usuarios) {
            lista.add(new UsuarioDTO(usuario, casinosn));
        }
        listaperfiles = sessionBean.adminFacade.findAllPerfiles();
        cargos = sessionBean.adminFacade.findAllCargos();
        if (elemento.getCasinos() == null) {
            elemento.setCasinos(new ArrayList<CasinoBoolean>());
        }
        elemento.setUsuariodetalle(new Usuariodetalle());
        elemento.getUsuariodetalle().setIdcargo(new Cargo());
        sessionBean.registerObserver(this);
    }

    @PreDestroy
    public void preDestroy() {
        sessionBean.removeObserver(this);
    }

    public List<UsuarioDTO> getLista() {
        return lista;
    }

    public void setLista(List<UsuarioDTO> lista) {
        this.lista = lista;
    }

    public UsuarioDTO getElemento() {
        return elemento;
    }

    public void setElemento(UsuarioDTO elemento) {
        this.elemento = elemento;
    }

    public List<Perfil> getListaperfiles() {
        return listaperfiles;
    }

    public void setListaperfiles(List<Perfil> listaperfiles) {
        this.listaperfiles = listaperfiles;
    }

    public void delete() {
        sessionBean.adminFacade.deleteUsuarios(elemento.getUsuarioWithId());
        List<Usuario> usuarios = sessionBean.adminFacade.findAllUsuarios();
        lista = new ArrayList<UsuarioDTO>();
        List<Casino> casinosn = sessionBean.marketingUserFacade.findAllCasinos();
        for (Usuario usuario : usuarios) {
            lista.add(new UsuarioDTO(usuario, casinosn));
        }
        FacesUtil.addInfoMessage("Usuario eliminado", elemento.getNombreUsuario());

        setNuevoUsuario();

    }

    public void guardar() {

        System.out.println("primero compara");
        if (elemento.getContrasena() == null || contrasena.equals(elemento.getContrasena())) {
            try {
                System.out.println("empezando");
                elemento = sessionBean.adminFacade.guardarUsuarios(elemento);

                System.out.println("empiezo a llenarla");

                System.out.println("llamo a los usuarios");
                List<Usuario> usuarios = sessionBean.adminFacade.findAllUsuarios();
                lista = new ArrayList<UsuarioDTO>();
                List<Casino> casinosn = sessionBean.marketingUserFacade.findAllCasinos();
                for (Usuario usuario : usuarios) {
                    lista.add(new UsuarioDTO(usuario, casinosn));
                }
                FacesUtil.addInfoMessage("Usuario guardado", elemento.getNombreUsuario());

                setNuevoUsuario();
            } catch (NombreUsuarioExistenteException ex) {
                FacesUtil.addErrorMessage("Usuario no creado", "Nombre de usuario existente");
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else {
            FacesUtil.addErrorMessage("Usuario no creado", "Las contraseñas deben coincidir");
        }
    }

    @Override
    public void update() {
        listaperfiles = sessionBean.adminFacade.findAllPerfiles();
    }

    private void setNuevoUsuario() {
        List<Casino> casinosn = sessionBean.marketingUserFacade.findAllCasinos();
        elemento = new UsuarioDTO(casinosn);
        elemento.setIdPerfil(new Perfil());
        elemento.setUsuariodetalle(new Usuariodetalle());
        elemento.getUsuariodetalle().setIdcargo(new Cargo());
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }

}
