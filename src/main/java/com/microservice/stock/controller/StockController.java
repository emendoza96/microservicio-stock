package com.microservice.stock.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.stock.domain.OrderDetail;
import com.microservice.stock.domain.Provision;
import com.microservice.stock.domain.StockMovement;
import com.microservice.stock.service.StockService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/stock")
@ApiOperation(value = "StockRest")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/provision")
    @ApiOperation(value = "Get Provisions")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Provision successfully retrieved"),
        @ApiResponse(code = 401, message = "Not authorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Provisions not found")
    })
    public ResponseEntity<List<Provision>> getProvisions(
        @RequestParam(required = false) LocalDate startDate,
        @RequestParam(required = false) LocalDate endDate
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
    @ApiOperation(value = "Create a new Provision")
    @ApiResponses( value = {
        @ApiResponse(code = 200, message = "New Provision successfully created"),
        @ApiResponse(code = 401, message = "Not authorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
    })
    public ResponseEntity<Provision> saveProvision(@RequestBody Provision provision) {

        try {
            Provision newProvision = stockService.createProvision(provision);
            stockService.createStockMovementByProvisionDetail(provision.getDetail());

            return ResponseEntity.status(201).body(newProvision);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/stock-movement")
    @ApiOperation(value = "Get Stock Movements")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Stock Movement successfully retrieved"),
        @ApiResponse(code = 401, message = "Not authorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Stock Movements not found")
    })
    public ResponseEntity<List<StockMovement>> getStockMovements(
        @RequestParam(required = false) String material,
        @RequestParam(required = false) LocalDate startDate,
        @RequestParam(required = false) LocalDate endDate
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
    @ApiOperation(value = "Create a new Stock Movement")
    @ApiResponses( value = {
        @ApiResponse(code = 200, message = "New Stock Movement successfully created"),
        @ApiResponse(code = 401, message = "Not authorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
    })
    public ResponseEntity<List<StockMovement>> saveStockMovementByOrderDetail(@RequestBody List<OrderDetail> orderDetails) {

        try {
            List<StockMovement> stockMovements = stockService.createStockMovementByOrderDetail(orderDetails);
            return ResponseEntity.status(200).body(stockMovements);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

    }

}
