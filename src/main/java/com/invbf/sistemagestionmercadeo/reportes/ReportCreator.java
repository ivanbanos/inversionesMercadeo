/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.reportes;

import com.invbf.sistemagestionmercadeo.controladores.GeneradorSolicitudLotesBonos;
import com.invbf.sistemagestionmercadeo.entity.Bononoincluido;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregalote;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregalotesmaestro;
import com.invbf.sistemagestionmercadeo.util.ConvertidorConsecutivo;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
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
                lbde.setDenominacion(sel.getLotesBonosid().getDenominacion().getValor() + " ");
                lbde.setSaladejuego(sel.getLotesBonosid().getIdCasino().getCasinodetalle().getAbreCiudad() + " " + sel.getLotesBonosid().getTipoBono().getNombre());
                lbde.setDel(ConvertidorConsecutivo.sumarUno(sel.getLotesBonosid().getHasta()));
                lbde.setAl(ConvertidorConsecutivo.sumarCantidad(ConvertidorConsecutivo.sumarUno(sel.getLotesBonosid().getHasta()), sel.getCantidad() - 1));
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
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=reporteReciboCustodiaBonosCaja" + elemento.getId() + ".pdf");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        } catch (JRException ex) {
            Logger.getLogger(GeneradorSolicitudLotesBonos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GeneradorSolicitudLotesBonos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
