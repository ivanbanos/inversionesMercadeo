/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.facade.impl;

import com.invbf.sistemagestionmercadeo.dao.GestionBarajasDao;
import com.invbf.sistemagestionmercadeo.dto.BarajasCantidad;
import com.invbf.sistemagestionmercadeo.dto.BarajasDTO;
import com.invbf.sistemagestionmercadeo.dto.InventarioBarajasDTO;
import com.invbf.sistemagestionmercadeo.dto.MaterialesDTO;
import com.invbf.sistemagestionmercadeo.dto.OrdenCompraBarajaDTO;
import com.invbf.sistemagestionmercadeo.dto.SolicitudBarajasDTO;
import com.invbf.sistemagestionmercadeo.entity.Barajas;
import com.invbf.sistemagestionmercadeo.entity.Inventarobarajas;
import com.invbf.sistemagestionmercadeo.entity.Materialesbarajas;
import com.invbf.sistemagestionmercadeo.entity.Ordencomprabaraja;
import com.invbf.sistemagestionmercadeo.entity.Ordencomprabarajadetalle;
import com.invbf.sistemagestionmercadeo.entity.Solicitudbarajadetalle;
import com.invbf.sistemagestionmercadeo.entity.Solicitudbarajas;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.facade.BarajasFacade;
import com.invbf.sistemagestionmercadeo.util.Notificador;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ivan
 */
public class BarajasFacadeImpl implements BarajasFacade, Serializable {

    private BarajasDTO transformarBaraja(Barajas baraja) {
        return new BarajasDTO(baraja.getId(), baraja.getColor(), baraja.getMarca(), baraja.getValorpromedio(), transformarMaterial(baraja.getMaterial()));
    }

    private Barajas transformarBaraja(BarajasDTO baraja) {
        return new Barajas(baraja.getId(), baraja.getColor(), baraja.getMarca(), baraja.getValorpromedio(), transformarMaterial(baraja.getMaterial()));
    }

    private MaterialesDTO transformarMaterial(Materialesbarajas material) {
        return new MaterialesDTO(material.getId(), material.getNombre());
    }

    private Materialesbarajas transformarMaterial(MaterialesDTO material) {
        return new Materialesbarajas(material.getId(), material.getNombre());
    }

    private InventarioBarajasDTO transformarInventario(List<Inventarobarajas> listaInvenratioBarajas) {
        InventarioBarajasDTO inventario = new InventarioBarajasDTO();
        System.err.println(listaInvenratioBarajas.size());
        for (Inventarobarajas item : listaInvenratioBarajas) {
            inventario.getInventario().add(new BarajasCantidad(item.getId(), transformarBaraja(item.getBaraja()), item.getCantidadbarajas(), item.getCantidadbarajas()));
        }

        return inventario;
    }

    private List<OrdenCompraBarajaDTO> transformarOrdenesCompra(List<Ordencomprabaraja> listaOrdenesCompraBarajas) {
        List<OrdenCompraBarajaDTO> ordenes = new ArrayList<OrdenCompraBarajaDTO>();

        for (Ordencomprabaraja item : listaOrdenesCompraBarajas) {
            ordenes.add(transormarOrdenCompra(item));
        }

        return ordenes;
    }

    private List<SolicitudBarajasDTO> transformarSolicitudesBarajas(List<Solicitudbarajas> listaSoliciudesBarajas) {
        List<SolicitudBarajasDTO> ordenes = new ArrayList<SolicitudBarajasDTO>();

        for (Solicitudbarajas item : listaSoliciudesBarajas) {
            ordenes.add(transormarSolicitudBarajas(item));
        }

        return ordenes;
    }

