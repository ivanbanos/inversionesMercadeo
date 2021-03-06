/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.facade.impl;

import com.invbf.sistemagestionmercadeo.dao.AccionDao;
import com.invbf.sistemagestionmercadeo.dao.AreaDao;
import com.invbf.sistemagestionmercadeo.dao.AtributoDao;
import com.invbf.sistemagestionmercadeo.dao.BonoDao;
import com.invbf.sistemagestionmercadeo.dao.BonosnofisicosDao;
import com.invbf.sistemagestionmercadeo.dao.BonosnoincluidosDao;
import com.invbf.sistemagestionmercadeo.dao.CasinoDao;
import com.invbf.sistemagestionmercadeo.dao.CategoriaDao;
import com.invbf.sistemagestionmercadeo.dao.ClienteDao;
import com.invbf.sistemagestionmercadeo.dao.ClienteatributoDao;
import com.invbf.sistemagestionmercadeo.dao.ConfiguracionDao;
import com.invbf.sistemagestionmercadeo.dao.ControlsalidabonosDao;
import com.invbf.sistemagestionmercadeo.dao.DetalleCasinoDao;
import com.invbf.sistemagestionmercadeo.dao.EventoDao;
import com.invbf.sistemagestionmercadeo.dao.LotebonoDao;
import com.invbf.sistemagestionmercadeo.dao.SolicitudEntregaClientesDao;
import com.invbf.sistemagestionmercadeo.dao.SolicitudEntregaDao;
import com.invbf.sistemagestionmercadeo.dao.SolicitudentregalotesDao;
import com.invbf.sistemagestionmercadeo.dao.SolicitudentregalotesmaestroDao;
import com.invbf.sistemagestionmercadeo.dao.TareasDao;
import com.invbf.sistemagestionmercadeo.dao.TipoDocumentoDao;
import com.invbf.sistemagestionmercadeo.dao.TipoJuegoDao;
import com.invbf.sistemagestionmercadeo.dao.TipostareasDao;
import com.invbf.sistemagestionmercadeo.dao.UsuarioDao;
import com.invbf.sistemagestionmercadeo.dao.VistaDao;
import com.invbf.sistemagestionmercadeo.dto.BonosAprobadosCanjeados;
import com.invbf.sistemagestionmercadeo.dto.BonosCantidadMes;
import com.invbf.sistemagestionmercadeo.entity.Accion;
import com.invbf.sistemagestionmercadeo.entity.Area;
import com.invbf.sistemagestionmercadeo.entity.Atributo;
import com.invbf.sistemagestionmercadeo.entity.Bono;
import com.invbf.sistemagestionmercadeo.entity.Bononofisico;
import com.invbf.sistemagestionmercadeo.entity.Bononoincluido;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Casinodetalle;
import com.invbf.sistemagestionmercadeo.entity.Categoria;
import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Clienteatributo;
import com.invbf.sistemagestionmercadeo.entity.Controlsalidabono;
import com.invbf.sistemagestionmercadeo.entity.ControlsalidabonosHasLotesbonos;
import com.invbf.sistemagestionmercadeo.entity.Denominacion;
import com.invbf.sistemagestionmercadeo.entity.Evento;
import com.invbf.sistemagestionmercadeo.entity.Listasclientestareas;
import com.invbf.sistemagestionmercadeo.entity.ListasclientestareasPK;
import com.invbf.sistemagestionmercadeo.entity.Lotebono;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentrega;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregacliente;
import com.invbf.sistemagestionmercadeo.entity.SolicitudentregaclientePK;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregalote;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregalotesmaestro;
import com.invbf.sistemagestionmercadeo.entity.Tarea;
import com.invbf.sistemagestionmercadeo.entity.Tipobono;
import com.invbf.sistemagestionmercadeo.entity.Tipodocumento;
import com.invbf.sistemagestionmercadeo.entity.Tipojuego;
import com.invbf.sistemagestionmercadeo.entity.Tipotarea;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.entity.Vista;
import com.invbf.sistemagestionmercadeo.exceptions.CasinoHaveSolicitudCreadaException;
import com.invbf.sistemagestionmercadeo.exceptions.ExistenBonosFisicosException;
import com.invbf.sistemagestionmercadeo.exceptions.LoteBonosExistenteException;
import com.invbf.sistemagestionmercadeo.facade.MarketingUserFacade;
import com.invbf.sistemagestionmercadeo.util.CasinoBoolean;
import com.invbf.sistemagestionmercadeo.util.CategoriaBoolean;
import com.invbf.sistemagestionmercadeo.util.ClienteDTO;
import com.invbf.sistemagestionmercadeo.util.DBConnection;
import com.invbf.sistemagestionmercadeo.util.Notificador;
import com.invbf.sistemagestionmercadeo.util.PropositosBoolean;
import com.invbf.sistemagestionmercadeo.util.TipoBonoBoolean;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author ideacentre
 */
public class MarketingUserFacadeImpl implements MarketingUserFacade, Serializable {

    @Override
    public List<Cliente> findAllClientes() {
        return ClienteDao.findAll();
    }

    @Override
    public List<Categoria> findAllCategorias() {
        return CategoriaDao.findAll();
    }

    @Override
    public void deleteCategorias(Categoria elemento) {
        CategoriaDao.remove(elemento);
    }

    @Override
    public boolean guardarCategorias(Categoria elemento) {
        if (elemento.getIdCategorias() == null) {
            CategoriaDao.create(elemento);
            return false;
        } else {
            CategoriaDao.edit(elemento);
            return true;
        }

    }

    @Override
    public List<Atributo> findAllAtributos() {
        return AtributoDao.findAll();
    }

