package com.rockall.trade.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "creditpolicy", "amount", "eligible_collateral"})
public class LoanAlert {
    private String id;
    @JsonProperty("creditpolicy")
    private String creditPolicy;
    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("eligible_collateral")
    private Double eligibleCollateral;

    public LoanAlert() {
    }

    public LoanAlert(String id, String creditPolicy, Double amount, Double eligibleCollateral) {
        this.id = id;
        this.creditPolicy = creditPolicy;
        this.amount = amount;
        this.eligibleCollateral = eligibleCollateral;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreditPolicy() {
        return creditPolicy;
    }

    public void setCreditPolicy(String creditPolicy) {
        this.creditPolicy = creditPolicy;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getEligibleCollateral() {
        return eligibleCollateral;
    }

    public void setEligibleCollateral(Double eligibleCollateral) {
        this.eligibleCollateral = eligibleCollateral;
    }
}