package com.lars.portfolio_manager.repositories;

import com.lars.portfolio_manager.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
