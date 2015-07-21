/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dao;

import com.invbf.sistemagestionmercadeo.dto.BarajasCantidad;
import com.invbf.sistemagestionmercadeo.dto.InventarioBarajasDTO;
import com.invbf.sistemagestionmercadeo.entity.Barajas;
import com.invbf.sistemagestionmercadeo.entity.Bodega;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Inventarobarajas;
import com.invbf.sistemagestionmercadeo.entity.Materialesbarajas;
import com.invbf.sistemagestionmercadeo.entity.Ordencomprabaraja;
import com.invbf.sistemagestionmercadeo.entity.Ordencomprabarajadetalle;
import com.invbf.sistemagestionmercadeo.entity.Solicitudbarajadetalle;
import com.invbf.sistemagestionmercadeo.entity.Solicitudbarajas;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.util.CasinoBoolean;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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
                em.flush();
                List<Bodega> lista;
                javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
                Root<Barajas> c = cq.from(Bodega.class);
                cq.select(c);
                lista = em.createQuery(cq).getResultList();
                for (Bodega bodega : lista) {
                    bodega.getInventarobarajasList().add(new Inventarobarajas(bodega, baraja));
                    em.merge(bodega);
                }
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

    public static Bodega getListaInvenratioBarajas(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Bodega bodega = new Bodega();

        tx.begin();
        try {
            bodega = em.find(Bodega.class, id);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return bodega;
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
        boolean creeOrden = false;
        tx.begin();

        try {
            Solicitudbarajas orden = em.find(Solicitudbarajas.class, idOrden);
            orden.setEstado("RECIBIDA");
            orden.setRecibidor(usuario);
            orden.setFecharecepcion(new Date());
            for (Solicitudbarajadetalle detalle : orden.getSolicitudbarajadetalleList()) {
                Inventarobarajas inventario = em.find(Inventarobarajas.class, detalle.getInventarobarajas().getId());
                inventario.setUso(inventario.getUso() + detalle.getCantidad());
                System.out.println("min" + inventario.getMin());
                System.out.println("cant" + (inventario.getCantidadbarajas() - inventario.getDestruidas() - inventario.getPordestruir() - inventario.getUso()));
                if (inventario.getMin() > (inventario.getCantidadbarajas() - inventario.getDestruidas() - inventario.getPordestruir() - inventario.getUso())&&!inventario.isOrdenActiva()) {
                    
                    creeOrden = true;
                }
                em.merge(inventario);
            }
            em.merge(orden);
            System.out.println("creando orden " + creeOrden);
            if (creeOrden) {
                Ordencomprabaraja ordencompra = new Ordencomprabaraja();
                ordencompra.setEsatdo("PREORDENADA");
                ordencompra.setOrdencomprabarajadetalleList(new ArrayList<Ordencomprabarajadetalle>());
                em.persist(ordencompra);
                em.flush();
                List<Inventarobarajas> lista;
                javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
                Root<Barajas> c = cq.from(Inventarobarajas.class);
                cq.select(c);
                lista = em.createQuery(cq).getResultList();
                for (Inventarobarajas inventariob : lista) {
                    Ordencomprabarajadetalle d = new Ordencomprabarajadetalle(ordencompra.getId(), inventariob.getId());
                    if ((inventariob.getCantidadbarajas() - inventariob.getDestruidas() - inventariob.getPordestruir() - inventariob.getUso()) < inventariob.getMax()) {
                        d.setCantidad(inventariob.getMax() - (inventariob.getCantidadbarajas() - inventariob.getDestruidas() - inventariob.getPordestruir() - inventariob.getUso()));

                    } else {
                        d.setCantidad(0);
                    }
                    em.merge(d);
                    ordencompra.getOrdencomprabarajadetalleList().add(d);
                }
                em.merge(ordencompra);
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
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

    public static List<Bodega> getBodegas() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Bodega> lista = new ArrayList<Bodega>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bodega> c = cq.from(Bodega.class);
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

    public static Integer crearBodega(String nombre) {
        if (nombre == null || nombre.equals("")) {
            nombre = "(sin nombre)";
        }
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        Bodega bodega = new Bodega();
        tx.begin();
        try {
            bodega.setNombre(nombre);
            em.persist(bodega);
            em.flush();
            List<Barajas> lista;
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Barajas> c = cq.from(Barajas.class);
            cq.select(c);
            lista = em.createQuery(cq).getResultList();
            bodega.setInventarobarajasList(new ArrayList<Inventarobarajas>());
            for (Barajas baraja : lista) {
                bodega.getInventarobarajasList().add(new Inventarobarajas(bodega, baraja));
            }
            em.merge(bodega);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return bodega.getId();
    }

    public static void guardarBodega(InventarioBarajasDTO inventario, List<CasinoBoolean> casinos) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Bodega bodega = new Bodega();

        tx.begin();
        try {
            bodega = em.find(Bodega.class, inventario.getId());
            bodega.setNombre(inventario.getNombre().toUpperCase());
            bodega.setCasinosList(new ArrayList<Casino>());
            for (Inventarobarajas invent : bodega.getInventarobarajasList()) {
                for (BarajasCantidad dto : inventario.getInventario()) {
                    System.out.println("invent "+invent.getId());
                    System.out.println("dto "+dto.getId());
                    if (invent.getId().equals(dto.getId())) {
                        invent.setMax(dto.getMax());
                        invent.setMin(dto.getMin());
                        em.merge(invent);
                    }
                }
            }
            for (CasinoBoolean casino : casinos) {
                Casino cas = em.find(Casino.class, casino.getCasino().getIdCasino());
                if (casino.isSelected()) {
                    cas.setBodega(bodega);
                    em.merge(cas);
                    bodega.getCasinosList().add(cas);
                }
            }
            em.merge(bodega);
            tx.commit();
        } catch (Exception e) {
            
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static List<Bodega> getBodegasUsusario(Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Bodega> lista = new ArrayList<Bodega>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bodega> c = cq.from(Bodega.class);
            cq.select(c);
            lista = em.createQuery(cq).getResultList();
            for (Iterator<Bodega> iterator = lista.iterator(); iterator.hasNext();) {
                Bodega next = iterator.next();

                for (Casino casino : next.getCasinosList()) {
                    if (!usuario.getCasinoList().contains(casino)) {
                        iterator.remove();
                    }
                }
            }
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
}
