package com.microservice.stock.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.stock.domain.Material;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/material")
public class MaterialController {

    @GetMapping()
    public Material getAllMaterials() {
        return null;
    }

    @GetMapping("/id/{id}")
    public Material getMaterial(@PathVariable Integer id) {
        return null;
    }

    @PostMapping
    public Material saveMaterial(@RequestBody Material material) {

        System.out.println(material);

        return material;
    }

    @PutMapping("/edit/{id}")
    public Material putMethodName(@PathVariable String id, @RequestBody Material material) {


        System.out.println(material);

        return material;
    }

    @DeleteMapping("/delete/{id}")
    public Boolean deleteMaterial(@PathVariable Integer id) {

        return true;
    }

}
