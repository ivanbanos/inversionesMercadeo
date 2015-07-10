/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dto;

import com.invbf.sistemagestionmercadeo.entity.Casino;
import com.invbf.sistemagestionmercadeo.entity.Cliente;
import com.invbf.sistemagestionmercadeo.entity.Controlsalidabono;
import com.invbf.sistemagestionmercadeo.entity.Denominacion;
import com.invbf.sistemagestionmercadeo.entity.Propositoentrega;
import com.invbf.sistemagestionmercadeo.entity.Tipobono;
import com.invbf.sistemagestionmercadeo.entity.Usuario;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ivan
 */
class BonoDTO {
    private Integer id;
    private String consecutivo;
    private String estado;
    private Date fechaValidacion;
    private Date fechaExpiracion;
    private Date fechaEntrega;
    private Usuario autorizador;
    private Usuario validador;
    private Tipobono tipo;
    private Propositoentrega propositosEntregaid;
    private Denominacion denominacion;
    private Controlsalidabono controlSalidaBonosid;
    private Cliente cliente;
    private Casino casino;
    private String nombreCliente;
    private String razonAnulamiento;
    private String causadenocanje;
}
