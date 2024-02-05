package com.microservice.stock.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.stock.dao.ProvisionRepository;
import com.microservice.stock.domain.OrderDetail;
import com.microservice.stock.domain.Provision;
import com.microservice.stock.domain.ProvisionDetail;
import com.microservice.stock.domain.StockMovement;

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
    private ProvisionRepository provisionRepository;

    @GetMapping("/provision")
    @ApiOperation(value = "Get Provisions")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Provision successfully retrieved"),
        @ApiResponse(code = 401, message = "Not authorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Provisions not found")
    })
    public List<Provision> getProvisions(
        @RequestParam(required = false) LocalDate startDate,
        @RequestParam(required = false) LocalDate endDate
    ) {
        return provisionRepository.findAll();
    }

    @PostMapping("/provision")
    @ApiOperation(value = "Create a new Provision")
    @ApiResponses( value = {
        @ApiResponse(code = 200, message = "New Provision successfully created"),
        @ApiResponse(code = 401, message = "Not authorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
    })
    public Provision saveProvision(@RequestBody Provision provision) {

        System.err.println(provision);

        List<ProvisionDetail> provisionDetails = provision.getDetail();

        if (provisionDetails != null) {
            for (ProvisionDetail provisionDetail : provisionDetails) {
                provisionDetail.setProvision(provision);
            }
        }

        Provision provision2 = provisionRepository.save(provision);

        return provision2;
    }

    @GetMapping("/stock-movement")
    @ApiOperation(value = "Get Stock Movements")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Stock Movement successfully retrieved"),
        @ApiResponse(code = 401, message = "Not authorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Stock Movements not found")
    })
    public List<StockMovement> getStockMovements(
        @RequestParam(required = false) String material,
        @RequestParam(required = false) LocalDate startDate,
        @RequestParam(required = false) LocalDate endDate
    ) {
        return null;
    }

    @PostMapping("/stock-movement/order")
    @ApiOperation(value = "Create a new Stock Movement")
    @ApiResponses( value = {
        @ApiResponse(code = 200, message = "New Stock Movement successfully created"),
        @ApiResponse(code = 401, message = "Not authorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
    })
    public StockMovement saveStockMovementByOrderDetail(@RequestBody OrderDetail orderDetail) {

        System.out.println(orderDetail);

        return null;
    }

}
