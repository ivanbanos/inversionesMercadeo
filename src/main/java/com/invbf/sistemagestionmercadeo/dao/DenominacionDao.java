/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dao;

import com.invbf.sistemagestionmercadeo.entity.Denominacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author ivan
 */
public class DenominacionDao {
    public static void create(Denominacion elemento) {
        
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.persist(elemento);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static void edit(Denominacion elemento) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.merge(elemento);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static void remove(Denominacion elemento) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.remove(em.merge(elemento));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static Denominacion find(Integer id) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Denominacion elemento = null;

        tx.begin();
        try {
            elemento = em.find(Denominacion.class, id);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return elemento;
    }

    public static List<Denominacion> findAll() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Denominacion> lista = new ArrayList<Denominacion>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Denominacion.class));
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

    public static List<Denominacion> findRange(int[] range) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Denominacion> lista = new ArrayList<Denominacion>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Denominacion.class));
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
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        int count = 0;

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            javax.persistence.criteria.Root<Denominacion> rt = cq.from(Denominacion.class);
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
