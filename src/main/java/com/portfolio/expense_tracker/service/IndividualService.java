package com.portfolio.expense_tracker.service;

import com.portfolio.expense_tracker.dto.IndividualDTO;
import java.util.Optional;

public interface IndividualService {
    void create(IndividualDTO clientData);
    Optional<IndividualDTO> getIfOwner();
    Optional<IndividualDTO> updateIfOwner(IndividualDTO newClientData);
}
