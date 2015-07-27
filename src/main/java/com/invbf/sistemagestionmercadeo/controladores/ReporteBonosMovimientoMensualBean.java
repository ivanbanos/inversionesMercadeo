/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.controladores;

import com.invbf.sistemagestionmercadeo.dto.BonosAprobadosCanjeados;
import com.invbf.sistemagestionmercadeo.dto.MesesCant;
import com.invbf.sistemagestionmercadeo.dto.MesesCantFloat;
import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.util.CasinoBoolean;
import com.invbf.sistemagestionmercadeo.util.Mensajes;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author ivan
 */
@ManagedBean
@ViewScoped
public class ReporteBonosMovimientoMensualBean implements Serializable {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<CasinoBoolean> casinos;
    private List<Integer> anos;
    private Integer ano;
    private Integer mes;
    private Integer annodesde;
    private Integer mesdesde;
    private List<BonosAprobadosCanjeados> bonosCantidad;
    private List<LineChartModel> lineModels;
    private long max;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    @PostConstruct
    public void init() {

        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("reportes");

        if (!sessionBean.perfilViewMatch("Reportes")) {
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        sessionBean.revisarEstadoBonos();
        List<Casino> casinosNormales = sessionBean.adminFacade.findAllCasinos();
        casinos = new ArrayList<CasinoBoolean>();
        for (Casino casinosNormale : casinosNormales) {
            casinos.add(new CasinoBoolean(casinosNormale, true));
        }

        anos = new ArrayList<Integer>();
        Calendar c = Calendar.getInstance();
        ano = c.get(Calendar.YEAR);
        mes = c.get(Calendar.MONTH);
        for (int i = 2015; i <= c.get(Calendar.YEAR); i++) {
            anos.add(i);
        }
        annodesde = ano;
        mesdesde = mes;
        buscarBonos();
    }

    public void buscarBonosPorCasinosYFecha() {

        System.gc();
    }

    public void buscarBonos() {
        if (annodesde > ano || ((annodesde == ano) && (mesdesde > mes))) {
            sessionBean.putMensaje(new Mensajes(Mensajes.ERROR, "Error en las fechas", "La fecha desde debe ser menor a la fecha hasta"));
        } else {
            bonosCantidad = sessionBean.marketingUserFacade.getBonosPorCantidadMesuales(casinos, ano, mes, annodesde, mesdesde);
            System.out.println("Cantidad de graficos = " + bonosCantidad.size());
            resolverbonos();
        }
    }

    public List<CasinoBoolean> getCasinos() {
        return casinos;
    }

    public void setCasinos(List<CasinoBoolean> casinos) {
        this.casinos = casinos;
    }

    public List<Integer> getAnos() {
        return anos;
    }

    public void setAnos(List<Integer> anos) {
        this.anos = anos;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAnnodesde() {
        return annodesde;
    }

    public void setAnnodesde(Integer annodesde) {
        this.annodesde = annodesde;
    }

    public Integer getMesdesde() {
        return mesdesde;
    }

    public void setMesdesde(Integer mesdesde) {
        this.mesdesde = mesdesde;
    }

    private void resolverbonos() {

        lineModels = new ArrayList<LineChartModel>();
        for (BonosAprobadosCanjeados bonos : bonosCantidad) {
            max = 0;
            LineChartModel lineModel = initLinearModel(bonos);
            lineModel.setTitle(bonos.getCasino());
            lineModel.setLegendPosition("e");
            lineModel.getAxes().put(AxisType.X, new CategoryAxis("Mes/Año"));
            lineModel.setShowPointLabels(true);
            Axis yAxis = lineModel.getAxis(AxisType.Y);
            yAxis.setLabel("$");
            yAxis.setMin(0);
            yAxis.setMax((long) max + (max/20));
            lineModels.add(lineModel);
        }
    }

    private LineChartModel initLinearModel(BonosAprobadosCanjeados bonos) {
        LineChartModel model = new LineChartModel();

        ChartSeries series1 = new ChartSeries();
        ChartSeries series2 = new ChartSeries();
        series1.setLabel("Aprobados");
        series2.setLabel("Canjeados");
        for (MesesCantFloat s : bonos.getMesesCant()) {
            System.out.println(s.getMesanio());
            System.out.println((long) s.getCantidad());
            System.out.println((long) s.getCantidad2());
            max = (long) (max < s.getCantidad() ? s.getCantidad() : max);
            max = (long) (max < s.getCantidad() ? s.getCantidad2() : max);
            series1.set(s.getMesanio(), (long) s.getCantidad());
            series2.set(s.getMesanio(), (long) s.getCantidad2());
            System.out.println(max);
        }
        model.addSeries(series1);
        model.addSeries(series2);

        return model;
    }

    public List<LineChartModel> getLineModels() {
        return lineModels;
    }

    public void setLineModels(List<LineChartModel> lineModels) {
        this.lineModels = lineModels;
    }

}
