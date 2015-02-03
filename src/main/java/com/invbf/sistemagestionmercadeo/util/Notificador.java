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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

/**
 *
 * @author ivan
 */
public class Notificador {

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

    public static void notificar(int tipo, String body, String subject) {
        switch (tipo) {
            case SOLICITUD_ENTREGA_LOTES_GENERADA:
                sendEmail("AceptarSolicitudLotesBonos", subject, body);
                break;
            case SOLICITUD_ENTREGA_LOTES_ACEPTADA:
                sendEmail("SolicitudLotes", subject, body);
                break;
            case SOLICITUD_BONOS_GENERADA:
                sendEmail("PreAprobarSolicitudBono", subject, body);
                sendEmail("AprobarSolicitudBono", subject, body);
                break;
            case SOLICITUD_BONOS_PREAPROBADA:
                sendEmail("AprobarSolicitudBono", subject, body);
                break;
            case SOLICITUD_CONTROL_SALIDA_GENERADA:
                sendEmail("Controlsalidabonos", subject, body);
                break;
            case SOLICITUD_CONTROL_SALIDA_SOLICITADA:
                sendEmail("AceptarSolicitudSalida", subject, body);
                break;
            case SOLICITUD_CONTROL_SALIDA_APROBADA:
                sendEmail("Validarbono", subject, body);
                break;
            case SOLICITUD_ENTREGA_BONOS:
                sendEmail("Entregarbonocaja", subject, body);
                break;
            case SOLICITUD_RECIBO_BONOS:
                sendEmail("Recibirbono", subject, body);
                break;
            case SOLICITUD_CAMBIO_CLIENTE:
                sendEmail("notificaciones", subject, body);
                break;
        }
    }

    private static void sendEmail(String permiso, String subject, String mesaje) {
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
                        System.out.println(usuario.getUsuariodetalle().getCorreo());
                        es.sendEmail(usuario.getUsuariodetalle().getCorreo(), subject, mesaje, "noimage");
                    }
                }
            }

        } catch (MessagingException ex) {
            Logger.getLogger(Notificador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Notificador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
