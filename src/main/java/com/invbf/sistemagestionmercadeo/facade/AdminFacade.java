/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.facade;

import com.invbf.sistemagestionmercadeo.entity.Acceso;
import com.invbf.sistemagestionmercadeo.entity.Area;
import com.invbf.sistemagestionmercadeo.entity.Cargo;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Casinodetalle;
import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Denominacion;
import com.invbf.sistemagestionmercadeo.entity.Formulario;
import com.invbf.sistemagestionmercadeo.entity.Perfil;
import com.invbf.sistemagestionmercadeo.entity.Propositoentrega;
import com.invbf.sistemagestionmercadeo.entity.Tarea;
import com.invbf.sistemagestionmercadeo.entity.Tipobono;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.entity.Usuariodetalle;
import com.invbf.sistemagestionmercadeo.entity.Vista;
import com.invbf.sistemagestionmercadeo.exceptions.NombreUsuarioExistenteException;
import com.invbf.sistemagestionmercadeo.exceptions.PerfilExistenteException;
import java.util.List;

/**
 *
 * @author ideacentre
 */
public interface AdminFacade {

    public List<Usuario> findAllUsuarios();
   
    public void deleteUsuarios(Usuario elemento);
    
    public Usuario guardarUsuarios(Usuario elemento) throws NombreUsuarioExistenteException;
    
    public void deletePerfiles(Perfil elemento);
    
    public List<Perfil> findAllPerfiles();
    
    public boolean guardarPerfiles(Perfil elemento) throws PerfilExistenteException;
    
    public List<Formulario> findAllFormularios();
    
    public void deleteFormularios(Formulario elemento);
    
    public boolean guardarFormularios(Formulario elemento);
    
    public List<Vista> findAllVistas();
    
    public void deleteVistas(Vista elemento);
    
    public boolean guardarVistas(Vista elemento);
    
    public Perfil findPerfil(Integer idPerfil);
    
    public List<Usuario> findAllUsuariosHostess() ;
    
    public Usuario findUsuarios(Integer idUsuario);

    public void agregarTareaUsuarios(Usuario s, Tarea elemento);

    public Vista findVistasByNombre(String nombre);

    public Formulario findFormularioByAccionAndTabla(String accion, String tabla);

    public List<Cargo> findAllCargos();
    
    public boolean guardarCargos(Cargo elemento);

    public void deleteCargos(Cargo elemento);

    public Usuariodetalle getDetalleUsuariosById(Integer idUsuario);

    public void deleteDetalleUsuarios(Usuariodetalle detalleElemento);

    public Usuariodetalle guardarDetalleUsuarios(Usuariodetalle detalleElemento);

    public List<Acceso> findAllAccesos();

    public boolean guardarAccesos(Acceso elemento);

    public void deleteAccesos(Acceso elemento);
    
    public List<Denominacion> findAllDenominaciones();

    public void deleteDenominacion(Denominacion elemento);

    public boolean guardarDenominacion(Denominacion elemento);

    public void deleteTiposbonos(Tipobono elemento);

    public List<Tipobono> findAllTiposbonos();

    public boolean guardarTiposbonos(Tipobono elemento);

    public List<Casino> findAllCasinos();

    public List<Propositoentrega> findAllPropositosentrega();

    public boolean guardarPropositosentrega(Propositoentrega elemento);

    public void deletePropositosentrega(Propositoentrega elemento);

    public List<Area> findAllAreas();

    public boolean guardarAreas(Area elemento);

    public void deleteAreas(Area elemento);

    public List<Cliente> findClientessgbByCasino(Casino idCasino);

    public Casino findCasino(Integer idCasino);

    public Casinodetalle findDetalleCasino(Integer idCasino);

    public Tipobono findTipobono(Integer id);

    public Propositoentrega findPropositoByNombre(String nombre);
    
}
