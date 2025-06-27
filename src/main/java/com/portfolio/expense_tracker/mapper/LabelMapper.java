package com.portfolio.expense_tracker.mapper;

import com.portfolio.expense_tracker.dto.LabelDTO;
import com.portfolio.expense_tracker.model.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LabelMapper {
    @Autowired private IncomeMapper incomeMapper;

    public Label toEntity(LabelDTO dto) {
        return new Label(
                dto.getId(),
                dto.getName(),
                null,
                incomeMapper.toEntities(dto.getIncomes())
        );
    }

    public LabelDTO toDto(Label entity) {
        return new LabelDTO(
                entity.getId(),
                entity.getName(),
                incomeMapper.toDtoList(entity.getIncomes())
        );
    }

    public List<LabelDTO> toDtoList(List<Label> entities) {
        List<LabelDTO> labels = new ArrayList<>();
        entities.forEach(label -> labels.add(toDto(label)));
        return labels;
    }

    public List<Label> toEntities(List<LabelDTO> dtos) {
        List<Label> labels = new ArrayList<>();
        dtos.forEach(dto -> labels.add(toEntity(dto)));
        return labels;
    }
}
