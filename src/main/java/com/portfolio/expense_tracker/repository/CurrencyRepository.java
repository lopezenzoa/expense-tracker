package com.portfolio.expense_tracker.repository;

import com.portfolio.expense_tracker.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findTop1BySign(String sign);
}
