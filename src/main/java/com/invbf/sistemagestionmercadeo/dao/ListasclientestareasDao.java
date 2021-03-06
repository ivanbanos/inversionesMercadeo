/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dao;

import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Listasclientestareas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author ideacentre
 */
public class ListasclientestareasDao {

    public static void refresh(Listasclientestareas l) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Listasclientestareas listasclientestareas = null;

        tx.begin();
        try {
            listasclientestareas = em.find(Listasclientestareas.class, l.getListasclientestareasPK());
            em.refresh(listasclientestareas);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public ListasclientestareasDao() {
    }

    public static void create(Listasclientestareas listasclientestareas) {
        if (listasclientestareas.getObservaciones() != null) {
            listasclientestareas.setObservaciones(listasclientestareas.getObservaciones().toUpperCase());
        }
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.persist(listasclientestareas);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static void edit(Listasclientestareas listasclientestareas) {
        if (listasclientestareas.getObservaciones() != null) {
            listasclientestareas.setObservaciones(listasclientestareas.getObservaciones().toUpperCase());
        }
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        System.out.println("guardando");
        tx.begin();

        System.out.println("transaccion empesada");
        try {

            em.merge(listasclientestareas);
            if (listasclientestareas.getTarea().getTipo().getIdTipotarea() == 6) {
                Cliente  cliente = em.find(Cliente.class, listasclientestareas.getCliente().getIdCliente());
                cliente.setPorActualizar(1);
                cliente.setObservacionesAct(listasclientestareas.getObservaciones());
                em.merge(cliente);
            }
            System.out.println("gurdado");
            tx.commit();

            System.out.println("comit");
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static void remove(Listasclientestareas listasclientestareas) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.remove(em.merge(listasclientestareas));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static Listasclientestareas find(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Listasclientestareas listasclientestareas = null;

        tx.begin();
        try {
            listasclientestareas = em.find(Listasclientestareas.class, id);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return listasclientestareas;
    }

    public static List<Listasclientestareas> findAll() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Listasclientestareas> lista = new ArrayList<Listasclientestareas>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Listasclientestareas.class));
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

    public static List<Listasclientestareas> findRange(int[] range) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Listasclientestareas> lista = new ArrayList<Listasclientestareas>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Listasclientestareas.class));
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
            javax.persistence.criteria.Root<Listasclientestareas> rt = cq.from(Listasclientestareas.class);
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

    public static List<Listasclientestareas> findByIdTarea(Integer idTarea) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Listasclientestareas> listaClienteTarea = null;
        tx.begin();
        try {
            listaClienteTarea = (List<Listasclientestareas>) em.createNamedQuery("Listasclientestareas.findByIdTarea")
                    .setParameter("idTarea", idTarea)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return listaClienteTarea;
    }

    public static List<Listasclientestareas> findByIdTareaInicial(Integer integer) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Listasclientestareas> listaClienteTarea = new ArrayList<Listasclientestareas>();
        tx.begin();
        try {
            listaClienteTarea = (List<Listasclientestareas>) em.createNamedQuery("Listasclientestareas.findByIdTareaInicial")
                    .setParameter("idTarea", integer)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return listaClienteTarea;
    }
}
