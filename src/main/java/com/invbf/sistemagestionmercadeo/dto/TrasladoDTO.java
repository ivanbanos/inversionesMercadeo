/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dto;

import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Inventarobarajas;
import com.invbf.sistemagestionmercadeo.entity.Trasladobarajadetalle;
import com.invbf.sistemagestionmercadeo.entity.Trasladobarajas;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ivan
 */
public class TrasladoDTO implements Serializable {

    private Integer id;
    private String estado;
    private Date fechacreada;
    private Date fechaenviada;
    private Date fecharecibida;
    private CasinoDto salaenviadora;
    private CasinoDto salareceptora;
    private UsuarioDTO creador;
    private UsuarioDTO enviador;
    private UsuarioDTO recibidor;
    private List<BarajasCantidad> detalle;

    public TrasladoDTO(Trasladobarajas traslado) {
        id = traslado.getId();
        estado = traslado.getEstado();
        fechacreada = traslado.getFechacreada();
        fechaenviada = traslado.getFechaenviada();
        fecharecibida = traslado.getFecharecibida();
        salaenviadora = new CasinoDto(traslado.getSalaenviadora());
        salareceptora = new CasinoDto(traslado.getSalareceptora());
        if (traslado.getCreador() != null) {
            creador = new UsuarioDTO(traslado.getCreador());
        }else {
            creador = new UsuarioDTO();
        }if (traslado.getEnviador() != null) {
            enviador = new UsuarioDTO(traslado.getEnviador());
        }else {
            enviador = new UsuarioDTO();
        }if (traslado.getRecibidor() != null) {
            recibidor = new UsuarioDTO(traslado.getRecibidor());
        }else {
            recibidor = new UsuarioDTO();
        }
        detalle = new ArrayList<BarajasCantidad>();
        for (Trasladobarajadetalle tbd : traslado.getTrasladobarajadetalleList()) {
            int cantCasinoEnviador = 0;
            int cantCasinoReceptor = 0;
            for (Inventarobarajas ib : traslado.getSalaenviadora().getInventarobarajasList()) {
                if (ib.getBaraja().equals(tbd.getBarajas())) {
                    cantCasinoEnviador = ib.getCantidadbarajas() - ib.getDestruidas() - ib.getPordestruir() - ib.getUso();
                }
            }
            for (Inventarobarajas ib : traslado.getSalareceptora().getInventarobarajasList()) {
                if (ib.getBaraja().equals(tbd.getBarajas())) {
                    cantCasinoReceptor = ib.getCantidadbarajas() - ib.getDestruidas() - ib.getPordestruir() - ib.getUso();
                }
            }
            detalle.add(new BarajasCantidad(tbd.getBarajas().getId(), new BarajasDTO(tbd.getBarajas()), tbd.getCantidad(), cantCasinoEnviador, cantCasinoReceptor, 0, 0, 0, 0, "", 0));
        }
    }

    public TrasladoDTO() {

        salaenviadora = new CasinoDto(0, "no casino");
        salareceptora = new CasinoDto(0, "no casino");
        creador = new UsuarioDTO();
        enviador = new UsuarioDTO();
        recibidor = new UsuarioDTO();
        detalle = new ArrayList<BarajasCantidad>();
    }

    public TrasladoDTO(Casino casinoenviador, Casino casinoreceptor) {

        salaenviadora = new CasinoDto(casinoenviador);
        salareceptora = new CasinoDto(casinoreceptor);
        creador = new UsuarioDTO();
        enviador = new UsuarioDTO();
        recibidor = new UsuarioDTO();
        System.out.println("Adding casinos");
        detalle = new ArrayList<BarajasCantidad>();
        for (Inventarobarajas tbd : casinoenviador.getInventarobarajasList()) {
            int cantCasinoEnviador = 0;
            int cantCasinoReceptor = 0;
            cantCasinoEnviador = tbd.getCantidadbarajas() - tbd.getDestruidas() - tbd.getPordestruir() - tbd.getUso();
            for (Inventarobarajas ib : casinoreceptor.getInventarobarajasList()) {
                if (ib.getBaraja().equals(tbd.getBaraja())) {
                    cantCasinoReceptor = ib.getCantidadbarajas() - ib.getDestruidas() - ib.getPordestruir() - ib.getUso();
                }
            }
            detalle.add(new BarajasCantidad(tbd.getBaraja().getId(), new BarajasDTO(tbd.getBaraja()), 0, cantCasinoEnviador, cantCasinoReceptor, 0, 0, 0, 0, "", 0));
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechacreada() {
        return fechacreada;
    }

    public void setFechacreada(Date fechacreada) {
        this.fechacreada = fechacreada;
    }

    public Date getFechaenviada() {
        return fechaenviada;
    }

    public void setFechaenviada(Date fechaenviada) {
        this.fechaenviada = fechaenviada;
    }

    public Date getFecharecibida() {
        return fecharecibida;
    }

    public void setFecharecibida(Date fecharecibida) {
        this.fecharecibida = fecharecibida;
    }

    public CasinoDto getSalaenviadora() {
        return salaenviadora;
    }

    public void setSalaenviadora(CasinoDto salaenviadora) {
        this.salaenviadora = salaenviadora;
    }

    public CasinoDto getSalareceptora() {
        return salareceptora;
    }

    public void setSalareceptora(CasinoDto salareceptora) {
        this.salareceptora = salareceptora;
    }

    public UsuarioDTO getCreador() {
        return creador;
    }

    public void setCreador(UsuarioDTO creador) {
        this.creador = creador;
    }

    public UsuarioDTO getEnviador() {
        return enviador;
    }

    public void setEnviador(UsuarioDTO enviador) {
        this.enviador = enviador;
    }

    public UsuarioDTO getRecibidor() {
        return recibidor;
    }

    public void setRecibidor(UsuarioDTO recibidor) {
        this.recibidor = recibidor;
    }

    public List<BarajasCantidad> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<BarajasCantidad> detalle) {
        this.detalle = detalle;
    }

    public Trasladobarajas getTraslado(){
        Trasladobarajas t = new Trasladobarajas();
        return t;
    }
}
