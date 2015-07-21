/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.facade;

import com.invbf.sistemagestionmercadeo.dto.BarajasDTO;
import com.invbf.sistemagestionmercadeo.dto.InventarioBarajasDTO;
import com.invbf.sistemagestionmercadeo.dto.MaterialesDTO;
import com.invbf.sistemagestionmercadeo.dto.OrdenCompraBarajaDTO;
import com.invbf.sistemagestionmercadeo.dto.SolicitudBarajasDTO;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.util.CasinoBoolean;
import java.util.List;

/**
 *
 * @author ivan
 */
public interface BarajasFacade {

    public List<BarajasDTO> getListaBarajas();

    public List<MaterialesDTO> getListaMateriales();

    public MaterialesDTO addMaterial(MaterialesDTO material);

    public MaterialesDTO deleteMaterial(MaterialesDTO material);

    public BarajasDTO addBaraja(BarajasDTO elemento);

    public BarajasDTO deleteBaraja(BarajasDTO elemento);

    public InventarioBarajasDTO getInventario(Integer id);

    public List<OrdenCompraBarajaDTO> getOrdenesCompra();

    public int crearOrdenBarajas(List<InventarioBarajasDTO> inventari, Usuario usuario);

    public OrdenCompraBarajaDTO getOrden(Integer idOrden);

    public void aprobarOrden(Integer idOrden, Usuario usuario);

    public void recibirOrden(Integer idOrden, Usuario usuario);

    public List<SolicitudBarajasDTO> getSolicitudesBarajas(boolean todas, int idUsuario);

    public int crearSolicitudBarajas(InventarioBarajasDTO inventario, Usuario usuario);

    public SolicitudBarajasDTO getSolicitud(Integer idOrden);

    public void entregarSolicitud(Integer idOrden, Usuario usuario);

    public void recibirSolicitud(Integer idOrden, Usuario usuario);

    public List<InventarioBarajasDTO> getBodegas();

    public Integer crearBodega(String nombre);

    public void guardarBodega(InventarioBarajasDTO inventario, List<CasinoBoolean> casinos);

    public List<InventarioBarajasDTO> getBodegas(Usuario usuario);

    
}
