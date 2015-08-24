/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.facade.impl;

import com.invbf.sistemagestionmercadeo.dao.GestionBarajasDao;
import com.invbf.sistemagestionmercadeo.dto.ActaDestruccionDTO;
import com.invbf.sistemagestionmercadeo.dto.BarajasCantidad;
import com.invbf.sistemagestionmercadeo.dto.BarajasDTO;
import com.invbf.sistemagestionmercadeo.dto.CasinoDto;
import com.invbf.sistemagestionmercadeo.dto.ConsumoBarajas;
import com.invbf.sistemagestionmercadeo.dto.DestruccionPorMes;
import com.invbf.sistemagestionmercadeo.dto.InventarioBarajasDTO;
import com.invbf.sistemagestionmercadeo.dto.MaterialesDTO;
import com.invbf.sistemagestionmercadeo.dto.OrdenCompraBarajaDTO;
import com.invbf.sistemagestionmercadeo.dto.SolicitudBarajasDTO;
import com.invbf.sistemagestionmercadeo.dto.SolicitudesPorMes;
import com.invbf.sistemagestionmercadeo.dto.TrasladoDTO;
import com.invbf.sistemagestionmercadeo.entity.Actasdestruccionbarajas;
import com.invbf.sistemagestionmercadeo.entity.Barajas;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Destruccionbarajasmaestro;
import com.invbf.sistemagestionmercadeo.entity.Inventarobarajas;
import com.invbf.sistemagestionmercadeo.entity.Materialesbarajas;
import com.invbf.sistemagestionmercadeo.entity.Ordencomprabaraja;
import com.invbf.sistemagestionmercadeo.entity.Ordencomprabarajadetalle;
import com.invbf.sistemagestionmercadeo.entity.Solicitudbarajadetalle;
import com.invbf.sistemagestionmercadeo.entity.Solicitudbarajas;
import com.invbf.sistemagestionmercadeo.entity.Trasladobarajas;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.facade.BarajasFacade;
import com.invbf.sistemagestionmercadeo.util.CasinoBoolean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
        return new MaterialesDTO(material.getId(), material.getNombre(), material.getDescripcion());
    }

    private Materialesbarajas transformarMaterial(MaterialesDTO material) {
        return new Materialesbarajas(material.getId(), material.getNombre(), material.getDescripcion());
    }

    private List<InventarioBarajasDTO> transformarBodegas(List<Casino> bodegas) {
        List<InventarioBarajasDTO> bodegasdto = new ArrayList<InventarioBarajasDTO>();
        for (Casino bodega : bodegas) {
            bodegasdto.add(transformarInventario(bodega));
        }
        return bodegasdto;
    }

    private List<InventarioBarajasDTO> transformarBodegasParaSol(List<Casino> bodegas) {
        List<InventarioBarajasDTO> bodegasdto = new ArrayList<InventarioBarajasDTO>();
        for (Casino bodega : bodegas) {
            bodegasdto.add(transformarInventarioParaSol(bodega));
        }
        return bodegasdto;
    }

    private List<InventarioBarajasDTO> transformarBodegasParaDes(List<Casino> bodegas) {
        List<InventarioBarajasDTO> bodegasdto = new ArrayList<InventarioBarajasDTO>();
        for (Casino bodega : bodegas) {
            bodegasdto.add(transformarInventarioParaDes(bodega));
        }
        return bodegasdto;
    }

    private InventarioBarajasDTO transformarInventario(Casino bodega) {

        InventarioBarajasDTO bodegadto = new InventarioBarajasDTO();
        bodegadto.setId(bodega.getIdCasino());
        bodegadto.setNombre(bodega.getNombre());
        for (Inventarobarajas item : bodega.getInventarobarajasList()) {
            bodegadto.getInventario().add(new BarajasCantidad(item.getId(), transformarBaraja(item.getBaraja()), item.getCantidadbarajas(), item.getCantidadbarajas(), item.getUso(), item.getPordestruir(), item.getDestruidas(), item.getMax(), item.getMin(), bodegadto.getNombre(), 0));
        }
        return bodegadto;
    }

    private InventarioBarajasDTO transformarInventarioO(Casino bodega) {

        InventarioBarajasDTO bodegadto = new InventarioBarajasDTO();
        bodegadto.setId(bodega.getIdCasino());
        bodegadto.setNombre(bodega.getNombre());
        for (Inventarobarajas item : bodega.getInventarobarajasList()) {
            bodegadto.getInventario().add(new BarajasCantidad(item.getId(), transformarBaraja(item.getBaraja()), item.getCantidadbarajas(), item.getCantidadbarajas(), item.getUso(), item.getPordestruir(), item.getDestruidas(), item.getMax(), item.getMin(), bodegadto.getNombre(), 0));
        }
        return bodegadto;
    }

    private InventarioBarajasDTO transformarInventarioParaSol(Casino bodega) {

        InventarioBarajasDTO bodegadto = new InventarioBarajasDTO();
        bodegadto.setId(bodega.getIdCasino());
        bodegadto.setNombre(bodega.getNombre());
        for (Inventarobarajas item : bodega.getInventarobarajasList()) {
            bodegadto.getInventario().add(new BarajasCantidad(item.getId(), transformarBaraja(item.getBaraja()), 0, (item.getCantidadbarajas() - item.getDestruidas() - item.getPordestruir() - item.getUso()), 0, 0, 0, 0, 0, bodegadto.getNombre(), 0));
        }
        return bodegadto;
    }

    private InventarioBarajasDTO transformarInventarioParaDes(Casino bodega) {

        InventarioBarajasDTO bodegadto = new InventarioBarajasDTO();
        bodegadto.setId(bodega.getIdCasino());
        bodegadto.setNombre(bodega.getNombre());
        for (Inventarobarajas item : bodega.getInventarobarajasList()) {
            bodegadto.getInventario().add(new BarajasCantidad(item.getId(), transformarBaraja(item.getBaraja()), 0, 0, 0, item.getPordestruir(), 0, 0, 0, bodegadto.getNombre(), 0));
        }
        return bodegadto;
    }

    private CasinoDto transformarCasino(Casino casino) {
        return new CasinoDto(casino.getIdCasino(), casino.getNombre());
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
            orden.getCantidades().add(new BarajasCantidad(detalle.getInventarobarajas().getId(), transformarBaraja(detalle.getInventarobarajas().getBaraja()), detalle.getCantidad(), detalle.getCantidad(), detalle.getCantidad(), detalle.getCantidad(), detalle.getCantidad(), detalle.getCantidad(), (detalle.getInventarobarajas().getCantidadbarajas() - detalle.getInventarobarajas().getUso() - detalle.getInventarobarajas().getPordestruir() - detalle.getInventarobarajas().getDestruidas()), detalle.getInventarobarajas().getCasino().getNombre(), 0));
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
        orden.setEntregadasnuevas(item.getEntregadasNuevas());
        orden.setEntregadasusadas(item.getEntregadasUsadas());
        orden.setRecibidasnuevas(item.getRecibidasNuevas());
        orden.setRecibidasusadas(item.getRecibidasUsadas());
        orden.setUsuarioCreado(item.getCreador() == null ? "" : item.getCreador().getNombreUsuario());
        orden.setCorreoUsuarioREcibidor(item.getCreador() == null ? "" : item.getCreador().getUsuariodetalle().getCorreo());
        orden.setUsuarioAceptador(item.getAceptador() == null ? "" : item.getAceptador().getNombreUsuario());
        orden.setUsuarioREcibidor(item.getRecibidor() == null ? "" : item.getRecibidor().getNombreUsuario());
        orden.setUsuarioDestructor(item.getDestructor() == null ? "" : item.getDestructor().getNombreUsuario());
        if (orden.getEstado().equals("ENTREGADAS/Pendientes por devolver")) {
            for (Solicitudbarajadetalle detalle : item.getSolicitudbarajadetalleList()) {
                orden.getCantidades().add(new BarajasCantidad(detalle.getInventarobarajas().getId(), transformarBaraja(detalle.getInventarobarajas().getBaraja()), detalle.getCantidad(), detalle.getCantidad(), detalle.getInventarobarajas().getUso(), detalle.getCantidad(), detalle.getCantidad(), detalle.getCantidad(), detalle.getCantidad(), detalle.getInventarobarajas().getCasino().getNombre(), detalle.getCantidad()));
            }
        } else {
            for (Solicitudbarajadetalle detalle : item.getSolicitudbarajadetalleList()) {
                orden.getCantidades().add(new BarajasCantidad(detalle.getInventarobarajas().getId(), transformarBaraja(detalle.getInventarobarajas().getBaraja()), detalle.getCantidad(), detalle.getCantidad(), detalle.getInventarobarajas().getUso(), detalle.getCantidad(), detalle.getCantidad(), detalle.getCantidad(), detalle.getCantidad(), detalle.getInventarobarajas().getCasino().getNombre(), detalle.getDevueltas()));
            }
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
    public MaterialesDTO editMaterial(MaterialesDTO material) {
        return transformarMaterial(GestionBarajasDao.editMaterial(transformarMaterial(material)));
    }

    @Override
    public BarajasDTO addBaraja(BarajasDTO elemento) {
        elemento = transformarBaraja(GestionBarajasDao.addBaraja(transformarBaraja(elemento)));
        return elemento;
    }

    @Override
    public BarajasDTO deleteBaraja(BarajasDTO elemento) {
        GestionBarajasDao.deleteBaraja(transformarBaraja(elemento));
        return elemento;
    }

    @Override
    public InventarioBarajasDTO getInventario(Integer id) {
        return transformarInventario(GestionBarajasDao.getListaInvenratioBarajas(id));
    }

    @Override
    public List<OrdenCompraBarajaDTO> getOrdenesCompra() {
        return transformarOrdenesCompra(GestionBarajasDao.getListaOrdenesCompraBarajas());
    }

    @Override
    public int crearOrdenBarajas(List<InventarioBarajasDTO> inventario, Usuario usuario, String observaciones) {
        Ordencomprabaraja orden = new Ordencomprabaraja();
        orden.setFechaCreacion(new Date());
        orden.setCreador(usuario);
        orden.setEsatdo("GENERADA");
        orden.setObservaciones(observaciones);
        orden = GestionBarajasDao.crearOrdenCompra(orden);
        for (InventarioBarajasDTO inventario1 : inventario) {
            orden.getOrdencomprabarajadetalleList().addAll(getDetallesOrden(inventario1, orden.getId()));
        }
        return GestionBarajasDao.guardarOrdenCompra(orden).getId();

    }

    private List<Ordencomprabarajadetalle> getDetallesOrden(InventarioBarajasDTO inventario, Integer id) {
        List<Ordencomprabarajadetalle> detalles = new ArrayList<Ordencomprabarajadetalle>();
        for (BarajasCantidad barajas : inventario.getInventario()) {
            if (barajas.getCantidad() > 0) {
                detalles.add(getDettaleOrden(barajas, id));
            }
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
        detalle.setDevueltas(barajas.getDevueltas());
        return detalle;
    }

    @Override
    public OrdenCompraBarajaDTO getOrden(Integer idOrden) {
        return transormarOrdenCompra(GestionBarajasDao.getOrdenCompraBaraja(idOrden));
    }

    @Override
    public void aprobarOrden(OrdenCompraBarajaDTO idOrden, Usuario usuario) {
        GestionBarajasDao.aprobarOrden(idOrden, usuario);
    }

    @Override
    public void recibirOrden(Integer idOrden, Usuario usuario) {
        GestionBarajasDao.recibirOrden(idOrden, usuario);
    }

    @Override
    public List<SolicitudBarajasDTO> getSolicitudesBarajas(boolean getTodas, Usuario idUsuario) {
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
        orden.setEstado("SOLICITADA");
        orden = GestionBarajasDao.crearSolicitudBarajas(orden);
        orden.setSolicitudbarajadetalleList(getDetallesSolicitud(inventario, orden.getId()));
        return GestionBarajasDao.guardarSolicitudBarajas(orden).getId();
    }

    @Override
    public SolicitudBarajasDTO getSolicitud(Integer idOrden) {
        return transormarSolicitudBarajas(GestionBarajasDao.getSolicitudBaraja(idOrden));
    }

    @Override
    public void entregarNuevasSolicitud(Integer idOrden, Usuario usuario) {
        GestionBarajasDao.entregarNuevasSolicitud(idOrden, usuario);

    }

    @Override
    public void recibirNuevasSolicitud(Integer idOrden, Usuario usuario) {
        GestionBarajasDao.recibirNuevasSolicitud(idOrden, usuario);

    }

    @Override
    public void entregarUsadasSolicitud(SolicitudBarajasDTO idOrden, Usuario usuario) {
        GestionBarajasDao.entregarUsadasSolicitud(idOrden, usuario);
    }

    @Override
    public void recibirUsadasSolicitud(Integer idOrden, Usuario usuario) {
        GestionBarajasDao.recibirUsadasSolicitud(idOrden, usuario);
    }

    @Override
    public List<InventarioBarajasDTO> getBodegas() {
        return transformarBodegas(GestionBarajasDao.getCasinos());
    }

    @Override
    public Integer crearBodega(int id) {
        return GestionBarajasDao.crearBodegaCasino(id);
    }

    @Override
    public void guardarBodega(InventarioBarajasDTO inventario) {
        GestionBarajasDao.guardarBodegaCasino(inventario);
    }

    @Override
    public List<InventarioBarajasDTO> getBodegas(Usuario usuario) {
        return transformarBodegas(GestionBarajasDao.getBodegasCasinoUsusario(usuario));
    }

    @Override
    public List<InventarioBarajasDTO> getBodegasParaSol(Usuario usuario) {
        return transformarBodegasParaSol(GestionBarajasDao.getBodegasCasinoUsusario(usuario));
    }

    @Override
    public Date getFechaDestruccion(Integer idOrden) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ActaDestruccionDTO> getActasDestruccion() {
        return transformarActasDestruccion(GestionBarajasDao.getActasDestruccion());
    }

    private List<ActaDestruccionDTO> transformarActasDestruccion(List<Destruccionbarajasmaestro> actasDestruccion) {
        List<ActaDestruccionDTO> actas = new ArrayList<ActaDestruccionDTO>();
        for (Destruccionbarajasmaestro actasDestruccion1 : actasDestruccion) {

            actas.add(tranformarActaDestruccion(actasDestruccion1));

        }
        return actas;
    }

    private BarajasCantidad transformarDetalledestruccion(Actasdestruccionbarajas detalle) {
        BarajasCantidad detalled = new BarajasCantidad();
        detalled.setBaraja(transformarBaraja(detalle.getInventario().getBaraja()));
        detalled.setCantidad(detalle.getCantidad());
        return detalled;
    }

    @Override
    public ActaDestruccionDTO getBodegasParaDes(Usuario usuario) {
        ActaDestruccionDTO nueva = new ActaDestruccionDTO();
        nueva.setDetalle(transformarDetalleDestrucinoNuevo(GestionBarajasDao.getBodegasCasinoUsusario(usuario)));
        return nueva;
    }

    @Override
    public ActaDestruccionDTO getBodegasParaDesPorId(Usuario usuario, Integer idOrden) {
        return tranformarActaDestruccion(GestionBarajasDao.getDestruccionMaestro(usuario, idOrden));
    }

    @Override
    public Integer destruir(ActaDestruccionDTO acta, Usuario usuario) {
        return GestionBarajasDao.destruir(acta, usuario);
    }

    private ActaDestruccionDTO tranformarActaDestruccion(Destruccionbarajasmaestro actasDestruccion1) {
        ActaDestruccionDTO acta = new ActaDestruccionDTO();
        if (actasDestruccion1.getId() != null) {
            acta.setId(actasDestruccion1.getId());
        }
        if (actasDestruccion1.getUsuario() != null) {
            acta.setUsuario(actasDestruccion1.getUsuario().getNombreUsuario());
        }
        acta.setFecha(actasDestruccion1.getFechaDestruccion());
        acta.setEstado(actasDestruccion1.getEstado());
        for (Actasdestruccionbarajas detalle : actasDestruccion1.getActasdestruccionbarajasList()) {
            acta.getDetalle().add(transformarDetalledestruccion(detalle));

        }
        return acta;
    }

    private List<BarajasCantidad> transformarDetalleDestrucinoNuevo(List<Casino> bodegas) {
        List<BarajasCantidad> lista = new ArrayList<BarajasCantidad>();
        for (Casino bodega : bodegas) {
            for (Inventarobarajas inventario : bodega.getInventarobarajasList()) {
                if (inventario.getPordestruir() != 0) {
                    lista.add(new BarajasCantidad(inventario.getId(), transformarBaraja(inventario.getBaraja()), inventario.getPordestruir(), Integer.MIN_VALUE, Integer.SIZE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.SIZE, Integer.SIZE, null, 0));
                }
            }
        }
        return lista;
    }

    @Override
    public void crearOrden(OrdenCompraBarajaDTO idOrden, Usuario usuario) {
        GestionBarajasDao.crearOrden(idOrden, usuario);
    }

    @Override
    public OrdenCompraBarajaDTO getOrdenRecibir(Integer idOrden, Usuario usuario) {
        return transormarOrdenCompra(GestionBarajasDao.getOrdenCompraBaraja(idOrden, usuario));
    }

    @Override
    public void recibirOrdenCaja(Integer idOrden, Usuario usuario) {
        GestionBarajasDao.recibirOrdenCaja(idOrden, usuario);
    }

    @Override
    public List<OrdenCompraBarajaDTO> getOrdenesCompra(Usuario usuario) {
        return transformarOrdenesCompra(GestionBarajasDao.getListaOrdenesCompraBarajasUsuario(usuario));
    }

    @Override
    public List<SolicitudesPorMes> getSolicitudesSolicitudesMes(Integer ano, Integer mes, Integer annodesde, Integer mesdesde) {
        List<SolicitudBarajasDTO> solicitudes = transformarSolicitudesBarajas(GestionBarajasDao.getListaSoliciudesBarajas());
        List<SolicitudesPorMes> solicitudesPorMeses = new ArrayList<SolicitudesPorMes>();
        Calendar c = Calendar.getInstance();
        for (SolicitudBarajasDTO solicitud : solicitudes) {
            c.setTime(solicitud.getFechaCreacion());
            if (!solicitudesPorMeses.contains(new SolicitudesPorMes(c.get(Calendar.MONTH), c.get(Calendar.YEAR)))) {
                solicitudesPorMeses.add(new SolicitudesPorMes(c.get(Calendar.MONTH), c.get(Calendar.YEAR)));
            }
            solicitudesPorMeses.get(solicitudesPorMeses.indexOf(new SolicitudesPorMes(c.get(Calendar.MONTH), c.get(Calendar.YEAR)))).getSolicitudes().add(solicitud);
        }
        return solicitudesPorMeses;
    }

    @Override
    public List<DestruccionPorMes> getDestruidasMes(Integer ano, Integer mes, Integer annodesde, Integer mesdesde) {
        List<ActaDestruccionDTO> solicitudes = transformarActasDestruccion(GestionBarajasDao.getDestruccionDesdeHasta(ano, mes, annodesde, mesdesde));
        List<DestruccionPorMes> solicitudesPorMeses = new ArrayList<DestruccionPorMes>();

        Calendar c = Calendar.getInstance();
        for (ActaDestruccionDTO solicitud : solicitudes) {
            c.setTime(solicitud.getFecha());
            if (!solicitudesPorMeses.contains(new DestruccionPorMes(c.get(Calendar.MONTH), c.get(Calendar.YEAR)))) {
                solicitudesPorMeses.add(new DestruccionPorMes(c.get(Calendar.MONTH), c.get(Calendar.YEAR)));
            }
            solicitudesPorMeses.get(solicitudesPorMeses.indexOf(new DestruccionPorMes(c.get(Calendar.MONTH), c.get(Calendar.YEAR)))).getSolicitudes().add(solicitud);
        }
        return solicitudesPorMeses;
    }

    @Override
    public List<TrasladoDTO> getTransferencias() {
        List<Trasladobarajas> traslados = GestionBarajasDao.getListaTraslados();
        List<TrasladoDTO> trasladosDto = new ArrayList<TrasladoDTO>();
        for (Trasladobarajas traslado : traslados) {
            trasladosDto.add(new TrasladoDTO(traslado));
        }
        return trasladosDto;
    }

    @Override
    public List<TrasladoDTO> getTransferencias(Usuario usuario) {
        List<Trasladobarajas> traslados = GestionBarajasDao.getListaTraslados(usuario);
        List<TrasladoDTO> trasladosDto = new ArrayList<TrasladoDTO>();
        for (Trasladobarajas traslado : traslados) {
            trasladosDto.add(new TrasladoDTO(traslado));
        }
        return trasladosDto;
    }

    @Override
    public TrasladoDTO getTransferencia(Integer idOrden) {
        return new TrasladoDTO(GestionBarajasDao.getTraslado(idOrden));
    }

    @Override
    public TrasladoDTO getTransferenciaNueva() {
        return new TrasladoDTO();
    }

    @Override
    public TrasladoDTO getTransferenciaNueva(int casinoenviador, int casinoreceptor) {
        Casino casinoenv = GestionBarajasDao.getListaInvenratioBarajas(casinoenviador);
        Casino casinorec = GestionBarajasDao.getListaInvenratioBarajas(casinoreceptor);
        return new TrasladoDTO(casinoenv, casinorec);
    }

    @Override
    public Integer guardarTransferencia(TrasladoDTO item) {
        return GestionBarajasDao.saveTraslado(item);
    }

    @Override
    public Integer enviarTransferencia(TrasladoDTO item) {
        return GestionBarajasDao.sendTraslado(item);
    }

    @Override
    public Integer recibirTransferencia(TrasladoDTO item) {
        return GestionBarajasDao.receiveTraslado(item);
    }

    @Override
    public List<ConsumoBarajas> getConsumoPorMEs(List<CasinoBoolean> casinos, Integer ano, Integer mes, Integer annodesde, Integer mesdesde) {
        List<Solicitudbarajadetalle> lista;
        lista = GestionBarajasDao.getListaSoliciudesBarajasRepo(casinos, ano, mes, annodesde, mesdesde);
        Calendar c = Calendar.getInstance();
        List<ConsumoBarajas> consumo = new ArrayList<ConsumoBarajas>();
        for (Solicitudbarajadetalle bono : lista) {
            c.setTime(bono.getSolicitudbarajas().getFechacreacion());
            ConsumoBarajas b = new ConsumoBarajas(new BarajasDTO(bono.getInventarobarajas().getBaraja()));
            if (!consumo.contains(b)) {
                consumo.add(b);

            }
                consumo.get(consumo.indexOf(b)).sumarCantidad(c.get(Calendar.MONTH), c.get(Calendar.YEAR), bono.getCantidad());
            

        }
        return consumo;
    }

}
