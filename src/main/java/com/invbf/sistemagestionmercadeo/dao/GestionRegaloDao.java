/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dao;

import com.invbf.sistemagestionmercadeo.dto.InventarioRegalosDTO;
import com.invbf.sistemagestionmercadeo.dto.RegalosCantidadDTO;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Regalos;
import com.invbf.sistemagestionmercadeo.entity.Regalosinventario;
import com.invbf.sistemagestionmercadeo.entity.Categorias;
import com.invbf.sistemagestionmercadeo.entity.Ordencompraregalos;
import com.invbf.sistemagestionmercadeo.entity.Ordencompraregalodetalle;
import com.invbf.sistemagestionmercadeo.entity.Solicitudregalodetalle;
import com.invbf.sistemagestionmercadeo.entity.Solicitudregalos;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.Root;

/**
 *
 * @author ivan
 */
public class GestionRegaloDao {
    
    public static List<Regalos> getListaRegalos() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Regalos> lista = new ArrayList<Regalos>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Regalos> c = cq.from(Regalos.class);
            cq.select(c);
            lista = em.createQuery(cq).getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return lista;
    }

    public static List<Categorias> getListaCategorias() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Categorias> lista = new ArrayList<Categorias>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categorias> c = cq.from(Categorias.class);
            cq.select(c);
            lista = em.createQuery(cq).getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return lista;
    }

    
    public static Regalos addRegalo(Regalos regalo) {
        regalo.setNombre(regalo.getNombre().toUpperCase());
        regalo.setDescripcion(regalo.getDescripcion().toUpperCase());
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            if (regalo.getId() == null||regalo.getId() == 0) {
                em.persist(regalo);
                em.flush();
                Regalosinventario inventario = new Regalosinventario();
                inventario.setCantidad(0);
                inventario.setRegalo(regalo);
                em.persist(inventario);
            } else {
                Regalos regaloc = em.find(Regalos.class, regalo.getId());
                regaloc.setNombre(regalo.getNombre());
                regaloc.setDescripcion(regalo.getDescripcion());
                regaloc.setGenero(regalo.getGenero());
                regaloc.setCategoria(regalo.getCategoria());
                em.merge(regaloc);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return regalo;
    }

    public static Regalos addInventarioBaraja(Regalos regalo) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Regalosinventario ib = new Regalosinventario();
            ib.setRegalo(regalo);
            ib.setCantidad(0);
            ib.setMax(0);
            ib.setMin(0);
            em.persist(ib);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return regalo;
    }

    public static List<Regalosinventario> getListaInvenratioRegalos() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Regalosinventario> lista = new ArrayList<Regalosinventario>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Regalosinventario> c = cq.from(Regalosinventario.class);
            cq.select(c);
            lista = em.createQuery(cq).getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        System.err.println(lista.size());
        em.clear();
        em.close();
        emf.close();
        return lista;
    }

    public static List<Ordencompraregalos> getListaOrdenesCompraRegalo() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Ordencompraregalos> lista = new ArrayList<Ordencompraregalos>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ordencompraregalos> c = cq.from(Ordencompraregalos.class);
            cq.select(c);
            lista = em.createQuery(cq).getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return lista;
    }

    public static List<Solicitudregalos> getListaSoliciudesRegalo() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Solicitudregalos> lista = new ArrayList<Solicitudregalos>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Solicitudregalos> c = cq.from(Solicitudregalos.class);
            cq.select(c);
            lista = em.createQuery(cq).getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return lista;
    }

    public static Ordencompraregalos crearOrdenCompra(Ordencompraregalos orden) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.persist(orden);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return orden;
    }

    public static Ordencompraregalos guardarOrdenCompra(Ordencompraregalos orden) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.merge(orden);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return orden;
    }

    public static Ordencompraregalos getOrdenCompra(Integer idOrden) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Ordencompraregalos orden = null;
        tx.begin();
        try {

            orden = em.find(Ordencompraregalos.class, idOrden);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return orden;
    }

    public static void aprobarOrden(Integer idOrden, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Ordencompraregalos orden = em.find(Ordencompraregalos.class, idOrden);
            orden.setEstado("APROBADA");
            orden.setAceptador(usuario);
            orden.setFechaAceptada(new Date());
            em.merge(orden);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static void recibirOrden(Integer idOrden, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Ordencompraregalos orden = em.find(Ordencompraregalos.class, idOrden);
            orden.setEstado("RECIBIDA");
            orden.setRecibidor(usuario);
            orden.setFechaRecibida(new Date());
            for (Ordencompraregalodetalle detalle : orden.getOrdencompraregalodetalleList()) {
                Regalosinventario inventario = em.find(Regalosinventario.class, detalle.getRegalosinventario().getId());
                inventario.setCantidad(inventario.getCantidad()+ detalle.getCantidad());
                em.merge(inventario);
            }
            em.merge(orden);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static Solicitudregalos crearSolicitud(Solicitudregalos orden) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.persist(orden);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return orden;
    }

    public static Solicitudregalos guardarSolicitud(Solicitudregalos orden) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.merge(orden);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return orden;
    }

    public static Solicitudregalos getSolicitud(Integer idOrden) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Solicitudregalos orden = null;
        tx.begin();
        try {

            orden = em.find(Solicitudregalos.class, idOrden);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return orden;
    }

    public static void entregarSolicitud(Integer idOrden, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Solicitudregalos orden = em.find(Solicitudregalos.class, idOrden);
            orden.setEstado("ENTREGADA");
            orden.setAceptador(usuario);
            orden.setFechentrega(new Date());
            em.merge(orden);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static void recibirSolicitud(Integer idOrden, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Solicitudregalos orden = em.find(Solicitudregalos.class, idOrden);
            orden.setEstado("RECIBIDA");
            orden.setRecibidor(usuario);
            orden.setFecharecepcion(new Date());
            for (Solicitudregalodetalle detalle : orden.getSolicitudregalodetalleList()) {
                Regalosinventario inventario = em.find(Regalosinventario.class, detalle.getRegalosinventario().getId());
                inventario.setCantidad(inventario.getCantidad()- detalle.getCantidad());
                em.merge(inventario);
            }
            em.merge(orden);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static List<Solicitudregalos> getListaSoliciudes(Usuario idUsuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Solicitudregalos> lista = new ArrayList<Solicitudregalos>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Solicitudregalos> c = cq.from(Solicitudregalos.class);
            cq.select(c);
            lista = em.createQuery(cq).getResultList();
            //for (Iterator<Solicitudregalos> iterator = lista.iterator(); iterator.hasNext();) {
            //    Solicitudregalos next = iterator.next();
            //    if(idUsuario.getCasinoList().contains(next.))
            //}
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return lista;
    }

    public static Regalos removeRegalo(Regalos regalo) {
        regalo.setNombre(regalo.getNombre().toUpperCase());
        regalo.setDescripcion(regalo.getDescripcion().toUpperCase());
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.remove(em.merge(regalo));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return regalo;
    }

    public static void saveInvenratioRegalos(InventarioRegalosDTO inventario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            for (RegalosCantidadDTO regalo : inventario.getInventario()) {
                Regalosinventario inventarioO = em.find(Regalosinventario.class, regalo.getId());
                inventarioO.setMin(regalo.getMin());
                inventarioO.setMax(regalo.getMax());
                em.merge(inventarioO);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }
}
