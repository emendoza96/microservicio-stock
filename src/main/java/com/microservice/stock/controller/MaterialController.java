package com.microservice.stock.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.stock.domain.Material;
import com.microservice.stock.service.MaterialService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/material")
@ApiOperation(value = "MaterialRest")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping()
    @ApiOperation(value = "Get all Materials")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "All Materials successfully retrieved"),
        @ApiResponse(code = 401, message = "Not authorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Materials not found"),
    })
    public ResponseEntity<List<Material>> getAllMaterials() {

        try {
            List<Material> materials = materialService.getAllMaterials();
            return ResponseEntity.status(200).body(materials);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "Get Material by id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Material successfully retrieved"),
        @ApiResponse(code = 401, message = "Not authorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Material not found"),
    })
    public ResponseEntity<Material> getMaterial(@PathVariable Integer id) {

        try {
            Material material = materialService.getMaterialById(id).orElseThrow();
            return ResponseEntity.status(200).body(material);
        }
        catch (NoSuchElementException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(204).build();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

    }


    @PostMapping
    @ApiOperation(value = "Create a new Material")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Material successfully created"),
        @ApiResponse(code = 401, message = "Not authorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
    })
    public ResponseEntity<Material> saveMaterial(@RequestBody Material material) {

        try {
            Material newMaterial = materialService.createMaterial(material);
            return ResponseEntity.status(201).body(newMaterial);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping("/edit/{id}")
    @ApiOperation(value = "Edit Material by id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Material successfully edited"),
        @ApiResponse(code = 401, message = "Not authorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Material not found"),
    })
    public ResponseEntity<Material> editMaterial(@PathVariable Integer id, @RequestBody Material material) {

        try {
            materialService.getMaterialById(id).orElseThrow();
            material.setId(id);

            Material materialResult = materialService.createMaterial(material);
            return ResponseEntity.status(200).body(materialResult);
        }
        catch (NoSuchElementException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(204).build();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

    }


    @ApiOperation(value = "Delete Material by id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Material successfully deleted"),
        @ApiResponse(code = 401, message = "Not authorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Material not found"),
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Material> deleteMaterial(@PathVariable Integer id) {

        try {
            Material material = materialService.getMaterialById(id).orElseThrow();
            material = materialService.disableMaterial(material);
            return ResponseEntity.status(200).body(material);
        }
        catch (NoSuchElementException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(204).build();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
