package com.lars.portfolio_manager.dto;

import com.lars.portfolio_manager.entities.Instrument;
import com.lars.portfolio_manager.entities.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionForm(
        String code,
        String isin,
        String name,
        String exchange,
        String currency,
        String instrumentType,

        @NotNull(message = "Type is required")
        TransactionType transactionType,

        @Min(value = 1, message = "Amount must be at least 1")
        int amount,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.01", message = "Price must be greater than 0")
        BigDecimal price,

        @NotNull(message = "Date is required")
        LocalDateTime dateTime
) {
    public static TransactionForm empty() {
        return fromTickerInfo(null, null, null, null, null, null);
    }

    public static TransactionForm fromTickerInfo(String code,
                                                 String isin,
                                                 String name,
                                                 String exchange,
                                                 String currency,
                                                 String instrumentType) {
        return new TransactionForm(
                code != null ? code : "",
                isin != null ? isin : "",
                name != null ? name : "",
                exchange != null ? exchange : "",
                currency != null ? currency : "",
                instrumentType != null ? instrumentType : "",
                TransactionType.BUY,
                1,
                null,
                LocalDateTime.now().withSecond(0).withNano(0)
        );
    }
}
