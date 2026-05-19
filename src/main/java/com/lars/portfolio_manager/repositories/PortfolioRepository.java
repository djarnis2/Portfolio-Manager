package com.lars.portfolio_manager.repositories;

import com.lars.portfolio_manager.entities.CustomUser;
import com.lars.portfolio_manager.entities.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    List<Portfolio> findByOwner(CustomUser owner);

    boolean existsByOwnerAndNameIgnoreCase(CustomUser owner, String name);

    Optional<Portfolio> findByIdAndOwner(Long id, CustomUser owner);

    List<Portfolio> findAllByOwner(CustomUser owner);

}
