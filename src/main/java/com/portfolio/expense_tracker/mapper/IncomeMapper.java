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
    @Autowired private LabelMapper labelMapper;

    public Income toEntity(IncomeDTO dto) {
        return new Income(
                dto.getId(),
                dto.getAmount(),
                dto.getLoadDate(),
                dto.getCurrency() == null ? null : currencyMapper.toEntity(dto.getCurrency()),
                individualMapper.toEntity(dto.getIndividual()),
                dto.getLabels() == null ? new ArrayList<>() : labelMapper.toEntities(dto.getLabels())
        );
    }

    public IncomeDTO toDto(Income entity) {
        return new IncomeDTO(
                entity.getId(),
                entity.getAmount(),
                entity.getLoadDate(),
                entity.getCurrency() == null ? null : currencyMapper.toDto(entity.getCurrency()),
                individualMapper.toDto(entity.getIndividual()),
                entity.getLabels() == null ? new ArrayList<>() : labelMapper.toDtoList(entity.getLabels())
        );
    }

    public List<IncomeDTO> toDtoList(List<Income> entities) {
        List<IncomeDTO> incomes = new ArrayList<>();
        entities.forEach(income -> incomes.add(toDto(income)));
        return incomes;
    }
}
