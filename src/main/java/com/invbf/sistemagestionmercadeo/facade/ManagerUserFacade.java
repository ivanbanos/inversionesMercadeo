/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.facade;

import com.invbf.sistemagestionmercadeo.entity.Bono;
import com.invbf.sistemagestionmercadeo.entity.Permiso;
import com.invbf.sistemagestionmercadeo.exceptions.clienteInexistenteException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ideacentre
 */
public interface ManagerUserFacade {

    public List<Permiso> getAllPermisos();

    public void eliminarPermiso(Permiso permiso);

    public String ejecutarPermiso(Permiso permiso)throws clienteInexistenteException;

    public void addPermiso(Permiso permiso);

    public List<Bono> getBonosPorFechas(Date time, Date time0);

    
}
