package com.portfolio.expense_tracker.service.impl;

import com.portfolio.expense_tracker.dto.CurrencyDTO;
import com.portfolio.expense_tracker.mapper.CurrencyMapper;
import com.portfolio.expense_tracker.model.Currency;
import com.portfolio.expense_tracker.repository.CurrencyRepository;
import com.portfolio.expense_tracker.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    @Autowired private CurrencyRepository repo;
    @Autowired private CurrencyMapper mapper;

    @Override
    public CurrencyDTO add(CurrencyDTO clientData) {
        Currency saved = repo.save(mapper.toEntity(clientData));
        return mapper.toDto(saved);
    }

    @Override
    public List<CurrencyDTO> getAll() {
        List<Currency> currencies = repo.findAll();
        return mapper.toDtoList(currencies);
    }

    @Override
    public Optional<CurrencyDTO> update(CurrencyDTO newClientData) {
        Currency updated = repo.save(mapper.toEntity(newClientData));
        return Optional.of(mapper.toDto(updated));
    }

    @Override
    public boolean delete(Long id) {
        Optional<Currency> currencyOpt = repo.findById(id);

        if (currencyOpt.isPresent()) {
            repo.deleteById(id);
            return true;
        }

        return false;
    }
}
