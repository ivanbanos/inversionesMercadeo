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
public class ActaREciboCustodiaBonosCajaBean {
    private String actnumber;
    private List<ListaBonosPorDenominacionEntregar> bonosPorDenominacionEntregars;
    private List<bonosnoincluidos> bonosnoincluidos;
    private Image ibf;
    private Image mrc;
    private Image tlc;
    private JasperReport subreportParameter;
    private JasperReport bonosno;

    public ActaREciboCustodiaBonosCajaBean() {
    }

    public ActaREciboCustodiaBonosCajaBean(String actnumber, List<ListaBonosPorDenominacionEntregar> bonosPorDenominacionEntregars) {
        this.actnumber = actnumber;
        this.bonosPorDenominacionEntregars = bonosPorDenominacionEntregars;
    }

    public String getActnumber() {
        return actnumber;
    }

    public void setActnumber(String actnumber) {
        this.actnumber = actnumber;
    }

    public List<ListaBonosPorDenominacionEntregar> getBonosPorDenominacionEntregars() {
        return bonosPorDenominacionEntregars;
    }

    public void setBonosPorDenominacionEntregars(List<ListaBonosPorDenominacionEntregar> bonosPorDenominacionEntregars) {
        this.bonosPorDenominacionEntregars = bonosPorDenominacionEntregars;
    }

    public Image getIbf() {
        return ibf;
    }

    public void setIbf(Image ibf) {
        this.ibf = ibf;
    }

    public Image getMrc() {
        return mrc;
    }

    public void setMrc(Image mrc) {
        this.mrc = mrc;
    }

    public Image getTlc() {
        return tlc;
    }

    public void setTlc(Image tlc) {
        this.tlc = tlc;
    }

    public JasperReport getSubreportParameter() {
        return subreportParameter;
    }

    public void setSubreportParameter(JasperReport subreportParameter) {
        this.subreportParameter = subreportParameter;
    }

    public List<bonosnoincluidos> getBonosnoincluidos() {
        return bonosnoincluidos;
    }

    public void setBonosnoincluidos(List<bonosnoincluidos> bonosnoincluidos) {
        this.bonosnoincluidos = bonosnoincluidos;
    }

    public JasperReport getBonosno() {
        return bonosno;
    }

    public void setBonosno(JasperReport bonosno) {
        this.bonosno = bonosno;
    }
    
    
}
