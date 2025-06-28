package com.portfolio.expense_tracker.service;

import com.portfolio.expense_tracker.dto.ExpenseDTO;
import com.portfolio.expense_tracker.dto.ExpenseLabelDTO;
import com.portfolio.expense_tracker.dto.LabelDTO;

import java.util.List;
import java.util.Optional;

public interface ExpenseService {
    ExpenseDTO add(ExpenseDTO clientData);
    List<ExpenseDTO> getAll();
    Optional<ExpenseDTO> getById(Long id);
    Optional<ExpenseDTO> update(ExpenseDTO newClientData);
    boolean delete(Long id);

    /* methods related to the labels of the expense */
    boolean addLabel(ExpenseLabelDTO clientData);
    boolean addLabels(Long id, List<LabelDTO> labels);
    boolean removeLabel(ExpenseLabelDTO clientData);

    /* filtering methods */
    Optional<List<ExpenseDTO>> filterByLabel(String clientData);
    Optional<List<ExpenseDTO>> filterByMultipleLabels(List<LabelDTO> clientData);
}
