/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.facade.impl;


import com.invbf.sistemagestionmercadeo.dao.AccionDao;
import com.invbf.sistemagestionmercadeo.dao.ConfiguracionDao;
import com.invbf.sistemagestionmercadeo.dao.ListasclientestareasDao;
import com.invbf.sistemagestionmercadeo.dao.TareasDao;
import com.invbf.sistemagestionmercadeo.dao.TipostareasDao;
import com.invbf.sistemagestionmercadeo.entity.Accion;
import com.invbf.sistemagestionmercadeo.entity.Listasclientestareas;
import com.invbf.sistemagestionmercadeo.entity.Tarea;
import com.invbf.sistemagestionmercadeo.entity.Tipotarea;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.exceptions.EventoSinClientesPorRevisarException;
import com.invbf.sistemagestionmercadeo.facade.HostessFacade;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ideacentre
 */
public class HostessFacadeImpl implements HostessFacade, Serializable {

    @Override
    public int findCantidadClientes() {
        return Integer.parseInt(ConfiguracionDao.findByNombre("CantidadClientes").getValor());
    }

    @Override
    public List<Tarea> findTareaHostess(Usuario usuario) {
        List<Tarea> tareasHostess;
        tareasHostess = TareasDao.findAll();
        Iterator<Tarea> iterator = tareasHostess.iterator();
        Tipotarea tipo = TipostareasDao.findBynombre("EMAIL");
        while (iterator.hasNext()) {
            Tarea e = iterator.next();
            if (e.getUsuarioList().contains(usuario) && e.getEstado().equals("ACTIVO") && !e.getTipo().equals(tipo)) {
            } else {
                iterator.remove();
            }
        }
        return tareasHostess;
    }

    @Override
    public List<Listasclientestareas> findClienteTareaHostess(Integer integer) throws EventoSinClientesPorRevisarException {
        System.out.println("Buscando clientes " + integer);
        List<Listasclientestareas> clientes = ListasclientestareasDao.findByIdTareaInicial(integer);

        System.out.println("Encontrados "+clientes.size());
        List<Listasclientestareas> clientesAEnviar = new ArrayList<Listasclientestareas>();

        int cantidadClientes = findCantidadClientes();
        System.out.println("cantidad de cleitnes " + cantidadClientes);
        for (int i = 0; i < cantidadClientes; i++) {
            System.out.println("buscando un cliente");
            if (clientes.size()>i && clientes.get(i) != null) {
                Listasclientestareas lce = clientes.get(i);
                lce.setCount(lce.getCount() + 1);
                System.out.println("encontrado sumandole uno al count");
                ListasclientestareasDao.edit(lce);

                System.out.println("editado");
                clientesAEnviar.add(lce);
                System.out.println("agregado");
            }
        }
        if (clientesAEnviar.isEmpty()) {
            throw new EventoSinClientesPorRevisarException();
        } else {
            return clientesAEnviar;
        }
    }

    @Override
    public Accion findAccionByName(String idAccion) {
        return AccionDao.findByNombreAccion(idAccion);
    }

    @Override
    public void guardarLCE(Listasclientestareas l, Integer idAccion) {
        try {
            l.setIdAccion(AccionDao.find(idAccion));

            DateFormat df = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
            DateFormat df2 = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
            TimeZone timeZone = TimeZone.getTimeZone("GMT-5");
            df.setTimeZone(timeZone);
            Calendar nowDate = Calendar.getInstance();
            nowDate.setTime(df2.parse(df.format(nowDate.getTime())));
            l.setFechaAtencion(nowDate.getTime());
            ListasclientestareasDao.edit(l);
        } catch (ParseException ex) {
            Logger.getLogger(HostessFacadeImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Listasclientestareas nuevoLCE(Integer index, List<Listasclientestareas> clientes, Listasclientestareas l) throws EventoSinClientesPorRevisarException {

        List<Listasclientestareas> clientesEv = ListasclientestareasDao.findByIdTareaInicial(index);
        Iterator<Listasclientestareas> iterator = clientesEv.iterator();

        while (iterator.hasNext()) {
            Listasclientestareas lce = iterator.next();
            if (!clientes.contains(lce) && !lce.equals(l)) {
                System.out.println(lce.getCliente().getNombres());
                System.out.println(lce.getCount());
                lce.setCount(lce.getCount() + 1);
                ListasclientestareasDao.edit(lce);
                return lce;
            }
        }

        throw new EventoSinClientesPorRevisarException();
    }

    @Override
    public Tipotarea findByNombreTipoTarea(String emaiL) {

        return TipostareasDao.findBynombre("EMAIL");
    }
}
