/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Atributo;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Categoria;
import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Clienteatributo;
import com.invbf.sistemagestionmercadeo.entity.ClienteatributoPK;
import com.invbf.sistemagestionmercadeo.entity.Permiso;
import com.invbf.sistemagestionmercadeo.entity.Tipodocumento;
import com.invbf.sistemagestionmercadeo.entity.Tipojuego;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import com.invbf.sistemagestionmercadeo.util.Mensajes;
import com.invbf.sistemagestionmercadeo.util.Notificador;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
public class ClientesActBean implements Serializable {

    private List<Tipojuego> tiposjuegos;
    private List<Atributo> atributos;
    private Cliente elemento;
    private Cliente viejo;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private DualListModel<Tipojuego> tiposJuegosTodos;
    private List<Casino> listacasinos;
    private List<Categoria> listacategorias;
    private List<Tipodocumento> tipoDocumentos;
    private String observaciones;
    private String observacionesTarea;

    private List<Integer> annos;

    private Integer dia;
    private Integer mes;
    private int anio;
    private Integer diav;
    private Integer mesv;
    private int aniov;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public ClientesActBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("clientes");
        if (!sessionBean.perfilViewMatch("Clientes")) {
            try {
                System.out.println("No lo coje");
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }

        if (sessionBean.getAttributes("idCliente") == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("AdministradorAtributosMarketing.xhtml");
            } catch (IOException ex) {
            }
        }
        if ((Integer) sessionBean.getAttributes("idCliente") != null && (Integer) sessionBean.getAttributes("idCliente") != 0) {
            elemento = sessionBean.marketingUserFacade.findCliente((Integer) sessionBean.getAttributes("idCliente"));
            viejo = sessionBean.marketingUserFacade.findCliente((Integer) sessionBean.getAttributes("idCliente"));
            if (elemento.getIdTipoDocumento() == null) {
                elemento.setIdTipoDocumento(new Tipodocumento(0));
            }
            tiposjuegos = sessionBean.marketingUserFacade.findAllTiposjuegos();
            for (Tipojuego tj : elemento.getTipojuegoList()) {
                if (tiposjuegos.contains(tj)) {
                    tiposjuegos.remove(tj);
                }
            }
            tiposJuegosTodos = new DualListModel<Tipojuego>(tiposjuegos, elemento.getTipojuegoList());
        } else {
            elemento = new Cliente();
            elemento.setIdCliente(0);
            elemento.setIdTipoDocumento(new Tipodocumento(0));
            elemento.setIdCategorias(new Categoria(0));
            elemento.setIdCasinoPreferencial(new Casino(0));
            elemento.setTipojuegoList(new ArrayList<Tipojuego>());
            elemento.setClienteatributoList(new ArrayList<Clienteatributo>());
            tiposjuegos = sessionBean.marketingUserFacade.findAllTiposjuegos();
            tiposJuegosTodos = new DualListModel<Tipojuego>(tiposjuegos, elemento.getTipojuegoList());
        }
        atributos = sessionBean.marketingUserFacade.findAllAtributos();
        for (Atributo a : atributos) {
            Clienteatributo clientesatributos = new Clienteatributo(elemento.getIdCliente(), a.getIdAtributo());
            if (!elemento.getClienteatributoList().contains(clientesatributos)) {
                clientesatributos.setClienteatributoPK(new ClienteatributoPK(elemento.getIdCliente(), a.getIdAtributo()));
                clientesatributos.setAtributo(a);
                clientesatributos.setCliente(elemento);
                elemento.getClienteatributoList().add(clientesatributos);
            }
        }
        listacasinos = sessionBean.marketingUserFacade.findAllCasinos();
        listacategorias = sessionBean.marketingUserFacade.findAllCategorias();
        tipoDocumentos = sessionBean.marketingUserFacade.findAllTipoDocumentos();
        Calendar calendar = Calendar.getInstance();
        int actual = calendar.get(Calendar.YEAR);
        annos = new ArrayList<Integer>();
        for (int i = 1900; i <= actual; i++) {
            annos.add(i);
        }
        if (elemento.getCumpleanos() != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(elemento.getCumpleanos());
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            anio = c.get(Calendar.YEAR);
            diav = c.get(Calendar.DAY_OF_MONTH);
            mesv = c.get(Calendar.MONTH);
            aniov = c.get(Calendar.YEAR);
        } else {
            mes = 12;
        }
        observacionesTarea = "";
        if (elemento.getPorActualizar() != null && elemento.getPorActualizar() == 1) {
            observacionesTarea = elemento.getObservacionesAct();
        }
    }

    public Cliente getElemento() {
        return elemento;
    }

    public void setElemento(Cliente elemento) {
        this.elemento = elemento;
    }

    public List<Atributo> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<Atributo> atributos) {
        this.atributos = atributos;
    }

    public DualListModel<Tipojuego> getTiposJuegosTodos() {
        return tiposJuegosTodos;
    }

    public void setTiposJuegosTodos(DualListModel<Tipojuego> tiposJuegosTodos) {
        this.tiposJuegosTodos = tiposJuegosTodos;
    }

    public void guardar() {
        guardar:
        {
            try {
                System.out.println(elemento.getIdCliente());

                if (elemento.getIdentificacion() == null || elemento.getIdentificacion().equals("")) {
                    elemento.setIdTipoDocumento(null);
                }
                if ((elemento.getIdTipoDocumento() == null)
                        && (elemento.getIdentificacion() != null && !elemento.getIdentificacion().equals(""))) {
                    FacesUtil.addErrorMessage("No se puede guardar cliente", "Si tiene identificación debe seleccionar un tipo");
                    break guardar;
                }
                if (!elemento.getIdentificacion().equals("")) {
                    if ((sessionBean.marketingUserFacade.existeid(elemento.getIdTipoDocumento(), elemento.getIdentificacion())) && (elemento.getIdCliente() == 0)) {
                        FacesUtil.addErrorMessage("No se puede guardar cliente", "Existe cliente con la misma identificación");
                        break guardar;
                    }
                }

                elemento.setTipojuegoList(tiposJuegosTodos.getTarget());
                if (mes != 12 && dia != 0) {
                    Calendar cumple = Calendar.getInstance();
                    cumple.set(Calendar.MONTH, mes);
                    cumple.set(Calendar.DAY_OF_MONTH, dia);
                    cumple.set(Calendar.YEAR, anio);
                    elemento.setCumpleanos(cumple.getTime());
                }

                if (elemento.getIdCliente() == null || elemento.getIdCliente() == 0) {

                    List<Clienteatributo> clienteatributos = elemento.getClienteatributoList();
                    elemento.setClienteatributoList(new ArrayList<Clienteatributo>());
                    sessionBean.marketingUserFacade.guardarClientes(elemento);
                    FacesUtil.addInfoMessage("Cliente creado con exito!", "");
                    sessionBean.registrarlog("actualizar", "Clientes", "Cliente creado: " + elemento.toString());
                    sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Cliente creado con exito", ""));
                } else {
                    if (sessionBean.perfilViewMatch("notificaciones")) {
                        sessionBean.marketingUserFacade.guardarClientes(elemento);
                        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Cliente actualizado con exito", ""));
                    } else {
                        String body = "<div><Label>El usuario "+sessionBean.getUsuario().getNombre()+" solicit&oacute; un cambio en un perfil de un cliente.</label><br /></div> ";

                        body += "<div><Label>Cliente:</label><br /></div>";
                        body += "<div><Label>Nombres: </label> " + elemento.getNombres() + "</div>";
                        body += "<div><Label>Apellidos: </label>  " + elemento.getApellidos() + "</div>";
                        body += "<div><Label>Sala: </label>  " + elemento.getIdCasinoPreferencial().getNombre()+ "</div>";

                        body += "<div><Label>Cambios:</label><br /></div>";
                        body += "<div><br /><table border=\"1\" style=\"width:100%\"><tr>"
                                + "<th>Campo</th>"
                                + "<th>Valor anterior</th>"
                                + "<th>Valor actualizado</th>"
                                + "</tr>";
                        boolean hubocambioImportante = false;
                        if (elemento.getIdCategorias() != null && !elemento.getIdCategorias().equals(viejo.getIdCategorias())) {
                            elemento.setIdCategorias(sessionBean.marketingUserFacade.findCategoria(elemento.getIdCategorias().getIdCategorias()));
                            sessionBean.managerUserFacade.addPermiso(new Permiso("EDITAR", elemento.getIdCliente().toString(), "CLIENTE", "CATEGORIA", elemento.getIdCategorias().getIdCategorias().toString(), elemento.getIdCategorias().getNombre(), viejo.getIdCategorias() == null ? "" : viejo.getIdCategorias().getIdCategorias().toString(), viejo.getIdCategorias() == null ? "" : viejo.getIdCategorias().getNombre(), observaciones, sessionBean.getUsuario()));
                            body += "<tr>";
                            body += "<td align=\"center\">Categor&iacute;a</td>";
                            body += "<td align=\"center\">" + viejo.getIdCategorias().getNombre() + "</td>";
                            body += "<td align=\"center\">" + elemento.getIdCategorias().getNombre() + "</td></tr>";
                            elemento.setIdCategorias(viejo.getIdCategorias());
                            hubocambioImportante = true;
                        }
                        if (!elemento.getTelefono1().equals(viejo.getTelefono1())) {
                            sessionBean.managerUserFacade.addPermiso(new Permiso("EDITAR", elemento.getIdCliente().toString(), "CLIENTE", "TELEFONO 1", elemento.getTelefono1(), elemento.getTelefono1(), viejo.getTelefono1(), viejo.getTelefono1(), observaciones, sessionBean.getUsuario()));
                            body += "<tr>";
                            body += "<td align=\"center\">Tel&eacute;fono 1</td>";
                            body += "<td align=\"center\">" + viejo.getTelefono1() + "</td>";
                            body += "<td align=\"center\">" + elemento.getTelefono1() + "</td></tr>";
                            elemento.setTelefono1(viejo.getTelefono1());
                            hubocambioImportante = true;
                        }
                        if (!elemento.getTelefono2().equals(viejo.getTelefono2())) {
                            sessionBean.managerUserFacade.addPermiso(new Permiso("EDITAR", elemento.getIdCliente().toString(), "CLIENTE", "TELEFONO 2", elemento.getTelefono2(), elemento.getTelefono2(), viejo.getTelefono2(), viejo.getTelefono2(), observaciones, sessionBean.getUsuario()));
                            body += "<tr>";
                            body += "<td align=\"center\">Tel&eacute;fono 2</td>";
                            body += "<td align=\"center\">" + viejo.getTelefono2() + "</td>";
                            body += "<td align=\"center\">" + elemento.getTelefono2() + "</td></tr>";
                            elemento.setTelefono2(viejo.getTelefono2());
                            hubocambioImportante = true;
                        }
                        if (!elemento.getCorreo().equals(viejo.getCorreo())) {
                            sessionBean.managerUserFacade.addPermiso(new Permiso("EDITAR", elemento.getIdCliente().toString(), "CLIENTE", "CORREO", elemento.getCorreo(), elemento.getCorreo(), viejo.getCorreo(), viejo.getCorreo(), observaciones, sessionBean.getUsuario()));
                            body += "<tr>";
                            body += "<td align=\"center\">Correo</td>";
                            body += "<td align=\"center\">" + viejo.getCorreo() + "</td>";
                            body += "<td align=\"center\">" + elemento.getCorreo() + "</td></tr>";
                            elemento.setCorreo(viejo.getCorreo());
                            hubocambioImportante = true;
                        }

                        if (!elemento.getBonoFidelizacion().equals(viejo.getBonoFidelizacion())) {
                            sessionBean.managerUserFacade.addPermiso(new Permiso("EDITAR", elemento.getIdCliente().toString(), "CLIENTE", "CUPO DE FIDELIZACION", elemento.getBonoFidelizacion(), elemento.getBonoFidelizacion(), viejo.getBonoFidelizacion(), viejo.getBonoFidelizacion(), observaciones, sessionBean.getUsuario()));
                            body += "<tr>";
                            body += "<td align=\"center\">Cupo de fidelizaci&oacute;n</td>";
                            body += "<td align=\"center\">" + viejo.getBonoFidelizacion() + "</td>";
                            body += "<td align=\"center\">" + elemento.getBonoFidelizacion() + "</td></tr>";
                            elemento.setBonoFidelizacion(viejo.getBonoFidelizacion());
                            hubocambioImportante = true;
                        }
                        body += "</table><br /></div>";

                        body += "<div><span style=\"font-weight:bold\">Justificaci&oacute;n del cambio:</span><br /></div>";
                        body += "<div><Label>"+observaciones+".</label><br /></div>";
                        body += "<div><br /><Label>Favor revisar la lista de cambios en clientes.</label><br /></div>";
                        elemento.setPorActualizar(0);
                        sessionBean.marketingUserFacade.guardarClientes(elemento);
                        if (hubocambioImportante) {
                            Notificador.notificar(Notificador.SOLICITUD_CAMBIO_CLIENTE,
                                    body,
                                    "Cambio en cliente", sessionBean.getUsuario().getUsuariodetalle().getCorreo());
                            sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Actualización enviada", "Pendiente por aprobación"));
                        } else {
                            sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "Cliente actualizado con exito", ""));
                        }
                    }
                }
                FacesContext.getCurrentInstance().getExternalContext().redirect("Reporteclientes.xhtml");

            } catch (IOException ex) {
            }
        }
    }

    public List<Casino> getListacasinos() {
        return listacasinos;
    }

    public void setListacasinos(List<Casino> listacasinos) {
        this.listacasinos = listacasinos;
    }

    public List<Categoria> getListacategorias() {
        return listacategorias;
    }

    public void setListacategorias(List<Categoria> listacategorias) {
        this.listacategorias = listacategorias;
    }

    public List<Tipodocumento> getTipoDocumentos() {
        return tipoDocumentos;
    }

    public void setTipoDocumentos(List<Tipodocumento> tipoDocumentos) {
        this.tipoDocumentos = tipoDocumentos;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<Tipojuego> getTiposjuegos() {
        return tiposjuegos;
    }

    public void setTiposjuegos(List<Tipojuego> tiposjuegos) {
        this.tiposjuegos = tiposjuegos;
    }

    public Cliente getViejo() {
        return viejo;
    }

    public void setViejo(Cliente viejo) {
        this.viejo = viejo;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public List<Integer> getAnnos() {
        return annos;
    }

    public void setAnnos(List<Integer> annos) {
        this.annos = annos;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getObservacionesTarea() {
        return observacionesTarea;
    }

    public void setObservacionesTarea(String observacionesTarea) {
        this.observacionesTarea = observacionesTarea;
    }

}
