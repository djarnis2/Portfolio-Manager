package com.lars.portfolio_manager.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Instrument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String isin;

    private String name;

    private String symbol;

    private String currency;

    private String instrumentType;

    private BigDecimal latestPrice;

    private LocalDateTime fetchedAt;

    public Instrument() {
    }

    public Instrument(String isin, String name, String symbol, String currency, String instrumentType) {
        this.isin = isin;
        this.name = name;
        this.symbol = symbol;
        this.currency = currency;
        this.instrumentType = instrumentType;
    }

    public Long getId() {
        return id;
    }

    public String getIsin() {
        return isin;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCurrency() {
        return currency;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public BigDecimal getLatestPrice() {
        return latestPrice;
    }

    public LocalDateTime getFetchedAt() {
        return fetchedAt;
    }
}
