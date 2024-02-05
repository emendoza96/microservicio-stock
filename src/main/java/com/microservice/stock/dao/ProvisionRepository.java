package com.microservice.stock.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.microservice.stock.domain.Provision;

public interface ProvisionRepository extends JpaRepository<Provision, Integer> {

    @Query(value =
        """
            SELECT
                *
            FROM
                provision p
            WHERE
                p.provision_date BETWEEN :startDate AND :endDate
        """,
        nativeQuery = true
    )
    public List<Provision> findProvisionByDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
