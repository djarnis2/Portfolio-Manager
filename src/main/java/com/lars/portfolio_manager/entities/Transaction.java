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
    @ManyToOne
    @JoinColumn(name = "instrument_id")
    private Instrument instrument;
    private String companyName;
    private String ticker;
    private int amount;
    private BigDecimal price;
    private String isin;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private LocalDateTime dateTime;
    private String currency;
    private BigDecimal cashAmount;
    private BigDecimal sum;

    public Transaction() {
    }

    public Transaction(Portfolio portfolio,
                       Instrument instrument,
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
        this.instrument = instrument;
        this.companyName = companyName;
        this.ticker = ticker;
        this.amount = amount;
        this.price = price;
        this.transactionType = transactionType;
        this.isin = isin;
        this.currency = currency;
        this.dateTime = dateTime;
        this.sum = calculateSum(amount, price);
    }

    public Transaction(Portfolio portfolio,
                       BigDecimal cashAmount,
                       String currency,
                       TransactionType transactionType,
                       LocalDateTime dateTime
                       ) {
        this.portfolio = portfolio;
        this.cashAmount = cashAmount;
        this.currency = currency;
        this.transactionType = transactionType;
        this.dateTime = dateTime;
    }

    public BigDecimal calculateSum(int amount, BigDecimal price) {
        return price.multiply(BigDecimal.valueOf(amount));
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

    public BigDecimal getSum() {
        return sum;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
}
