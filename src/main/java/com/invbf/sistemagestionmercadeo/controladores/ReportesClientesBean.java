/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Atributo;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Categoria;
import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Tipodocumento;
import com.invbf.sistemagestionmercadeo.entity.Tipojuego;
import com.invbf.sistemagestionmercadeo.util.CasinoBoolean;
import com.invbf.sistemagestionmercadeo.util.CategoriaBoolean;
import com.invbf.sistemagestionmercadeo.util.ClienteDTO;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
import com.invbf.sistemagestionmercadeo.util.Notificador;
import com.invbf.sistemagestionmercadeo.util.TipoJuegoBoolean;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
public class ReportesClientesBean implements Serializable {

    private String email;
    private List<ClienteDTO> lista;
    private ClienteDTO elemento;
    private List<Casino> listacasinos;
    private List<Atributo> listaatributos;
    private List<Categoria> listacategorias;
    private List<Tipojuego> listatiposjuegos;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private boolean editar;
    private String pais;
    private String ciudad;
    private List<CasinoBoolean> casinoBooleans;
    private List<TipoJuegoBoolean> juegoBooleans;
    private List<CategoriaBoolean> categoriaBooleans;
    private boolean todoscasinos;
    private boolean todosCat;
    private boolean todostip;
    private Integer totales;
    private Integer mes;
    private Integer dia;
    private String nombre;
    private String apellidos;
    private String identificacion;
    private Tipodocumento tipodocumento;
    private List<Tipodocumento> listaTipos;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    private List<ClienteDTO> flista;

    public List<ClienteDTO> getFlista() {
        return flista;
    }

