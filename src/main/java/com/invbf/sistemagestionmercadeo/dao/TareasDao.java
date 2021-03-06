/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dao;

import com.invbf.sistemagestionmercadeo.entity.Accion;
import com.invbf.sistemagestionmercadeo.entity.Bono;
import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Listasclientestareas;
import com.invbf.sistemagestionmercadeo.entity.ListasclientestareasPK;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregacliente;
import com.invbf.sistemagestionmercadeo.entity.Tarea;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.Root;

/**
 *
 * @author ideacentre
 */
public class TareasDao {

    public static List<Tarea> GetTareaByUsuario(Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Tarea> cargos = null;
        tx.begin();
        try {
            cargos = (List<Tarea>) em.createNamedQuery("Tarea.findByEstado")
                    .setParameter("estado", "ACTIVA")
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();

        return cargos;
    }

    public static void checkEstadoTarea() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            em.createNamedQuery("Tarea.setActive")
                    .executeUpdate();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static void checkEstadoTareaVn() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            em.createNamedQuery("Tarea.setVencida")
                    .executeUpdate();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static Tarea createBonoFide(Tarea tarea, List<Bono> solicitudentregaclienteList, Accion estadoscliente) {
        if (tarea.getNombre() == null) {
            tarea.setNombre("");
        }
        if (tarea.getDescripcion() == null) {
            tarea.setDescripcion("");
        }
        if (tarea.getSpeech() == null) {
            tarea.setSpeech("");
        }
        tarea.setNombre(tarea.getNombre().toUpperCase());
        tarea.setDescripcion(tarea.getDescripcion().toUpperCase());
        tarea.setSpeech(tarea.getSpeech().toUpperCase());
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.persist(tarea);
            em.flush();

            tarea.setListasclientestareasList(new ArrayList<Listasclientestareas>());
            for (Bono b : solicitudentregaclienteList) {
                if (b.getEstado().equals("EN SALA")) {
                    System.out.println(b.getCliente());
                    Listasclientestareas lct = new Listasclientestareas();
                    lct.setIdAccion(estadoscliente);
                    lct.setCliente(b.getCliente());
                    lct.setTarea(tarea);
                    lct.setListasclientestareasPK(new ListasclientestareasPK(tarea.getIdTarea(), b.getCliente().getIdCliente()));
                    lct.setCount(0);
                    if (!tarea.getListasclientestareasList().contains(lct)) {
                        em.persist(lct);
                        tarea.getListasclientestareasList().add(lct);
                    }
                }
            }
            em.merge(tarea);
            tx.commit();
        } catch (Exception e) {

            System.out.println(e);
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();

        return tarea;
    }

    public TareasDao() {
    }

    public static Tarea create(Tarea tarea) {
        if (tarea.getNombre() == null) {
            tarea.setNombre("");
        }
        if (tarea.getDescripcion() == null) {
            tarea.setDescripcion("");
        }
        if (tarea.getSpeech() == null) {
            tarea.setSpeech("");
        }
        tarea.setNombre(tarea.getNombre().toUpperCase());
        tarea.setDescripcion(tarea.getDescripcion().toUpperCase());
        tarea.setSpeech(tarea.getSpeech().toUpperCase());
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.persist(tarea);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return tarea;
    }

    public static void edit(Tarea tarea) {
        if (tarea.getNombre() == null) {
            tarea.setNombre("");
        }
        if (tarea.getDescripcion() == null) {
            tarea.setDescripcion("");
        }
        if (tarea.getSpeech() == null) {
            tarea.setSpeech("");
        }
        tarea.setNombre(tarea.getNombre().toUpperCase());
        tarea.setDescripcion(tarea.getDescripcion().toUpperCase());
        tarea.setSpeech(tarea.getSpeech().toUpperCase());
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {

            List<Listasclientestareas> lcts = tarea.getListasclientestareasList();
            tarea.setListasclientestareasList(null);
            System.out.println("Canti clientes" + lcts.size());
            for (Listasclientestareas lct : lcts) {
                lct.setTarea(tarea);
                lct.setListasclientestareasPK(new ListasclientestareasPK(tarea.getIdTarea(), lct.getCliente().getIdCliente()));
                em.merge(lct);
            }
            tarea.setListasclientestareasList(lcts);
            em.merge(tarea);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static void remove(Tarea tarea) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.remove(em.merge(tarea));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static Tarea find(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Tarea tarea = null;

        tx.begin();
        try {
            tarea = em.find(Tarea.class, id);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return tarea;
    }

    public static List<Tarea> findAll() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Tarea> lista = new ArrayList<Tarea>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tarea> c = cq.from(Tarea.class);
            cq.select(c);
            cq.orderBy(em.getCriteriaBuilder().desc(c.get("fechaFinalizacion")));
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

    public static List<Tarea> findRange(int[] range) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Tarea> lista = new ArrayList<Tarea>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tarea.class));
            javax.persistence.Query q = em.createQuery(cq);
            q.setMaxResults(range[1] - range[0]);
            q.setFirstResult(range[0]);
            lista = q.getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return lista;
    }

    public static int count() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        int count = 0;

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            javax.persistence.criteria.Root<Tarea> rt = cq.from(Tarea.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            javax.persistence.Query q = em.createQuery(cq);
            count = ((Long) q.getSingleResult()).intValue();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return count;

    }
}
