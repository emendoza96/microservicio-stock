package com.microservice.stock.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.stock.domain.Material;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/material")
@ApiOperation(value = "MaterialRest")
public class MaterialController {

    @GetMapping()
    @ApiOperation(value = "Get all Materials")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "All Materials successfully retrieved"),
        @ApiResponse(code = 401, message = "Not authorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Materials not found"),
    })
    public List<Material> getAllMaterials() {
        return null;
    }

    @GetMapping("/id/{id}")
    @ApiOperation(value = "Get Material by id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Material successfully retrieved"),
        @ApiResponse(code = 401, message = "Not authorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Material not found"),
    })
    public Material getMaterial(@PathVariable Integer id) {
        return null;
    }

    @PostMapping
    @ApiOperation(value = "Create a new Material")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Material successfully created"),
        @ApiResponse(code = 401, message = "Not authorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
    })
    public Material saveMaterial(@RequestBody Material material) {

        System.out.println(material);

        return material;
    }

    @PutMapping("/edit/{id}")
    @ApiOperation(value = "Edit Material by id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Material successfully edited"),
        @ApiResponse(code = 401, message = "Not authorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Material not found"),
    })
    public Material editMaterial(@PathVariable String id, @RequestBody Material material) {


        System.out.println(material);

        return material;
    }

    @ApiOperation(value = "Delete Material by id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Material successfully deleted"),
        @ApiResponse(code = 401, message = "Not authorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Material not found"),
    })
    @DeleteMapping("/delete/{id}")
    public Boolean deleteMaterial(@PathVariable Integer id) {

        return true;
    }

}
