/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.facade.impl;

import com.invbf.sistemagestionmercadeo.dao.GestionRegaloDao;
import com.invbf.sistemagestionmercadeo.dto.CategoriaDTO;
import com.invbf.sistemagestionmercadeo.dto.RegaloDTO;
import com.invbf.sistemagestionmercadeo.entity.Categorias;
import com.invbf.sistemagestionmercadeo.entity.Regalos;
import com.invbf.sistemagestionmercadeo.facade.RegalosFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ivan
 */
public class RegalosFacadeImpl implements RegalosFacade, Serializable {

    @Override
    public List<CategoriaDTO> getListaCategorias() {
        List<Categorias> categorias = GestionRegaloDao.getListaCategorias();
        List<CategoriaDTO> categoriasDto = new ArrayList<CategoriaDTO>();
        for (Categorias categoria : categorias) {
            categoriasDto.add(new CategoriaDTO(categoria.getIdCategorias(), categoria.getNombre()));
        }
        return categoriasDto;
    }

    @Override
    public List<RegaloDTO> getListaRegalos() {
        List<Regalos> regalos = GestionRegaloDao.getListaRegalos();
        List<RegaloDTO> regaloDTOs = new ArrayList<RegaloDTO>();
        for (Regalos regalo : regalos) {
            regaloDTOs.add(new RegaloDTO(regalo));
        }
        return regaloDTOs;
    }

    @Override
    public RegaloDTO deleteRegalo(RegaloDTO elemento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RegaloDTO addRegalo(RegaloDTO elemento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}