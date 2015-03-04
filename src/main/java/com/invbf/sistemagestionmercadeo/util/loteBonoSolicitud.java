/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Bononoincluido;
import com.invbf.sistemagestionmercadeo.entity.Lotebono;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregalote;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author ivan
 */
public class loteBonoSolicitud implements Serializable {

    private Integer id;
    private Integer cantidad;
    private Lotebono lotesBonosid;
    private List<BonosnoincluidosDTO> bonosnoincluidosList;
    private List<String> todosBonos;
    private List<String> Selected;
    private List<Integer> bonosReincluidos;
    private String desde;
    private String hasta;
    private Integer limite;

    public loteBonoSolicitud() {
    }

    public loteBonoSolicitud(Lotebono lote) {
        lotesBonosid = lote;
        cantidad = 0;
        bonosnoincluidosList = new ArrayList<BonosnoincluidosDTO>();
        this.desde = ConvertidorConsecutivo.sumarUno(lote.getHasta());
        this.hasta = ConvertidorConsecutivo.sumarUno(lote.getHasta());
        limite = 10000 - ConvertidorConsecutivo.getCantidadLote(desde);
        System.out.println("get");
        System.out.println(desde);
        System.out.println(hasta);

    }

    public loteBonoSolicitud(Integer id, Integer cantidad, Lotebono lotesBonosid, List<BonosnoincluidosDTO> bonosnoincluidosList) {
        this.id = id;
        this.cantidad = cantidad;
        this.lotesBonosid = lotesBonosid;
        this.bonosnoincluidosList = bonosnoincluidosList;

        this.desde = ConvertidorConsecutivo.sumarUno(lotesBonosid.getHasta());
        if (cantidad != 0) {
            this.hasta = ConvertidorConsecutivo.sumarCantidad(desde, this.cantidad - 1);
        } else {
            this.hasta = "";
        }
        todosBonos = new ArrayList<String>();
        Selected = new ArrayList<String>();
        limite = 10000 - ConvertidorConsecutivo.getCantidadLote(desde);
        Long from = ConvertidorConsecutivo.getNumeroFromConsecutivo(desde);
        for (int i = 1; i <= cantidad; i++) {
            todosBonos.add(ConvertidorConsecutivo.getConsecutivoFromNumero(from + cantidad));
        }
        System.out.println("get");
        System.out.println(desde);
        System.out.println(hasta);
    }

    public Solicitudentregalote getSolicitudEntregaLote() {
        Solicitudentregalote sol = new Solicitudentregalote(id);
        System.out.println("Cantidad " + cantidad);
        sol.setCantidad(cantidad);
        sol.setBononoincluidoList(new ArrayList<Bononoincluido>());
        sol.setDesde(ConvertidorConsecutivo.sumarUno(lotesBonosid.getHasta()));
        if (cantidad == 0) {
            sol.setHasta("");
        } else {
            sol.setHasta(ConvertidorConsecutivo.sumarCantidad(lotesBonosid.getHasta(), cantidad));
        }
        bonosReincluidos = new ArrayList<Integer>();
        for (BonosnoincluidosDTO bonosnoincluidos : bonosnoincluidosList) {
            if (bonosnoincluidos.getConsecutivo() != null && !bonosnoincluidos.getConsecutivo().equals("") && isBonoDentro(bonosnoincluidos)) {
                System.out.println("bono no incluido: " + bonosnoincluidos.getConsecutivo());
                Bononoincluido bni = new Bononoincluido(bonosnoincluidos.getId());
                bni.setConsecutivo(bonosnoincluidos.getConsecutivo());
                sol.getBononoincluidoList().add(bni);
            } else {
                if (bonosnoincluidos.getId() != null) {
                    bonosReincluidos.add(bonosnoincluidos.getId());
                }
            }
        }
        sol.setLotesBonosid(lotesBonosid);
        return sol;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Lotebono getLotesBonosid() {
        return lotesBonosid;
    }

    public void setLotesBonosid(Lotebono lotesBonosid) {
        this.lotesBonosid = lotesBonosid;
    }

    public List<BonosnoincluidosDTO> getBonosnoincluidosList() {
        return bonosnoincluidosList;
    }

    public void setBonosnoincluidosList(List<BonosnoincluidosDTO> bonosnoincluidosList) {
        this.bonosnoincluidosList = bonosnoincluidosList;
    }

    private boolean isBonoDentro(BonosnoincluidosDTO next) {
        String consecutivo = next.getConsecutivo();
        String desde = lotesBonosid.getHasta();
        System.out.println(consecutivo);
        System.out.println(desde);
        Long consecutivonumero = ConvertidorConsecutivo.getNumeroFromConsecutivo(consecutivo);
        Long desdenumero = ConvertidorConsecutivo.getNumeroFromConsecutivo(desde);
        System.out.println(desdenumero);
        System.out.println(consecutivonumero);
        System.out.println(cantidad);
        return desdenumero <= consecutivonumero && consecutivonumero <= desdenumero + cantidad;
    }

    public List<Integer> getBonosReincluidos() {
        return bonosReincluidos;
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

    public List<String> getTodosBonos() {
        return todosBonos;
    }

    public void setTodosBonos(List<String> todosBonos) {
        this.todosBonos = todosBonos;
    }

    public List<String> getSelected() {
        return Selected;
    }

    public void setSelected(List<String> Selected) {
        this.Selected = Selected;
    }

    public Integer getLimite() {
        return limite;
    }

    public void setLimite(Integer limite) {
        this.limite = limite;
    }

}
