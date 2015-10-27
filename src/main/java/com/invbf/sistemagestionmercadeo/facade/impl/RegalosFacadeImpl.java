/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.facade.impl;

import com.invbf.sistemagestionmercadeo.dao.ClienteDao;
import com.invbf.sistemagestionmercadeo.dao.ConfiguracionDao;
import com.invbf.sistemagestionmercadeo.dao.GestionRegaloDao;
import com.invbf.sistemagestionmercadeo.dto.CasinoDto;
import com.invbf.sistemagestionmercadeo.dto.CategoriaDTO;
import com.invbf.sistemagestionmercadeo.dto.ClienteRegaloDTO;
import com.invbf.sistemagestionmercadeo.dto.InventarioRegalosDTO;
import com.invbf.sistemagestionmercadeo.dto.OrdenCompraRegaloDTO;
import com.invbf.sistemagestionmercadeo.dto.RegaloCanje;
import com.invbf.sistemagestionmercadeo.dto.RegaloDTO;
import com.invbf.sistemagestionmercadeo.dto.RegalosCantidadDTO;
import com.invbf.sistemagestionmercadeo.dto.ReporteGestionEntregaRegalos;
import com.invbf.sistemagestionmercadeo.dto.SolicitudRegaloDTO;
import com.invbf.sistemagestionmercadeo.entity.Categoria;
import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Ordencompraregalodetalle;
import com.invbf.sistemagestionmercadeo.entity.Ordencompraregalos;
import com.invbf.sistemagestionmercadeo.entity.Regalos;
import com.invbf.sistemagestionmercadeo.entity.Regalosinventario;
import com.invbf.sistemagestionmercadeo.entity.Solicitudregalodetalle;
import com.invbf.sistemagestionmercadeo.entity.Solicitudregalos;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.facade.RegalosFacade;
import com.invbf.sistemagestionmercadeo.util.CasinoBoolean;
import com.invbf.sistemagestionmercadeo.util.CategoriaBoolean;
import com.invbf.sistemagestionmercadeo.util.ClienteDTO;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author ivan
 */
public class RegalosFacadeImpl implements RegalosFacade, Serializable {

    @Override
    public List<CategoriaDTO> getListaCategorias() {
        List<Categoria> categorias = GestionRegaloDao.getListaCategorias();
        List<CategoriaDTO> categoriasDto = new ArrayList<CategoriaDTO>();
        for (Categoria categoria : categorias) {
            categoriasDto.add(new CategoriaDTO(categoria.getIdCategorias(), categoria.getNombre()));
        }
        return categoriasDto;
    }

    @Override
    public List<RegaloDTO> getListaRegalos() {
        List<Regalos> regalos = GestionRegaloDao.getListaRegalos();
        List<RegaloDTO> regaloDTOs = new ArrayList<RegaloDTO>();
        for (Regalos regalo : regalos) {
            regaloDTOs.add(new RegaloDTO(regalo));
        }
        System.out.println(regaloDTOs);
        return regaloDTOs;
    }

    @Override
    public RegaloDTO deleteRegalo(RegaloDTO elemento) {
        return new RegaloDTO(GestionRegaloDao.removeRegalo(elemento.getRegalo()));
    }

    @Override
    public RegaloDTO addRegalo(RegaloDTO elemento) {
        return new RegaloDTO(GestionRegaloDao.addRegalo(elemento.getRegalo()));
    }

    @Override
    public InventarioRegalosDTO getInventario() {
        return new InventarioRegalosDTO(GestionRegaloDao.getListaInvenratioRegalos());
    }

    @Override
    public void guardarInventario(InventarioRegalosDTO inventario) {
        GestionRegaloDao.saveInvenratioRegalos(inventario);
    }

    @Override
    public List<OrdenCompraRegaloDTO> getOrdenesCompra() {
        return transformarOrdenesComra(GestionRegaloDao.getListaOrdenesCompraRegalo());
    }

