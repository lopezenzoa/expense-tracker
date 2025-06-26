package com.portfolio.expense_tracker.repository;

import com.portfolio.expense_tracker.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> getAllByIndividualId(Long id);
}
