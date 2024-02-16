package com.microservice.stock.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "provision_detail")
public class ProvisionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "provision_id")
    @JsonBackReference
    private Provision provision;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    public ProvisionDetail() {}

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

    public Provision getProvision() {
        return provision;
    }

    public void setProvision(Provision provision) {
        this.provision = provision;
    }

    @Override
    public String toString() {
        return "ProvisionDetail [id=" + id + ", quantity=" + quantity + ", provision=" + provision + ", material="
                + material + "]";
    }

}
