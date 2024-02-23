package com.microservice.stock.domain;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
