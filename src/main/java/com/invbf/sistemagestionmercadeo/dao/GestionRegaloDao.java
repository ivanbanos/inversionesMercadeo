/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dao;

import com.invbf.sistemagestionmercadeo.dto.CasinoDto;
import com.invbf.sistemagestionmercadeo.dto.ClienteRegaloDTO;
import com.invbf.sistemagestionmercadeo.dto.InventarioRegalosDTO;
import com.invbf.sistemagestionmercadeo.dto.OrdenCompraRegaloDTO;
import com.invbf.sistemagestionmercadeo.dto.RegaloCanje;
import com.invbf.sistemagestionmercadeo.dto.RegalosCantidadDTO;
import com.invbf.sistemagestionmercadeo.dto.SolicitudRegaloDTO;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Regalos;
import com.invbf.sistemagestionmercadeo.entity.Regalosinventario;
import com.invbf.sistemagestionmercadeo.entity.Categoria;
import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Ordencompraregalos;
import com.invbf.sistemagestionmercadeo.entity.Ordencompraregalodetalle;
import com.invbf.sistemagestionmercadeo.entity.OrdencompraregalodetallePK;
import com.invbf.sistemagestionmercadeo.entity.Solicitudregalodetalle;
import com.invbf.sistemagestionmercadeo.entity.SolicitudregalodetallePK;
import com.invbf.sistemagestionmercadeo.entity.Solicitudregalos;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
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
public class GestionRegaloDao {

    public static List<Regalos> getListaRegalos() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Regalos> lista = new ArrayList<Regalos>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Regalos> c = cq.from(Regalos.class);
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

    public static List<Categoria> getListaCategorias() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Categoria> lista = new ArrayList<Categoria>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categoria> c = cq.from(Categoria.class);
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

    public static Regalos addRegalo(Regalos regalo) {
        regalo.setNombre(regalo.getNombre().toUpperCase());
        regalo.setDescripcion(regalo.getDescripcion().toUpperCase());
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            if (regalo.getId() == null || regalo.getId() == 0) {
                em.persist(regalo);
                em.flush();
                Regalosinventario inventario = new Regalosinventario();
                inventario.setCantidad(0);
                inventario.setRegalo(regalo);
                em.persist(inventario);
            } else {
                Regalos regaloc = em.find(Regalos.class, regalo.getId());
                regaloc.setNombre(regalo.getNombre());
                regaloc.setDescripcion(regalo.getDescripcion());
                regaloc.setGenero(regalo.getGenero());
                regaloc.setCategoria(regalo.getCategoria());
                regaloc.setFoto(regalo.getFoto());
                em.merge(regaloc);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return regalo;
    }

    public static Regalos addInventarioBaraja(Regalos regalo) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Regalosinventario ib = new Regalosinventario();
            ib.setRegalo(regalo);
            ib.setCantidad(0);
            ib.setMax(0);
            ib.setMin(0);
            em.persist(ib);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return regalo;
    }

    public static List<Regalosinventario> getListaInvenratioRegalos() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Regalosinventario> lista = new ArrayList<Regalosinventario>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Regalosinventario> c = cq.from(Regalosinventario.class);
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

    public static List<Ordencompraregalos> getListaOrdenesCompraRegalo() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Ordencompraregalos> lista = new ArrayList<Ordencompraregalos>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ordencompraregalos> c = cq.from(Ordencompraregalos.class);
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

