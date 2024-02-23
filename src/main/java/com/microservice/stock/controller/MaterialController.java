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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/material")
@Tag(name = "MaterialRest")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping()
    @Operation(summary = "Get all Materials")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "All Materials successfully retrieved"),
        @ApiResponse(responseCode = "401", description = "Not authorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Materials not found"),
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
    @Operation(summary = "Get Material by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Material successfully retrieved"),
        @ApiResponse(responseCode = "401", description = "Not authorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Material not found"),
    })
    public ResponseEntity<Material> getMaterial(@PathVariable Integer id) {

        try {
            Material material = materialService.getMaterialById(id).orElseThrow();
            return ResponseEntity.status(200).body(material);
        }
        catch (NoSuchElementException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(404).build();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

    }


    @PostMapping
    @Operation(summary = "Create a new Material")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Material successfully created"),
        @ApiResponse(responseCode = "401", description = "Not authorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
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
    @Operation(summary = "Edit Material by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Material successfully edited"),
        @ApiResponse(responseCode = "401", description = "Not authorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Material not found"),
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
            return ResponseEntity.status(404).build();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

    }


    @Operation(summary = "Delete Material by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Material successfully deleted"),
        @ApiResponse(responseCode = "401", description = "Not authorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Material not found"),
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
            return ResponseEntity.status(404).build();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