    public void setFlista(List<ClienteDTO> flista) {
        this.flista = flista;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public ReportesClientesBean() {
    }

    @PostConstruct
    public void init() {
        pais = "";
        ciudad = "";
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("clientes");
        if (!sessionBean.perfilViewMatch("Clientes")) {
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        elemento = new ClienteDTO();
        totales = sessionBean.marketingUserFacade.getCantClientes();
        listacasinos = sessionBean.marketingUserFacade.findAllCasinos();
        listaatributos = sessionBean.marketingUserFacade.findAllAtributos();
        listacategorias = sessionBean.marketingUserFacade.findAllCategorias();
        listatiposjuegos = sessionBean.marketingUserFacade.findAllTiposjuegos();
        editar = false;

        List<Casino> casinos = sessionBean.getUsuario().getCasinoList();
        List<Tipojuego> tipoJuegos = sessionBean.marketingUserFacade.findAllTiposjuegos();
        List<Categoria> categorias = sessionBean.marketingUserFacade.findAllCategorias();
        juegoBooleans = new ArrayList<TipoJuegoBoolean>();
        casinoBooleans = new ArrayList<CasinoBoolean>();
        for (Casino casinob : casinos) {
            casinoBooleans.add(new CasinoBoolean(casinob, true));
        }
        categoriaBooleans = new ArrayList<CategoriaBoolean>();
        for (Tipojuego tipoJuego : tipoJuegos) {
            juegoBooleans.add(new TipoJuegoBoolean(tipoJuego, false));
        }
        for (Categoria categoria : categorias) {
            categoriaBooleans.add(new CategoriaBoolean(categoria, false));
        }
        listaTipos = sessionBean.marketingUserFacade.findAllTipoDocumentos();
        tipodocumento = new Tipodocumento();
        mes = 12;
        buscarCLientesIniciales();
        sessionBean.printMensajes();
        System.gc();
    }

    public List<ClienteDTO> getLista() {
        return lista;
    }

    public void setLista(List<ClienteDTO> lista) {
        this.lista = lista;
    }

    public ClienteDTO getElemento() {
        return elemento;
    }

    public void setElemento(ClienteDTO elemento) {
        this.elemento = elemento;
    }

    public List<Casino> getListacasinos() {
        return listacasinos;
    }

    public void setListacasinos(List<Casino> listacasinos) {
        this.listacasinos = listacasinos;
    }

    public List<Atributo> getListaatributos() {
        return listaatributos;
    }

    public void setListaatributos(List<Atributo> listaatributos) {
        this.listaatributos = listaatributos;
    }

    public List<Categoria> getListacategorias() {
        return listacategorias;
    }

    public void setListacategorias(List<Categoria> listacategorias) {
        this.listacategorias = listacategorias;
    }

    public List<Tipojuego> getListatiposjuegos() {
        return listatiposjuegos;
    }

    public void setListatiposjuegos(List<Tipojuego> listatiposjuegos) {
        this.listatiposjuegos = listatiposjuegos;
    }

    public void delete() {
        sessionBean.marketingUserFacade.deleteClientes(elemento.getIdCliente());
        sessionBean.registrarlog("eliminar", "Clientes", elemento.getNombres() + " " + elemento.getApellidos());
        FacesUtil.addInfoMessage("Cliente eliminado", elemento.getNombres() + " " + elemento.getApellidos());
        elemento = new ClienteDTO();
        busquedaAvanzada();
    }

    public void goCliente(int id) {
        try {
            sessionBean.setAttribute("idCliente", new Integer(id));
            FacesContext.getCurrentInstance().getExternalContext().redirect("ClientesAct.xhtml");
        } catch (IOException ex) {
        }
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
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

    public void busquedaAvanzada() {

        List<Cliente> clientes = new ArrayList<Cliente>();
        for (CasinoBoolean casino : casinoBooleans) {
            if (casino.isSelected()) {
                clientes.addAll(sessionBean.marketingUserFacade.findAllClientesCasinos(casino.getCasino(), nombre, apellidos, identificacion, tipodocumento));
            }
        }

        boolean noCatselected = true;
        boolean noTipselected = true;
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

        for (Iterator<Cliente> it = clientes.iterator(); it.hasNext();) {
            Cliente cliente = it.next();

            boolean siCategoria = false;
            boolean siTipoJuego = false;
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
                continue;
            }
            if (!siTipoJuego) {
                it.remove();
                continue;
            }
            if (ciudad != null && !ciudad.equals("")) {
                if (!cliente.getCiudad().contains(ciudad)) {
                    it.remove();
                    continue;
                }
            }
            if (pais != null && !pais.equals("")) {
                if (!cliente.getPais().contains(pais)) {
                    it.remove();
                    continue;
                }
            }
            if ((mes != 12) || (dia != null && !dia.equals(0))) {
                Calendar fecha = Calendar.getInstance();
                if (cliente.getCumpleanos() == null) {
                    it.remove();
                    continue;
                }
                fecha.setTime(cliente.getCumpleanos());
                if (mes != 12) {
                    if (fecha.get(Calendar.MONTH) != mes) {
                        it.remove();
                        continue;
                    }
                }
                if (dia != null && !dia.equals(0)) {
                    if (fecha.get(Calendar.DAY_OF_MONTH) != dia) {
                        it.remove();
                        continue;
                    }
                }
            }
        }

        lista = new ArrayList<ClienteDTO>();
        fillClientes(clientes);
        listaTipos= sessionBean.marketingUserFacade.findAllTipoDocumentos();
        FacesUtil.addInfoMessage("Clientes filtrados!", "Cantidad " + lista.size());
        System.gc();
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

    public boolean isTodoscasinos() {
        return todoscasinos;
    }

    public void setTodoscasinos(boolean todoscasinos) {
        this.todoscasinos = todoscasinos;
    }

    public boolean isTodostip() {
        return todostip;
    }

    public void setTodostip(boolean todostip) {
        this.todostip = todostip;
    }

    public List<CategoriaBoolean> getCategoriaBooleans() {
        return categoriaBooleans;
    }

    public void setCategoriaBooleans(List<CategoriaBoolean> categoriaBooleans) {
        this.categoriaBooleans = categoriaBooleans;
    }

    public boolean isTodosCat() {
        return todosCat;
    }

    public void setTodosCat(boolean todosCat) {
        this.todosCat = todosCat;
    }

    public Integer getTotales() {
        return totales;
    }

    public void setTotales(Integer totales) {
        this.totales = totales;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    private void buscarCLientesIniciales() {
        lista = new ArrayList<ClienteDTO>();
        for (Casino c : sessionBean.getUsuario().getCasinoList()) {
            List<Cliente> clientes = sessionBean.marketingUserFacade.findAllClientesCasinos(c, "", "", "", tipodocumento);
            fillClientes(clientes);
        }
    }

    private void fillClientes(List<Cliente> clientes) {
        for (Cliente cliente : clientes) {
            lista.add(new ClienteDTO(cliente));
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void enviarMail() {
        Notificador.notificar(Notificador.EMAIL_CLIENTE, elemento.getAsEmail(), "Perfil de Cliente", email);
        FacesUtil.addInfoMessage("Correo enviado", "");
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public Tipodocumento getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(Tipodocumento tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public List<Tipodocumento> getListaTipos() {
        return listaTipos;
    }

    public void setListaTipos(List<Tipodocumento> listaTipos) {
        this.listaTipos = listaTipos;
    }
    
}
