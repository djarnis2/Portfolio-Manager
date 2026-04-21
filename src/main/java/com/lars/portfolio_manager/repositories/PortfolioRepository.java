package com.lars.portfolio_manager.repositories;

import com.lars.portfolio_manager.entities.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}
