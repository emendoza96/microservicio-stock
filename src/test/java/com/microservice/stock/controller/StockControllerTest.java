package com.microservice.stock.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.stock.dao.MaterialRepository;
import com.microservice.stock.dao.ProvisionRepository;
import com.microservice.stock.dao.StockMovementRepository;
import com.microservice.stock.domain.Material;
import com.microservice.stock.domain.Provision;
import com.microservice.stock.domain.ProvisionDetail;
import com.microservice.stock.domain.StockMovement;
import com.microservice.stock.domain.Unit;

@SpringBootTest
@AutoConfigureMockMvc
public class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private ProvisionRepository provisionRepository;

    @Autowired
    private StockMovementRepository stockMovementRepository;

    private Material material1;
    private Material material2;
    private Provision provision1;
    private Provision provision2;
    private ProvisionDetail detail1;
    private ProvisionDetail detail2;
    private ProvisionDetail detail3;

    @BeforeEach
    void setUp(){
        material1 = Material.builder()
            .name("Brick")
            .description("Test description")
            .currentStock(100)
            .price(5.5d)
            .unit(new Unit())
            .build();

        material2 = Material.builder()
            .name("Brick 2")
            .description("Test description 2")
            .currentStock(800)
            .price(8.5d)
            .unit(new Unit())
            .build();

        // Provision 1

        provision1 = Provision.builder()
            .provisionDate(LocalDate.now())
            .build();

        detail1 = ProvisionDetail.builder()
            .quantity(100)
            .provision(provision1)
            .build();

        detail2 = ProvisionDetail.builder()
            .quantity(200)
            .provision(provision1)
            .build();

        // Provision 2

        provision2 = Provision.builder()
            .provisionDate(LocalDate.now())
            .build();

        detail3 = ProvisionDetail.builder()
            .quantity(300)
            .provision(provision2)
            .build();
    }

    @Test
    void testGetProvisions() throws Exception {
        Material materialResult1 = materialRepository.save(material1);
        Material materialResult2 = materialRepository.save(material2);
        detail1.setMaterial(materialResult1);
        detail2.setMaterial(materialResult2);
        detail3.setMaterial(materialResult2);
        provision1.setDetail(List.of(detail1, detail2));
        provision2.setDetail(List.of(detail3));
        provisionRepository.save(provision1);
        provisionRepository.save(provision2);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/stock/provision")
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isNumber());
    }

    @Test
    void testGetProvisionsByDate() throws Exception {
        Material materialResult1 = materialRepository.save(material1);
        Material materialResult2 = materialRepository.save(material2);
        detail1.setMaterial(materialResult1);
        detail2.setMaterial(materialResult2);
        detail3.setMaterial(materialResult2);
        provision1.setDetail(List.of(detail1, detail2));
        provision2.setDetail(List.of(detail3));
        provisionRepository.save(provision1);
        provisionRepository.save(provision2);

        LocalDate startDate = LocalDate.of(2023, 12, 2);
        LocalDate endDate = LocalDate.of(2025, 12, 2);

        mockMvc.perform(
            MockMvcRequestBuilders.get(
                "/api/stock/provision?startDate={startDate}&endDate={endDate}",
                startDate,
                endDate
            )
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].provisionDate").isNotEmpty());
    }

    @Test
    void testSaveProvision() throws Exception {
        Provision provision = Provision.builder()
            .provisionDate(LocalDate.now())
            .build();

        Material materialResult = materialRepository.save(material1);

        ProvisionDetail detail = ProvisionDetail.builder()
            .quantity(340)
            .provision(provision)
            .material(materialResult)
            .build();

        provision.setDetail(List.of(detail));

        String provisionJson = objectMapper.writeValueAsString(provision);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/stock/provision")
            .contentType(MediaType.APPLICATION_JSON)
            .content(provisionJson)
        )
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath("$.detail.[0].id").isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath("$.detail.[0].quantity").value(provision.getDetail().get(0).getQuantity()));
    }

    @Test
    void testGetStockMovements() throws Exception {
        Material materialResult1 = materialRepository.save(material1);
        Material materialResult2 = materialRepository.save(material2);
        detail1.setMaterial(materialResult1);
        detail2.setMaterial(materialResult2);
        detail3.setMaterial(materialResult2);
        provision1.setDetail(List.of(detail1, detail2));
        Provision provisionResult = provisionRepository.save(provision1);

        StockMovement movement = StockMovement.builder()
            .date(Instant.now())
            .inputQuantity(300)
            .provisionDetail(provisionResult.getDetail().get(0))
            .material(provisionResult.getDetail().get(0).getMaterial())
            .build()
        ;

        stockMovementRepository.save(movement);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/stock/stock-movement")
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isNumber());
    }

    @Test
    void testGetStockMovementsByMaterial() throws Exception {
        Material materialResult1 = materialRepository.save(material1);
        Material materialResult2 = materialRepository.save(material2);
        detail1.setMaterial(materialResult1);
        detail2.setMaterial(materialResult2);
        detail3.setMaterial(materialResult2);
        provision1.setDetail(List.of(detail1, detail2));
        Provision provisionResult = provisionRepository.save(provision1);

        StockMovement movement = StockMovement.builder()
            .date(Instant.now())
            .inputQuantity(300)
            .provisionDetail(provisionResult.getDetail().get(0))
            .material(provisionResult.getDetail().get(0).getMaterial())
            .build()
        ;

        stockMovementRepository.save(movement);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/stock/stock-movement?material={material}", materialResult1.getName())
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].material.name").value(materialResult1.getName()));
    }

}
