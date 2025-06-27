package com.portfolio.expense_tracker.mapper;

import com.portfolio.expense_tracker.dto.LabelDTO;
import com.portfolio.expense_tracker.model.Label;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LabelMapper {
    public Label toEntity(LabelDTO dto) {
        return new Label(
                dto.getId(),
                dto.getName(),
                null,
                null
        );
    }

    public LabelDTO toDto(Label entity) {
        return new LabelDTO(
                entity.getId(),
                entity.getName()
        );
    }

    public List<LabelDTO> toDtoList(List<Label> entities) {
        List<LabelDTO> labels = new ArrayList<>();
        entities.forEach(label -> labels.add(toDto(label)));
        return labels;
    }
}
