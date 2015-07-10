/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.dto;

import java.util.Date;
import java.util.List;

/**
 *
 * @author ivan
 */
public class ControlsalidabonoDTO {
    private Integer id;
    private Date fecha;
    private String estado;
    private Date fechavencimientobonos;
    private List<BonoDTO> bonoList;
    private List<ControlsalidabonosHasLotesbonosDTO> controlsalidabonosHasLotesbonosList;
    private UsuarioDTO solicitante;
}
