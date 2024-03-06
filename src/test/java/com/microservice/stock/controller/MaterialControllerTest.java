package com.microservice.stock.controller;


import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.stock.dao.MaterialRepository;
import com.microservice.stock.domain.Material;
import com.microservice.stock.domain.Unit;
import com.microservice.stock.security.filters.MockJwtAuthorizationFilter;

@SpringBootTest
@AutoConfigureMockMvc
public class MaterialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private MaterialController materialController;

    private Material material1;
    private Material material2;

    @BeforeEach
    void setUp(){
        material1 = Material.builder()
            .name("Brick")
            .description("Test description")
            .currentStock(100)
            .price(5.5d)
            .unit(new Unit())
            .build()
        ;

        material2 = Material.builder()
            .name("Brick 2")
            .description("Test description 2")
            .currentStock(800)
            .price(8.5d)
            .unit(new Unit())
            .build()
        ;

        mockMvc = MockMvcBuilders.standaloneSetup(materialController)
            .addFilters(new MockJwtAuthorizationFilter())
            .setMessageConverters(new MappingJackson2HttpMessageConverter())
            .build()
        ;
    }

    @Test
    void testGetAllMaterials() throws Exception {
        materialRepository.save(material1);
        materialRepository.save(material2);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/material")
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").isString());
    }

    @Test
    void testGetMaterial() throws Exception {
        Integer material_id = 1;
        materialRepository.save(material1);
        materialRepository.save(material2);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/material/{id}", material_id)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(material_id));
    }

    @Test
    void testSaveMaterial() throws Exception {
        String materialJson = objectMapper.writeValueAsString(material1);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/material")
            .contentType(MediaType.APPLICATION_JSON)
            .content(materialJson)
        )
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(material1.getName()));
    }

    @Test
    void testEditMaterial() throws Exception {
        Material materialSaved = materialRepository.save(material1);
        Integer material_id = materialSaved.getId();

        // Change current stock
        materialSaved.setCurrentStock(200);

        String materialJson = objectMapper.writeValueAsString(materialSaved);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/material/edit/{id}", material_id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(materialJson)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(materialSaved.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.currentStock").value(materialSaved.getCurrentStock()));

    }

    @Test
    void testDeleteMaterial() throws Exception {
        Integer material_id = 2;
        materialRepository.save(material1);
        materialRepository.save(material2);

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/material/disable/{id}", material_id)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.enabled").value(false));
    }
}
