/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.dao.ConfiguracionDao;
import com.invbf.sistemagestionmercadeo.dao.PerfilDao;
import com.invbf.sistemagestionmercadeo.dao.UsuarioDao;
import com.invbf.sistemagestionmercadeo.dao.VistaDao;
import com.invbf.sistemagestionmercadeo.entity.Perfil;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.entity.Vista;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

/**
 *
 * @author ivan
 */
public class Notificador implements Serializable {

    public static final int SOLICITUD_ENTREGA_LOTES_GENERADA = 1;
    public static final int SOLICITUD_ENTREGA_LOTES_ACEPTADA = 2;
    public static final int SOLICITUD_BONOS_GENERADA = 3;
    public static final int SOLICITUD_BONOS_PREAPROBADA = 4;
    public static final int SOLICITUD_CONTROL_SALIDA_GENERADA = 5;
    public static final int SOLICITUD_CONTROL_SALIDA_SOLICITADA = 6;
    public static final int SOLICITUD_CONTROL_SALIDA_ACEPTADA = 7;
    public static final int SOLICITUD_ENTREGA_BONOS = 8;
    public static final int SOLICITUD_RECIBO_BONOS = 9;
    public static final int SOLICITUD_CONTROL_SALIDA_APROBADA = 10;
    public static final int SOLICITUD_CAMBIO_CLIENTE = 11;
    public static final int INVENTARIO_EN_PROBLEMA = 12;
    public static final int EMAIL_CLIENTE = 13;
    public static final int REQUERIMIENTO_LOTE_GENERADO = 14;
    public static final int REQUERIMIENTO_LOTE_ORDENADO = 15;
    public static final int REQUERIMIENTO_LOTE_RECHAZADO = 16;
    public static final int REQUERIMIENTO_LOTE_RECIBIDO = 17;
    public static final int SOLICITUD_ENTREGA_LOTES_ENVIADA = 18;
    public static final int REQUERIMIENTO_LOTE_DEVUELTA = 19;
    public static final int ENTREGA_A_SALA = 20;
    public static final int AVISO_TAREA_ASIGNADA = 21;
    public static final int correoLimiteAlcanzadoBarajas = 22;
    public static final int correoSolicitudBarajaRecibida = 23;
    public static final int correoSolicitudBarajaEntregada = 24;
    public static final int correoSolicitudBarajaCreada = 25;
    public static final int correoOrdenBarajasRecibida = 26;
    public static final int correoOrdenBarajasAprobada = 27;
    public static final int correoOrdenBarajasCreada = 28;
    public static final int correoLibre=29;

    public static void notificar(int tipo, String body, String subject, String correosolicitantes) {
        switch (tipo) {
            case SOLICITUD_ENTREGA_LOTES_GENERADA:
                sendEmail("entregalotesgeneradacorreo", subject, body, false, correosolicitantes);
                break;
            case SOLICITUD_ENTREGA_LOTES_ACEPTADA:
                sendEmail("correoSolicitudEntradaValidada", subject, body, false, correosolicitantes);
                break;
            case SOLICITUD_BONOS_GENERADA:
                sendEmail("solicitudbonosgeneradacorreo", subject, body, true, correosolicitantes);
                break;
            case SOLICITUD_BONOS_PREAPROBADA:
                sendEmail("solicitudbonospreaprobadacorreo", subject, body, false, correosolicitantes);
                break;
            case SOLICITUD_CONTROL_SALIDA_GENERADA:
                sendEmail("solicitudaprobadacorreo", subject, body, true, correosolicitantes);
                break;
            case SOLICITUD_CONTROL_SALIDA_SOLICITADA:
                sendEmail("senalbusquedacorreo", subject, body, false, correosolicitantes);
                break;
            case SOLICITUD_CONTROL_SALIDA_APROBADA:
                sendEmail("bonosretiradosCorreo", subject, body, false, correosolicitantes);
                break;
            case SOLICITUD_ENTREGA_BONOS:
                sendEmail("entregarbonocajacorreo", subject, body, false, correosolicitantes);
                break;
            case SOLICITUD_RECIBO_BONOS:
                sendEmail("recibirbonocorreo", subject, body, false, correosolicitantes);
                break;
            case SOLICITUD_CAMBIO_CLIENTE:
                sendEmail("notificacionescorreo", subject, body, false, correosolicitantes);
                break;
            case ENTREGA_A_SALA:
                sendEmail("entregarbonocajacorreo", subject, body, false, correosolicitantes);
                break;
            case INVENTARIO_EN_PROBLEMA:
                sendEmail("SolicitudLotes", body, subject, false, correosolicitantes);
                break;
            case EMAIL_CLIENTE:
                sendEmailCliente(subject, body, correosolicitantes);
                break;

            case REQUERIMIENTO_LOTE_GENERADO:
                sendEmail("correoRequerimientoLoteCreado", subject, body, false, correosolicitantes);
                break;
            case REQUERIMIENTO_LOTE_ORDENADO:
                sendEmail("correoRequerimeintoLoteOrdenado", subject, body, false, correosolicitantes);
                break;
            case REQUERIMIENTO_LOTE_RECHAZADO:
                sendEmail("correoRequerimientoLoteRechazado", subject, body, false, correosolicitantes);
                break;
            case REQUERIMIENTO_LOTE_RECIBIDO:
                sendEmail("correoRequerimentoLoteRecibido", subject, body, false, correosolicitantes);
                break;
            case SOLICITUD_ENTREGA_LOTES_ENVIADA:
                sendEmail("correoSolicitudEntradaEnviada", subject, body, false, correosolicitantes);
                break;
            case REQUERIMIENTO_LOTE_DEVUELTA:
                sendEmail("correoSolicitudEntradaDevuelta", subject, body, false, correosolicitantes);
                break;
            case AVISO_TAREA_ASIGNADA:

                sendEmailCliente(subject, body, correosolicitantes);
                break;
            case correoLimiteAlcanzadoBarajas:
                sendEmail("correoLimiteAlcanzadoBarajas", subject, body, false, correosolicitantes);
                break;
            case correoSolicitudBarajaRecibida:
                sendEmail("correoSolicitudBarajaRecibida", subject, body, false, correosolicitantes);
                break;
            case correoSolicitudBarajaEntregada:
                sendEmail("correoSolicitudBarajaEntregada", subject, body, false, correosolicitantes);
                break;
            case correoSolicitudBarajaCreada:
                sendEmail("correoSolicitudBarajaCreada", subject, body, false, correosolicitantes);
                break;
            case correoOrdenBarajasRecibida:
                sendEmail("correoOrdenBarajasRecibida", subject, body, true, correosolicitantes);
                break;
            case correoOrdenBarajasAprobada:
                sendEmail("correoOrdenBarajasAprobada", subject, body, false, correosolicitantes);
                break;
            case correoOrdenBarajasCreada:
                sendEmail("correoOrdenBarajasCreada", subject, body, false, correosolicitantes);
                break;
            case correoLibre:
                sendEmail(subject, body, correosolicitantes);
                break;
        }
    }

