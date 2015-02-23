/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Tipojuego;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ivan
 */
public class ClienteDTO implements Serializable {

    private Integer idCliente;
    private String nombres;
    private String apellidos;
    private String telefono1;
    private String telefono2;
    private String identificacion;
    private String correo;
    private Date cumpleanos;
    private String pais;
    private String direccion;
    private String ciudad;
    private String bonoFidelizacion;
    private String genero;
    private String ocupacion;
    private Float montodejuegovisita;
    private String maquinapreferida;
    private String horaHabitualVisita;
    private String diaSemanaVisita;
    private String acompananteHabitual;
    private String descripcionPersonalidad;
    private String bebida;
    private String comida;
    private String gustosPreferencias;
    private List<Tipojuego> tipojuegoList;
    private String idTipoDocumento;
    private String idCategorias;
    private String idCasinoPreferencial;

    public ClienteDTO() {
    }

    public ClienteDTO(Cliente c) {
        this.idCliente = c.getIdCliente();
        this.nombres = c.getNombres();
        this.apellidos = c.getApellidos();
        this.telefono1 = c.getTelefono1();
        this.telefono2 = c.getTelefono2();
        this.identificacion = c.getIdentificacion();
        this.correo = c.getCorreo();
        this.cumpleanos = c.getCumpleanos();
        this.pais = c.getPais();
        this.direccion = c.getDireccion();
        this.ciudad = c.getCiudad();
        this.bonoFidelizacion = c.getBonoFidelizacion();
        this.genero = c.getGenero();
        this.ocupacion = c.getOcupacion();
        this.montodejuegovisita = c.getMontodejuegovisita();
        this.maquinapreferida = c.getMaquinapreferida();
        this.horaHabitualVisita = c.getHoraHabitualVisita();
        this.diaSemanaVisita = c.getDiaSemanaVisita();
        this.acompananteHabitual = c.getAcompananteHabitual();
        this.descripcionPersonalidad = c.getDescripcionPersonalidad();
        this.bebida = c.getBebida();
        this.comida = c.getComida();
        this.gustosPreferencias = c.getGustosPreferencias();
        this.tipojuegoList = c.getTipojuegoList();
        if (c.getIdTipoDocumento() != null) {
            this.idTipoDocumento = c.getIdTipoDocumento().getNombre();
        }
        this.idCategorias = c.getIdCategorias().getNombre();
        this.idCasinoPreferencial = c.getIdCasinoPreferencial().getNombre();
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getCumpleanos() {
        return cumpleanos;
    }

    public void setCumpleanos(Date cumpleanos) {
        this.cumpleanos = cumpleanos;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getBonoFidelizacion() {
        return bonoFidelizacion;
    }

    public void setBonoFidelizacion(String bonoFidelizacion) {
        this.bonoFidelizacion = bonoFidelizacion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public Float getMontodejuegovisita() {
        return montodejuegovisita;
    }

    public void setMontodejuegovisita(Float montodejuegovisita) {
        this.montodejuegovisita = montodejuegovisita;
    }

    public String getMaquinapreferida() {
        return maquinapreferida;
    }

    public void setMaquinapreferida(String maquinapreferida) {
        this.maquinapreferida = maquinapreferida;
    }

    public String getHoraHabitualVisita() {
        return horaHabitualVisita;
    }

    public void setHoraHabitualVisita(String horaHabitualVisita) {
        this.horaHabitualVisita = horaHabitualVisita;
    }

    public String getDiaSemanaVisita() {
        return diaSemanaVisita;
    }

    public void setDiaSemanaVisita(String diaSemanaVisita) {
        this.diaSemanaVisita = diaSemanaVisita;
    }

    public String getAcompananteHabitual() {
        return acompananteHabitual;
    }

    public void setAcompananteHabitual(String acompananteHabitual) {
        this.acompananteHabitual = acompananteHabitual;
    }

    public String getDescripcionPersonalidad() {
        return descripcionPersonalidad;
    }

    public void setDescripcionPersonalidad(String descripcionPersonalidad) {
        this.descripcionPersonalidad = descripcionPersonalidad;
    }

    public String getBebida() {
        return bebida;
    }

    public void setBebida(String bebida) {
        this.bebida = bebida;
    }

    public String getComida() {
        return comida;
    }

    public void setComida(String comida) {
        this.comida = comida;
    }

    public String getGustosPreferencias() {
        return gustosPreferencias;
    }

    public void setGustosPreferencias(String gustosPreferencias) {
        this.gustosPreferencias = gustosPreferencias;
    }

    public List<Tipojuego> getTipojuegoList() {
        return tipojuegoList;
    }

    public void setTipojuegoList(List<Tipojuego> tipojuegoList) {
        this.tipojuegoList = tipojuegoList;
    }

    public String getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(String idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getIdCategorias() {
        return idCategorias;
    }

    public void setIdCategorias(String idCategorias) {
        this.idCategorias = idCategorias;
    }

    public String getIdCasinoPreferencial() {
        return idCasinoPreferencial;
    }

    public void setIdCasinoPreferencial(String idCasinoPreferencial) {
        this.idCasinoPreferencial = idCasinoPreferencial;
    }

    public String getAsEmail() {
        String htmlText = "";
        htmlText += "<div><Label>Nombres: </label> " + nombres.substring(0, 1) + "*****</div>";
        htmlText += "<div><Label>Apellidos: </label>  " + apellidos.substring(0, 1) + "*****</div>";
        if (telefono1 != null && !telefono1.equals("")) {
            htmlText += "<div><Label>Teléfono 1: </label>  " + telefono1.substring(0, 1) + "*****</div>";
        }
        if (telefono2 != null && !telefono2.equals("")) {
            htmlText += "<div><Label>Teléfono 2: </label> " + telefono2.substring(0, 1) + "*****</div>";
        }
        if (identificacion != null && !identificacion.equals("")) {
            htmlText += "<div><Label>Identificacion: </label> " + identificacion.substring(0, 1) + "*****</div>";
        }
        htmlText += "<div><Label>Tipo de documento: </label>  " + idTipoDocumento + "</div>";
        htmlText += "<div><Label>Categoria: </label> " + idCategorias + "</div>";
        htmlText += "<div><Label>Casino de preferencia: </label> " + idCasinoPreferencial + "</div>";
        htmlText += "<div><Label>Genero: </label>  " + genero + "</div>";
        htmlText += "<div><Label>Correo: </label> " + correo + "</div>";
        if (cumpleanos != null) {
            htmlText += "<div><Label>Cumpleaños: " + cumpleanos + "</div>";
        }
        htmlText += "<div><Label>Pais: </label> " + pais + "</div>";
        htmlText += "<div><Label>Ciudad: </label> " + ciudad + "</div>";
        htmlText += "<div><Label>Dirección: </label> " + direccion + "</div>";
        htmlText += "<div><Label>Cupo de fidelización: </label> " + bonoFidelizacion + "</div>";
        htmlText += "<div><Label>Ocupación: </label> " + ocupacion + "</div>";
        htmlText += "<div><Label>Monto aprox. jugado por visita: </label> " + montodejuegovisita + "</div>";
        htmlText += "<div><Label>Máquina preferida: </label> " + maquinapreferida + "</div>";
        htmlText += "<div><Label>Hora habitual de visita: </label> " + horaHabitualVisita + "</div>";
        htmlText += "<div><Label>Días habituales de visita: </label> " + diaSemanaVisita + "</div>";
        htmlText += "<div><Label>Acompañante Habitual: </label> " + acompananteHabitual + "</div>";
        htmlText += "<div><Label>Personalidad: </label> " + descripcionPersonalidad + "</div>";
        htmlText += "<div><Label>Bebida preferida: </label> " + bebida + "</div>";
        htmlText += "<div><Label>Comida preferida: </label> " + comida + "</div>";
        htmlText += "<div><Label>Otros gustos: </label> " + gustosPreferencias + "</div>";
        htmlText += "<div><Label>Tipos de juego: </label> <ul>";
        for (Tipojuego tipojuegoList1 : tipojuegoList) {
            htmlText += "<li>" + tipojuegoList1.getNombre() + "</li>";
        }
        htmlText += " </ul></div>";
        return htmlText;
    }

}
