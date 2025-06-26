package com.portfolio.expense_tracker.service;

import com.portfolio.expense_tracker.dto.ExpenseDTO;

import java.util.List;
import java.util.Optional;

public interface ExpenseService {
    ExpenseDTO add(ExpenseDTO clientData);
    List<ExpenseDTO> getAll();
    Optional<ExpenseDTO> getById(Long id);
    Optional<ExpenseDTO> update(ExpenseDTO newClientData);
    boolean delete(Long id);
}