    @Override
    public List<OrdenCompraRegaloDTO> getOrdenesCompra(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public OrdenCompraRegaloDTO transformarOrdenCompra(Ordencompraregalos orden) {
        OrdenCompraRegaloDTO ordendto = new OrdenCompraRegaloDTO();
        ordendto.setId(orden.getId());
        ordendto.setEstado(orden.getEstado());
        ordendto.setFechaAceptada(orden.getFechaAceptada());
        ordendto.setFechaCreacion(orden.getFechaCreacion());
        ordendto.setFechaRecibida(orden.getFechaRecibida());
        ordendto.setFechaIngresada(orden.getFechaIngresada());
        ordendto.setUsuarioAceptador(orden.getAceptador() == null ? "" : orden.getAceptador().getNombreUsuario());
        ordendto.setUsuarioCreado(orden.getCreador().getNombreUsuario());
        ordendto.setUsuarioREcibidor(orden.getRecibidor() == null ? "" : orden.getRecibidor().getNombreUsuario());
        ordendto.setUsuarioIngresador(orden.getIngresador() == null ? "" : orden.getIngresador().getNombreUsuario());
        ordendto.setObservaciones(orden.getObservaciones());
        ordendto.setCantidades(new ArrayList<RegalosCantidadDTO>());

        for (Ordencompraregalodetalle detalle : orden.getOrdencompraregalodetalleList()) {
            ordendto.getCantidades().add(transformarDetalleOrdenDTO(detalle));

        }
        return ordendto;
    }

    private RegalosCantidadDTO transformarDetalleOrdenDTO(Ordencompraregalodetalle detalle) {
        RegalosCantidadDTO detalledto = new RegalosCantidadDTO();
        detalledto.setId(detalle.getRegalosinventario().getId());
        detalledto.setCantidad(detalle.getCantidad());
        detalledto.setCantidadAprobada(detalle.getCantidadAprobada());
        detalledto.setCantidadR(detalle.getCantidadRecibida());
        detalledto.setRegalo(new RegaloDTO(detalle.getRegalosinventario().getRegalo()));
        detalledto.setJustificacion(detalle.getJustificacion());
        return detalledto;
    }

    public List<OrdenCompraRegaloDTO> transformarOrdenesComra(List<Ordencompraregalos> ordenes) {
        List<OrdenCompraRegaloDTO> ordenesDTo = new ArrayList<OrdenCompraRegaloDTO>();
        for (Ordencompraregalos orden : ordenes) {
            ordenesDTo.add(transformarOrdenCompra(orden));
        }

        return ordenesDTo;

    }

    @Override
    public int generarOrdenRegalos(InventarioRegalosDTO inventario, String observaciones, Usuario usuairo) {
        Ordencompraregalos orden = new Ordencompraregalos();
        orden.setCreador(usuairo);
        orden.setFechaCreacion(new Date());
        orden.setEstado("GENERADO");
        orden.setObservaciones(observaciones);
        orden.setOrdencompraregalodetalleList(new ArrayList<Ordencompraregalodetalle>());
        for (RegalosCantidadDTO regalo : inventario.getInventario()) {
            if (regalo.getCantidad() > 0) {
                Ordencompraregalodetalle detalle = new Ordencompraregalodetalle();
                detalle.setCantidad(regalo.getCantidad());
                detalle.setCantidadAprobada(regalo.getCantidad());
                detalle.setCantidadRecibida(regalo.getCantidad());
                detalle.setRegalosinventario(new Regalosinventario(regalo.getId()));
                orden.getOrdencompraregalodetalleList().add(detalle);
            }
        }
        return GestionRegaloDao.guardarOrdenCompra(orden).getId();
    }

    @Override
    public OrdenCompraRegaloDTO getOrden(Integer idOrden) {
        return transformarOrdenCompra(GestionRegaloDao.getOrdenCompra(idOrden));
    }

    @Override
    public void rechazarOrden(OrdenCompraRegaloDTO orden, Usuario usuario) {

        GestionRegaloDao.rechazarOrden(orden, usuario);
    }

    @Override
    public void aprobarOrden(OrdenCompraRegaloDTO orden, Usuario usuario) {
        GestionRegaloDao.aprobarOrden(orden, usuario);
    }

    @Override
    public void recibirOrden(OrdenCompraRegaloDTO idOrden, Usuario usuario) {
        GestionRegaloDao.recibirOrden(idOrden, usuario);
    }

    @Override
    public void crearOrden(OrdenCompraRegaloDTO orden, Usuario usuario) {
        GestionRegaloDao.crearOrden(orden, usuario);
    }

    @Override
    public List<SolicitudRegaloDTO> getSolicitudesRegalos(boolean perfilViewMatch, Usuario usuario) {
        return transformarSolicitudesRegalos(GestionRegaloDao.getListaSoliciudesRegalo(perfilViewMatch, usuario));
    }

    private List<SolicitudRegaloDTO> transformarSolicitudesRegalos(List<Solicitudregalos> listaSoliciudesRegalo) {
        List<SolicitudRegaloDTO> solicitudes = new ArrayList<SolicitudRegaloDTO>();
        for (Solicitudregalos solicitud : listaSoliciudesRegalo) {
            solicitudes.add(transformarSolicitudRegalo(solicitud));
        }
        return solicitudes;
    }

    private SolicitudRegaloDTO transformarSolicitudRegalo(Solicitudregalos solicitud) {
        SolicitudRegaloDTO solicitudDto = new SolicitudRegaloDTO();
        solicitudDto.setId(solicitud.getId());
        solicitudDto.setCasino(new CasinoDto(solicitud.getSala()));
        solicitudDto.setEstado(solicitud.getEstado());
        solicitudDto.setFechaAceptada(solicitud.getFechaaprobacion());
        solicitudDto.setFechaCreacion(solicitud.getFechacreacion());
        solicitudDto.setFechaEnviada(solicitud.getFechentrega());
        solicitudDto.setFechaRecibida(solicitud.getFecharecepcion());
        solicitudDto.setUsuarioAceptador(solicitud.getAceptador() == null ? "" : solicitud.getAceptador().getNombreUsuario());
        solicitudDto.setUsuarioCreado(solicitud.getCreador() == null ? "" : solicitud.getCreador().getNombreUsuario());
        solicitudDto.setUsuarioEnviador(solicitud.getEnviador() == null ? "" : solicitud.getEnviador().getNombreUsuario());
        solicitudDto.setUsuarioREcibidor(solicitud.getRecibidor() == null ? "" : solicitud.getRecibidor().getNombreUsuario());
        solicitudDto.setCantidades(new ArrayList<RegalosCantidadDTO>());
        for (Solicitudregalodetalle detalle : solicitud.getSolicitudregalodetalleList()) {
            RegalosCantidadDTO detaleDto = new RegalosCantidadDTO(detalle.getSolicitudregalodetallePK().getInventario(),
                    new RegaloDTO(detalle.getRegalosinventario().getRegalo()), 0, 0, 0, 0);
            detaleDto.setCliente(new ClienteDTO(detalle.getClientes()));
            System.out.println(detaleDto.getCliente().getApellidos());
            detaleDto.setEstado(detalle.getEstado());
            solicitudDto.getCantidades().add(detaleDto);

        }
        return solicitudDto;
    }

    @Override
    public List<ClienteRegaloDTO> findAllClientesCasinos(CasinoDto casino, List<CategoriaBoolean> categoriaBooleans, Integer mes, String sexo) {
        List<Cliente> clientes = ClienteDao.findByIdCasinoAndCat(casino.getIdCasino(), categoriaBooleans, mes, sexo);
        List<ClienteRegaloDTO> clientesDto = new ArrayList<ClienteRegaloDTO>();
        for (Cliente cliente : clientes) {
            clientesDto.add(new ClienteRegaloDTO(cliente.getIdCliente(), cliente.getNombres() + " " + cliente.getApellidos(), cliente.getIdCategorias().getNombre(), cliente.getGenero(), cliente.getCumpleanos()));
        }
        return clientesDto;
    }

    @Override
    public InventarioRegalosDTO getInventarioParaSolicitud() {
        return new InventarioRegalosDTO(GestionRegaloDao.getListaInvenratioRegalos(), 1);
    }

    @Override
    public void generarSolicitudRegalos(CasinoDto casino, List<ClienteRegaloDTO> lista, Usuario usuario) {
        GestionRegaloDao.generarSolicitudRegalo(casino, lista, usuario);
    }

    @Override
    public SolicitudRegaloDTO getSolicitud(Integer idOrden) {
        return transformarSolicitudRegalo(GestionRegaloDao.getSolicitud(idOrden));
    }

    @Override
    public void aprobarSolicitud(SolicitudRegaloDTO solicitud, Usuario usuario) {
        GestionRegaloDao.aprobarSolicitud(solicitud, usuario);
    }

    @Override
    public void rechazarSolicitud(SolicitudRegaloDTO solicitud, Usuario usuario) {
        GestionRegaloDao.rechazarSolicitud(solicitud, usuario);
    }

    @Override
    public void recibirSolicitud(SolicitudRegaloDTO solicitud, Usuario usuario) {
        GestionRegaloDao.recibirSolicitud(solicitud.getId(), usuario);
    }

    @Override
    public void enviarSolicitud(SolicitudRegaloDTO solicitud, Usuario usuario) {
        GestionRegaloDao.entregarSolicitud(solicitud.getId(), usuario);
    }

    @Override
    public List<RegaloCanje> buscarRegalos(String buscar, Usuario usuario) {
        List<Solicitudregalodetalle> soldet = GestionRegaloDao.buscar(buscar, usuario);

        System.out.println("Tamaño " + soldet.size());
        List<RegaloCanje> regalos = new ArrayList<RegaloCanje>();
        for (Solicitudregalodetalle soldet1 : soldet) {
            RegaloCanje regalo = new RegaloCanje(new RegaloDTO(soldet1.getRegalosinventario().getRegalo()),
                    new ClienteDTO(soldet1.getClientes()), soldet1.getSolicitudregalos().getId());
            regalo.getRegalo().setId(soldet1.getRegalosinventario().getId());
            regalos.add(regalo);
        }

        return regalos;
    }

    @Override
    public void guardarImagen(byte[] contents, String fileName) {
        FTPClient client = new FTPClient();
        try {
            System.out.println("entra");
            String sFTP = ConfiguracionDao.findByNombre("FTP").getValor();
            String sUser = "regalos@ibfcolombia.com";
            String sPassword = "regalos";

            client.connect(sFTP);
            boolean login = client.login(sUser, sPassword);

            int reply = client.getReplyCode();

            System.out.println("Respuesta recibida de conexión FTP:" + reply);

            if (FTPReply.isPositiveCompletion(reply)) {
                System.out.println("Conectado Satisfactoriamente");
            } else {
                System.out.println("Imposible conectarse al servidor");
            }
            System.out.println(client.printWorkingDirectory());
            client.changeWorkingDirectory("/home/easl4284/public_html/regalos");
            System.out.println(client.printWorkingDirectory());
            client.setFileType(FTP.BINARY_FILE_TYPE);

            client.deleteFile(fileName);

            BufferedInputStream buffIn = null;
            buffIn = new BufferedInputStream(new ByteArrayInputStream(contents));//Ruta del archivo para enviar
            client.enterLocalPassiveMode();
            client.storeFile(fileName, buffIn);//Ruta completa de alojamiento en el FTP

            buffIn.close(); //Cerrar envio de arcivos al FTP

        } catch (FileNotFoundException ex) {
            System.out.println("filenotfound");
        } catch (IOException ex) {
            System.out.println("error");
        } finally {
            try {
                client.logout();
                client.disconnect();
            } catch (IOException ex) {
            }
        }
    }

    @Override
    public void entregar(RegaloCanje regalo, Usuario usuario) {
        GestionRegaloDao.entregarRegalo(regalo, usuario);
    }

    @Override
    public InventarioRegalosDTO getInventarioRequerimiento() {
        return new InventarioRegalosDTO(GestionRegaloDao.getListaInvenratioRegalos(), 2);
    }

    @Override
    public void ingresarOrden(OrdenCompraRegaloDTO orden, Usuario usuario) {
        GestionRegaloDao.ingresarOrden(orden, usuario);
    }

    @Override
    public ReporteGestionEntregaRegalos getReporte(List<CasinoBoolean> casinos, Integer ano, Integer mes, List<CategoriaBoolean> categorias, String sexo) {
        List<Solicitudregalodetalle> detalles = GestionRegaloDao.getReporte(casinos, ano, mes);
        Calendar fecha = Calendar.getInstance();
        fecha.set(Calendar.MONTH, mes);
        fecha.set(Calendar.YEAR, ano);
        fecha.set(Calendar.DAY_OF_MONTH, 1);
        ReporteGestionEntregaRegalos reporte = new ReporteGestionEntregaRegalos();
        for (Solicitudregalodetalle detalle : detalles) {
            if(!sexo.equals("")&&!detalle.getClientes().getGenero().equals(sexo)){
                continue;
            }
            if(!categorias.contains(new CategoriaBoolean(detalle.getClientes().getIdCategorias(), true))){
                continue;
            }
            if (!detalle.getEstado().equals("POR APROBAR")) {
                Calendar fechames = Calendar.getInstance();
                fechames.setTime(detalle.getSolicitudregalos().getFecharecepcion());
                if (fecha.get(Calendar.MONTH) == fechames.get(Calendar.MONTH)
                        && fecha.get(Calendar.YEAR) == fechames.get(Calendar.YEAR)) {
                    reporte.sumarObsequiosRecibidos();
                    
                }

                if (detalle.getFechaEntrega() != null) {
                    Calendar fechaent = Calendar.getInstance();
                    fechaent.setTime(detalle.getFechaEntrega());

                    if (fecha.get(Calendar.MONTH) == fechaent.get(Calendar.MONTH)
                            && fecha.get(Calendar.YEAR) == fechaent.get(Calendar.YEAR)) {
                        reporte.sumarEntregados();
                    }
                    if (fecha.after(fechames) && ((fecha.get(Calendar.YEAR) < fechaent.get(Calendar.YEAR)) || ((fecha.get(Calendar.YEAR) == fechaent.get(Calendar.YEAR)) && (fecha.get(Calendar.MONTH) < fechaent.get(Calendar.MONTH))))) {
                        reporte.sumarPendientes();
                    }
                } else {
                    if (fecha.after(fechames) && (!detalle.getEstado().equals("ENTREGADO"))) {
                        reporte.sumarPendientes();
                    }
                }
            }
        }
        return reporte;
    }

}
