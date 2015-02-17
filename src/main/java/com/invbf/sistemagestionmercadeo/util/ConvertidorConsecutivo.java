/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import java.util.HashMap;

/**
 *
 * @author ivan
 */
public class ConvertidorConsecutivo {

    private static final HashMap<String, Long> mapLetrasValores = new HashMap<String, Long>();

    private static final HashMap<Long, String> mapValoresLetras = new HashMap<Long, String>();

    static {
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

    public static Long getNumeroFromConsecutivo(String consecutivo) {
        System.out.println(consecutivo);
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

    public static String getConsecutivoFromNumero(long numero) {
        long parteInicial = numero % 10000;
        long cantidad = numero / 10000;
        String letraactual;
        String nuevaletra = "";
        while (cantidad != 0) {
            nuevaletra = mapValoresLetras.get(cantidad % 26) + nuevaletra;
            letraactual = mapValoresLetras.get(cantidad % 26);
            cantidad = cantidad / 26;
            if (letraactual.equals("Z") && cantidad == 1) {
                break;
            }
        }
        if (parteInicial < 10) {
            return "000" + parteInicial + "-" + nuevaletra;
        } else if (parteInicial < 100) {
            return "00" + parteInicial + "-" + nuevaletra;
        } else if (parteInicial < 1000) {
            return "0" + parteInicial + "-" + nuevaletra;
        } else {
            return parteInicial + "-" + nuevaletra;
        }
    }

    public static String sumarUno(String desde) {
        return getConsecutivoFromNumero(getNumeroFromConsecutivo(desde) + 1);
    }
    
    public static String sumarCantidad(String desde, int cantidad) {
        return getConsecutivoFromNumero(getNumeroFromConsecutivo(desde) + cantidad);
    }
    
    public static String getCantidad(String desde, String hasta){
        System.out.println("desde"+desde);
        System.out.println("hasta"+hasta);
        return (getNumeroFromConsecutivo(hasta) - getNumeroFromConsecutivo(desde))+"";
    }
    public static Long getCantidadInt(String desde, String hasta){
        System.out.println("desde"+desde);
        System.out.println("hasta"+hasta);
        return (getNumeroFromConsecutivo(hasta) - getNumeroFromConsecutivo(desde));
    }
}
