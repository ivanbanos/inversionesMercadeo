/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.entity.Bononoincluido;
import com.invbf.sistemagestionmercadeo.entity.Lotebono;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregalote;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author ivan
 */
public class loteBonoSolicitud {

    private Integer id;
    private Integer cantidad;
    private Lotebono lotesBonosid;
    private List<BonosnoincluidosDTO> bonosnoincluidosList;
    private List<Integer> bonosReincluidos;
    private String desde;
    private String hasta;

    public loteBonoSolicitud() {
    }

    public loteBonoSolicitud(Lotebono lote) {
        lotesBonosid = lote;
        cantidad = 0;
        bonosnoincluidosList = new ArrayList<BonosnoincluidosDTO>();
        this.desde = ConvertidorConsecutivo.sumarUno(lote.getDesde());
        this.hasta = ConvertidorConsecutivo.sumarUno(lote.getDesde());

        System.out.println("get");
        System.out.println(desde);
        System.out.println(hasta);

    }

    public loteBonoSolicitud(Integer id, Integer cantidad, Lotebono lotesBonosid, List<BonosnoincluidosDTO> bonosnoincluidosList) {
        this.id = id;
        this.cantidad = cantidad;
        this.lotesBonosid = lotesBonosid;
        this.bonosnoincluidosList = bonosnoincluidosList;
        this.desde = ConvertidorConsecutivo.sumarUno(lotesBonosid.getDesde());
        this.hasta = ConvertidorConsecutivo.sumarCantidad(desde, this.cantidad-1 );
        System.out.println("get");
        System.out.println(desde);
        System.out.println(hasta);
    }

    public Solicitudentregalote getSolicitudEntregaLote() {
        Solicitudentregalote sol = new Solicitudentregalote(id);
        sol.setCantidad(cantidad);
        sol.setBononoincluidoList(new ArrayList<Bononoincluido>());
        sol.setDesde(desde);
        sol.setHasta(hasta);
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

}
