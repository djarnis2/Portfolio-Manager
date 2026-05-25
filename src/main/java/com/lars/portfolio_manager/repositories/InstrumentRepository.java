package com.lars.portfolio_manager.repositories;

import com.lars.portfolio_manager.entities.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, Long> {
    Optional<Instrument> findByIsin(String isin);

    boolean existsByIsin(String isin);

}
