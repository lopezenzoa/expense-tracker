package com.portfolio.expense_tracker.service;

import com.portfolio.expense_tracker.dto.CurrencyDTO;

import java.util.List;
import java.util.Optional;

public interface CurrencyService {
    CurrencyDTO add(CurrencyDTO clientData);
    List<CurrencyDTO> getAll();
    Optional<CurrencyDTO> update(CurrencyDTO newClientData);
    boolean delete(Long id);
}
