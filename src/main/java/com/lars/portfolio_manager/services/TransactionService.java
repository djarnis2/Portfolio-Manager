package com.lars.portfolio_manager.services;

import com.lars.portfolio_manager.dto.TransactionForm;
import com.lars.portfolio_manager.entities.Exchange;
import com.lars.portfolio_manager.entities.Portfolio;
import com.lars.portfolio_manager.entities.Transaction;
import com.lars.portfolio_manager.entities.TransactionType;
import com.lars.portfolio_manager.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> findTransactionsForPortfolio(Portfolio portfolio){
        return transactionRepository.findByPortfolioOrderByDateTimeDesc(portfolio);
    }

    public Transaction createTransaction(Portfolio portfolio, TransactionForm form) {
        TransactionType transactionType = TransactionType.valueOf(form.type());

        Transaction transaction = new Transaction(
                portfolio,
                form.name(),
                form.code(),
                form.amount(),
                form.price(),
                transactionType,
                form.isin(),
                form.currency(),
                form.date().atStartOfDay()
        );
        return transactionRepository.save(transaction);
    }
}
