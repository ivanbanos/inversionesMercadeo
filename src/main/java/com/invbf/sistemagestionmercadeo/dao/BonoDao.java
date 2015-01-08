/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dao;

import com.invbf.sistemagestionmercadeo.entity.Bono;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author ivan
 */
public class BonoDao {

    public static List<Bono> getBonosRangoFecha(Date desde, Date hasta) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Bono> cargos = null;
        tx.begin();
        try {
            cargos = (List<Bono>) em.createNamedQuery("Bono.findByRangoFechas")
                    .setParameter("desde", desde)
                    .setParameter("hasta", hasta)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
        return cargos;
    }

    public BonoDao() {
    }

    public static void create(Bono casino) {

        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.persist(casino);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
    }

    public static void edit(Bono casino) {

        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.merge(casino);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
    }

    public static void remove(Bono casino) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.remove(em.merge(casino));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
    }

    public static Bono find(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Bono casino = null;

        tx.begin();
        try {
            casino = em.find(Bono.class, id);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
        return casino;
    }

    public static List<Bono> findAll() {

        System.out.println("Empezando la busqueda de casinos");
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        System.out.println("Emf");
        EntityManager em = emf.createEntityManager();
        System.out.println("Em");
        EntityTransaction tx = em.getTransaction();
        System.out.println("tx");
        List<Bono> lista = new ArrayList<Bono>();

        System.out.println("yx begin");
        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bono.class));
            lista = em.createQuery(cq).getResultList();
            System.out.println("lista lista");
            tx.commit();
            System.out.println("comit");
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }

        System.out.println("terminada la busqueda de casinos");
        em.close();
        emf.close();
        return lista;
    }

    public static List<Bono> findRange(int[] range) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Bono> lista = new ArrayList<Bono>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bono.class));
            javax.persistence.Query q = em.createQuery(cq);
            q.setMaxResults(range[1] - range[0]);
            q.setFirstResult(range[0]);
            lista = q.getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

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
            javax.persistence.criteria.Root<Bono> rt = cq.from(Bono.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            javax.persistence.Query q = em.createQuery(cq);
            count = ((Long) q.getSingleResult()).intValue();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
        return count;

    }

    public static List<Bono> getBonosPorEstado(String estado) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Bono> cargos = null;
        tx.begin();
        try {
            cargos = (List<Bono>) em.createNamedQuery("Bono.findByEstado")
                    .setParameter("estado", estado)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
        return cargos;
    }

    public static List<Bono> getBonosPorEstadoYCasino(String estado, Casino idCasino) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Bono> cargos = null;
        tx.begin();
        try {
            cargos = (List<Bono>) em.createNamedQuery("Bono.findByEstadoYCasino")
                    .setParameter("estado", estado)
                    .setParameter("casino", idCasino)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
        return cargos;
    }
}
