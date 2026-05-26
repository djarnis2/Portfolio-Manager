package com.lars.portfolio_manager.dto;

import com.lars.portfolio_manager.entities.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CashTransactionForm(
        @Positive(message = "Negative amounts are not allowed")
        BigDecimal cashAmount,

        @NotNull(message = "Transactiontype is required")
        TransactionType transactionType,

        @NotNull(message = "Date is required")
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        LocalDateTime dateTime

) {
        public static CashTransactionForm empty() {
                return new CashTransactionForm(
                        null,
                        TransactionType.DEPOSIT,
                        LocalDateTime.now().withSecond(0).withNano(0)
                );
        }
}
