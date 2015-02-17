package com.invbf.sistemagestionmercadeo.controladores;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "applicationContainer", eager = true)
@ApplicationScoped
public class ApplicationContainer {

    private List<String> usuariosConectados;

    public ApplicationContainer() {
        usuariosConectados = new ArrayList<String>();
    }

    public void conectarUsuario(String usuario) {
        if (!usuariosConectados.contains(usuario)) {
            usuariosConectados.add(usuario);
        }
    }

    public void desconectarUsuario(String usuario) {
        if (usuariosConectados.contains(usuario)) {
            usuariosConectados.remove(usuario);
        }
    }

    public boolean isUsuarioConectado(String usuario) {
        return usuariosConectados.contains(usuario);
    }
}
