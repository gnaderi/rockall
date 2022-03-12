package com.rockall.trade.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"currency", "ticker", "exchange", "id", "price", "name"})
public class Trade {
    private String currency;
    @JsonProperty(value = "ticker")
    private String ticker = null;
    private String exchange;
    private String id;
    private Double price;
    private String name;

    public Trade() {
    }

    public Trade(String currency, String ticker, String exchange, String id, Double price, String name) {
        this.currency = currency;
        this.ticker = ticker;
        this.exchange = exchange;
        this.id = id;
        this.price = price;
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}