/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dao;

import com.invbf.sistemagestionmercadeo.entity.Cliente;
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
public class ClienteDao {

    public static List<Cliente> findClientes(Cliente cliente) {
        if (cliente.getNombres() != null) {
            System.out.println(cliente.getNombres());
            cliente.setNombres(cliente.getNombres().toUpperCase());
        }
        if (cliente.getApellidos() != null) {
            System.out.println(cliente.getApellidos());
            cliente.setApellidos(cliente.getApellidos().toUpperCase());
        }
        if (cliente.getIdentificacion() != null) {
            System.out.println(cliente.getIdentificacion());
            cliente.setIdentificacion(cliente.getIdentificacion().toUpperCase());
        }
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Cliente> clientes = new ArrayList<Cliente>();
        StringBuilder query = new StringBuilder("SELECT c FROM Cliente c");
        if (cliente.getNombres() != null && !cliente.getNombres().equals("")) {
            query.append(" WHERE c.nombres LIKE '%").append(cliente.getNombres()).append("%'");
        }
        if (cliente.getApellidos() != null && !cliente.getApellidos().equals("")) {
            query.append(" WHERE c.apellidos LIKE '%").append(cliente.getApellidos()).append("%'");
        }
        if (cliente.getIdentificacion() != null && !cliente.getIdentificacion().equals("")) {
            query.append(" WHERE c.identificacion LIKE '%").append(cliente.getIdentificacion()).append("%'");
        }
        System.out.println("Query" + query.toString());
        tx.begin();
        try {

            clientes = (List<Cliente>) em.createQuery(query.toString())
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
        return clientes;
    }

    public ClienteDao() {
    }

    public static void create(Cliente cliente) {
        cliente.setNombres(cliente.getNombres().toUpperCase());
        cliente.setApellidos(cliente.getApellidos().toUpperCase());
        cliente.setCiudad(cliente.getCiudad().toUpperCase());
        cliente.setPais(cliente.getPais().toUpperCase());
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.persist(cliente);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
    }

    public static void edit(Cliente cliente) {
        cliente.setNombres(cliente.getNombres().toUpperCase());
        cliente.setApellidos(cliente.getApellidos().toUpperCase());
        cliente.setCiudad(cliente.getCiudad().toUpperCase());
        cliente.setPais(cliente.getPais().toUpperCase());
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.merge(cliente);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
    }

    public static void remove(Cliente cliente) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.remove(em.merge(cliente));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
    }

    public static Cliente find(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Cliente cliente = null;

        tx.begin();
        try {
            cliente = em.find(Cliente.class, id);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
        return cliente;
    }

    public static List<Cliente> findAll() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Cliente> lista = new ArrayList<Cliente>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
            lista = em.createQuery(cq).getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
        return lista;
    }

    public static List<Cliente> findRange(int[] range) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Cliente> lista = new ArrayList<Cliente>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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
            javax.persistence.criteria.Root<Cliente> rt = cq.from(Cliente.class);
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

    public static List<Cliente> findByIdCasino(Integer idCasino) {

        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Cliente> cargos = null;
        tx.begin();
        try {
            cargos = (List<Cliente>) em.createNamedQuery("Cliente.findByCasino")
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