    public static List<Solicitudregalos> getListaSoliciudesRegalo(boolean perfilViewMatch, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Solicitudregalos> lista = new ArrayList<Solicitudregalos>();

        tx.begin();
        try {
            if (perfilViewMatch) {
                javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
                Root<Solicitudregalos> c = cq.from(Solicitudregalos.class);
                cq.select(c);
                lista = em.createQuery(cq).getResultList();
                tx.commit();

            } else {
                for (Casino casino : usuario.getCasinoList()) {
                    lista.addAll(em.createNamedQuery("Solicitudregalos.findBySala")
                            .setParameter("casino", casino.getIdCasino())
                            .getResultList());
                }
            }
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return lista;
    }

    public static Ordencompraregalos crearOrdenCompra(Ordencompraregalos orden) {
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

    public static Ordencompraregalos guardarOrdenCompra(Ordencompraregalos orden) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            List<Ordencompraregalodetalle> lista = orden.getOrdencompraregalodetalleList();
            orden.setOrdencompraregalodetalleList(new ArrayList<Ordencompraregalodetalle>());
            em.persist(orden);
            em.flush();
            for (Ordencompraregalodetalle detalle : lista) {
                detalle.setOrdencompraregalos(orden);
                detalle.setOrdencompraregalodetallePK(new OrdencompraregalodetallePK(orden.getId(), detalle.getRegalosinventario().getId()));
                em.persist(detalle);
            }
            orden.setOrdencompraregalodetalleList(lista);
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

    public static Ordencompraregalos getOrdenCompra(Integer idOrden) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Ordencompraregalos orden = null;
        tx.begin();
        try {

            orden = em.find(Ordencompraregalos.class, idOrden);
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

    public static Solicitudregalos crearSolicitud(Solicitudregalos orden) {
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

    public static Solicitudregalos guardarSolicitud(Solicitudregalos orden) {
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

    public static Solicitudregalos getSolicitud(Integer idOrden) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Solicitudregalos orden = null;
        tx.begin();
        try {

            orden = em.find(Solicitudregalos.class, idOrden);
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

    public static List<Solicitudregalos> getListaSoliciudes(Usuario idUsuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Solicitudregalos> lista = new ArrayList<Solicitudregalos>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Solicitudregalos> c = cq.from(Solicitudregalos.class);
            cq.select(c);
            lista = em.createQuery(cq).getResultList();
            //for (Iterator<Solicitudregalos> iterator = lista.iterator(); iterator.hasNext();) {
            //    Solicitudregalos next = iterator.next();
            //    if(idUsuario.getCasinoList().contains(next.))
            //}
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return lista;
    }

    public static Regalos removeRegalo(Regalos regalo) {
        regalo.setNombre(regalo.getNombre().toUpperCase());
        regalo.setDescripcion(regalo.getDescripcion().toUpperCase());
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.remove(em.merge(regalo));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return regalo;
    }

    public static void saveInvenratioRegalos(InventarioRegalosDTO inventario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            for (RegalosCantidadDTO regalo : inventario.getInventario()) {
                Regalosinventario inventarioO = em.find(Regalosinventario.class, regalo.getId());
                inventarioO.setMin(regalo.getMin());
                inventarioO.setMax(regalo.getMax());
                em.merge(inventarioO);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static void rechazarOrden(OrdenCompraRegaloDTO ordendto, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Ordencompraregalos orden = em.find(Ordencompraregalos.class, ordendto.getId());
            orden.setEstado("RECHAZADO");
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

    public static void crearOrden(OrdenCompraRegaloDTO ordendto, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Ordencompraregalos orden = em.find(Ordencompraregalos.class, ordendto.getId());
            orden.setEstado("GENERADO");
            orden.setAceptador(usuario);
            orden.setFechaAceptada(new Date());
            for (Ordencompraregalodetalle detalle : orden.getOrdencompraregalodetalleList()) {
                detalle.setCantidad(ordendto.getCantidades().get(ordendto.getCantidades().indexOf(new RegalosCantidadDTO(detalle.getRegalosinventario().getId()))).getCantidad());
                em.merge(detalle);
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

    public static void aprobarOrden(OrdenCompraRegaloDTO idOrden, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Ordencompraregalos orden = em.find(Ordencompraregalos.class, idOrden.getId());
            orden.setEstado("APROBADO");
            orden.setAceptador(usuario);
            orden.setFechaAceptada(new Date());
            for (Ordencompraregalodetalle detalle : orden.getOrdencompraregalodetalleList()) {
                detalle.setCantidadAprobada(idOrden.getCantidades().get(idOrden.getCantidades().indexOf(new RegalosCantidadDTO(detalle.getRegalosinventario().getId()))).getCantidadAprobada());
                detalle.setCantidadRecibida(idOrden.getCantidades().get(idOrden.getCantidades().indexOf(new RegalosCantidadDTO(detalle.getRegalosinventario().getId()))).getCantidadAprobada());
                em.merge(detalle);
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

    public static void recibirOrden(OrdenCompraRegaloDTO idOrden, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Ordencompraregalos orden = em.find(Ordencompraregalos.class, idOrden);
            orden.setEstado("RECIBIDO");
            orden.setRecibidor(usuario);
            orden.setFechaRecibida(new Date());
            for (Ordencompraregalodetalle detalle : orden.getOrdencompraregalodetalleList()) {
                detalle.setCantidadRecibida(idOrden.getCantidades().get(idOrden.getCantidades().indexOf(new RegalosCantidadDTO(detalle.getRegalosinventario().getId()))).getCantidadR());
                em.merge(detalle);
                Regalosinventario inventario = em.find(Regalosinventario.class, detalle.getRegalosinventario().getId());
                inventario.setCantidad(inventario.getCantidad() + detalle.getCantidadRecibida());
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

    public static void generarSolicitudRegalo(CasinoDto casino, List<ClienteRegaloDTO> lista, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Solicitudregalos solicitud = new Solicitudregalos();
            solicitud.setEstado("GENERADA");
            solicitud.setCreador(usuario);
            solicitud.setFechacreacion(new Date());
            solicitud.setSala(new Casino(casino.getIdCasino()));
            em.persist(solicitud);
            em.flush();
            solicitud.setSolicitudregalodetalleList(new ArrayList<Solicitudregalodetalle>());
            for (ClienteRegaloDTO cliente : lista) {
                if (cliente.getRegalo().getId() != 0) {
                    Solicitudregalodetalle detalle = new Solicitudregalodetalle();
                    detalle.setCantidad(1);
                    detalle.setClientes(new Cliente(cliente.getIdCliente()));
                    detalle.setEstado("POR APROBAR");
                    detalle.setSolicitudregalos(solicitud);
                    detalle.setRegalosinventario(new Regalosinventario(cliente.getRegalo().getId()));
                    detalle.setSolicitudregalodetallePK(new SolicitudregalodetallePK(solicitud.getId(), cliente.getRegalo().getId(), cliente.getIdCliente()));
                    solicitud.getSolicitudregalodetalleList().add(detalle);
                }
            }
            em.merge(solicitud);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static void aprobarSolicitud(SolicitudRegaloDTO solicitudDto, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Solicitudregalos solicitud = em.find(Solicitudregalos.class, solicitudDto.getId());
            solicitud.setEstado("APROBADA");
            solicitud.setAceptador(usuario);
            solicitud.setFechaAprobacion(new Date());

            em.merge(solicitud);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static void entregarSolicitud(Integer idOrden, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Solicitudregalos orden = em.find(Solicitudregalos.class, idOrden);
            orden.setEstado("ENTREGADA");
            orden.setEnviador(usuario);
            orden.setFechentrega(new Date());

            for (Solicitudregalodetalle detalle : orden.getSolicitudregalodetalleList()) {
                Regalosinventario inventario = em.find(Regalosinventario.class, detalle.getRegalosinventario().getId());
                inventario.setCantidad(inventario.getCantidad() - detalle.getCantidad());
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

    public static void recibirSolicitud(Integer idOrden, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Solicitudregalos orden = em.find(Solicitudregalos.class, idOrden);
            orden.setEstado("RECIBIDA");
            orden.setRecibidor(usuario);
            orden.setFecharecepcion(new Date());
            for (Solicitudregalodetalle detalle : orden.getSolicitudregalodetalleList()) {
                detalle.setEstado("EN SALA");
                em.merge(detalle);
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

    public static void rechazarSolicitud(SolicitudRegaloDTO solicitudDto, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Solicitudregalos solicitud = em.find(Solicitudregalos.class, solicitudDto.getId());
            solicitud.setEstado("RECHAZADA");
            solicitud.setAceptador(usuario);
            solicitud.setFechaAprobacion(new Date());

            em.merge(solicitud);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }

    public static List<Solicitudregalodetalle> buscar(String buscar, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Solicitudregalodetalle> lista = new ArrayList<Solicitudregalodetalle>();

        tx.begin();
        try {

            for (Casino casino : usuario.getCasinoList()) {
                lista.addAll(em.createNamedQuery("Solicitudregalodetalle.findBySala")
                        .setParameter("sala", casino.getIdCasino())
                        .setParameter("buscar",'%' + buscar + '%' )
                        .getResultList());
            }
        } catch (Exception e) {
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
        return lista;
    }

    public static void entregarRegalo(RegaloCanje regalo, Usuario usuario) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Solicitudregalodetalle regalosol = null;
        tx.begin();
        try {
            regalosol = em.find(Solicitudregalodetalle.class, new SolicitudregalodetallePK(regalo.getSolicitud(), regalo.getRegalo().getId(), regalo.getCliente().getIdCliente()));
            regalosol.setEstado("ENTREGADO");
            regalosol.setFechaEntrega(new Date());
            em.merge(regalosol);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }

        em.clear();
        em.close();
        emf.close();
    }
}
