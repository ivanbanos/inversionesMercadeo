/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dao;

import com.invbf.sistemagestionmercadeo.entity.Bono;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Controlsalidabono;
import com.invbf.sistemagestionmercadeo.entity.Denominacion;
import com.invbf.sistemagestionmercadeo.util.CasinoBoolean;
import com.invbf.sistemagestionmercadeo.util.PropositosBoolean;
import com.invbf.sistemagestionmercadeo.util.TipoBonoBoolean;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

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

        em.clear();
        em.close();
        emf.close();
        return cargos;
    }

    public static List<Bono> getBonosRangoFechaYCasino(Date desde, Date hasta, Casino casino) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Bono> cargos = null;
        tx.begin();
        try {
            cargos = (List<Bono>) em.createNamedQuery("Bono.findByRangoFechasCasino")
                    .setParameter("desde", desde)
                    .setParameter("hasta", hasta)
                    .setParameter("casino", casino)
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

    public static List<Bono> getBonosPorAtributos(String estado, Casino casinoSelected, String nombres, String apellidos, String identificacion, String consecutivo) {
        if (nombres != null) {
            nombres = nombres.toUpperCase();
        } else {
            nombres = "";
        }
        System.out.println(nombres);
        if (apellidos != null) {
            apellidos = apellidos.toUpperCase();
        } else {
            apellidos = "";
        }
        System.out.println(apellidos);
        if (identificacion != null) {
            identificacion = identificacion.toUpperCase();
        } else {
            identificacion = "";
        }
        System.out.println(identificacion);
        if (consecutivo != null) {
            consecutivo = consecutivo.toUpperCase();
        } else {
            consecutivo = "";
        }
        System.out.println(consecutivo);
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Bono> cargos = null;
        tx.begin();
        try {
            String query = "SELECT b FROM Bono b WHERE b.estado = :estado AND b.tipo.nombre = 'NO PROMOCIONAL' AND b.casino = :casino AND b.cliente.nombres LIKE :nombres AND b.cliente.apellidos LIKE :apellidos AND b.cliente.identificacion LIKE :identificacion AND b.consecutivo LIKE :consecutivo";
            cargos = (List<Bono>) em.createQuery(query)
                    .setParameter("estado", estado)
                    .setParameter("casino", casinoSelected)
                    .setParameter("nombres", nombres + '%')
                    .setParameter("apellidos", apellidos + '%')
                    .setParameter("identificacion", identificacion + '%')
                    .setParameter("consecutivo", '%' + consecutivo + '%')
                    .getResultList();
            String query2 = "SELECT b FROM Bono b WHERE b.estado = :estado AND b.tipo.nombre = 'PROMOCIONAL' AND b.casino = :casino b.consecutivo LIKE :consecutivo";
            cargos.addAll((List<Bono>) em.createQuery(query2)
                    .setParameter("estado", estado)
                    .setParameter("casino", casinoSelected)
                    .setParameter("consecutivo", '%' + consecutivo + '%')
                    .getResultList());

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

    public static List<Bono> getBonosRangoFechaYCasino(Date desde, Date hasta, List<CasinoBoolean> casinos, List<PropositosBoolean> propositos, String nombre, String apellidos) {
        if (nombre == null) {
            nombre = "";
        }
        nombre = nombre.toUpperCase();
        if (apellidos == null) {
            apellidos = "";
        }
        apellidos = apellidos.toUpperCase();
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
            CriteriaBuilder cb = em.getCriteriaBuilder();
            javax.persistence.criteria.CriteriaQuery<Bono> cq = cb.createQuery(Bono.class);
            Root<Bono> c = cq.from(Bono.class);
            cq.select(c);

            ParameterExpression<Integer> nom = cb.parameter(Integer.class);
            ParameterExpression<Integer> ape = cb.parameter(Integer.class);
            cq.where(cb.equal(c.get("apellidos"), ape), cb.equal(c.get("nombres"), nom));
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

    public static List<Bono> findFechas(Date desde, Date hasta) {
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
            System.out.println(cargos.size());
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return cargos;
    }

    public static void revisarEstadoBonos() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            em.createNamedQuery("Bono.revisarestados")
                    .executeUpdate();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static Bono buscarBono(Casino casinoSelected, Denominacion denoinacionSelected, String consecutivo) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Bono> cargos = null;
        tx.begin();
        try {
            cargos = (List<Bono>) em.createNamedQuery("Bono.findBonoEsp")
                    .setParameter("casino", casinoSelected)
                    .setParameter("denominacion", denoinacionSelected)
                    .setParameter("consecutivo", consecutivo)
                    .getResultList();
            System.out.println(cargos.size());
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        if (cargos == null || cargos.isEmpty()) {
            return null;
        }
        return cargos.get(0);
    }

    public static List<Bono> getBonoPorCasinoDenominacionConsecutivo(String estado, Casino casinoSelected, String consecutivo, Denominacion denominacion) {
        consecutivo = consecutivo.toUpperCase();
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Bono> cargos = null;
        tx.begin();
        try {
            String query = "SELECT b FROM Bono b WHERE b.estado = :estado AND b.consecutivo  = :consecutivo AND b.denominacion = :denominacion";
            cargos = (List<Bono>) em.createQuery(query)
                    .setParameter("estado", estado)
                    .setParameter("consecutivo", consecutivo)
                    .setParameter("denominacion", denominacion)
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

    public static List<Bono> getBonosporCasinoPropositoTipoFecha(List<CasinoBoolean> casinos, List<PropositosBoolean> propositos, List<TipoBonoBoolean> tipos, Integer ano, Integer mes, Integer anodesde, Integer mesdesde) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Bono> cargos = new ArrayList<Bono>();
        tx.begin();
        Calendar desde = Calendar.getInstance();
        Calendar hasta = Calendar.getInstance();

        hasta.set(Calendar.YEAR, ano);
        hasta.set(Calendar.MONTH, mes);
        hasta.add(Calendar.MONTH, 1);
        hasta.set(Calendar.DAY_OF_MONTH, 1);

        desde.set(Calendar.YEAR, anodesde);
        desde.set(Calendar.MONTH, mesdesde);
        desde.set(Calendar.DAY_OF_MONTH, 1);
        try {
            for (CasinoBoolean cb : casinos) {
                if (cb.isSelected()) {
                    for (PropositosBoolean pb : propositos) {
                        if (pb.isSelected()) {
                            cargos.addAll(em.createNamedQuery("Bono.getReporteBono")
                                    .setParameter("desde", desde.getTime())
                                    .setParameter("hasta", hasta.getTime())
                                    .setParameter("casino", cb.getCasino())
                                    .setParameter("proposito", pb.getPropositoentrega())
                                    .getResultList());
                        }
                    }
                }
            }

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

    public static void editList(List<Bono> bonos, Controlsalidabono elemento) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            em.createNamedQuery("Bono.cambiarestadoBonoByControl")
                    .setParameter("control", elemento)
                    .executeUpdate();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static void getBonosPorCasino(List<CasinoBoolean> casinos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

        em.clear();
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

        em.clear();
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

        em.clear();
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

        em.clear();
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
        em.clear();
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
            javax.persistence.criteria.Root<Bono> rt = cq.from(Bono.class);
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

        em.clear();
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

        em.clear();
        em.close();
        emf.close();
        return cargos;
    }

    public static List<Bono> getBonosPorCasino(List<CasinoBoolean> casinos, Integer ano, Integer mes, Integer annodesde, Integer mesdesde) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Bono> cargos = new ArrayList<Bono>();
        tx.begin();
        Calendar desde = Calendar.getInstance();
        Calendar hasta = Calendar.getInstance();

        hasta.set(Calendar.YEAR, ano);
        hasta.set(Calendar.MONTH, mes);
        hasta.add(Calendar.MONTH, 1);
        hasta.set(Calendar.DAY_OF_MONTH, 1);

        desde.set(Calendar.YEAR, annodesde);
        desde.set(Calendar.MONTH, mesdesde);
        desde.set(Calendar.DAY_OF_MONTH, 1);
        try {
            for (CasinoBoolean cb : casinos) {
                if (cb.isSelected()) {
                    cargos.addAll(em.createNamedQuery("Bono.getReporteBonoVer")
                            .setParameter("desde", desde.getTime())
                            .setParameter("hasta", hasta.getTime())
                            .setParameter("casino", cb.getCasino())
                            .getResultList());

                }
            }

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
