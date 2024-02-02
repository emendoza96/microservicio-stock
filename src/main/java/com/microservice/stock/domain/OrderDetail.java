package com.microservice.stock.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    // Only Read entity

    public OrderDetail() {}

    @Override
    public String toString() {
        return "OrderDetail [id=" + id + ", quantity=" + quantity + ", material=" + material + "]";
    }
}
