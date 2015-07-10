/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.facade.impl;

import com.invbf.sistemagestionmercadeo.dao.GestionBarajasDao;
import com.invbf.sistemagestionmercadeo.dto.BarajasCantidad;
import com.invbf.sistemagestionmercadeo.dto.BarajasDTO;
import com.invbf.sistemagestionmercadeo.dto.InventarioBarajasDTO;
import com.invbf.sistemagestionmercadeo.dto.MaterialesDTO;
import com.invbf.sistemagestionmercadeo.entity.Barajas;
import com.invbf.sistemagestionmercadeo.entity.Inventarobarajas;
import com.invbf.sistemagestionmercadeo.entity.Materialesbarajas;
import com.invbf.sistemagestionmercadeo.facade.BarajasFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ivan
 */
public class BarajasFacadeImpl implements BarajasFacade, Serializable {

    private BarajasDTO transformarBaraja(Barajas baraja) {
        return new BarajasDTO(baraja.getId(), baraja.getColor(), baraja.getMarca(), baraja.getValorpromedio(), transformarMaterial(baraja.getMaterial()));
    }

    private Barajas transformarBaraja(BarajasDTO baraja) {
        return new Barajas(baraja.getId(), baraja.getColor(), baraja.getMarca(), baraja.getValorpromedio(), transformarMaterial(baraja.getMaterial()));
    }

    private MaterialesDTO transformarMaterial(Materialesbarajas material) {
        return new MaterialesDTO(material.getId(), material.getNombre());
    }

    private Materialesbarajas transformarMaterial(MaterialesDTO material) {
        return new Materialesbarajas(material.getId(), material.getNombre());
    }

    private InventarioBarajasDTO transformarInventario(List<Inventarobarajas> listaInvenratioBarajas) {
        InventarioBarajasDTO inventario = new InventarioBarajasDTO();
        
        for (Inventarobarajas item : listaInvenratioBarajas) {
            inventario.getInventario().add(new BarajasCantidad(transformarBaraja(item.getBaraja()), item.getCantidadbarajas()));
        }
        
        return inventario;
    }
    
    @Override
    public List<BarajasDTO> getListaBarajas() {
        List<Barajas> barajas = GestionBarajasDao.getListaBArajas();
        List<BarajasDTO> barajasDTO = new ArrayList<BarajasDTO>();
        for (Barajas baraja : barajas) {
            barajasDTO.add(transformarBaraja(baraja));
        }
        return barajasDTO;
    }

    @Override
    public List<MaterialesDTO> getListaMateriales() {
        List<Materialesbarajas> materiales = GestionBarajasDao.getListaMateriales();
        List<MaterialesDTO> materialesDTOs = new ArrayList<MaterialesDTO>();
        for (Materialesbarajas material : materiales) {
            System.out.println("Nombre" + material.getNombre());
            materialesDTOs.add(transformarMaterial(material));
        }
        return materialesDTOs;
    }

    @Override
    public MaterialesDTO addMaterial(MaterialesDTO material) {
        return transformarMaterial(GestionBarajasDao.addMaterial(transformarMaterial(material)));
    }

    @Override
    public MaterialesDTO deleteMaterial(MaterialesDTO material) {
        GestionBarajasDao.deleteMaterial(transformarMaterial(material));
        return material;
    }

    @Override
    public BarajasDTO addBaraja(BarajasDTO elemento) {
        elemento = transformarBaraja(GestionBarajasDao.addBaraja(transformarBaraja(elemento)));
        GestionBarajasDao.addInventarioBaraja(transformarBaraja(elemento));
        return elemento;
    }

    @Override
    public BarajasDTO deleteBaraja(BarajasDTO elemento) {
        GestionBarajasDao.deleteBaraja(transformarBaraja(elemento));
        return elemento;
    }

    @Override
    public InventarioBarajasDTO getInventario() {
        return transformarInventario(GestionBarajasDao.getListaInvenratioBarajas());
    }


}
