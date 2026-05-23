package com.lars.portfolio_manager.services;

import com.lars.portfolio_manager.dto.StockCall;
import com.lars.portfolio_manager.dto.StockCallResponse;
import com.lars.portfolio_manager.dto.TickerInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class StockApiClient {

    private final RestClient restClient;
    private final String token;

    public StockApiClient(@Value("${stock.api.base-url}") String baseurl,
                          @Value("${stock.api.token}") String token) {
        this.token = token;
        this.restClient = RestClient.builder()
                .baseUrl(baseurl)
                .build();
    }
    public StockCallResponse getStockInfo(String symbol) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("eod")
                        .queryParam("symbols", symbol)
                        .queryParam("access_key", token)
                        .build())
                .retrieve()
                .body(StockCallResponse.class);
    }
}
