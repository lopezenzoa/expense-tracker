package com.portfolio.expense_tracker.repository;

import com.portfolio.expense_tracker.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<Credential, Long> {
}
