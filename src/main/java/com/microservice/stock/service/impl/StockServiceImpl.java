package com.microservice.stock.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.stock.dao.OrderDetailRepository;
import com.microservice.stock.dao.ProvisionRepository;
import com.microservice.stock.dao.StockMovementRepository;
import com.microservice.stock.domain.Material;
import com.microservice.stock.domain.OrderDetail;
import com.microservice.stock.domain.Provision;
import com.microservice.stock.domain.ProvisionDetail;
import com.microservice.stock.domain.StockMovement;
import com.microservice.stock.helpers.OrderEventHelper;
import com.microservice.stock.service.MaterialService;
import com.microservice.stock.service.StockService;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private ProvisionRepository provisionRepository;

    @Autowired
    private StockMovementRepository stockMovementRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private MaterialService materialService;

    @Override
    public List<Provision> getProvisionsByDates(LocalDate startDate, LocalDate endDate) {
        LocalDate startDate1 = startDate == null ? LocalDate.of(2000, 1, 1) : startDate;
        LocalDate endDate1 = endDate == null ? LocalDate.now() : endDate;

        return provisionRepository.findProvisionByDates(startDate1, endDate1);
    }

    @Override
    public Provision createProvision(Provision provision) {

        List<ProvisionDetail> provisionDetails = provision.getDetail();

        if (provisionDetails != null) {
            for (ProvisionDetail provisionDetail : provisionDetails) {
                provisionDetail.setProvision(provision);
            }
        }

        return provisionRepository.save(provision);
    }

    @Override
    public List<StockMovement> getAllStockMovements() {
        return stockMovementRepository.findAll();
    }

    @Override
    public Boolean validateProvision(Provision provision) {
        return provision.getDetail() != null && !provision.getDetail().isEmpty();
    }

    @Override
    public List<StockMovement> getStockMovementsByParams(String material, LocalDate startDate, LocalDate endDate) {
        LocalDate startDate1 = startDate == null ? LocalDate.of(2000, 1, 1) : startDate;
        LocalDate endDate1 = endDate == null ? LocalDate.now() : endDate;

        Instant startInstant = startDate1.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endInstant = endDate1.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant();

        return stockMovementRepository.findStockMovementsByParams(startInstant, endInstant)
            .stream()
            .filter(sm -> material == null || sm.getMaterial().getName().equals(material))
            .collect(Collectors.toList());
    }

    @Override
    public List<StockMovement> createStockMovementByOrderDetail(List<OrderDetail> orderDetails) {
        List<StockMovement> stockMovements = new ArrayList<>();

        for(OrderDetail orderDetail : orderDetails) {
            stockMovements.add(
                new StockMovement(0, orderDetail.getQuantity(), Instant.now(), orderDetail.getMaterial())
            );
        }

        return stockMovementRepository.saveAll(stockMovements);
    }

    @Override
    public List<StockMovement> createStockMovementByProvisionDetail(List<ProvisionDetail> provisionDetails) {
        List<StockMovement> stockMovements = new ArrayList<>();

        for(ProvisionDetail provisionDetail : provisionDetails) {
            StockMovement movement = new StockMovement(provisionDetail.getQuantity(), 0, Instant.now(), provisionDetail.getMaterial());
            movement.setProvisionDetail(provisionDetail);
            stockMovements.add(movement);
        }

        return stockMovementRepository.saveAll(stockMovements);
    }

    @Override
    public void createProvisionByOrderEvent(List<OrderEventHelper> orderDetails) {
        List<ProvisionDetail> provisionDetails = new ArrayList<>();
        Provision provision = new Provision();

        for(OrderEventHelper detail : orderDetails) {
            if (!materialService.checkMaterialHasStockMin(detail.getIdMaterial())){
                ProvisionDetail provisionDetail = new ProvisionDetail();
                provisionDetail.setMaterial(materialService.getMaterialById(detail.getIdMaterial()).get());
                provisionDetail.setQuantity(detail.getQuantity());
                provisionDetail.setProvision(provision);

                provisionDetails.add(provisionDetail);
            }
        }

        if (!provisionDetails.isEmpty()){
            provision.setDetail(provisionDetails);
            provision.setProvisionDate(LocalDate.now());

            provisionRepository.save(provision);

            // The stock movements will be created manually when the provision arrive at the Builder's yard.
            // createStockMovementByProvisionDetail(newProvision.getDetail());
        }
    }

    @Override
    public void updateStock(List<OrderEventHelper> orderDetails) {
        List<StockMovement> stockMovements = new ArrayList<>();

        for(OrderEventHelper detail : orderDetails) {
            OrderDetail orderDetail = orderDetailRepository.findById(detail.getIdDetail()).get();
            Material material = materialService.getMaterialById(detail.getIdMaterial()).get();

            material.setCurrentStock(material.getCurrentStock() - detail.getQuantity());

            StockMovement movement = new StockMovement(
                0,
                orderDetail.getQuantity(),
                Instant.now(),
                // Update stock and retrieve the material
                materialService.createMaterial(material)
            );

            movement.setOrderDetail(orderDetail);

            stockMovements.add(movement);
        }

        stockMovementRepository.saveAll(stockMovements);
    }


}
