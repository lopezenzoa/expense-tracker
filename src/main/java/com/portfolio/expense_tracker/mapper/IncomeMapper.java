package com.portfolio.expense_tracker.mapper;

import com.portfolio.expense_tracker.dto.IncomeDTO;
import com.portfolio.expense_tracker.model.Income;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class IncomeMapper {
    @Autowired private IndividualMapper individualMapper;
    @Autowired private CurrencyMapper currencyMapper;

    public Income toEntity(IncomeDTO dto) {
        return new Income(
                dto.getId(),
                dto.getAmount(),
                dto.getLoadDate(),
                dto.getCurrency() == null ? null : currencyMapper.toEntity(dto.getCurrency()),
                individualMapper.toEntity(dto.getIndividual()),
                null
        );
    }

    public IncomeDTO toDto(Income entity) {
        return new IncomeDTO(
                entity.getId(),
                entity.getAmount(),
                entity.getLoadDate(),
                entity.getCurrency() == null ? null : currencyMapper.toDto(entity.getCurrency()),
                individualMapper.toDto(entity.getIndividual()),
                null
        );
    }

    public List<IncomeDTO> toDtoList(List<Income> entities) {
        List<IncomeDTO> incomes = new ArrayList<>();
        entities.forEach(income -> incomes.add(toDto(income)));
        return incomes;
    }

    public List<Income> toEntities(List<IncomeDTO> dtos) {
        List<Income> incomes = new ArrayList<>();
        dtos.forEach(dto -> incomes.add(toEntity(dto)));
        return incomes;
    }

    public List<Income> addToEntitiesList(IncomeDTO newDto) {
        List<Income> entities = new ArrayList<>();
        entities.add(toEntity(newDto));
        return entities;
    }
}
