package com.portfolio.expense_tracker.mapper;

import com.portfolio.expense_tracker.dto.ExpenseDTO;
import com.portfolio.expense_tracker.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExpenseMapper {
    @Autowired private IndividualMapper individualMapper;
    @Autowired private CurrencyMapper currencyMapper;

    public Expense toEntity(ExpenseDTO dto) {
        return new Expense(
                dto.getId(),
                dto.getAmount(),
                dto.getLoadDate(),
                dto.getCurrency() == null ? null : currencyMapper.toEntity(dto.getCurrency()),
                individualMapper.toEntity(dto.getIndividual()),
                null
        );
    }

    public ExpenseDTO toDto(Expense entity) {
        return new ExpenseDTO(
                entity.getId(),
                entity.getAmount(),
                entity.getLoadDate(),
                entity.getCurrency() == null ? null : currencyMapper.toDto(entity.getCurrency()),
                individualMapper.toDto(entity.getIndividual()),
                null
        );
    }

    public List<ExpenseDTO> toDtoList(List<Expense> entities) {
        List<ExpenseDTO> incomes = new ArrayList<>();
        entities.forEach(income -> incomes.add(toDto(income)));
        return incomes;
    }
}
