package com.portfolio.expense_tracker.mapper;

import com.portfolio.expense_tracker.dto.IndividualDTO;
import com.portfolio.expense_tracker.model.Individual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IndividualMapper {
    @Autowired
    private CredentialMapper credMapper;

    public Individual toEntity(IndividualDTO dto) {
        return new Individual(
                dto.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                credMapper.toEntity(dto.getCredentials()),
                null,
                null
        );
    }

    public IndividualDTO toDto(Individual entity) {
        return new IndividualDTO(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                credMapper.toDto(entity.getCredentials()),
                null,
                null
        );
    }
}
