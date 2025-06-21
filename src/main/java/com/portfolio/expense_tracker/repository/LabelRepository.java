package com.portfolio.expense_tracker.repository;

import com.portfolio.expense_tracker.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Long> {
}
