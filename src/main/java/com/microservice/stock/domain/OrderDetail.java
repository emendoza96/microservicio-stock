package com.microservice.stock.domain;

public class OrderDetail {

    private Integer id;
    private Integer quantity;

    private Material material;

    // Only Read entity

    @Override
    public String toString() {
        return "OrderDetail [id=" + id + ", quantity=" + quantity + ", material=" + material + "]";
    }
}
