/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.facade;

import com.invbf.sistemagestionmercadeo.dto.CategoriaDTO;
import com.invbf.sistemagestionmercadeo.dto.RegaloDTO;
import java.util.List;

/**
 *
 * @author ivan
 */
public interface RegalosFacade {

    public List<CategoriaDTO> getListaCategorias();

    public List<RegaloDTO> getListaRegalos();

    public RegaloDTO deleteRegalo(RegaloDTO elemento);

    public RegaloDTO addRegalo(RegaloDTO elemento);

}
