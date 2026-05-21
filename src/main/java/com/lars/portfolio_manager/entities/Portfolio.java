package com.lars.portfolio_manager.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(optional = false)
    private CustomUser owner;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

    BigDecimal dollarValue;
    BigDecimal kronerValue;
    BigDecimal cashValue;

    public Portfolio() {}

    public Portfolio(String name, CustomUser owner) {
        this.name = name;
        this.owner = owner;
    }


    private double conversionRateDollarKroner;

    public BigDecimal getTransactionValue(double conversionRateDollarKroner) {
        dollarValue = BigDecimal.ZERO;
        kronerValue = BigDecimal.ZERO;
        cashValue = BigDecimal.ZERO;

        for (Transaction tr : transactions) {
            if (tr.getTransactionType().equals(TransactionType.BUY)) {
                if (tr.getCurrency().equals("DKK")) {
                    cashValue = cashValue.subtract(tr.getSum());
                    kronerValue = kronerValue.add(tr.getSum());
                    System.out.println("Buy dkk");
                } else {
                    cashValue = cashValue.subtract(dollarValue.multiply(BigDecimal.valueOf(conversionRateDollarKroner)));
                    dollarValue = dollarValue.add(tr.getSum());
                    System.out.println("Buy dollar");

                }
            }
            if (tr.getTransactionType().equals(TransactionType.SELL)) {
                if (tr.getCurrency().equals("DKK")) {
                    cashValue = cashValue.add(tr.getSum());
                    kronerValue = kronerValue.subtract(tr.getSum());
                    System.out.println("Sell dkk");
                } else {
                    cashValue = cashValue.add(dollarValue.multiply(BigDecimal.valueOf(conversionRateDollarKroner)));
                    dollarValue = dollarValue.subtract(tr.getSum());
                    System.out.println("Sell dollar");
                }
            }
            if (tr.getTransactionType().equals((TransactionType.DEPOSIT))) {
                if (tr.getCurrency().equals("DKK")) {
                    cashValue = cashValue.add(tr.getCashAmount());
                } else {
                    cashValue = cashValue.add(tr.getCashAmount().multiply(BigDecimal.valueOf(conversionRateDollarKroner)));
                }
            }
            if (tr.getTransactionType().equals((TransactionType.WITHDRAW))) {
                if (tr.getCurrency().equals("DKK")) {
                    cashValue = cashValue.subtract(tr.getCashAmount());
                } else {
                    cashValue = cashValue.subtract(tr.getCashAmount().multiply(BigDecimal.valueOf(conversionRateDollarKroner)));
                }
            }
        }
        kronerValue = kronerValue.add(dollarValue.multiply(BigDecimal.valueOf(conversionRateDollarKroner)));
        return kronerValue;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CustomUser getOwner() {
        return owner;
    }

    public BigDecimal getCashValue() {
        return cashValue;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
