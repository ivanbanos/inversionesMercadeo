/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.reportes;

import com.invbf.sistemagestionmercadeo.controladores.GeneradorSolicitudLotesBonos;
import com.invbf.sistemagestionmercadeo.entity.Bononoincluido;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentrega;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregacliente;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregalote;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregalotesmaestro;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author ivan
 */
public class ReportCreator {

    public static void generadorEntregaBonosCaja(Solicitudentregalotesmaestro elemento) {
        try {
            ActaREciboCustodiaBonosCajaBean elementoReporte = new ActaREciboCustodiaBonosCajaBean();
            elementoReporte.setActnumber(elemento.getId().toString());
            elementoReporte.setBonosPorDenominacionEntregars(new ArrayList<ListaBonosPorDenominacionEntregar>());
            List<bonosnoincluidos> bonosnoincluidos = new ArrayList<bonosnoincluidos>();
            for (Solicitudentregalote sel : elemento.getSolicitudentregaloteList()) {
                ListaBonosPorDenominacionEntregar lbde = new ListaBonosPorDenominacionEntregar();
                lbde.setCantidad(sel.getCantidad().toString());
                lbde.setDenominacion(getAsString(sel.getLotesBonosid().getDenominacion().getValor() + " "));

                if (sel.getLotesBonosid().getTipoBono().getNombre().equals("PROMOCIONAL")) {
                    lbde.setSaladejuego(sel.getLotesBonosid().getIdCasino().getCasinodetalle().getAbreCiudad()+ " " + sel.getLotesBonosid().getTipoBono().getNombre());
                    lbde.setDel(sel.getLotesBonosid().getIdCasino().getCasinodetalle().getAbreCiudad()+ "-" +sel.getDesde());
                    lbde.setAl(sel.getLotesBonosid().getIdCasino().getCasinodetalle().getAbreCiudad()+ "-" +sel.getHasta());
                } else {
                    lbde.setSaladejuego(sel.getLotesBonosid().getIdCasino().getCasinodetalle().getAbrenopromo()+ " " + sel.getLotesBonosid().getTipoBono().getNombre());
                    lbde.setDel(sel.getLotesBonosid().getIdCasino().getCasinodetalle().getAbrenopromo()+ "-" + sel.getDesde());
                    lbde.setAl(sel.getLotesBonosid().getIdCasino().getCasinodetalle().getAbrenopromo()+ "-" + sel.getHasta());
                }
                elementoReporte.getBonosPorDenominacionEntregars().add(lbde);
                String bonosnoincluidosString = "Bono " + sel.getLotesBonosid().getTipoBono().getNombre() + " "
                        + " / " + sel.getLotesBonosid().getDenominacion().getValor() + " / Faltantes: ";
                if (sel.getBononoincluidoList().isEmpty()) {
                    for (Bononoincluido bonosnoincluido : sel.getBononoincluidoList()) {
                        bonosnoincluidosString += " " + bonosnoincluido.getConsecutivo();
                    }

                    bonosnoincluidos.add(new bonosnoincluidos(bonosnoincluidosString));
                }
            }

            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            InputStream input = externalContext.getResourceAsStream("/resources/reportes/ReciboCustodiaBonosCaja.jasper");
            InputStream subreport = externalContext.getResourceAsStream("/resources/reportes/ReciboCustodiaBonosCaja_detalle.jasper");
            InputStream bonosno = externalContext.getResourceAsStream("/resources/reportes/ReciboCustodiaBonosCaja_bonosnoincluidos.jasper");
            InputStream logotexas = externalContext.getResourceAsStream("/resources/reportes/LogoTLCNegro-01.jpeg");
            InputStream logomasters = externalContext.getResourceAsStream("/resources/reportes/IBFLogo01jpeg.jpeg");
            InputStream logoibf = externalContext.getResourceAsStream("/resources/reportes/LogoMRCNegro.jpeg");
            ImageIcon tlc = new ImageIcon(IOUtils.toByteArray(logotexas));
            ImageIcon mrc = new ImageIcon(IOUtils.toByteArray(logomasters));
            ImageIcon ibf = new ImageIcon(IOUtils.toByteArray(logoibf));
            elementoReporte.setIbf(ibf.getImage());
            elementoReporte.setMrc(mrc.getImage());
            elementoReporte.setTlc(tlc.getImage());
            elementoReporte.setBonosnoincluidos(bonosnoincluidos);
            List<ActaREciboCustodiaBonosCajaBean> lista = new ArrayList<ActaREciboCustodiaBonosCajaBean>();
            lista.add(elementoReporte);
            JRBeanCollectionDataSource beanColDataSource
                    = new JRBeanCollectionDataSource(lista);
            JasperReport jasperMasterReport = (JasperReport) JRLoader.loadObject(input);
            JasperReport jasperSubReport = (JasperReport) JRLoader.loadObject(subreport);
            JasperReport bonosnoReport = (JasperReport) JRLoader.loadObject(bonosno);
            elementoReporte.setSubreportParameter(jasperSubReport);
            elementoReporte.setBonosno(bonosnoReport);
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("actnumber", lista.get(0).getActnumber());
            parameters.put("tlc", lista.get(0).getTlc());
            parameters.put("ibf", lista.get(0).getIbf());
            parameters.put("mrc", lista.get(0).getMrc());
            parameters.put("subreportParameter", jasperSubReport);
            parameters.put("bonosno", bonosnoReport);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperMasterReport, parameters, beanColDataSource);
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=ActaReciboCustodiaBonosCaja" + elemento.getId() + ".pdf");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        } catch (JRException ex) {
            Logger.getLogger(GeneradorSolicitudLotesBonos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GeneradorSolicitudLotesBonos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void generadorSolicitudBono(Solicitudentrega elemento) {
        ServletOutputStream servletOutputStream = null;
        try {
            SolicitudBonoJuego elementoReporte = new SolicitudBonoJuego();
            if (elemento.getSolicitante().getUsuariodetalle().getIdcargo() != null) {
                elementoReporte.setCargo(elemento.getSolicitante().getUsuariodetalle().getIdcargo().getNombre());
            } else {
                elementoReporte.setCargo("");
            }
            elementoReporte.setCasino(elemento.getIdCasino().getNombre());
            SimpleDateFormat formateador = new SimpleDateFormat("dd-MMMM-yy", new Locale("es_ES"));
            elementoReporte.setFecha(formateador.format(elemento.getFecha()));
            elementoReporte.setJustificacion(elemento.getJustificacion());
            elementoReporte.setNombre(elemento.getSolicitante().getNombreUsuario());
            elementoReporte.setProposito(elemento.getPropositoEntrega().getNombre());
            elementoReporte.setTipobono(elemento.getTipoBono().getNombre());
            List<SolicitudBonoJuegoCliente> clientes = new ArrayList<SolicitudBonoJuegoCliente>();
            int item = 1;
            Float total = 0f;
            for (Solicitudentregacliente sec : elemento.getSolicitudentregaclienteList()) {
                clientes.add(new SolicitudBonoJuegoCliente((item) + "", sec.getCliente().getNombres() + " " + sec.getCliente().getApellidos(), sec.getValorTotal().toString(), sec.getAreaid().getNombre()));
                total += sec.getValorTotal();
                item++;
            }
            elementoReporte.setClientes(clientes);
            elementoReporte.setTotal(total.toString());
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            InputStream input = externalContext.getResourceAsStream("/resources/reportes/solicitudbonosjuego.jasper");
            InputStream subreport = externalContext.getResourceAsStream("/resources/reportes/solicitudbonosjuego_cliente.jasper");
            InputStream logo = externalContext.getResourceAsStream("/resources/reportes/LogoMRCNegro.jpeg");
            InputStream logoibf = externalContext.getResourceAsStream("/resources/reportes/IBFLogo01jpeg.jpeg");
            ImageIcon tlc = new ImageIcon(IOUtils.toByteArray(logo));
            ImageIcon ibf = new ImageIcon(IOUtils.toByteArray(logoibf));
            elementoReporte.setIbf(ibf.getImage());
            elementoReporte.setLogo(tlc.getImage());
            List<SolicitudBonoJuego> lista = new ArrayList<SolicitudBonoJuego>();
            lista.add(elementoReporte);
            JRBeanCollectionDataSource beanColDataSource
                    = new JRBeanCollectionDataSource(lista);
            JasperReport jasperMasterReport = (JasperReport) JRLoader.loadObject(input);
            JasperReport jasperSubReport = (JasperReport) JRLoader.loadObject(subreport);
            elementoReporte.setSubreportclientes(jasperSubReport);
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("logo", lista.get(0).getLogo());
            parameters.put("ibf", lista.get(0).getIbf());
            parameters.put("clientes", lista.get(0).getClientes());
            parameters.put("subreportclientes", jasperSubReport);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperMasterReport, parameters, beanColDataSource);
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=ActaSolicitudBonosJuego" + elemento.getId() + ".pdf");
            servletOutputStream = httpServletResponse.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        } catch (IOException ex) {
            Logger.getLogger(ReportCreator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(ReportCreator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                servletOutputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(ReportCreator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static String getAsString(Object o) {
        String numberseparated = "";
        String iPartS = "";
        if (o instanceof Double) {
            double number = (Double) o;
            Long iPart = (long) number;
            iPartS = iPart.toString();
        } else if (o instanceof Float) {
            float number = (Float) o;
            Long iPart = (long) number;
            iPartS = iPart.toString();
        } else {
            String number = (String) o;
            System.out.println("numero " + number);
            if (number.lastIndexOf(".") != -1) {
                iPartS = number.substring(0, number.lastIndexOf("."));
            } else {
                iPartS = number;
            }
        }
        boolean milesima = true;

        while (true) {
            System.out.println("asi va el numero " + numberseparated);
            System.out.println("y queda esto " + iPartS);
            if (iPartS.length() == 0) {
                break;
            } else if (iPartS.length() <= 3) {
                numberseparated = iPartS + numberseparated;
                iPartS = "";
            } else {
                if (milesima) {
                    numberseparated = "." + iPartS.substring(iPartS.length() - 3) + numberseparated;
                    iPartS = iPartS.substring(0, iPartS.length() - 3);
                    milesima = false;
                } else {
                    numberseparated = "'" + iPartS.substring(iPartS.length() - 3) + numberseparated;
                    iPartS = iPartS.substring(0, iPartS.length() - 3);
                    milesima = true;
                }
            }
        }
        System.out.println("asi quedo sin parte real " + numberseparated);
        System.out.println("asi quedo con parte real " + numberseparated);
        System.out.println();
        System.out.println();
        return numberseparated;
    }
    
    
    public static void generadorPreorden(Solicitudentregalotesmaestro elemento) {
        try {
            ActaREciboCustodiaBonosCajaBean elementoReporte = new ActaREciboCustodiaBonosCajaBean();
            elementoReporte.setActnumber(elemento.getId().toString());
            elementoReporte.setBonosPorDenominacionEntregars(new ArrayList<ListaBonosPorDenominacionEntregar>());
            List<bonosnoincluidos> bonosnoincluidos = new ArrayList<bonosnoincluidos>();
            for (Solicitudentregalote sel : elemento.getSolicitudentregaloteList()) {
                ListaBonosPorDenominacionEntregar lbde = new ListaBonosPorDenominacionEntregar();
                lbde.setCantidad(sel.getCantidad().toString());
                lbde.setDenominacion(getAsString(sel.getLotesBonosid().getDenominacion().getValor() + " "));

                if (sel.getLotesBonosid().getTipoBono().getNombre().equals("PROMOCIONAL")) {
                    lbde.setSaladejuego(sel.getLotesBonosid().getIdCasino().getCasinodetalle().getAbreCiudad()+ " " + sel.getLotesBonosid().getTipoBono().getNombre());
                    lbde.setDel(sel.getLotesBonosid().getIdCasino().getCasinodetalle().getAbreCiudad()+ "-" +sel.getDesde());
                    lbde.setAl(sel.getLotesBonosid().getIdCasino().getCasinodetalle().getAbreCiudad()+ "-" +sel.getHasta());
                } else {
                    lbde.setSaladejuego(sel.getLotesBonosid().getIdCasino().getCasinodetalle().getAbrenopromo()+ " " + sel.getLotesBonosid().getTipoBono().getNombre());
                    lbde.setDel(sel.getLotesBonosid().getIdCasino().getCasinodetalle().getAbrenopromo()+ "-" + sel.getDesde());
                    lbde.setAl(sel.getLotesBonosid().getIdCasino().getCasinodetalle().getAbrenopromo()+ "-" + sel.getHasta());
                }
                elementoReporte.getBonosPorDenominacionEntregars().add(lbde);
                String bonosnoincluidosString = "Bono " + sel.getLotesBonosid().getTipoBono().getNombre() + " "
                        + " / " + sel.getLotesBonosid().getDenominacion().getValor() + " / Faltantes: ";
                if (sel.getBononoincluidoList().isEmpty()) {
                    for (Bononoincluido bonosnoincluido : sel.getBononoincluidoList()) {
                        bonosnoincluidosString += " " + bonosnoincluido.getConsecutivo();
                    }

                    bonosnoincluidos.add(new bonosnoincluidos(bonosnoincluidosString));
                }
            }

            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            InputStream input = externalContext.getResourceAsStream("/resources/reportes/OrdenProduccionLoteBono.jasper");
            InputStream subreport = externalContext.getResourceAsStream("/resources/reportes/ReciboCustodiaBonosCaja_detalle.jasper");
            InputStream logotexas = externalContext.getResourceAsStream("/resources/reportes/LogoTLCNegro-01.jpeg");
            InputStream logomasters = externalContext.getResourceAsStream("/resources/reportes/IBFLogo01jpeg.jpeg");
            InputStream logoibf = externalContext.getResourceAsStream("/resources/reportes/LogoMRCNegro.jpeg");
            ImageIcon tlc = new ImageIcon(IOUtils.toByteArray(logotexas));
            ImageIcon mrc = new ImageIcon(IOUtils.toByteArray(logomasters));
            ImageIcon ibf = new ImageIcon(IOUtils.toByteArray(logoibf));
            elementoReporte.setIbf(ibf.getImage());
            elementoReporte.setMrc(mrc.getImage());
            elementoReporte.setTlc(tlc.getImage());
            elementoReporte.setBonosnoincluidos(bonosnoincluidos);
            List<ActaREciboCustodiaBonosCajaBean> lista = new ArrayList<ActaREciboCustodiaBonosCajaBean>();
            lista.add(elementoReporte);
            JRBeanCollectionDataSource beanColDataSource
                    = new JRBeanCollectionDataSource(lista);
            JasperReport jasperMasterReport = (JasperReport) JRLoader.loadObject(input);
            JasperReport jasperSubReport = (JasperReport) JRLoader.loadObject(subreport);
            elementoReporte.setSubreportParameter(jasperSubReport);
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("actnumber", lista.get(0).getActnumber());
            parameters.put("tlc", lista.get(0).getTlc());
            parameters.put("ibf", lista.get(0).getIbf());
            parameters.put("mrc", lista.get(0).getMrc());
            parameters.put("subreportParameter", jasperSubReport);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperMasterReport, parameters, beanColDataSource);
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=OrdenProduccionLotesBonos" + elemento.getId() + ".pdf");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        } catch (JRException ex) {
            Logger.getLogger(GeneradorSolicitudLotesBonos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GeneradorSolicitudLotesBonos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
