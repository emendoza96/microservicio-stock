package com.microservice.stock.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "provision")
public class Provision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate provisionDate;

    @OneToMany(mappedBy = "provision", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ProvisionDetail> detail = new ArrayList<>();

    public Provision() {}

    public Provision(LocalDate provisionDate,  List<ProvisionDetail> detail) {
        this.provisionDate = provisionDate;
        this.detail = detail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getProvisionDate() {
        return provisionDate;
    }

    public void setProvisionDate(LocalDate provisionDate) {
        this.provisionDate = provisionDate;
    }

    public List<ProvisionDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<ProvisionDetail> detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Provision [id=" + id + ", provisionDate=" + provisionDate + ", detail=" + detail + "]";
    }

}
