/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dao;

import com.invbf.sistemagestionmercadeo.dto.ActaDestruccionDTO;
import com.invbf.sistemagestionmercadeo.dto.BarajasCantidad;
import com.invbf.sistemagestionmercadeo.dto.InventarioBarajasDTO;
import com.invbf.sistemagestionmercadeo.dto.OrdenCompraBarajaDTO;
import com.invbf.sistemagestionmercadeo.dto.SolicitudBarajasDTO;
import com.invbf.sistemagestionmercadeo.dto.TrasladoDTO;
import com.invbf.sistemagestionmercadeo.entity.Actasdestruccionbarajas;
import com.invbf.sistemagestionmercadeo.entity.Barajas;
import com.invbf.sistemagestionmercadeo.entity.Bono;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Destruccionbarajasmaestro;
import com.invbf.sistemagestionmercadeo.entity.Inventarobarajas;
import com.invbf.sistemagestionmercadeo.entity.Materialesbarajas;
import com.invbf.sistemagestionmercadeo.entity.Ordencomprabaraja;
import com.invbf.sistemagestionmercadeo.entity.Ordencomprabarajadetalle;
import com.invbf.sistemagestionmercadeo.entity.Solicitudbarajadetalle;
import com.invbf.sistemagestionmercadeo.entity.Solicitudbarajas;
import com.invbf.sistemagestionmercadeo.entity.Trasladobarajadetalle;
import com.invbf.sistemagestionmercadeo.entity.Trasladobarajas;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.util.CasinoBoolean;
import com.invbf.sistemagestionmercadeo.util.Notificador;
import com.invbf.sistemagestionmercadeo.util.PropositosBoolean;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.Expression;
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

    public static Materialesbarajas editMaterial(Materialesbarajas material) {
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
                List<Casino> lista;
                javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
                Root<Barajas> c = cq.from(Casino.class);
                cq.select(c);
                lista = em.createQuery(cq).getResultList();
                for (Casino bodega : lista) {
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

    public static Casino getListaInvenratioBarajas(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Casino bodega = new Casino();

        tx.begin();
        try {
            bodega = em.find(Casino.class, id);
            bodega.setInventarobarajasList(em.createNamedQuery("Inventarobarajas.findBySalaOrderByMaterial")
                    .setParameter("casino", id)
                    .getResultList());
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
            orden.setOrdencomprabarajadetalleList(em.createNamedQuery("Inventarobarajas.findBySalaOrderByMaterial")
                    .setParameter("orden", idOrden)
                    .getResultList());
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

    public static Ordencomprabaraja getOrdenCompraBaraja(Integer idOrden, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Ordencomprabaraja orden = null;
        tx.begin();
        try {

            orden = em.find(Ordencomprabaraja.class, idOrden);
            List<Ordencomprabarajadetalle> detalles = orden.getOrdencomprabarajadetalleList();
            for (Iterator<Ordencomprabarajadetalle> iterator = detalles.iterator(); iterator.hasNext();) {
                Ordencomprabarajadetalle inventario = iterator.next();
                boolean sedeja = false;
                if (usuario.getCasinoList().contains(inventario.getInventarobarajas().getCasino())) {
                    sedeja = true;
                    break;
                }

                if (!sedeja) {
                    iterator.remove();
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
        return orden;
    }

    public static void aprobarOrden(OrdenCompraBarajaDTO idOrden, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Ordencomprabaraja orden = em.find(Ordencomprabaraja.class, idOrden.getId());
            for (Iterator<Ordencomprabarajadetalle> iterator = orden.getOrdencomprabarajadetalleList().iterator(); iterator.hasNext();) {
                Ordencomprabarajadetalle next = iterator.next();
                for (BarajasCantidad barajas : idOrden.getCantidades()) {
                    if (barajas.getId().intValue() == next.getInventarobarajas().getId()) {
                        if (barajas.getCantidadR() == 0) {
                            iterator.remove();
                            em.remove(em.merge(next));
                        } else {
                            next.setCantidadAprobada(barajas.getCantidadR());
                        }
                    }
                }
            }
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

    public static void crearOrden(OrdenCompraBarajaDTO idOrden, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Ordencomprabaraja orden = em.find(Ordencomprabaraja.class, idOrden.getId());
            for (Iterator<Ordencomprabarajadetalle> iterator = orden.getOrdencomprabarajadetalleList().iterator(); iterator.hasNext();) {
                Ordencomprabarajadetalle next = iterator.next();
                for (BarajasCantidad barajas : idOrden.getCantidades()) {
                    if (barajas.getId().intValue() == next.getInventarobarajas().getId()) {
                        if (barajas.getCantidad() == 0) {
                            iterator.remove();
                            em.remove(em.merge(next));
                        } else {
                            next.setCantidad(barajas.getCantidad());
                        }
                    }
                }
            }
            orden.setEsatdo("GENERADA");
            orden.setCreador(usuario);
            orden.setFechaCreacion(new Date());
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
            //for (Ordencomprabarajadetalle detalle : orden.getOrdencomprabarajadetalleList()) {
            // Inventarobarajas inventario = em.find(Inventarobarajas.class, detalle.getInventarobarajas().getId());
            // inventario.setCantidadbarajas(inventario.getCantidadbarajas() + detalle.getCantidad());
            // em.merge(inventario);
            //}
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

    public static void recibirOrdenCaja(Integer idOrden, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Ordencomprabaraja orden = em.find(Ordencomprabaraja.class, idOrden);
            orden.setEsatdo("RECIBIDA");
            for (Ordencomprabarajadetalle detalle : orden.getOrdencomprabarajadetalleList()) {
                Inventarobarajas inventario = em.find(Inventarobarajas.class, detalle.getInventarobarajas().getId());

                if (usuario.getCasinoList().contains(inventario.getCasino())) {
                    inventario.setCantidadbarajas(inventario.getCantidadbarajas() + detalle.getCantidad());
                    em.merge(inventario);
                    detalle.setRecibidor(usuario);
                    detalle.setFecharecibida(new Date());
                    em.merge(detalle);
                }

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
                orden.setSolicitudbarajadetalleList(em.createNamedQuery("Solicitudbarajadetalle.findBySolicitudOrderByMaterial")
                        .setParameter("solicitud", orden.getId())
                        .getResultList());
            
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

    public static void entregarNuevasSolicitud(Integer idOrden, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean creeOrden = false;
        tx.begin();
        try {
            Solicitudbarajas orden = em.find(Solicitudbarajas.class, idOrden);
            orden.setEstado("ENTREGADAS/Pendientes por devolver");
            orden.setAceptador(usuario);
            orden.setFechentrega(new Date());

            for (Solicitudbarajadetalle detalle : orden.getSolicitudbarajadetalleList()) {
                Inventarobarajas inventario = em.find(Inventarobarajas.class, detalle.getInventarobarajas().getId());
                inventario.setUso(inventario.getUso() + detalle.getCantidad());
                System.out.println("min" + inventario.getMin());
                System.out.println("cant" + (inventario.getCantidadbarajas() - inventario.getDestruidas() - inventario.getPordestruir() - inventario.getUso()));
                if (inventario.getMin() >= (inventario.getCantidadbarajas() - inventario.getDestruidas() - inventario.getPordestruir() - inventario.getUso()) && !inventario.isOrdenActiva()) {

                    creeOrden = true;
                }
                em.merge(inventario);
            }
            orden.setEntregadasNuevas(new Date());
            Notificador.notificar(Notificador.correoLibre,
                    "Recuerde que debe entregar las barajas usadas de la solicitud con el n&uacute;mero de acta " + orden.getId() + ". Favor revisar la lista de solicitudes de barajas.",
                    "Aviso de entrega de barajas usadas", orden.getCreador().getUsuariodetalle().getCorreo());
            em.merge(orden);

            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }
        if (creeOrden) {
            crearNuevaOrdenPregenerada();
        }
        em.clear();
        em.close();
        emf.close();
    }

    public static void crearNuevaOrdenPregenerada() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq2 = em.getCriteriaBuilder().createQuery();
            Root<Ordencomprabaraja> c2 = cq2.from(Ordencomprabaraja.class);
            cq2.select(c2);
            Expression<String> name = c2.get("esatdo");
            cq2.where(
                    em.getCriteriaBuilder().notLike(name, "RECIBIDA"));
            int cant = em.createQuery(cq2).getResultList().size();
            boolean creeOrden = cant == 0;

            if (creeOrden) {
                Ordencomprabaraja ordencompra = new Ordencomprabaraja();
                ordencompra.setEsatdo("PREGENERADA");
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
                        continue;
                    }
                    em.merge(d);
                    ordencompra.getOrdencomprabarajadetalleList().add(d);
                }
                em.merge(ordencompra);
                em.flush();
                Notificador.notificar(Notificador.correoLimiteAlcanzadoBarajas,
                        "Se ha pregenerado el requerimiento de compra de barajas con el n&uacute;mero de acta " + ordencompra.getId() + ". Favor revisar la lista de requerimientos de compra de barajas.",
                        "Se ha pregenerado un requerimiento de compra de barajas", "");
            }
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static void recibirNuevasSolicitud(Integer idOrden, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Solicitudbarajas orden = em.find(Solicitudbarajas.class, idOrden);
            if (orden.getRecibidasUsadas() != null) {
                orden.setEstado("TERMINADA");
            } else {
                orden.setEstado("RECIBIDAS NUEVAS");
            }
            orden.setRecibidor(usuario);
            orden.setFecharecepcion(new Date());

            orden.setRecibidasNuevas(new Date());
            em.merge(orden);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static void entregarUsadasSolicitud(SolicitudBarajasDTO idOrden, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Solicitudbarajas orden = em.find(Solicitudbarajas.class, idOrden.getId());
            orden.setEstado("Pendiente por recibir usadas");
            for (Solicitudbarajadetalle detalle : orden.getSolicitudbarajadetalleList()) {
                for (BarajasCantidad det : idOrden.getCantidades()) {
                    System.out.println("Detalle");
                    System.out.println(det.getDevueltas());
                    System.out.println(detalle.getDevueltas());
                    if (detalle.getInventarobarajas().getId() == det.getId().intValue()) {
                        detalle.setDevueltas(det.getDevueltas());
                    }
                    em.merge(detalle);
                }
            }
            orden.setRecibidor(usuario);
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

    public static void recibirUsadasSolicitud(Integer idOrden, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Solicitudbarajas orden = em.find(Solicitudbarajas.class, idOrden);
            orden.setEstado("TERMINADA");

            List<Actasdestruccionbarajas> actas = new ArrayList<Actasdestruccionbarajas>();
            for (Solicitudbarajadetalle detalle : orden.getSolicitudbarajadetalleList()) {
                Inventarobarajas inventario = em.find(Inventarobarajas.class, detalle.getInventarobarajas().getId());
                inventario.setUso(inventario.getUso() - detalle.getDevueltas());

                inventario.setPordestruir(inventario.getPordestruir() + detalle.getDevueltas());
                if (detalle.getDevueltas() > 0) {
                    Actasdestruccionbarajas adb = new Actasdestruccionbarajas();
                    adb.setInventario(inventario);
                    adb.setCantidad(detalle.getDevueltas());
                    actas.add(adb);
                }
                orden.setEntregadasUsadas(new Date());
                em.merge(inventario);
            }
            orden.setRecibidasUsadas(new Date());

            em.merge(orden);
            if (!actas.isEmpty()) {
                Destruccionbarajasmaestro destruccionbarajasmaestro = new Destruccionbarajasmaestro();
                destruccionbarajasmaestro.setEstado("POR DESTRUIR");
                destruccionbarajasmaestro.setActasdestruccionbarajasList(new ArrayList<Actasdestruccionbarajas>());
                em.persist(destruccionbarajasmaestro);
                em.flush();
                for (Actasdestruccionbarajas acta : actas) {
                    acta.setActa(destruccionbarajasmaestro);
                    em.persist(acta);
                    destruccionbarajasmaestro.getActasdestruccionbarajasList().add(acta);
                }
                em.merge(destruccionbarajasmaestro);
            }
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static List<Solicitudbarajas> getListaSoliciudesBarajas(Usuario idUsuario) {
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
            cq.where(em.getCriteriaBuilder().notEqual(c.get("estado"), "TERMINADA"));
            lista = em.createQuery(cq).getResultList();
            for (Iterator<Solicitudbarajas> iterator = lista.iterator(); iterator.hasNext();) {
                Solicitudbarajas next = iterator.next();
                boolean sacar = true;
                for (Solicitudbarajadetalle soldet : next.getSolicitudbarajadetalleList()) {
                    if (idUsuario.getCasinoList().contains(soldet.getInventarobarajas().getCasino())) {
                        sacar = false;
                        break;
                    }
                }
                if (sacar) {
                    iterator.remove();
                }
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return lista;
    }

    public static List<Casino> getCasinos() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Casino> lista = new ArrayList<Casino>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Casino> c = cq.from(Casino.class);
            cq.select(c);
            lista = em.createQuery(cq).getResultList();
            for (Casino casino : lista) {
                casino.setInventarobarajasList(em.createNamedQuery("Inventarobarajas.findBySalaOrderByMaterial")
                        .setParameter("casino", casino.getIdCasino())
                        .getResultList());
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

    public static List<Casino> getBodegasCasinoUsusario(Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Casino> lista = new ArrayList<Casino>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Casino> c = cq.from(Casino.class);
            cq.select(c);
            lista = em.createQuery(cq).getResultList();
            for (Iterator<Casino> iterator = lista.iterator(); iterator.hasNext();) {
                Casino next = iterator.next();
                boolean sacar = true;
                if (usuario.getCasinoList().contains(next)) {
                    sacar = false;
                }

                if (sacar) {
                    iterator.remove();
                }
            }
            for (Casino casino : lista) {
                casino.setInventarobarajasList(em.createNamedQuery("Inventarobarajas.findBySalaOrderByMaterial")
                        .setParameter("casino", casino.getIdCasino())
                        .getResultList());
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

    public static Integer crearBodegaCasino(int id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Casino casino = em.find(Casino.class, id);
            em.flush();
            List<Barajas> lista;
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Barajas> c = cq.from(Barajas.class);
            cq.select(c);
            lista = em.createQuery(cq).getResultList();
            casino.setInventarobarajasList(new ArrayList<Inventarobarajas>());
            for (Barajas baraja : lista) {
                casino.getInventarobarajasList().add(new Inventarobarajas(casino, baraja));
            }
            em.merge(casino);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return id;
    }

    public static void guardarBodegaCasino(InventarioBarajasDTO inventario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Casino bodega = new Casino();

        tx.begin();
        try {
            bodega = em.find(Casino.class, inventario.getId());
            for (Inventarobarajas invent : bodega.getInventarobarajasList()) {
                for (BarajasCantidad dto : inventario.getInventario()) {
                    System.out.println("invent " + invent.getId());
                    System.out.println("dto " + dto.getId());
                    if (invent.getId().equals(dto.getId())) {
                        invent.setMax(dto.getMax());
                        invent.setMin(dto.getMin());
                        invent.setUso(dto.getUso());
                        em.merge(invent);
                    }
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

    public static Integer destruir(ActaDestruccionDTO acta, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        Destruccionbarajasmaestro dbm = null;
        tx.begin();
        try {
            dbm = em.find(Destruccionbarajasmaestro.class, acta.getId());
            for (Actasdestruccionbarajas invent : dbm.getActasdestruccionbarajasList()) {
                System.out.println(invent.getCantidad());
                Inventarobarajas inventario = invent.getInventario();
                inventario.setDestruidas(inventario.getDestruidas() + invent.getCantidad());
                inventario.setPordestruir(inventario.getPordestruir() - invent.getCantidad());
                em.merge(inventario);
            }
            dbm = em.find(Destruccionbarajasmaestro.class, acta.getId());
            dbm.setFechaDestruccion(new Date());
            dbm.setUsuario(usuario);
            dbm.setEstado("DESTRUIDAS");

            em.merge(dbm);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        em.clear();
        em.close();
        emf.close();
        return dbm == null ? 0 : dbm.getId();

    }

    public static List<Destruccionbarajasmaestro> getActasDestruccion() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Destruccionbarajasmaestro> lista = new ArrayList<Destruccionbarajasmaestro>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Destruccionbarajasmaestro> c = cq.from(Destruccionbarajasmaestro.class);
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

    public static Destruccionbarajasmaestro getDestruccionMaestro(Usuario usuario, Integer idOrden) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Destruccionbarajasmaestro material = null;
        tx.begin();
        try {

            material = em.find(Destruccionbarajasmaestro.class, idOrden);
            for (Iterator<Actasdestruccionbarajas> iterator = material.getActasdestruccionbarajasList().iterator(); iterator.hasNext();) {
                Actasdestruccionbarajas next = iterator.next();
                boolean sacar = true;
                if (usuario.getCasinoList().contains(next.getInventario().getCasino())) {
                    sacar = false;
                }

                if (sacar) {
                    iterator.remove();
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
        return material;
    }

    public static List<Ordencomprabaraja> getListaOrdenesCompraBarajasUsuario(Usuario usuario) {
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
            for (Iterator<Ordencomprabaraja> iterator = lista.iterator(); iterator.hasNext();) {
                Ordencomprabaraja next = iterator.next();
                boolean sedeja = false;
                salida:
                for (Iterator<Ordencomprabarajadetalle> iterator2 = next.getOrdencomprabarajadetalleList().iterator(); iterator.hasNext();) {
                    Ordencomprabarajadetalle inventario = iterator2.next();
                    if (usuario.getCasinoList().contains(inventario.getInventarobarajas().getCasino())) {
                        sedeja = true;
                        break salida;

                    }
                }

                if (!sedeja) {
                    iterator.remove();
                }
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return lista;
    }

    public static long getOrdenesGenerar() {

        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Ordencomprabaraja> ordenes = null;
        tx.begin();
        try {
            ordenes = em.createNamedQuery("Ordencomprabaraja.findByEsatdo")
                    .setParameter("esatdo", "PREGENERADA")
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return ordenes.size();

    }

    public static long getOrdenesAprobar() {

        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Ordencomprabaraja> ordenes = null;
        tx.begin();
        try {
            ordenes = em.createNamedQuery("Ordencomprabaraja.findByEsatdo")
                    .setParameter("esatdo", "GENERADA")
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return ordenes.size();

    }

    public static long getOrdenesPrRecibirCaja() {

        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Ordencomprabaraja> ordenes = null;
        tx.begin();
        try {
            ordenes = em.createNamedQuery("Ordencomprabaraja.findByEsatdo")
                    .setParameter("esatdo", "APROBADA")
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return ordenes.size();

    }

    public static long getNumRecibirBarajasCaja(Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Ordencomprabaraja> ordenes = null;
        tx.begin();
        long count = 0;
        try {
            ordenes = em.createNamedQuery("Ordencomprabaraja.findByEsatdo")
                    .setParameter("esatdo", "RECIBIDA")
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        for (Ordencomprabaraja ordene : ordenes) {

            for (Ordencomprabarajadetalle detalle : ordene.getOrdencomprabarajadetalleList()) {
                if (detalle.getRecibidor() == null) {
                    if (usuario.getCasinoList().contains(detalle.getInventarobarajas().getCasino())) {
                        count++;
                        break;
                    }

                }
            }
        }
        return count;
    }

    public static long getEntregarBarajanuevas(Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Solicitudbarajas> solicitudes = null;
        long count = 0;
        tx.begin();
        try {
            String query = "SELECT s FROM Solicitudbarajas s WHERE s.estado = 'SOLICITADA'";
            solicitudes = (List<Solicitudbarajas>) em.createQuery(query).getResultList();
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        for (Solicitudbarajas ordene : solicitudes) {
            for (Solicitudbarajadetalle detalle : ordene.getSolicitudbarajadetalleList()) {
                if (usuario.getCasinoList().contains(detalle.getInventarobarajas().getCasino())) {
                    count++;
                    break;
                }

            }
        }
        return count;
    }

    public static long getRecibirusadas(Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Solicitudbarajas> solicitudes = null;
        long count = 0;
        tx.begin();
        try {
            String query = "SELECT s FROM Solicitudbarajas s WHERE s.estado = 'Pendiente por recibir usadas'";
            solicitudes = (List<Solicitudbarajas>) em.createQuery(query).getResultList();
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        for (Solicitudbarajas ordene : solicitudes) {
            for (Solicitudbarajadetalle detalle : ordene.getSolicitudbarajadetalleList()) {
                if (usuario.getCasinoList().contains(detalle.getInventarobarajas().getCasino())) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    public static long getEntregarUsadas(Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Solicitudbarajas> solicitudes = null;
        long count = 0;
        tx.begin();
        try {
            String query = "SELECT s FROM Solicitudbarajas s WHERE s.estado = 'ENTREGADAS/Pendientes por devolver'";
            solicitudes = (List<Solicitudbarajas>) em.createQuery(query).getResultList();
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        for (Solicitudbarajas ordene : solicitudes) {
            for (Solicitudbarajadetalle detalle : ordene.getSolicitudbarajadetalleList()) {
                if (usuario.getCasinoList().contains(detalle.getInventarobarajas().getCasino())) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    public static long getREcibirNuevas(Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Solicitudbarajas> solicitudes = null;
        long count = 0;
        tx.begin();
        try {
            String query = "SELECT s FROM Solicitudbarajas s WHERE s.recibidasNuevas != null";
            solicitudes = (List<Solicitudbarajas>) em.createQuery(query).getResultList();
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        for (Solicitudbarajas ordene : solicitudes) {
            for (Solicitudbarajadetalle detalle : ordene.getSolicitudbarajadetalleList()) {
                if (usuario.getCasinoList().contains(detalle.getInventarobarajas().getCasino())) {
                    count++;
                }
            }
        }
        return count;
    }

    public static long getIfDestruir(Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Inventarobarajas> ordenes = null;
        tx.begin();
        long count = 0;
        try {
            String query = "SELECT i FROM Inventarobarajas i WHERE i.pordestruir != 0";
            ordenes = (List<Inventarobarajas>) em.createQuery(query).getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        for (Inventarobarajas ordene : ordenes) {
            if (usuario.getCasinoList().contains(ordene.getCasino())) {
                count++;
            }
        }
        return count;
    }

    public static List<Solicitudbarajas> getSolicitudesDesdeHasta(Integer ano, Integer mes, Integer annodesde, Integer mesdesde) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Solicitudbarajas> cargos = new ArrayList<Solicitudbarajas>();
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
            cargos.addAll(em.createNamedQuery("Solicitudbarajas.getPorFecha")
                    .setParameter("desde", desde.getTime())
                    .setParameter("hasta", hasta.getTime())
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

    public static List<Destruccionbarajasmaestro> getDestruccionDesdeHasta(Integer ano, Integer mes, Integer annodesde, Integer mesdesde) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Destruccionbarajasmaestro> cargos = new ArrayList<Destruccionbarajasmaestro>();
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
            cargos.addAll(em.createNamedQuery("Destruccionbarajasmaestro.getPorFecha")
                    .setParameter("desde", desde.getTime())
                    .setParameter("hasta", hasta.getTime())
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

    public static List<Trasladobarajas> getListaTraslados() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Trasladobarajas> ordenes = null;
        tx.begin();
        try {
            String query = "SELECT t FROM Trasladobarajas t";
            ordenes = (List<Trasladobarajas>) em.createQuery(query).getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return ordenes;
    }

    public static List<Trasladobarajas> getListaTraslados(Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        if (usuario.getCasinoList().isEmpty()) {
            return new ArrayList<Trasladobarajas>();
        }
        List<Trasladobarajas> ordenes = new ArrayList<Trasladobarajas>();
        tx.begin();
        try {
            String query = "SELECT t FROM Trasladobarajas t WHERE t.estado != 'RECIBIDA' AND (";

            for (Casino c : usuario.getCasinoList()) {
                query += " t.salaenviadora.idCasino = " + c.getIdCasino() + " OR t.salareceptora.idCasino = " + c.getIdCasino() + " OR";
            }
            query = query.substring(0, query.length() - 2);
            query += ")";
            ordenes = (List<Trasladobarajas>) em.createQuery(query).getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return ordenes;
    }

    public static Trasladobarajas getTraslado(Integer idOrden) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Trasladobarajas orden = null;
        tx.begin();
        try {

            orden = em.find(Trasladobarajas.class, idOrden);
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

    public static Integer saveTraslado(TrasladoDTO item) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Trasladobarajas orden = item.getTraslado();
        tx.begin();
        try {
            if (item.getId() == null) {
                orden.setSalaenviadora(new Casino(item.getSalaenviadora().getIdCasino()));
                orden.setSalareceptora(new Casino(item.getSalareceptora().getIdCasino()));
                orden.setCreador(new Usuario(item.getCreador().getId()));
                orden.setFechacreada(new Date());
                orden.setEstado("POR ENVIAR");
                List<Trasladobarajadetalle> detalle = new ArrayList<Trasladobarajadetalle>();
                orden.setTrasladobarajadetalleList(null);
                em.persist(orden);
                em.flush();
                for (BarajasCantidad bc : item.getDetalle()) {
                    if (bc.getCantidad() != 0) {
                        Trasladobarajadetalle tbd = new Trasladobarajadetalle(orden.getId(), bc.getId());
                        tbd.setCantidad(bc.getCantidad());
                        em.persist(tbd);
                        detalle.add(tbd);
                    }
                }
                orden.setTrasladobarajadetalleList(detalle);
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
        return orden.getId();
    }

    public static Integer sendTraslado(TrasladoDTO item) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Trasladobarajas orden = item.getTraslado();
        tx.begin();
        try {
            orden = em.find(Trasladobarajas.class, item.getId());
            orden.setEnviador(new Usuario(item.getCreador().getId()));
            orden.setFechaenviada(new Date());
            orden.setEstado("POR RECIBIR");
            em.merge(orden);
            for (Trasladobarajadetalle i : orden.getTrasladobarajadetalleList()) {
                for (Inventarobarajas inventario : orden.getSalaenviadora().getInventarobarajasList()) {
                    if (inventario.getBaraja().equals(i.getBarajas())) {
                        inventario.setCantidadbarajas(inventario.getCantidadbarajas() - i.getCantidad());
                        em.merge(inventario);
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
        return orden.getId();
    }

    public static Integer receiveTraslado(TrasladoDTO item) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Trasladobarajas orden = item.getTraslado();
        tx.begin();
        try {
            orden = em.find(Trasladobarajas.class, item.getId());
            orden.setRecibidor(new Usuario(item.getCreador().getId()));
            orden.setFecharecibida(new Date());
            orden.setEstado("RECIBIDA");
            em.merge(orden);
            for (Trasladobarajadetalle i : orden.getTrasladobarajadetalleList()) {
                for (Inventarobarajas inventario : orden.getSalareceptora().getInventarobarajasList()) {
                    if (inventario.getBaraja().equals(i.getBarajas())) {
                        inventario.setCantidadbarajas(inventario.getCantidadbarajas() + i.getCantidad());
                        em.merge(inventario);
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
        return orden.getId();
    }

    public static List<Solicitudbarajadetalle> getListaSoliciudesBarajasRepo(List<CasinoBoolean> casinos, Integer ano, Integer mes, Integer anodesde, Integer mesdesde) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Solicitudbarajadetalle> cargos = new ArrayList<Solicitudbarajadetalle>();
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
                    cargos.addAll(em.createNamedQuery("Solicitudbarajadetalle.getReporte")
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

    public static void rechazarOrden(OrdenCompraBarajaDTO idOrden, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Ordencomprabaraja orden = em.find(Ordencomprabaraja.class, idOrden.getId());
            for (Iterator<Ordencomprabarajadetalle> iterator = orden.getOrdencomprabarajadetalleList().iterator(); iterator.hasNext();) {
                Ordencomprabarajadetalle next = iterator.next();
                for (BarajasCantidad barajas : idOrden.getCantidades()) {
                    if (barajas.getId().intValue() == next.getInventarobarajas().getId()) {
                        if (barajas.getCantidadR() == 0) {
                            iterator.remove();
                            em.remove(em.merge(next));
                        } else {
                            next.setCantidadAprobada(barajas.getCantidadR());
                        }
                    }
                }
            }
            orden.setEsatdo("RECHAZADA");
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

    public static List<Solicitudbarajas> getListaSoliciudesBarajasPendientes() {
         EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Solicitudbarajas> ordenes = new ArrayList<Solicitudbarajas>();
        tx.begin();
        try {

                ordenes.addAll(em.createNamedQuery("Solicitudbarajas.findByEstado")
                        .setParameter("estado", "ENTREGADAS/Pendientes por devolver")
                        .getResultList());
            
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return ordenes;
    }
}
