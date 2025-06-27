package com.portfolio.expense_tracker.service;

import com.portfolio.expense_tracker.dto.IncomeDTO;
import com.portfolio.expense_tracker.dto.IncomeLabelDTO;
import com.portfolio.expense_tracker.dto.LabelDTO;

import java.util.List;
import java.util.Optional;

public interface IncomeService {
    IncomeDTO add(IncomeDTO clientData);
    List<IncomeDTO> getAll();
    Optional<IncomeDTO> getById(Long id);
    Optional<IncomeDTO> update(IncomeDTO newClientData);
    boolean delete(Long id);

    // methods related to the labels of the income
    boolean addLabel(IncomeLabelDTO clientData);
    boolean addLabels(Long id, List<LabelDTO> labels);
    // boolean removeLabel(IncomeLabelDTO clientData);
}
