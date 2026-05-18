package com.lars.portfolio_manager.controllers;

import com.lars.portfolio_manager.dto.TickerInfo;
import com.lars.portfolio_manager.entities.Transaction;
import com.lars.portfolio_manager.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;

//    @GetMapping("/api/exchange/tickers")
//    public TickerInfo getTickers(@RequestBody TickerInfo tickerInfo) {
//        return
//    }

    @PostMapping("/transaction")
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionRepository.save(transaction);
    }

}
