package com.microservice.stock.domain;

import java.time.LocalDate;
import java.util.List;

public class Provision {

    private Integer id;
    private LocalDate provisionDate;

    private List<ProvisionDetail> detail;

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
