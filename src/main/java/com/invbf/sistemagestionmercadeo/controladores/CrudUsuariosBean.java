/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Acceso;
import com.invbf.sistemagestionmercadeo.entity.Cargo;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Perfil;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.entity.Usuariodetalle;
import com.invbf.sistemagestionmercadeo.exceptions.NombreUsuarioExistenteException;
import com.invbf.sistemagestionmercadeo.facade.impl.AdminFacadeImpl;
import com.invbf.sistemagestionmercadeo.observer.Observer;
import com.invbf.sistemagestionmercadeo.util.AccesoBoolean;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
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
public class CrudUsuariosBean implements Observer {

    private List<Usuario> lista;
    private Usuario elemento;
    private Usuariodetalle detalleElemento;
    private List<Perfil> listaperfiles;
    private List<Cargo> cargos;
    private List<Casino> casinos;
    private List<AccesoBoolean> accesos;
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
        lista = sessionBean.adminFacade.findAllUsuarios();
        listaperfiles = sessionBean.adminFacade.findAllPerfiles();
        cargos = sessionBean.adminFacade.findAllCargos();
        casinos = sessionBean.marketingUserFacade.findAllCasinos();
        List<Acceso> listaAccesos = sessionBean.adminFacade.findAllAccesos();
        accesos = new ArrayList<AccesoBoolean>(2);
        for (Acceso a : listaAccesos) {

            accesos.add(new AccesoBoolean(a, false));
        }
        sessionBean.registerObserver(this);
    }

    @PreDestroy
    public void preDestroy() {
        sessionBean.removeObserver(this);
    }

    public List<Usuario> getLista() {
        return lista;
    }

    public void setLista(List<Usuario> lista) {
        this.lista = lista;
    }

    public Usuario getElemento() {
        return elemento;
    }

    public void setElemento(Usuario elemento) {

        this.elemento = elemento;
        if(this.elemento.getIdCasino()==null){
            this.elemento.setIdCasino(new Casino());
        }
        if (elemento.getIdUsuario() != null) {
            System.out.println("si entra");
            detalleElemento = sessionBean.adminFacade.getDetalleUsuariosById(elemento.getIdUsuario());
            if (detalleElemento == null) {
                detalleElemento = new Usuariodetalle(elemento.getIdUsuario());
                detalleElemento = sessionBean.adminFacade.guardarDetalleUsuarios(detalleElemento);
            }
            if (detalleElemento.getIdcargo() == null) {
                detalleElemento.setIdcargo(new Cargo());
            }
            if (detalleElemento.getAccesoList() == null) {
                detalleElemento.setAccesoList(new ArrayList<Acceso>());
            }
            for (AccesoBoolean a : accesos) {
                if (detalleElemento.getAccesoList().contains(a.getAcceso())) {
                    a.setSelected(true);
                }
            }

            System.out.println("y no pasa nada");
        }
    }

    public List<Perfil> getListaperfiles() {
        return listaperfiles;
    }

    public void setListaperfiles(List<Perfil> listaperfiles) {
        this.listaperfiles = listaperfiles;
    }

    public void delete() {
        sessionBean.adminFacade.deleteDetalleUsuarios(detalleElemento);
        sessionBean.adminFacade.deleteUsuarios(elemento);
        lista = sessionBean.adminFacade.findAllUsuarios();
        FacesUtil.addInfoMessage("Usuario eliminado", elemento.getNombreUsuario());

        setNuevoUsuario();

    }

    public void guardar() {

        System.out.println("primero compara");
        if (elemento.getContrasena() == null || contrasena.equals(elemento.getContrasena())) {
            try {
                System.out.println("empezando");
                elemento = sessionBean.adminFacade.guardarUsuarios(elemento);

                System.out.println("ahora el detalle");
                detalleElemento.setIdUsuario(elemento.getIdUsuario());

                System.out.println("limmpio la lsiat de accesos");
                detalleElemento.getAccesoList().clear();

                System.out.println("empiezo a llenarla");
                for (AccesoBoolean a : accesos) {
                    if (a.getSelected()) {
                        detalleElemento.getAccesoList().add(a.getAcceso());
                    }
                }

                System.out.println("guardo el detallo");
                detalleElemento = sessionBean.adminFacade.guardarDetalleUsuarios(detalleElemento);

                System.out.println("llamo a los usuarios");
                lista = sessionBean.adminFacade.findAllUsuarios();
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
        elemento = new Usuario();
        elemento.setIdPerfil(new Perfil());
        elemento.setIdCasino(new Casino());
        detalleElemento = new Usuariodetalle();
        detalleElemento.setIdcargo(new Cargo());
        detalleElemento.setAccesoList(new ArrayList<Acceso>());
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Usuariodetalle getDetalleElemento() {
        return detalleElemento;
    }

    public void setDetalleElemento(Usuariodetalle detalleElemento) {
        this.detalleElemento = detalleElemento;
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }

    public List<AccesoBoolean> getAccesos() {
        return accesos;
    }

    public void setAccesos(List<AccesoBoolean> accesos) {
        this.accesos = accesos;
    }

    public List<Casino> getCasinos() {
        return casinos;
    }

    public void setCasinos(List<Casino> casinos) {
        this.casinos = casinos;
    }

}
