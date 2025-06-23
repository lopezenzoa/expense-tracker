package com.portfolio.expense_tracker.service;

import com.portfolio.expense_tracker.dto.CredentialDTO;

import java.util.Optional;

public interface CredentialService {
    CredentialDTO create(CredentialDTO clientData);
    Optional<String> authenticate(CredentialDTO clientData);
}
