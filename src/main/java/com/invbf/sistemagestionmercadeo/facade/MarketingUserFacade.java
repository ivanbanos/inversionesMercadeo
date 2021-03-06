/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.facade;


import com.invbf.sistemagestionmercadeo.dto.BonosAprobadosCanjeados;
import com.invbf.sistemagestionmercadeo.dto.BonosCantidadMes;
import com.invbf.sistemagestionmercadeo.entity.Accion;
import com.invbf.sistemagestionmercadeo.entity.Atributo;
import com.invbf.sistemagestionmercadeo.entity.Bono;
import com.invbf.sistemagestionmercadeo.entity.Bononoincluido;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Casinodetalle;
import com.invbf.sistemagestionmercadeo.entity.Categoria;
import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Controlsalidabono;
import com.invbf.sistemagestionmercadeo.entity.Denominacion;
import com.invbf.sistemagestionmercadeo.entity.Evento;
import com.invbf.sistemagestionmercadeo.entity.Listasclientestareas;
import com.invbf.sistemagestionmercadeo.entity.Lotebono;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentrega;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregacliente;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregalote;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregalotesmaestro;
import com.invbf.sistemagestionmercadeo.entity.Tarea;
import com.invbf.sistemagestionmercadeo.entity.Tipobono;
import com.invbf.sistemagestionmercadeo.entity.Tipodocumento;
import com.invbf.sistemagestionmercadeo.entity.Tipojuego;
import com.invbf.sistemagestionmercadeo.entity.Tipotarea;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.exceptions.CasinoHaveSolicitudCreadaException;
import com.invbf.sistemagestionmercadeo.exceptions.ExistenBonosFisicosException;
import com.invbf.sistemagestionmercadeo.exceptions.LoteBonosExistenteException;
import com.invbf.sistemagestionmercadeo.util.CasinoBoolean;
import com.invbf.sistemagestionmercadeo.util.CategoriaBoolean;
import com.invbf.sistemagestionmercadeo.util.PropositosBoolean;
import com.invbf.sistemagestionmercadeo.util.TipoBonoBoolean;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ideacentre
 */
public interface MarketingUserFacade {

    public List<Cliente> findAllClientes();

    public List<Categoria> findAllCategorias();

    public void deleteCategorias(Categoria elemento);

    public boolean guardarCategorias(Categoria elemento);

    public List<Atributo> findAllAtributos();

    public void deleteAtributos(Atributo elemento);

    public boolean guardarAtributos(Atributo elemento);

    public void deleteTiposjuegos(Tipojuego elemento);

    public List<Tipojuego> findAllTiposjuegos();

    public boolean guardarTiposjuegos(Tipojuego elemento);

    public void deleteCasinos(Casino elemento);

    public Casino guardarCasinos(Casino elemento);

    public List<Casino> findAllCasinos();

    public List<Evento> findAllEventos();

    public void deleteEventos(Evento elemento);

    public Evento guardarEventos(Evento elemento);

    public void deleteClientes(Integer elemento);

    public Cliente guardarClientes(Cliente elemento);

    public List<Tipojuego> getTiposJuegosNoClientes(Integer idCliente);

    public Cliente findCliente(Integer integer);

    public Evento findEvento(Integer integer);

    public void guardarImagen(byte[] contents, String fileName);

    public List<Accion> findAllAcciones();

    public void deleteAccion(Accion elemento);

    public boolean guardarAccion(Accion elemento);

    public List<Tipotarea> findAllTipotarea();

    public boolean guardarTipotarea(Tipotarea elemento);

    public void deleteTipotarea(Tipotarea elemento);

    public void deleteTarea(Tarea tarea);

    public Tarea guardarTarea(Tarea elemento);

    public Accion findByNombreAccion(String iniciaL);

    public List<Tarea> findAllTareas();

    public Tarea findTarea(Integer integer);

    public List<Tipodocumento> findAllTipoDocumentos();
    
    public boolean guardarTipoDocumentos(Tipodocumento elemento);

    public void deleteTipoDocumentos(Tipodocumento elemento);

    public String findNombreAccion(Integer idAccion);

    public Accion findAccion(Integer accion);

    public Date getLCTFecha(Listasclientestareas lct);

    public Casino findCasino(Integer idCasino);

    public Categoria findCategoria(Integer idCategorias);

    public Tipodocumento findTipoDocumento(Integer idTipoDocumento);

    public Casinodetalle getDetalleCasinoById(Integer idCasino);

    public Casinodetalle guardarDetalleCasino(Casinodetalle detalleElemento);

    public void deleteDetalleCasino(Casinodetalle detalleElemento);
    
    public Solicitudentregalotesmaestro getSolicitudentregalotesbono(Integer id);

    public List<Lotebono> getAllLotesBonos();

    public Solicitudentregalotesmaestro guardarSolicitudentregabonos(Solicitudentregalotesmaestro elemento, List<Integer> bonosreincluidos, int porque);

    public List<Solicitudentregalotesmaestro> getAllSolicitudentregalotesmaestro();

    public void deleteSolicitudentregalotesmaestro(Solicitudentregalotesmaestro elemento);

    public void editLoteBono(Lotebono lotesBonosid, List<Bononoincluido> solicitudentregalotesList);

    public List<Solicitudentregalotesmaestro> getSolicitudentregalotesmaestroNoAceptadas();

    public void borrarLote(Lotebono elemento)throws ExistenBonosFisicosException;

    public void guardarLote(Lotebono elemento)throws LoteBonosExistenteException;

