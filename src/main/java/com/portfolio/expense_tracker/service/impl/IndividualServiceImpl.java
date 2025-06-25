package com.portfolio.expense_tracker.service.impl;

import com.portfolio.expense_tracker.dto.IndividualDTO;
import com.portfolio.expense_tracker.mapper.IndividualMapper;
import com.portfolio.expense_tracker.model.Individual;
import com.portfolio.expense_tracker.repository.IndividualRepository;
import com.portfolio.expense_tracker.service.IndividualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IndividualServiceImpl implements IndividualService {
    @Autowired
    private IndividualRepository repo;
    @Autowired
    private IndividualMapper mapper;

    // by default, all the individual profile is empty, since is not necessary for the API to have all personal info
    // then, the Individual can add its name by updating its profile
    @Override
    public void create(IndividualDTO clientData) {
        repo.save(mapper.toEntity(clientData));
    }

    @Override
    public Optional<IndividualDTO> getIfOwner() {
        String authEmail = SecurityContextHolder.getContext().getAuthentication().getName(); // this getName() retrieves the email

        Optional<Individual> individualOpt = repo.getByCredentials_Email(authEmail);

        if (individualOpt.isEmpty() || !individualOpt.get().getCredentials().getEmail().equals(authEmail))
            throw new RuntimeException("Access Denied"); // this could be a personalized exception

        return Optional.of(mapper.toDto(individualOpt.get()));
    }

    @Override
    public Optional<IndividualDTO> updateIfOwner(IndividualDTO newClientData) {
        Optional<IndividualDTO> individualOpt = getIfOwner();

        if (individualOpt.isEmpty())
            return Optional.empty();

        // credService.update(newClientData.getCredentials()); // this is to let the client change its email or password
        Individual updated = repo.save(mapper.toEntity(newClientData));

        return Optional.of(mapper.toDto(updated));
    }
}
