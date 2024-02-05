package com.microservice.stock.controller;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.stock.dao.MaterialRepository;
import com.microservice.stock.domain.Material;
import com.microservice.stock.domain.Unit;

@SpringBootTest
@AutoConfigureMockMvc
public class MaterialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MaterialRepository materialRepository;

    @Test
    void testGetAllMaterials() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/material")
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").isString());
    }

    @Test
    void testGetMaterial() throws Exception {
        Integer material_id = 1;

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/material/{id}", material_id)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(material_id));
    }

    @Test
    void testSaveMaterial() throws Exception {
        Material material = new Material("test", "test description", 60.5d, 100, 20, new Unit());

        String materialJson = objectMapper.writeValueAsString(material);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/material")
            .contentType(MediaType.APPLICATION_JSON)
            .content(materialJson)
        )
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(material.getName()));
    }

    @Test
    void testEditMaterial() throws Exception {
        Integer material_id = 1;
        Material material = materialRepository.findById(material_id).orElseThrow();
        material.setCurrentStock(200);

        String materialJson = objectMapper.writeValueAsString(material);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/material/edit/{id}", material_id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(materialJson)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(material.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.currentStock").value(material.getCurrentStock()));

    }

    @Test
    void testDeleteMaterial() throws Exception {
        Integer material_id = 2;
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/material/delete/{id}", material_id)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
