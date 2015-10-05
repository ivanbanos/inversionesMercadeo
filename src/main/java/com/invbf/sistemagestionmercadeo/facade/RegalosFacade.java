/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.facade;

import com.invbf.sistemagestionmercadeo.dto.CasinoDto;
import com.invbf.sistemagestionmercadeo.dto.CategoriaDTO;
import com.invbf.sistemagestionmercadeo.dto.ClienteRegaloDTO;
import com.invbf.sistemagestionmercadeo.dto.InventarioRegalosDTO;
import com.invbf.sistemagestionmercadeo.dto.OrdenCompraRegaloDTO;
import com.invbf.sistemagestionmercadeo.dto.RegaloCanje;
import com.invbf.sistemagestionmercadeo.dto.RegaloDTO;
import com.invbf.sistemagestionmercadeo.dto.ReporteGestionEntregaRegalos;
import com.invbf.sistemagestionmercadeo.dto.SolicitudRegaloDTO;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.util.CasinoBoolean;
import com.invbf.sistemagestionmercadeo.util.CategoriaBoolean;
import com.invbf.sistemagestionmercadeo.util.ClienteDTO;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author ivan
 */
public interface RegalosFacade {

    public List<CategoriaDTO> getListaCategorias();

    public List<RegaloDTO> getListaRegalos();

    public RegaloDTO deleteRegalo(RegaloDTO elemento);

    public RegaloDTO addRegalo(RegaloDTO elemento);

    public InventarioRegalosDTO getInventario();
    
    public InventarioRegalosDTO getInventarioParaSolicitud();

    public void guardarInventario(InventarioRegalosDTO inventario);

    public List<OrdenCompraRegaloDTO> getOrdenesCompra();

    public List<OrdenCompraRegaloDTO> getOrdenesCompra(Usuario usuario);

    public void generarOrdenRegalos(InventarioRegalosDTO inventario, String observaciones,  Usuario usuairo);

    public OrdenCompraRegaloDTO getOrden(Integer idOrden);

    public void rechazarOrden(OrdenCompraRegaloDTO orden, Usuario usuario);

    public void aprobarOrden(OrdenCompraRegaloDTO orden, Usuario usuario);

    public void recibirOrden(OrdenCompraRegaloDTO idOrden, Usuario usuario);

    public void crearOrden(OrdenCompraRegaloDTO orden, Usuario usuario);

    public List<SolicitudRegaloDTO> getSolicitudesRegalos(boolean perfilViewMatch, Usuario usuario);

    public List<ClienteRegaloDTO> findAllClientesCasinos(CasinoDto casino, List<CategoriaBoolean> categoriaBooleans, Integer mes, String sexo);

    public void generarSolicitudRegalos(CasinoDto casino, List<ClienteRegaloDTO> lista, Usuario usuario);

    public SolicitudRegaloDTO getSolicitud(Integer idOrden);

    public void aprobarSolicitud(SolicitudRegaloDTO solicitud, Usuario usuario);

    public void rechazarSolicitud(SolicitudRegaloDTO solicitud, Usuario usuario);

    public void recibirSolicitud(SolicitudRegaloDTO solicitud, Usuario usuario);

    public void enviarSolicitud(SolicitudRegaloDTO solicitud, Usuario usuario);

    public List<RegaloCanje> buscarRegalos(String buscar,Usuario usuario);

    public void guardarImagen(byte[] contents, String filename);

    public void entregar(RegaloCanje regalo, Usuario usuario);

    public InventarioRegalosDTO getInventarioRequerimiento();

    public void ingresarOrden(OrdenCompraRegaloDTO orden, Usuario usuario);

    public ReporteGestionEntregaRegalos getReporte(List<CasinoBoolean> casinos, Integer ano, Integer mes);

    

}
