package com.microservice.stock.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.stock.domain.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

}
