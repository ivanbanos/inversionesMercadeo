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

    private final HashMap<String, Long> mapLetrasValores;
    private final HashMap<Long, String> mapValoresLetras;

    public loteBonoSolicitud() {
        mapLetrasValores = new HashMap<String, Long>();
        mapLetrasValores.put("A", 1l);
        mapLetrasValores.put("B", 2l);
        mapLetrasValores.put("C", 3l);
        mapLetrasValores.put("D", 4l);
        mapLetrasValores.put("E", 5l);
        mapLetrasValores.put("F", 6l);
        mapLetrasValores.put("G", 7l);
        mapLetrasValores.put("H", 8l);
        mapLetrasValores.put("I", 9l);
        mapLetrasValores.put("J", 10l);
        mapLetrasValores.put("K", 11l);
        mapLetrasValores.put("L", 12l);
        mapLetrasValores.put("M", 13l);
        mapLetrasValores.put("N", 14l);
        mapLetrasValores.put("O", 15l);
        mapLetrasValores.put("P", 16l);
        mapLetrasValores.put("Q", 17l);
        mapLetrasValores.put("R", 18l);
        mapLetrasValores.put("S", 19l);
        mapLetrasValores.put("T", 20l);
        mapLetrasValores.put("U", 21l);
        mapLetrasValores.put("V", 22l);
        mapLetrasValores.put("W", 23l);
        mapLetrasValores.put("X", 24l);
        mapLetrasValores.put("Y", 25l);
        mapLetrasValores.put("Z", 26l);

        mapValoresLetras = new HashMap<Long, String>();
        mapValoresLetras.put(1l, "A");
        mapValoresLetras.put(2l, "B");
        mapValoresLetras.put(3l, "C");
        mapValoresLetras.put(4l, "D");
        mapValoresLetras.put(5l, "E");
        mapValoresLetras.put(6l, "F");
        mapValoresLetras.put(7l, "G");
        mapValoresLetras.put(8l, "H");
        mapValoresLetras.put(9l, "I");
        mapValoresLetras.put(10l, "J");
        mapValoresLetras.put(11l, "K");
        mapValoresLetras.put(12l, "L");
        mapValoresLetras.put(13l, "M");
        mapValoresLetras.put(14l, "N");
        mapValoresLetras.put(15l, "O");
        mapValoresLetras.put(16l, "P");
        mapValoresLetras.put(17l, "Q");
        mapValoresLetras.put(18l, "R");
        mapValoresLetras.put(19l, "S");
        mapValoresLetras.put(20l, "T");
        mapValoresLetras.put(21l, "U");
        mapValoresLetras.put(22l, "V");
        mapValoresLetras.put(23l, "W");
        mapValoresLetras.put(24l, "X");
        mapValoresLetras.put(25l, "Y");
        mapValoresLetras.put(26l, "Z");
    }

    public loteBonoSolicitud(Lotebono lote) {
        lotesBonosid = lote;
        cantidad = 0;
        bonosnoincluidosList = new ArrayList<BonosnoincluidosDTO>();
        mapLetrasValores = new HashMap<String, Long>();
        mapLetrasValores.put("A", 1l);
        mapLetrasValores.put("B", 2l);
        mapLetrasValores.put("C", 3l);
        mapLetrasValores.put("D", 4l);
        mapLetrasValores.put("E", 5l);
        mapLetrasValores.put("F", 6l);
        mapLetrasValores.put("G", 7l);
        mapLetrasValores.put("H", 8l);
        mapLetrasValores.put("I", 9l);
        mapLetrasValores.put("J", 10l);
        mapLetrasValores.put("K", 11l);
        mapLetrasValores.put("L", 12l);
        mapLetrasValores.put("M", 13l);
        mapLetrasValores.put("N", 14l);
        mapLetrasValores.put("O", 15l);
        mapLetrasValores.put("P", 16l);
        mapLetrasValores.put("Q", 17l);
        mapLetrasValores.put("R", 18l);
        mapLetrasValores.put("S", 19l);
        mapLetrasValores.put("T", 20l);
        mapLetrasValores.put("U", 21l);
        mapLetrasValores.put("V", 22l);
        mapLetrasValores.put("W", 23l);
        mapLetrasValores.put("X", 24l);
        mapLetrasValores.put("Y", 25l);
        mapLetrasValores.put("Z", 26l);

        mapValoresLetras = new HashMap<Long, String>();
        mapValoresLetras.put(1l, "A");
        mapValoresLetras.put(2l, "B");
        mapValoresLetras.put(3l, "C");
        mapValoresLetras.put(4l, "D");
        mapValoresLetras.put(5l, "E");
        mapValoresLetras.put(6l, "F");
        mapValoresLetras.put(7l, "G");
        mapValoresLetras.put(8l, "H");
        mapValoresLetras.put(9l, "I");
        mapValoresLetras.put(10l, "J");
        mapValoresLetras.put(11l, "K");
        mapValoresLetras.put(12l, "L");
        mapValoresLetras.put(13l, "M");
        mapValoresLetras.put(14l, "N");
        mapValoresLetras.put(15l, "O");
        mapValoresLetras.put(16l, "P");
        mapValoresLetras.put(17l, "Q");
        mapValoresLetras.put(18l, "R");
        mapValoresLetras.put(19l, "S");
        mapValoresLetras.put(20l, "T");
        mapValoresLetras.put(21l, "U");
        mapValoresLetras.put(22l, "V");
        mapValoresLetras.put(23l, "W");
        mapValoresLetras.put(24l, "X");
        mapValoresLetras.put(25l, "Y");
        mapValoresLetras.put(26l, "Z");
    }

    public loteBonoSolicitud(Integer id, Integer cantidad, Lotebono lotesBonosid, List<BonosnoincluidosDTO> bonosnoincluidosList) {
        this.id = id;
        this.cantidad = cantidad;
        this.lotesBonosid = lotesBonosid;
        this.bonosnoincluidosList = bonosnoincluidosList;
        mapLetrasValores = new HashMap<String, Long>();
        mapLetrasValores.put("A", 1l);
        mapLetrasValores.put("B", 2l);
        mapLetrasValores.put("C", 3l);
        mapLetrasValores.put("D", 4l);
        mapLetrasValores.put("E", 5l);
        mapLetrasValores.put("F", 6l);
        mapLetrasValores.put("G", 7l);
        mapLetrasValores.put("H", 8l);
        mapLetrasValores.put("I", 9l);
        mapLetrasValores.put("J", 10l);
        mapLetrasValores.put("K", 11l);
        mapLetrasValores.put("L", 12l);
        mapLetrasValores.put("M", 13l);
        mapLetrasValores.put("N", 14l);
        mapLetrasValores.put("O", 15l);
        mapLetrasValores.put("P", 16l);
        mapLetrasValores.put("Q", 17l);
        mapLetrasValores.put("R", 18l);
        mapLetrasValores.put("S", 19l);
        mapLetrasValores.put("T", 20l);
        mapLetrasValores.put("U", 21l);
        mapLetrasValores.put("V", 22l);
        mapLetrasValores.put("W", 23l);
        mapLetrasValores.put("X", 24l);
        mapLetrasValores.put("Y", 25l);
        mapLetrasValores.put("Z", 26l);

        mapValoresLetras = new HashMap<Long, String>();
        mapValoresLetras.put(1l, "A");
        mapValoresLetras.put(2l, "B");
        mapValoresLetras.put(3l, "C");
        mapValoresLetras.put(4l, "D");
        mapValoresLetras.put(5l, "E");
        mapValoresLetras.put(6l, "F");
        mapValoresLetras.put(7l, "G");
        mapValoresLetras.put(8l, "H");
        mapValoresLetras.put(9l, "I");
        mapValoresLetras.put(10l, "J");
        mapValoresLetras.put(11l, "K");
        mapValoresLetras.put(12l, "L");
        mapValoresLetras.put(13l, "M");
        mapValoresLetras.put(14l, "N");
        mapValoresLetras.put(15l, "O");
        mapValoresLetras.put(16l, "P");
        mapValoresLetras.put(17l, "Q");
        mapValoresLetras.put(18l, "R");
        mapValoresLetras.put(19l, "S");
        mapValoresLetras.put(20l, "T");
        mapValoresLetras.put(21l, "U");
        mapValoresLetras.put(22l, "V");
        mapValoresLetras.put(23l, "W");
        mapValoresLetras.put(24l, "X");
        mapValoresLetras.put(25l, "Y");
        mapValoresLetras.put(26l, "Z");
    }

    public Solicitudentregalote getSolicitudEntregaLote() {
        Solicitudentregalote sol = new Solicitudentregalote(id);
        sol.setCantidad(cantidad);
        sol.setBononoincluidoList(new ArrayList<Bononoincluido>());
        bonosReincluidos = new ArrayList<Integer>();
        for (BonosnoincluidosDTO bonosnoincluidos : bonosnoincluidosList) {
            if (bonosnoincluidos.getConsecutivo() != null && !bonosnoincluidos.getConsecutivo().equals("")&&isBonoDentro(bonosnoincluidos)) {
                System.out.println("bono no incluido: " + bonosnoincluidos.getConsecutivo());
                Bononoincluido bni = new Bononoincluido(bonosnoincluidos.getId());
                bni.setConsecutivo(bonosnoincluidos.getConsecutivo());
                sol.getBononoincluidoList().add(bni);
            } else {
                if(bonosnoincluidos.getId()!=null){
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
        Long consecutivonumero = getNumeroFromConsecutivo(consecutivo);
        Long desdenumero = getNumeroFromConsecutivo(desde);
        System.out.println(desdenumero);
        System.out.println(consecutivonumero);
        System.out.println(cantidad);
        return desdenumero <= consecutivonumero && consecutivonumero <= desdenumero + cantidad;
    }

    private Long getNumeroFromConsecutivo(String consecutivo) {
        long numerofrom = Long.parseLong(consecutivo.substring(0, 4));
        long total = numerofrom;
        Long cantidad = 0l;
        int factor = 0;
        String letra = consecutivo.substring(5, consecutivo.length());
        for (int i = letra.length() - 1; i >= 0; i--) {
            cantidad += (long) (Math.pow(26, factor)) * mapLetrasValores.get(letra.substring(i, i + 1));
            factor += 1;

        }
        return (cantidad * 10000) + total;
    }

    public List<Integer> getBonosReincluidos() {
        return bonosReincluidos;
    }


}
