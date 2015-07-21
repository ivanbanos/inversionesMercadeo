/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.facade.impl;

import com.invbf.sistemagestionmercadeo.dto.CategoriaDTO;
import com.invbf.sistemagestionmercadeo.dto.InventarioRegalosDTO;
import com.invbf.sistemagestionmercadeo.dto.OrdenCompraRegaloDTO;
import com.invbf.sistemagestionmercadeo.dto.RegaloDTO;
import com.invbf.sistemagestionmercadeo.dto.RegalosCantidadDTO;
import com.invbf.sistemagestionmercadeo.dto.SolicitudRegaloDTO;
import com.invbf.sistemagestionmercadeo.entity.Categorias;
import com.invbf.sistemagestionmercadeo.entity.Ordencompraregalodetalle;
import com.invbf.sistemagestionmercadeo.entity.Ordencompraregalos;
import com.invbf.sistemagestionmercadeo.entity.Regalos;
import com.invbf.sistemagestionmercadeo.entity.Regalosinventario;
import com.invbf.sistemagestionmercadeo.entity.Solicitudregalos;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.facade.RegalosFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ivan
 */
public class RegalosFacadeImpl implements RegalosFacade, Serializable {

    @Override
    public List<RegaloDTO> getListaBarajas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CategoriaDTO> getListaMateriales() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CategoriaDTO addMaterial(CategoriaDTO material) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CategoriaDTO deleteMaterial(CategoriaDTO material) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RegaloDTO addBaraja(RegaloDTO elemento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RegaloDTO deleteBaraja(RegaloDTO elemento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InventarioRegalosDTO getInventario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<OrdenCompraRegaloDTO> getOrdenesCompra() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int crearOrdenBarajas(InventarioRegalosDTO inventario, Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OrdenCompraRegaloDTO getOrden(Integer idOrden) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void aprobarOrden(Integer idOrden, Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void recibirOrden(Integer idOrden, Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SolicitudRegaloDTO> getSolicitudesBarajas(boolean todas, int idUsuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int crearSolicitudBarajas(InventarioRegalosDTO inventario, Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SolicitudRegaloDTO getSolicitud(Integer idOrden) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void entregarSolicitud(Integer idOrden, Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void recibirSolicitud(Integer idOrden, Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}