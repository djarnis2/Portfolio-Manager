package com.lars.portfolio_manager.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public record TickerInfo(
        @JsonProperty("Code")
        String code,
        @JsonProperty("Name")
        String name,
        @JsonProperty("Country")
        String country,
        @JsonProperty("Exchange")
        String exchange,
        @JsonProperty("Currency")
        String currency,
        @JsonProperty("Type")
        String instrumentType,
        @JsonProperty("Isin")
        String isin) {
}
