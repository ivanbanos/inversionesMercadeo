/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.facade.impl;


import com.invbf.sistemagestionmercadeo.dao.AccesoDao;
import com.invbf.sistemagestionmercadeo.dao.AccionDao;
import com.invbf.sistemagestionmercadeo.dao.ConfiguracionDao;
import com.invbf.sistemagestionmercadeo.dao.FormularioDao;
import com.invbf.sistemagestionmercadeo.dao.LogDao;
import com.invbf.sistemagestionmercadeo.dao.UsuarioDao;
import com.invbf.sistemagestionmercadeo.dao.UsuarioDetalleDao;
import com.invbf.sistemagestionmercadeo.entity.Acceso;
import com.invbf.sistemagestionmercadeo.entity.Accion;
import com.invbf.sistemagestionmercadeo.entity.Configuracion;
import com.invbf.sistemagestionmercadeo.entity.Formulario;
import com.invbf.sistemagestionmercadeo.entity.Listasclientestareas;
import com.invbf.sistemagestionmercadeo.entity.Log;
import com.invbf.sistemagestionmercadeo.entity.Tarea;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.entity.Usuariodetalle;
import com.invbf.sistemagestionmercadeo.exceptions.ClavesNoConcuerdanException;
import com.invbf.sistemagestionmercadeo.exceptions.NoCambioContrasenaException;
import com.invbf.sistemagestionmercadeo.exceptions.UsuarioInactivoException;
import com.invbf.sistemagestionmercadeo.exceptions.UsuarioNoConectadoException;
import com.invbf.sistemagestionmercadeo.exceptions.UsuarioNoExisteException;
import com.invbf.sistemagestionmercadeo.exceptions.UsuarioSinAccesoalSistemaException;
import com.invbf.sistemagestionmercadeo.facade.SystemFacade;
import com.invbf.sistemagestionmercadeo.util.EmailSender;
import com.invbf.sistemagestionmercadeo.util.EncryptUtil;
import com.invbf.sistemagestionmercadeo.util.InfoCorreoCliente;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author ideacentre
 */
public class SystemFacadeImpl implements SystemFacade {

    @Override
    public Usuario iniciarSession(Usuario usuario) throws ClavesNoConcuerdanException, UsuarioNoExisteException, UsuarioNoConectadoException, UsuarioInactivoException, UsuarioSinAccesoalSistemaException {
        try {
            Usuario usuarios = UsuarioDao.findByNombreUsuario(usuario.getNombreUsuario());
            System.out.println(usuarios);
            if (usuarios != null) {
                Usuario usuarioConectado = usuarios;
                if (usuarioConectado.getEstado()==null||usuarioConectado.getEstado().equals("INACTIVO")) {
                    throw new UsuarioInactivoException();
                }
                Usuariodetalle du = UsuarioDetalleDao.find(usuarioConectado.getIdUsuario());
                if(du==null){
                    UsuarioDetalleDao.create(du);
                }
                Acceso a = AccesoDao.findByNombreAcceso("SGC");
                if(du.getAccesoList()==null||du.getAccesoList().isEmpty()||!du.getAccesoList().contains(a)){
                    throw new UsuarioSinAccesoalSistemaException();
                }
                if (!EncryptUtil.comparePassword(usuario.getContrasena(), usuarioConectado.getContrasena())) {
                    throw new ClavesNoConcuerdanException();
                }
                return usuarioConectado;
            } else {
                throw new UsuarioNoExisteException();
            }
        } catch (NoSuchAlgorithmException ex) {
            throw new UsuarioNoConectadoException();
        }
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) {
        return UsuarioDao.find(usuario.getIdUsuario());
    }

    @Override
    public Usuario cambiarContrasena(String contrasena, String nueva, Usuario usuario) throws ClavesNoConcuerdanException, NoCambioContrasenaException {
        try {
            if (!EncryptUtil.comparePassword(contrasena, usuario.getContrasena())) {
                throw new ClavesNoConcuerdanException();
            } else {
                usuario.setContrasena(EncryptUtil.encryptPassword(nueva));
                UsuarioDao.edit(usuario);
                return usuario;
            }
        } catch (NoSuchAlgorithmException ex) {
            throw new NoCambioContrasenaException();
        }
    }

