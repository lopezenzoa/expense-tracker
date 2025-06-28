package com.portfolio.expense_tracker.repository;

import com.portfolio.expense_tracker.model.Expense;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> getAllByIndividualId(Long id);

    @Query(value = "DELETE FROM labels_expenses WHERE label_id = :labelId AND expense_id = :expenseId;", nativeQuery = true)
    @Modifying
    @Transactional
    void removeLabel(@Param("labelId") Long labelId, @Param("expenseId") Long expenseId);
}
