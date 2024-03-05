package com.microservice.stock.dao;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.microservice.stock.domain.StockMovement;

public interface StockMovementRepository extends JpaRepository<StockMovement, Integer> {

    @Query(value =
        """
            SELECT
                sm.*
            FROM
                stock_movement sm
            WHERE
                sm.date BETWEEN :startDate AND :endDate
        """,
        nativeQuery = true
    )
    public List<StockMovement> findStockMovementsByParams(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate);

}
