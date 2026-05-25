package com.lars.portfolio_manager.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record StockHolding(
        String companyName,
        String ticker,
        String isin,
        String currency,
        int totalAmount,
        BigDecimal totalCost,
        BigDecimal averageCostPrice
) {
}
