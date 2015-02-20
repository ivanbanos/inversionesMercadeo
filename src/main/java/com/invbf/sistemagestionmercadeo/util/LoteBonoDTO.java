/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Bononofisico;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Denominacion;
import com.invbf.sistemagestionmercadeo.entity.Tipobono;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ivan
 */
public class LoteBonoDTO  implements Serializable{
    private Integer id;
    private String desde;
    private String hasta;
    private List<Bononofisico> bononofisicoList;
    private Denominacion denominacion;
    private Tipobono tipoBono;
    private Casino idCasino;
    private String cantidad;

    public LoteBonoDTO() {
    }

    public LoteBonoDTO(Integer id) {
        this.id = id;
    }

    public LoteBonoDTO(Integer id, String desde, String hasta, List<Bononofisico> bononofisicoList, Denominacion denominacion, Tipobono tipoBono, Casino idCasino) {
        this.id = id;
        this.desde = desde;
        this.hasta = hasta;
        this.bononofisicoList = bononofisicoList;
        this.denominacion = denominacion;
        this.tipoBono = tipoBono;
        this.idCasino = idCasino;
        cantidad = ConvertidorConsecutivo.getCantidad(desde, hasta);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesde() {
        return desde;
    }

    public void setDesde(String desde) {
        this.desde = desde;
    }

    public String getHasta() {
        return hasta;
    }

    public void setHasta(String hasta) {
        this.hasta = hasta;
    }

    public List<Bononofisico> getBononofisicoList() {
        return bononofisicoList;
    }

    public void setBononofisicoList(List<Bononofisico> bononofisicoList) {
        this.bononofisicoList = bononofisicoList;
    }

    public Denominacion getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(Denominacion denominacion) {
        this.denominacion = denominacion;
    }

    public Tipobono getTipoBono() {
        return tipoBono;
    }

    public void setTipoBono(Tipobono tipoBono) {
        this.tipoBono = tipoBono;
    }

    public Casino getIdCasino() {
        return idCasino;
    }

    public void setIdCasino(Casino idCasino) {
        this.idCasino = idCasino;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
    
    
}