    private OrdenCompraBarajaDTO transormarOrdenCompra(Ordencomprabaraja item) {
        OrdenCompraBarajaDTO orden = new OrdenCompraBarajaDTO();
        orden.setEstado(item.getEsatdo());
        orden.setId(item.getId());
        orden.setFechaAceptada(item.getFechaAceptada());
        orden.setFechaCreacion(item.getFechaCreacion());
        orden.setFechaRecibida(item.getFechaRecibida());
        orden.setUsuarioCreado(item.getCreador() == null ? "" : item.getCreador().getNombreUsuario());
        orden.setUsuarioAceptador(item.getAceptador() == null ? "" : item.getAceptador().getNombreUsuario());
        orden.setUsuarioREcibidor(item.getRecibidor() == null ? "" : item.getRecibidor().getNombreUsuario());
        for (Ordencomprabarajadetalle detalle : item.getOrdencomprabarajadetalleList()) {
            orden.getCantidades().add(new BarajasCantidad(detalle.getInventarobarajas().getId(), transformarBaraja(detalle.getInventarobarajas().getBaraja()), detalle.getCantidad(), detalle.getCantidad()));
        }
        return orden;
    }

    private SolicitudBarajasDTO transormarSolicitudBarajas(Solicitudbarajas item) {
        SolicitudBarajasDTO orden = new SolicitudBarajasDTO();
        orden.setEstado(item.getEstado());
        orden.setId(item.getId());
        orden.setFechaAceptada(item.getFechentrega());
        orden.setFechaCreacion(item.getFechacreacion());
        orden.setFechaRecibida(item.getFecharecepcion());
        orden.setFechaDestruccion(item.getFechaDestruccion());
        orden.setUsuarioCreado(item.getCreador() == null ? "" : item.getCreador().getNombreUsuario());
        orden.setUsuarioAceptador(item.getAceptador() == null ? "" : item.getAceptador().getNombreUsuario());
        orden.setUsuarioREcibidor(item.getRecibidor() == null ? "" : item.getRecibidor().getNombreUsuario());
        orden.setUsuarioDestructor(item.getDestructor() == null ? "" : item.getDestructor().getNombreUsuario());
        for (Solicitudbarajadetalle detalle : item.getSolicitudbarajadetalleList()) {
            orden.getCantidades().add(new BarajasCantidad(detalle.getInventarobarajas().getId(), transformarBaraja(detalle.getInventarobarajas().getBaraja()), detalle.getCantidad(),detalle.getCantidad()));
        }
        return orden;
    }

    @Override
    public List<BarajasDTO> getListaBarajas() {
        List<Barajas> barajas = GestionBarajasDao.getListaBArajas();
        List<BarajasDTO> barajasDTO = new ArrayList<BarajasDTO>();
        for (Barajas baraja : barajas) {
            barajasDTO.add(transformarBaraja(baraja));
        }
        return barajasDTO;
    }

    @Override
    public List<MaterialesDTO> getListaMateriales() {
        List<Materialesbarajas> materiales = GestionBarajasDao.getListaMateriales();
        List<MaterialesDTO> materialesDTOs = new ArrayList<MaterialesDTO>();
        for (Materialesbarajas material : materiales) {
            System.out.println("Nombre" + material.getNombre());
            materialesDTOs.add(transformarMaterial(material));
        }
        return materialesDTOs;
    }

    @Override
    public MaterialesDTO addMaterial(MaterialesDTO material) {
        return transformarMaterial(GestionBarajasDao.addMaterial(transformarMaterial(material)));
    }

    @Override
    public MaterialesDTO deleteMaterial(MaterialesDTO material) {
        GestionBarajasDao.deleteMaterial(transformarMaterial(material));
        return material;
    }

    @Override
    public BarajasDTO addBaraja(BarajasDTO elemento) {
        elemento = transformarBaraja(GestionBarajasDao.addBaraja(transformarBaraja(elemento)));
        GestionBarajasDao.addInventarioBaraja(transformarBaraja(elemento));
        return elemento;
    }

    @Override
    public BarajasDTO deleteBaraja(BarajasDTO elemento) {
        GestionBarajasDao.deleteBaraja(transformarBaraja(elemento));
        return elemento;
    }

    @Override
    public InventarioBarajasDTO getInventario() {
        return transformarInventario(GestionBarajasDao.getListaInvenratioBarajas());
    }

    @Override
    public List<OrdenCompraBarajaDTO> getOrdenesCompra() {
        return transformarOrdenesCompra(GestionBarajasDao.getListaOrdenesCompraBarajas());
    }

