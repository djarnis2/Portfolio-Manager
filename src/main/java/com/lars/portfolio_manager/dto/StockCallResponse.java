package com.lars.portfolio_manager.dto;

import java.util.List;

public record StockCallResponse(
        List<StockCall> data
) {}
