/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dao;

import com.invbf.sistemagestionmercadeo.entity.Barajas;
import com.invbf.sistemagestionmercadeo.entity.Inventarobarajas;
import com.invbf.sistemagestionmercadeo.entity.Materialesbarajas;
import com.invbf.sistemagestionmercadeo.entity.Ordencomprabaraja;
import com.invbf.sistemagestionmercadeo.entity.Ordencomprabarajadetalle;
import com.invbf.sistemagestionmercadeo.entity.Solicitudbarajadetalle;
import com.invbf.sistemagestionmercadeo.entity.Solicitudbarajas;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

/**
 *
 * @author ivan
 */
public class GestionBarajasDao {

    public static List<Barajas> getListaBArajas() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Barajas> lista = new ArrayList<Barajas>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Barajas> c = cq.from(Barajas.class);
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

    public static List<Materialesbarajas> getListaMateriales() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Materialesbarajas> lista = new ArrayList<Materialesbarajas>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Materialesbarajas> c = cq.from(Materialesbarajas.class);
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

    public static Materialesbarajas addMaterial(Materialesbarajas material) {
        material.setNombre(material.getNombre().toUpperCase());
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            if (material.getId() == null) {
                em.persist(material);
            } else {
                em.merge(material);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return material;
    }

    public static void deleteMaterial(Materialesbarajas material) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            System.out.println("Removiendo material " + material.getId());

            material = em.find(Materialesbarajas.class, material.getId());
            em.remove(material);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static Barajas addBaraja(Barajas baraja) {
        baraja.setColor(baraja.getColor().toUpperCase());
        baraja.setMarca(baraja.getMarca().toUpperCase());
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            if (baraja.getId() == null) {
                em.persist(baraja);
            } else {
                Barajas barajac = em.find(Barajas.class, baraja.getId());
                barajac.setColor(baraja.getColor());
                barajac.setMarca(baraja.getMarca());
                barajac.setMaterial(baraja.getMaterial());
                barajac.setValorpromedio(baraja.getValorpromedio());
                em.merge(barajac);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return baraja;
    }

    public static Barajas addInventarioBaraja(Barajas baraja) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Inventarobarajas ib = new Inventarobarajas();
            ib.setBaraja(baraja);
            ib.setCantidadbarajas(0);
            em.persist(ib);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return baraja;
    }

    public static void deleteBaraja(Barajas transformarBaraja) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {

            transformarBaraja = em.find(Barajas.class, transformarBaraja.getId());
            em.remove(transformarBaraja);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static List<Inventarobarajas> getListaInvenratioBarajas() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Inventarobarajas> lista = new ArrayList<Inventarobarajas>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Inventarobarajas> c = cq.from(Inventarobarajas.class);
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

    public static List<Ordencomprabaraja> getListaOrdenesCompraBarajas() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Ordencomprabaraja> lista = new ArrayList<Ordencomprabaraja>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ordencomprabaraja> c = cq.from(Ordencomprabaraja.class);
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

    public static List<Solicitudbarajas> getListaSoliciudesBarajas() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Solicitudbarajas> lista = new ArrayList<Solicitudbarajas>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Solicitudbarajas> c = cq.from(Solicitudbarajas.class);
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

    public static Ordencomprabaraja crearOrdenCompra(Ordencomprabaraja orden) {
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

    public static Ordencomprabaraja guardarOrdenCompra(Ordencomprabaraja orden) {
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

    public static Ordencomprabaraja getOrdenCompraBaraja(Integer idOrden) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Ordencomprabaraja orden = null;
        tx.begin();
        try {

            orden = em.find(Ordencomprabaraja.class, idOrden);
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
            Ordencomprabaraja orden = em.find(Ordencomprabaraja.class, idOrden);
            orden.setEsatdo("APROBADA");
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
            Ordencomprabaraja orden = em.find(Ordencomprabaraja.class, idOrden);
            orden.setEsatdo("RECIBIDA");
            orden.setRecibidor(usuario);
            orden.setFechaRecibida(new Date());
            for (Ordencomprabarajadetalle detalle : orden.getOrdencomprabarajadetalleList()) {
                Inventarobarajas inventario = em.find(Inventarobarajas.class, detalle.getInventarobarajas().getId());
                inventario.setCantidadbarajas(inventario.getCantidadbarajas() + detalle.getCantidad());
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

    public static Solicitudbarajas crearSolicitudBarajas(Solicitudbarajas orden) {
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

    public static Solicitudbarajas guardarSolicitudBarajas(Solicitudbarajas orden) {
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

    public static Solicitudbarajas getSolicitudBaraja(Integer idOrden) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Solicitudbarajas orden = null;
        tx.begin();
        try {

            orden = em.find(Solicitudbarajas.class, idOrden);
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
            Solicitudbarajas orden = em.find(Solicitudbarajas.class, idOrden);
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
            Solicitudbarajas orden = em.find(Solicitudbarajas.class, idOrden);
            orden.setEstado("RECIBIDA");
            orden.setRecibidor(usuario);
            orden.setFecharecepcion(new Date());
            for (Solicitudbarajadetalle detalle : orden.getSolicitudbarajadetalleList()) {
                Inventarobarajas inventario = em.find(Inventarobarajas.class, detalle.getInventarobarajas().getId());
                inventario.setCantidadbarajas(inventario.getCantidadbarajas() - detalle.getCantidad());
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

    public static List<Solicitudbarajas> getListaSoliciudesBarajas(int idUsuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Solicitudbarajas> lista = new ArrayList<Solicitudbarajas>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Solicitudbarajas> c = cq.from(Solicitudbarajas.class);
            cq.select(c);
            cq.where(em.getCriteriaBuilder().equal(c.get("solicitante"), new Usuario(idUsuario)));
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
}
