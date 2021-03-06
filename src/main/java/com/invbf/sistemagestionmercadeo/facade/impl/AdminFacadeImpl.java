/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.facade.impl;

import com.invbf.sistemagestionmercadeo.dao.AreaDao;
import com.invbf.sistemagestionmercadeo.dao.BonoDao;
import com.invbf.sistemagestionmercadeo.dao.CargoDao;
import com.invbf.sistemagestionmercadeo.dao.CasinoDao;
import com.invbf.sistemagestionmercadeo.dao.ClienteDao;
import com.invbf.sistemagestionmercadeo.dao.CommputadorRegistradoDao;
import com.invbf.sistemagestionmercadeo.dao.DenominacionDao;
import com.invbf.sistemagestionmercadeo.dao.DetalleCasinoDao;
import com.invbf.sistemagestionmercadeo.dao.FormularioDao;
import com.invbf.sistemagestionmercadeo.dao.PerfilDao;
import com.invbf.sistemagestionmercadeo.dao.PropositosentregaDao;
import com.invbf.sistemagestionmercadeo.dao.TareasDao;
import com.invbf.sistemagestionmercadeo.dao.TipoBonoDao;
import com.invbf.sistemagestionmercadeo.dao.UsuarioDao;
import com.invbf.sistemagestionmercadeo.dao.UsuarioDetalleDao;
import com.invbf.sistemagestionmercadeo.dao.VistaDao;
import com.invbf.sistemagestionmercadeo.entity.Area;
import com.invbf.sistemagestionmercadeo.entity.Bono;
import com.invbf.sistemagestionmercadeo.entity.Cargo;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Casinodetalle;
import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.CommputadorRegistrado;
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
import com.invbf.sistemagestionmercadeo.facade.AdminFacade;
import com.invbf.sistemagestionmercadeo.util.CasinoBoolean;
import com.invbf.sistemagestionmercadeo.util.EncryptUtil;
import com.invbf.sistemagestionmercadeo.util.Notificador;
import com.invbf.sistemagestionmercadeo.util.UsuarioDTO;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author ideacentre
 */
public class AdminFacadeImpl implements AdminFacade, Serializable {

    @Override
    public List<Usuario> findAllUsuarios() {
        return UsuarioDao.findAll();
    }

    @Override
    public void deleteUsuarios(Usuario elemento) {
        List<Tarea> tareas = TareasDao.findAll();
        for (Tarea t : tareas) {
            if (t.getUsuarioList().contains(elemento)) {
                t.getUsuarioList().remove(elemento);
                TareasDao.edit(t);
            }
        }
        UsuarioDetalleDao.remove(elemento.getUsuariodetalle());
        UsuarioDao.remove(elemento);
    }

    @Override
    public UsuarioDTO guardarUsuarios(UsuarioDTO elemento) throws NombreUsuarioExistenteException {
        if (elemento.getIdUsuario() == null) {
            try {
                elemento.setContrasena(EncryptUtil.encryptPassword(elemento.getContrasena()));
            } catch (NoSuchAlgorithmException ex) {
            }
            if (UsuarioDao.findByNombreUsuario(elemento.getNombreUsuario()) != null) {
                throw new NombreUsuarioExistenteException();
            }
            Usuariodetalle detalle = elemento.getUsuariodetalle();
            Usuario u = new Usuario(elemento.getIdUsuario(), elemento.getNombreUsuario(), elemento.getContrasena());
            u.setIdPerfil(elemento.getIdPerfil());
            u.setEstado(elemento.getEstado());
            u.setNombre(elemento.getNombre());
            u.setCasinoList(new ArrayList<Casino>());
            for (CasinoBoolean casino : elemento.getCasinos()) {
                if (casino.isSelected()) {
                    u.getCasinoList().add(casino.getCasino());
                }
            }
            UsuarioDao.create(u);
            detalle.setIdUsuario(u.getIdUsuario());
            UsuarioDetalleDao.create(detalle);
            u.setUsuariodetalle(detalle);
            UsuarioDao.edit(u);
            return elemento;
        } else {
            Usuario u = UsuarioDao.find(elemento.getIdUsuario());
            if (elemento.getContrasena() == null || elemento.getContrasena().equals("")) {
            } else {
                try {
                    u.setContrasena(EncryptUtil.encryptPassword(elemento.getContrasena()));
                } catch (NoSuchAlgorithmException ex) {
                }
            }
            u.setCasinoList(new ArrayList<Casino>());
            for (CasinoBoolean casino : elemento.getCasinos()) {
                if (casino.isSelected()) {
                    u.getCasinoList().add(casino.getCasino());
                }
            }
            u.setIdUsuario(elemento.getIdUsuario());
            u.setNombreUsuario(elemento.getNombreUsuario());
            u.setIdPerfil(elemento.getIdPerfil());
            u.setNombre(elemento.getNombre());
            u.setEstado(elemento.getEstado());
            UsuarioDetalleDao.edit(u.getUsuariodetalle());
            UsuarioDao.edit(u);
            return elemento;
        }
    }

