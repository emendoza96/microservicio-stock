package com.microservice.stock.service;

import java.time.LocalDate;
import java.util.List;

import com.microservice.stock.domain.OrderDetail;
import com.microservice.stock.domain.Provision;
import com.microservice.stock.domain.ProvisionDetail;
import com.microservice.stock.domain.StockMovement;
import com.microservice.stock.dto.OrderDetailsDTO;
import com.microservice.stock.helpers.StockAvailability;

public interface StockService {

    public List<Provision> getProvisionsByDates(LocalDate startDate, LocalDate endDate);
    public Provision createProvision(Provision provision);
    public List<StockMovement> getAllStockMovements();
    public List<StockMovement> getStockMovementsByParams(String material, LocalDate startDate, LocalDate endDate);
    public List<StockMovement> createStockMovementByOrderDetail(List<OrderDetail> orderDetails);
    public List<StockMovement> createStockMovementByProvisionDetail(List<ProvisionDetail> provisionDetails);
    public Boolean validateProvision(Provision provision);
    public void createProvisionByOrderEvent(List<OrderDetailsDTO> orderDetails);
    public void updateStock(List<OrderDetailsDTO> orderDetails);
    public Boolean checkMaterialStock(Integer idMaterial, Integer quantity);
    public StockAvailability checkMaterialStocks(List<OrderDetailsDTO> orderDetails);

}