    public void deleteSolicitudentrega(Solicitudentrega elemento);

    public List<Solicitudentrega> getAllSolicitudentrega();

    public List<Solicitudentrega> getAllSolicitudentregaSolicitante(Integer idUsuario);

    public Solicitudentrega getSolicitudbono(Integer id);

    public Solicitudentrega guardarSolicitudentrega(Solicitudentrega elemento, List<Integer> clientesABorrar);

    public void borrarSolicitudCliente(Solicitudentregacliente sec);

    public void guardarBononoincluido(Bononoincluido bni);

    public void borrarBononoIncluido(Bononoincluido next);

    public void borrarSolicitudLote(Solicitudentregalote next2);

    public void convertBonosNoIncluidosToBonosNoFisicos(List<Solicitudentregalote> solicitudentregalotesList);

    public void crearSolicitudSalidaBonos(Solicitudentrega s, boolean enviar);

    public List<Controlsalidabono> getAllControlsalidabonos();

    public Controlsalidabono getSolicitudSalida(Integer id);

    public List<Lotebono> getLotesBonosCasinoTipoBono(Integer idCasino, Tipobono tipoBono);

    public Controlsalidabono guardarControlSalidaBonos(Controlsalidabono elemento, boolean enviar);
    
    public Controlsalidabono guardarControlSalidaBonosLista(Controlsalidabono elemento, boolean enviar, List<Bono> bonosAGuardar);

    public void cambiarEstadoSolicitudentrega(Solicitudentrega elemento);

    public void cambiarEstadoSolicitudentregabonos(Solicitudentregalotesmaestro elemento);

    public void guardarBonos(List<Bono> bonosAGuardar);
    
    public void editarBonos(List<Bono> bonosAGuardar);

    public List<Bono> getBonosPorEstado(String por_validar);

    public List<Bono> getBonosPorEstadoYCasino(String por_validar, Casino idCasino);

    public void saveBonos(Controlsalidabono elemento, Integer idUsuario);

    public List<Bono> getAllBonos();

    public Tipotarea findTipoTarea(Integer idTipotarea);

    public List<Cliente> buscarClientes(Cliente clienteAttr);

    public List<Bono> getBonosPorAtributos(String en_sala, Casino casinoSelected, String nombres, String apellidos, String identificacion, String consecuvtivo);

    public List<Bono> getAllBonosFecha(Date desde, Date hasta);

    public boolean existeid(Tipodocumento idTipoDocumento, String identificacion);

    public void guardaBono(Bono elemento);

    public List<Cliente> findAllClientesCasinos(Casino idCasino, String nombre, String apellidos, String ident, Tipodocumento tipodocumento, String sexo);

    public List<Lotebono> getLotesBonosByCasino(Casino casinoSelected)throws CasinoHaveSolicitudCreadaException ;

    public void guardarClientesSinCategoria(Cliente cliente);

    public Integer getCantClientes();

    public List<Tarea> getTareasUsuario(Usuario usuario);

    public List<Cliente> findAllClientesCasinosConCupo(Casino idCasino);

    public List<Solicitudentregalotesmaestro> getRequeriemntosLotes();

    public void borrarSolicitudentregabonos(Solicitudentregalotesmaestro elemento, Object object, int i);

    public List<Lotebono> getLotesBonosByCasinoPromo(Casino idCasino);

    public List<Lotebono> getLotesBonosByCasinoNoPromo(Casino idCasino);

    public List<Bono> getBonosPorAtributos(String en_sala, Casino casinoSelected, String consecutivo, Denominacion denominacion);

    public void verificarEstadoSolicitudes();

    public List<Solicitudentrega> getAllSolicitudentregaSolicitanteEstado(Integer idUsuario, String bonos_vencidos_pendiente_por_generar_repo);

    public void cerrarSol(Solicitudentrega elemento, List<Bono> bonos);

    public List<Bono> getBonosporCasinoPropositoTipoFecha(List<CasinoBoolean> casinos, List<PropositosBoolean> propositos, List<TipoBonoBoolean> tipos, Integer ano, Integer mes, Integer anodesde, Integer mesdesde);

    public List<Solicitudentrega> getAllSolicitudentreganovenc();

    public List<Solicitudentrega> getAllSolicitudentregaSolicitanteVENC(Integer idUsuario);

    public List<Solicitudentrega> getAllSolicitudentregaVENC();

    public List<Solicitudentrega> getAllSolicitudentregaCasino(Usuario usuario);

    public void cambiarFechaSolicitud(Solicitudentrega solicitudentrega);

    public void rechazar(Solicitudentrega elemento);

    public List<BonosCantidadMes> getBonosPorCantidad(List<CasinoBoolean> casinos, Integer ano, Integer mes, Integer annodesde, Integer mesdesde, List<PropositosBoolean> propositos);

    public List<BonosAprobadosCanjeados> getBonosPorCantidadMesuales(List<CasinoBoolean> casinos,List<PropositosBoolean> propositos, Integer ano, Integer mes, Integer annodesde, Integer mesdesde);

    public List<Cliente> findAllClientesCasinos(Casino idCasino, List<CategoriaBoolean> categoriaBooleans, String nombreBusqueda, String apellidosBusqueda, String identificacionBusqueda, Tipodocumento object, String string);

    public List<Bono> getBonosSinCliente(Casino casinoSelected);

    public void justificarBonos(List<Bono> bonosSinCliente, String justificacion);

    public void MakeTareaSolicitud(Solicitudentrega solicitudEntregaid);


}
