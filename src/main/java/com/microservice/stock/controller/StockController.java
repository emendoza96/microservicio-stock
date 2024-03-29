package com.microservice.stock.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.stock.domain.OrderDetail;
import com.microservice.stock.domain.Provision;
import com.microservice.stock.domain.StockMovement;
import com.microservice.stock.service.StockService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/stock")
@Tag(name = "StockRest")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/provision")
    @Operation(summary = "Get Provisions")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Provision successfully retrieved"),
        @ApiResponse(responseCode = "401", description = "Not authorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Provisions not found")
    })
    public ResponseEntity<List<Provision>> getProvisions(
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ) {

        try {
            List<Provision> provisions = stockService.getProvisionsByDates(startDate, endDate);
            return ResponseEntity.status(200).body(provisions);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/provision")
    @Operation(summary = "Create a new Provision")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "New Provision successfully created"),
        @ApiResponse(responseCode = "401", description = "Not authorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    public ResponseEntity<Provision> saveProvision(@RequestBody Provision provision) {

        try {
            Provision newProvision = stockService.createProvision(provision);
            return ResponseEntity.status(201).body(newProvision);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/stock-movement")
    @Operation(summary = "Get Stock Movements")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock Movement successfully retrieved"),
        @ApiResponse(responseCode = "401", description = "Not authorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Stock Movements not found")
    })
    public ResponseEntity<List<StockMovement>> getStockMovements(
        @RequestParam(required = false) String material,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ) {
        try {
            List<StockMovement> stockMovements = stockService.getStockMovementsByParams(material, startDate, endDate);
            return ResponseEntity.status(200).body(stockMovements);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/stock-movement/order")
    @Operation(summary = "Create a new Stock Movement")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "New Stock Movement successfully created"),
        @ApiResponse(responseCode = "401", description = "Not authorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    public ResponseEntity<List<StockMovement>> saveStockMovementByOrderDetail(@RequestBody List<OrderDetail> orderDetails) {

        try {
            List<StockMovement> stockMovements = stockService.createStockMovementByOrderDetail(orderDetails);
            return ResponseEntity.status(201).body(stockMovements);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

    }

}