    @Override
    public void deletePerfiles(Perfil elemento) {
        PerfilDao.remove(elemento);
    }

    @Override
    public List<Perfil> findAllPerfiles() {
        return PerfilDao.findAll();
    }

    @Override
    public boolean guardarPerfiles(Perfil elemento) throws PerfilExistenteException {
        if (elemento.getIdPerfil() == null) {
            if (PerfilDao.findByNombre(elemento.getNombre()) != null) {
                throw new PerfilExistenteException();
            }
            PerfilDao.create(elemento);

            return false;
        } else {
            PerfilDao.edit(elemento);
            return true;
        }
    }

    @Override
    public List<Formulario> findAllFormularios() {
        return FormularioDao.findAll();
    }

    @Override
    public void deleteFormularios(Formulario elemento) {
        FormularioDao.remove(elemento);
    }

    @Override
    public boolean guardarFormularios(Formulario elemento) {
        if (elemento.getIdFormulario() == null) {
            FormularioDao.create(elemento);
            return false;
        } else {
            FormularioDao.edit(elemento);
            return true;
        }
    }

    @Override
    public List<Vista> findAllVistas() {
        return VistaDao.findAll();
    }

    @Override
    public void deleteVistas(Vista elemento) {
        VistaDao.remove(elemento);
    }

    @Override
    public boolean guardarVistas(Vista elemento) {
        if (elemento.getIdVista() == null) {
            VistaDao.create(elemento);
            return false;
        } else {
            VistaDao.edit(elemento);
            return true;
        }
    }

    @Override
    public Perfil findPerfil(Integer idPerfil) {
        return PerfilDao.find(idPerfil);
    }

    @Override
    public List<Usuario> findAllUsuariosHostess() {
        List<Usuario> usuarios = findAllUsuarios();
        Vista v = findVistasByNombre("ManejadorEventosHostess");
        for (Iterator<Usuario> it = usuarios.iterator(); it.hasNext();) {
            Usuario usuario = it.next();
            if (!usuario.getIdPerfil().getVistaList().contains(v)) {
                it.remove();
            }
        }
        return usuarios;
    }

    @Override
    public Usuario findUsuarios(Integer idUsuario) {
        return UsuarioDao.find(idUsuario);
    }

    @Override
    public void agregarTareaUsuarios(Usuario s, Tarea elemento) {
        Usuario usuario = UsuarioDao.find(s.getIdUsuario());
        if (usuario.getTareaList() == null) {
            usuario.setTareaList(new ArrayList<Tarea>(0));
        }
        usuario.getTareaList().add(elemento);
        UsuarioDao.edit(usuario);
    }

    @Override
    public Vista findVistasByNombre(String nombre) {
        return VistaDao.findByNombre(nombre);
    }

    @Override
    public Formulario findFormularioByAccionAndTabla(String accion, String tabla) {
        return FormularioDao.findByAccionAndTabla(accion, tabla);
    }

    @Override
    public List<Cargo> findAllCargos() {
        return CargoDao.findAll();
    }

    @Override
    public boolean guardarCargos(Cargo elemento) {
        if (elemento.getIdcargo() == null) {
            CargoDao.create(elemento);
            return false;
        } else {
            CargoDao.edit(elemento);
            return true;
        }
    }

    @Override
    public void deleteCargos(Cargo elemento) {
        CargoDao.remove(elemento);
    }

    @Override
    public Usuariodetalle getDetalleUsuariosById(Integer idUsuario) {
        return UsuarioDetalleDao.find(idUsuario);
    }

    @Override
    public void deleteDetalleUsuarios(Usuariodetalle detalleElemento) {
        UsuarioDetalleDao.remove(detalleElemento);
    }

    @Override
    public Usuariodetalle guardarDetalleUsuarios(Usuariodetalle detalleElemento) {
        if (detalleElemento.getIdUsuario() == null) {
            UsuarioDetalleDao.create(detalleElemento);
            return detalleElemento;
        } else {
            UsuarioDetalleDao.edit(detalleElemento);
            return detalleElemento;
        }
    }

    @Override
    public List<Denominacion> findAllDenominaciones() {
        return DenominacionDao.findAll();
    }

    @Override
    public void deleteDenominacion(Denominacion elemento) {
        DenominacionDao.remove(elemento);
    }

    @Override
    public boolean guardarDenominacion(Denominacion elemento) {
        if (elemento.getId() == null) {

            DenominacionDao.create(elemento);
            return false;
        } else {
            DenominacionDao.edit(elemento);
            return true;
        }
    }

    @Override
    public void deleteTiposbonos(Tipobono elemento) {
        TipoBonoDao.remove(elemento);
    }

    @Override
    public List<Tipobono> findAllTiposbonos() {
        return TipoBonoDao.findAll();
    }

    @Override
    public boolean guardarTiposbonos(Tipobono elemento) {
        if (elemento.getId() == null) {

            TipoBonoDao.create(elemento);
            return false;
        } else {
            TipoBonoDao.edit(elemento);
            return true;
        }
    }

    @Override
    public List<Casino> findAllCasinos() {
        return CasinoDao.findAll();
    }