    private static void sendEmail(String permiso, String subject, String mesaje, boolean enviarSol, String correo) {
        EmailSender es = new EmailSender();
        es.setAuth(true);
        es.setDebug(true);
        es.setFrom(ConfiguracionDao.findByNombre("correo").getValor());
        es.setHost(ConfiguracionDao.findByNombre("host").getValor());
        es.setPort(Integer.parseInt(ConfiguracionDao.findByNombre("port").getValor()));
        es.setProtocol(ConfiguracionDao.findByNombre("protocol").getValor());
        es.setUsername(ConfiguracionDao.findByNombre("username").getValor());
        es.setPassword(ConfiguracionDao.findByNombre("contrasena").getValor());

        try {
            Vista v = VistaDao.findByNombre(permiso);
            System.out.println(v.getNombreVista());
            List<Perfil> perfiles = PerfilDao.findAll();
            for (Perfil perfil : perfiles) {
                System.out.println(perfil.getVistaList().contains(v));
                if (perfil.getVistaList().contains(v)) {
                    List<Usuario> usuarios = UsuarioDao.findByPerfil(perfil);
                    System.out.println(usuarios.size());
                    for (Usuario usuario : usuarios) {
                        if (usuario.getUsuariodetalle() != null && usuario.getUsuariodetalle().getCorreo() != null && !usuario.getUsuariodetalle().getCorreo().equals("")) {
                            System.out.println(usuario.getUsuariodetalle().getCorreo());

                            es.sendEmailNotificador(usuario.getUsuariodetalle().getCorreo(), subject, mesaje);
                        }
                    }
                }
            }
            if (enviarSol) {
                es.sendEmailNotificador(correo, subject, mesaje);
            }
        } catch (MessagingException ex) {
            Logger.getLogger(Notificador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Notificador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void sendEmail(String subject, String mesaje, String correo) {
        EmailSender es = new EmailSender();
        es.setAuth(true);
        es.setDebug(true);
        es.setFrom(ConfiguracionDao.findByNombre("correo").getValor());
        es.setHost(ConfiguracionDao.findByNombre("host").getValor());
        es.setPort(Integer.parseInt(ConfiguracionDao.findByNombre("port").getValor()));
        es.setProtocol(ConfiguracionDao.findByNombre("protocol").getValor());
        es.setUsername(ConfiguracionDao.findByNombre("username").getValor());
        es.setPassword(ConfiguracionDao.findByNombre("contrasena").getValor());

        try {
                es.sendEmailNotificador(correo, subject, mesaje);
            
        } catch (MessagingException ex) {
            Logger.getLogger(Notificador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Notificador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void sendEmailCliente(String subject, String body, String correosolicitantes) {
        EmailSender es = new EmailSender();
        es.setAuth(true);
        es.setDebug(true);
        es.setFrom(ConfiguracionDao.findByNombre("correo").getValor());
        es.setHost(ConfiguracionDao.findByNombre("host").getValor());
        es.setPort(Integer.parseInt(ConfiguracionDao.findByNombre("port").getValor()));
        es.setProtocol(ConfiguracionDao.findByNombre("protocol").getValor());
        es.setUsername(ConfiguracionDao.findByNombre("username").getValor());
        es.setPassword(ConfiguracionDao.findByNombre("contrasena").getValor());

        try {
            es.sendEmailNotificador(correosolicitantes, subject, body);

        } catch (MessagingException ex) {
            Logger.getLogger(Notificador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Notificador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
