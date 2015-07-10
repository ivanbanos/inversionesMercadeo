/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dto;

import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Controlsalidabono;
import com.invbf.sistemagestionmercadeo.entity.Propositoentrega;
import com.invbf.sistemagestionmercadeo.entity.Solicitudentregacliente;
import com.invbf.sistemagestionmercadeo.entity.Tipobono;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ivan
 */
public class SolicitudEntregaDTO implements Serializable{
    private Date fecha;
    private String justificacion;
    private String estado;
    private Integer formareparticrbonos;
    private Float total;
    private Float totalpreaprobado;
    private Float totalaprobado;
    private List<SolicitudClietnesDTO> solicitudentregaclienteList;
    private Usuario aprobador;
    private Usuario solicitante;
    private Tipobono tipoBono;
    private Propositoentrega propositoEntrega;
    private Casino idCasino;
    private Controlsalidabono controlsalidabono;
    private Date fechavencimientobonos;
}
