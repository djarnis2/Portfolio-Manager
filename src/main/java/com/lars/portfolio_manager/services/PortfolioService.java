package com.lars.portfolio_manager.services;

import com.lars.portfolio_manager.dto.StockHolding;
import com.lars.portfolio_manager.entities.CustomUser;
import com.lars.portfolio_manager.entities.Portfolio;
import com.lars.portfolio_manager.entities.Transaction;
import com.lars.portfolio_manager.entities.TransactionType;
import com.lars.portfolio_manager.repositories.PortfolioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PortfolioService {
    private PortfolioRepository portfolioRepository;

    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public Portfolio createPortfolio(String name, CustomUser owner) {
        Portfolio portfolio = new Portfolio(name, owner);
        return portfolioRepository.save(portfolio);
    }

    public List<Portfolio> findPortfoliosForUser(CustomUser customUser) {
        return portfolioRepository.findByOwner(customUser);
    }

    public Portfolio findByIdAndOwner(Long id, CustomUser owner) {
        return portfolioRepository.findByIdAndOwner(id, owner)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Portfolio> findAllByOwner(CustomUser owner) {
        return portfolioRepository.findAllByOwner(owner);
    }

    public boolean portfolioNameExistsForUser(CustomUser owner, String name) {
        return portfolioRepository.existsByOwnerAndNameIgnoreCase(owner, name);
    }

    public List<StockHolding> getHoldingOverview(Portfolio portfolio) {
        Map<String, List<Transaction>> isinHoldings = portfolio.getTransactions().stream()
                .filter(tr -> tr.getTransactionType() == TransactionType.BUY ||
                        tr.getTransactionType() == TransactionType.SELL)
                .filter(tr -> (tr.getIsin()) != null && !tr.getIsin().isBlank())
                .collect(Collectors.groupingBy(Transaction::getIsin));
        List<StockHolding> result = new ArrayList<>();
        for (Map.Entry<String, List<Transaction>> entry : isinHoldings.entrySet()) {
            String isin = entry.getKey();
            String currency = "";
            String ticker = "";
            String companyName = "";
            List<Transaction> transactions = entry.getValue();
            int totalAmount = 0;
            BigDecimal averagePrice = BigDecimal.ZERO;
            BigDecimal totalCost = BigDecimal.ZERO;
            for (Transaction tr : transactions) {
                currency = tr.getCurrency();
                if (tr.getTransactionType().equals(TransactionType.BUY)) {
                    totalAmount += tr.getAmount();
                    totalCost = totalCost.add(tr.getPrice().multiply(BigDecimal.valueOf(tr.getAmount())));
                }
                if (tr.getTransactionType().equals(TransactionType.SELL)) {
                    totalAmount -= tr.getAmount();
                    totalCost = totalCost.subtract(tr.getPrice().multiply(BigDecimal.valueOf(tr.getAmount())));
                }
                ticker = tr.getTicker();
                companyName = tr.getCompanyName();
            }
            if (totalAmount > 0) {
                averagePrice = averagePrice.add(totalCost.divide(BigDecimal.valueOf(totalAmount),2, RoundingMode.HALF_UP));

            } else averagePrice = BigDecimal.ZERO;

            result.add(new StockHolding(companyName, ticker, isin, currency, totalAmount, totalCost, averagePrice));

        } return result;
    }

    public BigDecimal getTransactionValue(Portfolio portfolio, double conversionRateDollarKroner) {
        BigDecimal dollarValue = BigDecimal.ZERO;
        BigDecimal kronerValue = BigDecimal.ZERO;
        BigDecimal cashValue = BigDecimal.ZERO;

        for (Transaction tr : portfolio.getTransactions()) {
            if (tr.getTransactionType().equals(TransactionType.BUY)) {
                if (tr.getCurrency().equals("DKK")) {
                    kronerValue = kronerValue.add(tr.getSum());
                } else {
                    dollarValue = dollarValue.add(tr.getSum());

                }
            }

            if (tr.getTransactionType().equals(TransactionType.SELL)) {
                if (tr.getCurrency().equals("DKK")) {
                    kronerValue = kronerValue.subtract(tr.getSum());
                } else {
                    dollarValue = dollarValue.subtract(tr.getSum());
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
        return kronerValue.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getCashValue(Portfolio portfolio, double conversionRateDollarKroner) {
        BigDecimal cashValue = BigDecimal.ZERO;

        for (Transaction tr : portfolio.getTransactions()) {
            if (tr.getTransactionType().equals(TransactionType.BUY)) {
                if (tr.getCurrency().equals("DKK")) {
                    cashValue = cashValue.subtract(tr.getSum());
                } else {
                    cashValue = cashValue.subtract(tr.getSum().multiply(BigDecimal.valueOf(conversionRateDollarKroner)));
                }
            }
            if (tr.getTransactionType().equals(TransactionType.SELL)) {
                if (tr.getCurrency().equals("DKK")) {
                    cashValue = cashValue.add(tr.getSum());
                } else {
                    cashValue = cashValue.add(tr.getSum().multiply(BigDecimal.valueOf(conversionRateDollarKroner)));
                }
            }

            if (tr.getTransactionType().equals((TransactionType.DEPOSIT))) {
                cashValue = cashValue.add(tr.getCashAmount());
            }

            if (tr.getTransactionType().equals((TransactionType.WITHDRAW))) {
                cashValue = cashValue.subtract(tr.getCashAmount());
            }
        }
        return cashValue.setScale(2, RoundingMode.HALF_UP);
    }

}
