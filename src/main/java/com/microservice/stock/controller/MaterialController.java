package com.microservice.stock.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.stock.domain.Material;
import com.microservice.stock.error.ErrorDetails;
import com.microservice.stock.error.ErrorResponse;
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
    public ResponseEntity<?> getAllMaterials() {

        try {
            List<Material> materials = materialService.getAllMaterials();
            return ResponseEntity.ok().body(materials);
        } catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails();
            errorDetails.setCode(HttpStatus.BAD_REQUEST.value());
            errorDetails.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(errorDetails));
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
    public ResponseEntity<?> getMaterial(@PathVariable Integer id) {

        try {
            Material material = materialService.getMaterialById(id).orElseThrow();
            return ResponseEntity.ok().body(material);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails();
            errorDetails.setCode(HttpStatus.BAD_REQUEST.value());
            errorDetails.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(errorDetails));
        }

    }


    @PostMapping
    @Operation(summary = "Create a new Material")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Material successfully created"),
        @ApiResponse(responseCode = "401", description = "Not authorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    public ResponseEntity<?> saveMaterial(@RequestBody Material material) {

        try {
            Material newMaterial = materialService.createMaterial(material);
            return ResponseEntity.status(201).body(newMaterial);
        } catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails();
            errorDetails.setCode(HttpStatus.BAD_REQUEST.value());
            errorDetails.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(errorDetails));
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
    public ResponseEntity<?> editMaterial(@PathVariable Integer id, @RequestBody Material material) {

        try {
            materialService.getMaterialById(id).orElseThrow();
            material.setId(id);

            Material materialResult = materialService.createMaterial(material);
            return ResponseEntity.status(200).body(materialResult);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails();
            errorDetails.setCode(HttpStatus.BAD_REQUEST.value());
            errorDetails.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(errorDetails));
        }

    }


    @Operation(summary = "Disable Material by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Material successfully disabled"),
        @ApiResponse(responseCode = "401", description = "Not authorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Material not found"),
    })
    @DeleteMapping("/disable/{id}")
    public ResponseEntity<?> disableMaterial(@PathVariable Integer id) {

        try {
            Material material = materialService.getMaterialById(id).orElseThrow();
            material = materialService.disableMaterial(material);
            return ResponseEntity.ok().body(material);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails();
            errorDetails.setCode(HttpStatus.BAD_REQUEST.value());
            errorDetails.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(errorDetails));
        }
    }

}
