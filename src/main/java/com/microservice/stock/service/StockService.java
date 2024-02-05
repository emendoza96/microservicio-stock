package com.microservice.stock.service;

import java.time.LocalDate;
import java.util.List;

import com.microservice.stock.domain.OrderDetail;
import com.microservice.stock.domain.Provision;
import com.microservice.stock.domain.ProvisionDetail;
import com.microservice.stock.domain.StockMovement;

public interface StockService {

    public List<Provision> getProvisionsByDates(LocalDate startDate, LocalDate endDate);
    public Provision createProvision(Provision provision);
    public List<StockMovement> getAllStockMovements();
    public List<StockMovement> getStockMovementsByParams(String material, LocalDate startDate, LocalDate endDate);
    public List<StockMovement> createStockMovementByOrderDetail(List<OrderDetail> orderDetailS);
    public List<StockMovement> createStockMovementByProvisionDetail(List<ProvisionDetail> provisionDetails);
    public Boolean validateProvision(Provision provision);

}
