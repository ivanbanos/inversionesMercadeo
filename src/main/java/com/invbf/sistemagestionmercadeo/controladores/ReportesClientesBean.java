/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.entity.Atributo;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Categoria;
import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Permiso;
import com.invbf.sistemagestionmercadeo.entity.Tipodocumento;
import com.invbf.sistemagestionmercadeo.entity.Tipojuego;
import com.invbf.sistemagestionmercadeo.util.CasinoBoolean;
import com.invbf.sistemagestionmercadeo.util.CategoriaBoolean;
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
public class ReportesClientesBean  implements Serializable{

    private List<Cliente> lista;
    private Cliente elemento;
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

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    private List<Cliente> flista;

    public List<Cliente> getFlista() {
        return flista;
    }

    public void setFlista(List<Cliente> flista) {
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
        elemento = new Cliente();
        elemento.setIdCasinoPreferencial(new Casino());
        elemento.setIdCategorias(new Categoria());
        elemento.setIdTipoDocumento(new Tipodocumento());
        buscarCLientesIniciales();
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
        categoriaBooleans = new ArrayList<CategoriaBoolean>();
        for (Tipojuego tipoJuego : tipoJuegos) {
            juegoBooleans.add(new TipoJuegoBoolean(tipoJuego, false));
        }
        for (Casino casinob : casinos) {
            casinoBooleans.add(new CasinoBoolean(casinob, false));
        }
        for (Categoria categoria : categorias) {
            categoriaBooleans.add(new CategoriaBoolean(categoria, false));
        }
        mes= 12;
        System.gc();
    }

    public List<Cliente> getLista() {
        return lista;
    }

    public void setLista(List<Cliente> lista) {
        this.lista = lista;
    }

    public Cliente getElemento() {
        return elemento;
    }

    public void setElemento(Cliente elemento) {
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
        sessionBean.marketingUserFacade.deleteClientes(elemento);
        lista = sessionBean.marketingUserFacade.findAllClientes();
        sessionBean.registrarlog("eliminar", "Clientes", elemento.getNombres()+" "+elemento.getApellidos());
        FacesUtil.addInfoMessage("Cliente eliminado", elemento.getNombres()+" "+elemento.getApellidos());
        elemento = new Cliente();
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

        lista = sessionBean.marketingUserFacade.findAllClientes();

        boolean noCatselected = true;
        boolean noTipselected = true;
        boolean noCasselected = true;
        for (CasinoBoolean cb : casinoBooleans) {
            if (todoscasinos) {
                cb.setSelected(true);
                continue;
            }
            if (cb.isSelected()) {
                noCasselected = false;
                break;
            }
        }
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

        for (Iterator<Cliente> it = lista.iterator(); it.hasNext();) {
            Cliente cliente = it.next();

            boolean siCategoria = false;
            boolean siTipoJuego = false;
            boolean siCasino = false;
            if (noCasselected) {
                siCasino = true;
            } else {
                for (CasinoBoolean cb : casinoBooleans) {
                    if (cb.isSelected()) {
                        if (cliente.getIdCasinoPreferencial().equals(cb.getCasino())) {
                            siCasino = true;
                            break;
                        }
                    }
                }
            }
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
            if (!siCasino) {
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
            if ((mes!=12)|| (dia != null && !dia.equals(0))) {
                Calendar fecha = Calendar.getInstance();
                if (cliente.getCumpleanos() == null) {
                    it.remove();
                    continue;
                }
                fecha.setTime(cliente.getCumpleanos());
                if (mes!=12) {
                    if (fecha.get(Calendar.MONTH)!=mes) {
                        it.remove();
                        continue;
                    }
                }
                if (dia != null && !dia.equals(0)) {
                    if (fecha.get(Calendar.DAY_OF_MONTH)!=dia) {
                        it.remove();
                        continue;
                    }
                }
            }
        }
        FacesUtil.addInfoMessage("Clientes filtrados!","Cantidad "+lista.size());
        
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
        lista = new ArrayList<Cliente>();
        for(Casino c: sessionBean.getUsuario().getCasinoList()){
            lista.addAll(sessionBean.marketingUserFacade.findAllClientesCasinos(c));
        }
    }

}
