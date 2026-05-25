package com.lars.portfolio_manager.services;

import com.lars.portfolio_manager.dto.CashTransactionForm;
import com.lars.portfolio_manager.dto.TransactionForm;
import com.lars.portfolio_manager.entities.Instrument;
import com.lars.portfolio_manager.entities.Portfolio;
import com.lars.portfolio_manager.entities.Transaction;
import com.lars.portfolio_manager.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final InstrumentService instrumentService;

    public TransactionService(TransactionRepository transactionRepository, InstrumentService instrumentService) {
        this.transactionRepository = transactionRepository;
        this.instrumentService = instrumentService;
    }

    public List<Transaction> findTransactionsForPortfolio(Portfolio portfolio){
        return transactionRepository.findByPortfolioOrderByDateTimeDesc(portfolio);
    }

    public Transaction createTransaction(Portfolio portfolio, TransactionForm form) {
        String marketStackCode = marketStackCode(form.exchange(), form.code());
        Instrument instrument = instrumentService.findOrCreate(
                form.isin(),
                form.name(),
                marketStackCode,
                form.currency(),
                form.instrumentType()
        );
        Transaction transaction = new Transaction(
                portfolio,
                instrument,
                form.name(),
                marketStackCode,
                form.amount(),
                form.price(),
                form.transactionType(),
                form.isin(),
                form.currency(),
                form.dateTime()
        );
        return transactionRepository.save(transaction);
    }

    public Transaction createCashTransaction(Portfolio portfolio, CashTransactionForm form) {

        Transaction transaction = new Transaction(
                portfolio,
                form.cashAmount(),
                "DKK",
                form.transactionType(),
                form.dateTime()
        );
        return transactionRepository.save(transaction);
    }

    public String marketStackCode(String exchange, String code) {
        if (code == null || code.isBlank()) {
            return null;
        }
        if ("CO".equalsIgnoreCase(exchange)) {
            return code + ".CO";
        }
        return code;
    }
}
