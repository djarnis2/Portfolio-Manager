package com.lars.portfolio_manager.dto;

import com.lars.portfolio_manager.entities.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CashTransactionForm(
        @Positive(message = "Negative amounts are not allowed")
        BigDecimal cashAmount,

        @NotNull(message = "Transactiontype is required")
        TransactionType transactionType,

        @NotNull(message = "Date is required")
        LocalDateTime dateTime

) {
        public static CashTransactionForm empty() {
                return new CashTransactionForm(
                        BigDecimal.ZERO,
                        TransactionType.DEPOSIT,
                        LocalDateTime.now()
                );
        }
}
