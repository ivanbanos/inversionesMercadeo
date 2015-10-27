/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.facade;

import com.invbf.sistemagestionmercadeo.dto.ActaDestruccionDTO;
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
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.util.CasinoBoolean;
import java.util.Date;
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

    public int crearOrdenBarajas(List<InventarioBarajasDTO> inventari, Usuario usuario, String observaciones);

    public OrdenCompraBarajaDTO getOrden(Integer idOrden);

    public void aprobarOrden(OrdenCompraBarajaDTO idOrden, Usuario usuario);

    public void recibirOrden(Integer idOrden, Usuario usuario);

    public List<SolicitudBarajasDTO> getSolicitudesBarajas(boolean todas, Usuario idUsuario);

    public int crearSolicitudBarajas(InventarioBarajasDTO inventario, Usuario usuario);

    public SolicitudBarajasDTO getSolicitud(Integer idOrden);

    public void entregarNuevasSolicitud(Integer idOrden, Usuario usuario);

    public void recibirNuevasSolicitud(Integer idOrden, Usuario usuario);

    public void entregarUsadasSolicitud(SolicitudBarajasDTO idOrden, Usuario usuario);

    public void recibirUsadasSolicitud(Integer idOrden, Usuario usuario);

    public List<InventarioBarajasDTO> getBodegas();

    public Integer crearBodega(int id);

    public void guardarBodega(InventarioBarajasDTO inventario);

    public List<InventarioBarajasDTO> getBodegas(Usuario usuario);

    public List<InventarioBarajasDTO> getBodegasParaSol(Usuario usuario);

    public ActaDestruccionDTO getBodegasParaDes(Usuario usuario);

    public ActaDestruccionDTO getBodegasParaDesPorId(Usuario usuario, Integer idOrden);

    public Integer destruir(ActaDestruccionDTO acta, Usuario usuario);

    public Date getFechaDestruccion(Integer idOrden);

    public List<ActaDestruccionDTO> getActasDestruccion(Usuario usuario);

    public MaterialesDTO editMaterial(MaterialesDTO material);

    public void crearOrden(OrdenCompraBarajaDTO idOrden, Usuario usuario);

    public OrdenCompraBarajaDTO getOrdenRecibir(Integer idOrden, Usuario usuario);

    public void recibirOrdenCaja(OrdenCompraBarajaDTO idOrden, Usuario usuario);

    public List<OrdenCompraBarajaDTO> getOrdenesCompra(Usuario usuario);

    public List<SolicitudesPorMes> getSolicitudesSolicitudesMes(Integer ano, Integer mes, Integer annodesde, Integer mesdesde);

    public List<DestruccionPorMes> getDestruidasMes(Integer ano, Integer mes, Integer annodesde, Integer mesdesde);

    public List<TrasladoDTO> getTransferencias();

    public List<TrasladoDTO> getTransferencias(Usuario usuario);

    public TrasladoDTO getTransferencia(Integer idOrden);

    public TrasladoDTO getTransferenciaNueva();

    public TrasladoDTO getTransferenciaNueva(int casinoenviador, int casinoreceptor);

    public Integer guardarTransferencia(TrasladoDTO item);

    public Integer enviarTransferencia(TrasladoDTO item);

    public Integer recibirTransferencia(TrasladoDTO item);

    public List<ConsumoBarajas> getConsumoPorMEs(List<CasinoBoolean> casinos, Integer ano, Integer mes, Integer annodesde, Integer mesdesde);

    public void rechazarOrden(OrdenCompraBarajaDTO orden, Usuario usuario);

    public void ingresarOrdenCaja(Integer idOrden, Usuario usuario);

}
