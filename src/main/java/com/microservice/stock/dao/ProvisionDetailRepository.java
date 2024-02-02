package com.microservice.stock.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.stock.domain.ProvisionDetail;

public interface ProvisionDetailRepository extends JpaRepository<ProvisionDetail, Integer> {

}
