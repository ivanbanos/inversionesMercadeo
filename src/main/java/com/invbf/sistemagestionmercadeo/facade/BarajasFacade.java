/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.facade;

import com.invbf.sistemagestionmercadeo.dto.BarajasDTO;
import com.invbf.sistemagestionmercadeo.dto.InventarioBarajasDTO;
import com.invbf.sistemagestionmercadeo.dto.MaterialesDTO;
import com.invbf.sistemagestionmercadeo.entity.Barajas;
import java.util.List;

/**
 *
 * @author ivan
 */
public interface BarajasFacade {

    public List<BarajasDTO> getListaBarajas();

    public List<MaterialesDTO> getListaMateriales();

    public MaterialesDTO addMaterial(MaterialesDTO material);

    public MaterialesDTO deleteMaterial(MaterialesDTO material);

    public BarajasDTO addBaraja(BarajasDTO elemento);

    public BarajasDTO deleteBaraja(BarajasDTO elemento);

    public InventarioBarajasDTO getInventario();

    
}
