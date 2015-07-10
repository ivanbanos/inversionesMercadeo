/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dao;

import com.invbf.sistemagestionmercadeo.entity.Bono;
import com.invbf.sistemagestionmercadeo.entity.Clienteblanco;
import com.invbf.sistemagestionmercadeo.entity.Controlsalidabono;
import com.invbf.sistemagestionmercadeo.entity.ControlsalidabonosHasLotesbonos;
import com.invbf.sistemagestionmercadeo.entity.ControlsalidabonosHasLotesbonosPK;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentrega;
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
public class ControlsalidabonosDao {

    public static Controlsalidabono finish(Controlsalidabono elemento) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx2 = em.getTransaction();
        tx2.begin();
        try {
            System.out.println("1");
            for (ControlsalidabonosHasLotesbonos csbhlb1 : elemento.getControlsalidabonosHasLotesbonosList()) {
                System.out.println("2");
                csbhlb1.setControlsalidabono(elemento);
                System.out.println("3");
                csbhlb1.setControlsalidabonosHasLotesbonosPK(new ControlsalidabonosHasLotesbonosPK(elemento.getId(), csbhlb1.getLotebono().getId()));
                List<Clienteblanco> cbl = csbhlb1.getClienteblancoList() == null ? new ArrayList<Clienteblanco>() : csbhlb1.getClienteblancoList();
                System.out.println("Cant clb" + cbl.size());
                for (Clienteblanco clientesBlanco1 : cbl) {
                    clientesBlanco1.setControlsalidabonosHasLotesbonos(csbhlb1);
                    System.out.println("Cant pre" + clientesBlanco1.getCantPre());
                }
                System.out.println("4");
                em.merge(csbhlb1);
                System.out.println("5");
            }
            em.merge(elemento);
            System.out.println("6");
            tx2.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx2.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return elemento;
    }

    public static List<Controlsalidabono> findAllButPRESOLICITADA() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Controlsalidabono> cargos = null;
        tx.begin();
        try {
            cargos = (List<Controlsalidabono>) em.createNamedQuery("Controlsalidabono.findAllButPRESOLICITADA")
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

    public static long countPorDiligenciar() {
         EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        long cargos = 0;
        tx.begin();
        try {
            cargos = em.createNamedQuery("Controlsalidabono.findcountPorDiligenciar", Long.class).getSingleResult();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();

        return cargos;
       
    }

    public static long countconfirmarrecepcion() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        long cargos = 0;
        tx.begin();
        try {
            cargos = em.createNamedQuery("Controlsalidabono.confirmarrecepcion", Long.class).getSingleResult();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();

        return cargos;
    }

    public static long countconfimacionordenretiro() {EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        long cargos = 0;
        tx.begin();
        try {
            cargos = em.createNamedQuery("Controlsalidabono.confirmarretiro", Long.class).getSingleResult();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();

        return cargos;
    }

    public ControlsalidabonosDao() {
    }

    public static void create(Controlsalidabono cargo) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.persist(cargo);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static Controlsalidabono editwb(Controlsalidabono cargo) {

        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<ControlsalidabonosHasLotesbonos> csbhlb = cargo.getControlsalidabonosHasLotesbonosList() != null ? cargo.getControlsalidabonosHasLotesbonosList() : new ArrayList<ControlsalidabonosHasLotesbonos>();

        tx.begin();
        try {
            System.out.println("Sigue todo");
            for (Bono b : cargo.getBonoList()) {
                em.merge(b);
            }
            if (cargo.getId() == null) {
                em.persist(cargo);
            }
            em.merge(cargo.getSolicitudEntregaid());
            em.merge(cargo);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return cargo;
    }

    public static Controlsalidabono edit(Controlsalidabono cargo) {

        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<ControlsalidabonosHasLotesbonos> csbhlb = cargo.getControlsalidabonosHasLotesbonosList() != null ? cargo.getControlsalidabonosHasLotesbonosList() : new ArrayList<ControlsalidabonosHasLotesbonos>();

        tx.begin();
        try {
            System.out.println("Sigue todo");
            System.out.println(cargo.getSolicitudEntregaid().getFecha());
            if (cargo.getId() == null) {
                em.persist(cargo);
            }
            em.merge(cargo);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return cargo;
    }

    public static void remove(Controlsalidabono cargo) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.remove(em.merge(cargo));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static Controlsalidabono find(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Controlsalidabono cargo = null;

        tx.begin();
        try {
            cargo = em.find(Controlsalidabono.class, id);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return cargo;
    }

    public static List<Controlsalidabono> findAll() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Controlsalidabono> lista = new ArrayList<Controlsalidabono>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Controlsalidabono.class));
            lista = em.createQuery(cq).getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println(e);
        }

        em.clear();
        em.close();
        emf.close();
        return lista;
    }

    public static List<Controlsalidabono> findRange(int[] range) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Controlsalidabono> lista = new ArrayList<Controlsalidabono>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Controlsalidabono.class));
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
            javax.persistence.criteria.Root<Controlsalidabono> rt = cq.from(Controlsalidabono.class);
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
