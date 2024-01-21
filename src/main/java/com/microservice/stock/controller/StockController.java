package com.microservice.stock.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.stock.domain.OrderDetail;
import com.microservice.stock.domain.Provision;
import com.microservice.stock.domain.StockMovement;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/strock")
public class StockController {


    @GetMapping
    public List<Provision> getProvisions(
        @RequestParam(required = false) LocalDate startDate,
        @RequestParam(required = false) LocalDate endDate
    ) {
        return null;
    }

    @PostMapping("/provision")
    public Provision saveProvision(@RequestBody Provision provision) {

        System.out.println(provision);

        return provision;
    }

    @GetMapping("/stock-movement")
    public List<StockMovement> getStockMovements(
        @RequestParam(required = false) String material,
        @RequestParam(required = false) LocalDate startDate,
        @RequestParam(required = false) LocalDate endDate
    ) {
        return null;
    }

    @PostMapping("/stock-movement/order")
    public StockMovement saveStockMovementByOrderDetail(@RequestBody OrderDetail orderDetail) {

        System.out.println(orderDetail);

        return null;
    }

}
