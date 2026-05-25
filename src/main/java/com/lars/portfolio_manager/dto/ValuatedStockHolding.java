package com.lars.portfolio_manager.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ValuatedStockHolding(
        StockHolding holding,
        BigDecimal latestPrice,
        LocalDateTime fetchedAt,
        BigDecimal valuation
) {}
