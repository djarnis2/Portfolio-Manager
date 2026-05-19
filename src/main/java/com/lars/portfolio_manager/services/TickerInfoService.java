package com.lars.portfolio_manager.services;

import com.lars.portfolio_manager.dto.TickerInfo;
import com.lars.portfolio_manager.entities.Exchange;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.List;

@Service
public class TickerInfoService {
    private final ObjectMapper objectMapper;

    public TickerInfoService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<TickerInfo> loadExchange(Exchange exchange) {
        try {
            ClassPathResource path = new ClassPathResource("exchanges/" + exchange.code() + ".json" );

            return objectMapper.readValue(
                    path.getInputStream(),
                    new TypeReference<List<TickerInfo>>() {
                    }
            );
        } catch (IOException e) {
            throw new RuntimeException("Could not load exchange: " + exchange, e);
        }
    }

    public List<TickerInfo> tickerSearch(Exchange exchange, String query) {
        String toLower = query.toLowerCase();
        String toUpper = query.toUpperCase();

        return loadExchange(exchange)
                .stream()
                .filter(ticker -> ticker.name().contains(toLower)
                || ticker.code().contains(toUpper)
                )
                .limit(10)
                .toList();
    }
}