    @Override
    public void deleteAtributos(Atributo elemento) {
        List<Clienteatributo> listaca = ClienteatributoDao.findByAtributo(elemento);
        List<Cliente> clientes = ClienteDao.findAll();
        for (Cliente c : clientes) {
            c.getClienteatributoList().remove(new Clienteatributo(c.getIdCliente(), elemento.getIdAtributo()));
        }

        if (listaca != null) {
            for (Clienteatributo ca : listaca) {

                ClienteatributoDao.remove(ca);
            }
        }
        AtributoDao.remove(elemento);
    }

    @Override
    public boolean guardarAtributos(Atributo elemento) {
        if (elemento.getIdAtributo() == null) {
            AtributoDao.create(elemento);
            List<Cliente> clientes = ClienteDao.findAll();
            List<Clienteatributo> clientesatributos = new ArrayList<Clienteatributo>();
            for (Cliente c : clientes) {
                Clienteatributo ca = new Clienteatributo(c.getIdCliente(), elemento.getIdAtributo());
                ca.setAtributo(elemento);
                ca.setCliente(c);
                ClienteatributoDao.create(ca);
                clientesatributos.add(ca);
            }
            elemento.setClienteatributoList(clientesatributos);
            AtributoDao.edit(elemento);
            return false;
        } else {
            AtributoDao.edit(elemento);
            return true;
        }
    }

    @Override
    public void deleteTiposjuegos(Tipojuego elemento) {
        TipoJuegoDao.remove(elemento);
    }

    @Override
    public List<Tipojuego> findAllTiposjuegos() {
        return TipoJuegoDao.findAll();
    }

    @Override
    public boolean guardarTiposjuegos(Tipojuego elemento) {
        if (elemento.getIdTipoJuego() == null) {
            TipoJuegoDao.create(elemento);
            return true;
        } else {
            TipoJuegoDao.edit(elemento);
            return true;
        }

    }

    @Override
    public void deleteCasinos(Casino elemento) {
        DetalleCasinoDao.remove(elemento.getCasinodetalle());
        CasinoDao.remove(elemento);
    }

    @Override
    public Casino guardarCasinos(Casino elemento) {
        if (elemento.getIdCasino() == null) {
            Casinodetalle detalle = elemento.getCasinodetalle();
            elemento.setCasinodetalle(null);
            CasinoDao.create(elemento);
            detalle.setIdCasino(elemento.getIdCasino());
            DetalleCasinoDao.create(detalle);
            elemento.setCasinodetalle(detalle);
            CasinoDao.edit(elemento);
            return elemento;
        } else {
            DetalleCasinoDao.edit(elemento.getCasinodetalle());
            CasinoDao.edit(elemento);
            return elemento;
        }
    }

    @Override
    public List<Casino> findAllCasinos() {
        return CasinoDao.findAll();
    }

    @Override
    public List<Evento> findAllEventos() {
        return EventoDao.findAll();
    }

    @Override
    public void deleteEventos(Evento elemento) {
        if (elemento.getImagen() != null && !elemento.getImagen().equals("")) {
            FTPClient client = new FTPClient();
            try {
                System.out.println("entra");
                String sFTP = ConfiguracionDao.findByNombre("FTP").getValor();
                String sUser = ConfiguracionDao.findByNombre("FTPuser").getValor();
                String sPassword = ConfiguracionDao.findByNombre("FTPpassword").getValor();

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
                client.changeWorkingDirectory("/home/easl4284/public_html/imagenes");
                System.out.println(client.printWorkingDirectory());
                client.setFileType(FTP.BINARY_FILE_TYPE);

                client.deleteFile(elemento.getImagen());
            } catch (IOException ex) {
            } finally {
                try {
                    client.logout();
                    client.disconnect();
                } catch (IOException ex) {
                }
            }
        }
        EventoDao.remove(elemento);
    }

    @Override
    public void deleteClientes(Integer elemento) {
        List<Clienteatributo> listaca = ClienteatributoDao.findByCliente(new Cliente(elemento));
        if (listaca != null) {
            for (Clienteatributo ca : listaca) {
                ClienteatributoDao.remove(ca);
            }
        }
        ClienteDao.remove(new Cliente(elemento));
    }

    @Override
    public Cliente guardarClientes(Cliente elemento) {
        if (elemento.getIdCliente() == null) {
            ClienteDao.create(elemento);
            return elemento;
        } else {
            ClienteDao.edit(elemento);
            return elemento;
        }
    }

    @Override
    public List<Tipojuego> getTiposJuegosNoClientes(Integer idCliente) {
        Cliente cliente = ClienteDao.find(idCliente);
        List<Tipojuego> tiposjuego = TipoJuegoDao.findAll();
        Iterator<Tipojuego> iter = tiposjuego.iterator();
        while (iter.hasNext()) {
            if (iter.next().getClienteList().contains(cliente)) {
                iter.remove();
            }
        }
        return tiposjuego;
    }

    @Override
    public Cliente findCliente(Integer integer) {
        return ClienteDao.find(integer);
    }

    @Override
    public Evento findEvento(Integer integer) {
        return EventoDao.find(integer);
    }

    @Override
    public Evento guardarEventos(Evento elemento) {
        if (elemento.getIdEvento() == null) {

            EventoDao.create(elemento);
            return elemento;
        } else {
            EventoDao.edit(elemento);
            return elemento;
        }
    }

