/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.facade;

import com.invbf.sistemagestionmercadeo.entity.Configuracion;
import com.invbf.sistemagestionmercadeo.entity.Log;
import com.invbf.sistemagestionmercadeo.entity.Tarea;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import com.invbf.sistemagestionmercadeo.exceptions.ClavesNoConcuerdanException;
import com.invbf.sistemagestionmercadeo.exceptions.NoCambioContrasenaException;
import com.invbf.sistemagestionmercadeo.exceptions.UsuarioInactivoException;
import com.invbf.sistemagestionmercadeo.exceptions.UsuarioNoConectadoException;
import com.invbf.sistemagestionmercadeo.exceptions.UsuarioNoExisteException;
import com.invbf.sistemagestionmercadeo.exceptions.UsuarioSinAccesoalSistemaException;
import com.invbf.sistemagestionmercadeo.util.InfoCorreoCliente;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author ideacentre
 */
public interface SystemFacade {

    public Usuario iniciarSession(Usuario usuario)throws ClavesNoConcuerdanException, UsuarioNoExisteException, UsuarioNoConectadoException, UsuarioInactivoException,UsuarioSinAccesoalSistemaException ;

    public Usuario actualizarUsuario(Usuario usuario);

    public Usuario cambiarContrasena(String contrasena, String nueva, Usuario usuario)throws ClavesNoConcuerdanException, NoCambioContrasenaException;

    public List<Configuracion> getAllConfiguraciones();

    public void setAllConfiguraciones(List<Configuracion> configuraciones);

    public void registrarlog(String accion, String tabla, String mensaje, Usuario usuairo);

    public Configuracion getConfiguracionByName(String nombre);

    public ByteArrayOutputStream getOutputStreamImage(byte[] imagen, String mime)throws IOException ;
    
    public String getPathImage(byte[] imagen, String mime, String nombre)throws IOException ;

    public List<InfoCorreoCliente> enviarCorreo(Tarea elemento, String asunto, String cuerpo, String enviarimagen) ;

    public Usuario getUsuario(Integer idUsuario);

    public List<Log> getLogs();

    public String getNombreDeUsuario(Integer id);

    public long getnumedicioncliente();

    public long getnumrecibirsala();

    public long getnumdiligenciar();

    public long getnumconfirmarrecepcion();

    public long getnumconfimacionordenretiro();

    public long getnumrequerimientosolicitado();

    public long getnumingresoloteinventario();

    public long getnumpreaprobarsolicitud();

    public long getnumaprobarsolicitud();

    public void registrarEquipo( Usuario usuario, String ipAddress);

    public boolean isIpActiva(Usuario usuario, String ipAddress);

    public void checkEstadoTarea();

}
