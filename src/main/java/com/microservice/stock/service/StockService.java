package com.microservice.stock.service;

import java.time.LocalDate;
import java.util.List;

import com.microservice.stock.domain.OrderDetail;
import com.microservice.stock.domain.Provision;
import com.microservice.stock.domain.ProvisionDetail;
import com.microservice.stock.domain.StockMovement;
import com.microservice.stock.helpers.OrderEventHelper;

public interface StockService {

    public List<Provision> getProvisionsByDates(LocalDate startDate, LocalDate endDate);
    public Provision createProvision(Provision provision);
    public List<StockMovement> getAllStockMovements();
    public List<StockMovement> getStockMovementsByParams(String material, LocalDate startDate, LocalDate endDate);
    public List<StockMovement> createStockMovementByOrderDetail(List<OrderDetail> orderDetails);
    public List<StockMovement> createStockMovementByProvisionDetail(List<ProvisionDetail> provisionDetails);
    public Boolean validateProvision(Provision provision);
    public void createProvisionByOrderEvent(List<OrderEventHelper> orderDetails);
    public void updateStock(List<OrderEventHelper> orderDetails);

}
