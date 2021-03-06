/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.facade.impl;


import com.invbf.sistemagestionmercadeo.dao.BonoDao;
import com.invbf.sistemagestionmercadeo.dao.CasinoDao;
import com.invbf.sistemagestionmercadeo.dao.CategoriaDao;
import com.invbf.sistemagestionmercadeo.dao.ClienteDao;
import com.invbf.sistemagestionmercadeo.dao.PermisosDao;
import com.invbf.sistemagestionmercadeo.dao.TipoDocumentoDao;
import com.invbf.sistemagestionmercadeo.entity.Bono;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Permiso;
import com.invbf.sistemagestionmercadeo.exceptions.clienteInexistenteException;
import com.invbf.sistemagestionmercadeo.facade.ManagerUserFacade;
import com.invbf.sistemagestionmercadeo.util.CasinoBoolean;
import com.invbf.sistemagestionmercadeo.util.PropositosBoolean;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ideacentre
 */
public class ManagerUserFacadeImpl implements ManagerUserFacade, Serializable{

    @Override
    public List<Permiso> getAllPermisos() {
        return PermisosDao.findAll();
    }

    @Override
    public void eliminarPermiso(Permiso permiso) {
        PermisosDao.remove(permiso);
    }

    @Override
    public String ejecutarPermiso(Permiso permiso) throws clienteInexistenteException {
        if (permiso.getTipo().equals("ELIMINAR")) {
            Cliente cliente = ClienteDao.find(Integer.parseInt(permiso.getId()));
            if (cliente == null) {
                throw new clienteInexistenteException();
            } else {
                ClienteDao.remove(cliente);
                return "Cliente removido";
            }
        } else if (permiso.getTipo().equals("EDITAR")) {
            Cliente cliente = ClienteDao.find(Integer.parseInt(permiso.getId()));
            if (cliente == null) {
                throw new clienteInexistenteException();
            } else {
                if (permiso.getCampo().equals("CUPO DE FIDELIZACION")) {
                    cliente.setBonoFidelizacion(permiso.getNuevoValor());
                    ClienteDao.edit(cliente);
                }else if (permiso.getCampo().equals("CATEGORIA")) {
                    cliente.setIdCategorias(CategoriaDao.find(Integer.parseInt(permiso.getNuevoValor())));
                    ClienteDao.edit(cliente);
                }else if (permiso.getCampo().equals("TELEFONO 1")) {
                    cliente.setTelefono1(permiso.getNuevoValor());
                    ClienteDao.edit(cliente);
                }else if (permiso.getCampo().equals("TELEFONO 2")) {
                    cliente.setTelefono2(permiso.getNuevoValor());
                    ClienteDao.edit(cliente);
                }else if (permiso.getCampo().equals("CORREO")) {
                    cliente.setCorreo(permiso.getNuevoValor());
                    ClienteDao.edit(cliente);
                }
                return "Cliente actualizado";
            }
        }
        return null;
    }

    @Override
    public void addPermiso(Permiso permiso) {
        PermisosDao.create(permiso);
    }

    @Override
    public List<Bono> getBonosPorFechas(Date desde, Date hasta) {
        return BonoDao.getBonosRangoFecha(desde, hasta);
    }

    @Override
    public List<Bono> getBonosPorFechasYCasinos(Date desde, Date hasta, List<CasinoBoolean> casinos, List<PropositosBoolean> propositos, String nombre, String apellidos) {
        return BonoDao.getBonosRangoFechaYCasino(desde, hasta, casinos, propositos, nombre, apellidos);
    }
}