    @Override
    public List<Propositoentrega> findAllPropositosentrega() {
        return PropositosentregaDao.findAll();
    }

    @Override
    public boolean guardarPropositosentrega(Propositoentrega elemento) {
        if (elemento.getId() == null) {

            PropositosentregaDao.create(elemento);
            return false;
        } else {
            PropositosentregaDao.edit(elemento);
            return true;
        }
    }

    @Override
    public void deletePropositosentrega(Propositoentrega elemento) {
        PropositosentregaDao.remove(elemento);
    }

    @Override
    public List<Area> findAllAreas() {
        return AreaDao.findAll();
    }

    @Override
    public boolean guardarAreas(Area elemento) {
        if (elemento.getId() == null) {

            AreaDao.create(elemento);
            return false;
        } else {
            AreaDao.edit(elemento);
            return true;
        }
    }

    @Override
    public void deleteAreas(Area elemento) {
        AreaDao.remove(elemento);
    }

    @Override
    public Casino findCasino(Integer idCasino) {
        return CasinoDao.find(idCasino);
    }

    @Override
    public Casinodetalle findDetalleCasino(Integer idCasino) {
        return DetalleCasinoDao.find(idCasino);
    }

    @Override
    public Tipobono findTipobono(Integer id) {
        return TipoBonoDao.find(id);
    }

    @Override
    public Propositoentrega findPropositoByNombre(String nombre) {
        return PropositosentregaDao.findByNombre(nombre);
    }

    @Override
    public void revisarBonos() {
        BonoDao.revisarEstadoBonos();
    }

    @Override
    public Bono buscarBono(Casino casinoSelected, Denominacion denoinacionSelected, String consecutivo) {
        return BonoDao.buscarBono(casinoSelected, denoinacionSelected, consecutivo);
    }

    @Override
    public void guardarBono(Bono bono) {
        BonoDao.edit(bono);
    }

    @Override
    public Propositoentrega findProposito(Integer id) {
        return PropositosentregaDao.find(id);
    }

    @Override
    public List<Usuario> findUsuariosAutorizadoresCasino(Casino casinoSelected) {
        return UsuarioDao.findAutorizadoresCasino(casinoSelected);
    }

    @Override
    public List<CommputadorRegistrado> getComputadoresRegistrados() {
        return CommputadorRegistradoDao.findAll();
    }

    @Override
    public void saveMaq(CommputadorRegistrado elemento) {
        CommputadorRegistradoDao.edit(elemento);
    }

    @Override
    public void deleteMaq(CommputadorRegistrado elemento) {
        CommputadorRegistradoDao.eliminarMaq(elemento);
    }

    public void enviarCorreoCumpeanos() {
        List<Casino> casinos = CasinoDao.findAll();

        for (Casino casino : casinos) {
            List<Cliente> clientes = ClienteDao.getCumpeaneros(casino);
            Calendar hoy = Calendar.getInstance();
            System.out.println("Hoy");
            System.out.println(hoy.getTime());
            for (Iterator<Cliente> iterator = clientes.iterator(); iterator.hasNext();) {
                Cliente next = iterator.next();
                if (next.getCumpleanos() != null) {
                    Calendar cumpleanos = Calendar.getInstance();
                    cumpleanos.setTime(next.getCumpleanos());
                    System.out.println(cumpleanos.getTime());
                    System.out.println(cumpleanos.get(Calendar.DAY_OF_MONTH));
                    System.out.println(hoy.get(Calendar.DAY_OF_MONTH));
                    System.out.println(cumpleanos.get(Calendar.MONTH));
                    System.out.println(hoy.get(Calendar.MONTH));
                    System.out.println(cumpleanos.get(Calendar.DAY_OF_MONTH)!=hoy.get(Calendar.DAY_OF_MONTH)
                            ||cumpleanos.get(Calendar.MONTH)!=hoy.get(Calendar.MONTH));
                    if(cumpleanos.get(Calendar.DAY_OF_MONTH)!=hoy.get(Calendar.DAY_OF_MONTH)
                            ||cumpleanos.get(Calendar.MONTH)!=hoy.get(Calendar.MONTH)){
                        iterator.remove();
                    }
                }else{iterator.remove();}
            }
            System.out.println("Cantidad");
            System.out.println(clientes.size());
            if (!clientes.isEmpty()) {
                String body = "En la sala "+casino.getNombre()+" se encuentran de cumpleanos hoy:<br />";
                body += "<table>";
                body += "<tr><th>Nombre</th><th>Categoria</th></tr>";

                for (Cliente cliente : clientes) {
                    body += "<tr><th>" + cliente.getNombres() + " " + cliente.getApellidos() + "</th>"
                            + "<th>" + cliente.getIdCategorias().getNombre() + "</th>"
                            + "</tr>";
                }
                body += "</table>"
                        + "<br />"
                        + "Debe llamar a estos clientes para felicitarlos, invitarlos a la sala y hacerle entrega de su obsequio (si fue solicitado y aprobado).";
                Notificador.notificar(Notificador.correoCumpleanos, body, "Cumpleanos de hoy", "", casino);
            }
        }
    }

}
