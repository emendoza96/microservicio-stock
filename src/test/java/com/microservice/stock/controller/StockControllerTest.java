package com.microservice.stock.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import com.microservice.stock.dao.OrderDetailRepository;
import com.microservice.stock.domain.Material;
import com.microservice.stock.domain.OrderDetail;
import com.microservice.stock.domain.Provision;
import com.microservice.stock.domain.ProvisionDetail;

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
    private OrderDetailRepository orderDetailRepository;

    @Test
    void testGetProvisions() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/stock/provision")
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isNumber());
    }

    @Test
    void testGetProvisionsByDate() throws Exception {
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
        Provision provision = new Provision();
        provision.setProvisionDate(LocalDate.now());

        Material material = materialRepository.findById(1).orElseThrow();
        provision.getDetail().add(new ProvisionDetail(999, material));

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
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/stock/stock-movement")
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isNumber());
    }

    @Test
    void testGetStockMovementsByMaterial() throws Exception {
        Material material = materialRepository.findById(1).orElseThrow();

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/stock/stock-movement?material={material}", material.getName())
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].material.name").value(material.getName()));
    }

    @Test
    void testSaveStockMovementByOrderDetail() throws Exception {
        List<OrderDetail> orderDetails = new ArrayList<>();

        OrderDetail orderDetail = orderDetailRepository.findById(1).orElseThrow();
        orderDetails.add(orderDetail);

        String orderDetailJson = objectMapper.writeValueAsString(orderDetails);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/stock/stock-movement/order")
            .contentType(MediaType.APPLICATION_JSON)
            .content(orderDetailJson)
        )
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].date").isNotEmpty());
    }
}
