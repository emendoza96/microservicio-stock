package com.microservice.stock.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.stock.dao.MaterialRepository;
import com.microservice.stock.domain.Material;
import com.microservice.stock.service.MaterialService;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public Material createMaterial(Material material) {
        return materialRepository.save(material);
    }

    @Override
    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }

    @Override
    public Optional<Material> getMaterialById(Integer id) {
        return materialRepository.findById(id);
    }

    @Override
    public Material disableMaterial(Material material) {
        material.setEnabled(false);
        return materialRepository.save(material);
    }

}
