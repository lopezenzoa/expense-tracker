package com.portfolio.expense_tracker.mapper;

import com.portfolio.expense_tracker.dto.CurrencyDTO;
import com.portfolio.expense_tracker.model.Currency;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CurrencyMapper {
    public Currency toEntity(CurrencyDTO dto) {
        return new Currency(
                dto.getId(),
                dto.getName(),
                dto.getSign()
        );
    }

    public CurrencyDTO toDto(Currency entity) {
        return new CurrencyDTO(
                entity.getId(),
                entity.getName(),
                entity.getSign()
        );
    }

    public List<CurrencyDTO> toDtoList(List<Currency> entities) {
        List<CurrencyDTO> currencies = new ArrayList<>();
        entities.forEach(currency -> currencies.add(toDto(currency)));
        return currencies;
    }
}
