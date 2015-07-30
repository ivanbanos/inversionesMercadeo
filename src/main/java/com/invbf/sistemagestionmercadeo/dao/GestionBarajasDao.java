/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dao;

import com.invbf.sistemagestionmercadeo.dto.ActaDestruccionDTO;
import com.invbf.sistemagestionmercadeo.dto.BarajasCantidad;
import com.invbf.sistemagestionmercadeo.dto.InventarioBarajasDTO;
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
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.util.CasinoBoolean;
import com.invbf.sistemagestionmercadeo.util.Notificador;
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
import javax.persistence.criteria.ParameterExpression;
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

    public static void crearOrden(Integer idOrden, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Ordencomprabaraja orden = em.find(Ordencomprabaraja.class, idOrden);
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
        tx.begin();
        try {
            Solicitudbarajas orden = em.find(Solicitudbarajas.class, idOrden);
            orden.setEstado("ENTREGADAS/Pendientes por devolver");
            orden.setAceptador(usuario);
            orden.setFechentrega(new Date());
            boolean creeOrden = false;
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
            em.merge(orden);
            javax.persistence.criteria.CriteriaQuery cq2 = em.getCriteriaBuilder().createQuery();
            Root<Ordencomprabaraja> c2 = cq2.from(Ordencomprabaraja.class);
            cq2.select(c2);
            Expression<String> name = c2.get("estado");
            cq2.where(
                    em.getCriteriaBuilder().notLike(name, "RECIBIDA"));
            int cant = em.createQuery(cq2).getResultList().size();
            creeOrden = cant == 0 ? creeOrden : false;
            tx.commit();
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
                        d.setCantidad(0);
                    }
                    em.merge(d);
                    ordencompra.getOrdencomprabarajadetalleList().add(d);
                }
                em.merge(ordencompra);
                em.flush();
                Notificador.notificar(Notificador.correoLimiteAlcanzadoBarajas,
                        "Se ha pregenerado el requerimiento de compra de barajas con el n&uacute;mero de acta " + orden.getId() + ". Favor revisar la lista de requerimientos de compra de barajas.",
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

    public static void entregarUsadasSolicitud(Integer idOrden, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Solicitudbarajas orden = em.find(Solicitudbarajas.class, idOrden);
            orden.setEstado("Pendiente por recibir usadas");
            for (Solicitudbarajadetalle detalle : orden.getSolicitudbarajadetalleList()) {
                Inventarobarajas inventario = em.find(Inventarobarajas.class, detalle.getInventarobarajas().getId());
                inventario.setUso(inventario.getUso() - detalle.getCantidad());

                inventario.setPordestruir(inventario.getPordestruir() + detalle.getCantidad());

                orden.setEntregadasUsadas(new Date());
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

    public static void recibirUsadasSolicitud(Integer idOrden, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Solicitudbarajas orden = em.find(Solicitudbarajas.class, idOrden);
            orden.setEstado("TERMINADA");

            orden.setRecibidasUsadas(new Date());

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
            for (BarajasCantidad invent : acta.getDetalle()) {
                System.out.println(invent.getCantidad());
                Inventarobarajas inventario = em.find(Inventarobarajas.class, invent.getId());
                inventario.setDestruidas(inventario.getDestruidas() + invent.getCantidad());
                inventario.setPordestruir(inventario.getPordestruir() - invent.getCantidad());
                em.merge(inventario);
            }
            dbm = new Destruccionbarajasmaestro();
            dbm.setFechaDestruccion(new Date());
            dbm.setUsuario(usuario);
            em.persist(dbm);
            em.flush();
            dbm.setActasdestruccionbarajasList(new ArrayList<Actasdestruccionbarajas>());
            for (BarajasCantidad bc : acta.getDetalle()) {
                Actasdestruccionbarajas adb = new Actasdestruccionbarajas();
                adb.setActa(dbm);
                adb.setCantidad(bc.getCantidad());
                adb.setInventario(new Inventarobarajas(bc.getId()));
                dbm.getActasdestruccionbarajasList().add(adb);
            }
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
            String query = "SELECT s FROM Solicitudbarajas s WHERE s.entregadasNuevas != null";
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

    public static long getRecibirusadas(Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Solicitudbarajas> solicitudes = null;
        long count = 0;
        tx.begin();
        try {
            String query = "SELECT s FROM Solicitudbarajas s WHERE s.recibidasUsadas != null";
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

    public static long getEntregarUsadas(Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Solicitudbarajas> solicitudes = null;
        long count = 0;
        tx.begin();
        try {
            String query = "SELECT s FROM Solicitudbarajas s WHERE s.entregadasUsadas != null";
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
}
