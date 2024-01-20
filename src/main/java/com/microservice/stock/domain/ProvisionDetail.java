package com.microservice.stock.domain;

public class ProvisionDetail {

    private Integer id;
    private Integer quantity;
    private Material material;

    public ProvisionDetail(Integer quantity, Material material) {
        this.quantity = quantity;
        this.material = material;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "ProvisionDetail [id=" + id + ", quantity=" + quantity + ", material=" + material + "]";
    }

}
