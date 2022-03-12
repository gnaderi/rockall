package com.rockall.trade.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"ticker", "price", "id"})
public class NormalizedTrade {
    private String ticker = null;
    private Double price;
    private String isin;

    public NormalizedTrade() {
    }

    public NormalizedTrade(String ticker, Double price, String isin) {
        this.ticker = ticker;
        this.price = price;
        this.isin = isin;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }
}