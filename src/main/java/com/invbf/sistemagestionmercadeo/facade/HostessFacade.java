/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.facade;

import com.invbf.sistemagestionmercadeo.entity.Accion;
import com.invbf.sistemagestionmercadeo.entity.Listasclientestareas;
import com.invbf.sistemagestionmercadeo.entity.Tarea;
import com.invbf.sistemagestionmercadeo.entity.Tipotarea;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.exceptions.EventoSinClientesPorRevisarException;
import java.util.List;

/**
 *
 * @author ideacentre
 */
public interface HostessFacade {

    public List<Tarea> findTareaHostess(Usuario usuario);

    public int findCantidadClientes();

    public List<Listasclientestareas> findClienteTareaHostess(Integer integer)throws EventoSinClientesPorRevisarException;

    public Accion findAccionByName(String idAccion);

    public void guardarLCE(Listasclientestareas l, Integer idAccion);

    public Listasclientestareas nuevoLCE(Integer integer, List<Listasclientestareas> clientes, Listasclientestareas l)throws EventoSinClientesPorRevisarException;

    public Tipotarea findByNombreTipoTarea(String emaiL);
}
