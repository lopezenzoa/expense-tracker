package com.portfolio.expense_tracker.repository;

import com.portfolio.expense_tracker.dto.CredentialDTO;
import com.portfolio.expense_tracker.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CredentialRepository extends JpaRepository<Credential, Long> {
    @Query(value = "SELECT * FROM credentials WHERE email = :email", nativeQuery = true)
    Optional<CredentialDTO> getByEmail(@Param("email") String email);
}
