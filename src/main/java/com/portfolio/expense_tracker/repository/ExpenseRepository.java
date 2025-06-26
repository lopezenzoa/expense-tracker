package com.portfolio.expense_tracker.repository;

import com.portfolio.expense_tracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> getAllByIndividualId(Long id);
}