    @Override
    public List<Configuracion> getAllConfiguraciones() {
        return ConfiguracionDao.findAll();
    }

    @Override
    public void setAllConfiguraciones(List<Configuracion> configuraciones) {
        for (Configuracion c : configuraciones) {
            ConfiguracionDao.edit(c);
        }
    }

    @Override
    public void registrarlog(String accion, String tabla, String mensaje, Usuario usuairo) {

        try {
            Log log = new Log();
            if (accion != null && tabla != null) {
                Formulario f = FormularioDao.findByAccionAndTabla(accion, tabla);
                if (f != null) {
                    log.setIdFormulario(f);
                }
            }
            log.setIdUsuario(usuairo);
            log.setMensaje(mensaje);
            DateFormat df = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
            DateFormat df2 = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
            TimeZone timeZone = TimeZone.getTimeZone("GMT-5");
            df.setTimeZone(timeZone);
            Calendar nowDate = Calendar.getInstance();
            nowDate.setTime(df2.parse(df.format(nowDate.getTime())));
            log.setFecha(nowDate.getTime());
            LogDao.create(log);
        } catch (ParseException ex) {
            Logger.getLogger(SystemFacadeImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Configuracion getConfiguracionByName(String nombre) {
        return ConfiguracionDao.findByNombre(nombre);
    }

    @Override
    public ByteArrayOutputStream getOutputStreamImage(byte[] imagen, String mime) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedImage bi = ImageIO.read(new ByteArrayInputStream(imagen));
        if (mime.endsWith("png")) {
            ImageIO.write(bi, "png", out);
        }
        return out;
    }

    @Override
    public String getPathImage(byte[] imagen, String mime, String nombre) throws IOException {
        FileInputStream fileInputStream = null;

        File file = new File("nombre.png");
        FileOutputStream fileOuputStream = new FileOutputStream("nombre.png");
        fileOuputStream.write(imagen);

        return file.getAbsolutePath();
    }

    @Override
    public List<InfoCorreoCliente> enviarCorreo(Tarea elemento, String asunto, String cuerpo, boolean enviarimagen) {
        EmailSender es = new EmailSender();
        es.setAuth(true);
        es.setDebug(true);
        es.setFrom(ConfiguracionDao.findByNombre("correo").getValor());
        es.setHost(ConfiguracionDao.findByNombre("host").getValor());
        es.setPort(Integer.parseInt(ConfiguracionDao.findByNombre("port").getValor()));
        es.setProtocol(ConfiguracionDao.findByNombre("protocol").getValor());
        es.setUsername(ConfiguracionDao.findByNombre("username").getValor());
        es.setPassword(ConfiguracionDao.findByNombre("contrasena").getValor());
        Accion enviado = AccionDao.findByNombreAccion("ENVIADO");
        Accion noenviado = AccionDao.findByNombreAccion("NO ENVIADO");
        for (Listasclientestareas lce : elemento.getListasclientestareasList()) {
            try {
                String correoString = lce.getCliente().getCorreo();
                if (correoString.equals("")) {

                    lce.setIdAccion(noenviado);
                    lce.setObservaciones("Correo vacio");
                }
                if (elemento.getIdEvento() != null && elemento.getIdEvento().getImagen() != null && !elemento.getIdEvento().getImagen().equals("") && enviarimagen) {
                    es.sendEmail(correoString, asunto, cuerpo, elemento.getIdEvento().getImagen());
                } else {
                    es.sendEmail(correoString, asunto, cuerpo, "noimage");
                }

                lce.setIdAccion(enviado);
            } catch (Exception ex) {
                lce.setIdAccion(noenviado);
                lce.setObservaciones("problemas externos, llamar administrador");
            }
        }
        return null;
    }

    @Override
    public Usuario getUsuario(Integer idUsuario) {
        return UsuarioDao.find(idUsuario);
    }

    @Override
    public List<Log> getLogs() {
        return LogDao.findAll();
    }
    
    @Override
    public String getNombreDeUsuario(Integer id) {
        Usuario u = UsuarioDao.find(id);
        if(u==null){
            return "No registrado";
        } else {
            return u.getNombreUsuario();
        }
    }
}
