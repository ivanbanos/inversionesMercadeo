/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.reportes;

import java.awt.Image;
import java.util.List;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author ivan
 */
public class SolicitudBonoJuego {
    private Image ibf;
    private Image logo;
    private String casino;
    private String nombre;
    private String cargo;
    private String fecha;
    private String tipobono;
    private String proposito;
    private String justificacion;
    private String total;
    private JasperReport subreportclientes;
    private List<SolicitudBonoJuegoCliente> clientes;

    public SolicitudBonoJuego() {
    }

    public SolicitudBonoJuego(Image ibf, Image logo, String casino, String nombre, String cargo, String fecha, String tipobono, String proposito, String justificacion, String total, JasperReport subreportclientes, List<SolicitudBonoJuegoCliente> clientes) {
        this.ibf = ibf;
        this.logo = logo;
        this.casino = casino;
        this.nombre = nombre;
        this.cargo = cargo;
        this.fecha = fecha;
        this.tipobono = tipobono;
        this.proposito = proposito;
        this.justificacion = justificacion;
        this.total = total;
        this.subreportclientes = subreportclientes;
        this.clientes = clientes;
    }

    public Image getIbf() {
        return ibf;
    }

    public void setIbf(Image ibf) {
        this.ibf = ibf;
    }

    public Image getLogo() {
        return logo;
    }

    public void setLogo(Image logo) {
        this.logo = logo;
    }

    public String getCasino() {
        return casino;
    }

    public void setCasino(String casino) {
        this.casino = casino;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipobono() {
        return tipobono;
    }

    public void setTipobono(String tipobono) {
        this.tipobono = tipobono;
    }

    public String getProposito() {
        return proposito;
    }

    public void setProposito(String proposito) {
        this.proposito = proposito;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public JasperReport getSubreportclientes() {
        return subreportclientes;
    }

    public void setSubreportclientes(JasperReport subreportclientes) {
        this.subreportclientes = subreportclientes;
    }

    public List<SolicitudBonoJuegoCliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<SolicitudBonoJuegoCliente> clientes) {
        this.clientes = clientes;
    }
            
    
}