    @Override
    public int crearOrdenBarajas(InventarioBarajasDTO inventario, Usuario usuario) {
        Ordencomprabaraja orden = new Ordencomprabaraja();
        orden.setFechaCreacion(new Date());
        orden.setCreador(usuario);
        orden.setEsatdo("CREADA");
        orden = GestionBarajasDao.crearOrdenCompra(orden);
        orden.setOrdencomprabarajadetalleList(getDetallesOrden(inventario, orden.getId()));
        return GestionBarajasDao.guardarOrdenCompra(orden).getId();

    }

    private List<Ordencomprabarajadetalle> getDetallesOrden(InventarioBarajasDTO inventario, Integer id) {
        List<Ordencomprabarajadetalle> detalles = new ArrayList<Ordencomprabarajadetalle>();
        for (BarajasCantidad barajas : inventario.getInventario()) {
            detalles.add(getDettaleOrden(barajas, id));
        }
        return detalles;
    }

    private List<Solicitudbarajadetalle> getDetallesSolicitud(InventarioBarajasDTO inventario, Integer id) {
        List<Solicitudbarajadetalle> detalles = new ArrayList<Solicitudbarajadetalle>();
        for (BarajasCantidad barajas : inventario.getInventario()) {
            detalles.add(getDettaleSolicitud(barajas, id));
        }
        return detalles;
    }

    private Ordencomprabarajadetalle getDettaleOrden(BarajasCantidad barajas, Integer id) {
        Ordencomprabarajadetalle detalle = new Ordencomprabarajadetalle(id, barajas.getId());
        detalle.setCantidad(barajas.getCantidad());
        detalle.setCantidadAprobada(barajas.getCantidadR());
        return detalle;
    }

    private Solicitudbarajadetalle getDettaleSolicitud(BarajasCantidad barajas, Integer id) {
        Solicitudbarajadetalle detalle = new Solicitudbarajadetalle(id, barajas.getId());
        detalle.setCantidad(barajas.getCantidad());
        return detalle;
    }

    @Override
    public OrdenCompraBarajaDTO getOrden(Integer idOrden) {
        return transormarOrdenCompra(GestionBarajasDao.getOrdenCompraBaraja(idOrden));
    }

    @Override
    public void aprobarOrden(Integer idOrden, Usuario usuario) {
        GestionBarajasDao.aprobarOrden(idOrden, usuario);
    }

    @Override
    public void recibirOrden(Integer idOrden, Usuario usuario) {
        GestionBarajasDao.recibirOrden(idOrden, usuario);
    }

    @Override
    public List<SolicitudBarajasDTO> getSolicitudesBarajas(boolean getTodas, int idUsuario) {
        if (getTodas) {
            return transformarSolicitudesBarajas(GestionBarajasDao.getListaSoliciudesBarajas());
        } else {
            return transformarSolicitudesBarajas(GestionBarajasDao.getListaSoliciudesBarajas(idUsuario));
        }
    }

    @Override
    public int crearSolicitudBarajas(InventarioBarajasDTO inventario, Usuario usuario) {
        Solicitudbarajas orden = new Solicitudbarajas();
        orden.setFechacreacion(new Date());
        orden.setCreador(usuario);
        orden.setEstado("CREADA");
        orden = GestionBarajasDao.crearSolicitudBarajas(orden);
        orden.setSolicitudbarajadetalleList(getDetallesSolicitud(inventario, orden.getId()));
        return GestionBarajasDao.guardarSolicitudBarajas(orden).getId();
    }

    @Override
    public SolicitudBarajasDTO getSolicitud(Integer idOrden) {
        return transormarSolicitudBarajas(GestionBarajasDao.getSolicitudBaraja(idOrden));
    }

    @Override
    public void entregarSolicitud(Integer idOrden, Usuario usuario) {
        GestionBarajasDao.entregarSolicitud(idOrden, usuario);

        
    }

    @Override
    public void recibirSolicitud(Integer idOrden, Usuario usuario) {
        GestionBarajasDao.recibirSolicitud(idOrden, usuario);
    }

}
