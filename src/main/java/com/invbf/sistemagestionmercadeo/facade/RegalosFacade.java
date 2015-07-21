/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.facade;

import com.invbf.sistemagestionmercadeo.dto.RegaloDTO;
import com.invbf.sistemagestionmercadeo.dto.InventarioRegalosDTO;
import com.invbf.sistemagestionmercadeo.dto.CategoriaDTO;
import com.invbf.sistemagestionmercadeo.dto.OrdenCompraRegaloDTO;
import com.invbf.sistemagestionmercadeo.dto.SolicitudRegaloDTO;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import java.util.List;

/**
 *
 * @author ivan
 */
public interface RegalosFacade {
    public List<RegaloDTO> getListaBarajas();

    public List<CategoriaDTO> getListaMateriales();

    public CategoriaDTO addMaterial(CategoriaDTO material);

    public CategoriaDTO deleteMaterial(CategoriaDTO material);

    public RegaloDTO addBaraja(RegaloDTO elemento);

    public RegaloDTO deleteBaraja(RegaloDTO elemento);

    public InventarioRegalosDTO getInventario();

    public List<OrdenCompraRegaloDTO> getOrdenesCompra();

    public int crearOrdenBarajas(InventarioRegalosDTO inventario, Usuario usuario);

    public OrdenCompraRegaloDTO getOrden(Integer idOrden);

    public void aprobarOrden(Integer idOrden, Usuario usuario);

    public void recibirOrden(Integer idOrden, Usuario usuario);

    public List<SolicitudRegaloDTO> getSolicitudesBarajas(boolean todas, int idUsuario);

    public int crearSolicitudBarajas(InventarioRegalosDTO inventario, Usuario usuario);

    public SolicitudRegaloDTO getSolicitud(Integer idOrden);

    public void entregarSolicitud(Integer idOrden, Usuario usuario);

    public void recibirSolicitud(Integer idOrden, Usuario usuario);

    
}
