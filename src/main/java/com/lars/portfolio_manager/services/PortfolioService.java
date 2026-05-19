package com.lars.portfolio_manager.services;

import com.lars.portfolio_manager.entities.CustomUser;
import com.lars.portfolio_manager.entities.Portfolio;
import com.lars.portfolio_manager.repositories.PortfolioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
}
