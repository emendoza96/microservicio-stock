package com.microservice.stock.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.stock.domain.Material;

public interface MaterialRepository extends JpaRepository<Material, Integer> {

}
