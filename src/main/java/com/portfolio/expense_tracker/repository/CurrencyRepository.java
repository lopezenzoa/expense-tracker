package com.portfolio.expense_tracker.repository;

import com.portfolio.expense_tracker.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
