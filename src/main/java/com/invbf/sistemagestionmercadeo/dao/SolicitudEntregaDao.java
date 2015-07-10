/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dao;

import com.invbf.sistemagestionmercadeo.entity.Clienteblanco;
import com.invbf.sistemagestionmercadeo.entity.ControlsalidabonosHasLotesbonos;
import com.invbf.sistemagestionmercadeo.entity.ControlsalidabonosHasLotesbonosPK;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentrega;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
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
public class SolicitudEntregaDao {

    public static void verificar() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            em.createNamedQuery("Solicitudentrega.revisarestados")
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

    public static List<Solicitudentrega> findByIdCreadorEstado(Integer id, String estado) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Solicitudentrega> cargos = null;
        tx.begin();
        try {
            cargos = (List<Solicitudentrega>) em.createNamedQuery("Solicitudentrega.findBySolicitanteestado")
                    .setParameter("solicitante", id)
                    .setParameter("estado", estado)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();

        return cargos;
    }

    public static List<Solicitudentrega> findByNoVenc() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Solicitudentrega> cargos = null;
        tx.begin();
        try {
            cargos = (List<Solicitudentrega>) em.createNamedQuery("Solicitudentrega.novencido")
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

    public static List<Solicitudentrega> findByVenc() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Solicitudentrega> cargos = null;
        tx.begin();
        try {
            cargos = (List<Solicitudentrega>) em.createNamedQuery("Solicitudentrega.vencido")
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

    public static List<Solicitudentrega> findBySolVenc(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Solicitudentrega> cargos = null;
        tx.begin();
        try {
            cargos = (List<Solicitudentrega>) em.createNamedQuery("Solicitudentrega.vencidoSol")
                    .setParameter("solicitante", id)
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

    public static List<Solicitudentrega> findByCasinoUsuario(Usuario usuario) {
        if (usuario.getCasinoList().isEmpty()) {
            return new ArrayList<Solicitudentrega>();
        } else {
            String query = "SELECT s FROM Solicitudentrega s WHERE (s.estado != 'REPORTE DE GESTIÓN DISPONIBLE') AND ( s.idCasino.idCasino = " + usuario.getCasinoList().get(0).getIdCasino();
            for (int i = 1; i < usuario.getCasinoList().size(); i++) {
                query += " OR s.idCasino.idCasino = " + usuario.getCasinoList().get(i).getIdCasino();
            }
            query+=" ) ";
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("AdminClientesPU");
            EntityManager em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            List<Solicitudentrega> cargos = null;
            tx.begin();
            try {
                cargos = (List<Solicitudentrega>) em.createQuery(query)
                        .getResultList();
                tx.commit();
            } catch (Exception e) {
                System.out.println(e);
                tx.rollback();
            }

            em.clear();
            em.close();
            emf.close();

            return cargos;
        }
    }

    public static long countpreaprobarsolicitud() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        long cargos = 0;
        tx.begin();
        try {
            cargos = em.createNamedQuery("Solicitudentrega.creados", Long.class).getSingleResult();
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        System.out.println("cantodad creadas: "+cargos);
        return cargos;
    }

    public static long countaprobarsolicitud() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        long cargos = 0;
        tx.begin();
        try {
            cargos = em.createNamedQuery("Solicitudentrega.preaprobados", Long.class).getSingleResult();
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        System.out.println("cantodad creadas: "+cargos);

        return cargos;
    }

    public SolicitudEntregaDao() {
    }

    public static void create(Solicitudentrega cargo) {

        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.persist(cargo);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static void edit(Solicitudentrega cargo) {

        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            System.err.println(cargo.getFechavencimientobonos());
            em.merge(cargo);
            System.err.println(cargo.getFechavencimientobonos());
            tx.commit();
        } catch (Exception e) {
            System.err.println(e);
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static void remove(Solicitudentrega cargo) {
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

    public static Solicitudentrega find(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Solicitudentrega cargo = null;

        tx.begin();
        try {
            cargo = em.find(Solicitudentrega.class, id);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return cargo;
    }

    public static List<Solicitudentrega> findAll() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Solicitudentrega> lista = new ArrayList<Solicitudentrega>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Solicitudentrega.class));
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

    public static List<Solicitudentrega> findRange(int[] range) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Solicitudentrega> lista = new ArrayList<Solicitudentrega>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Solicitudentrega.class));
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
            javax.persistence.criteria.Root<Solicitudentrega> rt = cq.from(Solicitudentrega.class);
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

    public static List<Solicitudentrega> findByIdCreador(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Solicitudentrega> cargos = null;
        tx.begin();
        try {
            cargos = (List<Solicitudentrega>) em.createNamedQuery("Solicitudentrega.novencidoSol")
                    .setParameter("solicitante", id)
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
}
