/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dao;

import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Tipodocumento;
import com.invbf.sistemagestionmercadeo.util.FacesUtil;
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
public class ClienteDao {

    public static List<Cliente> findClientes(Cliente cliente) {
        if (cliente.getNombres() != null) {
            System.out.println(cliente.getNombres());
            cliente.setNombres(cliente.getNombres().toUpperCase());
        } else {
            cliente.setNombres("");
        }
        if (cliente.getApellidos() != null) {
            System.out.println(cliente.getApellidos());
            cliente.setApellidos(cliente.getApellidos().toUpperCase());
        } else {
            cliente.setApellidos("");
        }
        if (cliente.getIdentificacion() != null) {
            System.out.println(cliente.getIdentificacion());
            cliente.setIdentificacion(cliente.getIdentificacion().toUpperCase());
        } else {
            cliente.setIdentificacion("");
        }
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Cliente> cargos = null;
        tx.begin();
        try {
            cargos = (List<Cliente>) em.createNamedQuery("Cliente.findByAttr")
                    .setParameter("nombres", '%' + cliente.getNombres() + '%')
                    .setParameter("apellidos", '%' + cliente.getApellidos() + '%')
                    .setParameter("identificacion", '%' + cliente.getIdentificacion() + '%')
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

    public static boolean isIdentificacioniAsociatedCliente(Tipodocumento idTipoDocumento, String identificacion) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Cliente> cargos = null;
        tx.begin();
        try {
            cargos = (List<Cliente>) em.createNamedQuery("Cliente.findByIdent")
                    .setParameter("identificacion", identificacion)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();

        if (cargos == null) {
            return false;
        } else if (!cargos.isEmpty()) {
            return true;
        }
        return false;
    }

    public ClienteDao() {
    }

    public static void create(Cliente cliente) {
        cliente.setNombres(cliente.getNombres()!=null?cliente.getNombres().toUpperCase():"");
        cliente.setApellidos(cliente.getApellidos()!=null?cliente.getApellidos().toUpperCase():"");
        
        cliente.setCiudad(cliente.getCiudad()!=null?cliente.getCiudad().toUpperCase():"");
        cliente.setPais(cliente.getPais()!=null?cliente.getPais().toUpperCase():"");
        cliente.setBebida(cliente.getBebida()!=null?cliente.getBebida().toUpperCase():"");
        cliente.setComida(cliente.getComida()!=null?cliente.getComida().toUpperCase():"");
        cliente.setDescripcionPersonalidad(cliente.getDescripcionPersonalidad()!=null?cliente.getDescripcionPersonalidad().toUpperCase():"");
        cliente.setGustosPreferencias(cliente.getGustosPreferencias()!=null?cliente.getGustosPreferencias().toUpperCase():"");
        cliente.setMaquinapreferida(cliente.getMaquinapreferida()!=null?cliente.getMaquinapreferida().toUpperCase():"");
        cliente.setOcupacion(cliente.getOcupacion()!=null?cliente.getOcupacion().toUpperCase():"");
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

        em.clear();
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

        em.clear();
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
            cliente = em.find(Cliente.class, cliente.getIdCliente());
            em.remove(em.merge(cliente));
            tx.commit();
        } catch (Exception e) {
            FacesUtil.addErrorMessage("No se pudo eliminar a el clliente", "Tiene Bonos asignados");
            if (tx.isActive()) {
                tx.rollback();
            }
        }

        em.clear();
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

        em.clear();
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
            Root<Cliente> c = cq.from(Cliente.class);
            cq.select(c);
            cq.orderBy(em.getCriteriaBuilder().asc(c.get("nombres")));
            lista = em.createQuery(cq).getResultList();
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }

        em.clear();
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
            javax.persistence.criteria.Root<Cliente> rt = cq.from(Cliente.class);
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

        em.clear();
        em.close();
        emf.close();

        return cargos;
    }
}
