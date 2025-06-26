package com.portfolio.expense_tracker.service;

import com.portfolio.expense_tracker.dto.IncomeDTO;

import java.util.List;
import java.util.Optional;

public interface IncomeService {
    IncomeDTO add(IncomeDTO clientData);
    List<IncomeDTO> getAll();
    Optional<IncomeDTO> getById(Long id);
    Optional<IncomeDTO> update(IncomeDTO newClientData);
    boolean delete(Long id);
}
