package com.portfolio.expense_tracker.service;

import com.portfolio.expense_tracker.dto.AuthDTO;

import java.util.Optional;

public interface AuthService {
    Optional<AuthDTO> register(AuthDTO clientData);
}
