/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.dto.RegaloDTO;
import com.invbf.sistemagestionmercadeo.dto.CategoriaDTO;
import com.invbf.sistemagestionmercadeo.util.Mensajes;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author ivan
 */

@ManagedBean
@ViewScoped
public class CrudRegalosBean implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<RegaloDTO> lista;
    private RegaloDTO elemento;
    private List<CategoriaDTO> categorias;
    private CategoriaDTO categoria;
    
    private String filename;
    private UploadedFile file;

    /**
     * @return the sessionBean
     */
    public SessionBean getSessionBean() {
        return sessionBean;
    }

    /**
     * @param sessionBean the sessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public CrudRegalosBean() {

    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("regalos");
        if (!sessionBean.perfilViewMatch("verRegalos")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        inicializar();
    }

    public List<RegaloDTO> getLista() {
        return lista;
    }

    public void setLista(List<RegaloDTO> lista) {
        this.lista = lista;
    }

    public RegaloDTO getElemento() {
        return elemento;
    }

    public void setElemento(RegaloDTO elemento) {
        this.elemento = elemento;
    }

    public List<CategoriaDTO> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaDTO> categorias) {
        this.categorias = categorias;
    }

    public CategoriaDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDTO categoria) {
        this.categoria = categoria;
    }

    public void actualizarRegalo() {
        System.out.println("Regalo id:"+elemento.getId());
        System.out.println("Regalo id:"+elemento.getFileName());
        elemento = sessionBean.regalosFacade.addRegalo(elemento);
        sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "OK!", "Regalo Agregado con exito!"));
        inicializar();
    }

    public void eliminarRegalo() {
        if (elemento.getId() == null) {
            sessionBean.putMensaje(new Mensajes(Mensajes.ADVERTENCIA, "Advertencia!", "Debe seleccionar un regalo primero"));
        } else {
            elemento = sessionBean.regalosFacade.deleteRegalo(elemento);
            sessionBean.putMensaje(new Mensajes(Mensajes.INFORMACION, "OK!", "Regalo eliminado con exito!"));
        }
        inicializar();
    }

    public void nuevoRegalo() {
        inicializar();
    }

    private void inicializar() {
        categoria = new CategoriaDTO();
        elemento = new RegaloDTO();
        elemento.setCategoria(new CategoriaDTO());
        lista = sessionBean.regalosFacade.getListaRegalos();
        categorias = sessionBean.regalosFacade.getListaCategorias();
        sessionBean.printMensajes();
    }
    
    public void upload() {
        filename = file.getFileName().replace(" ", "");
        System.out.println(filename.replace(" ", ""));
        sessionBean.regalosFacade.guardarImagen(file.getContents(), filename);
        System.out.println("File name: " + filename);
        file = null;
        elemento.setFileName(filename);
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    public void handleFileUpload(FileUploadEvent event) {
        if (event != null) {
            file = event.getFile();
            sessionBean.regalosFacade.guardarImagen(file.getContents(), file.getFileName());
            sessionBean.setAttribute("imagen", file.getContents());
            
        filename = file.getFileName().replace(" ", "");
elemento.setFileName(filename);
            System.out.println("File name: " + file.getFileName());
            System.out.println("File name: " + elemento.getFileName());
            System.out.println("File name: " + elemento.getNombre());
        }
    }
}
