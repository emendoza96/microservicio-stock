package com.microservice.stock.domain;

public class Unit {

    private Integer id;
    private String description;

    public Unit(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Unit [id=" + id + ", description=" + description + "]";
    }


}
