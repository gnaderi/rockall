package com.rockall.trade.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"amount", "creditpolicy", "id", "positions"})
public class Loan {

    private Double amount;
    @JsonProperty("creditpolicy")
    private String creditPolicy;
    private String id;
    private List<Position> positions = null;

    public Loan() {
    }

    public Loan(Double amount, String creditPolicy, String id, List<Position> positions) {
        this.amount = amount;
        this.creditPolicy = creditPolicy;
        this.id = id;
        this.positions = positions;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCreditPolicy() {
        return creditPolicy;
    }

    public void setCreditPolicy(String creditPolicy) {
        this.creditPolicy = creditPolicy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }
}
