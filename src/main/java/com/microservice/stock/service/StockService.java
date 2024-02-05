package com.microservice.stock.service;

import java.util.List;

import com.microservice.stock.domain.Provision;

public interface StockService {

    public List<Provision> getProvisionsByDates();

}
