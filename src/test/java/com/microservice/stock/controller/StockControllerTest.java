package com.microservice.stock.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.microservice.stock.domain.Material;
import com.microservice.stock.domain.OrderDetail;
import com.microservice.stock.domain.Provision;
import com.microservice.stock.domain.ProvisionDetail;
import com.microservice.stock.domain.StockMovement;
import com.microservice.stock.domain.Unit;
import com.microservice.stock.security.filters.MockJwtAuthorizationFilter;
import com.microservice.stock.service.StockService;

@ExtendWith(MockitoExtension.class)
public class StockControllerTest {

    @Mock
    private StockService stockService;

    @InjectMocks
    private StockController stockController;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private Material material1;
    private Material material2;
    private Provision provision1;
    private Provision provision2;
    private ProvisionDetail detail1;
    private ProvisionDetail detail2;
    private ProvisionDetail detail3;

    @BeforeEach
    void setUp(){

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

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

        // Provision 1

        provision1 = Provision.builder()
            .provisionDate(LocalDate.now())
            .build()
        ;

        detail1 = ProvisionDetail.builder()
            .quantity(100)
            .provision(provision1)
            .build()
        ;

        detail2 = ProvisionDetail.builder()
            .quantity(200)
            .provision(provision1)
            .build()
        ;

        provision1.setDetail(List.of(detail1, detail2));

        // Provision 2

        provision2 = Provision.builder()
            .provisionDate(LocalDate.now())
            .build()
        ;

        detail3 = ProvisionDetail.builder()
            .quantity(300)
            .provision(provision2)
            .build()
        ;

        provision2.setDetail(List.of(detail3));

        // Authorization

        mockMvc = MockMvcBuilders.standaloneSetup(stockController)
            .addFilters(new MockJwtAuthorizationFilter())
            .setMessageConverters(new MappingJackson2HttpMessageConverter())
            .build()
        ;
    }

    @Test
    void testGetProvisions() throws Exception {

        when(stockService.getProvisionsByDates(any(), any())).thenReturn(List.of(provision1, provision2));

        ResultActions response = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/stock/provision")
                .contentType(MediaType.APPLICATION_JSON)
            )
        ;

        response.andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
        ;
    }

    @Test
    void testGetStockMovements() throws Exception {
        when(stockService.getStockMovementsByParams(any(), any(), any())).thenReturn(List.of(StockMovement.builder()
            .date(Instant.now())
            .inputQuantity(300)
            .provisionDetail(detail3)
            .material(material1)
            .build()
        ));

        ResultActions response = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/stock/stock-movement")
            .contentType(MediaType.APPLICATION_JSON)
        );

        response.andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
        ;

    }

    @Test
    void testSaveProvision() throws Exception {
        when(stockService.createProvision(any())).thenReturn(provision1);

        String jsonResult = objectMapper.writeValueAsString(provision1);

        ResultActions response = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/stock/provision")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonResult)
        );

        response.andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.provisionDate").exists())
        ;
    }

    @Test
    void testSaveStockMovementByOrderDetail() throws Exception {
        when(stockService.createStockMovementByOrderDetail(any())).thenReturn(List.of(StockMovement.builder()
            .date(Instant.now())
            .inputQuantity(300)
            .provisionDetail(detail3)
            .material(material2)
            .build()
        ));

        String jsonResult = objectMapper.writeValueAsString(List.of(new OrderDetail()));

        ResultActions response = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/stock/stock-movement/order")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonResult)
        );

        response.andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
        ;
    }
}

