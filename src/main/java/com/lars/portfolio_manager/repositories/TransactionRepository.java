package com.lars.portfolio_manager.repositories;

import com.lars.portfolio_manager.entities.Portfolio;
import com.lars.portfolio_manager.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByPortfolioOrderByDateTimeDesc(Portfolio portfolio);

}
