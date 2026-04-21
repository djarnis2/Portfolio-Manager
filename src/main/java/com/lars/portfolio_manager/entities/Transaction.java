package com.lars.portfolio_manager.entities;


import jakarta.persistence.*;
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
    private double price;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private LocalDateTime dateTime;

    public Transaction() {
    }

    public Transaction(Portfolio portfolio, String companyName, String ticker, int amount, double price, TransactionType transactionType, LocalDateTime dateTime) {
        this.portfolio = portfolio;
        this.companyName = companyName;
        this.ticker = ticker;
        this.amount = amount;
        this.price = price;
        this.transactionType = transactionType;
        this.dateTime = dateTime;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
}
