package com.microservice.stock.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.stock.domain.Provision;

public interface ProvisionRepository extends JpaRepository<Provision, Integer> {

}
