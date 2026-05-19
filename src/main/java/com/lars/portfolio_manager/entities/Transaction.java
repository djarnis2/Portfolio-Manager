package com.lars.portfolio_manager.entities;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
    private String companyName;
    private String ticker;
    private int amount;
    private BigDecimal price;
    private String isin;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private LocalDateTime dateTime;
    private String currency;

    public Transaction() {
    }

    public Transaction(Portfolio portfolio,
                       String companyName,
                       String ticker,
                       int amount,
                       BigDecimal price,
                       TransactionType transactionType,
                       String isin,
                       String currency,
                       LocalDateTime dateTime
    ) {
        this.portfolio = portfolio;
        this.companyName = companyName;
        this.ticker = ticker;
        this.amount = amount;
        this.price = price;
        this.transactionType = transactionType;
        this.isin = isin;
        this.currency = currency;
        this.dateTime = dateTime;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }


    public Long getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getTicker() {
        return ticker;
    }

    public int getAmount() {
        return amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getIsin() {
        return isin;
    }

    public String getCurrency() {
        return currency;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
}
