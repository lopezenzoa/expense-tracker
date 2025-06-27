package com.portfolio.expense_tracker.service;

import com.portfolio.expense_tracker.dto.LabelDTO;

import java.util.List;
import java.util.Optional;

public interface LabelService {
    LabelDTO add(LabelDTO clientData);
    List<LabelDTO> getAll();
    Optional<LabelDTO> getByName(String name);
    Optional<LabelDTO> update(LabelDTO newClientData);
    // boolean delete(Long id);
}