    @Override
    public void guardarImagen(byte[] contents, String fileName) {
        FTPClient client = new FTPClient();
        try {
            System.out.println("entra");
            String sFTP = ConfiguracionDao.findByNombre("FTP").getValor();
            String sUser = ConfiguracionDao.findByNombre("FTPuser").getValor();
            String sPassword = ConfiguracionDao.findByNombre("FTPpassword").getValor();

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
            client.changeWorkingDirectory("/home/easl4284/public_html/imagenes");
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
    public List<Accion> findAllAcciones() {
        return AccionDao.findAll();
    }

    @Override
    public void deleteAccion(Accion elemento) {
        AccionDao.remove(elemento);
    }

    @Override
    public boolean guardarAccion(Accion elemento) {
        if (elemento.getIdAccion() == null) {

            AccionDao.create(elemento);
            return false;
        } else {
            AccionDao.edit(elemento);
            return true;
        }
    }

    @Override
    public List<Tipotarea> findAllTipotarea() {
        return TipostareasDao.findAll();
    }

    @Override
    public boolean guardarTipotarea(Tipotarea elemento) {
        if (elemento.getIdTipotarea() == null) {

            TipostareasDao.create(elemento);
            for (Accion a : elemento.getAccionList()) {
                a = AccionDao.find(a.getIdAccion());
                if (a.getTipotareaList() == null) {
                    a.setTipotareaList(new ArrayList<Tipotarea>());
                }
                a.getTipotareaList().add(elemento);
                AccionDao.edit(a);
            }
            return false;
        } else {
            TipostareasDao.edit(elemento);
            for (Accion a : elemento.getAccionList()) {
                a = AccionDao.find(a.getIdAccion());
                if (a.getTipotareaList() == null) {
                    a.setTipotareaList(new ArrayList<Tipotarea>());
                }
                a.getTipotareaList().add(elemento);
                AccionDao.edit(a);
            }
            return true;
        }
    }

    @Override
    public void deleteTipotarea(Tipotarea elemento) {
        TipostareasDao.remove(elemento);
    }

    @Override
    public void deleteTarea(Tarea tarea) {
        List<Usuario> usuarios = UsuarioDao.findAll();
        for (Usuario u : usuarios) {
            if (u.getTareaList().contains(tarea)) {
                u.getTareaList().remove(tarea);
                UsuarioDao.edit(u);
            }
        }
        TareasDao.remove(tarea);
    }

    @Override
    public Tarea guardarTarea(Tarea elemento) {
        List<Listasclientestareas> lcts = elemento.getListasclientestareasList();

        elemento.setListasclientestareasList(null);
        elemento = TareasDao.create(elemento);
        System.out.println(elemento == null);
        for (Listasclientestareas lct : lcts) {
            lct.setListasclientestareasPK(new ListasclientestareasPK(elemento.getIdTarea(), lct.getCliente().getIdCliente()));
        }
        elemento.setListasclientestareasList(lcts);
        for (int i = 0; i < elemento.getUsuarioList().size(); i++) {
            Usuario u = UsuarioDao.find(elemento.getUsuarioList().get(i).getIdUsuario());
            elemento.getUsuarioList().remove(i);
            if (u.getTareaList() == null) {
                u.setTareaList(new ArrayList<Tarea>());
            }
            u.getTareaList().add(elemento);
            elemento.getUsuarioList().add(i, u);
            UsuarioDao.edit(u);
        }
        TareasDao.edit(elemento);
        return elemento;
    }

    @Override
    public Accion findByNombreAccion(String nombre) {
        return AccionDao.findByNombreAccion(nombre);
    }

    @Override
    public List<Tarea> findAllTareas() {
        return TareasDao.findAll();
    }

    @Override
    public Tarea findTarea(Integer integer) {
        Tarea tarea = TareasDao.find(integer);
        return tarea;
    }

    @Override
    public List<Tipodocumento> findAllTipoDocumentos() {
        return TipoDocumentoDao.findAll();
    }

    @Override
    public boolean guardarTipoDocumentos(Tipodocumento elemento) {
        if (elemento.getIdTipoDocumento() == null) {

            TipoDocumentoDao.create(elemento);
            return false;
        } else {
            TipoDocumentoDao.edit(elemento);
            return true;
        }
    }

    @Override
    public void deleteTipoDocumentos(Tipodocumento elemento) {
        TipoDocumentoDao.remove(elemento);
    }

    @Override
    public String findNombreAccion(Integer idAccion) {
        Accion a = AccionDao.find(idAccion);
        if (a == null) {
            return "";
        } else {
            return a.getNombre();
        }
    }

    @Override
    public Accion findAccion(Integer accion) {
        return AccionDao.find(accion);
    }

    @Override
    public Date getLCTFecha(Listasclientestareas lct) {
        PreparedStatement st = null;
        ResultSet rs = null;
        Date fechaAtencion = null;
        DBConnection dBConnection = new DBConnection();
        try {
            if (dBConnection.getConnection() != null) {
                String query = "SELECT fechaAtencion FROM listasclientestareas WHERE idCliente=? AND idTarea=?;";
                st = dBConnection.getConnection().prepareStatement(query);
                st.setInt(1, lct.getListasclientestareasPK().getIdCliente());
                st.setInt(2, lct.getListasclientestareasPK().getIdTarea());
                rs = st.executeQuery();
                if (rs.next()) {
                    Calendar cal1 = Calendar.getInstance();
                    if (rs.getTimestamp("fechaAtencion") != null) {
                        cal1.setTime(rs.getTimestamp("fechaAtencion"));
                        cal1.set(Calendar.HOUR, cal1.get(Calendar.HOUR) - 5);
                        fechaAtencion = cal1.getTime();
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(HostessFacadeImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBConnection.shutdown();
                if (st != null) {
                    st.close();
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        return fechaAtencion;
    }

    @Override
    public Casino findCasino(Integer idCasino) {
        return CasinoDao.find(idCasino);
    }

    @Override
    public Categoria findCategoria(Integer idCategorias) {
        return CategoriaDao.find(idCategorias);
    }

    @Override
    public Tipodocumento findTipoDocumento(Integer idTipoDocumento) {
        return TipoDocumentoDao.find(idTipoDocumento);
    }

    @Override
    public Casinodetalle getDetalleCasinoById(Integer idCasino) {
        return DetalleCasinoDao.find(idCasino);
    }

    @Override
    public Casinodetalle guardarDetalleCasino(Casinodetalle detalleElemento) {
        if (detalleElemento.getIdCasino() == null) {
            DetalleCasinoDao.create(detalleElemento);
            return detalleElemento;
        } else {
            DetalleCasinoDao.edit(detalleElemento);
            return detalleElemento;
        }
    }

    @Override
    public void deleteDetalleCasino(Casinodetalle detalleElemento) {
        DetalleCasinoDao.remove(detalleElemento);
    }

    @Override
    public Solicitudentregalotesmaestro getSolicitudentregalotesbono(Integer id) {
        return SolicitudentregalotesmaestroDao.find(id);
    }

    @Override
    public List<Lotebono> getAllLotesBonos() {
        return LotebonoDao.findAll();
    }

    @Override
    public List<Solicitudentregalotesmaestro> getAllSolicitudentregalotesmaestro() {
        return SolicitudentregalotesmaestroDao.findAll();
    }

    @Override
    public void deleteSolicitudentregalotesmaestro(Solicitudentregalotesmaestro elemento) {
        for (Solicitudentregalote sel : elemento.getSolicitudentregaloteList()) {
            SolicitudentregalotesDao.remove(sel);
        }
        SolicitudentregalotesmaestroDao.remove(elemento);
    }

    @Override
    public List<Solicitudentregalotesmaestro> getSolicitudentregalotesmaestroNoAceptadas() {
        return SolicitudentregalotesmaestroDao.findNoAceptadas();
    }

    @Override
    public void borrarLote(Lotebono elemento) throws ExistenBonosFisicosException {
        if (elemento.getDesde().equals(elemento.getHasta())) {
            for (Iterator<Solicitudentregalote> iterator = elemento.getSolicitudentregaloteList().iterator(); iterator.hasNext();) {
                Solicitudentregalote next = iterator.next();
                SolicitudentregalotesDao.remove(next);

            }
            elemento.getSolicitudentregaloteList().clear();
            LotebonoDao.remove(elemento);
        } else {
            throw new ExistenBonosFisicosException();
        }
    }

    @Override
    public void guardarLote(Lotebono elemento) throws LoteBonosExistenteException {
        if (elemento.getId() == null) {
            if (!LotebonoDao.existeLote(elemento)) {
                elemento.setSolicitudentregaloteList(new ArrayList<Solicitudentregalote>());
                LotebonoDao.create(elemento);

            } else {
                throw new LoteBonosExistenteException();
            }
        } else {
            LotebonoDao.edit(elemento);
        }
    }

    @Override
    public void deleteSolicitudentrega(Solicitudentrega elemento) {
        if (elemento.getSolicitudentregaclienteList() != null) {
            for (Solicitudentregacliente col : elemento.getSolicitudentregaclienteList()) {
                SolicitudEntregaClientesDao.remove(col);
            }
        }
        SolicitudEntregaDao.remove(elemento);
    }

    @Override
    public List<Solicitudentrega> getAllSolicitudentrega() {
        return SolicitudEntregaDao.findAll();
    }

    @Override
    public List<Solicitudentrega> getAllSolicitudentregaSolicitante(Integer idUsuario) {
        return SolicitudEntregaDao.findByIdCreador(idUsuario);
    }

    @Override
    public Solicitudentrega getSolicitudbono(Integer id) {
        return SolicitudEntregaDao.find(id);
    }

    @Override
    public Solicitudentrega guardarSolicitudentrega(Solicitudentrega elemento, List<Integer> clientesABorrar) {

        System.out.println("entra aqui");
        Area a = AreaDao.findAll().get(0);
        if (elemento.getId() != null) {
            if (elemento.getTipoBono().getNombre().equals("NO PROMOCIONAL")) {
                for (Integer i : clientesABorrar) {
                    SolicitudEntregaClientesDao.remove(new Solicitudentregacliente(new SolicitudentregaclientePK(elemento.getId(), i)));
                }
                List<Solicitudentregacliente> secList = elemento.getSolicitudentregaclienteList();
                for (Solicitudentregacliente secList1 : secList) {
                    secList1.setSolicitudentrega(elemento);
                    secList1.setSolicitudentregaclientePK(new SolicitudentregaclientePK(elemento.getId(), secList1.getCliente().getIdCliente()));
                    SolicitudEntregaClientesDao.edit(secList1);
                }
                elemento.setSolicitudentregaclienteList(secList);
            }
            SolicitudEntregaDao.edit(elemento);
            return elemento;
        } else {
            List<Solicitudentregacliente> secList = elemento.getSolicitudentregaclienteList();
            elemento.setSolicitudentregaclienteList(null);
            SolicitudEntregaDao.create(elemento);
            for (Solicitudentregacliente secList1 : secList) {
                secList1.setSolicitudentrega(elemento);
                secList1.setSolicitudentregaclientePK(new SolicitudentregaclientePK(elemento.getId(), secList1.getCliente().getIdCliente()));
                SolicitudEntregaClientesDao.edit(secList1);
            }
            elemento.setSolicitudentregaclienteList(secList);
            SolicitudEntregaDao.edit(elemento);
            return elemento;
        }
    }

    @Override
    public void borrarSolicitudCliente(Solicitudentregacliente sec) {
        SolicitudEntregaClientesDao.remove(sec);
    }

    @Override
    public void guardarBononoincluido(Bononoincluido bni) {
        BonosnoincluidosDao.edit(bni);
    }

    @Override
    public void borrarBononoIncluido(Bononoincluido next) {
        BonosnoincluidosDao.remove(next);
    }

    @Override
    public void borrarSolicitudLote(Solicitudentregalote next2) {
        SolicitudentregalotesDao.remove(next2);
    }

    @Override
    public void convertBonosNoIncluidosToBonosNoFisicos(List<Solicitudentregalote> solicitudentregalotesList) {
        for (Solicitudentregalote solicitudentregalotesList1 : solicitudentregalotesList) {
            List<Bononoincluido> bonosNoIncluidos = solicitudentregalotesList1.getBononoincluidoList();
            for (Bononoincluido next : bonosNoIncluidos) {
                Bononofisico bnf = new Bononofisico();
                bnf.setConsecutivo(next.getConsecutivo());
                bnf.setLotesBonosid(solicitudentregalotesList1.getLotesBonosid());
                BonosnofisicosDao.create(bnf);
                solicitudentregalotesList1.getLotesBonosid().getBononofisicoList().add(bnf);
            }
            LotebonoDao.edit(solicitudentregalotesList1.getLotesBonosid());
        }
    }

    @Override
    public void editLoteBono(Lotebono lotesBonosid, List<Bononoincluido> bonosnoincluidos) {
        if (lotesBonosid.getBononofisicoList() == null) {
            lotesBonosid.setBononofisicoList(new ArrayList<Bononofisico>());
        }
        for (Bononoincluido next : bonosnoincluidos) {
            if (next.getConsecutivo() != null || !next.getConsecutivo().equals("")) {
                Bononofisico bnf = new Bononofisico();
                bnf.setConsecutivo(next.getConsecutivo());
                bnf.setLotesBonosid(lotesBonosid);
                BonosnofisicosDao.create(bnf);
                lotesBonosid.getBononofisicoList().add(bnf);
            }
        }
        LotebonoDao.edit(lotesBonosid);
    }

    @Override
    public Solicitudentregalotesmaestro guardarSolicitudentregabonos(Solicitudentregalotesmaestro elemento, List<Integer> bonosreincluidos, int porque) {
        if (bonosreincluidos != null) {
            for (Integer bonoreincluido : bonosreincluidos) {
                BonosnoincluidosDao.remove(new Bononoincluido(bonoreincluido));
            }
        }
        List<Solicitudentregalote> solicitudentregaloteses = elemento.getSolicitudentregaloteList() != null ? elemento.getSolicitudentregaloteList() : new ArrayList<Solicitudentregalote>();
        elemento.setSolicitudentregaloteList(null);

        if (elemento.getId() == null) {
            SolicitudentregalotesmaestroDao.create(elemento);
        } else {
            SolicitudentregalotesmaestroDao.edit(elemento);
        }
        for (Solicitudentregalote solicitudentregalotese : solicitudentregaloteses) {
            solicitudentregalotese.setSolicitudEntregaLotesMaestro(elemento);
            List<Bononoincluido> bonosnoincluidos = solicitudentregalotese.getBononoincluidoList();
            solicitudentregalotese.setBononoincluidoList(null);
            if (solicitudentregalotese.getId() == null) {
                SolicitudentregalotesDao.create(solicitudentregalotese);
            } else {
                SolicitudentregalotesDao.edit(solicitudentregalotese);
            }
            for (Iterator<Bononoincluido> iterator = bonosnoincluidos.iterator(); iterator.hasNext();) {
                Bononoincluido next = iterator.next();
                if (next.getConsecutivo() == null || next.getConsecutivo().equals("")) {
                    iterator.remove();
                } else {
                    next.setSolicitudEntregaLotesid(solicitudentregalotese);
                    if (next.getId() == null) {
                        BonosnoincluidosDao.create(next);
                    } else {
                        BonosnoincluidosDao.edit(next);
                    }
                }
            }
            solicitudentregalotese.setBononoincluidoList(bonosnoincluidos);
            SolicitudentregalotesDao.edit(solicitudentregalotese);

        }
        elemento.setSolicitudentregaloteList(solicitudentregaloteses);
        SolicitudentregalotesmaestroDao.edit(elemento);
        if (porque == 1) {
            String body = "Se ha generado una orden de entrada de lotes de bono con el número de acta " + elemento.getId()
                    + ".\nPor favor revisar la página de Lista de solicitudes de lotes de bonos.";
            Notificador.notificar(Notificador.SOLICITUD_ENTREGA_LOTES_GENERADA, body, "Orden de entrada de lotes de bonos generada", "");
        }
        return elemento;
    }

    @Override
    public void crearSolicitudSalidaBonos(Solicitudentrega s, boolean enviar) {
        Controlsalidabono csb = new Controlsalidabono();
        csb.setSolicitudEntregaid(s);
        csb.setSolicitante(s.getSolicitante());
        csb.setEstado("PRECREADA");
        try {
            DateFormat df = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
            DateFormat df2 = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
            TimeZone timeZone = TimeZone.getTimeZone("GMT-5");
            df.setTimeZone(timeZone);
            Calendar nowDate = Calendar.getInstance();
            nowDate.setTime(df2.parse(df.format(nowDate.getTime())));
            csb.setFecha(nowDate.getTime());

        } catch (ParseException ex) {
        }
        ControlsalidabonosDao.create(csb);
        if (enviar) {
            String body = "Se ha creado una solicitud de salida de bonos con el número de acta " + csb.getId()
                    + ".\nPor favor revisar la pagina de Lista de solicitudes de salida de bonos.";
            if (csb.getSolicitante().getUsuariodetalle() != null) {
                Notificador.notificar(Notificador.SOLICITUD_CONTROL_SALIDA_GENERADA, body, "Se ha generado una solicitud de salida de bonos de caja", csb.getSolicitante().getUsuariodetalle().getCorreo());
            }
        }
        if (s.getControlsalidabonoList() == null) {
            s.setControlsalidabonoList(new ArrayList<Controlsalidabono>());
        }
        s.getControlsalidabonoList().add(csb);
        SolicitudEntregaDao.edit(s);
    }

    @Override
    public List<Controlsalidabono> getAllControlsalidabonos() {
        return ControlsalidabonosDao.findAllButPRESOLICITADA();
    }

    @Override
    public Controlsalidabono getSolicitudSalida(Integer id) {
        return ControlsalidabonosDao.find(id);
    }

    @Override
    public List<Lotebono> getLotesBonosCasinoTipoBono(Integer idCasino, Tipobono tipoBono) {
        return LotebonoDao.getByCasinoTipoBono(idCasino, tipoBono);
    }

    @Override
    public Controlsalidabono guardarControlSalidaBonos(Controlsalidabono elemento, boolean enviar) {
        System.out.println("Comienza todo");
        List<ControlsalidabonosHasLotesbonos> csbhlb = elemento.getControlsalidabonosHasLotesbonosList() != null ? elemento.getControlsalidabonosHasLotesbonosList() : new ArrayList<ControlsalidabonosHasLotesbonos>();
        elemento.setControlsalidabonosHasLotesbonosList(null);
        elemento = ControlsalidabonosDao.edit(elemento);
        elemento.setControlsalidabonosHasLotesbonosList(csbhlb);
        System.out.println(elemento.getId());
        elemento = ControlsalidabonosDao.finish(elemento);

        if (enviar) {
            String cuerpo = "Orden de salida de bonos con número de acta" + elemento.getId() + " aceptada.";
            String titulo = "Orden de salida de bonos aceptada.";
            Notificador.notificar(Notificador.SOLICITUD_CONTROL_SALIDA_ACEPTADA, cuerpo, titulo, "");
        }
        return elemento;
    }

    @Override
    public void cambiarEstadoSolicitudentrega(Solicitudentrega elemento) {
        SolicitudEntregaDao.edit(elemento);
    }

    @Override
    public void cambiarEstadoSolicitudentregabonos(Solicitudentregalotesmaestro elemento) {
        SolicitudentregalotesmaestroDao.edit(elemento);
    }

    @Override
    public void guardarBonos(List<Bono> bonosAGuardar) {
        for (Bono bono : bonosAGuardar) {
            System.out.println("Consecutivo " + bono.getConsecutivo());
            if (bono.getId() == null) {
                BonoDao.create(bono);
            } else {
                BonoDao.edit(bono);
            }
        }
    }

    @Override
    public void editarBonos(List<Bono> bonosAGuardar) {
        for (Bono bono : bonosAGuardar) {
            BonoDao.edit(bono);
        }
    }

    @Override
    public List<Bono> getBonosPorEstado(String estado) {
        return BonoDao.getBonosPorEstado(estado);
    }

    @Override
    public List<Bono> getBonosPorEstadoYCasino(String estado, Casino idCasino) {
        return BonoDao.getBonosPorEstadoYCasino(estado, idCasino);
    }

    @Override
    public void saveBonos(Controlsalidabono elemento, Integer idUsuario) {
        List<Bono> bonos = elemento.getBonoList();
        for (Bono bono : bonos) {
            if (bono.getTipo().getNombre().equals("PROMOCIONAL") || bono.getCliente() == null || bono.getCliente().getIdCliente() == null) {
                bono.setCliente(null);
            }
            if (bono.getEstado().equals("ANULADO")) {
                BonoDao.edit(bono);
            }
        }
        elemento.setBonoList(bonos);
        ControlsalidabonosDao.editwb(elemento);
    }

    @Override
    public List<Bono> getAllBonos() {
        return BonoDao.findAll();
    }

    @Override
    public Tipotarea findTipoTarea(Integer idTipotarea) {
        return TipostareasDao.find(idTipotarea);
    }

    @Override
    public List<Cliente> buscarClientes(Cliente clienteAttr) {
        return ClienteDao.findClientes(clienteAttr);
    }

    @Override
    public List<Bono> getBonosPorAtributos(String estado, Casino casinoSelected, String nombres, String apellidos, String identificacion, String consecutivo) {
        return BonoDao.getBonosPorAtributos(estado, casinoSelected, nombres, apellidos, identificacion, consecutivo);
    }

    @Override
    public List<Bono> getAllBonosFecha(Date desde, Date hasta) {
        return BonoDao.findFechas(desde, hasta);
    }

    @Override
    public boolean existeid(Tipodocumento idTipoDocumento, String identificacion) {
        return ClienteDao.isIdentificacioniAsociatedCliente(idTipoDocumento, identificacion);
    }

    @Override
    public void guardaBono(Bono bono) {
        System.out.println("Consecutivo " + bono.getConsecutivo());
        if (bono.getId() == null) {
            BonoDao.create(bono);
        } else {
            BonoDao.edit(bono);
        }
    }

    @Override
    public List<Cliente> findAllClientesCasinos(Casino idCasino, String nombre, String apellidos, String ident, Tipodocumento tipodocumento, String sexo) {
        return ClienteDao.findByIdCasino(idCasino.getIdCasino(), nombre, apellidos, ident, tipodocumento, sexo);
    }

    @Override
    public List<Lotebono> getLotesBonosByCasino(Casino casinoSelected) throws CasinoHaveSolicitudCreadaException {
        System.out.println(SolicitudentregalotesDao.CasinoHaveSolicitudCreada(casinoSelected));
        if (SolicitudentregalotesDao.CasinoHaveSolicitudCreada(casinoSelected)) {
            throw new CasinoHaveSolicitudCreadaException();
        }
        return LotebonoDao.getByCasino(casinoSelected.getIdCasino());
    }

    @Override
    public void guardarClientesSinCategoria(Cliente cliente) {
        Categoria cat = CategoriaDao.findByName("C");
        cliente.setIdCategorias(cat);
        ClienteDao.create(cliente);
    }

    @Override
    public Integer getCantClientes() {
        return ClienteDao.count();
    }

    @Override
    public List<Tarea> getTareasUsuario(Usuario usuario) {
        return TareasDao.GetTareaByUsuario(usuario);
    }

    @Override
    public List<Cliente> findAllClientesCasinosConCupo(Casino idCasino) {
        return ClienteDao.findAllClientesCasinosConCupo(idCasino);
    }

    @Override
    public List<Solicitudentregalotesmaestro> getRequeriemntosLotes() {
        return SolicitudentregalotesmaestroDao.findRequerimientos();
    }

    @Override
    public void borrarSolicitudentregabonos(Solicitudentregalotesmaestro elemento, Object object, int i) {
        SolicitudentregalotesmaestroDao.remove(elemento);
    }

    @Override
    public List<Lotebono> getLotesBonosByCasinoPromo(Casino idCasino) {
        return LotebonoDao.getByCasinoPromo(idCasino.getIdCasino());
    }

    @Override
    public List<Lotebono> getLotesBonosByCasinoNoPromo(Casino idCasino) {
        return LotebonoDao.getByCasinoNoPromo(idCasino.getIdCasino());
    }

    @Override
    public Controlsalidabono guardarControlSalidaBonosLista(Controlsalidabono elemento, boolean enviar, List<Bono> bonosAGuardar) {
        System.out.println("Vamos a ver que pasa aqui");
        System.out.println("Control " + elemento.getId());
        System.out.println("Control " + elemento.getEstado());
        elemento = ControlsalidabonosDao.editConBonos(elemento, bonosAGuardar);

        return elemento;
    }

    @Override
    public List<Bono> getBonosPorAtributos(String en_sala, Casino casinoSelected, String consecutivo, Denominacion denominacion) {
        return BonoDao.getBonoPorCasinoDenominacionConsecutivo(en_sala, casinoSelected, consecutivo, denominacion);
    }

    @Override
    public void verificarEstadoSolicitudes() {
        SolicitudEntregaDao.verificar();
        BonoDao.revisarEstadoBonos();
    }

    @Override
    public List<Solicitudentrega> getAllSolicitudentregaSolicitanteEstado(Integer idUsuario, String bonos_vencidos_pendiente_por_generar_repo) {
        return SolicitudEntregaDao.findByIdCreadorEstado(idUsuario, bonos_vencidos_pendiente_por_generar_repo);
    }

    @Override
    public void cerrarSol(Solicitudentrega elemento, List<Bono> bonos) {
        SolicitudEntregaDao.edit(elemento);
        for (Bono bono : bonos) {
            BonoDao.edit(bono);
        }
    }

    @Override
    public List<Bono> getBonosporCasinoPropositoTipoFecha(List<CasinoBoolean> casinos, List<PropositosBoolean> propositos, List<TipoBonoBoolean> tipos, Integer ano, Integer mes, Integer anodesde, Integer mesdesde) {
        return BonoDao.getBonosporCasinoPropositoTipoFecha(casinos, propositos, tipos, ano, mes, anodesde, mesdesde);
    }

    @Override
    public List<Solicitudentrega> getAllSolicitudentreganovenc() {
        return SolicitudEntregaDao.findByNoVenc();
    }

    @Override
    public List<Solicitudentrega> getAllSolicitudentregaSolicitanteVENC(Integer idUsuario) {
        return SolicitudEntregaDao.findBySolVenc(idUsuario);
    }

    @Override
    public List<Solicitudentrega> getAllSolicitudentregaVENC() {
        return SolicitudEntregaDao.findByVenc();
    }

    @Override
    public List<Solicitudentrega> getAllSolicitudentregaCasino(Usuario usuario) {
        return SolicitudEntregaDao.findByCasinoUsuario(usuario);
    }

    @Override
    public void cambiarFechaSolicitud(Solicitudentrega solicitudentrega) {
        for (Controlsalidabono col : solicitudentrega.getControlsalidabonoList()) {
            col.setFechavencimientobonos(solicitudentrega.getFechavencimientobonos());
            for (Bono bono : col.getBonoList()) {
                bono.setFechaExpiracion(solicitudentrega.getFechavencimientobonos());
            }
        }
        SolicitudEntregaDao.edit(solicitudentrega);
    }

    @Override
    public void rechazar(Solicitudentrega elemento) {
        SolicitudEntregaDao.remove(elemento);
    }

    @Override
    public List<BonosCantidadMes> getBonosPorCantidad(List<CasinoBoolean> casinos, Integer ano, Integer mes, Integer annodesde, Integer mesdesde, List<PropositosBoolean> propositos) {
        List<Bono> bonos = BonoDao.getBonosporCasinoPropositoTipoFecha(casinos, propositos, null, ano, mes, annodesde, mesdesde);
        List<BonosCantidadMes> bonosPorFecha = new ArrayList<BonosCantidadMes>();
        Calendar c = Calendar.getInstance();
        for (Bono bono : bonos) {
            if (bono.getFechaExpiracion() != null) {
                c.setTime(bono.getFechaExpiracion());
                BonosCantidadMes b = new BonosCantidadMes(bono.getCasino().getNombre(), bono.getDenominacion().getValor() + "", bono.getTipo().getNombre());
                if (!bonosPorFecha.contains(b)) {
                    bonosPorFecha.add(b);

                }
                bonosPorFecha.get(bonosPorFecha.indexOf(b)).sumarCantidad(c.get(Calendar.MONTH), c.get(Calendar.YEAR));
            }
        }
        return bonosPorFecha;
    }

    @Override
    public List<BonosAprobadosCanjeados> getBonosPorCantidadMesuales(List<CasinoBoolean> casinos, List<PropositosBoolean> propositos, Integer ano, Integer mes, Integer annodesde, Integer mesdesde) {
        List<Bono> bonos = BonoDao.getBonosporCasinoPropositoTipoFecha(casinos, propositos, null, ano, mes, annodesde, mesdesde);
        List<BonosAprobadosCanjeados> bonosPorFecha = new ArrayList<BonosAprobadosCanjeados>();
        Calendar c = Calendar.getInstance();
        for (Bono bono : bonos) {
            if (bono.getFechaExpiracion() != null) {
                c.setTime(bono.getFechaExpiracion());
                BonosAprobadosCanjeados b = new BonosAprobadosCanjeados(bono.getCasino().getNombre());
                if (!bonosPorFecha.contains(b)) {
                    bonosPorFecha.add(b);

                }
                if (!bono.getEstado().equals("ANULADO")) {
                    System.out.println("Bono den " + bono.getDenominacion().getValor());
                    if (bono.getEstado().equals("CANJEADO")) {
                        bonosPorFecha.get(bonosPorFecha.indexOf(b)).sumarCantidad2(c.get(Calendar.MONTH), c.get(Calendar.YEAR), bono.getDenominacion().getValor());
                    } else {
                        bonosPorFecha.get(bonosPorFecha.indexOf(b)).sumarCantidad(c.get(Calendar.MONTH), c.get(Calendar.YEAR), bono.getDenominacion().getValor());
                    }
                }
            }
        }
        return bonosPorFecha;
    }

    @Override
    public List<Cliente> findAllClientesCasinos(Casino idCasino, List<CategoriaBoolean> categoriaBooleans, String nombre, String apellidos, String ident, Tipodocumento tipodocumento, String sexo) {
        return ClienteDao.findByIdCasinoAndCat(idCasino.getIdCasino(), categoriaBooleans, nombre, apellidos, ident, tipodocumento, sexo);
    }

    @Override
    public List<Bono> getBonosSinCliente(Casino casinoSelected) {
        return BonoDao.getBonosCasinoSinJustificacion(casinoSelected);
    }

    @Override
    public void justificarBonos(List<Bono> bonosSinCliente, String justificacion) {
        BonoDao.justificarBonos(bonosSinCliente, justificacion);
    }

    @Override
    public void MakeTareaSolicitud(Solicitudentrega solicitudEntregaid) {

        System.out.println("Generamos tareas");
        Tarea t = new Tarea();
        t.setNombre("ENTREGA DE BONOS DE FIDELIZACIÓN. ACTA #" + solicitudEntregaid.getId() + "");
        t.setDescripcion("Tarea para comunicar a los clientes que tienen un bono de fidelización en la sala " + solicitudEntregaid.getIdCasino().getNombre() + ".");
        t.setEstado("ACTIVA");
        t.setIdEvento(new Evento());
        t.getIdEvento().setNombre("GENÉRICO");
        t.setFechaInicio(new Date());
        t.setFechaFinalizacion(solicitudEntregaid.getFechavencimientobonos());
        t.setTipo(new Tipotarea(4));
        t.setSpeech("Estimado sr(a) XXXXX. En la sala de juegos " + solicitudEntregaid.getIdCasino().getNombre()
                + " tiene un bono de fidelización el cual puede ser reclamado ddesde ya.");
        List<Usuario> usuarios = UsuarioDao.findAll();
        Vista v = VistaDao.findByNombre("ManejadorEventosHostess");
        for (Iterator<Usuario> it = usuarios.iterator(); it.hasNext();) {
            Usuario usuario = it.next();
            if (!usuario.getIdPerfil().getVistaList().contains(v)) {
                it.remove();
            }
        }
        t.setUsuarioList(new ArrayList<Usuario>());
        for (Usuario usuario : usuarios) {
            if (usuario.getCasinoList().contains(solicitudEntregaid.getIdCasino())) {
                t.getUsuarioList().add(usuario);
            }
        }
        t.setListasclientestareasList(new ArrayList<Listasclientestareas>());

        Accion estadoscliente = findByNombreAccion("INICIAL");

        System.out.println("Enviamos tareas");
        t = TareasDao.createBonoFide(t, solicitudEntregaid.getControlsalidabonoList().get(0).getBonoList(), estadoscliente);
        for (Usuario s : t.getUsuarioList()) {
            Notificador.notificar(Notificador.AVISO_TAREA_ASIGNADA, "Se le ha asignado la tarea " + t.getNombre() + " #" + t.getIdTarea() + " para ser ejecutada.", "Tarea Asignada", s.getUsuariodetalle().getCorreo());
        }
    }
}
