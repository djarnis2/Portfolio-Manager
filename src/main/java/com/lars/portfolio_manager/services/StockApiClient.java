package com.lars.portfolio_manager.services;

import com.lars.portfolio_manager.dto.TickerInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StockApiClient {

    @Value("${stock.api.base-url}")
    private String baseurl;

    @Value("${stock.api.token}")
    private String token;

    public TickerInfo getTickerInfo(String ticker) {
        return null;
    }
}
