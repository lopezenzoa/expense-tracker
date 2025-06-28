package com.portfolio.expense_tracker.repository;

import com.portfolio.expense_tracker.model.Income;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> getAllByIndividualId(Long id);

    @Query(value = "DELETE FROM labels_incomes WHERE label_id = :labelId AND income_id = :incomeId;", nativeQuery = true)
    @Modifying
    @Transactional
    void removeLabel(@Param("labelId") Long labelId, @Param("incomeId") Long incomeId);
}
