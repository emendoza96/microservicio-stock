package com.microservice.stock.domain;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "stock_movement")
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer inputQuantity;
    private Integer outputQuantity;
    private Instant date;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    @OneToOne
    @JoinColumn(name = "provision_detail_id")
    private ProvisionDetail provisionDetail;

    @OneToOne
    @JoinColumn(name = "order_detail_id")
    private OrderDetail orderDetail;

    public StockMovement() {}

    public StockMovement(Integer inputQuantity, Integer outputQuantity, Instant date, Material material) {
        this.inputQuantity = inputQuantity;
        this.outputQuantity = outputQuantity;
        this.date = date;
        this.material = material;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInputQuantity() {
        return inputQuantity;
    }

    public void setInputQuantity(Integer inputQuantity) {
        this.inputQuantity = inputQuantity;
    }

    public Integer getOutputQuantity() {
        return outputQuantity;
    }

    public void setOutputQuantity(Integer outputQuantity) {
        this.outputQuantity = outputQuantity;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public ProvisionDetail getProvisionDetail() {
        return provisionDetail;
    }

    public void setProvisionDetail(ProvisionDetail provisionDetail) {
        this.provisionDetail = provisionDetail;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    @Override
    public String toString() {
        return "StockMovement [id=" + id + ", inputQuantity=" + inputQuantity + ", outputQuantity=" + outputQuantity
                + ", date=" + date + ", material=" + material + "]";
    }

}
