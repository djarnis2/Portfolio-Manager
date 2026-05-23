package com.lars.portfolio_manager.dto;

import java.math.BigDecimal;

public record StockCall(
        BigDecimal open,
        BigDecimal high,
        BigDecimal low,
        BigDecimal close,
        BigDecimal volume,
        BigDecimal adj_close,
        BigDecimal dividend,
        String price_currency,
        String symbol,
        String exchange,
        String date
) {}