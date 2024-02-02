package com.microservice.stock.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.stock.domain.Unit;

public interface UnitRepository extends JpaRepository<Unit, Integer> {

}
