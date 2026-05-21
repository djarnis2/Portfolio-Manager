package com.lars.portfolio_manager.dto;

import java.math.BigDecimal;

public record StockHolding(
        String companyName,
        String ticker,
        String isin,
        int totalAmount,
        BigDecimal totalCost,
        BigDecimal averagePrice
) {
}
