package com.lars.portfolio_manager.services;

import com.lars.portfolio_manager.entities.Instrument;
import com.lars.portfolio_manager.repositories.InstrumentRepository;
import org.springframework.stereotype.Service;

@Service
public class InstrumentService {
    private InstrumentRepository instrumentRepository;

    public InstrumentService(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }

    public Instrument findOrCreate(String isin, String name, String symbol, String currency, String instrumentType) {
        return instrumentRepository.findByIsin(isin)
                .orElseGet(() -> instrumentRepository.save(
                        new Instrument(isin, name, symbol, currency, instrumentType)
                ));
    }
}
