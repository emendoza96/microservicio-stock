package com.microservice.stock.service;

import java.util.List;
import java.util.Optional;

import com.microservice.stock.domain.Material;

public interface MaterialService {

    public Material createMaterial(Material material);
    public List<Material> getAllMaterials();
    public Optional<Material> getMaterialById(Integer id);
    public void deleteMaterialById(Integer id);

}
