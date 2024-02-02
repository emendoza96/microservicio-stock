package com.microservice.stock.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.stock.domain.StockMovement;

public interface StockMovementRepository extends JpaRepository<StockMovement, Integer> {

}
