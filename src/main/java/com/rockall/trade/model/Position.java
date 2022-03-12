package com.rockall.trade.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "quantity"})
public class Position {

    private String id;
    private Integer quantity;

    public Position() {
    }

    public Position(String id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}