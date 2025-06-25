package com.portfolio.expense_tracker.repository;

import com.portfolio.expense_tracker.model.Individual;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IndividualRepository extends JpaRepository<Individual, Long> {
    Optional<Individual> getByCredentials_Email(String email);
}
